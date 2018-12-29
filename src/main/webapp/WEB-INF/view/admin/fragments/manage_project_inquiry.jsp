<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_PROJECT_INQUIRY);
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
		   url: "deleteProjectInquiry",
		   data: "id=" + id,
		   success: function(){
				$.fallr('hide');
				loadPage(MANAGE_PROJECT_INQUIRY);
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
		    content     : '<div id="fallrHeadline"><h5>Edit Employee Role</h5></div>'
		    			+ '<form action="updateProjectInquiry" method="post" id="fallrContent">'
		    			+ '<fieldset>'
		    			+ 'Name:' 
		    			+ '<select name="edtProject" id="edtProject"'
						//+ enclosingROW.find('.projName').text()
						+ ' class="text ui-widget-content ui-corner-all"><c:forEach items="${projects}" var="x"><option value="${x.id}">${x.name}</option></c:forEach>'
						+ '</select><br/>'
		      			+ '<input type="hidden" name="proInquiryID" id="proInquiryID" value="'
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
		$("#projectInquiry").ajaxForm(options).submit();
		return false;
	}
</script>
<div class="fivecol">
	<h3>Manage Inquiry</h3>
	<hr>
	<form:form modelAttribute="projectInquiry" action="addProjectInquiry"
		method="post">
		<fieldset>
			<ol>
				<li><label for="expenseName" class="mediumRed">Inquiry:</label>
				<form:select path="inquiry"  class="contact-person-multiple tour2" style="width: 100%">
					<c:forEach items="${inquiries}" var="inquiry">
						<form:option value="${inquiry.id}">${inquiry.visitedSite.name }</form:option>
					</c:forEach>
				</form:select></li>
				
				
				<li><label for="expenseName" class="mediumRed">Project:</label>
				<form:select path="project"  class="contact-person-multiple tour2" style="width: 100%">
					<c:forEach items="${projects}" var="project">
						<form:option value="${project.id}">${project.name }</form:option>
					</c:forEach>
				</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Property Type:</label>
				<form:select path="projpropertyType"  class="contact-person-multiple tour2" style="width: 100%">
					<c:forEach items="${proptypes}" var="proptype">
						<form:option value="${proptype.id}">${proptype.propertyType.name }</form:option>
					</c:forEach>
				</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Interested:</label>
				<form:checkbox path="interested" class="tour2" /></li>
			<br>
			
				<li><label for="expenseName" class="mediumRed">Show Sample House:</label>
				<form:checkbox path="showSampleHouse" class="tour2" /></li>
				<br>
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
					<th style="display:none;"></th>
					<th>Inquiry</th>
					<th>Project</th>
					<th>Property Type</th>
					<th>Interested</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:set var="counter" value="1" />
				<c:forEach items="${projinquiries}" var="projinquiry">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display:none;" class="propID">${projinquiry.id }</td>
						<td class="propName">${projinquiry.inquiry.visitedSite.name }</td>
						<td class="project">${projinquiry.project.name }</td>
						<td class="proptype">${projinquiry.projpropertyType.propertyType.name }</td>
						<td class="interesetedd">${projinquiry.interested }</td>
						<td width="40"><img class="invOption" src="resources/images/invOption.png" rel="" onclick="editOptions(this);"></td>
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
