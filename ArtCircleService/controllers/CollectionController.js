const dbConfig = require('../util/dbconfig');
addCollection = (req, res) => {
  const body = req.body;
  const sql = 'INSERT INTO `artcircle`.`collection` (`userId`, `collectionId`, `type`, `cover`, `name`, `url`) VALUES (?, ?, ?, ?, ?, ?);';
  const sqlArr = [
    body.userId,
    body.collectionId,
    body.type,
    body.cover,
    body.name,
    body.url
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 0,
        'msg': "操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sql, sqlArr, callBack);
};
deleteCollection = (req, res) => {
  const body = req.body;
  const sql = 'DELETE FROM `artcircle`.`collection` WHERE (`userId` = ?) and (`collectionId` = ?);';
  const sqlArr = [
    body.userId,
    body.collectionId
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 0,
        'msg': "操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sql, sqlArr, callBack);
};
judgeExist = (req, res) => {
  let {userId,collectionId} = req.query;
  const sql = 'SELECT * FROM `artcircle`.`collection` WHERE `userId` = ? and collectionId = ?;';
  const sqlArr = [
    userId,
    collectionId
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql, sqlArr, callBack);
};
module.exports = {
  addCollection,
  deleteCollection,
  judgeExist
};
