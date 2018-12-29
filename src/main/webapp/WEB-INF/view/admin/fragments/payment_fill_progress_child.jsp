<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	$(".inputToggle").change(function() {
		var enclosingColumn = $("#progressTable").find("." + $(this).parent().attr("name")); 
		enclosingColumn.find("input[type='date']").prop('disabled', $(this).is(':checked'));
		if($(this).is(':checked'))
			enclosingColumn.addClass("highlightTD");
		else
			enclosingColumn.removeClass("highlightTD");
	});
</script>
<style>
.highlightTD {
	background: #C9EBBE;
}
table a:link {
	color: #666;
	font-weight: bold;
	text-decoration:none;
}
table a:visited {
	color: #999999;
	font-weight:bold;
	text-decoration:none;
}
table a:active,
table a:hover {
	color: #bd5a35;
	text-decoration:underline;
}
table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	background:#eaebec;
	margin-top:20px;
	border:#ccc 1px solid;

	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;

	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child {
	text-align: left;
	padding-left:20px;
}
table tr:first-child th:first-child {
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child {
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr {
	text-align: center;
	padding-left:20px;
}
table td:first-child {
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table td {
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;

	background: #fafafa;
	background: -webkit-gradient(linear, left top, left bottom, from(#fbfbfb), to(#fafafa));
	background: -moz-linear-gradient(top,  #fbfbfb,  #fafafa);
}
table tr.even td {
	background: #f6f6f6;
	background: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f6f6f6));
	background: -moz-linear-gradient(top,  #f8f8f8,  #f6f6f6);
}
table tr:last-child td {
	border-bottom:0;
}
table tr:last-child td:first-child {
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child {
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}
table td {
	background: #f2f2f2;
}
</style>
<table id="progressTable">
	<c:if test="${fn:length(paymentPlan) gt 0}">
		<thead>
			<tr>
				<th>Block</th>
				<c:set var="percCounter" value="0" />
				<c:forEach items="${paymentPlan }" var="plan">
					<c:set var="percCounter" value="${percCounter + plan.completedPercentage }" />
					<th>${plan.name } (${percCounter }%)</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${blocks}" var="block">
				<tr>
					<td rowspan="2">${block.block }</td>
					<c:set var="counter" value="0" />
					<c:set var="looper" value="0" />
					<c:set var="counter2" value="0" />
					<c:set var="looper2" value="0" />
					<c:forEach items="${paymentPlan }" var="plan">
						<td name="col_${block.blockId }_${counter }" class="col_${block.blockId }_${counter } <c:if test="${block.progress[looper].completed }">highlightTD</c:if>" >
							<input type="checkbox" class="inputToggle" name="cbx_${block.blockId }_${counter }" <c:if test="${block.progress[looper].completed }">checked</c:if>>
							<c:if test="${block.progress[looper].completed }">(Completed on <fmt:formatDate pattern='yyyy-MM-dd' value="${block.progress[looper].finalDateOfCompletion }" />)</c:if>
							<br/>Completion Date:
							<input type="date" class="dateInput" name="dtp_${block.blockId }_${counter }" value="<fmt:formatDate pattern='yyyy-MM-dd' value="${block.progress[looper].dateOfCompletion }" />" <c:if test="${block.progress[looper].completed }">disabled='true'</c:if>>
							<c:set var="counter" value="${counter + 1 }" />
							<c:set var="looper" value="${looper + 1 }" />
							<c:set var="looper" value="${looper % fn:length(paymentPlan)}" />
						</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${paymentPlan }" var="plan">
						<td name="col_${block.blockId }_${counter2 }" class="col_${block.blockId }_${counter2 } <c:if test="${block.progress[looper2].completed }">highlightTD</c:if>" >
							Review Date:
							<input type="date" class="dateInput" name="dtp_review_${block.blockId }_${counter2 }" value="<fmt:formatDate pattern='yyyy-MM-dd' value="${block.progress[looper2].reiewDates[fn:length(block.progress[looper2].reiewDates) - 1] }" />" <c:if test="${block.progress[looper2].completed }">disabled='true'</c:if> >
							<c:set var="counter2" value="${counter2 + 1 }" />
							<c:set var="looper2" value="${looper2 + 1 }" />
							<c:set var="looper2" value="${looper2 % fn:length(paymentPlan)}" />
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</c:if>
</table>
