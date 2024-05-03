<template>
        <div style="display: flex; height: 50px; border-bottom: 1px solid #ccc; background-color: royalblue;">
            <div style="width: 200px;color:white;font-weight: bold;padding-left: 10px; font-size: 13px;">
                    <img src="../assets/hn.jpg" alt="Vue logo" align="center" style="padding-right:5px">
                    医药管理系统</div>
            <div style="flex:1"></div>
            <div style="width:100px;padding-top: 20px;">
                <el-dropdown>
                    <span class="el-dropdown-link">
                        <span style="color:white">个人中心</span>
                        <el-icon class="el-icon--right">
                            <arrow-down />
                        </el-icon>    
                    </span>
                    <template #dropdown> 
                        <el-dropdown-menu>
                            <el-dropdown-item>个人信息</el-dropdown-item>
                            <el-dropdown-tiem>退出系统</el-dropdown-tiem>    
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
</template>

<script>
    export default {
        // eslint-disable-next-line  vue/multi-word-component-names
        name:"Medi-Header"
    }
</script>

<style lang="scss" scoped>

</style>