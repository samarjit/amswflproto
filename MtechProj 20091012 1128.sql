-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ams
--

CREATE DATABASE IF NOT EXISTS ams;
USE ams;

--
-- Definition of table `panel_fields`
--

DROP TABLE IF EXISTS `panel_fields`;
CREATE TABLE `panel_fields` (
  `scr_Name` varchar(20) default NULL,
  `panel_name` varchar(20) default NULL,
  `orderNo` varchar(20) default NULL,
  `lblname` varchar(40) default NULL,
  `fname` varchar(40) default NULL,
  `idname` varchar(40) default NULL,
  `datatype` varchar(20) default NULL,
  `dbcol` varchar(20) default NULL,
  `validation` varchar(255) default NULL,
  `queryname` varchar(255) default NULL,
  `nrow` varchar(4) default NULL,
  `ncol` varchar(4) default NULL,
  `CLASSNAME` varchar(20) default NULL,
  `HTMLELM` varchar(30) default NULL,
  `STORE` varchar(2) default NULL,
  `PRKEY` varchar(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `panel_fields`
--

/*!40000 ALTER TABLE `panel_fields` DISABLE KEYS */;
INSERT INTO `panel_fields` (`scr_Name`,`panel_name`,`orderNo`,`lblname`,`fname`,`idname`,`datatype`,`dbcol`,`validation`,`queryname`,`nrow`,`ncol`,`CLASSNAME`,`HTMLELM`,`STORE`,`PRKEY`) VALUES 
 ('frmRequest','panelFields','1','Business Date','bdate','bdate','DATE','BDATE',NULL,NULL,'0','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields','2','Employee Id','EmpId','empid','number','EMPID','','','0','1',NULL,'TEXTBOX',NULL,'Y'),
 ('frmRequest','panelFields','3','Employee Name','EmpName','empname','VARCHAR','EMPNAME','','','1','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields','4','Email Id','Empemail','empemail','VARCHAR','EMPEMAIL','','','1','1',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','buttonPanel','5','Submit','btnsubmit','btnSubmit','','','','','4','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequest','buttonPanel','2','Insert','btnInsert','btnUpdate','','','','','1','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequest','buttonPanel','3','Update','btnUpdate','btnUpdate','','','','','2','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequest','buttonPanel','1','Read','btnRead','btnRead','','','','','0','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequest','buttonPanel','4','Delete','btnDelete','btnDelete','','','','','3','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequest','panelFields1','1','Manager Id','mgrId','mgrId','VARCHAR','MGRID',NULL,NULL,'0','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields1','2','Manager Name','mgrName','mgrName','VARCHAR','MGRNAME','','','0','1',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields1','3','Employee Name','EmpName','empname','VARCHAR','EMPNAME','','','1','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields1','4','Email Id','Empemail','empemail','VARCHAR','EMPEMAIL','','','1','1',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields2','1','Business Date','bdate','bdate','DATE','BDATE',NULL,NULL,'0','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields2','2','Employee Id','EmpId','empid','number','EMPID','','','0','1',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields2','3','Employee Name','EmpName','empname','VARCHAR','EMPNAME','','','1','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequest','panelFields2','4','Email Id','Empemail','empemail','VARCHAR','EMPEMAIL','','','1','1',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequestList','panelFields','1','Employee Name','empName','empName','VARCHAR','EMPNAME',NULL,NULL,'0','0',NULL,'TEXTBOX','Y','Y'),
 ('frmRequestList','buttonPanel','1','Submit',NULL,NULL,NULL,NULL,NULL,NULL,'1','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequestList','buttonPanel','1','Search',NULL,NULL,NULL,NULL,NULL,NULL,'0','0',NULL,'BUTTON',NULL,NULL),
 ('frmRequestList','searchPanel','1','Employ Id','sempid','sempid',NULL,'EMPID',NULL,NULL,'0','0',NULL,'TEXTBOX',NULL,NULL),
 ('frmRequestList','searchPanel','2','Employ Name','sempname','sempname',NULL,'EMPNAME',NULL,NULL,'0','1',NULL,'TEXTBOX',NULL,NULL);
/*!40000 ALTER TABLE `panel_fields` ENABLE KEYS */;


--
-- Definition of table `request`
--

DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `BDATE` date default NULL,
  `EMPID` varchar(20) default NULL,
  `EMPNAME` varchar(30) default NULL,
  `MGRID` varchar(20) default NULL,
  `MGRNAME` varchar(30) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `request`
--

/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` (`BDATE`,`EMPID`,`EMPNAME`,`MGRID`,`MGRNAME`) VALUES 
 ('2009-10-11','9801','sam','9080','mad'),
 ('2009-10-08','780','tutu','10980','bhai');
/*!40000 ALTER TABLE `request` ENABLE KEYS */;


--
-- Definition of table `screen_panel`
--

DROP TABLE IF EXISTS `screen_panel`;
CREATE TABLE `screen_panel` (
  `scr_Name` varchar(20) default NULL,
  `panel_name` varchar(20) default NULL,
  `table_name` varchar(30) default NULL,
  `pk_name` varchar(30) default NULL,
  `template_name` varchar(30) default NULL,
  `SORTORDER` varchar(20) default NULL,
  `PANELTYPE` varchar(3) default NULL,
  `RELATEDPANEL` varchar(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `screen_panel`
--

/*!40000 ALTER TABLE `screen_panel` DISABLE KEYS */;
INSERT INTO `screen_panel` (`scr_Name`,`panel_name`,`table_name`,`pk_name`,`template_name`,`SORTORDER`,`PANELTYPE`,`RELATEDPANEL`) VALUES 
 ('frmRequest','panelFields','REQUEST','REQID','template1','1','2',NULL),
 ('frmRequest','panelFields1','REQUEST',NULL,NULL,'2','2',NULL),
 ('frmRequest','panelFields2','REQUEST',NULL,NULL,'3','2',NULL),
 ('frmRequest','buttonPanel',NULL,NULL,NULL,'4','1',NULL),
 ('frmRequestList','panelFields','REQUEST',NULL,'template1','2','2',NULL),
 ('frmRequestList','buttonPanel',NULL,NULL,NULL,'3','1',NULL),
 ('frmRequestList','searchPanel',NULL,NULL,'template1','1','2','panelFields');
/*!40000 ALTER TABLE `screen_panel` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
