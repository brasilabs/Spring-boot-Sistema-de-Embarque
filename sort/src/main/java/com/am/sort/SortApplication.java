package com.am.sort;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.am.sort.api.repository.UserRepository;
import com.am.sort.api.security.entity.User;
import com.am.sort.api.security.enums.ProfileEnum;

@SpringBootApplication
public class SortApplication {

	public static void main(String[] args) {
		SpringApplication.run(SortApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(usuarioRepository, passwordEncoder);
		};
	}
	
	private void initUsers(UserRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		admin.setEmail("admin@sortfruit.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		User find = usuarioRepository.findByEmail("admin@sortfruit.com");
		if (find == null) {
			usuarioRepository.save(admin);
		}
	}


}
