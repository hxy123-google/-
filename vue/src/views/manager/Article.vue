<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入课程名称" style="width: 200px" v-model="name"></el-input>
      <el-input placeholder="请输入作者名称" style="width: 200px" v-model="author"></el-input>
      <el-select v-model="type" placeholder="请选择类型" style="width: 200px">
        <el-option label="中文" value="CHINESE"></el-option>
        <el-option label="英文" value="ENGLISH"></el-option>
      </el-select>
      <el-select v-model="recommend" placeholder="查找推荐" style="width: 200px">
        <el-option label="是" value="是"></el-option>
        <el-option label="否" value="否"></el-option>
      </el-select>
      <el-date-picker v-model="startDate" type="date" placeholder="选择日期时间"></el-date-picker>
      <el-date-picker v-model="endDate" type="date" placeholder="选择日期时间"></el-date-picker>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="img" label="文献封面" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 60px; height: 40px; border-radius: 10px" v-if="scope.row.img" :src="scope.row.img"
                :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="文献名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="author" label="文献作者" show-overflow-tooltip></el-table-column>
        <el-table-column prop="content" label="综述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="文献类别"></el-table-column>
        <el-table-column prop="file" label="文献链接" show-overflow-tooltip>
          <template v-slot="scope">
            <el-button type="warning" size="mini" @click="down(scope.row.file)">点击下载</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="文献价格" show-overflow-tooltip></el-table-column>
        <el-table-column prop="discount" label="文献折扣"></el-table-column>
        <el-table-column prop="recommend" label="是否推荐"></el-table-column>
        <el-table-column prop="reference" label="文献引用量"></el-table-column>
        <el-table-column prop="time" label="发表时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleRef(scope.row.id)" size="mini">引用</el-button>
            <el-button plain type="primary" @click="delRef(scope.row.id)" size="mini">删除引用</el-button>
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
          :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
        </el-pagination>
      </div>
    </div>
    <el-dialog title="文献引用删除" :visible.sync="EditRefVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="reference">
        <el-select v-model="reference.id" placeholder="请选择">
          <el-option v-for="item in options" :key="item.id" :label="item.id + ' ' + item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="EditRefVisible = false">取 消</el-button>
        <el-button type="primary" @click="delMyRel">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="文献引用" :visible.sync="referenceVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form1" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="引用文献id">
          <el-input v-model="form1.citeId" autocomplete="off" placeholder="请输入引用文献id"></el-input>
        </el-form-item>
        <el-form-item label="被引用文献id" prop="byId">
          <el-input v-model="form1.byId" disabled></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="referenceVisible= false">取 消</el-button>
        <el-button type="primary" @click="addReference">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="文献信息" :visible.sync="fromVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="文献封面">
          <el-upload class="avatar-uploader" :action="$baseUrl + '/files/upload'" :headers="{ token: user.token }"
            list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="文献名称">
          <el-input v-model="form.name" autocomplete="off" placeholder="请输入文献名称"></el-input>
        </el-form-item>
        <el-form-item prop="reference" label="引用数">
          <el-input v-model="form.reference" autocomplete="off" placeholder="请输入文献名称"></el-input>
        </el-form-item>
        <el-form-item prop="author" label="文献作者">
          <el-input v-model="form.author" autocomplete="off" placeholder="请被引用数"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="文献类别">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
            <el-option label="中文" value="CHINESE"></el-option>
            <el-option label="英文" value="ENGLISH"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="recommend" label="是否推荐">
          <el-select v-model="form.recommend" placeholder="请选择" style="width: 100%">
            <el-option label="是" value="是"></el-option>
            <el-option label="否" value="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="category" label="文献种类">
          <el-select v-model="form.category" placeholder="请选择" style="width: 100%">
            <el-option label="复合材料" value="复合材料"></el-option>
            <el-option label="石墨烯" value="石墨烯"></el-option>
            <el-option label="冶金学" value="冶金学"></el-option>
            <el-option label="碳纳米管" value="碳纳米管"></el-option>
            <el-option label="TiO_2" value="TiO_2"></el-option>
            <el-option label="建筑材料" value="建筑材料"></el-option>
            <el-option label="其他" value:null></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="journal" label="文献期刊">
          <el-select v-model="form.journal" placeholder="请选择" style="width: 100%">
            <el-option label="Advanced Materials" value="Advanced Materials"></el-option>
            <el-option label="Materials Science and Engineering: R: Reports"
              value="Materials Science and Engineering: R: Reports"></el-option>
            <el-option label="Nature Materials" value="Nature Materials"></el-option>
            <el-option label="硅酸盐学报" value="硅酸盐学报"></el-option>
            <el-option label="其他" value:null></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文献链接">
          <el-upload class="avatar-uploader" :action="$baseUrl + '/files/upload'" :headers="{ token: user.token }"
            :on-success="handleVideoSuccess">
            <el-button type="primary">上传文献</el-button>
          </el-upload>
        </el-form-item>
        <!-- <el-form-item prop="file" label="">
            <el-input v-model="form.file" autocomplete="off" placeholder="请输入资料链接"></el-input>
          </el-form-item> -->
        <el-form-item prop="price" label="文献价格">
          <el-input v-model="form.price" autocomplete="off" placeholder="请输入文献折扣"></el-input>
        </el-form-item>
        <el-form-item prop="discount" label="文献折扣">
          <el-input v-model="form.discount" autocomplete="off" placeholder="请输入文献折扣"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="文献综述">
          <el-input type="textarea" :rows="4" v-model="form.content" autocomplete="off"
            placeholder="请输入文献综述"></el-input>
        </el-form-item>
        <el-form-item prop="time" label="发表时间">
          <div class="block">
            <el-date-picker v-model="form.time" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Article",
  data() {
    return {
      EditRefVisible: false,
      reference:{
        id:null
      },
      which:null,
      options: [],
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      author: null,
      type: null,
      recommend: null,
      startDate: null,
      endDate: null,
      category: null,
      discount: null,
      price: null,
      fromVisible: false,
      referenceVisible: false,
      form: {},
      form1: {},//ref
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          { required: true, message: '请输入文献名称', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择文献类型', trigger: 'blur' },
        ],
        author: [
          { required: true, message: '请输入作者名字', trigger: 'blur' },
        ],
        time: [
          { required: true, message: '请输入发表时间', trigger: 'blur' },
        ],
        recommend: [
          { required: true, message: '请选择是否推荐', trigger: 'blur' },
        ],
        price: [
          { required: true, message: '请输入文献价格', trigger: 'blur' },
        ],
        discount: [
          { required: true, message: '请输入文献折扣', trigger: 'blur' },
        ],

      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          console.log(this.form.id);
          this.$request({
            url: this.form.id ? '/article/update' : '/article/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/article/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/article/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {
      if (this.startDate != null) {
        var dateTimeString = this.startDate;
        var dateTime = new Date(dateTimeString);
        var year = dateTime.getFullYear();
        var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
        var day = dateTime.getDate();
        this.startDate = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
      }// 分页查询
      if (this.endDate != null) {
        var dateTimeString = this.endDate;
        var dateTime = new Date(dateTimeString);
        var year = dateTime.getFullYear();
        var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
        var day = dateTime.getDate();
        this.endDate = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
      }// 分页查询}
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/article/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
          startDate: this.startDate,
          endDate: this.endDate,
          recommend: this.recommend,
          category: this.category,
          price: this.price,
          discount: this.discount
        }
      }).then(res => {
        console.log(res);
        for (var i = 0; i < res.data.list.length; i++) {

          var dateTimeString = res.data.list[i].time;
          var dateTime = new Date(dateTimeString);
          var year = dateTime.getFullYear();
          var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
          var day = dateTime.getDate();
          res.data.list[i].time = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
        }
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleImgSuccess(res) {
      this.form.img = res.data
    },
    handleVideoSuccess(res) {
      this.form.file = res.data
    },
    down(url) {
      location.href = url
    },
    handleRef(id) {
      this.form1.byId = id;
      this.referenceVisible = true;
      //this.addReference();
    },
    addReference() {
      this.$request.get('/bycited/add', {
        params: {
          byId: this.form1.byId,
          citeId: this.form1.citeId,
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success('添加成功')
        } else {
          this.$message.error(res.msg)
        }
      })
      this.referenceVisible = false;
      this.reset();
    },
    loadrefdata(id) {
      this.$request.get('/bycited/selectAll', {
        params: {
          byId: id,
        }
      }).then(res => {
        if (res.code === '200') {
          // for (var i = 0; i < res.data.list.length; i++) {

          //   var dateTimeString = res.data.list[i].time;
          //   var dateTime = new Date(dateTimeString);
          //   var year = dateTime.getFullYear();
          //   var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
          //   var day = dateTime.getDate();
          //   res.data.list[i].time = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
          // }
          this.options = res.data;
          console.log(this.options);
        } else {
          this.$message.error(res.msg);
        }
      })

    },
    delMyRel(){
      this.$request.get('/bycited/deletbyId/', {
        params: {
          byId:this.which,
          citeId: this.reference.id,
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.msg);
        }
        this.reference.id=null
      })
      
    },
    delRef(id) {
      this.which=id;
      this.loadrefdata(id);
      this.EditRefVisible = true;
    }
  }
}
</script>

<style scoped>
/* 将容器设置为 Flexbox 布局 */
.search {
  display: flex;
}
</style>