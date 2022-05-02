const createError = require('http-errors');
const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');

const indexRouter = require('./routes/index');

//解决post请求得不到参数的问题
const bodyParser = require('body-parser');

const app = express();
const http = require('http');
const server = http.createServer(app);

//解决post请求得不到参数的问题
app.use(bodyParser.json({limit:"10240kb"}));
app.use(bodyParser.urlencoded({ extended: true }));


//基本配置和接口设置
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use('/', indexRouter);

//启动服务端口
server.listen('3000');
