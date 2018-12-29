package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.vo.ProjectPropertyBlock;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectPropertyBlockApi {

	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@RequestMapping(value = "/api/admin/projectPropertyBlock", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectPropertyBlock >> listAllPropertyBlock() {
		List<ProjectPropertyBlock> propertyDetails = projectPropertyBlockService.getAll();
		if(propertyDetails.isEmpty()){
			return new ResponseEntity<List<ProjectPropertyBlock>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProjectPropertyBlock>>(propertyDetails, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/projectPropertyBlock/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		ProjectPropertyBlock ret = projectPropertyBlockService.getById(idi);
		return ret == null ? new Result("Property not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
}
