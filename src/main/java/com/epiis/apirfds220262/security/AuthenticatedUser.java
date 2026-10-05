package com.epiis.apirfds220262.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epiis.apirfds220262.entity.EntityUser;
import com.epiis.apirfds220262.staticdata.EnumRole;

public class AuthenticatedUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final String idUser;
	private final String email;
	private final String password;
	private final EnumRole role;

	public AuthenticatedUser(EntityUser entityUser, EnumRole role) {
		this.idUser = entityUser.getIdUser();
		this.email = entityUser.getEmail();
		this.password = entityUser.getPassword();
		this.role = role;
	}

	public String getIdUser() {
		return this.idUser;
	}

	public EnumRole getRole() {
		return this.role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> listGrantedAuthority = new ArrayList<>();

		listGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_" + this.role.getCode()));

		return listGrantedAuthority;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
