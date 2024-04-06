<template>
    <div class="main-content">
        <div style="display: flex; grid-gap:10px">
            <div style="width:150px" class="card">
                <!-- <div class="category-item category-item-active">全部</div> -->
                <!-- <div class="category-item"  v-for="item in categoryList" :key="item.id">{{ item.category }}</div> -->
                <div class="category-item" :class="{'category-item-active': item.category===current}" 
                v-for="item in categoryList" :key="item.id"  @click="selectCategory(item.category)">{{ item.category }}</div>
            </div>
            <div style="flex:1" class="card"></div>
            <div  style="width:260px" class="card"></div>
        </div>

    </div>
    
  
  
</template>
  
  <script>
  
  export default {
  
    data() {
      return {
        current:'全部文献',
        categoryList:[]
      }
    },
    mounted() {
    this.load()

  },
  
   methods:{
    load(){
        this.$request.get("/category").then(res=>{
            console.log(res);
            this.categoryList=res.data;
            this.categoryList.unshift({category:'全部文献'})
        })
    },
    selectCategory(categoryName){
        this.current=categoryName;
    }
   }
  }
  </script>
<style>
.category-item{
    text-align:center;
    padding:10px 0;
    font-size:16px;
    cursor:pointer;
}
.category-item-active{
    background-color:#1890ff;
    color:#fff;
    border-radius:5px;
}
</style>
  