package com.am.sort.api.security.enums;



public enum StatusEnum {

	
	Carregado,
	Rota,
	Galpao,
	Acertou,
	Finalizador;
	
	public static StatusEnum getStatus(String status) {
		switch(status) {
		case "Carregado" : return Carregado;
		case "Rota" : return Rota;
		case "Galpao" : return Galpao;
		case "Acertou" : return Acertou;
		case "Finalizador" : return Finalizador;
		default : return Carregado;
		}
	}

}
