<template>
    <div>
      <div style="display: flex; margin: 10px 0">
        <!-- <div style="width: 50%; height: 400px" class="card" id="userPie"></div> -->
        <div style="width: 50%; height: 400px" class="card" id="bar"></div>
      </div>
    </div>
</template>
<script>
  import * as echarts from "echarts";
  let barOptions = {
    title: {
      text: '', // 主标题
      subtext: '', // 副标题
      left: 'center'
    },
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] // 示例数据：统计的维度（横坐标）
    },
    yAxis: {
      type: 'value',
      minInterval: 1 

    },
    tooltip: {
      trigger: 'item'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
        type: 'bar',
        itemStyle: {
          normal: {
            color:function(){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
          },
        },
      }
    ]
  }
  export default {
    name: 'Mystatics',
    data() {
      return {
        user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
        notices: []
      }
    },
    created() {
      this.loadBar();
    },
    methods: {
      loadBar() {
      this.$request.get('/Userarticle/getBar/',
        {
            params:{
                cId:this.user.id
            }
        }
      ).then(res => {
      if (res.code === '200') {
        let chartDom = document.getElementById('bar');
        let myChart = echarts.init(chartDom);
        barOptions.title.text = res.data.text
        barOptions.title.subtext = res.data.subText
        barOptions.xAxis.data = res.data.xAxis
        barOptions.series[0].data = res.data.yAxis
        myChart.setOption(barOptions);
      } else {
        this.$message.error(res.msg)
      }
    })
  },
  
    }
  
  }
</script>
<style>
</style>