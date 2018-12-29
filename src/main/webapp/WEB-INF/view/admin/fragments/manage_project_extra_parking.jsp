<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_PROJECT_EXTRA_PARKING );
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
		}
	}
	
	function updatePropertyTypeName() {
		toggleLoadingIcon(true);
		var ret = true;
		var pattern=/^([0-9])*\.*([0-9])+$/;
		
		if($("#pNo").val()==''){
			$("#par_span").text("* Field is required");
			ret = false;
		}
		else if(!pattern.test($("#pNo").val())){
			$("#par_span").text("* Only digits are allowed");
				ret = false;
			}
		
		 else if($("#note").val().length>255){
		    	$("#nt_span").text("*Max length should be 255 characters");
		    	ret=false;
		    }
		
		if(ret)
			$('#fallrContent').ajaxForm(options).submit();
	 	else
	 		{
	 		toggleLoadingIcon(false);
	 		}

	}
	
	function sendDeleteRequest(id) {
		toggleLoadingIcon(true);
		$.ajax({
		   type: "POST",
		   url: "deleteProjectExtraParking",
		   data: "id=" + id,
		   success: function(){
				$.fallr('hide');
				loadPage(MANAGE_PROJECT_EXTRA_PARKING );
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
		    content     : '<div id="fallrHeadline"><h5>Edit Extra Parking</h5></div>'
		    			+ '<form action="updateProjectExtraParking" method="post" id="fallrContent">'
		    			+ '<fieldset>'
		    			+ 'Project:' 
		    			+ '<select name="edtProject" id="edtProject"'
						//+ enclosingROW.find('.projName').text()
						+ ' class="text ui-widget-content ui-corner-all"><c:forEach items="${projects}" var="x"><option value="${x.id}">${x.name}</option></c:forEach>'
						+ '</select><br/><br/>'
		      			+ 'Parking No:' 
		      			+ '<input type="text" name="pNo" id="pNo" value="'
		      			+ enclosingROW.find('.propNo').text()
		      			+ '" class="text ui-widget-content ui-corner-all"><br>'
		      			+'<span class="clr" id="par_span"></span><br/>'
		      			+ 'Note:' 
		      			+ '<input type="text" name="note" id="note" value="'
		      			+ enclosingROW.find('.propNote').text()
		      			+ '" class="text ui-widget-content ui-corner-all"><br>'
		      			+'<span class="clr" id="nt_span"></span><br/>'
		      			+ '<input type="hidden" name="parkingID" id="parkingID" value="'
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
		$("#edtProject").val(enclosingROW.find('.projID').text());
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
		$("#extraParkingModel").ajaxForm(options).submit();
		return false;
	}
	$(document).ready(function() {
		$("#extraParkingModel").on("submit", function() {
			var ret = true;
			var pattern=/^([0-9])*\.*([0-9])+$/;
			
			if($("#parkingNo").val()==''){
				$("#parkingNo_span").text("* Field is required");
				ret = false;
			}
			else if(!pattern.test($("#parkingNo").val())){
				$("#parkingNo_span").text("* Only digits are allowed");
					ret = false;
				}
			
			 else if($("#note").val().length>255){
			    	$("#note_span").text("*Max length should be 255 characters");
			    	ret=false;
			    }
			toggleLoadingIcon(false);
		return ret;
		})
	})
</script>
<style>
.clr {
	color: red;
}
</style>
<div class="fivecol">
	<h3>Manage Project Extra Parking</h3>
	<hr>
	<form:form modelAttribute="extraParkingModel" action="addProjectExtraParking"
		method="post">
		<fieldset>
			<ol>
				
				<li><label for="expenseName" class="mediumRed">project</label>
					<form:select path="project">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select></li>
					
				<li><label for="expenseName" class="mediumRed">Parking No</label>
				<form:input type="text" path="parkingNo" class="tour2" />
				<span id="parkingNo_span" class="clr">
				${sessionScope.errorMsg }
				<c:remove var="errorMsg" scope="session"/>
				
				</span></li>
				
				<li><label for="expenseName" class="mediumRed">Note</label>
				<form:input type="text" path="note" class="tour2" />
				<span id="note_span" class="clr"></span></li>
				
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
					<th style="display: none;"></th>
					<th>Project</th>
					<th>Parking Number</th>
					<th style="display:none;"></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />`
				<c:set var="counter" value="1" />
				<c:forEach items="${parkings}" var="parking">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display:none;" class="propID">${parking.id }</td>
						<td style="display: none;" class="projID">${parking.project.id }</td>
						<td class="propName">${parking.project.name }</td>
						<td class="propNo">${parking.parkingNo}</td>	
						<td style="display:none;" class="propNote">${parking.note}</td>
						<td width="40"><img class="invOption" src="../resources/images/invOption.png" rel="" onclick="editOptions(this);"></td>
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
		<center>
			<br/><br/>
			<font color="red">${sessionScope.errorMsg2 }</font>
		</center>
		<c:remove var="errorMsg2" scope="session"/>
	</div>
</div>