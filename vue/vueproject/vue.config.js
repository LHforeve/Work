module.exports = {
  publicPath:'/',
  devServer:{
    Proxy:{
      '/api':{
        target:'http://localhost:8080',
        pathRewrite:{'^/api':''},
        ws:true,
        changOrigin:true
      }
    }
  }
}
