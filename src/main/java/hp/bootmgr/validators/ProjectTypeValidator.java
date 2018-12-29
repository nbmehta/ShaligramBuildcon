package hp.bootmgr.validators;

import hp.bootmgr.services.ProjectTypeService;
import hp.bootmgr.vo.ProjectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class ProjectTypeValidator  implements Validator{
	@Autowired
	ProjectTypeService projectTypeService;
	
	@Override
	public boolean supports(Class<?> clazz) {

		return ProjectType.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ProjectType projectType= (ProjectType) arg0;
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "Please provide Project Type");
		if(projectTypeService.isAlreadyExist(projectType.getName()))
			errors.rejectValue("name","","Project Type already exist.");
		
	}

}
