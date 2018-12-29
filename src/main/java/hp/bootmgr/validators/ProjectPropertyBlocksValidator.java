package hp.bootmgr.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hp.bootmgr.vo.ProjectPropertyBlock;

@Component
public class ProjectPropertyBlocksValidator implements Validator {
	
	private Pattern pattern;
	private Matcher matcher;
	String ID_PATTERN = "[0-9]+";

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectPropertyBlock.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors error) {
		ValidationUtils.rejectIfEmpty(error, "block", "name.empty", "Please provide Block name");
		ProjectPropertyBlock projectPropertyBlock = (ProjectPropertyBlock) arg0;

		if(projectPropertyBlock.getBlock().length() > 5)
			error.rejectValue("block", "length.incorrect", "Block name should be of less than 5 characters");
		if ((Integer) projectPropertyBlock.getNoOfFloor() != null) {
			pattern = Pattern.compile(ID_PATTERN);
			matcher = pattern.matcher(String.valueOf(projectPropertyBlock.getNoOfFloor()));
			if (!matcher.matches()) {
				error.rejectValue("noOfFloor", "id.incorrect", "Enter a numeric value");
			}
		}
	}
}
