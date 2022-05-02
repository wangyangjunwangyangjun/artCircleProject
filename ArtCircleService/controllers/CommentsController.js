const dbConfig = require('../util/dbconfig');
addComments = (req, res) => {
  const body = req.body;
  const sqlForInsert = 'INSERT INTO `artcircle`.`comments` (`id`, `content`, `type`, `userName`, `userId`, `shareId`, `userLogo`) VALUES (?, ?, ?, ?, ?, ?, ?);';
  const sqlArrForInsert = [
    body.id,
    body.content,
    body.type,
    body.userName,
    body.userId,
    body.shareId,
    body.userLogo,
  ];
  const callBackForInsert = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      const sqlForUpdate = 'update artcircle.share set comment=comment+1 where shareId = ?';
      const sqlArrForUpdate = [
        body.shareId
      ];
      const callBackForUpdate = (err, data) => {
        if (err) {
          console.log('链接出错' + err.toString())
        } else {
          res.send({
            'code': 0,
            'msg': "操作成功",
          })
        }
      };
      dbConfig.sqlConnect(sqlForUpdate, sqlArrForUpdate, callBackForUpdate);
    }
  };
  dbConfig.sqlConnect(sqlForInsert, sqlArrForInsert, callBackForInsert);
};
deleteComment = (req, res) => {
  const body = req.body;
  const sqlForDelete = 'DELETE FROM `artcircle`.`comments` WHERE (`id` = ?)';
  const sqlArrForDelete = [
    body.commentId,
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
  addComments,
  deleteComment
};
