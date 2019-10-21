package com.am.sort.api.security.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.am.sort.api.security.enums.PrioridadeEnum;
import com.am.sort.api.security.enums.StatusEnum;

@Document
public class Embarque {
	
	
	@Id
	private String id;
	
	@DBRef(lazy = true)
	private User user;
	
	private String rota;
		
	private String endereco;
	
	private String motorista;
	
	private Date date;
	
	private String client;
		
	private String vendedor;
	
	private String romaneiolog;
	
	private String tipoNfe;
	
	private String nfeNfce;
	
	private Double totalNfe;
	
	private String formPag;
	
	private String descricao;
	
	private Integer numero;
	
	private StatusEnum status;
	
	private PrioridadeEnum prioridade;
	
	@DBRef(lazy = true)
	private User assignedUser;
	
	@Transient
	private List<ChangeStatus> auteracoes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRota() {
		return rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getRomaneiolog() {
		return romaneiolog;
	}

	public void setRomaneiolog(String romaneiolog) {
		this.romaneiolog = romaneiolog;
	}

	public String getTipoNfe() {
		return tipoNfe;
	}

	public void setTipoNfe(String tipoNfe) {
		this.tipoNfe = tipoNfe;
	}

	public String getNfeNfce() {
		return nfeNfce;
	}

	public void setNfeNfce(String nfeNfce) {
		this.nfeNfce = nfeNfce;
	}

	public Double getTotalNfe() {
		return totalNfe;
	}

	public void setTotalNfe(Double totalNfe) {
		this.totalNfe = totalNfe;
	}

	public String getFormPag() {
		return formPag;
	}

	public void setFormPag(String formPag) {
		this.formPag = formPag;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public PrioridadeEnum getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadeEnum prioridade) {
		this.prioridade = prioridade;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public List<ChangeStatus> getAuteracoes() {
		return auteracoes;
	}

	public void setAuteracoes(List<ChangeStatus> auteracoes) {
		this.auteracoes = auteracoes;
	}
	
	
}
