package hp.bootmgr.controller;

import hp.bootmgr.binders.BookingMemberDetailBinder;
import hp.bootmgr.constants.ExcelImport;
import hp.bootmgr.services.*;
import hp.bootmgr.validators.BookingDetailValidator;
import hp.bootmgr.vo.*;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SuppressWarnings({"rawtypes","unused"})
public class BookingDetailController {

    private static Logger logger = Logger.getLogger(BookingDetailController.class);

	@Autowired
	private BookingDetailService bookingDetailService;
	
	@Autowired
	private EmployeeManagementService employeeManagementService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PropertyDetailService propertyDetailService;

    @Autowired
    private EmployeeRoleService employeeRoleService;

	@RequestMapping(value = {"admin/addBookingDetail", "user/addBookingDetail"}, method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addState(HttpSession session, @RequestParam("isAlreadyRegistered") boolean isAlreadyRegistered, @Valid @ModelAttribute("bookingDetailModel") BookingMemberDetailBinder bookingMemberDetailBinder, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else  {
            BookingDetail bookingDetail = bookingMemberDetailBinder.getBookingDetail();
            Employee member = bookingMemberDetailBinder.getEmployee();
            if(!isAlreadyRegistered) {
                // If member is not registered, register it first
                member.setRole(employeeRoleService.getRoleByName("ROLE_MEMBER"));
                employeeManagementService.save(member);
                bookingDetail.setMemberDetail(member);
            }

            // copy current time
            Date enteredDate = bookingDetail.getBookingDate();
            Date now = new Date();
            enteredDate.setHours(now.getHours());
            enteredDate.setMinutes(now.getMinutes());
            enteredDate.setSeconds(now.getSeconds());

            // Initialize payment data
			int noPhasesOfPayment = bookingDetail.getPropertyDetail().getBlock().getProgress().size();
			for(int i=0; i < noPhasesOfPayment; i++) {
				bookingDetail.getPaymentData().add(false);
			}
			bookingDetailService.save(bookingDetail);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = {"admin/importBookingDetail", "user/importBookingDetail"}, method = RequestMethod.POST)
    public ResponseEntity importBookingDetailData(@RequestParam("file") MultipartFile file, HttpSession session) {
        HttpStatus status = HttpStatus.OK;
        if(!file.isEmpty()) {
            logger.info("Incoming File: " + file.getOriginalFilename() + " (" + file.getContentType() + ") size: " + file.getSize() / 1024 + " KB");
            try {
                Sheet sheet = WorkbookFactory.create(file.getInputStream()).getSheetAt(ExcelImport.SHEET_BOOKING_DETAIL_MEMBER_DETAIL);

                EmployeeRole memberRole = employeeRoleService.getRoleByName("ROLE_MEMBER");

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                // fetch project name first
                CellReference reference = new CellReference("C1");
                Row refRow = sheet.getRow(reference.getRow());
                Cell refCell = refRow.getCell(reference.getCol());
                Project project = projectService.getProjectByName(refCell.getStringCellValue());
                if(project == null) {
                    logger.error("Invalid project name...!!!");
                    session.setAttribute("errorMsg", "Invaild Project name, please specify correct project name in cell 'C1'...!!");
                }

                loopOnRow:
                for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);

                    BookingDetail bookingDetail = new BookingDetail();
                    Employee employee = new Employee();
                    MemberDetail memberDetail = new MemberDetail();
                    employee.setDetails(memberDetail);
                    employee.setRole(memberRole);

                    // set project
                    bookingDetail.setProject(project);
                    bookingDetail.setMemberDetail(employee);

                    for(int j = 0; j <= ExcelImport.BOOKING_DETAIL_TABLE_END; j++) {
                        Cell cell = row.getCell(j);
                        String blockName = null;
                        if(cell == null) continue;
                        switch (j) {
                            case ExcelImport.BOOKING_FLAT_NUMBER:
                                String val = cell.getStringCellValue();
                                blockName = val;
                                employee.setUsername(val);
                                employee.setPassword(val);
                                PropertyDetail propertyDetail = propertyDetailService.getPropertyDetailByQualifiedName(project, val);
                                if(propertyDetail == null) {
                                    logger.debug("Couldn't find requested property: " + val);
                                    continue loopOnRow;
                                }
                                bookingDetail.setPropertyDetail(propertyDetail);
                                break;
                            case ExcelImport.BOOKING_DATE:
                                if(cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                                    logger.debug("Not entering " + (i + 1) + " row");
                                    continue loopOnRow;
                                }
                                if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell))
                                    bookingDetail.setBookingDate(cell.getDateCellValue());
                                else {
                                    try {
                                        bookingDetail.setBookingDate(format.parse(cell.getStringCellValue()));
                                    } catch (ParseException ex) {
                                        logger.error("Invalid Date: " + cell.getStringCellValue());
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case ExcelImport.BOOKING_MEMBER_NAME:
                                employee.setFirstName(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_BOOKED_BY:
                                bookingDetail.setBookedByEmployee(employeeManagementService.getByUserName(cell.getStringCellValue()));
                                break;
                            case ExcelImport.BOOKING_NOTE:
                                bookingDetail.setNote(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_AGE:
                                memberDetail.setAge(String.valueOf(getValue(cell)));
                                break;
                            case ExcelImport.BOOKING_MEMBER_ADDRESS:
                                employee.setAddress1(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_OFFICE_ADDRESS:
                                employee.setAddress2(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_CONTACT_NO1:
                                employee.setPhone(String.valueOf(getValue(cell)).replaceAll("[^\\d]", ""));
                                //employee.setPhone(String.valueOf(getValue(cell)));
                                break;
                            case ExcelImport.BOOKING_MEMBER_CONTACT_NO2:
                                employee.setMobileNo(String.valueOf(getValue(cell)).replaceAll("[^\\d]", ""));
                                //employee.setMobileNo(String.valueOf(getValue(cell)));
                                break;
                            case ExcelImport.BOOKING_MEMBER_CONTACT_NO3:
                                memberDetail.setContactNo1(String.valueOf(getValue(cell)).replaceAll("[^\\d]", ""));
                                //memberDetail.setContactNo1(String.valueOf(getValue(cell)));
                                break;
                            case ExcelImport.BOOKING_MEMBER_EMAIL:
                                String email = cell.getStringCellValue();
                                employee.setEmail(email.equals("") ? blockName : email);
                                break;
                            case ExcelImport.BOOKING_MEMBER_PROFESSION:
                                memberDetail.setProfession(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_PAN_NO:
                                memberDetail.setPANNumber(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_DOB:
                                if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell))
                                    memberDetail.setDateOfBirth(format.format(cell.getDateCellValue()));
                                else
                                    memberDetail.setDateOfBirth(cell.getStringCellValue());
                                break;
                            case ExcelImport.BOOKING_MEMBER_ANNIVERSARY_DATE:
                                if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell))
                                    memberDetail.setMarriageAnniversaryDate(format.format(cell.getDateCellValue()));
                                else
                                    memberDetail.setMarriageAnniversaryDate(cell.getStringCellValue());
                                break;
                        }
                    }
                    employeeManagementService.save(employee);
                    bookingDetailService.save(bookingDetail);
                }
            } catch (InvalidFormatException e) {
                session.setAttribute("errorMsg", "Invalid file format...!!");
                logger.error("Invaild format..!!!");
                e.printStackTrace();
            } catch (IOException e) {
                session.setAttribute("errorMsg", "File read error, please try again...!");
                logger.error("File read error..!!!");
                e.printStackTrace();
            }
        }
        return new ResponseEntity(status);
    }

    private Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case XSSFCell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case XSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
        }
        return null;
    }
	
