package hp.bootmgr.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hp.bootmgr.binders.InquiryFormBinder;
import hp.bootmgr.constants.RegPattern;

public class InquiryValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;  
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return InquiryFormBinder.class.equals(clazz);	}

	@Override
	public void validate(Object arg0, Errors errors) {
		
		InquiryFormBinder inquiry= (InquiryFormBinder) arg0; 
		
		ValidationUtils.rejectIfEmpty(errors, "inquiry.visitorName", "required", "Please provide Visitor's Name");
		if (!(inquiry.getInquiry().getVisitorName() != null && inquiry
				.getInquiry().getVisitorName().isEmpty())) {
			pattern = Pattern.compile(RegPattern.STRING_PATTERN);
			matcher = pattern.matcher(inquiry.getInquiry().getVisitorName());
			if (!matcher.matches()) {
				errors.rejectValue("inquiry.visitorName",
						"inquiry.visitorName.containNonChar",
						"Only characters are allowed in name field");
			}
		}
		ValidationUtils.rejectIfEmpty(errors, "inquiry.visitDate", "required", "Please provide Visit Date");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.intimeHour", "required", "Please provide Intime Hour");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.intimeMinute", "required", "Please provide Intime Minute");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.areaOrCity", "required", "Please provide Area Or City");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.contactNumber", "required", "Please provide Contact Number");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.visitedSite", "required", "Visited site is Required");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.attendee", "required", "Attendee is Required");
		ValidationUtils.rejectIfEmpty(errors, "inquiry.followUpBy", "required", "followUpBy is Required");
		
		
		if (!(inquiry.getInquiry().getContactNumber()!= null && inquiry.getInquiry().getContactNumber().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.MOBILE_PATTERN);  
			   matcher = pattern.matcher(inquiry.getInquiry().getContactNumber());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("inquiry.contactNumber", "inquiry.contactNumber.incorrect",  
			      "Enter a ContactNumber of 10 digits");  
			   }  
		}
		if (inquiry.getInquiry().getContactNumber()!= null) {  
			   pattern = Pattern.compile(RegPattern.ID_PATTERN);  
			   matcher = pattern.matcher(inquiry.getInquiry().getContactNumber());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("inquiry.contactNumber", "inquiry.contactNumber.incorrect",  
			      "ContactNumber must includes digits only");  
			   }  
		}
		ValidationUtils.rejectIfEmpty(errors, "inquiry.email", "required", "Please provide Email");
		if (!(inquiry.getInquiry().getEmail()!= null && inquiry.getInquiry().getEmail().isEmpty())) {  
			   pattern = Pattern.compile(RegPattern.EMAIL_PATTERN);  
			   matcher = pattern.matcher(inquiry.getInquiry().getEmail());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("inquiry.email", "inquiry.email.incorrect",  
			      "Enter a correct email");  
			   }  
			  }  
		  
	}

}
