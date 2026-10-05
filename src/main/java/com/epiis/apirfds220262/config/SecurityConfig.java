package com.epiis.apirfds220262.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.epiis.apirfds220262.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.cors(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(
					"/general/**",
					"/complaint/**",
					"/suggestion/**",
					"/complaintcomment/**",
					"/user/login",
					"/user/register",
					"/docs",
					"/swagger-ui",
					"/swagger-ui/**",
					"/api-docs",
					"/api-docs/**",
					"/v3/api-docs",
					"/v3/api-docs/**"
				).permitAll()
				.requestMatchers("/admin/**").hasRole("ENCARGADO")
				.anyRequest().authenticated()
			)
			.exceptionHandling(handling -> handling
				.authenticationEntryPoint((request, response, exception) -> writeJson(response, HttpStatus.UNAUTHORIZED, "Sesión no iniciada o token inválido."))
				.accessDeniedHandler((request, response, exception) -> writeJson(response, HttpStatus.FORBIDDEN, "No cuenta con permisos para acceder a este recurso."))
			)
			.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	private void writeJson(HttpServletResponse response, HttpStatus httpStatus, String message) throws IOException {
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write("{\"type\":\"error\",\"status\":\"" + httpStatus.value() + "\",\"listMessage\":[\"" + message + "\"]}");
	}
}
