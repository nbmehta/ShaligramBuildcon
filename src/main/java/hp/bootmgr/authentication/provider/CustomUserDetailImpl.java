/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.authentication.provider;

import hp.bootmgr.vo.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserDetailImpl implements ICustomUserDetail {

	private static final long serialVersionUID = 3070929966983161441L;
	
	private Employee employee;
	private List<GrantedAuthority> authorities;
	
	public CustomUserDetailImpl(Employee employee) {
		this.employee = employee;
		authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(employee.getRole().getName()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getUsername();
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

	@Override
	public Employee getEmployee() {
		return this.employee;
	}
}
