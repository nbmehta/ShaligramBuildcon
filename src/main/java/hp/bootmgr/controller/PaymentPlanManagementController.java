package hp.bootmgr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hp.bootmgr.services.BookingDetailService;
import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.BlockProgress;
import hp.bootmgr.vo.BookingDetail;
import hp.bootmgr.vo.PaymentPlan;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;

@Controller
@SuppressWarnings("rawtypes")
public class PaymentPlanManagementController {
		
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@Autowired
	private BookingDetailService bookingDetailService;
	
	private static Logger logger = Logger.getLogger(PaymentPlanManagementController.class);
	
	@RequestMapping(value="admin/addProjectPayment", method=RequestMethod.POST)
	public ResponseEntity addPaymentPlan(@RequestParam int project_id, @RequestParam String name_str, @RequestParam String percentage_str) {
		HttpStatus status = HttpStatus.OK;
		logger.debug(project_id + " " + name_str + " " + percentage_str);
		Project project = projectService.getById(project_id);
		String[] nameArr = name_str.split(":");
		String[] percArr = percentage_str.split(":");
		List<PaymentPlan> plan = project.getPaymentPlan();
		
		// clear older data before inserting new one
		plan.clear();
		if(nameArr.length != percArr.length) {
			logger.error(nameArr + " <-> " + percArr);
			logger.error("nameArr[" + nameArr.length + "] & percArr[" + percArr.length + "] size mismatch, most probably malformed request...!!!");
			status = HttpStatus.BAD_REQUEST;
		} else {
			// As we are updating project plan, we need to clear progress as well
			// to keep data consistent
			project.getBlocks().forEach(block -> {
				block.getProgress().clear();
			});
			for(int i=0; i < nameArr.length; i++) {
				PaymentPlan paymentPlan = new PaymentPlan();
				paymentPlan.setCompletedPercentage(Integer.parseInt(percArr[i]));
				paymentPlan.setName(nameArr[i]);
				paymentPlan.setProject(project);
				plan.add(paymentPlan);
				
				// Initialize plan table
				project.getBlocks().forEach(block -> {
					BlockProgress progress = new BlockProgress();
					progress.setBlock(block);
					block.getProgress().add(progress);
				});
			}
			project.setPaymentPlan(plan);
			projectService.update(project);
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value="admin/updateProgressForBlock", method=RequestMethod.POST)
	public ResponseEntity updateProgressForBlock(@RequestParam Map<String, String> params) {
		HttpStatus status = HttpStatus.OK;
		logger.debug("updateProgressForBlock(): PARAMS: " + params);
		
		String projectID = params.get("project_id");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(projectID != null && !projectID.equals("")) {
			Project project = projectService.getById(Integer.parseInt(projectID));
			List<ProjectPropertyBlock> blocks = project.getBlocks();

			// Fetch params column wise
			for(int i=0; i < project.getPaymentPlan().size(); i++) {
				for(ProjectPropertyBlock block : blocks) {
					int blockId = block.getBlockId();
					BlockProgress progress = block.getProgress().get(i);
					
					/**
					 * Parameters are named in following way
					 * For checkbox: cbx_<block-id>_<index of phase>
					 * For Date: dtp_<block-id>_<index of phase>
					 * For Review Date: dtp_review_<block-id>_<index of phase>
					 */
					String cbxParam = params.get("cbx_" + blockId + "_" + i);
					Boolean isCompleted = cbxParam != null && cbxParam.equals("on");
					
					String dtParam = params.get("dtp_" + blockId + "_" + i);
					String dtReview = params.get("dtp_review_" + blockId + "_" + i);
					
					progress.setCompleted(isCompleted);
					List<Date> reviewDates = progress.getReiewDates();
					try {
						if(isCompleted) {
							// If phase is marked as completed, set date from previous data
							progress.setFinalDateOfCompletion(new Date());
						} else {
							if(dtParam != null && !dtParam.equals(""))
								progress.setDateOfCompletion(dateFormat.parse(dtParam));
							// If there is any review, check duplicate entry in not being inserted
							if(dtReview != null && !dtReview.equals("") && !(reviewDates.size() > 0 && dateFormat.format(reviewDates.get(reviewDates.size() - 1)).equals(dtReview)))
								reviewDates.add(dateFormat.parse(dtReview));
						}
					} catch (ParseException e) {
						e.printStackTrace();
						status = HttpStatus.BAD_REQUEST;
					}
					projectPropertyBlockService.update(block);
				}
			}
		} else status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value={"admin/updatePaymentData", "user/updatePaymentData"}, method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> updatePaymentData(@RequestParam Map<String, String> params) {
		HttpStatus status = HttpStatus.OK;
		String detailId = params.get("detailId");
		if(detailId != null) {
			try {
				BookingDetail detail = bookingDetailService.getById(Integer.parseInt(detailId));
				List<Boolean> paymentData = detail.getPaymentData();
				for(int i=0; i < paymentData.size(); i++) {
					String chkBoxData = params.get("box_" + i);
					paymentData.set(i, chkBoxData != null && chkBoxData.equals("on"));
				}
				bookingDetailService.update(detail);
			} catch (Exception e) {
				e.printStackTrace();
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		return new ResponseEntity<HttpStatus>(status);
	}
}
