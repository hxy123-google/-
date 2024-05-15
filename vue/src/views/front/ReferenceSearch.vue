<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
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
                        <el-table-column prop="content" label="综述" show-overflow-tooltip></el-table-column>
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
                        <!-- <el-table-column prop="file" label="文献链接" show-overflow-tooltip>
                            <template v-slot="scope">
                                <el-button type="warning" size="mini" @click="down(scope.row.file)">点击下载</el-button>
                            </template>
                        </el-table-column> -->
                        <el-table-column prop="discount" label="文献折扣">
                            <template v-slot="scope">
                                <span style="color: #448231" v-if="scope.row.discount < 1">{{ scope.row.discount * 10 }}
                                    折</span>
                                <span style="color: #448231" v-else>——</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="discount" label="文献折扣"></el-table-column>
                        <el-table-column prop="reference" label="文献引用量"></el-table-column>
                        <el-table-column prop="time" label="发表时间"></el-table-column>
                        <el-table-column label="引用" width="180" align="center">
                            <template v-slot="scope">
                                <!-- <el-button plain type="primary" @click="handleRef(scope.row.id)"
                                    size="mini">引用</el-button> -->
                                <el-button plain type="danger" size="mini" @click=col(scope.row.id)>收藏</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
        <el-dialog title="文献收藏" :visible.sync="menuVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
            <el-form label-width="100px" style="padding-right: 50px" :model="menu">
                <el-select v-model="menu.name" placeholder="请选择">
                    <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.name">
                    </el-option>
                </el-select>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="menuVisible = false">取 消</el-button>
                <el-button type="primary" @click="addMyMenu">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

export default {
    name: "ReferenceSearch",
    data() {
        let myArray = this.$route.query.articleArray;
        return {
            options:[],
            tableData: [],  // 所有的数据
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            myArray: myArray,
            menuVisible: false,
            menu: {  // 用于收藏的表单对象
                name: null,
            },
        }
    },
    mounted() {
        //let myArray = this.$route.query.articleArray;
        //console.log(myArray); // 输出 [1, 2, 3]
        //this.loadleft();
        this.load();
    },

    methods: {
        load() {
            this.tableData = this.myArray.data;
            console.log(this.tableData);
            this.tableData.forEach((item, index) => {
                console.log(`Item ${index}:`, item);
                var dateTimeString = item.time;
                var dateTime = new Date(dateTimeString);
                var year = dateTime.getFullYear();
                var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
                var day = dateTime.getDate();
                item.time = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
                // 继续输出其他属性
            })
        },
        addMyMenu() {
            console.log(this.menu.name);
            this.$request({
            url:'/Userarticle/add/', 
            method:'POST',
                data: {
                    articleId: this.articleId,
                    cId: this.user.id,
                    name: this.menu.name,
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('添加成功')
                } else {
                    this.$message.error(res.msg)
                }
            })

        },
        col(id) {
            this.menuVisible = true;
            this.loadleftmenu();
            this.articleId = id;
        },
        loadleftmenu() {
            this.$request.get("/menu/selectAll", {
                params: {
                    userId: this.user.id
                }
            }).then(res => {
                console.log(res);
               // this.categoryList = res.data;
                this.options = res.data;
                //this.categoryList.unshift({ name: '全部文献' })
            })
        },
    },
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