package com.example.art.dataInterface;

import com.example.art.theme.museumTheme.MuseumTagItem;

public class DataInterface {
//    public static String MYURL = "http://192.168.1.130:3000";
    public static String USERNAME;
    public static String USERLOGO;
    public static String USERID;
    //临时保存原先的用户id
    public static String USER_TEMP_ID;
    public static String LEVEL;
    public static String MYURL = "http://192.168.43.60:3000";
    public static String NEWS = "/getNews";
    public static String ARTWORK = "/getArtwork";
    public static String MUSEUM = "/getMuseum";
    public static String RECOMMENDFORARTWORKFROMMUSEUM = "/getRecommendForArtworkFromMuseum";
    public static String RECOMMENDFORARTWORKFROMARTIST = "/getRecommendForArtworkFromArtist";
    public static String RECOMMENDFORMUSEUMFROMARTWORK = "/getRecommendForMuseumFromArtwork";
    public static String RECOMMENDFORARTISTFROMARTWORK = "/getRecommendForArtistFromArtwork";

    public static String GETALLMUSEUM = "/getAllMuseum";
    public static String GETAllARTWORK = "/getAllArtwork";
    public static String GETALLARTIST = "/getAllArtist";
    public static String GETALLSHARE = "/getAllShare";

    //mineActivity
    public static String GETMYHISTORY = "/getMyHistory";
    public static String GETMYSHARE = "/getMyShare";
    public static String GETMYACHIEVEMENT = "/getMyAchievement";
    public static String GETMYCOLLECTION = "/getMyCollection";
    public static String LOGIN = "/login";

    //museumDetailActivity
    public static String GETMUSEUMDETAIL = "/getMuseumIntroduction";
    public static String GETMUSEUMPLAN = "/getMuseumPlan";
    public static String GETMUSEUMARTWORK = "/getMuseumArtwork";

    //search
    public static String GETSEARCH = "/getSearch";

    //upLoad
    public static String UPLOAD = "/upLoad";
    public static String CREATESHARE = "/createShare";

    //theme
    public static String ARTTHEMEFORTAG = "/getThemeForTag";

    //comments
    public static String GETCOMMENTS = "/getComments";

    //轮播图跳转
    public static String lunBoTuUrl = "http://www.zgysyjy.org.cn/";

    //新增计划
    public static String ADDPLAN = "/addPlan";

    //设置状态
    public static String FULFILL_PLAN = "/fulfillPlan";
    public static String MODIFY_PLAN = "/modifyPlan";
    public static String DELETE_PLAN = "/deletePlan";

    //添加收藏
    public static String ADDCOLLECTION = "/addCollection";

    //删除收藏
    public static String DELETECOLLECTION = "/deleteCollection";

    //查看是否存在该收藏
    public static String JUDGEEXIST = "/judgeExist";

    //添加评论
    public static String ADDCOMMENTS = "/addComments";

    //点赞功能
    public static String FINDFAVOR = "/findFavor";
    public static String MODIFYFAVOR = "/ModifyFavor";

    public static String ADD_BROWSE = "/addBrowse";

    //添加历史
    public static String ADD_HISTORY = "/addHistory";

    //修改动态
    public static String MODIFY_SHARE = "/modifyShare";

    //删除动态
    public static String DELETE_SHARE = "/deleteShare";

    //用户基本信息
    public static String GET_USER_INFO = "/getUserInfo";

    //删除历史
    public static String DELETE_COMMENT = "/deleteComment";
    //删除评论
    public static String DELETE_HISTORY = "/deleteHistory";

