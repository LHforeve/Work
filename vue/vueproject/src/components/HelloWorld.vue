<template>
  <div class="home" style="padding:50px;">
    <el-table
      :data="tableData"
      stripe
      border:true;
      style="width:100%;margin-bottom:10px;">
      <el-table-column prop="id" label="编号" width="180" sortable />
      <el-table-colum prop="mediname" label="药品名称" width="180" />
      <el-table-colum prop="shuxing" label="药品类别" />
      <el-table-colum prop="username" label="用户姓名" />
      <el-table-colum prop="address" laberl="家庭地址" width="150" />

       <el-table-colum fixed="right" label="操作" width="150">
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
      <template>
        <div>
          <button @click="refundOrder" :disabled="buttonDisabled">退单</button>
        </div>
      </template>
      </template>
     </el-table-colum>

    </el-table>
    <el-pagination
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

    <el-dialog v-model="dialogVisible" title="药品信息" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="药品名">
          <el-input v-model="form.Mediname" />
        </el-form-item>
        <el-form-item label="类别">
          <el-radio v-model="form.leibie" label="otc" size="large">OTC</el-radio>
          <el-radio v-model="form.leibie" label="non-otc" size="large">非OTC</el-radio>
        </el-form-item>
        <el-form-item label="家庭住址">
          <el-input type="textarea" v-model="form.address" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span  class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { onMounted,reactive,toRefs } from "vue";
import axios from "axios"

export default {
  name: "HelloWorld",
  components: {},
  setup() {
    const state = reactive({
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
      search: "",
      form: {},
      dialogVisible: false,
    })

    const buttonDisabled = ref(false);

    const refundOrder = async () => {
      try {
        buttonDisabled.value = true;

        const response = await fetch(`/refund_order/${orderId}`, {
          method: 'POST'
        });

        if (response.ok) {
          alert('订单已退单');
        } else {
          buttonDisabled.value = false;
          alert('订单无法退单');
        }
      } catch (error) {
        buttonDisabled.value = false;
        console.error('Error:', error);
      }
    }
    const load = () => {
      axios
          .get("/api/medis", {
            params: {
              pageNum: state.currentPage,
              pageSize: state.pageSize,
              // total: state.total,
              search: state.search,
            },
          })
          .then((res) => {
            console.log(res);
            state.tableData = res.data.data.records
            state.total = res.data.data.total
          })
    }

    onMounted(() => {
      load()
    })

    const handleCurrentChange = (pageNum) => {
      state.currentPage = pageNum;
      load();
    }
    const handleSizeChange = (pageSize) => {
      state.pageSize = pageSize;
      load()
    }

    const add = () => {
      state.dialogVisible = true;
      state.form = {}
    }

    const save = () => {
      if (state.form.id) {
        axios.put("/api/medis", state.form).then((res) => {
          if (res.data.code === "0") {
            ElMessage({
              message: "修改成功！",
              type: "success",
            })
          } else {
            ElMessage.error(res.msg)
          }
          load()
          state.dialogVisible = false
        });
      } else {
        axios.post("/api/medis", state.form).then((res) => {
          if (res.data.code === "0") {
            ElMessage({
              message: "添加成功！",
              type: "success",
            })
          } else {
            ElMessage.error(res.msg)
          }
          load()
          state.dialogVisible = false
        });
      }
    }
    const handleEdit = (row) => {
      state.form = JSON.parse(JSON.stringify(row))
      state.dialogVisible = true
    }
    const handleDelete = (id) => {
      axios.delete("/api/medis/" + id).then((res) => {
        if (res.data.code === "0") {
          ElMessage({
            message: "删除成功！",
            type: "success",
          })
        } else {
          ElMessage.error(res.msg);
        }
        load();
        state.dialogVisible = false;
      })
    }

    return {
      ...toRefs(state),
      handleCurrentChange,
      handleSizeChange,
      add,
      save,
      handleEdit,
      handleDelete,
      load,
      refundOrder,
      buttonDisabled
    }
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
