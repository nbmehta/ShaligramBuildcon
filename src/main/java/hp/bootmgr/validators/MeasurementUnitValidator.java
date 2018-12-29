package hp.bootmgr.validators;

import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.vo.MeasurementUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MeasurementUnitValidator implements Validator{
	
	@Autowired
	private MeasurementUnitService measurementUnitService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MeasurementUnit.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors error) {
		MeasurementUnit measurementUnit=(MeasurementUnit) arg0;
		
		ValidationUtils.rejectIfEmpty(error, "name", "name.empty", "Please provide Measurement unit");
		if(measurementUnitService.isAlreadyAvailable(measurementUnit.getName()))
			error.rejectValue("name", "","Measurement Unit name already exist.");
	}
}