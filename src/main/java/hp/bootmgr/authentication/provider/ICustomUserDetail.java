/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.authentication.provider;

import hp.bootmgr.vo.Employee;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetail extends UserDetails {
	public Employee getEmployee();
}
