<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="width:150px" class="card">
                <div style="display: flex; justify-content: space-between;">
                    <el-button plain type="danger" size="mini" @click="openAdd">添加</el-button>
                    <el-button plain type="danger" size="mini" @click="openMinus">删除</el-button>
                </div>
                <!-- <div class="category-item"  v-for="item in categoryList" :key="item.id">{{ item.category }}</div> -->
                <div class="category-item" :class="{ 'category-item-active': item.name === current }"
                    v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name
                    }}
                </div>
            </div>
            <div style="flex:1" class="card">
                <div class="search">
                    <el-select v-model="journal" placeholder="请选择期刊" style="width: 100px">
                        <el-option label="Advanced Materials" value="Advanced Materials"></el-option>
                        <el-option label="Materials Science and Engineering: R: Reports"
                            value="Materials Science and Engineering: R: Reports"></el-option>
                        <el-option label="Nature Materials" value="Nature Materials"></el-option>
                        <el-option label="硅酸盐学报" value="硅酸盐学报"></el-option>
                        <el-option label="全部" value:null></el-option>
                    </el-select>
                    <el-input placeholder="文献关键字" style="width: 100px" v-model="keywords"></el-input>
                    <el-input placeholder="文献名称" style="width: 100px" v-model="aName"></el-input>
                    <el-input placeholder="作者名称" style="width: 100px" v-model="author"></el-input>
                    <el-select v-model="type" placeholder="请选择类型" style="width: 100px">
                        <el-option label="中文" value="CHINESE"></el-option>
                        <el-option label="英文" value="ENGLISH"></el-option>
                        <el-option label="全部" value:null></el-option>
                    </el-select>
                    <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
                    <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
                    <el-button type="warning" plain style="margin-left: 10px" @click="openArticle">新增</el-button>
                </div>
                <div class="table">
                    <el-table :data="tableData">
                        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
                        <el-table-column prop="img" label="文献封面" show-overflow-tooltip>
                            <template v-slot="scope">
                                <div style="display: flex; align-items: center">
                                    <el-image style="width: 60px; height: 40px; border-radius: 10px"
                                        v-if="scope.row.img" :src="scope.row.img"
                                        :preview-src-list="[scope.row.img]"></el-image>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="articlename" label="文献名称" show-overflow-tooltip width="300">
                            <template v-slot="scope">
                                <a :href="'/front/ruser?id=' + scope.row.id">{{ scope.row.articlename }}</a>
                            </template>
                        </el-table-column>

                        <el-table-column prop="author" label="文献作者" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="type" label="文献类别">
                            <template v-slot="scope">
                                <span v-if="scope.row.type === 'CHINESE'" style="color: #b67259">中文 </span>
                                <span v-else style="color: #448231">英文</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="time" label="发表时间"></el-table-column>
                    </el-table>

                    <div class="pagination">
                        <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
                            :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next"
                            :total="total">
                        </el-pagination>
                    </div>
                </div>
            </div>
            <el-dialog title="文献引用" :visible.sync="fromVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                    <el-form-item prop="name" label="引用文献id">
                        <el-input v-model="form.citeId" autocomplete="off" placeholder="请输入引用文献id"></el-input>
                    </el-form-item>
                    <el-form-item label="被引用文献id" prop="byId">
                        <el-input v-model="form.byId" disabled></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="fromVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addReference">确 定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="添加分类" :visible.sync="menuVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="menu">
                    <el-form-item prop="name" label="添加目录名">
                        <el-input v-model="menu.name" autocomplete="off" placeholder="请输入目录名称"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="fromVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addMenu">确 定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="删除分类" :visible.sync="minusVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="menu1">
                    <el-select v-model="menu1.value" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.name">
                        </el-option>
                    </el-select>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="fromVisible = false">取 消</el-button>
                    <el-button type="primary" @click="minusMenu">确 定</el-button>
                </div>

            </el-dialog>
            <el-dialog title="文献信息" :visible.sync="addVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="form1" :rules="rules" ref="formRef">
                    <el-form-item label="文献封面">
                        <el-upload class="avatar-uploader" :action="$baseUrl + '/files/upload'"
                            :headers="{ token: user.token }" list-type="picture" :on-success="handleImgSuccess">
                            <el-button type="primary">上传图片</el-button>
                        </el-upload>
                    </el-form-item>
                    <el-form-item prop="articlename" label="文献名称">
                        <el-input v-model="form1.articlename" autocomplete="off" placeholder="请输入文献名称"></el-input>
                    </el-form-item>
                    <el-form-item prop="author" label="文献作者">
                        <el-input v-model="form1.author" autocomplete="off" placeholder="文献作者"></el-input>
                    </el-form-item>
                    <el-form-item prop="keywords" label="关键字">
                        <el-input v-model="form1.keywords" autocomplete="off" placeholder="关键字"></el-input>
                    </el-form-item>
                    <el-form-item prop="type" label="文献类别">
                        <el-select v-model="form1.type" placeholder="请选择类型" style="width: 100%">
                            <el-option label="中文" value="CHINESE"></el-option>
                            <el-option label="英文" value="ENGLISH"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="name" label="文献种类">
                        <el-select v-model="form1.name" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.name">
                        </el-option>
                    </el-select>
                    </el-form-item>
                    <el-form-item prop="journal" label="文献期刊">
                        <el-input v-model="form1.journal" autocomplete="off" placeholder="请输入文献期刊名称"></el-input>
                    </el-form-item>
                    <el-form-item label="文献链接">
                        <el-upload class="avatar-uploader" :action="$baseUrl + '/files/upload'"
                            :headers="{ token: user.token }" :on-success="handleVideoSuccess">
                            <el-button type="primary">上传文献</el-button>
                        </el-upload>
                    </el-form-item>
                    <el-form-item prop="time" label="发表时间">
                        <div class="block">
                            <el-date-picker v-model="form1.time" type="date" placeholder="选择日期">
                            </el-date-picker>
                        </div>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="fromVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addMyMenu">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import dayjs from 'dayjs'; // 导入 Day.js
