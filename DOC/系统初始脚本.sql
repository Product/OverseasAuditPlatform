--init

DELETE FROM USERROLE
DELETE FROM ROLEMENUS
DELETE FROM MENU
DELETE FROM ROLEMENU
DELETE FROM USERINFO

--菜单数据
SET IDENTITY_INSERT MENU ON
insert into MENU([Menu_ID],[MENU_NAME],[MENU_NODEID],[MENU_SORT],[MENU_PARENTID],[MENU_LEVEL],MENU_PICURL)
values
--第一层
( 1,'数据采集','',1,0,1,''),
( 2,'规则与分析','',2,0,1,''),
( 3,'评价体系','',3,0,1,''),
( 4,'统计报表','',4,0,1,''),
( 5,'网站互动管理','',5,0,1,''),
( 6,'舆情系统','',6,0,1,''),

--第二层
( 106,'海淘网站','',1,1,2,'images/www_page.png'),
( 107,'海淘商品','',2,1,2,'images/cart.png'),
( 108,'规则分析','',1,2,2,'images/meun1.png'),
( 109,'风险分析','',2,2,2,'images/action_log.png'),
( 110,'评价报告','',0,3,2,'images/meun2.png'),
( 111,'诚信评价','',0,3,2,'images/auction_hammer_gavel.png'),
( 112,'样品检测','',0,3,2,'images/tree3.png'),
( 113,'网络测评','',0,3,2,'images/meun1.png'),
( 114,'统计报表','',0,4,2,'images/tree2.png'),
( 115,'系统管理','',0,5,2,'images/meun5.png'),
( 116,'预约管理','',0,5,2,'images/clock_history_frame.png'),
( 117,'网站互动','',0,5,2,'images/contact_email.png'),
( 118,'舆情管理','',1,6,2,'images/contact_email.png'),
( 119,'基础配置','',2,6,2,'images/contact_email.png'),

--第三层
( 1001,'分类管理','websiteStyleDom',0,106,3,'images/sortManagement.gif'),
( 1002,'海淘网列表','websiteListDom',0,106,3,'images/websiteList.gif'),
( 1003,'商品分类','productStyleDom',0,107,3,'images/productStyle.gif'),
( 1004,'数据采集','datagatherDom',0,107,3,'images/datagather.gif'),
( 1005,'商品库','productListDom',0,107,3,'images/productList.gif'),
( 1006,'规则设定','rulesDom',0,108,3,'images/ruleSet.gif'),
( 1007,'抽取数据查看','ExtractViewDom',0,108,3,'images/ruleSet.gif'),
( 1008,'分析类别','VentureAnalysisStyleDom',0,109,3,'images/ventureAnalysisStyle.gif'),
( 1009,'分析报告','VentureAnalysisReportDom',0,109,3,'images/reportOutput.gif'),
( 1010,'评价类别','EvaluationTypeDom',0,110,3,'images/reportTemplate.gif'),
( 1011,'报告输出','ReportListDom',0,110,3,'images/reportOutput.gif'),
( 1012,'评价指标设定','evaluationIndexDom',0,111,3,'images/evaluationIndexSet.gif'),
( 1013,'评价列表','evaluationDom',0,111,3,'images/evaluationList.gif'),
( 1014,'检测事件','DetectingEventDom',0,112,3,'images/testEvent.gif'),
( 1015,'检测记录','DetectingRecordDom',0,112,3,'images/testRecord.gif'),
( 1016,'检测评价','DetectingEvaluationDom',0,112,3,'images/testEvaluation.gif'),
( 1017,'测评信息','testDom',0,113,3,'images/application_view_list.png'),
( 1018,'测评类别','testStyleDom',0,113,3,'images/calendar_copy.png'),
( 1019,'投诉网站统计','Chart',0,114,3,'images/complainWebsiteCount.gif'),
( 1020,'投诉热点统计','Chart',0,114,3,'images/complainHotCount.gif'),
( 1021,'投诉地区统计','Chart',0,114,3,'images/complainAreaCount.gif'),
( 1022,'用户基本信息','PersonalInformationDom',0,115,3,'images/personalInformation.gif'),
( 1023,'角色管理','roleMenuDom',0,115,3,'images/roleMenu.gif'),
( 1024,'模板管理','ReportTemplateDom',0,116,3,'images/productInformation.gif'),
( 1025,'邮件列表','emailListDom',0,116,3,'images/emailList.gif'),
( 1026,'群组管理','GroupListDom',0,116,3,'images/reseller_programm.png'),
( 1027,'咨询建议','ConsultiveEditDom',0,117,3,'images/comments.png'),
( 1028,'专家问答','ExpertAnswerDom',0,117,3,'images/client_account_template.png'),
( 1029,'投诉处理','ComplaintHandlingDom',0,117,3,'images/productCategory.gif'),
( 1030,'舆情采集','NewsGatherDom',1,118,3,'images/productCategory.gif'),
( 1031,'舆情文章','NewsDocumentsDom',2,118,3,'images/productCategory.gif'),
( 1032,'舆情抽取','NewsExtractDom',3,118,3,'images/productCategory.gif'),
( 1033,'舆情关注','NewsFocusDom',4,118,3,'images/productCategory.gif'),
( 1034,'国家地区','CountryAreasDom',1,119,3,'images/productCategory.gif'),
( 1035,'品牌库','BrandsDom',2,119,3,'images/productCategory.gif'),
( 1036,'产品类别','ProductTypeDom',3,119,3,'images/productCategory.gif'),
( 1037,'产品库','ProductDefinitionDom',4,119,3,'images/productCategory.gif'),
( 1038,'配方原料类别','MaterialTypeDom',5,119,3,'images/productCategory.gif'),
( 1039,'配方原料库','MaterialsDom',6,119,3,'images/productCategory.gif')


