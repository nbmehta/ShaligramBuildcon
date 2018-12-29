<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			loadPage(MANAGE_PARKING_BOOKING_DETAIL);
			$.fancybox.close();
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			$.fancybox.close();
		}
	}

	function submitForm() {
		toggleLoadingIcon(true);
		$('#updateParkingBookingDetailModel').ajaxForm(options).submit();
		return false;
	}
	$(document).ready(function() {
		$("#updateParkingBookingDetailModel").on("submit", function() {
			var ret = true;
		

			if (($(".popup #bookingDate").val()) == '') {
				
				$("#sdatee").text("*Starting Date is Required");
				ret = false;
			}
			


			toggleLoadingIcon(false);
			return ret;
		})
	})
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Edit Parking
		Booking Detail</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last popup">
		<form:form modelAttribute="updateParkingBookingDetailModel" method="post"
			action="updateParkingBookingDetail">
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
				<li>
					<label for="expenseName" class="mediumRed">Project :</label>
					<form:select path="project">
						<form:options items="${projects}" itemLabel="name" itemValue="id"/>
					</form:select>
				</li>
				
				<li><label for="expenseName" class="mediumRed">Extra Parking:</label>
				<form:select path="extraParking">
						<form:options items="${parkings}" itemLabel="parkingNo" itemValue="id"/>
					</form:select>
				</li>
				
				<li><label for="expenseName" class="mediumRed">Booking Date:</label>
				<form:input type="date" path="bookingDate" class="tour2" />
				<span class="clr" id="sdatee"></span></li>
				
				
				</li>
				
				<li><label for="expenseName" class="mediumRed">Member Detail:</label>
				<form:select path="memberDetail">
						<form:options items="${members}" itemLabel="name" itemValue="id"/>
					</form:select></li>
					
				<li><label for="expenseName" class="mediumRed">Booked By Employee:</label>
				<form:select path="bookedByEmployee">
						<c:forEach items="${employees }" var="emp">
							<form:option value="${emp.id }">${emp.firstName } ${emp.lastName }</form:option>
						</c:forEach>
					</form:select></li>
					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						
				<li><label for="expenseName" class="mediumRed">Resale   :</label>
				<form:checkbox  path="Resale" class="tour2" /></li><br>
				<li><label for="expenseName" class="mediumRed">Available For Resale:</label>
				<form:checkbox path="AvailableForResale" class="tour2" /></li><br>
				
			
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