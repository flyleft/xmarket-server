var webpack = require('webpack');
var path = require('path');

module.exports = {
  //入口文件配置
  entry: {
    index: './src/app.js',
  },
  //出口文件配置
  output: {
    path: path.join(__dirname, './dist/js/'),
    filename: '[name].js',
  },

  module: {
    loaders: [
      {
        test: /\.css$/,
        loader: 'style!css'
      },
      {
        test: /\.js$/,
        loader: 'babel',
        exclude: /(node_modules)/,
        query: {
          presets: ['es2015', 'react']
        }
      },
      {
        test: /\.js[x]?$/,
        exclude: /node_modules/,
        loader: 'babel-loader?presets[]=es2015&presets[]=react',
      },
      {
        test: /\.(gif|jpg|png|woff|svg|eot|ttf)\??.*$/,
        loader: 'url-loader?limit=50000&name=[path][name].[ext]'
      },
      {
        test: /\.json$/,
        loader: 'json'
      }
    ]
  },
  //插件化
  plugins: [
    new webpack.ProvidePlugin({
      'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
    })
  ]

};
