//链接图数据库-数据库名称和密码
const neo4j = require("neo4j-driver");
const driver = neo4j.driver('bolt://localhost', neo4j.auth.basic('ArtCircle','123456'));
// const driver = require('../util/dbconfigForNeo4j');
const session = driver.session();
getRecommendForArtworkFromArtist = (req,res) =>{
  let {artist,k} = req.query;
  const sql = `match p = shortestpath((:ARTIST{name:'${artist}'})-[*..${k}]-(artwork:ARTWORK)) return artwork limit 100`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};
getRecommendForArtworkFromMuseum = (req, res) =>{
  let {museum, k} = req.query;
  const sql = `match p = shortestpath((:MUSEUM{name:'${museum}'})-[*..${k}]-(artwork:ARTWORK)) return artwork limit 100`;
  // const sql = `match p = shortestpath((:MUSEUM{name:'故宫博物馆'})-[*..1]-(artwork:ARTWORK)) return artwork limit 100`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};

//艺术家的推送
getRecommendForArtistFromArtwork = (req, res) =>{
  let {artwork, k} = req.query;
  const sql = `match p = shortestpath((:ARTWORK{name:'${artwork}'})-[*..${k}]-(artist:ARTIST)) return artist limit 100`;
  // const sql = `match p = shortestpath((:MUSEUM{name:'故宫博物馆'})-[*..1]-(artwork:ARTWORK)) return artwork limit 100`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};

//博物馆的推送
getRecommendForMuseumFromArtwork = (req, res) =>{
  let {artwork, k} = req.query;
  const sql = `match p = shortestpath((:ARTWORK{name:'${artwork}'})-[*..${k}]-(museum:MUSEUM)) return museum limit 100`;
  session.run(sql).then(function (result) {
    res.send({
      'list':result.records
    })
  }).catch(function (err) {
    console.log(err);
  })
};

module.exports = {
  getRecommendForArtworkFromArtist,
  getRecommendForArtworkFromMuseum,
  getRecommendForArtistFromArtwork,
  getRecommendForMuseumFromArtwork
};
