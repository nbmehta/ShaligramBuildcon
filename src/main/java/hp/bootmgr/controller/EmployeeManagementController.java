
package hp.bootmgr.controller;

import com.mysql.jdbc.MysqlErrorNumbers;
import hp.bootmgr.authentication.provider.CustomUserDetailImpl;
import hp.bootmgr.binders.PasswordBinder;
import hp.bootmgr.constants.ExcelImport;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.validators.ManageEmployeeValidator;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.EmployeeRole;
import hp.bootmgr.vo.State;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

@Controller
@SuppressWarnings("rawtypes")
public class EmployeeManagementController {

	@Autowired
	private static Logger logger = Logger.getLogger(EmployeeManagementController.class);

	@Autowired
	private EmployeeManagementService employeeManagementService;

	@Autowired
	private StateManagementService stateManagementService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@RequestMapping(value="admin/addEmployee")
	public ResponseEntity<HttpStatus> addState(HttpSession session, @Valid @ModelAttribute("newUser") Employee employee, BindingResult result)
	 {
		logger.debug(employee);
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else {
			try {
				employeeManagementService.save(employee);
			} catch (DataIntegrityViolationException ex) {
				Throwable throwable = ex.getCause();
				if(throwable instanceof ConstraintViolationException) {
					ConstraintViolationException violationException = (ConstraintViolationException) throwable;
					String constraintName = violationException.getConstraintName();
					logger.error("ConstraintViolation:  Constraint: " + constraintName + " code: " + violationException.getErrorCode());
					switch (violationException.getErrorCode()) {
						case MysqlErrorNumbers.ER_DUP_ENTRY:
							if (constraintName.equals("UNIQUE_EMAIL"))
								session.setAttribute("errorMsg", "Error: Email id is already registered...!!!");
							else if (constraintName.equals("UNIQUE_USERNAME"))
								session.setAttribute("errorMsg", "Error: Username is already registered...!!!");
					}
				}
				ex.printStackTrace();
			}
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value="admin/deleteEmployee", method=RequestMethod.POST)
	public ResponseEntity deleteEmployee(@RequestParam("id") int id, HttpSession session) {
		logger.debug("Request -> Delete -> Employee -> " + id);
		HttpStatus status = HttpStatus.OK;
		try {
			employeeManagementService.deleteById(id);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for id = [" + id + "]");
			session.setAttribute("errorMsg", "You can not delete this data because it is being referenced by other records. Please delete all relevant data before deleting this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}

	@RequestMapping(value="admin/updateEmployee", method=RequestMethod.POST)
	public ResponseEntity updateEmployee(@ModelAttribute("newUser") Employee employee) {
		logger.debug("Request -> Update -> Employee -> " + employee.getId());
		return new ResponseEntity(employeeManagementService.update(employee) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value={"admin/doChangePassword", "user/doChangePassword", "member/doChangePassword"}, method=RequestMethod.POST)
	public ResponseEntity setNewPassword(@ModelAttribute("changePasswordModel") PasswordBinder pwd) {
		Employee employee = ((CustomUserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployee();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(employee.getPassword().equals(pwd.getOldPassword())) {
			employee.setPassword(pwd.getNewPassword());
			employeeManagementService.mergeChanges(employee);
			status = HttpStatus.OK;
		}
		return new ResponseEntity(status);
	}

	@RequestMapping(value = "admin/importEmployees", method = RequestMethod.POST)
	public ResponseEntity importEmployeeData(@RequestParam("file") MultipartFile file) {
		HttpStatus status = HttpStatus.OK;
		if (!file.isEmpty()) {
			logger.info("Incoming File: " + file.getOriginalFilename() + " (" + file.getContentType() + ") size: " + file.getSize());
			try {
				Sheet sheet = WorkbookFactory.create(file.getInputStream()).getSheetAt(0);
				for (Row row : sheet) {
					Employee employee = new Employee();
                    employee.setRole(employeeRoleService.getRoleByName("ROLE_EMPLOYEE"));
					for (int i = 0; i <= ExcelImport.EMPLOYEE_TABLE_END; i++) {
						Cell cell = row.getCell(i);
                        int cellType = cell.getCellType();
						Object data = cellType == HSSFCell.CELL_TYPE_STRING ? cell.getStringCellValue() : cellType == HSSFCell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue() : cell.getBooleanCellValue();
						switch (i) {
                            case ExcelImport.EMPLOYEE_FIRST_NAME:
                                employee.setFirstName((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_MIDDLE_NAME:
                                employee.setMiddleName((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_LAST_NAME:
                                employee.setLastName((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_ACTIVE:
                                employee.setActive((boolean) data);
                                break;
                            case ExcelImport.EMPLOYEE_ADDRESS_1:
                                employee.setAddress1((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_ADDRESS_2:
                                employee.setAddress2((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_CITY:
                                employee.setCity((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_EMAIL:
                                employee.setEmail((String) data);
                                break;
                            case ExcelImport.EMPLOYEE_MOBILE_NO:
                                String num = String.valueOf(data).replaceAll("[^\\d]", "");
                                employee.setMobileNo(num);
                                // TODO: Generate and email password
                                employee.setPassword(num);
                                break;
                            case ExcelImport.EMPLOYEE_PHONE_NO:
                                employee.setPhone(String.valueOf(data).replaceAll("[^\\d]", ""));
                                break;
                            case ExcelImport.EMPLOYEE_STATE:
                                employee.setState(stateManagementService.getStateByName((String) data));
                                break;
                            case ExcelImport.EMPLOYEE_USERNAME:
                                employee.setUsername((String) data);
                                break;
                        }
					}
                    employeeManagementService.save(employee);
				}
				logger.debug("Successfully imported file...");
			} catch (IOException | InvalidFormatException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
		}
		return new ResponseEntity(status);
	}

	@InitBinder(value="newUser")
	private void InitBinder(WebDataBinder binder){
		binder.setValidator(new ManageEmployeeValidator());
		binder.registerCustomEditor(EmployeeRole.class, new EmployeeRoleValueBinder());
		binder.registerCustomEditor(State.class, new StateValueBinder());
	}

	private class StateValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(stateManagementService.getById(Integer.parseInt(text)));
		}
	}

	private class EmployeeRoleValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeRoleService.getById(Integer.parseInt(text)));
		}
	}

}
