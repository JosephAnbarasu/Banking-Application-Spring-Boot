package com.Banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Banking.jwtFilter.JwtAuthenticationFilter;
import com.Banking.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	private UserDetailsServiceImpl userDetailsService;
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req-> req
						.requestMatchers("/api/accounts/{id}/deleteAccount","/api/users/{id}/deleteUser").hasRole("ADMIN")
						.requestMatchers("/login/**","/register/**")
						.permitAll()
						.anyRequest()
						.authenticated()
						).userDetailsService(userDetailsService)
						.sessionManagement(session-> session
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
						.build();
	}
    
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	return configuration.getAuthenticationManager();
	}
}
