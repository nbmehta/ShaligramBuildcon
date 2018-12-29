package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.vo.PropertyType;

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
public class GetPropertyTypeApi {
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@RequestMapping(value = "/api/admin/getPropertyType", method = RequestMethod.POST)
	public ResponseEntity<List<PropertyType>> listAllState() {
		List<PropertyType> propertyTypes = propertyTypeProviderService.getPropertyTypes();
		if(propertyTypes.isEmpty()){
			return new ResponseEntity<List<PropertyType>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<PropertyType>>(propertyTypes, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/getPropertyType/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		PropertyType ret = propertyTypeProviderService.getPropertyTypeById(idi);
		return ret == null ? new Result("Property type not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/updatepropetytype/", method = RequestMethod.POST)
	public Result updatestate(@RequestParam("name") String name, @RequestParam("propertyTypeID") int id) {
		try {
			PropertyType ns =propertyTypeProviderService.getPropertyTypeById(id);
			ns.setName(name);
			Boolean b = true;
			if (propertyTypeProviderService.isAlreadyAvailable(name) == false) {
				ns.setName(name);
				if (propertyTypeProviderService.updatePropertyType(ns) == true) {
					b = true;
					return new Result("successfull added ", 0);
				}

			} else {
				b = false;
				return new Result("Already Exist", ResultCodes.ALREADY_EXITS);
			}
			return new Result("Unknown Error", ResultCodes.ERROR);
		} catch (NullPointerException ex) {
			return new Result("Id not present", -7);
		}
	}

	@RequestMapping(value = "/api/admin/deletePropertyType/{id}", method = RequestMethod.DELETE)
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
			ret = propertyTypeProviderService.deletePropertyType(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Property Type not found", ResultCodes.STATE_NOT_FOUND);
	}
	
	
}
