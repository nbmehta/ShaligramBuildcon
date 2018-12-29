package hp.bootmgr.validators;

import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.vo.EmployeeRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class EmployeeRoleValidator implements Validator{
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeeRole.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		
		EmployeeRole employeeRole= (EmployeeRole) arg0;
		ValidationUtils.rejectIfEmpty(errors, "name", "required", "Please provide Employee Role");
		if(employeeRoleService.isAlreadyExist("ROLE_"+employeeRole.getName()))
			errors.rejectValue("name","","Employee Role already exist.");
	}
}
