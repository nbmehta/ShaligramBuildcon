<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="functions.js"></script>
<script type="text/javascript">
	var cntr = 0;
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_INQUIRY);
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
		}
	}

	function updatePropertyTypeName() {
		toggleLoadingIcon(true);
		$('#fallrContent').ajaxForm(options).submit();
	}

	function sendDeleteRequest(id) {
		toggleLoadingIcon(true);
		$
				.ajax({
					type : "POST",
					url : "deleteInquiry",
					data : "id=" + id,
					success : function() {
						$.fallr('hide');
						loadPage(MANAGE_INQUIRY);
					},
					error : function() {
						alert("Failed to connect to server, Please try again later...");
					}
				});
	}
	function onUpdateClick(e) {
		$.fancybox({
			'href' : "fragment/fragment_edit_inquiry?id="
					+ $(e).closest('tr').find('.propID').text(),
			'autoDimensions' : false,
			'scrolling' : 'no',
			'width' : 450,
			'height' : 500,
			'overlayShow' : true,
			cyclic : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			onComplete : function() {
				bottomContentHeightChange();
			},
			onClosed : function() {
				//refreshGrid("ct");
			}
		});
	}
	function onDeleteClick(e) {
		var id = $(e).closest('tr').find('.propID').text();
		$
				.fallr(
						'show',
						{
							useOverlay : true,
							position : 'center',
							buttons : {
								button1 : {
									text : 'Delete',
									onclick : function() {
										sendDeleteRequest(id);
									}
								},
								button2 : {
									text : 'Cancel'
								}
							},
							content : '<div id="fallrHeadline">'
									+ '<h5>Warning</h5>'
									+ '</div><div id="fallrContent">'
									+ '<p class="mediumRed">Are you sure want to delete?</p></div>',
							icon : 'error'
						});
		fallrCustom();
	}
	function submitForm() {
		toggleLoadingIcon(true);
		var i = 0;
		var ret = "";
		$('#projectContainer tbody tr').each(
				function() {
					ret += $(this).find(':nth-child(2)').html() + ",";
					ret += $(this).find(':nth-child(4)').html() + ",";
					ret += $("#check_" + (++i)).is(":checked") + " ,"
							+ $("#check1_" + (i)).is(":checked") + ",";
				});
		$('input[name="str"]').val(ret);
		$("#inquiryModel").ajaxForm(options).submit();
		return false;
	}
	
	function onAddProjectInquiry(e) {
		$.fancybox({
			'href' : "fragment/fragment_add_project_inquiry",
			'autoDimensions' : false,
			'scrolling' : 'no',
			'width' : 450,
			'height' : 400,
			'overlayShow' : true,
			cyclic : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			onComplete : function() {
				bottomContentHeightChange();
			},
			onClosed : function() {

			}
		});
	}

	$(document).ready(function() {
		$('#projectContainer').DataTable({
			"columnDefs" : [ {
				"targets" : [ 1 ],
				"sClass" : "myClass",
				"searchable" : false
			}, {
				"targets" : [ 3 ],
				"sClass" : "myClass"
			} ]
		});
	});
</script>
<div class="fivecol">
	<h3>Manage Inquiry</h3>
	<hr>
	<form:form modelAttribute="inquiryModel" action="addProjectInquiry"
		method="post">
		<fieldset>
			<ol>

				<li><label for="expenseName" class="mediumRed">Visit
						Site</label> <form:select path="inquiry.visitedSite">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select></li>

				<li><label for="expenseName" class="mediumRed">Visitor
						Name:</label></li>
				<li><form:input type="text" path="inquiry.visitorName"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Visit
						Date:</label></li>
				<li><form:input type="date" path="inquiry.visitDate"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Intime
						Hour:</label></li>
				<li><form:input type="text" path="inquiry.intimeHour"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Intime
						Minute:</label></li>
				<li><form:input type="text" path="inquiry.intimeMinute"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Outtime
						Hour:</label></li>
				<li><form:input type="text" path="inquiry.outtimeHour"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Outtime
						Minute:</label></li>
				<li><form:input type="text" path="inquiry.outtimeMinute"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Area Or
						City:</label></li>
				<li><form:input type="text" path="inquiry.areaOrCity"
						class="tour2" /></li>

				<li><label for="expenseName" class="mediumRed">FollowUp
						By:</label> <form:select path="inquiry.followUpBy">
						<c:forEach items="${employees}" var="employee">
							<form:option value="${employee.id }">${employee.firstName }  ${employee.lastName }</form:option>
						</c:forEach>
					</form:select></li>

				<li><label for="expenseName" class="mediumRed">Contact
						Number:</label></li>
				<li><form:input type="text" path="inquiry.contactNumber"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Email:</label></li>
				<li><form:input type="text" path="inquiry.email" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Reference
						By:</label></li>
				<li><form:input type="text" path="inquiry.referenceBy"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Inquiry
						Type:</label></li>
				<li><form:input type="text" path="inquiry.inquiryType"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">No Of
						Visit:</label></li>
				<li><form:input type="text" path="inquiry.noOfVisit"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Remark:</label></li>
				<li><form:input type="text" path="inquiry.remark" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">FollowUp
						Date:</label></li>
				<li><form:input type="date" path="inquiry.folowUpDate"
						class="tour2" /></li>
				<li><form:hidden path="str" value="ret" /></li>
				

				<li><input type="submit" name="submit" class="tour2"
					value="Add" onclick="javascript: return submitForm();"></li>
				<li><input type="button" class="tour2"
					value="Add Project Inquiry"
					onclick="javascript: return onAddProjectInquiry();"></li>
			</ol>
		</fieldset>
	</form:form>
</div>
<div id="expenseGrid" class="sevencol last grid">
	<div class="creativetbl_container">
		<table id="projectContainer">
			<thead>
				<tr>
					<th>Index</th>
					<th>ProID</th>
					<th>Project</th>
					<th>PropID</th>
					<th>Property Type</th>
					<th>Interested</th>
					<th>Show Sample House</th>
					<th></th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
</div>