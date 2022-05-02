const fs = require('fs');
const dbConfig = require('../util/dbconfig');
const path = require('path');
upLoadImage = (req, res) => {
  fs.readFile(req.file.path, (err, data) => {
    //读取失败，说明没有上传成功
    if (err) {
      return res.send('上传失败')
    }
    let time = Date.now() + parseInt(Math.random() * 10000 + "");
    let extname = req.file.mimetype.split('/')[1];
    let keepname = time + '.' + extname;
    fs.writeFile(path.join(__dirname, '../public/images/share/' + keepname), data, (err) => {
      if (err) {
        return res.send('上传失败')
      }
      res.send({code: 0, msg: "/images/share/" + keepname})
    })
  })
};

createShare = (req, res) => {
  const body = req.body;
  const sql = 'INSERT INTO `artcircle`.`share` (`userId`, `title`, `contentSimple`, `time`, `browse`, `favor`, `comment`, `userName`, `userLogo`, `cover`, `shareId`) VALUES (?, ?, ?, ?, 0, 0, 0, ?, ?, ?, ?)';
  const sqlArr = [body.userId,
    body.title,
    body.contentSimple,
    body.time,
    body.userName,
    body.userLogo,
    body.cover,
    body.shareId];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 1,
        'msg':"操作成功"
      })
    }
  };
  dbConfig.sqlConnect(sql, sqlArr, callBack);
};

addBrowse = (req, res) => {
  const body = req.body;
  const sqlForAddBrowse = 'update artcircle.share set browse=browse+1 where shareId = ?';
  const sqlArrForAddBrowse = [
    body.shareId
  ];
  const callBackForAddBrowse = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 0,
        'msg': "操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sqlForAddBrowse, sqlArrForAddBrowse, callBackForAddBrowse);
};

module.exports = {
  upLoadImage,
  createShare,
  addBrowse
};
