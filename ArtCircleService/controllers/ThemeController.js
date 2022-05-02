//链接图数据库-数据库名称和密码
const neo4j = require("neo4j-driver");
const driver = neo4j.driver('bolt://localhost', neo4j.auth.basic('ArtCircle','123456'));
const session = driver.session();

//艺术品主题搜索
getThemeForTag = (req, res) =>{
  let {tag} = req.query;
  console.log(tag);
  const sql = `match(n)-[r:type]-(m:ARTWORK) where n.name='${tag}' return m`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};
module.exports = {
  getThemeForTag
};
