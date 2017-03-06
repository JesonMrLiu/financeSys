package com.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinancialClassificationProfitOrLoss implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int code;
	private BigDecimal profitOrLossAmount;//盈亏数额
	private Date createDate;
	private int userId;
	private Date profitLossDate;//盈亏日期
	private String name;//理财分类名称
	private int isDeleted;//是否已删除。0-未删除；1-已删除，默认为0
	private Date deleteTime;//删除时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public BigDecimal getProfitOrLossAmount() {
		return profitOrLossAmount;
	}
	public void setProfitOrLossAmount(BigDecimal profitOrLossAmount) {
		this.profitOrLossAmount = profitOrLossAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getProfitLossDate() {
		return profitLossDate;
	}
	public void setProfitLossDate(Date profitLossDate) {
		this.profitLossDate = profitLossDate;
	}
	
	public String getProfitLossDateStr(){
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(this.profitLossDate);
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
	
}
