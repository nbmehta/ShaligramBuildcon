<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url value="/resources" var="resources"></c:url>
<link href="${resources}/css/custom_table2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$("#detail_id").on("change", function () {
		toggleLoadingIcon(true);
		fetchData();
	});
	$(document).ready(function() {
		fetchData();
	});
	function fetchData() {
		$.ajax({
			url: 'getBookingDetail/' + $("#detail_id").val(),
			type: 'GET',
			success: function (data) {
				$('#bookingDetailTable tbody tr').remove();
				for(var tmp in data.propertyDetail.project.paymentPlan) {
					var paymentCompleted = data.paymentData[tmp] ? 'checked=true' : '';
					var phaseCompleted = data.propertyDetail.block.progress[tmp].completed ? 'checked=true' : '';
					$('#bookingDetailTable').append('<tr><td>'
							+ data.propertyDetail.project.paymentPlan[tmp].name
							+ '</td><td><input type="checkbox" disabled="true" '
							+ phaseCompleted
							+ '></td><td><input type="checkbox" disabled="true" '
							+ paymentCompleted
							+ '></td></tr>');
				}
				toggleLoadingIcon(false);
			},
			error: function (e) {
				alert("Unable to connect to server...");
				toggleLoadingIcon(false);
			}
		});
	}
</script>
<div class="fivecol">
	<h3>Booking Details</h3>
	<hr>
	<form id="bookingDetailModel" action="addBookingDetail" method="post">
		<fieldset>
			<ol>
				<li>
					<label class="mediumRed">Select Property<span style="font-size: 80%"> (Format: 'Project' - 'Block' - 'Property Number'):</span></label>
					<select name="detail_id" id="detail_id">
						<c:forEach items="${bookedProperties}" var="property">
							<option value="${property.id}">${property.project.name} - ${property.propertyDetail.block.block} - ${property.propertyDetail.propertyNumber}</option>
						</c:forEach>
					</select>
					<span class="clr" id="sdate1"></span>
				</li>
			</ol>
		</fieldset>
	</form>
</div>
<div id="expenseGrid" class="sevencol last grid">
	<div class="creativetbl_container" id="block_progress_display">
		<table id="bookingDetailTable" class="tsc_table_s3" width="100%">
			<thead>
				<tr>
					<td>Phase</td>
					<td>Phase Completed</td>
					<td>Payment Done</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
