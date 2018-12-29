/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hp.bootmgr.authentication.provider.CustomUserDetailImpl;
import hp.bootmgr.binders.BookingMemberDetailBinder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import hp.bootmgr.binders.InquiryFormBinder;
import hp.bootmgr.binders.PasswordBinder;
import hp.bootmgr.binders.ProjectFormBinder;
import hp.bootmgr.services.BookingDetailService;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.services.InquiryService;
import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.services.ParkingBookingDetailService;
import hp.bootmgr.services.ProjectExtraParkingService;
import hp.bootmgr.services.ProjectInquiryService;
import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.services.ProjectPropertyTypeService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.services.ProjectTypeService;
import hp.bootmgr.services.PropertyDetailService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.vo.BookingDetail;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.EmployeeRole;
import hp.bootmgr.vo.Inquiry;
import hp.bootmgr.vo.MeasurementUnit;
import hp.bootmgr.vo.ParkingBookingDetail;
import hp.bootmgr.vo.PaymentPlan;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectExtraParking;
import hp.bootmgr.vo.ProjectInquiry;
import hp.bootmgr.vo.ProjectPropertyBlock;
import hp.bootmgr.vo.ProjectPropertyPlan;
import hp.bootmgr.vo.ProjectPropertyType;
import hp.bootmgr.vo.ProjectStatus;
import hp.bootmgr.vo.ProjectType;
import hp.bootmgr.vo.PropertyDetail;
import hp.bootmgr.vo.PropertyType;
import hp.bootmgr.vo.State;

@Controller
@SessionAttributes({"updateMemberDetailModel", "projectUpdateModel"})
public class FragmentProvider {

	private static Logger logger = Logger.getLogger(FragmentProvider.class);

	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;

	@Autowired
	private ProjectTypeService projectTypeService;
	@Autowired
	private ParkingBookingDetailService parkingBookingDetailService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private StateManagementService stateManagementService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	private ProjectStatusProviderService projectStatusService;

	@Autowired
	private MeasurementUnitService measurementUnitService;

	@Autowired
	private EmployeeManagementService employeeManagementService;
	
	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;

	@Autowired
	private ProjectPropertyTypeService projectPropertyTypeService;

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private ProjectInquiryService projectInquiryService;

	@Autowired
	private ProjectExtraParkingService projectExtraParkingService;

	@Autowired
	private PropertyDetailService propertyDetailService;

	@Autowired
	private ProjectPropertyPlanService projectPropertyPlanService;

	@Autowired
	private BookingDetailService bookingDetailService;

	@RequestMapping("admin/fragment/fragement_add_property_type")
	public String getAddPropertyTypeView(ModelMap model) {
		model.addAttribute("propType", new PropertyType());
		model.addAttribute("types",	propertyTypeProviderService.getPropertyTypes());
		return "admin/fragments/manage_property_type";
	}

