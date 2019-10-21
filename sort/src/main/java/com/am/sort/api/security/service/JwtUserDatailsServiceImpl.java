package com.am.sort.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.am.sort.api.security.entity.User;
import com.am.sort.api.security.jwt.JwtUserFactory;
import com.am.sort.api.service.UserService;

@Service
public class JwtUserDatailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userService.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Nenhum Usuário Encontrado com Nome de Usuário '%s'.", email));
		}else {
			return JwtUserFactory.create(user);
		}
		
	}

}
