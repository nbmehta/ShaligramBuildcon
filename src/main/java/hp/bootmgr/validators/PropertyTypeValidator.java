package hp.bootmgr.validators;

import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.vo.PropertyType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PropertyTypeValidator implements Validator {

	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PropertyType.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		PropertyType propertyType = (PropertyType) arg0;
		if(propertyTypeProviderService.isAlreadyAvailable(propertyType.getName()))
			errors.rejectValue("name", "", "Property Type already exist.");
	}

}
