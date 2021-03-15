module.exports = {
  devServer: {
    port: 3001,
    proxy: {
      '/ws': {
        target: 'http://localhost:8084',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/ws': '/ws' // remove path remove '/api'
        }
      },
      '/api': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '' // remove path remove '/api'
        }
      },
      '/core-api': {
        target: 'http://localhost:8000',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/core-api': '' // remove path remove '/api'
        }
      },
      '/resource-api': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/resource-api': '' // remove path remove '/api'
        }
      }

    }
  }
}