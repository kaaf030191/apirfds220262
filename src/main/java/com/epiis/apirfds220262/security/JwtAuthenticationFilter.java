package com.epiis.apirfds220262.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epiis.apirfds220262.entity.EntityUser;
import com.epiis.apirfds220262.repository.RepositoryUser;
import com.epiis.apirfds220262.staticdata.EnumRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final RepositoryUser repositoryUser;
	private final JwtTokenService jwtTokenService;

	public JwtAuthenticationFilter(RepositoryUser repositoryUser, JwtTokenService jwtTokenService) {
		this.repositoryUser = repositoryUser;
		this.jwtTokenService = jwtTokenService;
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String header = request.getHeader("Authorization");

		if(header != null && header.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication() == null) {
			String token = header.substring(7);

			try {
				EntityUser entityUser = this.repositoryUser.findById(this.jwtTokenService.extractIdUser(token)).orElse(null);

				if(entityUser != null && this.jwtTokenService.isValid(token, entityUser)) {
					EnumRole role = EnumRole.fromValue(entityUser.getRole());

					if(role != null) {
						AuthenticatedUser authenticatedUser = new AuthenticatedUser(entityUser, role);

						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							authenticatedUser,
							null,
							authenticatedUser.getAuthorities()
						);

						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch(Exception e) {
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}
}
