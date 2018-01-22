var path = require('path');

module.exports = {
    entry: './src/main/js/app.js',

    devtool: 'sourcemaps',

    output: {
        path: __dirname,
        filename: './src/main/resources/static/bundle.js'
    },

    module: {
        rules: [{
            loader: 'babel-loader',
            test: /\.js$/,
            exclude: /node_modules/
        }]
    },
};