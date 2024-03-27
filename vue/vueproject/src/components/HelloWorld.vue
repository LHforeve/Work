<template>
  <div class="home" style="padding:50px;">
    <el-table
      :data="tableData"
      stripe
      border:true;
      style="width:100%;margin-bottom:10px;">
      <el-table-column prop="id" label="编号" width="180" sortable />
      <el-table-colum prop="mediname" label="药品名称" width="180" />
      <el-table-colum prop="shuxing" label="药品类别" width="150">
    <template #default="scope">
          <el-button type="primary" size="small"
   @click="handleEdit(scope.row)">编辑</el-button>
   <el-popconfirm 
     title="你确定要删除吗？"
     @confirm="handleDelete(scope.row.id)">
     <template #reference>
        <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
     </el-table-colum>
    </el-table>
  </div>         
</template>

<script>
export default {
  name: 'HelloWorld',
  components:{
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
