const dbConfig = require('../util/dbconfig');
addHistory = (req, res) => {
  const body = req.body;
  const sqlForInsertHistory = 'INSERT INTO `artcircle`.`history` (`id`, `title`, `time`, `userId`) VALUES (?, ?, ?, ?);';
  const sqlArrForInsertHistory = [
    body.userId+body.time+Math.random()*100,
    body.title,
    body.time,
    body.userId
  ];
  const callBackForInsertHistory = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 0,
        'msg': "操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sqlForInsertHistory, sqlArrForInsertHistory, callBackForInsertHistory);
};
deleteHistory = (req, res) => {
  const body = req.body;
  const sqlForDelete = 'DELETE FROM `artcircle`.`history` WHERE (`id` = ?)';
  const sqlArrForDelete = [
    body.historyId,
  ];
  const callBackForDelete = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      res.send({
        'code': 0,
        'msg': "操作成功",
      });
    }
  };
  dbConfig.sqlConnect(sqlForDelete, sqlArrForDelete, callBackForDelete);
};
module.exports = {
  addHistory,
  deleteHistory
};