	@RequestMapping(value={"admin/deleteBookingDetail", "user/deleteBookingDetail"})
	public ResponseEntity deleteState(@RequestParam("id") int id) {
		return new ResponseEntity(bookingDetailService.deleteById(id) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = {"admin/updateBookingDetail", "user/updateBookingDetail"})
	public ResponseEntity updateState(
			@ModelAttribute("updateBookingDetailModel") BookingDetail bookingDetail) {
			return new ResponseEntity(bookingDetailService.update(bookingDetail) ? HttpStatus.OK
				: HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = {"admin/getBookingDetail/{id}", "member/getBookingDetail/{id}"})
	public @ResponseBody
	BookingDetail getBookingDetail(@PathVariable Integer id) {
		if(id != null) {
			return bookingDetailService.getById(id);
		}
		return null;
	}
	
	@RequestMapping(value = {"admin/getPropertiesBookedByMember/{member_id}", "user/getPropertiesBookedByMember/{member_id}"})
	public @ResponseBody 
	List<BookingDetail> getPropertiesBookedByMember(@PathVariable("member_id") Integer member_id) {
		return bookingDetailService.getPropertiesBookedByMember(member_id);
	}
	

	public class projectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	public class propertyDetailValueBinder extends PropertyEditorSupport
	{
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(propertyDetailService.getById(Integer.parseInt(text)));
		}
		
		
	}

	public class employeeValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeManagementService.getById(Integer.parseInt(text)));

		}
	}
	
	public class propertyValueBinder extends PropertyEditorSupport
	{	
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(propertyDetailService.getById(Integer.parseInt(text)));
		}
	}

	public class memberValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeManagementService.getById(Integer.parseInt(text)).getDetails());
		}
	}

	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Project.class, new projectValueBinder());
		binder.registerCustomEditor(Employee.class, new employeeValueBinder());
		binder.registerCustomEditor(MemberDetail.class,new memberValueBinder());
		binder.registerCustomEditor(PropertyDetail.class, new propertyValueBinder());
		//binder.setValidator(new BookingDetailValidator());

	}
}
