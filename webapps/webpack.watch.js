const path = require('path');
const merge = require('webpack-merge');

const webpackProductionConfig = require('./webpack.prod.js');

module.exports = merge(webpackProductionConfig, {
  output: {
    path: '/home/oussama/Oussama/plf-enterprise-tomcat-standalone-6.2.x-20210709.123141-1088/platform-6.2.x-SNAPSHOT/webapps/share-mood/',
    filename: 'js/[name].bundle.js',
    libraryTarget: 'amd'
  }
});
