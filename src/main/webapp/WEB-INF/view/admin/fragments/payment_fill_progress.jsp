<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
$(document).ready(function() {
	reloadFragment();
});
$("#project_id").on("change", function() {
	reloadFragment();
});
function reloadFragment() {
	toggleLoadingIcon(true);
	execLoadWrapper('#userInputWrapper', 'fragment/getFillPaymentChildView/' + $("#project_id").val(), function() {
		toggleLoadingIcon(false);
	});
}

var options = {
	success : function(data) {
		loadPage(FILL_PROGRESS);
	},
	error : function(e) {
		alert("Failed to send request to server, Please try again later...!");
		toggleLoadingIcon(false);
	}
}

function submitForm() {
	toggleLoadingIcon(true);
	$('#progressUpdateForm').ajaxForm(options).submit();
	return false;
}

function showMemberPaymentProgressDialog() {
	$.fancybox({
		'href' : "fragment/fragment_payment_progress_member/"
			+ $('#project_id').val(),
		'autoDimensions' : false,
		'scrolling' : 'no',
		'width' : 800,
		'height' : 600,
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
</script>
<div class="elevencol">
	<form action="updateProgressForBlock" method="post" id="progressUpdateForm">
		Select Project:
		<select id="project_id" name="project_id">
			<c:forEach items="${projects }" var="project">
				<option value="${project.id }">${project.name }</option>
			</c:forEach>
		</select>
		<div id="userInputWrapper" style="overflow-x: auto"></div>
		<input type="submit" id="submitButton" value="Update Progress" style="margin-top: 10px; margin-right: 5px; width: 49%;" onclick="javascript: return submitForm();">
		<input type="button" id="updateProgressForMember" value="Update Member's Payment" style="margin-top: 10px; margin-left: 5px; width: 49%; float: right;" onclick="javascript: showMemberPaymentProgressDialog();">
	</form>
</div>
