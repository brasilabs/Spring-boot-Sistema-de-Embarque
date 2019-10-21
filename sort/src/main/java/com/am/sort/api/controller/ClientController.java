package com.am.sort.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.am.sort.api.dto.Summary;
import com.am.sort.api.response.Response;
import com.am.sort.api.security.entity.ChangeStatus;
import com.am.sort.api.security.entity.Client;
import com.am.sort.api.security.entity.User;
import com.am.sort.api.security.enums.ProfileEnum;
import com.am.sort.api.security.enums.StatusEnum;
import com.am.sort.api.security.jwt.JwtTokenUtil;
import com.am.sort.api.service.ClientService;
import com.am.sort.api.service.UserService;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;



	@PostMapping()
	@PreAuthorize("hasAnyRole('FATURISTA')")
	public ResponseEntity<Response<Client>> create(HttpServletRequest request, @RequestBody Client client,
			BindingResult result) {
		Response<Client> response = new Response<Client>();
		try {
			validateCreateClient(client, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			client.setStatus(StatusEnum.getStatus("Carregado"));
			client.setUser(userFromRequest(request));
			client.setDate(new Date());
			client.setNumer(generateNumber());
			Client clientPersisted = (Client) clientService.createOrUpdate(client);
			response.setData(clientPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateClient(Client client, BindingResult result) {
		if (client.getRazao() == null) {
			result.addError(new ObjectError("Razão", "Razão não informada"));
			return;
		}
	}
	
	public User userFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userService.findByEmail(email);
    }
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
	
	@PutMapping()
	@PreAuthorize("hasAnyRole('FATURISTA')")
	public ResponseEntity<Response<Client>> update(HttpServletRequest request, @RequestBody Client client,
			BindingResult result) {
		Response<Client> response = new Response<Client>();
		try {
			validateUpdateClient(client, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Client clientCurrent = clientService.findById(client.getId());
			client.setStatus(clientCurrent.getStatus());
			client.setUser(clientCurrent.getUser());
			client.setDate(clientCurrent.getDate());
			client.setNumero(clientCurrent.getNumero());
			if(clientCurrent.getAssignedUser() != null) {
				client.setAssignedUser(clientCurrent.getAssignedUser());
			}
			Client clientPersisted = (Client) clientService.createOrUpdate(client);
			response.setData(clientPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdateClient(Client client, BindingResult result) {
		if (client.getId() == null) {
			result.addError(new ObjectError("Cliente", "Id no information"));
			return;
		}
		if (client.getRazao() == null) {
			result.addError(new ObjectError("Razão", "Razão no information"));
			return;
		}
	}
	
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
	public ResponseEntity<Response<Client>> findById(@PathVariable("id") String id) {
		Response<Client> response = new Response<Client>();
		Client client = clientService.findById(id);
		if (client == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		List<ChangeStatus> changes = new ArrayList<ChangeStatus>();
		Iterable<ChangeStatus> changesCurrent =  clientService.listChangeStatus(client.getId());
		for (Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			ChangeStatus changeStatus = iterator.next();
			changeStatus.setClient(null);
			changes.add(changeStatus);
		}	
		client.setAuteracoes(changes);
		response.setData(client);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('FATURISTA')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Client client = clientService.findById(id);
		if (client == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		clientService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
    public  ResponseEntity<Response<Page<Client>>> findAll(HttpServletRequest request, @PathVariable int page, @PathVariable int count) {
		
		Response<Page<Client>> response = new Response<Page<Client>>();
		Page<Client> clients = null;
		User userRequest = userFromRequest(request);
		if(userRequest.getProfile().equals(ProfileEnum.ROLE_FINANCEIRO)) {
			clients = clientService.ListClient(page, count);
		} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_FATURISTA)) {
			clients = clientService.findByCurrentUser(page, count, userRequest.getId());
		}
		response.setData(clients);
		return ResponseEntity.ok(response);
    }
	
	@GetMapping(value = "{page}/{count}/{number}/{razao}/{status}/{prioridade}/{assigned}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
    public  ResponseEntity<Response<Page<Client>>> findByParams(HttpServletRequest request, 
    		 							@PathVariable("page") int page, 
    		 							@PathVariable("count") int count,
    		 							@PathVariable("numer") Integer numer,
    		 							@PathVariable("razao") String razao,
    		 							@PathVariable("status") String status,
    		 							@PathVariable("prioridade") String prioridade,
    		 							@PathVariable("assigned") boolean assigned) {
		
		razao = razao.equals("uninformed") ? "" : razao;
		status = status.equals("uninformed") ? "" : status;
		prioridade = prioridade.equals("uninformed") ? "" : prioridade;
		
		Response<Page<Client>> response = new Response<Page<Client>>();
		Page<Client> clients = null;
		if(numer > 0) {
			clients = clientService.findByNumer(page, count, numer);
		} else {
			User userRequest = userFromRequest(request);
			if(userRequest.getProfile().equals(ProfileEnum.ROLE_FINANCEIRO)) {
				if(assigned) {
					clients = clientService.findByParametersAndAssignedUser(page, count, razao, status, prioridade, userRequest.getId());
				} else {
					clients = clientService.findByParameters(page, count, razao, status, prioridade);
				}
			} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_FATURISTA)) {
				clients = clientService.findByParametersAndCurrentUser(page, count, razao, status, prioridade, userRequest.getId());
			}
		}
		response.setData(clients);
		return ResponseEntity.ok(response);
    }
	
	@PutMapping(value = "/{id}/{status}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
	public ResponseEntity<Response<Client>> changeStatus(
													@PathVariable("id") String id, 
													@PathVariable("status") String status, 
													HttpServletRequest request,  
													@RequestBody Client client,
													BindingResult result) {
		
		Response<Client> response = new Response<Client>();
		try {
			validateChangeStatus(id, status, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Client clientCurrent = clientService.findById(id);
			clientCurrent.setStatus(StatusEnum.getStatus(status));
			if(status.equals("Assigned")) {
				clientCurrent.setAssignedUser(userFromRequest(request));
			}
			Client clientPersisted = (Client) clientService.createOrUpdate(clientCurrent);
			ChangeStatus changeStatus = new ChangeStatus();
			changeStatus.setUserChange(userFromRequest(request));
			changeStatus.setDateChangeStatus(new Date());
			changeStatus.setStatus(StatusEnum.getStatus(status));
			changeStatus.setClient(clientPersisted);
			clientService.createChangeStatus(changeStatus);
			response.setData(clientPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateChangeStatus(String id,String status, BindingResult result) {
		if (id == null || id.equals("")) {
			result.addError(new ObjectError("Cliente", "Id no information"));
			return;
		}
		if (status == null || status.equals("")) {
			result.addError(new ObjectError("Cliente", "Status no information"));
			return;
		}
	}
	
	@GetMapping(value = "/summary")
	public ResponseEntity<Response<Summary>> findChart() {
		Response<Summary> response = new Response<Summary>();
		Summary chart = new Summary();
		int amountCarregado = 0;
		int amountRota = 0;
		int amountGalpao = 0;
		int amountAcertou = 0;
		int amountFinalizador = 0;
		Iterable<Client> clients = clientService.findAll();
		if (clients != null) {
			for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
				Client client = iterator.next();
				if(client.getStatus().equals(StatusEnum.Carregado)){
					amountCarregado ++;
				}
				if(client.getStatus().equals(StatusEnum.Rota)){
					amountRota ++;
				}
				if(client.getStatus().equals(StatusEnum.Galpao)){
					amountGalpao ++;
				}
				if(client.getStatus().equals(StatusEnum.Acertou)){
					amountAcertou ++;
				}
				if(client.getStatus().equals(StatusEnum.Finalizador)){
					amountFinalizador ++;
				}
			}	
		}
		chart.setAmountCarregado(amountCarregado);
		chart.setAmountRota(amountRota);
		chart.setAmountGalpao(amountGalpao);
		chart.setAmountAcertou(amountAcertou);
		chart.setAmountFinalizador(amountFinalizador);
		response.setData(chart);
		return ResponseEntity.ok(response);
	}
	
}
