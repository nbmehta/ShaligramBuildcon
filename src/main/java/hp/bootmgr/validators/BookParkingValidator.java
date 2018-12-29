package hp.bootmgr.validators;

import hp.bootmgr.vo.ParkingBookingDetail;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookParkingValidator implements Validator{

	@Override
	public boolean supports(Class<?>clazz) {
		return ParkingBookingDetail.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "bookingDate", "required", "Please provide Booking Date");
		
	}

}
