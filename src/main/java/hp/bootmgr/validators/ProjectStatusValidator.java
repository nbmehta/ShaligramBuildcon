package hp.bootmgr.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.vo.ProjectStatus;

@Component
public class ProjectStatusValidator implements Validator {
	@Autowired
	private ProjectStatusProviderService projectStatusProviderService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectStatus.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ProjectStatus projectStatus= (ProjectStatus) obj;
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "Please provide status name");
		if(projectStatusProviderService.isAlreadyExist(projectStatus.getName()))
			errors.rejectValue("name","","Project Status already exist.");
	}

}
