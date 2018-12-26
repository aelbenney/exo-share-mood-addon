const path = require('path');
const merge = require('webpack-merge');
const webpackCommonConfig = require('./webpack.common.js');

// the display name of the war
const app = 'share-mood-portlets';

// add the server path to your server location path
const exoServerPath = "/home/ali/work/binaries/5.1/platform-5.1.0";

let config = merge(webpackCommonConfig, {
  output: {
    path: path.resolve(`${exoServerPath}/webapps/${app}/`)
  },
  devtool: 'inline-source-map'
});

module.exports = config;