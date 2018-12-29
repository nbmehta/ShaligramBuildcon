<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			loadPage(MANAGE_MEMBERDETAIL);
			$.fancybox.close();
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			$.fancybox.close();
		}
	}

	function submitForm() {
		toggleLoadingIcon(true);
		$('#updateMemberDetailModel').ajaxForm(options).submit();
		return false;
	}
	$(document).ready(function() {
		$("#updateMemberDetailModel").on("submit", function() {
			var ret = true;
			var pattern=/^([0-9])*\.*([0-9])+$/;
			var ptr= /[0-9]{10}\b/;
			var ptr1=/[0-9]/;
			var pattern1= /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			$(".memberValidation1").each(function(){
				
				if(($(this).val()=='')&&(!($(this).attr("id")=='contactNo2'))&&(!($(this).attr("id")=='contactNo3'))&&(!($(this).attr("id")=='marriageAnniversaryDate'))){
				$("#" + $(this).attr("id") + "_sspan").text("* Field is required");
					ret = false;
				}
				if(ptr1.test($(this).val())&&(!$(this).val()=='')&&(($(this).attr("id")=='name')))
					{
					$("#" + $(this).attr("id") + "_sspan").text("*only alpahabet allow");
					ret = false;
					}
			//	if(ptr1.test($(this).val())&&(!$(this).val()=='')&&(!($(this).attr("id")=='permenantAddress'))&&(!($(this).attr("id")=='contactNo1'))&&(!($(this).attr("id")=='officeAddress'))&&(!($(this).attr("id")=='dateOfBirth'))&&(!($(this).attr("id")=='age'))&&(!($(this).attr("id")=='email'))&&(!($(this).attr("id")=='profession'))){
			//		$("#" + $(this).attr("id") + "_span").text("*only alpahabet allow");
				//	ret = false;
				//}
				
				else{$(this).attr("id");
				switch($(this).attr("id")){
				case "dateOfBirth":
				case "PANNumber":
				case "marriageAnniversaryDate":
					
					if($(this).val().length>50)
						{
						
						$("#" + $(this).attr("id") + "_sspan").text("*Maximum length is 50");
						ret = false;
						}
					break;
					
				case "name":
				case "permenantAddress":
					
					if($(this).val().length>255)
					{
					
					$("#" + $(this).attr("id") + "_sspan").text("*Maximum length is 255");
					ret = false;
					}
				break;
				
				case "age":
				case "contactNo1":
				case "contactNo2":
				case "contactNo3":
			
					if($(this).val().length>30)
					{
					
					$("#" + $(this).attr("id") + "_sspan").text("*Maximum length is 30");
					ret = false;
					}
				if(((!ptr.test($(this).val()) || ($(this).val().length>10))&&($(this).attr("id")!="age"))&&(!$(this).val()==''))
						{
						$("#" + $(this).attr("id") + "_sspan").text("*Length should be of 10 digits.");
						ret = false;
						}
				if(!pattern.test($(this).val())&&(!$(this).val()=='')){
						$("#" + $(this).attr("id") + "_sspan").text("*only digits are allowed");
						ret = false;
					}
				break;
				
				case "email":
				case "profession":
					
					if((($(this).attr("id")=='email'))&&!(pattern1.test($(this).val())))
					{
					$("#" + $(this).attr("id") + "_sspan").text("* Not valid Email");
					ret = false;
					
					}
					
					if($(this).val().length>200)
					{
					
					$("#" + $(this).attr("id") + "_sspan").text("*Maximum length is 200");
					ret = false;
					}
				break;
				}
				}
				})
			toggleLoadingIcon(false);
		return ret;
		});
	})
	
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Edit
		Member Detail</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form:form modelAttribute="updateMemberDetailModel" method="post"
			action="updateMemberDetail">
			<div class="fivecol regbox">
				<fieldset>
					<ol>
						<li>
							<label for="expenseName" class="mediumRed">First Name:</label>
							<form:input type="text" path="firstName" class="tour2 memberValidation1" />
							<span class="clr" id="name_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Middle Name:</label>
							<form:input type="text" path="middleName" class="tour2 memberValidation1" />
							<span class="clr" id="name_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Last Name:</label>
							<form:input type="text" path="lastName" class="tour2 memberValidation1" />
							<span class="clr" id="name_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Permanent Address:</label>
							<form:input type="text" path="address1" class="tour2 memberValidation1" />
							<span class="clr" id="permenantAddress_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Office Address:</label>
							<form:input type="text" path="address2"	class="tour2 memberValidation1" />
							<span class="clr" id="officeAddress_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Date	Of Birth:</label>
							<form:input type="date" path="details.dateOfBirth" class="tour2 memberValidation1" />
							<span class="clr" id="dateOfBirth_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Age:</label>
							<form:input type="text" path="details.age" class="tour2 memberValidation1" />
							<span class="clr" id="age_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Mobile No:</label>
							<form:input type="text" path="mobileNo" class="tour2 memberValidation1" />
							<span class="clr" id="contactNo1_sspan"></span>
						</li>
					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li>
							<label for="expenseName" class="mediumRed">Phone No:</label>
							<form:input type="text" path="phone" class="tour2 memberValidation1" />
							<span class="clr" id="contactNo2_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Emergency Contact Number:</label>
							<form:input type="text" path="details.contactNo1" class="tour2 memberValidation1" />
							<span class="clr" id="contactNo3_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Email:</label>
							<form:input type="text" path="email" class="tour2 memberValidation1" />
							<span class="clr" id="email_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Profession:</label>
							<form:input type="text" path="details.profession" class="tour2 memberValidation1" />
							<span class="clr" id="profession_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">PAN Number:</label>
							<form:input type="text" path="details.PANNumber" class="tour2 memberValidation1" />
							<span class="clr" id="PANNumber_sspan"></span>
						</li>
						<li>
							<label for="expenseName" class="mediumRed">Marriage	Anniversary Date:</label>
							<form:input type="text" path="details.marriageAnniversaryDate" class="tour2 memberValidation1" />
						<li>
							<form:hidden path="id" />
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