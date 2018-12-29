/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hp.bootmgr.binders.InquiryFormBinder;
import hp.bootmgr.constants.ExcelImport;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.InquiryService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.validators.InquiryValidator;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.Inquiry;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectInquiry;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Win
 *
 */
@Controller
@SuppressWarnings("rawtypes")
public class InquiryController {
	  
	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmployeeManagementService employeeManagementService;
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	private static Logger logger = Logger.getLogger(InquiryController.class);

	@RequestMapping(value = "admin/addInquiry", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addState(HttpSession session, @ModelAttribute("inquiryModel") Inquiry inquiry) {
		session.setAttribute("status", inquiryService.save(inquiry));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = {"admin/deleteInquiry", "user/deleteInquiry"}, method = RequestMethod.POST)
	public ResponseEntity deleteInquiry(@RequestParam("id") int id) {
		return new ResponseEntity(inquiryService.deleteById(id) ? HttpStatus.OK	: HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "admin/updateInquiryDetail")
	public ResponseEntity updateState(
			@ModelAttribute("updateInquiryModel") Inquiry name) {
		return new ResponseEntity(inquiryService.update(name) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@RequestMapping(value={"admin/addProjectInquiry", "user/addProjectInquiry"}, method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addInquiry( HttpSession session, @Valid @ModelAttribute("inquiryModel")InquiryFormBinder formBinder, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				if(error.getField().equals("inquiry.visitDate"))
					sb.append("Please enter valid Date");
				else
					sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		Inquiry inquiry = formBinder.getInquiry();
		
		/**
		 * Client side request for Project Inquiry comes like
		 * 		2,1,true ,true,...
		 * in following format
		 * 		<Project, PropertyType, interested, sample_house_shown>
		 * 
		 */
		String arr[] = formBinder.getStr().split(",");
		int numOfInquiries = arr.length / 4;
		logger.debug("Inquiries [RAW DATA]: " + numOfInquiries);
		List<ProjectInquiry> projectInquiries = new ArrayList<ProjectInquiry>();
		int counter = -1;
		ProjectInquiry ref = new ProjectInquiry();
		for(int i=0; i < arr.length; i++) {
			counter = (counter == 3) ? 0 : (counter + 1);
			switch(counter) {
			case 0:
				ref = new ProjectInquiry();
				ref.setProject(projectService.getById(Integer.parseInt(arr[i])));
				break;
			case 1:
				ref.setPropertyType(propertyTypeProviderService.getPropertyTypeById(Integer.parseInt(arr[i])));
				break;
			case 2:
				ref.setInterested(Boolean.parseBoolean(arr[i]));
				break;
			case 3:
				ref.setShowSampleHouse(Boolean.parseBoolean(arr[i]));
				ref.setInquiry(inquiry);
				projectInquiries.add(ref);
				break;
			}
		}
		inquiry.setProjectInquiries(projectInquiries);
		inquiryService.save(inquiry);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = {"admin/importInquiryData", "user/importInquiryData"})
    public ResponseEntity importInquiryData(@RequestParam("file") MultipartFile file) {
        HttpStatus status = HttpStatus.OK;
        try {
            if (!file.isEmpty()) {
                logger.info("Incoming File: " + file.getOriginalFilename() + " (" + file.getContentType() + ") size: " + file.getSize() / 1024 + " KB");
                Sheet sheet = WorkbookFactory.create(file.getInputStream()).getSheetAt(0);
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);

                    Inquiry inquiry = new Inquiry();

                    // TODO: Merge more than one project inquiry into single inquiry record
                    List<ProjectInquiry> inquiries = new ArrayList<>();
                    ProjectInquiry projectInquiry = new ProjectInquiry();
                    projectInquiry.setInquiry(inquiry);
                    inquiries.add(projectInquiry);
                    inquiry.setProjectInquiries(inquiries);

                    for(int j = ExcelImport.INQUIRY_TABLE_START; j <= ExcelImport.INQUIRY_TABLE_END; j++) {
                        Cell cell = row.getCell(j);
                        int cellType = cell.getCellType();
                        switch (j) {
                            case ExcelImport.INQUIRY_PROJECT_NAME:
                                inquiry.setVisitedSite(projectService.getProjectByName(cell.getStringCellValue()));
                                break;
                            case ExcelImport.INQUIRY_DATE:
                                inquiry.setVisitDate(cell.getDateCellValue());
                                break;
                            case ExcelImport.INQUIRY_IN_TIME:
                                Date inTime = cell.getDateCellValue();
                                inquiry.setIntimeHour(inTime.getHours());
                                inquiry.setIntimeMinute(inTime.getMinutes());
                                break;
                            case ExcelImport.INQUIRY_OUT_TIME:
                                Date outTime = cell.getDateCellValue();
                                inquiry.setOuttimeHour(outTime.getHours());
                                inquiry.setOuttimeMinute(outTime.getMinutes());
                                break;
                            case ExcelImport.INQUIRY_VISITOR_NAME:
                                inquiry.setVisitorName(cell.getStringCellValue());
                                break;
                            case ExcelImport.INQUIRY_REFERENCED_BY:
                                inquiry.setReferenceBy(cell.getStringCellValue());
                                break;
                            case ExcelImport.INQUIRY_ATTENDEE:
                                inquiry.setAttendee(employeeManagementService.getByUserName(cell.getStringCellValue()));
                                break;
                            case ExcelImport.INQUIRY_AREA_CITY:
                                inquiry.setAreaOrCity(cell.getStringCellValue());
                                break;
                            case ExcelImport.INQUIRY_VISITOR_NUMBER:
                                inquiry.setContactNumber(String.valueOf(cell.getNumericCellValue()).replaceAll("[^\\d]", ""));
                                break;
                            case ExcelImport.INQUIRY_VISITOR_EMAIL:
                                inquiry.setEmail(cell.getStringCellValue());
                                break;
                            case ExcelImport.INQUIRY_FOR:
                                projectInquiry.setProject(projectService.getProjectByName(cell.getStringCellValue()));
                                break;
                            case ExcelImport.INQUIRY_SAMPLE_HOUSE_SHOWN:
                                projectInquiry.setShowSampleHouse(cell.getStringCellValue().equalsIgnoreCase("YES"));
                                break;
                            case ExcelImport.INQUIRY_INTERESTED:
                                projectInquiry.setInterested(cell.getStringCellValue().equalsIgnoreCase("YES"));
                                break;
                            case ExcelImport.INQUIRY_TYPE:
                                // TODO: Add inquiry type support
                                //inquiry.setInquiryType();
                                break;
                            case ExcelImport.INQUIRY_REMARKS:
                                inquiry.setRemark(cell.getStringCellValue());
                                break;
                        }
                    }
                    inquiryService.save(inquiry);
                }
                logger.debug("Successfully loaded all inquiries...!");
            }
        } catch (InvalidFormatException ex) {
            logger.error("Invalid file format...!!!");
            ex.printStackTrace();
        } catch (IOException ex) {
            logger.error("Failed to upload file...!!!");
            ex.printStackTrace();
        }
        return new ResponseEntity(status);
    }

	
	/**
	 * fetchInquiries(): Handles the DataTable requests and sends back appropriate response
	 * in JSON format known by DataTable
	 * 
	 * Refer: https://www.datatables.net/examples/data_sources/server_side.html
	 * 
	 */
	@RequestMapping(value={"admin/fetchInquiries", "user/fetchInquiries"})
	private @ResponseBody
	String fetchInquiries(@RequestParam int draw, @RequestParam int start, @RequestParam int length, @RequestParam("search[value]") String search) {
		logger.debug("DataTable: Inquiry: draw->" + draw + " start->" + start + " length->" + length + " search->" + search);
		List<Inquiry> ret = inquiryService.getFilteredInquiries(start, length, search);
		
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode rootNode = factory.objectNode();
		rootNode.put("draw", draw);
		rootNode.put("recordsTotal", inquiryService.getAll().size());
		rootNode.put("recordsFiltered", inquiryService.getAll().size());

		ArrayNode arraynode = factory.arrayNode();
		rootNode.set("data", arraynode);
		for(Inquiry inquiry : ret) {
			ArrayNode inqArr = factory.arrayNode();
			inqArr.add(inquiry.getId());
			inqArr.add(inquiry.getVisitedSite().getName());
			inqArr.add(inquiry.getVisitorName());
			inqArr.add(inquiry.getVisitDate().toString());
			inqArr.add(inquiry.getContactNumber());
			inqArr.add(inquiry.getRemark());
			// TODO: Follow MVC, let the client handle those views
			inqArr.add("<a class='creativeButton squareYellowButton editButton ct_edit_group' onclick='onViewInquiry(" + inquiry.getId() + ");' ></a><a class='creativeButton squareRedButton closeButton' onClick='javascript: return onDeleteClick(" + inquiry.getId() + ");'></a>");
			arraynode.add(inqArr);
		}
		return rootNode.toString();
	}

	private class EmployeeManagementValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeManagementService.getById(Integer.parseInt(text)));
		}
	}  

	private class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}

	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.registerCustomEditor(Employee.class,	new EmployeeManagementValueBinder());
		binder.setValidator(new InquiryValidator());
	}

}
