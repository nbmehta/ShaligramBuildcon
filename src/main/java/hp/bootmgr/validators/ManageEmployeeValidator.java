package hp.bootmgr.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hp.bootmgr.constants.RegPattern;
import hp.bootmgr.vo.Employee;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ManageEmployeeValidator implements Validator {
	private Pattern pattern;  
	private Matcher matcher;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Employee.class;
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty",
				"Please provide First name");
		
		Employee employee = (Employee) arg0;
		 if (!(employee.getFirstName() != null && employee.getFirstName().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.STRING_PATTERN);  
			   matcher = pattern.matcher(employee.getFirstName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("firstName", "firstName.containNonChar",  
			      "First Name must be characters");  
			   }  
			  }  
		
		
		ValidationUtils.rejectIfEmpty(errors, "middleName", "middleName.empty","Please provide Middle Name");
		 if (!(employee.getMiddleName() != null && employee.getMiddleName().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.STRING_PATTERN);  
			   matcher = pattern.matcher(employee.getMiddleName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("middleName", "middleName.containNonChar",  
			      "Middle Name must be characters");  
			   }  
			  }  
		ValidationUtils.rejectIfEmpty(errors, "lastName","lastName.empty","Please provide Last Name");
		 if (!(employee.getLastName() != null && employee.getLastName().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.STRING_PATTERN);  
			   matcher = pattern.matcher(employee.getLastName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("lastName", "lastName.containNonChar",  
			      "lastName Name must be characters");  
			   }  
			  }  
		
		ValidationUtils.rejectIfEmpty(errors, "address1","address1.empty","Please provide Address1");
		ValidationUtils.rejectIfEmpty(errors, "city", "city.empty","Please provide  City");
		if (!(employee.getCity() != null && employee.getCity().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.STRING_PATTERN);  
			   matcher = pattern.matcher(employee.getCity());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("city", "city.containNonChar",  
			      "City Name must be characters");  
			   }  
			  } 
		ValidationUtils.rejectIfEmpty(errors, "phone","phone.empty","Please provide Phone");
		/*if (!(employee.getPhone() != null && employee.getPhone().isEmpty())) {  
			
			   pattern = Pattern.compile(RegPattern.ID_PATTERN);  
			   matcher = pattern.matcher(employee.getPhone());  
			   if (matcher.matches()) {  
			    errors.rejectValue("phone", "phone.incorrect",  
			      "Phone Number must be numeric");  
			   }  
			  }  
	*/
		
		 if (!(employee.getPhone() != null && employee.getPhone().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.MOBILE_PATTERN);  
			   matcher = pattern.matcher(employee.getPhone());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("phone", "phone.incorrect",  
			      "Enter a phone number of 10 digits");  
			   }  
			  }  
		
		ValidationUtils.rejectIfEmpty(errors, "mobileNo", "mobileNo.empty","Please provide Mobile Number");
		 if (!(employee.getMobileNo() != null && employee.getMobileNo().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.MOBILE_PATTERN);  
			   matcher = pattern.matcher(employee.getMobileNo());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("mobileNo", "mobileNo.incorrect",  
			      "Mobile Number must be numeric");  
			   }  
			  }  
		 if (!(employee.getMobileNo() != null && employee.getMobileNo().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.ID_PATTERN);  
			   matcher = pattern.matcher(employee.getMobileNo());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("mobileNo", "mobileNo.incorrect",  
			      "Enter a mobile number of 10 digits");  
			   }  
			  }  
		ValidationUtils.rejectIfEmpty(errors, "email","email.empty","Please provide Email");
		if (!(employee.getEmail() != null && employee.getEmail().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.EMAIL_PATTERN);  
			   matcher = pattern.matcher(employee.getEmail());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("email", "email.incorrect",  
			      "Enter a correct Email");  
			   }  
			  }  
		ValidationUtils.rejectIfEmpty(errors, "username", "username.empty","Please provide Username");
		ValidationUtils.rejectIfEmpty(errors, "password","password.empty","Please provide Password");
		ValidationUtils.rejectIfEmpty(errors, "extension","extension.empty","Please provide Extension");
		ValidationUtils.rejectIfEmpty(errors, "state", "username.empty","Please provide State");
		
		
		
		
		
	}

}
