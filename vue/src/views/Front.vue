<template>
  <div>
    <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div>
    <!--头部-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">材料科学文献管理系统</div>
      </div>
      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
            <el-menu-item index="/front/home">首页</el-menu-item>
            <el-submenu index="2">
              <template slot="title">文献搜索</template>
              <el-menu-item index="/front/article">模糊搜索</el-menu-item>
              <el-menu-item @click='openAcc'>精准查询</el-menu-item>
            </el-submenu>
            <el-menu-item index="/front/ProfessorPage">教授查询</el-menu-item>
            <el-menu-item index="/front/score">评论专区</el-menu-item>
            <el-menu-item index="/front/Statics">数据统计</el-menu-item>
          </el-menu>
        </div>
        <el-dialog title="精准搜索" :visible.sync="selectVisible" width="55%" :close-on-click-modal="false"
          destroy-on-close>
          <el-form label-width="100px" style="padding-right: 50px" :model="form">
            <el-form-item prop="name" label="文献名称">
              <el-input v-model="form.name" autocomplete="off" placeholder="请输入文献名称"></el-input>
            </el-form-item>
            <el-form-item prop="author" label="文献作者">
              <el-input v-model="form.author" autocomplete="off" placeholder="请输入作者名称"></el-input>
            </el-form-item>
            <el-form-item prop="journal" label="文献期刊">
              <el-input v-model="form.journal" autocomplete="off" placeholder="请输入期刊名称"></el-input>
            </el-form-item>
          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="selectVisible = false">取 消</el-button>
            <el-button type="primary" @click="searchAcc">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/person')">个人中心</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/scoreOrder')">购买资料</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/ImSingle')">私信</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/UploadScore')">我的上传</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/orders')">购买文献</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navto('/front/Collect')">我的收藏</div>
              </el-dropdown-item>


            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!--主体-->
    <div class="main-body">
      <router-view ref="child" @update:user="updateUser" />
    </div>
  </div>

</template>

<script>
import E from 'wangeditor'
export default {
  name: "FrontLayout",

  data() {
    return {
      editor: null,
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      selectVisible: false,
      form: {},
    }
  },

  mounted() {
    this.loadNotice()
  },
  methods: {
    initWangEditor(content) {
      this.$nextTick(() => {
        this.editor = new E('#editor')
        this.editor.config.placeholder = '请输入内容'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgServer = 'http://localhost:9090/files/wang/upload'
        this.editor.create()
        setTimeout(() => {
          this.editor.txt.html(content)
        })
      })
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    navto(url) {
      location.href = url;
    },
    openAcc(){
      this.selectVisible=true;
    },
    searchAcc(){
       this.$request.get('/article/getAccArticle',{params:{
        name:this.form.name,
        journal:this.form.journal,
        author:this.form.author
       }}).then(res=>{
        console.log(res);
        this.selectVisible=false;
        this.$router.push({path:'/front/ReferenceSearch',query:{articleArray:res}});
       })
    },
  }

}
</script>

<style scoped>
@import "@/assets/css/front.css";
</style>