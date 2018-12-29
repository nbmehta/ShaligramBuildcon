<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Inquiry Description</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<li class="mediumRed"><h3>Visitor Details:</h3><hr/></li>
						<li>Visitor Name: ${inquiry.visitorName}</li>
						<li>Visited Site: ${inquiry.visitedSite.name}</li>
						<li>Visit Date: ${inquiry.visitDate}</li>
						<li>Number of visits: ${inquiry.noOfVisit}</li>
						<li>Referenced By: ${inquiry.referenceBy}</li>
						<li>Email: ${inquiry.email}</li>
						<li>Contact Number: ${inquiry.contactNumber}</li>
						<li>Area/City: ${inquiry.areaOrCity}</li>
						<li>Total Time: ${inquiry.intimeHour}:${inquiry.intimeMinute} - ${inquiry.outtimeHour}:${inquiry.outtimeMinute}</li><br />
						<li class="mediumRed"><h3>Attendee:</h3><hr/></li>
						<li>Name: ${inquiry.attendee.firstName} ${inquiry.attendee.lastName}</li>
					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li class="mediumRed"><h3>Follow up Details:</h3><hr/></li>
						<li>Person: ${inquiry.followUpBy.firstName} ${inquiry.followUpBy.lastName}</li>
						<li>Date: ${inquiry.folowUpDate}</li><br/>
						<li class="mediumRed"><h3>Project Inquiries:</h3><hr/></li>
						<c:forEach items="${inquiry.projectInquiries }" var="prjInq">
							<li>${prjInq.project.name } - ${prjInq.propertyType.name } 
								<c:if test="${prjInq.interested }">- Interested</c:if>
								<c:if test="${prjInq.showSampleHouse }">- Sample house shown</c:if>
							</li>
						</c:forEach>
					</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>