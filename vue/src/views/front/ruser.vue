<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="flex:1 ;width: 50%; margin: 20px auto; min-height: 1000px">
                <div style="text-align: center; margin-top: 15px">
                    <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">文献名称：{{ courseData.articlename }}</span>
                </div>
                <div style="text-align: center; margin-top: 15px">
                    <span style="color: #666666; margin-left: 50px">文献作者：{{ courseData.author }}</span>
                </div>
                <div style="text-align: center">
                    <el-button type="success">{{ courseData.type === 'ENGLISH' ? '英文' : '中文' }}</el-button>
                    <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{
                        courseData.name
                    }}</span>
                </div>
                <div style="text-align: center; margin-top: 15px">
                    <span style="color: #666666; margin-left: 50px">发布时间：{{ courseData.time }}</span>
                </div>
                <!--   课程保密区域   -->
                <div>
                    <div style="font-size: 18px; margin: 10px 0">资料</div>
                    <div style="margin-top: 10px">文献链接：<a :href="courseData.file" target="_blank">{{ courseData.file
                            }}</a>
                    </div>
                </div>
                <!--   课程介绍区域   -->
                <div style="margin-top: 20px">
                    <div style="font-size: 18px">文献笔记</div>
                    <el-button type="info" plain style="margin-left: 10px" size="mini"
                        @click="note(courseData.descr)">增加笔记</el-button>
                    <div style="margin-top: 10px" v-html="courseData.descr" class="w-e-text w-e-text-container"></div>
                </div>
            </div>
            <el-dialog title="添加笔记" :visible.sync="fromVisible" width="55%" :close-on-click-modal="false"
                destroy-on-close>
                <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                    <el-form-item prop="descr" label="文献笔记">
                        <div id="editor"></div>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="fromVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addDescr">确 定</el-button>
                </div>
            </el-dialog>
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
                        <span style="color: #666;"> <a :href="'/front/ScoreDetail?id=' + item.id">{{ item.name
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
            showList: [],
            pageNum: 1,
            pageSize: 5,
            total: 0,
            fromVisible: null,
            descr: null,
            flag: false,
            editor: null,
            form: {},
        }
    },
    created(){
       this.loadCourse();
    },
    mounted() {
        //this.loadCourse();
        this.loadScore(1);
    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
        initWangEditor(descr) {
            this.$nextTick(() => {
                this.editor = new E('#editor')
                this.editor.config.placeholder = '请输入内容'
                this.editor.config.uploadFileName = 'file'
                this.editor.config.uploadImgServer = 'http://localhost:9090/files/wang/upload'
                this.editor.create()
                setTimeout(() => {
                    this.editor.txt.html(descr)
                })
            })
        },
        loadCourse() {
            this.$request.get('/Userarticle/selectById/' + this.scoreId).then(res => {
                if (res.code === '200') {
                    this.courseData = res.data;
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        addDescr() {
            this.form.descr = this.editor.txt.html();
            this.$request({
                url:'/Userarticle/edit/', 
                method:'POST',
                data: {
                    id: this.scoreId,
                    descr: this.form.descr
                }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('修改成功')

                } else {
                    this.$message.error(res.msg)
                }
                this.loadCourse();
            })

        },
        note(descr) {
            this.form.descr = JSON.parse(JSON.stringify(descr))
            this.fromVisible = true;
            this.initWangEditor(this.form.descr || '')
        },
        loadScore(pageNum) {
            let ffff;
            this.$request.get('/Userarticle/selectById/' + this.scoreId).then(res => {
                if (res.code === '200') {
                    ffff = res.data.articleId;
                    console.log(ffff);
                    this.pageNum=pageNum;
                    this.$request.get('/articlescore/selectScore/', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    articleId: ffff
                }
            }).then(res => {
                if (res.code === '200') {
                    console.log(ffff);
                    this.showList = res.data?.list
                    this.total = res.data?.total
                }
            })
                } else {
                    this.$message.error(res.msg)
                }
            })
            //console.log(this.courseData.articleId);
        },
    }
}
</script>
<style scoped></style>