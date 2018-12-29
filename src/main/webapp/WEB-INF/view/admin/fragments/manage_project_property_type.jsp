<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_PROJECT_PROPERTY_TYPE);
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
		$.ajax({
		   type: "POST",
		   url: "deleteProjectPropertyType",
		   data: "id=" + id,
		   success: function(){
				$.fallr('hide');
				loadPage(MANAGE_PROJECT_PROPERTY_TYPE);
		   },
		   error: function() {
			   alert("Failed to connect to server, Please try again later...");
		   }
		});
	}
	
	function onUpdateClick(e) {
		var enclosingROW = $(e).closest('tr');
		$.fallr('show', {
			useOverlay	: true,
			position	: 'center',
		    icon        : 'gear',
		    content     : '<div id="fallrHeadline"><h5>Edit Measurements</h5></div>'
		    			+ '<form action="updateProjectPrpertyType" method="post" id="fallrContent">'
		    			+ '<fieldset>'
		    			+ 'Name:' 
		      			+ '<input type="text" name="name" id="name" value="'
		      			+ enclosingROW.find('.propName').text()
		      			+ '" class="text ui-widget-content ui-corner-all">'
		      			+ '<input type="hidden" name="mid" id="mid" value="'
		      			+ enclosingROW.find('.propID').text()
		      			+ '">'
		    			+ '</fieldset>'
		  				+ '</form>',
		    buttons : {
		        button1 : {text: 'Submit', onclick: updatePropertyTypeName},
		        button4 : {text: 'Cancel'}
		    },
		});
		fallrCustom();
	}
	
	function onDeleteClick(e) {
		var id = $(e).closest('tr').find('.propID').text(); 
		$.fallr('show', {
			useOverlay: true,
			position: 'center',
			buttons: {
				button1: {
					text: 'Delete',
					onclick: function() {
						sendDeleteRequest(id);
					}
				},
				button2: {
					text: 'Cancel'
				}
			},
			content : '<div id="fallrHeadline">'
					+ '<h5>Warning</h5>'
					+ '</div><div id="fallrContent">'
					+ '<p class="mediumRed">Are you sure want to delete?</p></div>',
			icon: 'error'
		});
		fallrCustom();
	}
	function submitForm() {
		toggleLoadingIcon(true);
		$("#projectPropType").ajaxForm(options).submit();
		return false;
	}
</script>
<div class="fivecol">
	<h3>Manage Project Property Type</h3>
	<hr>
	<form:form modelAttribute="projectPropType" action="addProjectPropertyType"
		method="post">
		<fieldset>
			<ol>
				<li><label for="expenseName" class="mediumRed">Project</label>
					<form:select path="project">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select></li>
				<li><label for="expenseName" class="mediumRed">Property Type:</label></li>
				<li><form:select path="propertyType">
						<c:forEach items="${types}" var="type">
							<form:option value="${type.propertyTypeID}">${type.name}</form:option>
						</c:forEach>
					</form:select></li>
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
					<th>Property Type</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:set var="counter" value="1" />
				<c:forEach items="${proptypes}" var="proptype">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display: none;" class="propID">${proptype.id }</td>
						<td class="projName">${proptype.project.name }</td>
						<td class="propTypeName">${proptype.propertyType.name}</td>
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