package hp.bootmgr.validators;

import java.util.List;

import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.vo.ProjectPropertyBlock;
import hp.bootmgr.vo.State;
import hp.bootmgr.web.services.ResultCodes;
import hp.bootmgr.web.services.responses.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectPropertyBlock {

	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@RequestMapping(value = "/api/admin/projectpropertyblock", method = RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyBlock>> projectpropertyblock() {
		List<ProjectPropertyBlock> projectPropertyBlocks = projectPropertyBlockService.getAll();
		if (projectPropertyBlocks.isEmpty()) {
			return new ResponseEntity<List<ProjectPropertyBlock>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectPropertyBlock>>(projectPropertyBlocks, HttpStatus.OK);
	}
	@RequestMapping(value="/api/admin/projectpropertyblock/{id}",method=RequestMethod.POST)
		public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",ResultCodes.INVALID_REQUEST);
		}
		ProjectPropertyBlock ret = projectPropertyBlockService.getById(idi);
		return ret == null ? new Result("State not found",ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/deleteprojectpropertyblock/{id}", method = RequestMethod.DELETE)
	public Object deleteById(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",	ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = projectPropertyBlockService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("State not found", ResultCodes.STATE_NOT_FOUND);
	}
	
}
