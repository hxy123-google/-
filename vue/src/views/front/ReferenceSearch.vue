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
                    <el-input placeholder="请输入作者名称" style="width: 100px" v-model="author"></el-input>
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
                            </template> -->
                        <!-- </el-table-column> -->
                        <el-table-column prop="discount" label="文献折扣">
                            <template v-slot="scope">
                                <span style="color: #448231" v-if="scope.row.discount < 1">{{ scope.row.discount * 10 }}
                                    折</span>
                                <span style="color: #448231" v-else>——</span>
                            </template>
                        </el-table-column>
                        <!-- <el-table-column prop="file" label="文献资料" show-overflow-tooltip></el-table-column> -->
                        <!-- <el-table-column prop="discount" label="文献折扣"></el-table-column> -->
                        <el-table-column prop="reference" label="文献引用量"></el-table-column>
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
            <div style="width:260px" class="card">
                <div v-for="item in userData" :key="item.id">
                    <div>
                        <div style="font-size: 18px; margin: 10px 0">头像</div>
                        <div>
                            <img :src="item.avatar" controls style="width: 65%; height: 400px"
                                @click="naveToUser(item.id)">
                            <!-- <div style="margin-top: 10px">头像：<a :href="courseData.file" target="_blank">{{ userData.avatar }}</a></div> -->
                        </div>
                        <!-- <div style="font-size: 18px; margin: 10px 0">邮箱</div>
                        <div>{{ userData.email }}</div>
                        <div style="font-size: 18px; margin: 10px 0">电话</div>
                        <div>{{ userData.phone }}</div> -->
                    </div>
                    <!--   课程介绍区域   -->
                    <div style="margin-top: 20px">
                        <div style="font-size: 18px">个人介绍</div>
                        <div style="margin-top: 10px" v-html="item.introduce" class="w-e-text w-e-text-container">
                        </div>
                    </div>
                </div>
                <div class="pagination">
                    <el-pagination background @current-change="handleCurrentChange1" :current-page="pageNum1"
                        :page-sizes="[5, 10, 20]" :page-size="pageSize1" layout="total, prev, pager, next"
                        :total="total1">
                    </el-pagination>
                </div>
            </div>
        </div>

    </div>



</template>

<script>

export default {
    name: "ReferenceSearch",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            total: 0,
            name: null,
            journal: null,
            author: null,
            type: null,
            recommend: null,
            userData: [],
            startDate: null,
            endDate: null,
            current: '全部文献',
            categoryList: [],
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            pageNum1: 1,   // 当前的页码
            pageSize1: 1,
            total1: 0,
        }
    },
    mounted() {
        this.loadleft();
        this.load(1);
    },

    methods: {
        loadleft() {
            this.$request.get("/category").then(res => {
                console.log(res);
                this.categoryList = res.data;
                this.categoryList.unshift({ category: '全部文献' })
            })
        },
        selectCategory(categoryName) {
            this.current = categoryName;
            this.load(1);
        },
        load(pageNum) {
            if (this.author !== null) {
                this.$request.get('/user/getAccPro',
                    {
                        params: {
                            name: this.author,
                            role: 'PRO',
                            pageNum1: this.pageNum1,
                            pageSize1: this.pageSize1,
                        }
                    }
                ).then(res => {
                    this.userData = res.data?.list
                    this.total1 = res.data?.total

                    console.log(this.userData);
                })
            }
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
            this.$request.get('/article/selectAccPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    startDate: this.startDate,
                    endDate: this.endDate,
                    author: this.author,
                    category: this.current === '全部文献' ? null : this.current,
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
        },
        reset() {
            this.name = null,
                this.author = null,
                this.startDate = null,
                this.endDate = null
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        down(url) {
            location.href = url
        },
        handleCurrentChange1(pageNum1) {
            if (this.author !== null) {
                this.pageNum1 = pageNum1;
                this.$request.get('/user/getAccPro',
                    {
                        params: {
                            name: this.author,
                            role: 'PRO',
                            pageNum1: this.pageNum1,
                            pageSize1: this.pageSize1,
                        }
                    }
                ).then(res => {
                    console.log(res.data);
                    this.userData = res.data?.list
                    this.total1 = res.data?.total

                })
            }
        },
        naveToUser(id) {
            location.href = '/front/userDetail?id=' + id
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