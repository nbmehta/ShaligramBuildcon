package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.vo.MeasurementUnit;


import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMeasurementUnitApi {
	
	
	@Autowired
	private  MeasurementUnitService measurementUnitService;
	
	@RequestMapping(value = "/api/admin/getMeasurement", method = RequestMethod.POST)
	public ResponseEntity<List<MeasurementUnit>> listAllState() {
		List<MeasurementUnit> measurementUnits = measurementUnitService.getAll();
		if(measurementUnits.isEmpty()){
			return new ResponseEntity<List<MeasurementUnit>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MeasurementUnit>>(measurementUnits, HttpStatus.OK);
	}
	@RequestMapping(value="/api/admin/getMeasurement/{id}",method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		MeasurementUnit ret = measurementUnitService.getById(idi);
		return ret == null ? new Result("Measurement Unit not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/deleteMeasurement/{id}", method = RequestMethod.DELETE)
	public Object deleteById(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = measurementUnitService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Measurement Unit not found", ResultCodes.STATE_NOT_FOUND);
	}
	@RequestMapping(value = "/api/admin/deleteMeasurement/", method = RequestMethod.POST)
	public Boolean deleteMeasurement() {
		return true;
	}
	@RequestMapping(value = "/api/admin/updateMeasurement/", method = RequestMethod.POST)
	public Boolean updateMeasurement() {
		return true;
	}
	@RequestMapping(value = "/api/admin/saveMeasurement/", method = RequestMethod.POST)
	public Boolean saveMeasurement() {
		return true;
	}
	@RequestMapping(value = "/api/admin/isAlreadyMeasurement/", method = RequestMethod.POST)
	public Boolean  isAlreadyMeasurement() {
		return true;
	}
 
}
