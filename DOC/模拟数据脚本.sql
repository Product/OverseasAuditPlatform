delete from COUNTRYREGION;
delete from NewsGather;
delete from NEWSGATHERTASK;
delete from NEWSGATHERTASKITEMPATH;
delete from NEWSGATHERTASKITEMPATH;
delete from ARTICLE;
delete from WEBSITE;
delete from WEBSITESTYLE;
delete from BRAND;
delete from AREA;
delete from REGION;
delete from COUNTRY;
delete from PRODUCTTYPE;
delete from EVENTTYPE;

SET IDENTITY_INSERT EVENTTYPE ON
insert into EVENTTYPE(EVENTTYPEID,EVENTTYPENAME,TYPEAFFECT_AREA,TYPEAFFECT_WEBSITE,TYPEAFFECT_BRAND,TYPEAFFECT_PRODUCTTYPE) values
(1,'�����¼�',0,1,0,1),
(2,'��վ�¼�',1,0,0,0),
(3,'Ʒ���¼�',0,0,1,0),
(4,'�����¼�',0,1,0,1)
SET IDENTITY_INSERT EVENTTYPE OFF


insert into REGION (REGION_REGIONCODE, REGION_NAME)
values('AS', '����');
insert into REGION (REGION_REGIONCODE, REGION_NAME)
values('EU', 'ŷ��');
insert into REGION (REGION_REGIONCODE, REGION_NAME)
values('NA', '������');
insert into REGION(REGION_REGIONCODE, REGION_NAME)
values('SA', '������');
insert into REGION(REGION_REGIONCODE, REGION_NAME)
values('OC', '������');

insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('CN', '�й�');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('JP', '�ձ�');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('KR', '����');

insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(1, 1);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(2, 1);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(3, 1);

insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('XXL', '������');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('ADL', '�Ĵ�����');

insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(4, 5);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(5, 5);

insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('HL', '����');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('DG', '�¹�');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('YG', 'Ӣ��');

insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(6, 2);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(7, 2);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(8, 2);

insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('US', '����');
insert into COUNTRY(COUNTRY_COUNTRYCODE, COUNTRY_NAME)
values('JND', '���ô�');
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(9, 3);
insert into COUNTRYREGION(COUNTRY_ID, REGION_ID)
values(10, 3);

insert into AREA(AREA_AREACODE, AREA_NAME, COUNTRY)
values('XG', '���', 1);
insert into AREA(AREA_AREACODE, AREA_NAME, COUNTRY)
values('TW', '̨��', 1);


insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('ţ��', 'Nutrilon', 6, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'Hero Baby', 6, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('������', 'Ekobaby', 6, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('Karicare', 'Karicare', 4, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('������', 'Aptamil', 4, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('ϲ��', 'Hipp', 7, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('�ظ���', 'Topfer', 7, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'Similac', 9, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('���޳�', 'Enfagrow', 9, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('������', 'Bellamy', 5, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����׿', 'Maxigenes', 5, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'SMA', 8, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('���ɽ', 'Wandashan', 1, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('��ʿ��', 'Yashily', 1, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'Yili', 1, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'Merries', 2, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'GOO.N', 2, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('�ն�', 'lldong', 3, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('���޳�', 'Enfamil', 10, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY,AREA, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('��������', 'illuma', 1, 1, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());
insert into BRAND(BRAND_NAMECN, BRAND_NAMEEN, COUNTRY,AREA, POPULARITY, MARKET_SHARE, BRAND_CREATETIME)
values('����', 'Abbott', 1, 1, cast(FLOOR(rand()*100) as int ), ceiling(rand()*100)*1.0/100, GETDATE());

insert into WEBSITESTYLE(WEBSITESTYLE_NAME, WEBSITESTYLE_CREATETIME, WEBSITESTYLE_REMARK, WEBSITESTYLE_CREATEPERSON)
values('������վ', GETDATE(), '', 1);

insert into WEBSITE(WEBSITE_NAME, WEBSITE_STYLE, WEBSITE_ADDRESS, WEBSITE_CREATETIME, LOCATION, INTEGRITY_DEGREE, COMPREHENSIVE_SCORE, WEBSITE_REMARK, REGULATORY, WEBSITE_CREATEPERSON)
values('���ֹ�', 1, '�й�', GETDATE(), 'http://www.m6go.com/', 60, 70, '', 1, 1);

insert into PRODUCTTYPE(PRODUCTTYPE_LEVEL, PRODUCTTYPE_NAME, PRODUCTTYPE_PTID, PRODUCTTYPE_REMARK, PRODUCTTYPE_USERINFO, PRODUCTTYPE_CREATETIME)
values(1, 'ʳƷ', 0, '', 1, GETDATE());
insert into PRODUCTTYPE(PRODUCTTYPE_LEVEL, PRODUCTTYPE_NAME, PRODUCTTYPE_PTID, PRODUCTTYPE_REMARK, PRODUCTTYPE_USERINFO, PRODUCTTYPE_CREATETIME)
values(2, 'ĸӤ', 1, '', 1, GETDATE());
insert into PRODUCTTYPE(PRODUCTTYPE_LEVEL, PRODUCTTYPE_NAME, PRODUCTTYPE_PTID, PRODUCTTYPE_REMARK, PRODUCTTYPE_USERINFO, PRODUCTTYPE_CREATETIME)
values(3, '�̷�', 2, '', 1, GETDATE());