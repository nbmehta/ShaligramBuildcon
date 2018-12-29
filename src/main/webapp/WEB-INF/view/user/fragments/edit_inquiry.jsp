<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			loadPage(MANAGE_INQUIRY);
			$.fancybox.close();
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			$.fancybox.close();
		}
	}

	function submitForm() {
		toggleLoadingIcon(true);
		$('#updateInquiryModel').ajaxForm(options).submit();
		return false;
	}
	
	
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Edit
		Inquiry</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form:form modelAttribute="updateInquiryModel" method="post"
			action="updateInquiryDetail">
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
				
				<li><label for="expenseName" class="mediumRed">Visit Site</label>
					<form:select path="visitedSite">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select></li>

				<li><label for="expenseName" class="mediumRed">Visitor Name:</label></li>
				<li><form:input type="text" path="visitorName" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Visit Date:</label></li>
				<li><form:input type="date" path="visitDate" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Intime Hour:</label></li>
				<li><form:input type="text" path="intimeHour" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Intime Minute:</label></li>
				<li><form:input type="text" path="intimeMinute" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Outtime Hour:</label></li>
				<li><form:input type="text" path="outtimeHour" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Outtime Minute:</label></li>
				<li><form:input type="text" path="outtimeMinute" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Area Or City:</label></li>
				<li><form:input type="text" path="areaOrCity" class="tour2" /></li>
				
				<li><label for="expenseName" class="mediumRed">FollowUp By:</label>
					<form:select path="followUpBy">
						<c:forEach items="${employees}" var="employee">
							<form:option value="${employee.id }">${employee.firstName }  ${employee.lastName }</form:option>
						</c:forEach>
					</form:select></li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						
				<li><label for="expenseName" class="mediumRed">Contact Number:</label></li>
				<li><form:input type="text" path="contactNumber" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Email:</label></li>
				<li><form:input type="text" path="email" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Reference By:</label></li>
				<li><form:input type="text" path="referenceBy" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Inquiry Type:</label></li>
				<li><form:input type="text" path="inquiryType" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">No Of Visit:</label></li>
				<li><form:input type="text" path="noOfVisit" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Remark:</label></li>
				<li><form:input type="text" path="remark" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">FollowUp Date:</label></li>
				<li><form:input type="date" path="folowUpDate" class="tour2" /></li>
				<form:hidden path="id" />
						<li>
							<button type="submit" value="Add User"
								class="blueButton submit fallr-button" id="submit"
								onclick="javascript: return submitForm();">Update
								Project</button>
							<button type="reset" value="Clear" class="submit fallr-button"
								id="clear">Clear</button>
						</li>
					</ol>
				</fieldset>
			</div>
		</form:form>
		<div class="clear"></div>
	</div>
</div>