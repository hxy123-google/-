<template>
  <div class="main-content">
    <el-card style="width: 50%; margin: 30px auto">
      <div style="text-align: right; margin-bottom: 20px">
        <el-button type="primary" @click="updatePassword">修改密码</el-button>
        <el-button type="warning" @click="initRecharge">充值</el-button>
        <el-button type="warning" @click="initIntroduce">编辑个人介绍</el-button>
      </div>
      <el-form :model="user" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload class="avatar-uploader" :action="$baseUrl + '/files/upload'" :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" placeholder="用户名" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="会员" prop="member">
          <el-input v-model="user.member" placeholder="会员" disabled></el-input>
        </el-form-item>
        <el-form-item label="积分" prop="score ">
          <el-input v-model="user.score" placeholder="积分" disabled></el-input>
        </el-form-item>
        <el-form-item label="余额" prop="account ">
          <el-input v-model="user.account" placeholder="余额 " disabled></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>
      </el-form>
    </el-card>
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="user" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password" placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="个人充值" :visible.sync="rechargeVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="80px" style="padding-right: 20px">
        <el-form-item label="充值说明">
          <span style="color: red">充值一次性满50，可成为会员</span>
        </el-form-item>
        <el-form-item label="充值金额">
          <el-input v-model="account" placeholder="请输入充值金额"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-radio v-model="type" label="支付宝">支付宝</el-radio>
          <el-radio v-model="type" label="微信">微信</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="rechargeVisible = false">取 消</el-button>
        <el-button type="primary" @click="recharge">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="编辑个人介绍" :visible.sync="editVisible" width="30%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="user" label-width="80px" style="padding-right: 20px" >
        <el-form-item prop="introduce" v-model="user.introduce" label="资料介绍">
            <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateIntroduce">确 定</el-button>
      </div>
    </el-dialog>
  
  </div>
</template>

<script>
 import E from 'wangeditor'
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('确认密码错误'))
      } else {
        callback()
      }
    }
    return {
      editor: null,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dialogVisible: false,
      rechargeVisible: false,
      editVisible:false,
      account: null,
      type: '支付宝',
      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    console.log(this.user);
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
    update() {
      //this.user.introduce = this.editor.txt.html();
      console.log(this.user);
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update',
        this.user
      ).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    updateIntroduce() {
      this.user.introduce = this.editor.txt.html();
      console.log(this.user);
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update',
        this.user
      ).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    initRecharge() {
      this.account = 100
      this.rechargeVisible = true
    },
    initIntroduce(){
      this.editVisible=true;
      this.initWangEditor('');
    },
    
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          
          this.$request.put(
            '/updatePassword', this.user).then(res => {
              if (res.code === '200') {
                // 成功更新
                this.$message.success('修改密码成功')
                this.$router.push('/login')
              } else {
                this.$message.error(res.msg)
              }
            })
        }
      })
    },
    recharge() {
      this.$request.get('/user/recharge?account=' + this.account).then(res => {
        if (res.code === '200') {
          this.$message.success('充值成功')
          this.loadPerson()
          this.rechargeVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadPerson() {
      this.$request.get('/user/selectById/' + this.user.id).then(res => {
        if (res.code === '200') {
          this.user = res.data
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}

/deep/.el-upload {
  border-radius: 50%;
}

/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}

/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>