    public static String[] ARTTAGSFORTYPE1 = {
            "素描",
            "速写",
            "中国画",
            "油画",
            "版画",
            "壁画",
            "水粉画",
            "水彩画",
            "漫画",
            "连环画",
            "插画"
    };
    public static String[] ARTTAGSFORTYPE2 = {
            "礼器类",
            "兵器类",
            "杂器类",
            "农具类",
            "青铜铸",
            "铜镜类",
            "铜炉类",
            "铜像佛像类",
    };
    public static String[] ARTTAGSFORTYPE3 = {
            "古籍善本",
            "陈旧平装书",
            "线装书",
            "鉴赏图书",
            "连环画图书",
            "画报",
            "图册类",
            "摆设图书",
    };
    public static String[] ARTTAGSFORTYPE4 = {
            "石雕类",
            "木雕类",
            "竹雕类",
            "牙雕类",
            "角雕类",
            "根雕类",
            "玻璃钢雕塑类",
            "砂岩雕塑类",
            "金属雕塑类",
            "复合材料类",
    };
    public static String[] ARTTAGSFORTYPE5 = {
            "纺织类",
            "染制类",
            "织绣类",
    };
    public static String[] ARTTAGSFORTYPE6 = {
            "票据类",
            "钱币类",
            "邮票类",
            "火花类",
            "卡类",
    };
    public static String[] ARTTAGSFORTYPE7 = {
            "中堂",
            "条幅",
            "对联",
            "斗方",
    };
    public static String[] ARTTAGSFORTYPE8 = {
            "帽类",
            "鞋袜类",
            "马甲类",
            "掛类",
            "上衣类",
            "内衣类",
            "泳衣类",
    };
    public static String[] ARTTAGSFORTYPE9 = {
            "民间绘画类",
            "剪纸类",
            "皮影",
    };
    public static String[] ARTTAGSFORTYPE10 = {
            "笔类",
            "墨类",
            "纸类",
            "砚类",
    };
    public static String[] ARTTAGSFORTYPE11 = {
            "民间绘画类",
            "剪纸类",
            "皮影",
            "民间工艺类",
    };
    public static String[] ARTTAGSFORTYPE12 = {
            "笔类",
            "墨类",
            "纸类",
            "砚类",
            "其他配用类",
    };
    public static String[] ARTTAGSFORTYPE13 = {
            "椅凳类",
            "桌案类",
            "床榻类",
            "柜架类",
            "屏风类",
    };
    public static String[] ARTTAGSFORTYPE14 = {
            "风光摄影",
            "人物摄影",
            "静物摄影",
            "新闻摄影",
            "动物摄影",
            "广告摄影",
            "其他摄影",
    };
    public static String[] ARTTAGSFORTYPE15 = {
            "首饰类玉器",
            "器物类玉器",
            "陈设玉器",
    };
    public static String[] AUCTIONTAGSFORTYPE1 = {
            "苏富比拍卖行",
            "佳士得拍卖行",
            "中国嘉德",
            "北京保利",
            "北京瀚海",
            "北京匡时",
            "中国国枰拍卖",
            "广东华艺国际拍卖",
            "上海朵云轩集团",
            "北京大羿拍卖",
            "佳士得拍卖行",
            "德国纳高",
            "菲利普斯",
            "邦瀚斯(Bonhams)",
            "英国大维德拍卖",
    };
    public static String[] AUCTIONTAGSFORTYPE2 = {
            "瓷器",
            "玉器",
            "书画",
            "杂项",
            "油画",
    };
    public static MuseumTagItem[] CREATETAGSFORTYPE1 = {
            new MuseumTagItem("上海当代艺术博物馆", ""),
            new MuseumTagItem("浙江朱炳仁铜雕艺术博物馆", ""),
            new MuseumTagItem("老甲艺术馆", ""),
            new MuseumTagItem("北京奥运博物馆", ""),
            new MuseumTagItem("广东民间工艺博物馆", ""),
            new MuseumTagItem("南京云锦博物馆", ""),
            new MuseumTagItem("北京艺术博物馆", ""),
            new MuseumTagItem("大钟寺古钟博物馆", ""),
            new MuseumTagItem("西安碑林博物馆", ""),
            new MuseumTagItem("天津戏剧博物馆(广东会馆)", ""),
            new MuseumTagItem("南京博物院", ""),
            new MuseumTagItem("香港艺术馆", ""),
            new MuseumTagItem("中国美术馆", ""),
            new MuseumTagItem("中国国家博物馆", ""),
            new MuseumTagItem("故宫博物院", "museum_id_6"),
            new MuseumTagItem("浙江省博物馆", "museum_id_1"),
            new MuseumTagItem("温岭市博物馆", "museum_id_10"),
            new MuseumTagItem("三门县博物馆", "museum_id_11"),
            new MuseumTagItem("杭州博物馆", "museum_id_2"),
            new MuseumTagItem("中国茶叶博物馆", "museum_id_3"),
            new MuseumTagItem("浙江自然博物馆", "museum_id_4"),
            new MuseumTagItem("中国丝绸博物馆", "museum_id_5"),
            new MuseumTagItem("故宫博物馆", "museum_id_6"),
            new MuseumTagItem("台州市博物馆", "museum_id_7"),
            new MuseumTagItem("路桥区博物馆", "museum_id_8"),
            new MuseumTagItem("临海市博物馆", "museum_id_9"),
    };
    public static String[] MORETAGS = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
    };
}