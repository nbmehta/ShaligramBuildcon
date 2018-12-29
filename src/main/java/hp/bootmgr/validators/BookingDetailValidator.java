package hp.bootmgr.validators;

import hp.bootmgr.vo.BookingDetail;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookingDetailValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BookingDetail.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "bookingDate", "required", "Please provide Booking Date");
		ValidationUtils.rejectIfEmpty(errors, "project", "required", "Project is Required");
		ValidationUtils.rejectIfEmpty(errors, "propertyDetail", "required", "PropertyDetail is Required");
		ValidationUtils.rejectIfEmpty(errors, "memberDetail", "required", "Member Detail is Required");
		ValidationUtils.rejectIfEmpty(errors, "bookedByEmployee", "required", "bookedByEmployee is Required");
	}
}
