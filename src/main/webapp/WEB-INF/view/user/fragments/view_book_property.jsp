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
						<li><b id="mycss">Project Name: </b> ${bookdetail.project.name}</li>
						<li><b id="mycss">Property Detail: </b>${bookdetail.propertyDetail.propertyNumber}</li>
						<li><b id="mycss">Booking Date: </b>${bookdetail.bookingDate}</li>
						<li><b id="mycss">Member Detail: </b>${bookdetail.memberDetail.firstName} ${bookdetail.memberDetail.lastName}</li>
						<li><b id="mycss">Booked By Employee: </b>${bookdetail.bookedByEmployee.firstName}</li>
						<li><b id="mycss">Note: </b> ${bookdetail.note}</li>
						 <li><b id="mycss">Resale: </b><br/><input type='checkbox' disabled='true' <c:if test='${bookdetail.resale}'>checked</c:if>></input></li><br>
						<li><b id="mycss">Availabe For Resale: </b><br/><input type='checkbox' disabled='true' <c:if test='${bookdetail.availableForResale}'>checked</c:if>></input></li>
 
					</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>