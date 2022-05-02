const dbConfig = require('../util/dbconfig');
findFavor = (req, res) => {
  const body = req.body;
  const sqlForFind = 'SELECT * FROM artcircle.favor where userId = ? and shareId = ?;';
  const sqlArrForFind = [
    body.userId,
    body.shareId
  ];
  const callBackForFind = (err, data) => {
    if (err) {
      console.log(err.toString())
    } else {
      if(data.length===0){
        //即不存在该条记录，那么新增该条记录
        const sqlForInsert = 'INSERT INTO `artcircle`.`favor` (`userId`, `shareId`, `isFavored`) VALUES (?, ?, 0);';
        const sqlArrForInsert = [
          body.userId,
          body.shareId
        ];
        const callBackForInsert = (err, data) => {
          if (err) {
            console.log(err.toString())
          } else {
            res.send({
              'code': 0,
              'msg': "操作成功",
              'isFavored': 0
            })
          }
        };
        dbConfig.sqlConnect(sqlForInsert, sqlArrForInsert, callBackForInsert);
      }else{
        res.send({
          'code': 0,
          'msg': "操作成功",
          'isFavored': data[0].isFavored
        })
      }
    }
  };
  dbConfig.sqlConnect(sqlForFind, sqlArrForFind, callBackForFind);
};
//修改记录在设置初始状态之后
ModifyFavor = (req, res) => {
  const body = req.body;
  const sqlForAdd = 'UPDATE `artcircle`.`favor` SET `isFavored` = ? WHERE (`userId` = ?) and (`shareId` = ?);';
  const sqlArrForAdd = [
    body.isFavored,
    body.userId,
    body.shareId
  ];
  const callBackForAdd = (err, data) => {
    if (err) {
      console.log('链接出错' + err.toString())
    } else {
      if(body.isFavored===1){
        const sqlForUpdate = 'update artcircle.share set favor=favor+1 where shareId = ?';
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
      }else{
        const sqlForUpdate = 'update artcircle.share set favor=favor-1 where shareId = ?';
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
    }
  };
  dbConfig.sqlConnect(sqlForAdd, sqlArrForAdd, callBackForAdd);
};
module.exports = {
  findFavor,
  ModifyFavor
};
