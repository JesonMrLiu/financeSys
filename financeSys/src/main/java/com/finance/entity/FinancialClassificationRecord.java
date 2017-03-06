package com.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinancialClassificationRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private BigDecimal amount;//投入金额
	private Date invertTime;//投入的时间
	private int code;//理财分类代码
	private int userId;//用户ID
	private String name;//理财分类名称
	private int isDeleted;//是否已删除。0-未删除；1-已删除，默认为0
	private Date deleteTime;//删除时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmountAbs(){
		return amount.abs();
	}
	public Date getInvertTime() {
		return invertTime;
	}
	public void setInvertTime(Date invertTime) {
		this.invertTime = invertTime;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	public String getInvestTimeStr(){
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(this.invertTime);
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
