<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url var="resources" value="/resources"></c:url>
<link href="${resources}/css/custom_table.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var nameStr = "";
	var percentageStr = "";
	var counter = 0;
	$("a#fcbxLink").fancybox({
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

	$("#addButton").on("click", function() {
		$("#mainTable").append("<tr><td style='vertical-align:middle'><input type='text' class='phaseName'></input></td><td style='vertical-align:middle'><input type='text' class='numbox percentage'></input></td><td><input type='button' value='X' class='deleteRow'></td></tr>");	});
	$("#clearAllButton").on("click", function() {
		$("#mainTable tbody").remove();
	});
	$(".deleteRow").live("click", function() {
		$(this).parents("tr").remove();
	});
	$(".numbox").live("keypress", function (e) {
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			return false;
		}
	});
	$(".phaseName").live("keypress", function (e) {
		if (e.which != 8 && e.which != 0 && (e.which == 58)) {
			return false;
		}
	});
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(ADD_PROJECT_PAYMENT_PLAN);
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			toggleLoadingIcon(false);
		}
	}
	function submitForm() {
		$("#mainTable tbody tr").each(function() {
			var percVal = $(this).find(".percentage").val();
			nameStr += ($(this).find(".phaseName").val() + ":");
			percentageStr += (percVal + ":");
			counter += parseInt(percVal);
		});
		if(nameStr.length > 0)
			nameStr = nameStr.substring(0, nameStr.length - 1);
		if(percentageStr.length > 0)
			percentageStr = percentageStr.substring(0, percentageStr.length - 1);
		if(counter != 100) {
			counter = 0;
			alert("Sum of % of completion must be 100");
			return false;
		}
		toggleLoadingIcon(true);
		$("#name_str").val(nameStr);
		$("#percentage_str").val(percentageStr);
		$("#projectPaymentPlan").ajaxForm(options).submit();
		return false;
	}
</script>
<style>

</style>

<div class="fivecol">
	<form action="addProjectPayment" method="post" id="projectPaymentPlan">
		Select Project:
		<select id="project_id" name="project_id">
			<c:forEach items="${projects }" var="project">
				<option value="${project.id }">${project.name }</option>
			</c:forEach>
		</select>
	   	<input type="button" value="Add" id="addButton" title="Add" style="width: auto;margin: 10px; margin-left: 0px"/>
	   	<input type="button" value="Clear All" id="clearAllButton" title="Remove all records" style="width: auto;margin: 10px;margin-right: 0px;float: right;"/>
	    <div class="creativetbl_container">
	    	<table class="bordered" id="mainTable">
				<thead>
					<tr>
						<th width="65%">Phase</th>
						<th>%</th>
						<th width="10%">Operation</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
	    </div>
	    <input type="hidden" name="name_str" id="name_str">
	    <input type="hidden" name="percentage_str" id="percentage_str">
	    <input type="submit" value="Submit" style="margin-top: 10px" onclick="return submitForm()">
    </form>
</div>
<div id="expenseGrid" class="sevencol last grid">
	<div class="creativetbl_container">
		<table id="projectContainer">
			<thead>
				<tr>
					<th width="15%">Index</th>
					<th>Project - Block</th>
					<th width="15%"></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="counter" value="1" />
				<c:forEach items="${projects}" var="project">
						<tr>
							<td>${counter }</td>
							<td><a id="fcbxLink" href="showPaymentPlan?id=${project.id }">${project.name }</a></td>
							<td></td>
						</tr>
						<c:set var="counter" value="${counter + 1 }" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
