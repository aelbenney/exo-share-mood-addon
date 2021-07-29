const path = require('path');
const merge = require('webpack-merge');
const webpackCommonConfig = require('./webpack.common.js');

const config = merge(webpackCommonConfig, {
  mode: 'production',
  entry: {
    shareMood: './src/main/webapp/vue-app/share-mood/main.js'
  },
  output: {
    path: path.join(__dirname, 'target/share-mood/'),
    filename: 'js/[name].bundle.js',
    libraryTarget: 'amd'
  },
  externals: {
    jquery: '$',
    vuetify: 'Vuetify'
  }
});

module.exports = config;
