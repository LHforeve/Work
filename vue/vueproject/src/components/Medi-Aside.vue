<template>
    <el-menu 
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
    <el-menu-item index="1">
        <el-icon><location /></el-icon>
        <span>学生管理</span>
    </el-menu-item>
    <el-menu-item index="2">
        <el-icon><Setting /></el-icon>
        <span>系统管理</span>
    </el-menu-item>     
    </el-menu>
</template>

<script>
import { Location,Setting } from "@element-plus/icons";
    export default {
        // eslint-disable-next-line vue/multi-word-component-names
        components:(
            Location,
            Setting
        ),
     // eslint-disable-next-line vue/multi-word-component-names
     names:"Medi-Aside"   
    }
</script>

<style lang="scss" scoped>

</style>