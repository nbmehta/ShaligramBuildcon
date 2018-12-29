package hp.bootmgr.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hp.bootmgr.vo.ProjectPropertyPlan;


public class ProjectPropertyPlanValidator implements Validator{	
	 
	@Override
	public boolean supports(Class<?> clazz) {
		
		return ProjectPropertyPlan.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors error) {
		ValidationUtils.rejectIfEmpty(error, "planName", "required", "Please provide Plan name");
		ValidationUtils.rejectIfEmpty(error, "project", "required", "Please provide Project Name");
		ValidationUtils.rejectIfEmpty(error, "propertyType", "required", "Please provide Property Type");
		ValidationUtils.rejectIfEmpty(error, "block", "required", "Please provide Block");
		
	
	}

}

