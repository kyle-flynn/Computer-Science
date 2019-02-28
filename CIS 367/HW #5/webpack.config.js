var BrowserSyncPlugin = require('browser-sync-webpack-plugin');
module.exports = {
  entry: './index.js',
  output: {
    filename: './bundle.js'
  },
  mode: process.env.NODE_ENV || 'development',
  watch: true,
  module: {
    rules: [
      {
        test: /\.glsl$/,
        use: [
          {
            loader: 'webpack-glsl-loader'
          }
        ]
      }
    ]
  },
  plugins: [
    (e = new BrowserSyncPlugin({
      host: 'localhost',
      port: 3000,
      files: ['./*.js', './*.html', './*.glsl'],
      server: {
        baseDir: ['.']
      }
    }))
  ]
};
