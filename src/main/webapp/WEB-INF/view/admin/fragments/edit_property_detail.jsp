<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			loadPage(MANAGE_PROPERTY_DETAIL);
			$.fancybox.close();
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			$.fancybox.close();
		}
	}

	function submitForm() {
		toggleLoadingIcon(true);
		$('#updatePropertyDetailModel').ajaxForm(options).submit();
		return false;
	}
	$(document).ready(function() {
		$("#updatePropertyDetailModel").on("submit", function() {
			var ret = true;
			var pattern=/^([0-9])*\.*([0-9])+$/;
			$(".property").each(function(){
				
				if($(this).val()=='') {
					
				$("#" + $(this).attr("id") + "_spann").text("* Field is required");
					ret = false;
				}
				else if(!pattern.test($(this).val())){
				$("#" + $(this).attr("id") + "_spann").text("* Only digits are allowed");
					ret = false;
				}
				
				
			})
			toggleLoadingIcon(false);
		return ret;
		})
	})
</script>
<style>
.clr {
	color: red;
}
</style>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Edit
		Property Details</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form:form modelAttribute="updatePropertyDetailModel" method="post"
			action="updatePropertyDetail">
			<div class="fivecol regbox">
				<fieldset>
					<ol>
						<li>
					<label for="expenseName" class="mediumRed">Project :</label>
					<form:select path="project" class="volatile">
						<form:options items="${projects}" itemLabel="name" itemValue="id"/>
					</form:select>
				</li>
				<li>
					<label for="expenseName" class="mediumRed">Property Type:</label>
					<form:select path="propertyType" class="volatile">
						<form:options items="${propTypes }" itemLabel="name" itemValue="propertyTypeID"/>
					</form:select>
				</li>
				<li>
					<label for="expenseName" class="mediumRed">Block:</label>
					<form:select path="block" class="volatile">
						<form:options items="${blockTypes}" itemLabel="block" itemValue="blockId"/>
					</form:select>
				</li>
				<li>
					<label for="expenseName" class="mediumRed">Property Plan:</label>
					<form:select path="projectPropertyPlan">
						<form:options items="${plans }" itemLabel="planName" itemValue="id"/>
					</form:select>
				</li>
					<li>
					<label for="expenseName" class="mediumRed">Property Number:</label>
					<form:input type="text" path="propertyNumber" class="tour2 property" /> 
					<span class="clr" id="propertyNumber_spann"></span>
						</li>
				
				<li><label for="expenseName" class="mediumRed">Floor Number:</label>
				<form:input type="text" path="floorNumber" class="tour2" /></li>
				
				<li><label for="expenseName" class="mediumRed">Flat Area:</label>
				<form:input type="text" path="flatArea" class="tour2  property"  />
				<span id="flatArea_spann" class="clr"></span></li>
				
				<li><label for="expenseName" class="mediumRed">BuildUp Area:</label>
				<form:input type="text" path="buildUpArea" class="tour2 property" />
				<span id="buildUpArea_spann" class="clr"></span></li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
					
				
				<li><label for="expenseName" class="mediumRed">Undivided Land Area:</label>
				<form:input type="text" path="undividedLandArea" class="tour2 property" />
				<span id="undividedLandArea_spann" class="clr"></span></li>
				<li>
					<label for="expenseName" class="mediumRed">Unit:</label>
					<form:select path="unit">
						<form:options items="${measurements }" itemLabel="name" itemValue="id"/>
					</form:select>
				</li>
				
				<li><label for="expenseName" class="mediumRed">Description:</label>
				<form:input type="text" path="description" class="tour2" /></li>
						<form:hidden path="id" />
						<li>
							<button type="submit" value="Add User"
								class="blueButton submit fallr-button" id="submit"
								onclick="javascript: return submitForm();">Update
								Project</button>
							<button type="reset" value="Clear" class="submit fallr-button"
								id="clear">Clear</button>
						</li>
					</ol>
				</fieldset>
			</div>
		</form:form>
		<div class="clear"></div>
	</div>
</div>