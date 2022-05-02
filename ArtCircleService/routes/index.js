const express = require('express');
const router = express.Router();
//生成的图片放入uploads文件夹下
const multer = require('multer');
const upload = multer({dest: 'uploads/'});

const infoController = require('../controllers/InfoController');
const recommendController = require('../controllers/RecommendController');
const userController = require('../controllers/UserController');
const museumController = require('../controllers/MuseumController');
const collectionController = require('../controllers/CollectionController');
const commentsController = require('../controllers/CommentsController');
const favorController = require('../controllers/FavorController');
//搜索
const searchController = require('../controllers/SearchController');
//路由
// router.get('/getCollection', infoController.getCollection);
const shareController = require('../controllers/ShareController');
const themeController = require('../controllers/ThemeController');
const historyController = require('../controllers/HistoryController');

router.get('/getArtwork', infoController.getArtwork);
router.get('/getAllArtwork', infoController.getAllArtwork);
router.get('/getMuseum', infoController.getMuseum);
router.get('/getAllMuseum', infoController.getAllMuseum);
router.get('/getMuseumIntroduction', museumController.getMuseumIntroduction);
router.get('/getMuseumArtwork', museumController.getMuseumArtwork);
router.get('/getMuseumPlan', museumController.getMuseumPlan);
router.get('/getAllArtist', infoController.getAllArtist);
router.get('/getNews', infoController.getNews);
router.get('/getMyAchievement', userController.getMyAchievement);
router.get('/getMyHistory', userController.getMyHistory);
router.get('/getAllShare', infoController.getAllShare);
router.get('/getMyShare', userController.getMyShare);
router.get('/getMyCollection', userController.getMyCollection);
//搜索
router.get('/getSearch', searchController.getSearchResult);

// router.get('/getRecommend', cate.getRecommend);
router.get('/getRecommendForArtworkFromArtist', recommendController.getRecommendForArtworkFromArtist);
router.get('/getRecommendForArtworkFromMuseum', recommendController.getRecommendForArtworkFromMuseum);
router.get('/getRecommendForArtistFromArtwork', recommendController.getRecommendForArtistFromArtwork);
router.get('/getRecommendForMuseumFromArtwork', recommendController.getRecommendForMuseumFromArtwork);

router.post('/login', userController.login);

//上传图片文件
router.post('/upLoad', upload.single('myPhoto'),shareController.upLoadImage);
router.post('/createShare', shareController.createShare);

//主题搜索
router.get('/getThemeForTag',themeController.getThemeForTag);

//动态
router.get('/getComments', infoController.getComments);

//新增计划
router.post('/addPlan', museumController.addPlan);

//修改计划
router.post('/fulfillPlan', museumController.fulfillPlan);
router.post('/modifyPlan', museumController.modifyPlan);

//新增收藏
router.post('/addCollection', collectionController.addCollection);

//删除收藏
router.post('/deleteCollection', collectionController.deleteCollection);

//查找是否存在该收藏
router.get('/judgeExist', collectionController.judgeExist);

//添加评论
router.post('/addComments', commentsController.addComments);

//点赞功能
router.post('/findFavor', favorController.findFavor);
router.post('/ModifyFavor', favorController.ModifyFavor);

//增加浏览量
router.post('/addBrowse', shareController.addBrowse);

//添加历史
router.post('/addHistory', historyController.addHistory);

//删除计划
router.post('/deletePlan', museumController.deletePlan);

//修改动态
router.post('/modifyShare', userController.modifyShare);
router.post('/deleteShare', userController.deleteShare);

//获取用户基本信息
router.get('/getUserInfo', infoController.getUserInfo);

//删除评论
router.post('/deleteComment', commentsController.deleteComment);

//删除历史
router.post('/deleteHistory', historyController.deleteHistory);

module.exports = router;
