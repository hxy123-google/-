<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="flex:1 ;width: 50%; margin: 20px auto; min-height: 1000px">
                <div style="text-align: center">
                    <el-button type="success">{{ articleData.type === 'CHINESE' ? '中文文献' : '英文文献' }}</el-button>
                    <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{
                        articleData.name
                        }}</span>
                </div>
                <div style="text-align: center; margin-top: 15px">
                    <span style="color: red" v-if="articleData.price > 0">{{ articleData.price }} 积分</span>
                    <span style="color: red" v-else>免费</span>
                    <span style="color: #509a4d; margin-left: 20px" v-if="articleData.discount < 1">{{
                        articleData.discount
                        * 10 }} 折</span>
                    <span style="color: #666666; margin-left: 50px">发布时间：{{ articleData.time }}</span>
                </div>
                <!--   课程保密区域   -->
                <div>
                    <div style="font-size: 18px; margin: 10px 0">文献资料</div>
                    <div v-if="articleData.price === 0 || flag || user.role === 'PRO'">
                        <div style="margin-top: 10px">文献链接：<a :href="articleData.file" target="_blank">{{
                            articleData.file
                                }}</a></div>
                    </div>
                    <div v-else>
                        <span style="color: red; margin-right: 20px">该文献属于付费文献，购买后可解锁</span>
                        <el-button type="warning" size="mini" @click="buy">购买文献</el-button>
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
                                    <div style="color: #888; font-size: 13px">
                                        <span style="margin-right: 20px">{{ item.time }}</span>
                                        <span style="cursor: pointer;"
                                            :class="{ 'comment-active': item.showReplyInput }"
                                            @click="handleShowReplyInput(item)"><i
                                                class="el-icon-s-comment"></i>评论</span>
                                        <span style="margin-left: 20px; cursor: pointer" @click="del(item.id)"
                                            v-if="item.userId === user.id"><i class="el-icon-delete"></i>删除</span>
                                    </div>
                                    <div v-if="item.showReplyInput">
                                        <el-input type="textarea" placeholder="请输入回复内容"
                                            v-model="item.replyContent"></el-input>
                                        <div style="text-align: right; margin-top: 5px">
                                            <el-button type="primary" @click="addReplay(item)">回 复</el-button>
                                        </div>
                                    </div>
                                </div>

                                <!-- 这是回复 -->
                                <div style="display: flex;  grid-gap: 20px; margin-bottom: 20px"
                                    v-for="sub in item.children" :key="item.id">
                                    <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
                                    <div style="flex: 1">
                                        <div style="color: #666; margin-bottom: 10px">{{ sub.userName }} <span
                                                style="color: #333" v-if="sub.replyUser !== item.userName">回复
                                                {{ sub.replyUser }}</span></div>
                                        <div style="color: #444; margin-bottom: 10px">{{ sub.content }}</div>
                                        <div style="color: #888; font-size: 13px"><span style="margin-right: 20px">{{
                                            sub.time }}</span>
                                            <span style="cursor: pointer;"
                                                :class="{ 'comment-active': sub.showReplyInput }"
                                                @click="handleShowReplyInput(sub)"><i
                                                    class="el-icon-s-comment"></i>评论</span>
                                            <span style="margin-left: 20px; cursor: pointer" @click="del(sub.id)"
                                                v-if="sub.userId === user.id"><i class="el-icon-delete"></i>删除</span>
                                        </div>
                                        <div v-if="sub.showReplyInput">
                                            <el-input type="textarea" placeholder="请输入回复内容"
                                                v-model="sub.replyContent"></el-input>
                                            <div style="text-align: right; margin-top: 5px">
                                                <el-button type="primary" @click="addReplay(sub)">回 复</el-button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--  评论结束  -->
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
                        <span style="color: #666;"> <a :href="'/front/scoreDetail?id=' + item.id">{{ item.name
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
        let articleId = this.$route.query.id
        return {
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            articleId: articleId,
            articleData: {},
            commentCount: 0,
            commentContent: '',
            commentList: [],
            showList: [],
            pageNum: 1,
            pageSize: 5,
            total: 0,
            flag: false,
        }
    },
    mounted() {
        this.loadArticle();
        this.loadComment();
        this.loadScore(1);
        this.checkArticle();

    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
        del(id) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
                this.$request.delete('/comment/delete/' + id).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.loadComment()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        handleShowReplyInput(item) {
            this.$set(item, 'showReplyInput', !item.showReplyInput)
        },
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
            });
        },
        addReplay(item) {
            console.log(item);
            this.$request.post('/comment/add', { pid: item.id, rootId: item.rootId, content: item.replyContent, fid: this.articleId, module: '文献', role: this.user.role, userId: this.user.id }).then(res => {
                if (res.code === '200') {
                    this.$message.success('操作成功')
                    item.replyContent = ''
                    this.loadComment()  // 重新加载数据
                }
            })
        },
        loadComment() {
            this.$request.get('/comment/selectForUser', {
                params: { fid: this.articleId, module: '文献' }
            }).then(res => {
                //console.log(res);
                this.commentList = res.data || []
            });
            this.$request.get('/comment/selectCount', {
                params: { fid: this.articleId, module: '文献' }
            }).then(res => {
                // console.log(res);
                this.commentCount = res.data || 0;

            })
        },
        addComment() {
            alert(this.user.role);
            this.$request.post('/comment/add', { content: this.commentContent, userId: this.user.id, fid: this.articleId, module: '文献', role: this.user.role }).then(res => {
                if (res.code === '200') {
                    this.$message.success('操作成功')
                    this.commentContent = ''
                    this.loadComment()  // 重新加载数据
                }
            })
        },
        loadScore(pageNum) {
            this.pageNum=pageNum;
            this.$request.get('/articlescore/selectScore/', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    articleId: this.articleId
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
        checkArticle() {
            this.$request.get('/orders/selectAll', {
                params: {
                    userId: this.user.id,
                    articleId: this.articleId
                }
            }).then(res => {
                if (res.code === '200') {
                    if (res.data.length > 0) {
                        this.flag = true
                    }
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        buy() {
            let data = {
                articleId: this.articleId,
                userId: this.user.id
            }
            this.$request.post('/orders/add', data).then(res => {
                if (res.code === '200') {
                    this.$message.success('购买成功，已解锁课程')
                    this.loadArticle()
                    this.checkArticle()
                } else {
                    this.$message.error(res.msg)
                }
            })
        }

    }
}
</script>