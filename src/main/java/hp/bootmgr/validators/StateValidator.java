package hp.bootmgr.validators;

import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.vo.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StateValidator implements Validator {
	@Autowired
	private StateManagementService stateManagementService;
	@Override
	public boolean supports(Class<?> clazz) {
		return State.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		State state=(State) arg0;
		ValidationUtils.rejectIfEmpty(errors, "stateName", "stateName.empty", "Please provide state name");
		if(stateManagementService.isAlreadyExist(state.getStateName()))
			errors.rejectValue("stateName", "","State Name already exist.");
	}

}
