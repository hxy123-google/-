<template>
  <div class="main-content">
    <div style="display: flex; grid-gap:10px">
      <div style="flex:1 ;width: 50%; margin: 20px auto; min-height: 1000px">
        <div style="text-align: center">
          <el-button type="success">{{ courseData.type === 'ENGLISH' ? '英文' : '中文' }}</el-button>
          <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{ courseData.name
            }}</span>
        </div>
        <div style="text-align: center; margin-top: 15px">
          <span style="color: #12b127" v-if="courseData.price > 0">该资料需要 {{ courseData.price }} 积分</span>
          <span style="color: red" v-else>资料</span>
          <span style="color: #666666; margin-left: 50px">发布时间：{{ courseData.time }}</span>
        </div>
        <!--   课程保密区域   -->
        <div>
          <div style="font-size: 18px; margin: 10px 0">资料</div>
          <div v-if="courseData.price === 0 || flag || user.role === 'PRO' || user.username === courseData.author">
            <video :src="courseData.video" v-if="courseData.type === 'VIDEO'" controls
              style="width: 65%; height: 400px"></video>
            <div style="margin-top: 10px">资料链接：<a :href="courseData.file" target="_blank">{{ courseData.file }}</a>
            </div>
          </div>
          <div v-else>
            <span style="color: red; margin-right: 20px">该课程属于付费课程，购买后可解锁</span>
            <el-button type="warning" size="mini" @click="exchange">购买资料</el-button>
          </div>
        </div>
        <!--   课程介绍区域   -->
        <div style="margin-top: 20px">
          <div style="font-size: 18px">资料介绍</div>
          <div style="margin-top: 10px" v-html="courseData.content" class="w-e-text w-e-text-container"></div>
        </div>
      </div>
      <div style="width:260px" class="card">
                <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
                    <div style="font-size: 20px; flex: 1">文章榜单</div>
                </div>
                <div>
                    <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
                        <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
                            <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
                            <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
                            <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
                            <span style="color: #666" v-else>{{ item.index }}</span>
                        </span>
                        <span style="color: #666;"> <a :href="'/front/ArticleDetail?id=' + item.id">{{ item.name
                                }}</a></span>
                    </div>
                </div>
                <div class="pagination">
                    <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
                        :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next"
                        :total="total">
                    </el-pagination>
                </div>
            </div>
    </div>
  </div>
</template>
<script>
import E from 'wangeditor'
export default {

  data() {
    let scoreId = this.$route.query.id
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      scoreId: scoreId,
      courseData: {},
      showList:[],
      pageNum:1,
      pageSize:5,
      total:0,
      flag: false
    }
  },
  mounted() {
    this.loadCourse();
    this.checkOrder();
    this.loadArticle(1);
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadCourse() {
      console.log("hi");
      this.$request.get('/score/selectById/' + this.scoreId).then(res => {
        if (res.code === '200') {
          this.courseData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    checkOrder() {
      this.$request.get('/scoreorder/selectAll', {
        params: {
          userId: this.user.id,
          scoreId: this.scoreId
        }
      }).then(res => {
        if (res.code === '200') {
          console.log(res);
          if (res.data?.length > 0) {
            this.flag = true
          }
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    exchange() {
      let data = {
        scoreId: this.scoreId,
        score: this.courseData.price,
        userId: this.user.id,
      }
      this.$request.post('/scoreorder/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('兑换成功')
          this.loadCourse()
          this.checkOrder()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadArticle(pageNum) {
            this.pageNum=pageNum;
            this.$request.get('/articlescore/selectArticle/', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    scoreId: this.scoreId
                }
            }).then(res => {
                if (res.code === '200') {
                    console.log(res);
                    this.showList = res.data?.list
                    this.total = res.data?.total
                }
            })
        },
        handleCurrentChange(pageNum) {
            this.loadScore(pageNum);
        },
  }
}
</script>