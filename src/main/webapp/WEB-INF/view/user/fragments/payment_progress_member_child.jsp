<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.highlightTD {
	background: #C9EBBE;
}
</style>
<script type="text/javascript">
$(".inputToggle").change(function() {
	var checked = $(this).is(':checked');
	$(this).parent().parent().children().each(function() {
		if(checked)
			$(this).addClass("highlightTD");
		else
			$(this).removeClass("highlightTD");
	})
});
</script>
<center>
	<table id="progressTable">
		<thead>
			<tr>
				<td style="border-top-color: #e0e0e0;">Phase name</td>
				<td style="border-top-color: #e0e0e0;">Payment Received</td>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${paymentHistory}" var="history" varStatus="status">
				<tr>
					<td style="vertical-align: middle; text-align: center; border-top-color:#E0E0E0;" class="<c:if test="${history}">highlightTD</c:if>">${paymentPlan[status.index].name}</td>
					<td style="vertical-align: middle; text-align: center; border-top-color:#E0E0E0;" class="<c:if test="${history}">highlightTD</c:if>"><input type="checkbox" class="inputToggle" style="vertical-align: middle; text-align: center;" name="box_${status.index }" <c:if test="${history}">checked="true"</c:if>></input></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" name="detailId" value="${id}">
</center>