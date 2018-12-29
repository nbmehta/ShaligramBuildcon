<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
var options = {
	success : function(data) {
		$.fancybox.close();
		loadPage(FILL_PROGRESS);
	},
	error : function(e) {
		alert("Failed to send request to server, Please try again later...!");
		$.fancybox.close();
	}
}
	
$("#member_id").on("change", function() {
	var memId = $(this).val(); 
	if(memId != "#") {
		$.ajax({
			url : 'getPropertiesBookedByMember/'+ memId,
			type : 'GET',
			success : function(data) {
				$('#block_id').find('option').remove().end();
				for (division in data)
					$('#block_id').append($('<option>',
						{
							value : data[$('#block_id > option').length].id,
							text :
								data[$('#block_id > option').length].propertyDetail.project.name + " - " +
								data[$('#block_id > option').length].propertyDetail.block.block + " - " +
								data[$('#block_id > option').length].propertyDetail.propertyNumber
						}
					));
				if(data.length > 0) {
					// Now update progress plan also
					reloadChild();
				}
			},
			error : function(e) {
				alert("Unable to connect to server...");
			}
		});
	}
});
function submitForm() {
	toggleLoadingIcon(true);
	$('#updateMemberPayment').ajaxForm(options).submit();
	return false;
}
$("#block_id").on("change", function() {
	reloadChild();
});
function reloadChild() {
	toggleLoadingIcon(true);
	execLoadWrapper('#progress_plan_holder', 'fragment/fragment_payment_progress_member_child/' + $("#block_id").val(), function() {
		toggleLoadingIcon(false);
	});
}
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Update Payment Progress
	</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last" style="margin-top: 10px">
		<form action="updatePaymentData" method="post" id="updateMemberPayment">
			Select Member:
			<select id="member_id" name="member_id">
				<option value="#" disabled selected>--- Select ---</option>
				<c:forEach items="${members }" var="member">
					<option value="${member.id }">${member.firstName } ${member.middleName } ${member.lastName }</option>
				</c:forEach>
			</select>
			Select Booked Property <span style="font-size: 80%">(Format: 'Project' - 'Block' - 'Property Number')</span>:
			<select id="block_id" name="block_id"></select>
			<div id="progress_plan_holder"></div>
			<center>
				<input type="submit" id="submitButton" value="Update Progress" style="margin-top: 10px; float:none; width: 49%;" onclick="javascript: return submitForm();"/>
			</center>
			<div class="clear"></div>
		</form>
	</div>
</div>