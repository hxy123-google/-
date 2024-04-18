<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入账号查询" style="width: 200px" v-model="username"></el-input>
            <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="info" plain style="margin-left: 10px" @click="loadPro(1)">查询教授</el-button>
            <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
        </div>
        <div class="table">
            <el-table :data="tableData" strip @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="序号" width="70" align="center" sortable >
                    <template v-slot="scope">
                        <a :href="'/front/UserDetail?id=' + scope.row.id">{{ scope.row.id }}</a>
                    </template>
                </el-table-column>
                <el-table-column label="头像">
                    <template v-slot="scope">
                        <div style="display: flex; align-items: center">
                            <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                                :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="username" label="账号">
                    <template v-slot="scope">
                        <a :href="'/front/Professor?id=' + scope.row.id">{{ scope.row.username }}</a>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="phone" label="电话"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>

            </el-table>

            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
                    :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "User",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            username: null,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ]
            },
            ids: []
        }
    },
    created() {
        this.load(1);
    },
    methods: {
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/user/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    role: 'PRO'
                }
            }).then(res => {
                console.log(this.tableData);
                this.tableData = res.data?.list
                this.total = res.data?.total
            })
        },
        loadPro(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/user/selectPro').then(res => {
                //console.log(res);
                this.tableData = res.data?.list
                this.total = res.data?.total
            })
        },
        reset() {
            this.username = null
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        handleAvatarSuccess(response, file, fileList) {
            // 把头像属性换成上传的图片的链接
            this.form.avatar = response.data
        },
    }
}
</script>

<style scoped></style>