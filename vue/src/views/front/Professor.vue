<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="flex:1; width:150px" class="card">
                <div style="height:200px" class="search">
                    <div class="left">
                        <div style="margin: 15px; text-align: center">
                            <el-image :src="pro.avatar" style="width: 150px; height: auto;"></el-image>
                        </div>

                    </div>
                    <div class="right">
                        <div class="custom-text">{{ pro.name }}</div>
                        <div>{{ pro.username }}</div>
                        <div>{{ pro.email }}</div>
                        <div>{{ pro.phone }}</div>
                    </div>
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
            <div style="width:400px" class="card">
                <div style="width: 100%; height: 400px"  id="bar"></div>
            </div>
        </div>

    </div>



</template>

<script>
import * as echarts from "echarts";
let barOptions = {
    title: {
        text: '', // 主标题
        subtext: '', // 副标题
        left: 'center'
    },
    xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] // 示例数据：统计的维度（横坐标）
    },
    yAxis: {
        type: 'value'
    },
    tooltip: {
        trigger: 'item'
    },
    series: [
        {
            data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
            type: 'bar',
            itemStyle: {
                normal: {
                    color: function () { return "#" + Math.floor(Math.random() * (256 * 256 * 256 - 1)).toString(16); }
                },
            },
        }
    ]
}
export default {

    name: "Professor",
    data() {
        let authorId = this.$route.query.id
        return {
            tableData: [],  // 所有的数据
            pro: null,
            pageNum: 1,   // 当前的页码
            pageSize: 8,  // 每页显示的个数
            total: 0,
            name: null,
            journal: null,
            author: null,
            authorId: authorId,
            type: null,
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            pageNum1: 1,   // 当前的页码
            pageSize1: 8,
            total1: 0,
        }
    },
    created(){
        this.loadPro();
        this.load(1);
    },
    mounted() {
        
        //this.loadPro();
       // this.load(1);
        //this.loadBar();
    },

    methods: {
        load(pageNum) {
            if (pageNum) this.pageNum = pageNum;
            console.log(this.startDate);
            this.$request.get('/article/selectPager', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    authorId: this.authorId,
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
            this.loadBar();
        },
        loadPro() {
            this.$request.get('/user/selectById/' + this.authorId).then(res => {
                if (res.code === '200') {
                    this.pro = res.data;
                    console.log(this.pro);
                } else {
                    this.$message.error(res.msg);
                }
            }
            )

        },
        loadBar() {
            this.$request.get('/bycited/getProBar',{
                params:{
                   id:this.authorId 
                }
            }).then(res => {
                if (res.code === '200') {
                    console.log(res);
                    let chartDom = document.getElementById('bar');
                    let myChart = echarts.init(chartDom);
                    barOptions.title.text = res.data.text
                    barOptions.title.subtext = res.data.subText
                    barOptions.xAxis.data = res.data.xAxis
                    barOptions.series[0].data = res.data.yAxis
                    myChart.setOption(barOptions);
                } else {
                    this.$message.error(res.msg)
                }
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

.left {
    flex: 1;
    /* 左侧块会占据剩余空间 */
    /* background-color: lightblue; */
}

.right {
    flex: 1;
    /* 右侧块会占据剩余空间 */
    /* background-color: lightgreen; */
}

.custom-text {
    font-size: 50px;
    /* 设置文字大小为 16 像素 */
}
</style>