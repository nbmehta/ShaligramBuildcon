package hp.bootmgr.validators;

import hp.bootmgr.vo.ProjectExtraParking;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProjectExtraParkingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectExtraParking.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors error) {
		ValidationUtils.rejectIfEmpty(error, "parkingNo", "parkingNo.empty",
				"Parking Number required");
		
	}

}
