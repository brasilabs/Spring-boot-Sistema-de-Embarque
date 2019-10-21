package com.am.sort.api.dto;

import java.io.Serializable;

public class Summary implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer amountCarregado;
	private Integer amountRota;
	private Integer amountGalpao;
	private Integer amountAcertou;
	private Integer amountFinalizador;

	
	public Integer getAmountCarregado() {
		return amountCarregado;
	}

	public void setAmountCarregado(Integer amountCarregado) {
		this.amountCarregado = amountCarregado;
	}

	public Integer getAmountRota() {
		return amountRota;
	}

	public void setAmountRota(Integer amountRota) {
		this.amountRota = amountRota;
	}

	public Integer getAmountGalpao() {
		return amountGalpao;
	}

	public void setAmountGalpao(Integer amountGalpao) {
		this.amountGalpao = amountGalpao;
	}

	public Integer getAmountAcertou() {
		return amountAcertou;
	}

	public void setAmountAcertou(Integer amountAcertou) {
		this.amountAcertou = amountAcertou;
	}

	public Integer getAmountFinalizador() {
		return amountFinalizador;
	}

	public void setAmountFinalizador(Integer amountFinalizador) {
		this.amountFinalizador = amountFinalizador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
