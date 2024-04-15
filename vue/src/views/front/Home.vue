<template>
  <div class="main-content">
    <div style="display: flex">
      <div style="flex: 12%; height: 350px; background-color: #0c0c0c"></div>
      <div style="flex: 76%">
        <el-carousel height="350px">
          <el-carousel-item v-for="item in carouselData" :key="item">
            <img :src="item" alt="" style="width: 100%; height: 350px">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div style="flex: 12%; height: 350px; background-color: #0c0c0c"></div>
    </div>
    <div style="width: 76%; margin: 50px auto; min-height: 1000px">
      <div style="display: flex">
        <div style="flex: 1">
          <span style="font-size: 24px; color: #333333; margin-right: 50px">文献选择</span>
          <el-button type="primary" @click="initValue('CHINESE')">中文文献</el-button>
          <el-button type="success" @click="initValue('SCORE')">积分专区</el-button>
          <el-button type="warning" @click="initValue('ENGLISH')">英文文献</el-button>
        </div>
        <div style="width: 300px">
          <el-button type="info">签到</el-button>
          <span style="margin-left: 30px; color: #12b127; font-weight: 550">上次签到：</span>
          <span style="color: #666666">2024-01-02 08:52:49</span>
        </div>
      </div>
      <div style="display:flex;margin-top: 20px;height:300px">
        <div style="flex:1;margin-right:10px;height:300px">
          <img :src="recommend.img" alt="" style="width:100%;height:270px; border-radius:10px"   @click="navto(recommend.id)">
          <div style="font-size: 15px; margin-top: 5px" class="overflowShow">{{ recommend.name }}</div>
        </div>
        <div style="flex:2;margin-left:10px;height:500px">
          <el-row :gutter="20">
            <el-col :span="6" style="margin-bottom: 35px" v-for="item in rightData" :key="item">
              <img :src="item.img" alt="" style="width: 100%; height: 100px; border-radius: 5px" @click="navto(item.id)">
              <div style="color: #333333; margin-top: 10px" class="overflowShow">{{ item.name }}</div>
            </el-col>
          </el-row>

        </div>
      </div>
      <div style="margin-top: 50px">
        <span style="font-size: 24px; color: #333333; margin-right: 50px">学术大佬</span>
        <div style="display: flex; margin-top: 20px; height: 300px">
          <div style="flex: 2; margin-right: 10px">
            <el-row :gutter="20">
              <el-col :span="6" style="margin-bottom: 35px" v-for="item in leftData">
                <img :src="item.avatar" alt=""
                  style="width: 100%; height: 100px; border-radius: 5px; border: 1px solid #cccccc"
                  @click="naveToUser(item.id)">
                <div style="color: #333333; margin-top: 10px" class="overflowShow">{{ item.username }}</div>
              </el-col>
            </el-row>
          </div>
          <!-- <div style="flex: 1; margin-left: 10px; width: 0">
            <img :src="fileRecommend?.img" alt="" style="width: 100%; height: 270px; border-radius: 5px">
            <div style="font-size: 15px; margin-top: 5px" class="overflowShow">{{ fileRecommend?.name }}</div>
          </div> -->
        </div>

      </div>

    </div>
  </div>


</template>

<script>

export default {

  data() {
    return {
      carouselData: [
        require('@/assets/imgs/lun1.jpg'),
        require('@/assets/imgs/lun2.jpg'),
        require('@/assets/imgs/lun3.jpg'),
        require('@/assets/imgs/lun4.jpg'),
      ],
      type: 'CHINESE',
      recommend: {},
      rightData: [],
      leftData: [],
    }
  },
  mounted() {
    // this.loadRecommend()
    // this.loadRightData()
    this.loadLeftData();
    this.getData();
  },

  methods: {
    loadLeftData() {
      this.$request.get('/user/selectTop8?role=' + 'PRO').then(res => {
        if (res.code === '200') {
          console.log(res);
          this.leftData = res.data;

        } else {
          console.log(res);
          this.$message.error(res.msg)
        }
      }
      )
    },
    initValue(type) {
      this.type = type,
        console.log(this.type);
      this.getData();
    },
    getData() {
      // 积分专区这边的数据
      if ('SCORE' === this.type) {
        // 1. 获取推荐的那个积分课程
        this.getRecommend('/score/getRecommend')
        // 2. 获取推荐之外的最新的8个课程
        this.getRightData('/score/getTop8')
      } else {
        // 1. 获取推荐的那个视频课程
        this.getRecommend('/article/getRecommend?type=' + this.type)
        // 2. 获取推荐之外的最新的8个视频课程
        this.getRightData('/article/selectTop8?type=' + this.type)
      }
    },
    getRecommend(url) {
      this.$request.get(url).then(res => {
        if (res.code === '200') {
          this.recommend = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    loadRecommend() {
      this.$request.get('/article/getRecommend').then(res => {
        if (res.code === '200') {
          this.recommend = res.data;
          console.log(this.recommend.img);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getRightData(url) {
      this.$request.get(url).then(res => {
        if (res.code === '200') {
          this.rightData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadRightData() {
      this.$request.get('/article/selectTop8').then(res => {
        if (res.code === '200') {
          this.rightData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    naveToUser(id) {
      location.href = '/front/userDetail?id=' + id
    },
    navto(id) {
      if (this.type === 'score') {
        // 我们往积分课程详情页跳
        location.href = '/front/scoreDetail?id=' + id
      } else {
        // 往课程详情页跳
        location.href = '/front/articleDetail?id=' + id
      }
    }
  }
}
</script>
