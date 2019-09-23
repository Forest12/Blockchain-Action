package com.bcauction.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Auction
{
	private long id;
	private long auctionCreatorId; //회원id
	private long auctionId;
	private LocalDateTime createTime;
	private String isVaild = "V"; // V valid(유효함), C canceled, E ended
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private BigInteger lowestPrice;
	private String txsAddress;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAuctionCreatorId() {
		return auctionCreatorId;
	}

	public void setAuctionCreatorId(long auctionCreatorId) {
		this.auctionCreatorId = auctionCreatorId;
	}

	public long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(long auctionId) {
		this.auctionId = auctionId;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getIsVaild() {
		return isVaild;
	}

	public void setIsVaild(String isVaild) {
		this.isVaild = isVaild;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public BigInteger getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigInteger lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getTxsAddress() {
		return txsAddress;
	}

	public void setTxsAddress(String txsAddress) {
		this.txsAddress = txsAddress;
	}

	@Override
	public String toString() {
		return "Auction [auctionCreatorId=" + auctionCreatorId + ", auctionId=" + auctionId + ", createTime="
				+ createTime + ", endTime=" + endTime + ", id=" + id + ", isVaild=" + isVaild + ", lowestPrice="
				+ lowestPrice + ", startTime=" + startTime + ", txsAddress=" + txsAddress + "]";
	}

}
