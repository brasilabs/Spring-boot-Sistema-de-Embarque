package com.am.sort.api.security.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.am.sort.api.security.enums.StatusEnum;


@Document
public class ChangeStatus {
	
	@Id
	private String id;
	
	@DBRef
	private Embarque embarque;
	
	@DBRef
	private Client client;
		
	@DBRef
	private User userChange;
	
	private Date dateChangeStatus;
	
	private StatusEnum status;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Embarque getEmbarque() {
		return embarque;
	}

	public void setEmbarque(Embarque embarque) {
		this.embarque = embarque;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getUserChange() {
		return userChange;
	}

	public void setUserChange(User userChange) {
		this.userChange = userChange;
	}

	public Date getDateChangeStatus() {
		return dateChangeStatus;
	}

	public void setDateChangeStatus(Date dateChangeStatus) {
		this.dateChangeStatus = dateChangeStatus;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	
}
