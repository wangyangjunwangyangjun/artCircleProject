const dbConfig = require('../util/dbconfig');
//链接图数据库-数据库名称和密码
const neo4j = require("neo4j-driver");
const driver = neo4j.driver('bolt://localhost', neo4j.auth.basic('ArtCircle','123456'));
const session = driver.session();

//模糊搜索
getSearchResult = (req, res) =>{
  let {key} = req.query;
  const sql = `match(n) where n.name=~'.*${key}.*' return n`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};
module.exports = {
  getSearchResult
};
