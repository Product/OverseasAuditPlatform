--init

DELETE FROM USERROLE
DELETE FROM ROLEMENUS
DELETE FROM MENU
DELETE FROM ROLEMENU
DELETE FROM USERINFO

--�˵�����
SET IDENTITY_INSERT MENU ON
insert into MENU([Menu_ID],[MENU_NAME],[MENU_NODEID],[MENU_SORT],[MENU_PARENTID],[MENU_LEVEL],MENU_PICURL)
values
--��һ��
( 1,'���ݲɼ�','',1,0,1,''),
( 2,'���������','',2,0,1,''),
( 3,'������ϵ','',3,0,1,''),
( 4,'ͳ�Ʊ���','',4,0,1,''),
( 5,'��վ��������','',5,0,1,''),
( 6,'����ϵͳ','',6,0,1,''),

--�ڶ���
( 106,'������վ','',1,1,2,'images/www_page.png'),
( 107,'������Ʒ','',2,1,2,'images/cart.png'),
( 108,'�������','',1,2,2,'images/meun1.png'),
( 109,'���շ���','',2,2,2,'images/action_log.png'),
( 110,'���۱���','',0,3,2,'images/meun2.png'),
( 111,'��������','',0,3,2,'images/auction_hammer_gavel.png'),
( 112,'��Ʒ���','',0,3,2,'images/tree3.png'),
( 113,'�������','',0,3,2,'images/meun1.png'),
( 114,'ͳ�Ʊ���','',0,4,2,'images/tree2.png'),
( 115,'ϵͳ����','',0,5,2,'images/meun5.png'),
( 116,'ԤԼ����','',0,5,2,'images/clock_history_frame.png'),
( 117,'��վ����','',0,5,2,'images/contact_email.png'),
( 118,'�������','',1,6,2,'images/contact_email.png'),
( 119,'��������','',2,6,2,'images/contact_email.png'),

--������
( 1001,'�������','websiteStyleDom',0,106,3,'images/sortManagement.gif'),
( 1002,'�������б�','websiteListDom',0,106,3,'images/websiteList.gif'),
( 1003,'��Ʒ����','productStyleDom',0,107,3,'images/productStyle.gif'),
( 1004,'���ݲɼ�','datagatherDom',0,107,3,'images/datagather.gif'),
( 1005,'��Ʒ��','productListDom',0,107,3,'images/productList.gif'),
( 1006,'�����趨','rulesDom',0,108,3,'images/ruleSet.gif'),
( 1007,'��ȡ���ݲ鿴','ExtractViewDom',0,108,3,'images/ruleSet.gif'),
( 1008,'�������','VentureAnalysisStyleDom',0,109,3,'images/ventureAnalysisStyle.gif'),
( 1009,'��������','VentureAnalysisReportDom',0,109,3,'images/reportOutput.gif'),
( 1010,'�������','EvaluationTypeDom',0,110,3,'images/reportTemplate.gif'),
( 1011,'�������','ReportListDom',0,110,3,'images/reportOutput.gif'),
( 1012,'����ָ���趨','evaluationIndexDom',0,111,3,'images/evaluationIndexSet.gif'),
( 1013,'�����б�','evaluationDom',0,111,3,'images/evaluationList.gif'),
( 1014,'����¼�','DetectingEventDom',0,112,3,'images/testEvent.gif'),
( 1015,'����¼','DetectingRecordDom',0,112,3,'images/testRecord.gif'),
( 1016,'�������','DetectingEvaluationDom',0,112,3,'images/testEvaluation.gif'),
( 1017,'������Ϣ','testDom',0,113,3,'images/application_view_list.png'),
( 1018,'�������','testStyleDom',0,113,3,'images/calendar_copy.png'),
( 1019,'Ͷ����վͳ��','Chart',0,114,3,'images/complainWebsiteCount.gif'),
( 1020,'Ͷ���ȵ�ͳ��','Chart',0,114,3,'images/complainHotCount.gif'),
( 1021,'Ͷ�ߵ���ͳ��','Chart',0,114,3,'images/complainAreaCount.gif'),
( 1022,'�û�������Ϣ','PersonalInformationDom',0,115,3,'images/personalInformation.gif'),
( 1023,'��ɫ����','roleMenuDom',0,115,3,'images/roleMenu.gif'),
( 1024,'ģ�����','ReportTemplateDom',0,116,3,'images/productInformation.gif'),
( 1025,'�ʼ��б�','emailListDom',0,116,3,'images/emailList.gif'),
( 1026,'Ⱥ�����','GroupListDom',0,116,3,'images/reseller_programm.png'),
( 1027,'��ѯ����','ConsultiveEditDom',0,117,3,'images/comments.png'),
( 1028,'ר���ʴ�','ExpertAnswerDom',0,117,3,'images/client_account_template.png'),
( 1029,'Ͷ�ߴ���','ComplaintHandlingDom',0,117,3,'images/productCategory.gif'),
( 1030,'����ɼ�','NewsGatherDom',1,118,3,'images/productCategory.gif'),
( 1031,'��������','NewsDocumentsDom',2,118,3,'images/productCategory.gif'),
( 1032,'�����ȡ','NewsExtractDom',3,118,3,'images/productCategory.gif'),
( 1033,'�����ע','NewsFocusDom',4,118,3,'images/productCategory.gif'),
( 1034,'���ҵ���','CountryAreasDom',1,119,3,'images/productCategory.gif'),
( 1035,'Ʒ�ƿ�','BrandsDom',2,119,3,'images/productCategory.gif'),
( 1036,'��Ʒ���','ProductTypeDom',3,119,3,'images/productCategory.gif'),
( 1037,'��Ʒ��','ProductDefinitionDom',4,119,3,'images/productCategory.gif'),
( 1038,'�䷽ԭ�����','MaterialTypeDom',5,119,3,'images/productCategory.gif'),
( 1039,'�䷽ԭ�Ͽ�','MaterialsDom',6,119,3,'images/productCategory.gif')


SET IDENTITY_INSERT MENU OFF

--��ɫ����
select * from ROLEMENU --1
SET IDENTITY_INSERT ROLEMENU ON
insert into ROLEMENU(ROLE_ID,ROLE_NAME,createTime,ROLE_SORT) values(1,'ϵͳ����Ա',GETDATE(),1)
SET IDENTITY_INSERT ROLEMENU OFF

--��ɫ�˵�����
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


--�û�����
select * from USERINFO

SET IDENTITY_INSERT USERINFO ON
insert into USERINFO(USER_ID,USER_CODE, USER_PWD,USER_CREATETIME,USER_REALNAME)
values(1,'admin','admin123',GETDATE(),'ϵͳ����Ա')
SET IDENTITY_INSERT USERINFO OFF

--�û���ɫ����
select * from USERROLE
insert into USERROLE(ROLE_ID,[USER_ID])
values (1,1)

--�¼����Ͷ���
SET IDENTITY_INSERT EVENTTYPE ON
insert into EVENTTYPE(EVENTTYPEID,EVENTTYPENAME,TYPEAFFECT_AREA,TYPEAFFECT_WEBSITE,TYPEAFFECT_BRAND,TYPEAFFECT_PRODUCTTYPE) values
(1,'�����¼�',0,1,0,1),(2,'��վ�¼�',1,0,0,0),(3,'Ʒ���¼�',0,0,1,0),(4,'�����¼�',0,1,0,1)
SET IDENTITY_INSERT EVENTTYPE OFF