/* Load required modules: path and html-webpack-plugin */
var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var ROOT_PATH     = path.resolve(__dirname);
var ENTRY_PATH    = path.resolve(ROOT_PATH, 'app/js/index.js');
//var SRC_PATH      = path.resolve(ROOT_PATH, 'app');
var JS_PATH       = path.resolve(ROOT_PATH, 'app/js');
var TEMPLATE_PATH = path.resolve(ROOT_PATH, 'app/index.html');
// var SHADER_PATH   = path.resolve(ROOT_PATH, 'src/shaders');
var BUILD_PATH    = path.resolve(ROOT_PATH, 'dist');

// set debug to true if we are not in production environment
var debug = process.env.NODE_ENV !== 'production';

module.exports = {
  entry: ENTRY_PATH,

  output: {
    path: BUILD_PATH,
    filename: 'bundle.js'
  },
  plugins: [
    /*
    HTML webpack plugin generates <script> and <link> tags
    and merges them into our starter template HTML
    */
    new HtmlWebpackPlugin({
      title: 'WebGL Three.js Starter', // Goes under <title> of the generated HTML
      template: TEMPLATE_PATH,           // Template HTML to use
      inject: 'body'                     // Inject the <script> tags in <body>
    })
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        // include: JS_PATH,
        exclude: /(node_module|bower_components)/,
        use: 'babel-loader',
        // query: {
        //   cacheDirectory: true
        // }
      },
      // {
      //   test: /\.glsl$/,
      //   include: SHADER_PATH,
      //   loader: 'webpack-glsl-loader'
      // }
    ]
  },
  //devtool: 'source-map'
};
