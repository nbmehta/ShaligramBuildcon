<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

</script>
<div class="elevencol">
    <div class="creativetbl_container">
    	<table id="employeeContainer">
			<thead>
				<tr>
					<th style="display:none;"></th>
					<th>Unit no</th>
					<th>Date of Booking</th>
					<th>Remarks</th>
					<th>Name</th>
					<th>Age</th>
					<th>DOB</th>
					<th>Booked by</th>
					<th>Permanent Address</th>
					<th>office address</th>
					<th>contact 1</th>
					<th>contact 2</th>
					<th>Email id</th>
					<th>Bussiness</th>
					<th>pan card</th>
					<th>marrage</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:forEach items="${bookingdetail}" var="detail">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td style="display:none;" class="propID">${detail.id }</td>
						<td><a href="#" onclick="onOpenPopUp2(this);"></a>${detail.propertyDetail.block.block } - ${detail.propertyDetail.propertyNumber }</td>
						<td><fmt:formatDate value="${detail.bookingDate}" pattern="dd/MM/yyyy"/> </td>
						<td>${detail.note}</td>
						<td>${detail.memberDetail.firstName } ${detail.memberDetail.firstName} </td>
						<td>${detail.memberDetail.details.age}</td>
						<td>${detail.memberDetail.details.dateOfBirth}</td>
						<td>${detail.bookedByEmployee.firstName }</td>
						<td>${detail.bookedByEmployee.address1 }</td>
						<td>${detail.bookedByEmployee.address2 }</td>
						<td>${detail.memberDetail.details.contactNo1}</td>
						<td>${detail.memberDetail.mobileNo}</td>
						<td>${detail.memberDetail.email}</td>
						<td>${detail.memberDetail.details.profession }</td>
						<td>${detail.memberDetail.details.PANNumber }</td>
						<td>${detail.memberDetail.details.marriageAnniversaryDate }</td>
						<td width="40"><img class="invOption"
							src="../resources/images/invOption.png" rel=""
							onclick="editOptions(this);"></td>						
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
		<center>
			<br/><br/>
			<font color="red">${sessionScope.errorMsg }</font>
		</center>
		<c:remove var="errorMsg" scope="session"/>
    </div>
</div>
