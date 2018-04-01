package com.rust.bill.test;

import java.util.Date;

public class MspBillDownloadRecordDTO {

	private String recordId;

	private String billType;

	private String recCreateOperatorId;

	private String recUpdatePerson;

	private String merchantNo;

	private String fileName;

	private String fileUrl;

	private Date recCreateTime;

	private Date recUpdateTime;
	
	private Date startTime;
	
	private Date endTime;

	private String caller;

	private String status;

	private String remark;

	private String searchCondition;
	
	private Integer totalRecordNumber;

	public MspBillDownloadRecordDTO() {
	}

	public MspBillDownloadRecordDTO(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getRecCreateOperatorId() {
		return recCreateOperatorId;
	}

	public void setRecCreateOperatorId(String recCreateOperatorId) {
		this.recCreateOperatorId = recCreateOperatorId;
	}

	public String getRecUpdatePerson() {
		return recUpdatePerson;
	}

	public void setRecUpdatePerson(String recUpdatePerson) {
		this.recUpdatePerson = recUpdatePerson;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Date getRecCreateTime() {
		return recCreateTime;
	}

	public void setRecCreateTime(Date recCreateTime) {
		this.recCreateTime = recCreateTime;
	}

	public Date getRecUpdateTime() {
		return recUpdateTime;
	}

	public void setRecUpdateTime(Date recUpdateTime) {
		this.recUpdateTime = recUpdateTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public Integer getTotalRecordNumber() {
		return totalRecordNumber;
	}

	public void setTotalRecordNumber(Integer totalRecordNumber) {
		this.totalRecordNumber = totalRecordNumber;
	}
	
	
}
