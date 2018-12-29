<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
	var counter = 0;
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_PARKING_BOOKING_DETAIL);
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
		   url: "deleteParkingBookingDetail",
		   data: "id=" + id,
		   success: function(){
				$.fallr('hide');
				loadPage(MANAGE_PARKING_BOOKING_DETAIL);
		   },
		   error: function() {
			   alert("Failed to connect to server, Please try again later...");
		   }
		});
	}
	function onUpdateClick(e) {
		$.fancybox({
			'href' : "fragment/fragment_edit_parking_booking_detail?id=" + $(e).closest('tr').find('.propID').text(),
			'autoDimensions': false,
		    'scrolling': 'no',
		    'width': 850,
		    'height': 700,
		    'overlayShow': true,
		    cyclic: false,
		    'transitionIn': 'none',
		    'transitionOut': 'none',
		    onComplete: function() {
		        bottomContentHeightChange();
		    },
		    onClosed: function() {
		        //refreshGrid("ct");
		    }
		});
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
	
	
	
	$("#project").on('change', function(e) {
		
		$.ajax({
			  url: 'getExtraParkingForPrj/' + $("#project").val(),
			  type: 'GET',
			  success: function(data) {
				  $('#extraParking').find('option').remove().end();
				  for(division in data) {
					  $('#extraParking').append($('<option>', {
						  value: data[$('#extraParking > option').length].id,
						  text: data[$('#extraParking > option').length].parkingNo
					  }));
				  }
			  },
			  error: function(e) {
				if(counter == 5) {
					console.log(e.message);
					alert("Unable to connect to server...");
					counter = 0;
				} else {
					counter++;
				}
			  }
		});
	});
	function submitForm() {
		toggleLoadingIcon(true);
		$("#parkingModel").ajaxForm(options).submit();
		return false;
	}
/*	$(document)
	.ready(
			function() {
				$("#parkingModel")
						.on(
								"submit",
								function() {
									var ret = true;
															
	
									if (($("#bookingDate")
											.val()) == '') {

										$("#sdate")
												.text(
														"*Starting Date is Required");
										ret = false;
									}
									
									

									toggleLoadingIcon(false);
									return ret;
								})
			})*/
</script>
<style>
.clr {
	color: red;
}
</style>
<div class="fivecol">
	<h3>Manage Parking Booking Details</h3>
	<hr>
	<form:form modelAttribute="parkingModel" action="addParkingBookingDetail"
		method="post">
		<fieldset>
			<ol>
				<li><label for="expenseName" class="mediumRed">Project:</label>
				<form:select path="project">
						<form:options items="${projects}" itemLabel="name" itemValue="id"/>
					</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Extra Parking:</label>
				<form:select path="extraParking">
						<form:options items="${parkings}" itemLabel="parkingNo" itemValue="id"/>
					</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Booking Date:</label>
				<form:input type="date" path="bookingDate" class="tour2" />
				<span class="clr" id="sdate">
				${sessionScope.errorMsg }
				<c:remove var="errorMsg" scope="session"/>
				</span></li></li>
				
				<li><label for="expenseName" class="mediumRed">Member Details:</label>
				<form:select path="memberDetail">
						<form:options items="${members}" itemLabel="name" itemValue="id"/>
					</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Booked By Employee:</label>
				<form:select path="bookedByEmployee">
						<form:options items="${employees}" itemLabel="firstName" itemValue="id"/>
					</form:select></li>
				
				<li><label for="expenseName" class="mediumRed">Is Resale:</label>
				<form:checkbox  path="resale" class="tour2"/></li>
				<span class="clr" id="pnm">
					
				</span></li></li>
				
				
				<br/>
				
				<li><label for="expenseName" class="mediumRed">Is Available For Resale:</label>
				<form:checkbox path="availableForResale" class="tour2" /></li><br/>
				
			
				<input type="submit" name="submit" class="tour2"
					value="Add" onclick="javascript: return submitForm();"><br/>
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
					<th style="display:none"></th>
					<th>Project</th>
					<th>Extra Parking</th>
					<th>Booking Date</th>
					<th>Booked By Employee</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:set var="counter" value="1" />
				<c:forEach items="${pbookings}" var="pbooking">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display:none;" class="propID">${pbooking.id }</td>
						<td class="propName">${pbooking.project.name }</td>
						<td class="propName">${pbooking.extraParking.parkingNo }</td>
						<td class="propName">${pbooking.bookingDate }</td>
						<td class="propName">${pbooking.bookedByEmployee.firstName } ${pbooking.bookedByEmployee.lastName }</td>
						<td width="40"><img class="invOption" src="../resources/images/invOption.png" rel="" onclick="editOptions(this);"></td>
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
