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
            articleData: {}
        }
    },
    mounted() {
        this.loadArticle()
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
        }
    }
}
</script>