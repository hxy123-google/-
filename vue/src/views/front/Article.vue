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
                    <el-input placeholder="请输入课程名称" style="width: 100px" v-model="name"></el-input>
                    <el-input placeholder="请输入作者名称" style="width: 100px" v-model="author"></el-input>
                    <el-select v-model="type" placeholder="请选择类型" style="width: 100px">
                        <el-option label="中文" value="CHINESE"></el-option>
                        <el-option label="英文" value="ENGLISH"></el-option>
                    </el-select>
                    <el-select v-model="recommend" placeholder="查找推荐" style="width: 100px">
                        <el-option label="是" value="是"></el-option>
                        <el-option label="否" value="否"></el-option>
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
                        <el-table-column prop="name" label="文献名称" show-overflow-tooltip width="300"></el-table-column>
                        <el-table-column prop="author" label="文献作者" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="content" label="综述" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="type" label="文献类别">
                            <template v-slot="scope">
                                <span v-if="scope.row.type === 'ChINESE'" style="color: #b67259">中文 </span>
                                <span v-else style="color: #448231">英文</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="price" label="文献价格">
                            <template v-slot="scope">
                                <span style="color: red" v-if="scope.row.price > 0">￥ {{ scope.row.price }} / 元</span>
                                <span v-else style="color: green">免费</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="file" label="文献链接" show-overflow-tooltip>
                            <template v-slot="scope">
                                <el-button type="warning" size="mini" @click="down(scope.row.file)">点击下载</el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="discount" label="课程折扣">
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
            <div style="width:260px" class="card"></div>
        </div>

    </div>



</template>

<script>

export default {
    name: "Article",
    data() {
        return {
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
            current: '全部文献',
            categoryList: [],
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
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
            this.$request.get('/article/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    type: this.type,
                    startDate: this.startDate,
                    endDate: this.endDate,
                    recommend: this.recommend,
                    category:this.current==='全部文献'? null:this.current,
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
        down(url) {
            location.href = url
        }
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