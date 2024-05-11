<template>
    <div class="main-content">
      <div style="width: 70%; margin: 30px auto; min-height: 1000px">
        <div style="text-align: center">
          <el-button type="success">{{ userData.role === 'USER'? '用户' : '教授' }}</el-button>
          <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{ userData.name }}</span>
        </div>

        <!--   课程保密区域   -->
        <div>
          <div style="font-size: 18px; margin: 10px 0">头像</div>
          <div >
            <img :src="userData.avatar" controls style="width: 65%; height: 400px">
            <!-- <div style="margin-top: 10px">头像：<a :href="courseData.file" target="_blank">{{ userData.avatar }}</a></div> -->
          </div>
          <div style="font-size: 18px; margin: 10px 0">邮箱</div>
          <div>{{ userData.email }}</div>
          <div style="font-size: 18px; margin: 10px 0">电话</div>
          <div>{{ userData.phone }}</div>
        </div>
        <!--   课程介绍区域   -->
        <div style="margin-top: 20px">
          <div style="font-size: 18px">个人介绍</div>
          <div style="margin-top: 10px" v-html="userData.introduce" class="w-e-text w-e-text-container"></div>
        </div>
      </div>
    </div>
  </template>
  <script>
  import E from 'wangeditor'
  export default {
  
    data() {
      let userId = this.$route.query.id
      return {
        user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
        userId: userId,
        userData: {},
        flag:false
      }
    },
    mounted() {
      this.loadCourse();
    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
      loadCourse() {
        console.log("hi");
        this.$request.get('/user/selectById/' + this.userId).then(res => {
          if (res.code === '200') {
            this.userData = res.data;
            console.log(this.userData);
          } else {
            this.$message.error(res.msg)
          }
        })
      },
    }
  }
  </script>
  