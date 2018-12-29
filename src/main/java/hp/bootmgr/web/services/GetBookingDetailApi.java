package hp.bootmgr.web.services;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import hp.bootmgr.services.BookingDetailService;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.PropertyDetailService;
import hp.bootmgr.vo.BookingDetail;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBookingDetailApi {

	@Autowired
	private BookingDetailService bookingDetailService;
	@Autowired
	private ProjectService projectService;
	@Autowired 
	private EmployeeManagementService employeeManagementService;
	@Autowired
	private PropertyDetailService propertyDetailService;
	
	@RequestMapping(value = "/api/admin/getBookingDetail", method = RequestMethod.POST)
	public ResponseEntity<List<BookingDetail>> list() {
		List<BookingDetail> bookingDetails = bookingDetailService.getAll();
		if (bookingDetails.isEmpty()) {
			return new ResponseEntity<List<BookingDetail>>(HttpStatus.NO_CONTENT);// You many decide to return 	// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<BookingDetail>>(bookingDetails,HttpStatus.OK);
	}

	@RequestMapping(value = "/api/admin/getBookingDetail/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",	ResultCodes.INVALID_REQUEST);
		}
		BookingDetail ret = bookingDetailService.getById(idi);
		return ret == null ? new Result("Booking Detail not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}

	@RequestMapping(value = "/api/admin/deleteBookingDetail/{id}", method = RequestMethod.POST)
	public Object deleteByid(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = bookingDetailService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Booking detail not found",
				ResultCodes.STATE_NOT_FOUND);
	}

	@RequestMapping(value = "/api/admin/updateBookingDetail/", method = RequestMethod.POST)
	public ResponseEntity<Result> updateBookingDetail(@RequestParam HashMap<String, String> parms) {
		String id = parms.get("id");
		Result msg = null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
			BookingDetail detail = bookingDetailService.getById(Integer.parseInt(id));
			detail.setNote(parms.get("note"));
			detail.setProject(projectService.getById(Integer.parseInt(parms.get("project"))));
			detail.setBookedByEmployee(employeeManagementService.getById(Integer.parseInt(parms.get("employee"))));
			detail.setResale(parms.get("resale").equals("true"));
			detail.setAvailableForResale(parms.get("available").equals("true"));
			detail.setPropertyDetail(propertyDetailService.getById(Integer.parseInt(parms.get("prop"))));
			bookingDetailService.update(detail);
			msg = new Result("success", ResultCodes.UPDATE_BOOKING_DETAIL);
		} catch (Exception ex) {
			msg = new Result("error", ResultCodes.UPDATE_BOOKING_DETAIL);
		}
		return new ResponseEntity<Result>(msg, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/admin/saveBookingDetail/", method = RequestMethod.POST)
	public ResponseEntity<Result> saveBookingDetail(
			@RequestParam HashMap<String, String> parms) {
		String id = parms.get("id");
		Result msg = null;
		try {
			BookingDetail detail = bookingDetailService.getById(Integer.parseInt(id));
			detail.setNote("note");
			bookingDetailService.save(detail);
			msg = new Result("success", ResultCodes.UPDATE_BOOKING_DETAIL);
		} catch (Exception ex) {
			msg = new Result("error", ResultCodes.UPDATE_BOOKING_DETAIL);
		}
		return new ResponseEntity<Result>(msg, HttpStatus.OK);
	}
	@RequestMapping(value="/api/admin/getPropertiesBookedByMember/{id}")
	public Object getProperty(@RequestParam int id)
	{
			return bookingDetailService.getPropertiesBookedByMember(id);
	}

	@RequestMapping(value = "/api/admin/getBookingCount/{projectId}", method = RequestMethod.POST)
	public Object getBookingCount(@PathVariable int projectId) {
		int count = bookingDetailService.getBookingCount(projectId);
		return count;
	}
}
