/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hp.bootmgr.binders.LoginFormBinder;
import hp.bootmgr.services.BookingDetailService;
import hp.bootmgr.services.InquiryService;
import hp.bootmgr.services.ProjectInquiryService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.Project;

@Controller
public class MainController {
	
	private static Logger logger = Logger.getLogger(MainController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectInquiryService projectInquiryService;

	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private BookingDetailService bookingDetailService;
	
	@RequestMapping("/")
	private String redirectToHome() {
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	private String getHomeView(String request, ModelMap model) {
		logger.debug("User role: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().toString());
		switch(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().toString()) {
		case "ROLE_ADMIN":
			return "redirect:admin/home";
		case "ROLE_EMPLOYEE":
			return "redirect:user/home";
		case "ROLE_MEMBER":
			return "redirect:member/home";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String logIn(ModelMap model) {
		model.addAttribute("user", new LoginFormBinder());
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(authority) ? "login" : "redirect:/home";
	}
	
	// START - Admin side URL Mapping
	@RequestMapping("admin/home")
	private String getAdminHomeView(ModelMap model) {
		List<Project> projects = projectService.getAll();
		StringBuilder builder = new StringBuilder();
		StringBuilder listOfProjects = new StringBuilder("[");
		StringBuilder listOfInquiries = new StringBuilder("[");
		StringBuilder listOfBookings = new StringBuilder("[");
		
		for(Project project : projects) {
			int tmp = projectInquiryService.getInquiryCount(project.getId());
			builder.append("['");
			builder.append(project.getName());
			builder.append("',");
			builder.append(tmp);
			builder.append("],");
			listOfProjects.append("'");
			listOfProjects.append(project.getName());
			listOfProjects.append("',");
			listOfInquiries.append(tmp);
			listOfInquiries.append(",");
			listOfBookings.append(bookingDetailService.getBookingCount(project.getId()));
			listOfBookings.append(",");
		}
		if(listOfInquiries.length() > 1)
			listOfInquiries.deleteCharAt(listOfInquiries.length() - 1);
		if(listOfBookings.length() > 1)
			listOfBookings.deleteCharAt(listOfBookings.length() - 1);
		if(listOfProjects.length() > 1)
			listOfProjects.deleteCharAt(listOfProjects.length() - 1);
		if(builder.length() > 0)
			builder.deleteCharAt(builder.length() - 1);
		listOfProjects.append("]");
		listOfInquiries.append("]");
		listOfBookings.append("]");
		
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		model.addAttribute("projectName", listOfProjects.toString());
		model.addAttribute("inquiryCount", listOfInquiries.toString());
		model.addAttribute("inquiryCountData", builder.toString());
		model.addAttribute("bookingCount", listOfBookings.toString());
		model.addAttribute("currentYear", cal.get(Calendar.YEAR));
		model.addAttribute("inquiryPerMonthValue", inquiryService.getInquiriesPerMonth());
		model.addAttribute("bookingPerMonthValue", bookingDetailService.getBookingPerMonth());
		return "admin/dashboard";
	}
	
	@RequestMapping("admin/employees")
	private String getPrpertyView() {
		return "admin/manage_employee";
	}
	
	@RequestMapping("admin//projects")
	private String getProjectView() {
		return "admin/manage_project";
	}
	
	@RequestMapping("admin//properties")
	private String getmanagePropertiesView() {
		return "admin/manage_property";
	}
	
	@RequestMapping("admin//members")
	private String getMemberShipManagementView() {
		return "admin/manage_member";
	}
	
	@RequestMapping("{user}/inquiry")
	private String getInquiryView(@PathVariable String user) {
		return user + "/manage_inquiry";
	}
	
	@RequestMapping("{user}/booking")
	private String getBookingView(@PathVariable String user) {
		return user + "/manage_booking";
	}
	
	@RequestMapping("admin/parking")
	private String getParkingView() {
		return "admin/manage_parking";
	}
	
	@RequestMapping("admin//misc")
	private String getMiscView() {
		return "admin/manage_misc";
	}
	
	@RequestMapping("admin/payment")
	private String getPaymentManagement() {
		return "admin/manage_payment";
	}
	// END - Admin side URL Mapping
	
	// START - User side URL Mapping
	@RequestMapping("user/home")
	private String getEmployeeHomeView() {
		return "user/home";
	}
	// END - User side URL Mapping
	
	// START - Member side URL Mapping
	@RequestMapping("member/home")
	private String getMemberHomeView() {
		return "member/member_home";
	}
	// END - Member side URL Mapping
}
