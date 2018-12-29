<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_PROJECT_CONTACT_PERSON);
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
					url : "deleteProjectContactPerson",
					data : "id=" + id,
					success : function() {
						$.fallr('hide');
						loadPage(MANAGE_PROJECT_CONTACT_PERSON);
					},
					error : function() {
						alert("Failed to connect to server, Please try again later...");
					}
				});
	}

	function onUpdateClick(e) {
		var enclosingROW = $(e).closest('tr');
		$
				.fallr(
						'show',
						{
							useOverlay : true,
							position : 'center',
							icon : 'gear',
							content : '<div id="fallrHeadline"><h5>Edit Project Contact Person</h5></div>'
									+ '<form action="updateProjectContactPerson" method="post" id="fallrContent">'
									+ '<fieldset>'
									+ 'Project:'
									+ '<input type="text" name="project" id="project" value="'
									+ enclosingROW.find('.propName').text()
									+ '" class="text ui-widget-content ui-corner-all"><br>'
									+ 'Employee:'
									+ '<input type="text" name="employee" id="employee" value="'
									+ enclosingROW.find('.propName').text()
									+ '" class="text ui-widget-content ui-corner-all"><br>'
									+ '<input type="hidden" name="ProjContactPersonID" id="ProjContactPersonID" value="'
									+ enclosingROW.find('.propID').text()
									+ '">' + '</fieldset>' + '</form>',
							buttons : {
								button1 : {
									text : 'Submit',
									onclick : updatePropertyTypeName
								},
								button4 : {
									text : 'Cancel'
								}
							},
						});
		fallrCustom();
		$
				.fallr(
						'show',
						{
							icon : 'secure',
							content : '<div id="fallrHeadline"><p>Edit Project Contact Person.</p></div>'
									+ '<form action="updateProjectContactPerson" method="post" id="fallrContent">'
									+ '<fieldset>'
									+ 'Project:'
									+ '<input type="text" name="employee" id="employee" value="'
									+ enclosingROW.find('.ProjName').text()
									+ '" class="text ui-widget-content ui-corner-all">'
									+ 'Employee:'
									+ '<input type="text" name="employee" id="employee" value="'
									+ enclosingROW.find('.EmpName').text()
									+ '" class="text ui-widget-content ui-corner-all">'
									+ 'Active:'
									+ '<input type="text" name="active" id="active" value="'
									+ enclosingROW.find('.activeID').text()
									+ '" class="text ui-widget-content ui-corner-all">'
									+ '<input type="hidden" name="ProjContactPersonID" id="ProjContactPersonID" value="'
									+ enclosingROW.find('.propID').text()
									+ '">' + '</fieldset>' + '</form>',
							buttons : {
								button1 : {
									text : 'Submit',
									onclick : updatePropertyTypeName
								},
								button4 : {
									text : 'Cancel'
								}
							},
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
									+ '<p>Warning</p>'
									+ '</div><div id="fallrContent">'
									+ '<p class="mediumRed">Are you sure want to delete?</p></div>',
							icon : 'error'
						});
	}
	function submitForm() {
		toggleLoadingIcon(true);
		$("#contactPerson").ajaxForm(options).submit();
		return false;
	}
</script>
<div class="fivecol">
	<h3>Manage Contact Person</h3>
	<hr>
	<form:form modelAttribute="contactPerson"
		action="addProjectContactPerson" method="post">
		<fieldset>
			<ol>
				<li><label for="expenseName" class="mediumRed">Project</label>
					<form:select path="project">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select></li>

				<li><label for="expenseName" class="mediumRed">Employee</label>
					<form:select path="employee">
						<c:forEach items="${employees}" var="employee">
							<form:option value="${employee.id }">${employee.firstName }  ${employee.lastName }</form:option>
						</c:forEach>
					</form:select></li>

				<li><label for="expenseName" class="mediumRed">Active</label> <form:checkbox
						path="active" class="tour2" /></li>
				<br />

				<li><input type="submit" name="submit" class="tour2"
					value="Add" onclick="javascript: return submitForm();"></li>
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
					<th style="display: none;"></th>
					<th>Project</th>
					<th>Employee</th>
					<th></th>

				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:set var="counter" value="1" />
				<c:forEach items="${contactPersons}" var="contactPerson">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display: none;" class="propID">${contactPerson.id }</td>
						<td class="projName">${contactPerson.project.name }</td>
						<td class="EmpName">${contactPerson.employee.firstName}
							${contactPerson.employee.lastName}</td>
						<td width="40"><img class="invOption"
							src="resources/images/invOption.png" rel=""
							onclick="editOptions(this);"></td>
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>