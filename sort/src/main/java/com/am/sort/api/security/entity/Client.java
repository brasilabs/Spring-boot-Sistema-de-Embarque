package com.am.sort.api.security.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.am.sort.api.security.enums.AtivEnum;
import com.am.sort.api.security.enums.ContIcmsEnum;
import com.am.sort.api.security.enums.FormaRecebEnum;
import com.am.sort.api.security.enums.StatusEnum;

@Document
public class Client {
	
	@Id
	private String id;
	
	@DBRef(lazy = true)
	private User user;
	
	@NotBlank(message = "O Campo não pode esta vazio")
	@Size(min = 14, max = 18)
	@CNPJ(message = "Requer o CNPJ")
	@CPF(message = "Requer o CPF")
	private String cnpjCpf;
	
	@NotBlank(message = "Requer o Nome ou Razão Social da Empresa")
	private String razao;
	
	@NotBlank(message = "Requer a Inscrição Estadual ou Rg do cliente")
	private String inscEstadRg;
	
	private Date date;
	
	@NotBlank(message = "Requer a data de nascimento")
	private Date dateNasc;
	
	@NotBlank(message = "Requer Nome da Fantasia da Empresa")
	private String fantasia;
	
	private String email;
	
	private String inscMun;
	
	@NotBlank(message = "Requer um Vendedor")
	private String vendedor;
	
	@NotBlank(message = "Requer um Atividade")
	private AtivEnum atividade;
	
	
	@NotBlank(message = "Requer o Endereço")
	private String endereco;
	
	@NotBlank(message = "Requer o numero")
	private String numero;
	
	private String complemento;
	
	@NotBlank(message = "Requer o bairro")
	private String bairro;
	
	@NotBlank(message = "Requer a cidade")
	private String cidade;
	
	@NotBlank(message = "Requer a uf")
	@Size(min = 2)
	private String uf;
	
	@NotBlank(message = "Requer o cep")
	@Size(min = 10)
	private String cep;
	
	@NotBlank(message = "Requer o telefone")
	@Size(min = 13)
	private String telefone;
	
	@NotBlank(message = "Requer o celuler")
	@Size(min = 14)
	private String fone;
	
	private String banco;
	
	private String agencia;
	
	private String conta;
	
	@NotBlank(message = "Requer um contator Comercial")
	private String comercial;
	
	@NotBlank(message = "Requer um telefone Comercial")
	@Size(min = 10)
	private String telFone;
	
	@NotBlank(message = "Requer um celular Comercial")
	@Size(min = 14)
	private String celfone;
	
	@NotBlank(message = "Requer o nome do proprietario ou do Gerente")
	private String proprietario;
	
	@NotBlank(message = "Requer Dizer se é contribuinte de icms")
	private ContIcmsEnum iscms;
	
	@NotBlank(message = "Requer se tem Dependente sim ou não")
	private ContIcmsEnum dependente;
	
	@NotBlank(message = "Requer a Forma de Recebimento do cliente")
	private FormaRecebEnum formaRecebimet;
	
	@NotBlank(message = "Requer a quantidade de dias para pagamento")
	@Size(min = 2)
	private String prazo;
	
	@NotBlank(message = "Requer o valor do limente de pagamento")
	private String limete;
	
	private Integer numer;
	
	private StatusEnum status;
	
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

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getInscEstadRg() {
		return inscEstadRg;
	}

	public void setInscEstadRg(String inscEstadRg) {
		this.inscEstadRg = inscEstadRg;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateNasc() {
		return dateNasc;
	}

	public void setDateNasc(Date dateNasc) {
		this.dateNasc = dateNasc;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInscMun() {
		return inscMun;
	}

	public void setInscMun(String inscMun) {
		this.inscMun = inscMun;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public AtivEnum getAtividade() {
		return atividade;
	}

	public void setAtividade(AtivEnum atividade) {
		this.atividade = atividade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getComercial() {
		return comercial;
	}

	public void setComercial(String comercial) {
		this.comercial = comercial;
	}

	public String getTelFone() {
		return telFone;
	}

	public void setTelFone(String telFone) {
		this.telFone = telFone;
	}

	public String getCelfone() {
		return celfone;
	}

	public void setCelfone(String celfone) {
		this.celfone = celfone;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public ContIcmsEnum getIscms() {
		return iscms;
	}

	public void setIscms(ContIcmsEnum iscms) {
		this.iscms = iscms;
	}

	public ContIcmsEnum getDependente() {
		return dependente;
	}

	public void setDependente(ContIcmsEnum dependente) {
		this.dependente = dependente;
	}

	public FormaRecebEnum getFormaRecebimet() {
		return formaRecebimet;
	}

	public void setFormaRecebimet(FormaRecebEnum formaRecebimet) {
		this.formaRecebimet = formaRecebimet;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public String getLimete() {
		return limete;
	}

	public void setLimete(String limete) {
		this.limete = limete;
	}
		
	public Integer getNumer() {
		return numer;
	}

	public void setNumer(Integer numer) {
		this.numer = numer;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
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
