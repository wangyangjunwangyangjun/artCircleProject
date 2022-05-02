const dbConfig = require('../util/dbconfig');

// //获取某个用户的所有收藏
// getCollection = (req,res) => {
//   let {userId} = req.query;
//   const sql = 'select * from artcircle.collection where userId = ?';
//   const sqlArr = [userId];
//   const callBack = (err, data) => {
//     if (err) {
//       console.log('链接出错'+err.toString())
//     } else {
//       res.send({
//         'list': data
//       })
//     }
//   };
//   dbConfig.sqlConnect(sql,sqlArr,callBack);
// };

//根据作品的id查看某一个艺术品
getArtwork = (req,res) => {
  let {id} = req.query;
  const sql = 'select * from artcircle.artwork where id = ?';
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

getAllArtwork = (req,res) => {
  const sql = 'select * from artcircle.artwork order by createTime desc';
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,null,callBack);
};

//获取所有博物馆
getAllMuseum = (req,res) => {
  const sql = 'select * from artcircle.museum';
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,null,callBack);
};

//获取所有艺术家
getAllArtist = (req,res) => {
  const sql = 'select * from artcircle.artist';
  const callBack = (err, data) => {
    if (err) {
      console.log('链接出错'+err.toString())
    } else {
      res.send({
        'list': data
      })
    }
  };
  dbConfig.sqlConnect(sql,null,callBack);
};

//获取某个博物馆下所有的所有文物
getMuseum = (req,res) => {
  let{id} = req.query;
  const sqlArr = [id];
  const sql = 'select * from artcircle.museum where id = ?';
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


// type = 1,2,3,4
getNews = (req, res)=>{
  let {type} = req.query;
  const sql = 'select * from artcircle.news where type = ? order by time desc';
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

// type = 1表示艺术家推送、type=2表示博物馆推送、type=3表示艺术品推送
getRecommend = (req, res)=>{
  let {type} = req.query;
  const sql = 'select * from artcircle.recommend where type = ?';
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


getAllShare = (req, res)=>{
  let {type} = req.query;
  const sql = 'select * from artcircle.share order by time desc';
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

getComments = (req, res)=>{
  let {shareId} = req.query;
  const sql = 'select * from artcircle.comments where shareId = ? order by id desc';
  const sqlArr = [shareId];
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
getUserInfo = (req, res)=>{
  let {userId} = req.query;
  const sql = 'select * from artcircle.user where id = ?';
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

module.exports = {
  getArtwork,
  getAllArtwork,
  getAllMuseum,
  getAllArtist,
  getMuseum,
  getNews,
  getRecommend,
  getAllShare,
  getComments,
  getUserInfo
};
