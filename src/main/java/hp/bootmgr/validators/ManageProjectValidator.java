package hp.bootmgr.validators;

import hp.bootmgr.binders.ProjectFormBinder;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ManageProjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectFormBinder.class.equals(clazz);
	}

	@Override
	public void validate(Object objects, Errors errors) {
	
		ValidationUtils.rejectIfEmpty(errors, "project.name", "name.empty", "Please provide Project name");
		ValidationUtils.rejectIfEmpty(errors, "project.code", "name.empty", "Please provide Project code");
		ValidationUtils.rejectIfEmpty(errors, "project.startDate", "name.empty", "Please provide Project StartDate");
		ValidationUtils.rejectIfEmpty(errors, "project.endDate", "name.empty", "Please provide Project EndDate");
		ValidationUtils.rejectIfEmpty(errors, "project.city", "name.empty", "Please provide Project City");
		ValidationUtils.rejectIfEmpty(errors, "project.state", "name.empty", "Please provide Project State");
		ValidationUtils.rejectIfEmpty(errors, "project.projectType", "name.empty", "Please provide Project Type");
		
	}

}
