package com.bcauction.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid
{
	private long id;
	private long auctionPartId;
	private long auctionId;
	private LocalDateTime bidDate;
	private BigDecimal bidAmount;
	private String isBid = "N";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAuctionPartId() {
		return auctionPartId;
	}

	public void setAuctionPartId(long auctionPartId) {
		this.auctionPartId = auctionPartId;
	}

	public long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(long auctionId) {
		this.auctionId = auctionId;
	}

	public LocalDateTime getBidDate() {
		return bidDate;
	}

	public void setBidDate(LocalDateTime bidDate) {
		this.bidDate = bidDate;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getIsBid() {
		return isBid;
	}

	public void setIsBid(String isBid) {
		this.isBid = isBid;
	}

}