export default {
    name: "Collect",
    data() {
        return {
            options: [],
            categoryList: [],
            current: null,
            fromVisible: false,
            menuVisible: false,
            minusVisible: false,
            addVisible:false,
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            form: {},
            form1:{},
            menu: {},
            menu1: {},
            journal: null,
            aName: null,
            author: null,
            keywords: null,
            type: null,
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
        }
    },
    mounted() {
        this.loadleft();
        this.load(1);
    },
    methods: {
        openMinus() {
            this.minusVisible = true;
        },
        openAdd() {
            this.menuVisible = true;
        },
        openArticle(){
            this.addVisible=true;
        },
        minusMenu() {
            this.$request.get("/menu/Delmenu", {
                params: {
                    userId: this.user.id,
                    name: this.menu1.value
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('删除成功')
                } else {
                    this.$message.error(res.msg)
                }
            })

        },
        addMenu() {
            this.$request.get("/menu/Addmenu", {
                params: {
                    userId: this.user.id,
                    name: this.menu.name
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('添加成功');

                }
                else {
                    this.$message.error(res.msg)
                }
            });
            this.load
            this.minusVisible = false;
        },

        load(pageNum) {
            if (pageNum) this.pageNum = pageNum;
            console.log(this.startDate);
            this.$request.get('Userarticle/selectAll', {
                params: {
                    cId: this.user.id,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.current,
                    journal: this.journal,
                    articlename: this.aName,
                    author: this.author,
                    keywords: this.keywords,
                    type: this.type,
                    
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
        del(id) {
            this.$request.get('/Userarticle/delete', {
                params: {
                    id:id
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('取消成功');
                    this.load(1);

                } else {
                    this.$message.error(res.msg)
                }
            })

        },
        handleRef(id) {
            this.form.byId = id;
            this.fromVisible = true;
            //this.addReference();
        },
        addReference() {
            this.$request.get('/bycited/add', {
                params: {
                    byId: this.form.byId,
                    citeId: this.form.citeId,
                    userId: this.user.id
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('添加成功')
                } else {
                    this.$message.error(res.msg)
                }
            })
            this.fromVisible = false;
        },
        selectCategory(categoryName) {
            this.current = categoryName;
            this.author = null,
                this.load(1);
        },
        loadleft() {
            this.$request.get("/menu/selectAll", {
                params: {
                    userId: this.user.id
                }
            }).then(res => {
                console.log(res);
                this.categoryList = res.data;
                this.options = res.data;
                console.log(this.options);
                //this.categoryList.unshift({ name: '全部文献' })
            })
        },
        addMyMenu(){
            const formattedDate = dayjs(this.form1.time).format('YYYY-MM-DD');
            console.log(formattedDate);
             this.$request.get('/Userarticle/add/', {
                params: {
                    articleId: -1,
                    cId: this.user.id,
                    name:this.form1.name,
                    journal:this.form1.journal,
                    articlename:this.form1.articlename,
                    author:this.form1.author,
                    keywords:this.form1.keywords,
                    time:formattedDate
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('添加成功')
                } else {
                    this.$message.error(res.msg)
                }
            })

        },
        reset() {
            this.author = null;
            this.aName = null;
            this.type = null;
            this.category = null;
            this.keywords = null;
            this.journal = null;
            this.load(1)
        },
    }
}
</script>
<style>
.category-item {
    text-align: center;
    padding: 10px 0;
    font-size: 16px;
    cursor: pointer;
}

.category-item-active {
    background-color: #1890ff;
    color: #fff;
    border-radius: 5px;
}

.search {
    display: flex;
}
</style>