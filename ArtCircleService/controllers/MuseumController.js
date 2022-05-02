const dbConfig = require('../util/dbconfig');
//链接图数据库-数据库名称和密码
const neo4j = require("neo4j-driver");
const driver = neo4j.driver('bolt://localhost', neo4j.auth.basic('ArtCircle','123456'));
const session = driver.session();
//获取所有博物馆
getMuseumIntroduction = (req,res) => {
  let {museumId} = req.query;
  const sql = 'select * from artcircle.museum where id = ?';
  const sqlArr = [museumId];
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
// match(museum:MUSEUM)-[:house]-(artwork:ARTWORK) where museum.name='故宫博物馆' return artwork
getMuseumArtwork = (req,res) =>{
  let {museumName} = req.query;
  const sql = `match(museum:MUSEUM)-[:house]-(artwork:ARTWORK) where museum.name= '${museumName}' return artwork`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};
getMuseumPlan = (req,res) =>{
  let {museumId,userId} = req.query;
  let sql, sqlArr;
  if(museumId===undefined){
    sql = 'select * from artcircle.plan where userId = ? order by time desc';
    sqlArr = [userId];
  }else{
    sql = 'select * from artcircle.plan where museumId = ? and userId = ? order by time desc';
    sqlArr = [museumId,userId];
  }
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

addPlan = (req,res) =>{
  const body = req.body;
  const planId = req.body.time+body.userId;
  const sql = 'INSERT INTO `artcircle`.`plan` (`id`, `time`, `museumId`, `note`, `status`, `userId`, `museumName`) VALUES (?, ?, ?, ?, ?, ?, ?);';
  const sqlArr = [
      planId,
      body.time,
      body.museumId,
      body.note,
      0,
      body.userId,
      body.museumName
  ];
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'code': 0,
        'msg':"添加成功",
      })
    }
  };
  dbConfig.sqlConnect(sql,sqlArr,callBack);
};
fulfillPlan = (req,res) =>{
  const body = req.body;
  const sql = 'UPDATE `artcircle`.`plan` SET `status` = ? WHERE (`id` = ?);';
  const sqlArr = [
    body.status,
    body.id
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
modifyPlan = (req,res) =>{
  const body = req.body;
  const sql = 'UPDATE `artcircle`.`plan` SET `time` = ?, `museumId` = ?, `note` = ?, `museumName` = ? WHERE (`id` = ?);';
  const sqlArr = [
    body.time,
    body.museumId,
    body.note,
    body.museumName,
    body.planId,
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
deletePlan = (req,res) =>{
  const body = req.body;
  const sql = 'DELETE FROM `artcircle`.`plan` WHERE (`id` = ?);';
  const sqlArr = [
    body.planId,
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
  getMuseumIntroduction,
  getMuseumArtwork,
  getMuseumPlan,
  addPlan,
  modifyPlan,
  deletePlan,
  fulfillPlan
};
