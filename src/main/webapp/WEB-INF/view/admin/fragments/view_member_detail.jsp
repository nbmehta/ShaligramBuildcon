<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
#mycss{
color:#a82618;
font-family: 'PerspectiveSansRegular', Arial, Helvetica, sans-serif;
}
</style>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">View member Detail
		</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Member Name: </b> ${members.firstName} ${members.middleName} ${members.lastName}</li>
						<li><b id="mycss">Permanent Address: </b>${members.address1}</li>
						<li><b id="mycss">Office Address: </b>${members.address2}</li>
						<li><b id="mycss">Date Of Birth: </b>${members.details.dateOfBirth}</li>
						<li><b id="mycss">Age: </b>${members.details.age}</li>
						<li><b id="mycss">Mobile No: </b> ${members.mobileNo}</li>
						<li><b id="mycss">Phone No: </b>${members.phone}</li>
						<li><b id="mycss">Emergency Contact No: </b>${members.details.contactNo1}</li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Email: </b>${members.email}</li>
						<li><b id="mycss">Profession: </b>${members.details.profession}</li>
						<li><b id="mycss">PAN Number: </b>${members.details.PANNumber}</li>
						<li><b id="mycss">Marriage Anniversary Date: </b>${members.details.marriageAnniversaryDate}</li>
					</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>