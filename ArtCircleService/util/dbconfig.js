const mysql = require('mysql');

module.exports = {
  //数据库配置
  config: {
    host: '127.0.0.1',
    port: '3306',
    user: 'ArtCircle',
    password: 'ArtCircle',
    database: 'ArtCircle',
    dateStrings: true,
  },
  //连接数据库，使用mysql的连接池方式
  //连接池对象
  sqlConnect : function (sql, sqlArr, callBack) {
    var pool = mysql.createPool(this.config);
    pool.getConnection((err,conn)=>{
      console.log("sql: "+sql+" - sqlArr: "+sqlArr);
      if(err){
        console.log('连接失败');
        console.log(err.toString());
        return;
      }
      //事件驱动回调
      conn.query(sql,sqlArr,callBack);
      //释放连接
      conn.release();
    })
  }
};