SET IDENTITY_INSERT MENU OFF

--角色数据
select * from ROLEMENU --1
SET IDENTITY_INSERT ROLEMENU ON
insert into ROLEMENU(ROLE_ID,ROLE_NAME,createTime,ROLE_SORT) values(1,'系统管理员',GETDATE(),1)
SET IDENTITY_INSERT ROLEMENU OFF

--角色菜单数据
select * from ROLEMENUS where ROLE_ID = 1
insert into ROLEMENUS(ROLE_ID,MENU_ID) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,106),(1,107),
(1,108),(1,109),(1,110),(1,111),(1,112),(1,113),
(1,114),(1,115),(1,116),(1,117),(1,118),(1,119),
(1,1001),(1,1002),(1,1003),(1,1004),(1,1005),(1,1006),
(1,1007),(1,1008),(1,1009),(1,1010),(1,1011),(1,1012),
(1,1013),(1,1014),(1,1015),(1,1016),(1,1017),(1,1018),
(1,1019),(1,1020),(1,1021),(1,1022),(1,1023),(1,1024),
(1,1025),(1,1026),(1,1027),(1,1028),(1,1029),(1,1030),
(1,1031),(1,1032),(1,1033),(1,1034),(1,1035),(1,1036),
(1,1037),(1,1038),(1,1039)


--用户数据
select * from USERINFO

SET IDENTITY_INSERT USERINFO ON
insert into USERINFO(USER_ID,USER_CODE, USER_PWD,USER_CREATETIME,USER_REALNAME)
values(1,'admin','admin123',GETDATE(),'系统管理员')
SET IDENTITY_INSERT USERINFO OFF

--用户角色数据
select * from USERROLE
insert into USERROLE(ROLE_ID,[USER_ID])
values (1,1)

--事件类型定义
SET IDENTITY_INSERT EVENTTYPE ON
insert into EVENTTYPE(EVENTTYPEID,EVENTTYPENAME,TYPEAFFECT_AREA,TYPEAFFECT_WEBSITE,TYPEAFFECT_BRAND,TYPEAFFECT_PRODUCTTYPE) values
(1,'疫情事件',0,1,0,1),(2,'网站事件',1,0,0,0),(3,'品牌事件',0,0,1,0),(4,'地域事件',0,1,0,1)
SET IDENTITY_INSERT EVENTTYPE OFF