	@RequestMapping("admin/fragment/fragement_manage_property_detail")
	public String getAddPropertyBuildTypeView(ModelMap model) {
		model.addAttribute("propertyDetail", new PropertyDetail());
		List<Project> prjects = projectService.getAll();
		model.addAttribute("projects", prjects);
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		if (prjects != null && prjects.size() > 0) {
			List<ProjectPropertyBlock> blocks = prjects.get(0).getBlocks();
			model.addAttribute("blockTypes", prjects.get(0).getBlocks());
			if (blocks != null && blocks.size() > 0) {
                // TODO: Bidirectional Mapping???
                model.addAttribute("plans", projectPropertyPlanService.getPlansForBlock(blocks.get(0).getBlockId()));
            }
		}
		model.addAttribute("measurements", measurementUnitService.getAll());
		model.addAttribute("propDetails", propertyDetailService.getAll());
		return "admin/fragments/manage_property_detail";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_type")
	public String getManageProjectTypeView(ModelMap model) {
		model.addAttribute("projectTypes", projectTypeService.getAll());
		model.addAttribute("projectTypeModel", new ProjectType());
		return "admin/fragments/manage_project_type";
	}
	

	@RequestMapping("admin/fragment/fragement_manage_project")
	public String getManageProject(ModelMap model) {
		// model.addAttribute("projectModel", new Project());
		logger.debug("getManageProject() -> project size: "	+ projectService.getAll().size());
		model.addAttribute("projectModel", new ProjectFormBinder());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("projectTypes", projectTypeService.getAll());
		model.addAttribute("states", stateManagementService.getAll());
		model.addAttribute("projectstatuses", projectStatusService.getProjectStatus());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		return "admin/fragments/manage_project";
	}

	@RequestMapping("admin/fragment/fragement_manage_employee")
	public String getManageEmployee(ModelMap model) {
		model.addAttribute("employees", employeeManagementService.getAll());
		return "admin/fragments/manage_employee";
	}
	
	@RequestMapping(value={"admin/changePassword", "user/changePassword", "member/changePassword"})
	public String getChangePassword(ModelMap model) {
		model.addAttribute("changePasswordModel", new PasswordBinder());
		return "user/fragments/changePassword";
	}
	
	@RequestMapping("admin/fragment/fragment_view_member_detail")
	public String getViewMemberDetail(ModelMap model,@RequestParam(value = "id") Integer id){
		model.addAttribute("members", employeeManagementService.getById(id));
		return "admin/fragments/view_member_detail";	
	}
	
	@RequestMapping("admin/fragment/fragment_view_property_detail")
	public String getViewPropertyDetail(ModelMap model,@RequestParam(value = "id") Integer id){
		model.addAttribute("propertyDetail", new PropertyDetail());
		model.addAttribute("propDetails", propertyDetailService.getById(id));
		model.addAttribute("measurements", measurementUnitService.getById(id));
		model.addAttribute("propDetails", propertyDetailService.getById(id));
		return "admin/fragments/view_property_detail";
	}

	@RequestMapping("admin/fragment/fragement_manage_employee_role")
	public String getManageEmployeeRole(ModelMap model) {
		model.addAttribute("employeeRoleModel", new EmployeeRole());
		model.addAttribute("emplrole", employeeRoleService.getAll());
		return "admin/fragments/manage_employee_role";
	}

	@RequestMapping("admin/fragment/fragement_manage_measurement")
	public String getManageMeasurement(ModelMap model) {
		model.addAttribute("measurementModel", new MeasurementUnit());
		model.addAttribute("measurements", measurementUnitService.getAll());
		return "admin/fragments/manage_measurement";
		
	}

	@RequestMapping("admin/fragment/fragement_manage_member_detail")
	public String getManageMemberDetail(ModelMap model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("members", employeeManagementService.getAllMembers());
		return "admin/fragments/manage_member_detail";
	}

	@RequestMapping(value={"admin/fragment/fragement_view_inquiry", "user/fragment/fragement_view_inquiry"})
	public String getViewInquiry(ModelMap model) {
		return "user/fragments/view_inquiry";
	}
	@RequestMapping(value={"admin/fragment/fragement_manage_booking_detail_list", "user/fragment/fragement_manage_booking_detail_list"})
	public String getBookingDetail(ModelMap model) {
		model.addAttribute("bookingdetail",bookingDetailService.getAll());
		model.addAttribute("members", employeeManagementService.getAll());
	
		logger.debug(bookingDetailService.getAll());
		System.out.println("hi");
		return "user/fragments/manage_booking_detail_list";
	}

	@RequestMapping(value={"admin/fragment/fragement_view_all_inquiry", "user/fragment/fragement_view_all_inquiry"})
	public String getViewAllInquiry(ModelMap model,	@RequestParam(value = "id") Integer id) {
		model.addAttribute("inquiry", inquiryService.getById(id));
		return "user/fragments/view_all_inquiry";
	}
	
	@RequestMapping(value={"admin/fragment/fragment_book_property", "user/fragment/fragment_book_property"})
	public String getManageBlock(ModelMap model, @RequestParam(value = "prjid") Integer projectId, @RequestParam(value = "blkid") Integer blockId) {
		Project project= projectService.getById(projectId);
		ProjectPropertyBlock selectedBlock = projectPropertyBlockService.getById(blockId);

		// Strings to be sent to client. Tokenized by "," (comma)
		StringBuilder bookingStatus = new StringBuilder();		// either it is booked or not
		StringBuilder pattern = new StringBuilder();			// pattern of graph

		List<PropertyDetail> details = propertyDetailService.getPropertiesByBlock(blockId);
		int size = details.size();
		if(size > 0) {
			details.forEach(propDetail -> {
				bookingStatus.append(propDetail.getBookingDetail() == null ? "false" : "true");
				bookingStatus.append(",");
			});
			
			// remove trailing comma
			bookingStatus.deleteCharAt(bookingStatus.length() - 1);
			
			//int rows = size % 11 == 0 ? size / 11 : (size / 11) + 1;
            int rows = selectedBlock.getNoOfFloor();
			logger.debug("ROWS to draw: " + rows);
			/*
			outer:
			while(rows > 0) {
				int j = 0;
				pattern.append("'");
				while(j < 12 && size > 0) {
					if(j == 11) {
						pattern.append("',");
						rows--;
						continue outer;
					}
					pattern.append("e");
					size--;
					j++;
				}
				pattern.append("',");
				rows--;
			}
			*/
            for(int i=1; i <= rows; i++) {
                boolean isUpdated = false;
                pattern.append("'");
                for(PropertyDetail detail : details) {
                    if(detail.getFloorNumber() == i) {
                        isUpdated = true;
						pattern.append("e[");
						pattern.append(detail.getId());
						pattern.append(",");
						pattern.append(detail.getPropertyNumber());
						pattern.append("]");
					}
                }
                if(!isUpdated) pattern.append("_");
                pattern.append("',");
            }

			// remove trailing comma
			pattern.deleteCharAt(pattern.length() - 1);
		}
		
		// Let's hunt those bugs :D
		logger.debug("bookingStatus: " + bookingStatus.toString());
		logger.debug("size: " + details.size());
		logger.debug("PATTERN: " + pattern.toString());

		// set models
		model.addAttribute("title", project.getName() + " - " + selectedBlock.getBlock());
		model.addAttribute("bookingStatus", bookingStatus.toString());
		model.addAttribute("pattern", pattern.toString());
		return "user/fragments/book_property";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_contact_person")
	public String getManageProjectContactPerson(ModelMap model) {
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("projects", projectService.getAll());
		return "admin/fragments/manage_project_contact_person";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_property_block")
	public String getManageProjectPropertyBlock(ModelMap model) {
		model.addAttribute("propertyBlock", new ProjectPropertyBlock());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("blocks", projectPropertyBlockService.getAll());
		return "admin/fragments/manage_project_property_block";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_property_plan")
	public String getManageProjectPropertyPlan(ModelMap model) {
		List<Project> projects = projectService.getAll();
		model.addAttribute("projects", projects);
		if (projects.size() > 0) {
			//model.addAttribute("blocks", projects.get(0).getBlocks());
			model.addAttribute("propTypes", projects.get(0).getPropertyTypes());
		}
		model.addAttribute("propertyPlan", new ProjectPropertyPlan());
		model.addAttribute("plans", projectPropertyPlanService.getAll());
		return "admin/fragments/manage_project_property_plan";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_inquiry")
	public String getManageProjectInquiry(ModelMap model) {
		model.addAttribute("projectInquiry", new ProjectInquiry());
		model.addAttribute("inquiries", inquiryService.getAll());
		model.addAttribute("projinquiries", projectInquiryService.getAll());
		model.addAttribute("proptypes", projectPropertyTypeService.getAll());
		return "admin/fragments/manage_project_inquiry";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_property_type")
	public String getManageProjectPropertyType(ModelMap model) {
		model.addAttribute("projectPropType", new ProjectPropertyType());
		model.addAttribute("proptypes", projectPropertyTypeService.getAll());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("types",	propertyTypeProviderService.getPropertyTypes());
		return "admin/fragments/manage_project_property_type";
	}

	@RequestMapping("admin/fragment/fragement_manage_projects_status")
	public String getProjectStatus(ModelMap model) {
		model.addAttribute("projectStatuses", new ProjectStatus());
		model.addAttribute("projectStatusModel", new ProjectStatus());
		model.addAttribute("projectstatuses", projectStatusService.getProjectStatus());
		return "admin/fragments/manage_projects_status";
	}

	@RequestMapping(value={"user/fragment/fragement_manage_parking_booking_details", "admin/fragment/fragement_manage_parking_booking_details"})
	public String getPrarkingBookingDetail(ModelMap model) {
		model.addAttribute("parkingModel", new ParkingBookingDetail());
		model.addAttribute("pbookings", parkingBookingDetailService.getAll());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("members", employeeManagementService.getAllMembers());
		model.addAttribute("parkings", projectExtraParkingService.getAll());
		return "user/fragments/manage_parking_booking_details";
	}

	@RequestMapping("admin/fragment/fragement_manage_project_extra_parking")
	public String getManageProjectExtraParking(ModelMap model) {
		model.addAttribute("extraParkingModel", new ProjectExtraParking());
		model.addAttribute("parkings", projectExtraParkingService.getAll());
		model.addAttribute("projects", projectService.getAll());
		return "admin/fragments/manage_project_extra_parking";
	}

	@RequestMapping(value={"admin/fragment/fragement_manage_booking_detail", "user/fragment/fragement_manage_booking_detail"})
	public String getManageBookingDetail(ModelMap model) {
		List<Project> projects = projectService.getAll();
		model.addAttribute("bookingDetailModel", new BookingMemberDetailBinder());
		model.addAttribute("projects", projects);
		model.addAttribute("members", employeeManagementService.getAllMembers());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("bookingDetail", bookingDetailService.getAll());
		return "user/fragments/manage_booking_detail";
	}

	@RequestMapping("admin/fragment/fragement_manage_state")
	public String getManageStateView(ModelMap model) {
		model.addAttribute("stateModel", new State());
		model.addAttribute("states", stateManagementService.getAll());
		return "admin/fragments/manage_state";
	}

	@RequestMapping("admin/fragment/test")
	public String getTestView() {
		return "fragments/test";
	}

	
	@RequestMapping("admin/fragment/fragment_add_employee")
	public String getAddEmployeeView(ModelMap model, @RequestParam(value = "id", required = false) Integer id) {
		logger.debug("getAddEmployeeView() -> " + id);
		model.addAttribute("newUser", id == null ? new Employee() : employeeManagementService.getById(id));
		model.addAttribute("roles", employeeRoleService.getAll());
		model.addAttribute("states", stateManagementService.getAll());
		model.addAttribute("employees", employeeManagementService.getAll());
		if (id != null)
			model.addAttribute("isNotStandAloneFragment", true);
		return "admin/fragments/add_employee";
	}

	@RequestMapping("admin/fragment/fragment_edit_project")
	public String getEditProjectView(Model model, @RequestParam("id") Integer id) {
		// model.addAttribute("projectUpdateModel", projectService.getById(id));

		// for this binder object we don't care about contactPerson property
		// because those are already set while adding data
		ProjectFormBinder binder = new ProjectFormBinder();
		binder.setProject(projectService.getById(id));

		model.addAttribute("projectUpdateModel", binder);
		model.addAttribute("projectTypes", projectTypeService.getAll());
		model.addAttribute("states", stateManagementService.getAll());
		model.addAttribute("projectstatuses", projectStatusService.getProjectStatus());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		return "admin/fragments/edit_project";
	}

	@RequestMapping("admin/fragment/fragment_edit_member_detail/{id}")
	public String getEditMemberDetail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("updateMemberDetailModel", employeeManagementService.getById(id));
		model.addAttribute("employees", employeeManagementService.getAll());
		return "admin/fragments/edit_member_detail";
	}

	@RequestMapping(value={"admin/fragment/fragment_add_project_inquiry", "user/fragment/fragment_add_project_inquiry"})
	public String getEditProjectInquiryView(Model model) {
		model.addAttribute("projectInquiry", new ProjectInquiry());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		model.addAttribute("projinquiries", projectInquiryService.getAll());
		return "user/fragments/add_project_inquiry";
	}

	@RequestMapping("admin/fragment/fragment_edit_inquiry")
	public String getEditInquiryView(@ModelAttribute("updateInquiryModel") Inquiry inquiry,	ModelMap model, @RequestParam int id) {
		model.addAttribute("updateInquiryModel", inquiryService.getById(id));
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("projects", projectService.getAll());
		return "admin/fragments/edit_inquiry";
	}

	@RequestMapping(value={"user/fragment/fragment_edit_booking_detail", "admin/fragment/fragment_edit_booking_detail"})
	public String getEditBookingDetailView(ModelMap model, @RequestParam int id) {
		model.addAttribute("updateBookingDetailModel", bookingDetailService.getById(id));
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("members", employeeManagementService.getAllMembers());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("propDetails", propertyDetailService.getAll());
		return "user/fragments/edit_booking_detail";
	}
	
	@RequestMapping(value={"admin/fragment/fragment_edit_parking_booking_detail", "user/fragment/fragment_edit_parking_booking_detail"})
	public String getEditParkingBookingDetailView(ModelMap model, @RequestParam int id) {
		model.addAttribute("updateParkingBookingDetailModel", parkingBookingDetailService.getById(id));
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("members", employeeManagementService.getAllMembers());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("parkings", projectExtraParkingService.getAll());
		return "user/fragments/edit_parking_booking_detail";
	}
	
	@RequestMapping("admin/fragment/fragment_edit_property_detail")
	public String getEditPropertyDetailView(@ModelAttribute("updatePropertyDetailModel") PropertyDetail propertyDetail,	ModelMap model, @RequestParam int id) {
		model.addAttribute("updatePropertyDetailModel", propertyDetailService.getById(id));
		List<Project> prjects = projectService.getAll();
		model.addAttribute("projects", prjects);
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		if(prjects != null && prjects.size() > 0) {
			List<ProjectPropertyBlock> blocks = prjects.get(0).getBlocks();
			model.addAttribute("blockTypes", prjects.get(0).getBlocks());
			if(blocks != null && blocks.size() > 0)
				// TODO: Bidirectional Mapping???
				model.addAttribute("plans", projectPropertyPlanService.getPlansForBlock(blocks.get(0).getBlockId()));
		}
		model.addAttribute("measurements", measurementUnitService.getAll());
		model.addAttribute("propDetails", propertyDetailService.getAll());
		return "admin/fragments/edit_property_detail";
	}

	@RequestMapping("admin/fragment/fragment_edit_project_property_plan")
	public String getEditProjectPropertyPlanView(ModelMap model, @RequestParam int id) {
		model.addAttribute("updateProjectPropertyPlanModel", projectPropertyPlanService.getById(id));
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("blocks", projectPropertyBlockService.getAll());
		model.addAttribute("propTypes",	propertyTypeProviderService.getPropertyTypes());
		return "admin/fragments/edit_project_property_plan";
	}

	@RequestMapping(value={"admin/fragment/fragement_manage_inquiry", "user/fragment/fragement_manage_inquiry"})
	public String getManageInquiry(ModelMap model) {
		model.addAttribute("inquiryModel", new InquiryFormBinder());
		model.addAttribute("inquiries", inquiryService.getAll());
		model.addAttribute("projects", projectService.getAll());
		model.addAttribute("employees", employeeManagementService.getAll());
		model.addAttribute("projinquiries", projectInquiryService.getAll());
		return "user/fragments/manage_inquiry";
	}
	
	@RequestMapping("admin/fragment/fragment_add_payment_plan")
	public String getPaymentPlanView(ModelMap model) {
		model.addAttribute("projects", projectService.getAll());
		return "admin/fragments/add_project_payment_plan";
	}
	
	@RequestMapping(value="admin/showPaymentPlan")
	public String getShowPaymentView(ModelMap model, @RequestParam int id) {
		Project project = projectService.getById(id);
		List<PaymentPlan> plan = project.getPaymentPlan();
		model.addAttribute("plan", plan);
		return "admin/fragments/view_project_payment_plan";
	}
	
	@RequestMapping("admin/fragment/fragment_fill_progress")
	public String getPaymentProgressFillView(ModelMap model) {
		model.addAttribute("projects", projectService.getAll());
		return "admin/fragments/payment_fill_progress";
	}
	
	@RequestMapping("admin/fragment/getFillPaymentChildView/{id}")
	public String getPaymentProgressFillChildView(ModelMap model, @PathVariable int id) {
		Project project = projectService.getById(id);
		model.addAttribute("paymentPlan", project.getPaymentPlan());
		model.addAttribute("blocks", project.getBlocks());
		return "admin/fragments/payment_fill_progress_child";
	}

	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("admin/fragment/fragment_open_popUp2")
	public String getView2(ModelMap model, @RequestParam(value="id") Integer id) {
		model.addAttribute("projects", projectService.getById(id));
		int propertyCount = projectService.getPropertyCountForProject(id);
		int bookedPropertyCount = projectService.getBookedPropertyCountForProject(id);
	//	String s="[['Total Properties'" + ","+ propertyCount + "],['Booked Properties'," + bookedPropertyCount+"]]";
		model.addAttribute("propertyChartData",propertyCount);
		model.addAttribute("bookedPropertyChartData",bookedPropertyCount);
		return "admin/fragments/view_project";
	}
	
	@RequestMapping(value={"admin/fragment/fragment_view_book_property", "user/fragment/fragment_view_book_property"})
	public String getViewBookProperty(ModelMap model, @RequestParam(value="id") Integer id) {
		model.addAttribute("bookdetail", bookingDetailService.getById(id));
		return "user/fragments/view_book_property";
	}
	
	@RequestMapping(value={"admin/fragment/fragment_payment_progress_member/{id}", "user/fragment/fragment_payment_progress_member/{id}"})
	public String getMemberPaymentUpdateView(ModelMap model, @PathVariable("id") Integer id) {
		model.addAttribute("members", employeeManagementService.getAllMembers());
		return "user/fragments/payment_progress_member";
	}
	
	@RequestMapping(value={"admin/fragment/fragment_payment_progress_member_child/{id}", "user/fragment/fragment_payment_progress_member_child/{id}"})
	public String getMemberPaymentUpdateChildView(ModelMap model, @PathVariable("id") Integer id) {
		BookingDetail detail = bookingDetailService.getById(id);
		model.addAttribute("id", id);
		model.addAttribute("paymentPlan", detail.getPropertyDetail().getProject().getPaymentPlan());
		model.addAttribute("paymentHistory", detail.getPaymentData());
		return "user/fragments/payment_progress_member_child";
	}
	
	@RequestMapping("admin/fragment/fragment_view_employee")
	public String getViewEmployeee(ModelMap model, @RequestParam(value="id") Integer id) {
		model.addAttribute("newUser", id == null ? new Employee() : employeeManagementService.getById(id));
		return "admin/fragments/view_employee";
	}

	@RequestMapping("member/fragment/fragment_member_booking_detail")
	public String getMemberBookingDetailView(ModelMap model) {
		Employee user = ((CustomUserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployee();
		model.addAttribute("bookedProperties", bookingDetailService.getPropertiesBookedByMember(user.getId()));
		return "member/fragments/member_booking_details";
	}
	
	@RequestMapping("member/fragment/fragment_member_home")
	public String getMemberHomeView() {
		return "member/fragments/member_home_details";
	}
}
