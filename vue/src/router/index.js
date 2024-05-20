import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'article', name: 'Article', meta: { name: '文献信息' }, component: () => import('../views/manager/Article') },
      //{ path: 'professor', name: 'Professor', meta: { name: '教授信息' }, component: () => import('../views/manager/Professor') },
      { path: 'score', name: 'Score', meta: { name: '积分专区' }, component: () => import('../views/manager/Score') },
      { path: 'comment', name: 'Comment', meta: { name: '评论信息' }, component: () => import('../views/manager/Comment') },
      { path: 'scoreOrder', name: 'ScoreOrder', meta: { name: '积分兑课' }, component: () => import('../views/manager/ScoreOrder') },
      { path: 'information', name: 'Information', meta: { name: '审核区域' }, component: () => import('../views/manager/Information') },
      { path: 'orders', name: 'Orders', meta: { name: '课程订单' }, component: () => import('../views/manager/Orders') },
      { path: 'imSingle', name:'ImSingle', meta:{name :'私信教授'} ,component:()=>import('../views/manager/ImSingle.vue')},
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      { path: 'article', name: 'Article', meta: { name: '文献搜索' }, component: () => import('../views/front/Article') },
      { path: 'score', name: 'Score', meta: { name: '积分专区' }, component: () => import('../views/front/Score') },
      { path: 'articleDetail', name: 'ArticleDetail', meta: { name: '文献详情' }, component: () => import('../views/front/ArticleDetail') },
      { path: 'scoreDetail', name: 'ScoreDetail', meta: { name: '积分详情' }, component: () => import('../views/front/ScoreDetail') },
      { path: 'scoreOrder', name: 'ScoreOrder', meta: { name: '积分详情' }, component: () => import('../views/front/ScoreOrder') },
      { path: 'imSingle', name:'ImSingle', meta:{name :'私信教授'} ,component:()=>import('../views/front/ImSingle.vue')},
      { path: 'userDetail', name:'UserDetail', meta:{name :'用户详情'} ,component:()=>import('../views/front/UserDetail.vue')},
      { path: 'referenceSearch', name:'ReferenceSearch', meta:{name :'引用查询'} , component:()=>import('../views/front/ReferenceSearch.vue')},
      { path: 'uploadScore', name:'UploadScore', meta:{name :'上传积分'} ,component:()=>import('../views/front/UploadScore.vue')},
      { path: 'orders', name: 'Orders', meta: { name: '已购课程' }, component: () => import('../views/front/Orders') },
      { path: 'statics', name: 'STatics', meta: { name: '数据统计' }, component: () => import('../views/front/Statics') },
      { path: 'professor', name: 'Professor', meta: { name: '教授信息' }, component: () => import('../views/front/Professor') },
      { path: 'refarticle', name: 'refarticle', meta: { name: '文献' }, component: () => import('../views/front/refarticle') },
      { path: 'Collect', name: 'Collect', meta: { name: '我的收藏' }, component: () => import('../views/front/Collect.vue') },
      { path: 'test', name: 'test', meta: { name: '教授' }, component: () => import('../views/front/test.vue') },
      { path: 'professorPage', name: 'ProfessorPage', meta: { name: '教授查询' }, component: () => import('../views/front/ProfessorPage.vue') },
      { path: 'ruser', name: 'ruser', meta: { name: '个人查询详细文章' }, component: () => import('../views/front/ruser.vue') },
      { path: 'Mystatics', name: 'Mystatics', meta: { name: '个人统计' }, component: () => import('../views/front/Mystatics.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
router.beforeEach((to ,from, next) => {
  let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
  if (to.path === '/') {
    if (user.role) {
      if (user.role === 'USER'||user.rolr==='PRO') {
        next('/front/home')
      } else {
        next('/home')
      }
    } else {
      next('/login')
    }
  } else {
    next()
  }
})

export default router
