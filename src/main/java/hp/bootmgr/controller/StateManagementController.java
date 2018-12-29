/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.validators.StateValidator;
import hp.bootmgr.vo.State;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@SuppressWarnings("rawtypes")
public class StateManagementController {

	private static Logger logger = Logger.getLogger(StateManagementController.class);

	@Autowired
	private StateValidator stateValidator;
	
	@Autowired
	private StateManagementService stateManagementService;
	
	@RequestMapping(value="admin/addState", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addState(HttpSession session, @Valid @ModelAttribute("stateModel") State state, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", stateManagementService.save(state));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@InitBinder
	private void InitBinder(WebDataBinder binder) {
		binder.setValidator(stateValidator );	
	}

	@RequestMapping("admin/deleteState")
	public ResponseEntity deleteState(HttpSession session,@RequestParam("id") int id) {
		State state=stateManagementService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			stateManagementService.delete(state);
		} catch(DataIntegrityViolationException ex) {
		
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping("admin/updateState")
	public ResponseEntity updateState(@RequestParam("stateName") String name, @RequestParam("stateID") int id) {
		State target = stateManagementService.getById(id);
		target.setStateName(name);
		return new ResponseEntity(stateManagementService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "admin/importStates", method = RequestMethod.POST)
	public ResponseEntity importStateData(@RequestParam("file") MultipartFile file, HttpSession session) {
		HttpStatus status = HttpStatus.OK;
		if(!file.isEmpty()) {
			logger.info("Incoming File: " + file.getOriginalFilename() + " (" + file.getContentType() + ") size: " + file.getSize());
			try {
				Sheet sheet = WorkbookFactory.create(file.getInputStream()).getSheetAt(0);
				for(Row row : sheet) {
					for(Cell cell : row) {
						State state = new State();
						state.setStateName(cell.getStringCellValue());
						stateManagementService.save(state);
					}
				}
				logger.debug("Successfully imported file...");
			} catch (IOException | InvalidFormatException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
		}
		return new ResponseEntity(status);
	}
}
