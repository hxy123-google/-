<template>
    <div class="main-content">
        <div style="width: 70%; margin: 30px auto; min-height: 1000px">
            <div style="text-align: center">
                <el-button type="success">{{ articleData.type === 'CHINESE' ? '中文文献' : '英文文献' }}</el-button>
                <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{ articleData.name
                    }}</span>
            </div>
            <div style="text-align: center; margin-top: 15px">
                <span style="color: red" v-if="articleData.price > 0">￥ {{ articleData.price }} / 元</span>
                <span style="color: red" v-else>免费</span>
                <span style="color: #509a4d; margin-left: 20px" v-if="articleData.discount < 1">{{ articleData.discount
                    * 10 }} 折</span>
                <span style="color: #666666; margin-left: 50px">发布时间：{{ articleData.time }}</span>
            </div>
            <!--   课程保密区域   -->
            <div>
                <div style="font-size: 18px; margin: 10px 0">文献资料</div>
                <div v-if="articleData.price === 0">
                    <div style="margin-top: 10px">文献链接：<a :href="articleData.file" target="_blank">{{ articleData.file
                            }}</a></div>
                </div>
                <div v-else>
                    <span style="color: red; margin-right: 20px">该文献属于付费文献，购买后可解锁</span>
                    <el-button type="warning" size="mini">购买文献</el-button>
                </div>
            </div>
            <!--   课程介绍区域   -->
            <div style="margin-top: 20px">
                <div style="font-size: 18px">文献介绍</div>
                <div style="margin-top: 10px" v-html="articleData.content"></div>
            </div>


            <!--  评论开始  -->
            <div class="card">
                <h2 style="margin-top: 90px">评论 {{ commentCount }}</h2>

                <div style="margin-bottom: 20px">
                    <el-input type="textarea" placeholder="请输入评论内容" v-model="commentContent"></el-input>
                    <div style="text-align: right; margin-top: 5px">
                        <el-button type="primary" @click="addComment">评 论</el-button>
                    </div>
                </div>

                <div>
                    <div style="display: flex; grid-gap: 20px; margin-bottom: 20px" v-for="item in commentList"
                        :key="item.id">
                        <img :src="item.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
                        <div style="flex: 1">
                            <!-- 这是第一级评论 -->
                            <div style="margin-bottom: 10px">
                                <div style="color: #666; margin-bottom: 10px">{{ item.userName }}</div>
                                <div style="color: #444; margin-bottom: 10px">{{ item.content }}</div>
                                <div style="color: #888; font-size: 13px"><span style="margin-right: 20px">{{ item.time
                                        }}</span>
                                    <span style="cursor: pointer"><i class="el-icon-s-comment"></i>评论</span>
                                </div>
                            </div>
                            <!-- 这是回复 -->
                             <div style="display: flex;  grid-gap: 20px; margin-bottom: 20px" v-for="sub in item.children" :key="item.id">
                                <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
                                <div style="flex: 1">
                                    <div style="color: #666; margin-bottom: 10px">{{ sub.userName }} <span
                                            style="color: #333" v-if="sub.replyUser !== item.userName">回复 {{
                                                sub.replyUser }}</span></div>
                                    <div style="color: #444; margin-bottom: 10px">{{ sub.content }}</div>
                                    <div style="color: #888; font-size: 13px"><span style="margin-right: 20px">{{
                                        sub.time }}</span>
                                        <span style="cursor: pointer"><i class="el-icon-s-comment"></i>评论</span>
                                    </div>
                                </div>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
            <!--  评论结束  -->
        </div>
    </div>
</template>
<script>
import E from 'wangeditor'
export default {

    data() {
        let articleId = this.$route.query.id
        return {
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            articleId: articleId,
            articleData: {},
            commentCount: 0,
            commentContent: '',
            commentList: [],
            
        }
    },
    mounted() {
        this.loadArticle();

        this.loadComment();
  
    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
        loadArticle() {
            this.$request.get('/article/selectById/' + this.articleId).then(res => {
                if (res.code === '200') {
                    console.log(res.data);
                    var dateTimeString = res.data.time;
                    var dateTime = new Date(dateTimeString);
                    var year = dateTime.getFullYear();
                    var month = dateTime.getMonth() + 1; // 注意：月份从 0 开始，所以要加 1
                    var day = dateTime.getDate();
                    res.data.time = year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
                    this.articleData = res.data
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        loadComment() {
            this.$request.get('/comment/selectForUser', {
                params: { fid: this.articleId, module: '文献' }
            }).then(res => {
                console.log(res);
                this.commentList = res.data || []
            })
        },
        addComment() {
            alert(this.user.role);
            this.$request.post('/comment/add', { content: this.commentContent, userId:this.user.id,fid: this.articleId, module: '文献' ,role:this.user.role}).then(res => {
                if (res.code === '200') {
                    this.$message.success('操作成功')
                    this.commentContent = ''
                    this.loadComment()  // 重新加载数据
                }
            })
        },
    }
}
</script>