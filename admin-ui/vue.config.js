module.exports = {
  devServer: {
    port: 3002,
    proxy: {
      '/auth': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/auth': '' // remove path remove '/api'
        }
      },
      '/message-api': {
        target: 'http://localhost:8084',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/message-api': '' // remove path remove '/api'
        }
      },
       '/resource-api': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/resource-api': '' // remove path remove '/api'
        }
      },
 
    }
  }
}