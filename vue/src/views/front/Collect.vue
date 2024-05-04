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
                        <el-table-column prop="discount" label="文献折扣">
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
                                <el-button plain type="danger" size="mini" @click=del(scope.row.id)>取消收藏</el-button>
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
        </div>
    </div>
</template>
<script>
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
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            form: {},
            menu: {},
            menu1: {},
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
            this.$request.get('/collection/selectPager', {
                params: {
                    cId: this.user.id,
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    category: this.current,
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
            this.$request.get('/collection/delete', {
                params: {
                    cId: this.user.id,
                    articleId: id
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('取消成功');
                    this.loadleft();

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