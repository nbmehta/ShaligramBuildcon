<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
#mycss{
color:#a82618;
font-family: 'PerspectiveSansRegular', Arial, Helvetica, sans-serif;
}
</style>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">View Property Details
		</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Project Name: </b> ${propDetails.project.name }</li>
						<li><b id="mycss">Property Type: </b>${propDetails.propertyType.name}</li>
						<li><b id="mycss">Property Number: </b>${propDetails.propertyNumber}</li>
						<li><b id="mycss">Block: </b>${propDetails.block.block}</li>
						<li><b id="mycss">Floor Number: </b>${propDetails.floorNumber}</li>
						<li><b id="mycss">Flat Area: </b> ${propDetails.flatArea}</li>
						<li><b id="mycss">Build Up Area: </b>${propDetails.buildUpArea}</li>
						<li><b id="mycss">Undivided Land Area: </b>${propDetails.undividedLandArea}</li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Unit: </b>${propDetails.unit.name}</li>
						<li><b id="mycss">Description: </b>${propDetails.description}</li>
						
						

					</ol>
				</fieldset>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>