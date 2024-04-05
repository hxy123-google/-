<template>
  <div class="main-content">
     <div style="display: flex">
      <div style="flex: 12%; height: 350px; background-color: #0c0c0c"></div>
      <div style="flex: 76%">
      <el-carousel height="350px">
      <el-carousel-item  v-for="item in carouselData" :key="item">
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
      <el-button type="warning">物理方向</el-button>
    </div>
    <div style="width: 300px">
      <el-button type="info">签到</el-button>
      <span style="margin-left: 30px; color: #12b127; font-weight: 550">上次签到：</span>
      <span style="color: #666666">2024-01-02 08:52:49</span>
    </div>
  </div>
  
  </div>
</div>

  
</template>

<script>

export default {

  data() {
    return {
      carouselData:[
        require('@/assets/imgs/lun1.jpg'),
        require('@/assets/imgs/lun2.jpg'),
        require('@/assets/imgs/lun3.jpg'),
        require('@/assets/imgs/lun4.jpg'),
      ]
    }
  }
   
}
</script>
