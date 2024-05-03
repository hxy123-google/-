<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="width:150px" class="card">
                <!-- <div class="category-item category-item-active">全部</div> -->
                <!-- <div class="category-item"  v-for="item in categoryList" :key="item.id">{{ item.category }}</div> -->
                <div class="category-item" :class="{ 'category-item-active': item.category === current }"
                    v-for="item in categoryList" :key="item.id" @click="selectCategory(item.category)">{{ item.category
                    }}</div>
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
                    <el-input placeholder="请输入文献名称" style="width: 100px" v-model="name"></el-input>
                    <el-input placeholder="请输入作者名称" style="width: 100px" v-model="author"></el-input>
                    <el-select v-model="type" placeholder="请选择类型" style="width: 100px">
                        <el-option label="中文" value="CHINESE"></el-option>
                        <el-option label="英文" value="ENGLISH"></el-option>
                        <el-option label="全部" value:null></el-option>
                    </el-select>
                    <el-select v-model="sr" placeholder="是否按引用排序" style="width: 100px">
                        <el-option label="是" value="是"></el-option>
                        <el-option label="否" value="否"></el-option>
                        <!-- <el-option label="全部" value:null></el-option> -->
                    </el-select>
                    <el-date-picker v-model="startDate" type="date" placeholder="选择日期时间"></el-date-picker>
                    <el-date-picker v-model="endDate" type="date" placeholder="选择日期时间"></el-date-picker>
                    <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
                    <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
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
                        <el-table-column prop="name" label="文献名称" show-overflow-tooltip width="300">
                            <template v-slot="scope">
                                <a :href="'/front/articleDetail?id=' + scope.row.id">{{ scope.row.name }}</a>
                            </template>
                        </el-table-column>

                        <el-table-column prop="author" label="文献作者" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="type" label="文献类别">
                            <template v-slot="scope">
                                <span v-if="scope.row.type === 'CHINESE'" style="color: #b67259">中文 </span>
                                <span v-else style="color: #448231">英文</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="price" label="所属积分">
                            <template v-slot="scope">
                                <span style="color: #12b127; font-size: 15px" v-if="scope.row.price > 0">{{
                                    scope.row.price }} 积分</span>
                                <span v-else style="color: green">公开资料</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="discount" label="课程折扣">
                            <template v-slot="scope">
                                <span style="color: #448231" v-if="scope.row.discount < 1">{{ scope.row.discount * 10 }}
                                    折</span>
                                <span style="color: #448231" v-else>——</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="reference" label="文献引用量">
                            <template v-slot="scope">
                                <a :href="'/front/refarticle?id=' + scope.row.id">{{ scope.row.reference }}</a>
                            </template>
                        </el-table-column>
                        <el-table-column prop="time" label="发表时间"></el-table-column>
                        <el-table-column label="引用" width="180" align="center">
                            <template v-slot="scope">
                                <el-button plain type="primary" @click="handleRef(scope.row.id)"
                                    size="mini">引用</el-button>
                                <el-button plain type="danger" size="mini" @click=col(scope.row.id)>收藏</el-button>
                            </template>
                        </el-table-column>
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
            <el-dialog title="文献收藏" :visible.sync="menuVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="menu" >
                    <el-select v-model="menu.name" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.name">
                        </el-option>
                    </el-select>
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

export default {
    name: "Article",
    data() {
        return {
            articleId:null,
            options: [],
            fromVisible: false,
            menuVisible: false,
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            name: null,
            journal: null,
            author: null,
            type: null,
            recommend: null,
            startDate: null,
            endDate: null,
            current: '全部文献',
            categoryList: [],
            sr: null,
            form: {},
            menu: {  // 用于收藏的表单对象
            name: null,
        },
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),

        }
    },
    mounted() {
        this.loadleft();
        this.load(1);
    },

    methods: {
        addMyMenu(){
            console.log(this.menu.name);
             this.$request.get('/collection/add/', {
                params: {
                    articleId: this.articleId,
                    cId: this.user.id,
                    name:this.menu.name,
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('添加成功')
                } else {
                    this.$message.error(res.msg)
                }
            })

        },
        loadleft() {
            this.$request.get("/category").then(res => {
                console.log(res);
                this.categoryList = res.data;
                this.categoryList.unshift({ category: '全部文献' })
            })
        },
        selectCategory(categoryName) {
            this.current = categoryName;
            this.author = null,
                this.load(1);
        },
        load(pageNum) {

            console.log(this.startDate);
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
            console.log(this.startDate);
            if (this.sr === '是') {

                this.$request.get('/article/selectPager', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                        type: this.type,
                        startDate: this.startDate,
                        endDate: this.endDate,
                        recommend: this.recommend,
                        category: this.current === '全部文献' ? null : this.current,
                        author: this.author,
                        journal: this.journal
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
            }
            else {
                this.$request.get('/article/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                        type: this.type,
                        startDate: this.startDate,
                        endDate: this.endDate,
                        recommend: this.recommend,
                        category: this.current === '全部文献' ? null : this.current,
                        journal: this.journal,
                        author: this.author
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

            }
        },
        reset() {
            this.author = null,
                this.name = null
            this.load(1)
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
            this.reset();
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        down(url) {
            location.href = url
        },
        col(id) {
            this.menuVisible = true;
            this.loadleftmenu();
            this.articleId=id;
            //console.log(this.menu.name);
            // this.$request.get('/collection/add/', {
            //     params: {
            //         articleId: id,
            //         cId: this.user.id,
            //         name:this.menu.value
            //     }
            // }).then(res => {
            //     if (res.code === '200') {
            //         this.$message.success('添加成功')
            //     } else {
            //         this.$message.error(res.msg)
            //     }
            // })
        },
        loadleftmenu() {
            this.$request.get("/menu/selectAll", {
                params: {
                    userId: this.user.id
                }
            }).then(res => {
                console.log(res);
                this.categoryList = res.data;
                this.options = res.data;
                //this.categoryList.unshift({ name: '全部文献' })
            })
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