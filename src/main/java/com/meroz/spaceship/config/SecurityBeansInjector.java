package com.meroz.spaceship.config;

import com.meroz.spaceship.exception.GeneralSecurityException;
import com.meroz.spaceship.repository.UserRepository;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeansInjector {

	private final UserRepository userRepository;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
		authenticationStrategy.setPasswordEncoder(passwordEncoder());
		authenticationStrategy.setUserDetailsService(userDetailsService());

		return authenticationStrategy;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return (username) -> userRepository.findByUsername(username)
				.orElseThrow(() -> new GeneralSecurityException(ErrorsEnum.NOT_FOUND, new Object[]{username}));
	}

}
