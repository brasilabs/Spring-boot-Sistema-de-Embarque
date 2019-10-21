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
import com.am.sort.api.security.entity.Embarque;
import com.am.sort.api.security.entity.User;
import com.am.sort.api.security.enums.ProfileEnum;
import com.am.sort.api.security.enums.StatusEnum;
import com.am.sort.api.security.jwt.JwtTokenUtil;
import com.am.sort.api.service.EmbarqueService;
import com.am.sort.api.service.UserService;

@RestController
@RequestMapping("/api/embarque")
@CrossOrigin(origins = "*")
public class EmbarqueController {
	
	@Autowired
	private EmbarqueService embarqueService;

	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@PostMapping()
	@PreAuthorize("hasAnyRole('FATURISTA')")
	public ResponseEntity<Response<Embarque>> create(HttpServletRequest request, @RequestBody Embarque embarque,
			BindingResult result) {
		Response<Embarque> response = new Response<Embarque>();
		try {
			validateCreateEmbarque(embarque, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			embarque.setStatus(StatusEnum.getStatus("Carregado"));
			embarque.setUser(userFromRequest(request));
			embarque.setDate(new Date());
			embarque.setNumero(generateNumber());
			Embarque embarquePersisted = (Embarque) embarqueService.createOrUpdate(embarque);
			response.setData(embarquePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateEmbarque(Embarque embarque, BindingResult result) {
		if (embarque.getClient() == null) {
			result.addError(new ObjectError("Embarque", "Cliente no information"));
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
	public ResponseEntity<Response<Embarque>> update(HttpServletRequest request, @RequestBody Embarque embarque,
			BindingResult result) {
		Response<Embarque> response = new Response<Embarque>();
		try {
			validateUpdateEmbarque(embarque, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Embarque embarqueCurrent = embarqueService.findById(embarque.getId());
			embarque.setStatus(embarqueCurrent.getStatus());
			embarque.setUser(embarqueCurrent.getUser());
			embarque.setDate(embarqueCurrent.getDate());
			embarque.setNumero(embarqueCurrent.getNumero());
			if(embarqueCurrent.getAssignedUser() != null) {
				embarque.setAssignedUser(embarqueCurrent.getAssignedUser());
			}
			Embarque embarquePersisted = (Embarque) embarqueService.createOrUpdate(embarque);
			response.setData(embarquePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdateEmbarque(Embarque embarque, BindingResult result) {
		if (embarque.getId() == null) {
			result.addError(new ObjectError("Embarque", "Id no information"));
			return;
		}
		if (embarque.getClient() == null) {
			result.addError(new ObjectError("Embarque", "Cliente no information"));
			return;
		}
	}
	
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
	public ResponseEntity<Response<Embarque>> findById(@PathVariable("id") String id) {
		Response<Embarque> response = new Response<Embarque>();
		Embarque embarque = embarqueService.findById(id);
		if (embarque == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		List<ChangeStatus> changes = new ArrayList<ChangeStatus>();
		Iterable<ChangeStatus> changesCurrent =  embarqueService.listChangeStatus(embarque.getId());
		for (Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			ChangeStatus changeStatus = iterator.next();
			changeStatus.setEmbarque(null);
			changes.add(changeStatus);
		}	
		embarque.setAuteracoes(changes);
		response.setData(embarque);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('FATURISTA')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Embarque embarque = embarqueService.findById(id);
		if (embarque == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		embarqueService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
    public  ResponseEntity<Response<Page<Embarque>>> findAll(HttpServletRequest request, @PathVariable int page, @PathVariable int count) {
		
		Response<Page<Embarque>> response = new Response<Page<Embarque>>();
		Page<Embarque> embarques = null;
		User userRequest = userFromRequest(request);
		if(userRequest.getProfile().equals(ProfileEnum.ROLE_FINANCEIRO)) {
			embarques = embarqueService.listEmbarque(page, count);
		} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_FATURISTA)) {
			embarques = embarqueService.findByCurrentUser(page, count, userRequest.getId());
		}
		response.setData(embarques);
		return ResponseEntity.ok(response);
    }
	
	@GetMapping(value = "{page}/{count}/{numbero}/{rota}/{client}/{status}/{prioridade}/{assigned}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
    public  ResponseEntity<Response<Page<Embarque>>> findByParams(HttpServletRequest request, 
    		 							@PathVariable("page") int page, 
    		 							@PathVariable("count") int count,
    		 							@PathVariable("numero") Integer numero,
    		 							@PathVariable("rota") String rota,
    		 							@PathVariable("client") String client,
    		 							@PathVariable("status") String status,
    		 							@PathVariable("prioridade") String prioridade,
    		 							@PathVariable("assigned") boolean assigned) {
		
		rota = rota.equals("uninformed") ? "" : rota;
		client = client.equals("uninformed") ? "" : client;
		status = status.equals("uninformed") ? "" : status;
		prioridade = prioridade.equals("uninformed") ? "" : prioridade;
		
		Response<Page<Embarque>> response = new Response<Page<Embarque>>();
		Page<Embarque> embarques = null;
		if(numero > 0) {
			embarques = embarqueService.findByNumber(page, count, numero);
		} else {
			User userRequest = userFromRequest(request);
			if(userRequest.getProfile().equals(ProfileEnum.ROLE_FINANCEIRO)) {
				if(assigned) {
					embarques = embarqueService.findByParametersAndAssignedUser(page, count, rota, client, status, prioridade, userRequest.getId());
				} else {
					embarques = embarqueService.findByParameters(page, count, rota, client, status, prioridade);
				}
			} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_FATURISTA)) {
				embarques = embarqueService.findByParametersAndCurrentUser(page, count, rota, client, status, prioridade, userRequest.getId());
			}
		}
		response.setData(embarques);
		return ResponseEntity.ok(response);
    }
	
	@PutMapping(value = "/{id}/{status}")
	@PreAuthorize("hasAnyRole('FATURISTA','FINANCEIRO')")
	public ResponseEntity<Response<Embarque>> changeStatus(
													@PathVariable("id") String id, 
													@PathVariable("status") String status, 
													HttpServletRequest request,  
													@RequestBody Embarque embarque,
													BindingResult result) {
		
		Response<Embarque> response = new Response<Embarque>();
		try {
			validateChangeStatus(id, status, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Embarque embarqueCurrent = embarqueService.findById(id);
			embarqueCurrent.setStatus(StatusEnum.getStatus(status));
			if(status.equals("Assigned")) {
				embarqueCurrent.setAssignedUser(userFromRequest(request));
			}
			Embarque embarquePersisted = (Embarque) embarqueService.createOrUpdate(embarqueCurrent);
			ChangeStatus changeStatus = new ChangeStatus();
			changeStatus.setUserChange(userFromRequest(request));
			changeStatus.setDateChangeStatus(new Date());
			changeStatus.setStatus(StatusEnum.getStatus(status));
			changeStatus.setEmbarque(embarquePersisted);
			embarqueService.createChangeStatus(changeStatus);
			response.setData(embarquePersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateChangeStatus(String id,String status, BindingResult result) {
		if (id == null || id.equals("")) {
			result.addError(new ObjectError("Embarque", "Id no information"));
			return;
		}
		if (status == null || status.equals("")) {
			result.addError(new ObjectError("Embarque", "Status no information"));
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
		Iterable<Embarque> embarques = embarqueService.findAll();
		if (embarques != null) {
			for (Iterator<Embarque> iterator = embarques.iterator(); iterator.hasNext();) {
				Embarque embarque = iterator.next();
				if(embarque.getStatus().equals(StatusEnum.Carregado)){
					amountCarregado ++;
				}
				if(embarque.getStatus().equals(StatusEnum.Rota)){
					amountRota ++;
				}
				if(embarque.getStatus().equals(StatusEnum.Galpao)){
					amountGalpao ++;
				}
				if(embarque.getStatus().equals(StatusEnum.Acertou)){
					amountAcertou ++;
				}
				if(embarque.getStatus().equals(StatusEnum.Finalizador)){
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
