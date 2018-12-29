<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
#mycss{
color:#a82618;
font-family: 'PerspectiveSansRegular', Arial, Helvetica, sans-serif;
}
</style>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">View Book Property
		</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Project Name: </b> ${members.name}</li>
						<li><b id="mycss">Permanent Address: </b>${members.permenantAddress}</li>
						<li><b id="mycss">Office Address: </b>${members.officeAddress}</li>
						<li><b id="mycss">Office Address: </b>${members.dateOfBirth}</li>
						<li><b id="mycss">Date Of Birth: </b>${members.age}</li>
						<li><b id="mycss">Age: </b> ${members.contactNo1}</li>
						<li><b id="mycss">ContactNo1: </b>${members.contactNo2}</li>
						<li><b id="mycss">ContactNo2: </b>${members.contactNo3}</li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Email: </b>${members.email}</li>
						<li><b id="mycss">Profession: </b>${members.profession}</li>
						<li><b id="mycss">PAN Number: </b>${members.PANNumber}</li>
						<li><b id="mycss">Marriage Anniversary Date: </b>${members.marriageAnniversaryDate}</li>
						

					</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>