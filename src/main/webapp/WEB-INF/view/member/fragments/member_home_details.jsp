<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="resources" value="/resources" />
<sec:authentication property="principal.employee" var="employee"/>
<style>
.clr {
	color: #a82618;
}
</style>
<div class="elevencol">
    <div class="creativetbl_container">
		<fieldset>
			<table width="100%">
				<tr>
					<td width="49%">
						<ol>
							<h3>Basic Details</h3>
							<hr>
							<li><b class="clr">Name: </b> ${employee.firstName} ${employee.middleName} ${employee.lastName}</li>
							<li><b class="clr">Date of Birth: </b> ${employee.details.dateOfBirth}</li>
							<li><b class="clr">Age: </b> ${employee.details.age}</li>
							<li><b class="clr">Profession: </b> ${employee.details.profession}</li>
							<li><b class="clr">Anniversary Date: </b> ${employee.details.marriageAnniversaryDate}</li>
							<li><b class="clr">PAN Number: </b> ${employee.details.PANNumber}</li>
						</ol>
					</td>
					<td></td>
					<td width="49%">
						<ol>
							<h3>Contact Details</h3>
							<hr>
							<li><b class="clr">Email: </b> ${employee.email}</li>
							<li><b class="clr">Mobile No: </b> ${employee.mobileNo}</li>
							<li><b class="clr">Phone No: </b> ${employee.phone}</li>
							<li><b class="clr">Emergency No: </b> ${employee.details.contactNo1}</li>
						</ol>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</div>
