------------*** 理财分类表 ****-----------
CREATE TABLE `financialclassification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(2) DEFAULT NULL COMMENT '分类编号',
  `name` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `firstAmount` decimal(10,4) DEFAULT '0.0000' COMMENT '初始金额',
  `lastProfitOrLoss` decimal(10,4) DEFAULT '0.0000' COMMENT '昨日盈亏',
  `currentTotalAmount` decimal(10,4) DEFAULT '0.0000' COMMENT '当前总金额',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `investInTotal` decimal(10,4) DEFAULT '0.0000' COMMENT '累计追加投入',
  `totalProfitOrLossAmount` decimal(10,4) DEFAULT '0.0000' COMMENT '盈亏总计',
  `withdrawalsInTotal` decimal(10,4) DEFAULT '0.0000' COMMENT '提现总额',
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `isDeleted` int(1) DEFAULT '0' COMMENT '逻辑删除状态,0-未删除；1-已删除',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='理财分类';

---------****  理财分类投入资金记录表 ** --------------
CREATE TABLE `financialclassificationrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,4) DEFAULT NULL COMMENT '投入金额',
  `invertTime` datetime DEFAULT NULL COMMENT '投入时间',
  `code` int(11) DEFAULT NULL COMMENT '理财分类代码编号',
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `isDeleted` int(1) DEFAULT '0' COMMENT '逻辑删除状态,0-未删除；1-已删除',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财分类增加资金记录表';


-----------**** 理财分类盈亏记录表 ***------------
CREATE TABLE `lcflProfitOrLoss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL COMMENT '分类编号',
  `profitOrLossAmount` decimal(10,4) DEFAULT NULL COMMENT '盈亏数额',
  `createDate` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `profitLossDate` datetime DEFAULT NULL COMMENT '盈亏日期',
  `isDeleted` int(1) DEFAULT '0' COMMENT '逻辑删除状态,0-未删除；1-已删除',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财分类盈亏记录表';

--------/** 记账，账目表 */
CREATE TABLE `tally` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '账单标题',
  `type` int(1) DEFAULT NULL COMMENT '账目类型，1-消费；2-其他',
  `amount` decimal(10,4) DEFAULT '0.0000' COMMENT '收支金额',
  `totalAmount` decimal(10,4) DEFAULT '0.0000' COMMENT '本月累计总金额',
  `payType` int(1) DEFAULT NULL COMMENT '收入类型，1-支出；2-入账',
  `payee` varchar(100) DEFAULT NULL COMMENT '收款方名称,收款人',
  `payer` varchar(100) DEFAULT NULL COMMENT '付款人,付款方姓名',
  `typeName` varchar(40) DEFAULT NULL COMMENT '账目类别名称',
  `remark` varchar(200) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL COMMENT '所属用户的用户ID',
  `tallyDate` datetime DEFAULT NULL COMMENT '账目日期',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `isDeleted` int(1) DEFAULT '0' COMMENT '是否被删除。0-没有被删除；1-已经被删除',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单，记账表';