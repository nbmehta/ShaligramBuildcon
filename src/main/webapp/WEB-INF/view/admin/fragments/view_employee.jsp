<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
#mycss {
	color: #a82618;
	font-family: 'PerspectiveSansRegular', Arial, Helvetica, sans-serif;
}
</style>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">View Employee
	</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<h3>New Employee</h3>
						<hr>
						<li><b id="mycss">First Name: </b> ${newUser.firstName}</li>
						<li><b id="mycss">Middle Name: </b>${newUser.middleName}</li>
						<li><b id="mycss">Last Name: </b>${newUser.lastName}</li>
						<li><b id="mycss">AddressLine 1: </b> ${newUser.address1}</li>
						<li><b id="mycss">AddressLine 2: </b>${newUser.address2}</li>
						<li><b id="mycss">City: </b>${newUser.city}</li>
						<li><b id="mycss">State: </b>${newUser.state.stateName}</li>
					</ol>
				</fieldset>
				<fieldset>
					<ol>
						<h3>New Employee Contact Detail</h3>
						<hr>
						<li><b id="mycss">Phone: </b>${newUser.phone}</li>
						<li><b id="mycss">Mobile: </b>${newUser.mobileNo}</li>
						<li><b id="mycss">Email: </b>${newUser.email}</li>
					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<h3>New Employee Credentials</h3>
						<hr>
						<li><b id="mycss">Username: </b>${newUser.username}</li>
				</ol>
				</fieldset>
					<fieldset>
					<ol>
						<h3>New Employee Status</h3>
						<hr>
						
						<li><b id="mycss">Employee Role: </b>${newUser.role.name}</li>
						<li><b id="mycss">Extension: </b>${newUser.extension}</li>
						<li><b id="mycss">Active:</b><br/><input type='checkbox' disabled='true' <c:if test='${newUser.active}'>checked</c:if>></input></li>
				</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>