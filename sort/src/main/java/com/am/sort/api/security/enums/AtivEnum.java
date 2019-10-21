package com.am.sort.api.security.enums;

public enum AtivEnum {
	
	DISTRIBUIDOR("Distribuidor"),
	PRESTACAO_DE_SERVICO("Pretação de Serviço"),
	ONG("Ong"),
	PANIFICACAO("Panificação"),
	SERVICO_PUBLICO("Serviço Publico"),
	VAREJO_1CHECK_OUT("Varejo 1 check Out"),
	VAREJO_2_A_4("Varejo 2 a 4"),
	VAREJO_5_A_9("Varejo 5 a 9"),
	VAREJO_10_MAIS("Varejo 10 ou Mais"),
	FRUTARIAS_E_QUITANDA("Frutarias e Quitanda"),
	HOTEIS("Hoteis"),
	SUPERMERCADOS("Supermercados"),
	ACOUGUE("Açougue"),
	COMERCIO("Comercio"),
	BANCO("Banco"),
	CONTRUCAO_CIVIL("Construçao Civil"),
	COMUNICACAO("Comunicação"),
	CONFECCAO("Confecçao"),
	ESCOLA("Escola"),
	FARMACIA("Farmacia"),
	FAZENDA("Fazenda"),
	IGREJA("Igreja"),
	LABORATORIO("Laboratorio"),
	POSTO_DE_COMBUSTIVEL("Posto de Combutivel"),
	OTICA("Otica"),
	RESTAURANTE("Restaurante"),
	SERVICOS("Serviços"),
	TRANSPORTE("Transporte"),
	LANCHONETE("Lanchonete"),
	OPERADORA_DE_CARTAO("Operadora de Cartão"),
	INDUSTRIA("Industria"),
	ACAITERIA_SORVT_LANC("Açaiteria Sorveteria Lanchonete"),
	PIZZARIA("Pizzaria"),
	SINDICATO("Sindicato"),
	CRIACAO_DE_GADO("Criação de Gado"),
	FABRICACAO_DE_PRODUT("Fabricação de Produtos"),
	COMERCIO_EM_GERAL("Comercio em Geral"),
	COMERCIO_VAREJISTA("Comercio Varejista"),
	EVENTOS("Eventos");
	
	private String descricao;
	
	
	AtivEnum(String descricao){
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}
	
}
