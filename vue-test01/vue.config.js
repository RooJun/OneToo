const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,////关闭语法检查;Component name “School” should always be multi-word”的解决方法
  //解决表单提交跨域问题 action中用api开头+要访问的后端路径
  // proxyTable: {
  //   '/api': {
  //     //以下接口胡编，项目中需要使用自己的
  //     target: 'http://localhost:8081',  //这里配置的是 请求接口的域名
  //     // secure: false, // 如果是https接口，需要配置这个参数
  //     changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
  //     pathRewrite: {
  //       '^/api': '' //路径重写，这里理解成用'/api'代替target里面的地址。
  //     }
  //   },
  // },

  //解决跨域问题，axios的默认路径已经设置为'/api' ，见main.js
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: {
          '/api': ''
        }
      }
    }
  }

})
