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
          <el-button type="primary">中文文献</el-button>
          <el-button type="success">英文文献</el-button>
          <!-- <el-button type="warning"></el-button> -->
        </div>
        <div style="width: 300px">
          <el-button type="info">签到</el-button>
          <span style="margin-left: 30px; color: #12b127; font-weight: 550">上次签到：</span>
          <span style="color: #666666">2024-01-02 08:52:49</span> 
        </div>
      </div>
      <div style="display:flex;margin-top: 20px;height:300px">
        <div style="flex:1;margin-right:10px;height:300px">
         <img :src="recommend.img" alt="" style="width:100%;height:270px; border-radius:10px">
         <div style="font-size: 15px; margin-top: 5px" class="overflowShow">{{ recommend.name }}</div>
        </div>
        <div style="flex:2;margin-left:10px;height:500px">
          <el-row :gutter="20">
          <el-col :span="6" style="margin-bottom: 35px" v-for="item in rightData" :key="item">
          <img :src="item.img" alt="" style="width: 100%; height: 100px; border-radius: 5px">
          <div style="color: #333333; margin-top: 10px" class="overflowShow">{{ item.name }}</div>
          </el-col>
         </el-row>
      
        </div> 
      </div>
      <div style="margin-top: 50px">
    <span style="font-size: 24px; color: #333333; margin-right: 50px">学术大佬</span>
  </div>
  <div style="display: flex; margin-top: 20px; height: 300px">
    <div style="flex: 2; margin-right: 10px">
      <el-row :gutter="20">
        <el-col :span="6" style="margin-bottom: 35px">
          <img src="@/assets/imgs/2.png" alt="" style="width: 100%; height: 100px; border-radius: 5px">
          <div style="color: #333333; margin-top: 10px" class="overflowShow">电商+推荐算法,基于Springboot+Vue+协同过滤推荐算法的网上购物商城系统</div>
        </el-col>
      </el-row>
    </div>
    <div style="flex: 1; margin-left: 10px; width: 0">
      <img src="@/assets/imgs/2.png" alt="" style="width: 100%; height: 270px; border-radius: 5px">
      <div style="font-size: 15px; margin-top: 5px" class="overflowShow">电商+推荐算法，基于Springboot+Vue+协同过滤推荐算法的网上购物商城系统</div>
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
    recommend: {},
    rightData: [],
    }
  },
  mounted() {
  this.loadRecommend()
  this.loadRightData()
},

 methods:{
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
loadRightData() {
  this.$request.get('/article/selectTop8').then(res => {
    if (res.code === '200') {
      this.rightData = res.data
    } else {
      this.$message.error(res.msg)
    }
  })
}
 }
}
</script>
