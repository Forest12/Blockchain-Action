package com.bcauction.domain;

import java.time.LocalDateTime;

public class Ownership
{
	private long id;
	private long ownerId;
	private long workId;
	private LocalDateTime ownStartDate;
	private LocalDateTime ownEndDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public LocalDateTime getOwnStartDate() {
		return ownStartDate;
	}

	public void setOwnStartDate(LocalDateTime ownStartDate) {
		this.ownStartDate = ownStartDate;
	}

	public LocalDateTime getOwnEndDate() {
		return ownEndDate;
	}

	public void setOwnEndDate(LocalDateTime ownEndDate) {
		this.ownEndDate = ownEndDate;
	}

	@Override
	public String toString() {
		return "Ownership [id=" + id + ", ownEndDate=" + ownEndDate + ", ownStartDate=" + ownStartDate + ", ownerId="
				+ ownerId + ", workId=" + workId + "]";
	}
}
