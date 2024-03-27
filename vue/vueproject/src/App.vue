<template>
  <div id="nav">
    <el-container>
      <el-header>
        <Header/>
      </el-header>
      <el-container>
        <el-aside width="200px" >
          <Aside />
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>     
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Medi-Header"
import Aside from "@/components/Medi-Aside"

export default {
  name: 'App',
  components: {
    Aside,
    // eslint-disable-next-line vue/no-unused-components
    Header
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
