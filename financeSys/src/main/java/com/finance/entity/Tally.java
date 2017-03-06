package com.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Tally implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;//标题
	private int payType;//收支类型，1-支出；2-入账
	private BigDecimal amount;//收支金额
	private BigDecimal totalAmount;//本月累计总金额
	private int type;//账目类型；1-消费；2-其他
	private String typeName;//账目类型名称
	private String payer;//付款人
	private String payee;//收款人
	private int userId;//用户ID
	private Date tallyDate;//记账日期，账目时间
	private Date createDate;//创建时间
	private String remark;//备注
	private int isDeleted;//是否被删除。0-没有删除；1-已删除
	private Date deleteTime;//删除时间
	private BigDecimal oldAmount;//在修改时，修改之前的金额
	
	//getter and setter ...
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getTallyDate() {
		return tallyDate;
	}
	public void setTallyDate(Date tallyDate) {
		this.tallyDate = tallyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public BigDecimal getOldAmount() {
		return oldAmount;
	}
	public void setOldAmount(BigDecimal oldAmount) {
		this.oldAmount = oldAmount;
	}
	
}
