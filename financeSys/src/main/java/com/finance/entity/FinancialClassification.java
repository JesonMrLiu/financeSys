package com.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FinancialClassification implements Serializable{

	private static final long serialVersionUID = 3646719347305452093L;
	
	private int id;
	private int code;//编号分类
	private String name;//分类名称
	private BigDecimal firstAmount;//初始金额
	private BigDecimal lastProfitOrLoss;//昨日盈亏
	private BigDecimal currentTotalAmount;//当前总金额
	private Date createDate;//创建时间
	private BigDecimal investInTotal;//累计追加兔肉
	private BigDecimal totalProfitOrLossAmount;//盈亏总计
	private BigDecimal withdrawalsInTotal;//提现总额
	private int userId;//用户ID
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getFirstAmount() {
		return firstAmount;
	}
	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}
	public BigDecimal getLastProfitOrLoss() {
		return lastProfitOrLoss;
	}
	public void setLastProfitOrLoss(BigDecimal lastProfitOrLoss) {
		this.lastProfitOrLoss = lastProfitOrLoss;
	}
	public BigDecimal getCurrentTotalAmount() {
		return currentTotalAmount;
	}
	public void setCurrentTotalAmount(BigDecimal currentTotalAmount) {
		this.currentTotalAmount = currentTotalAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public BigDecimal getInvestInTotal() {
		return investInTotal;
	}
	public void setInvestInTotal(BigDecimal investInTotal) {
		this.investInTotal = investInTotal;
	}
	public BigDecimal getWithdrawalsInTotal() {
		return withdrawalsInTotal;
	}
	public void setWithdrawalsInTotal(BigDecimal withdrawalsInTotal) {
		this.withdrawalsInTotal = withdrawalsInTotal;
	}
	public BigDecimal getWithdrawalsInTotalAbs(){
		return withdrawalsInTotal.abs();
	}
	public BigDecimal getTotalProfitOrLossAmount() {
		return totalProfitOrLossAmount;
	}
	public void setTotalProfitOrLossAmount(BigDecimal totalProfitOrLossAmount) {
		this.totalProfitOrLossAmount = totalProfitOrLossAmount;
	}
	public String getTotalAmountZhengShu(){
		String number = String.valueOf(this.getCurrentTotalAmount());
		return number.substring(0, number.indexOf("."));
	}
	public String getTotalAmountXiaoShu(){
		String number = String.valueOf(this.getCurrentTotalAmount());
		String n = number.substring(number.indexOf(".")+1);
		if(Integer.parseInt(n) == 0){
			return ".0";
		}
		return number.substring(number.indexOf("."));
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
