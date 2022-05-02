const dbConfig = require('../util/dbconfig');
login = (req,res) =>{
  const body = req.body;
  const sql = 'select * from artcircle.user where id = ?';
  const sqlArr = [body.userId];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      if(body.userPass===data[0].password){
        res.send({
          'code': 0,
          'msg':"登录成功",
          'userLogo':data[0].userLogo,
          'userName':data[0].userName,
          'level':data[0].level,
        })
      }else{
        res.send({
          'code': 1,
          'msg':"用户不存在或密码错误"
        })
      }
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};


getMyAchievement = (req, res)=>{
  let {userId} = req.query;
  const sql = 'select * from artcircle.achievement where userId = ?';
  const sqlArr = [userId];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
getMyShare = (req, res)=>{
  let {id} = req.query;
  const sql = 'select * from artcircle.share where userId = ?';
  const sqlArr = [id];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
getMyHistory = (req, res)=>{
  let {type} = req.query;
  const sql = 'select * from artcircle.history';
  const sqlArr = [type];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
getMyCollection = (req, res)=>{
  let {userId,type} = req.query;
  const sql = 'select * from artcircle.collection where userId = ? and type = ?';
  const sqlArr = [userId,type];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
modifyShare = (req,res) =>{
  const body = req.body;
  const sql = 'UPDATE `artcircle`.`share` SET `title` = ?, `contentSimple` = ?, `cover` = ? WHERE (`shareId` = ?);';
  const sqlArr = [
    body.title,
    body.content,
    body.cover,
    body.shareId
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'code': 0,
        'msg':"操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
deleteShare = (req,res) =>{
  const body = req.body;
  const sql = 'DELETE FROM `artcircle`.`share` WHERE (`shareId` = ?);';
  const sqlArr = [
    body.shareId
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'code': 0,
        'msg':"操作成功",
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
module.exports = {
  login,
  getMyAchievement,
  getMyShare,
  getMyHistory,
  getMyCollection,
  modifyShare,
  deleteShare
};
