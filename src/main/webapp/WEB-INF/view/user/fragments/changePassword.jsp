<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var options = {
		success : function(data) {
			toggleLoadingIcon(false);
			$.fancybox.close();
		},
		error : function(e) {
			toggleLoadingIcon(false);
			$("#msg").text("* Please enter correct old password");
		}
	}
	function submitForm2() {
		var ret = true;
		if($("#newPassword").val() != $("#confirmNewPassword").val()) {
			$("#msg").text("* New Password and Confirm New Password must be same.");
			ret = false;
		} else {
			toggleLoadingIcon(true);
			$('#changePasswordModel').ajaxForm(options).submit();
		}
		return ret;
	}
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">	Change Password</span>
</div>
<div class="fancybottomContent" style="width:945px;">
	<div id="contentWrapper" class="ninecol last">
		<form:form modelAttribute="changePasswordModel" method="post"
			action="doChangePassword">
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						
				<li><label for="expenseName" class="mediumRed">	Enter Old Password:</label>
				<form:password  path="oldPassword" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">	Enter New Password:</label>
				<form:password  path="newPassword" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">	Confirm New Password:</label>
				<input type="password" id="confirmNewPassword" name="confirmNewPassword" class="tour2" /></li>
				<form:hidden path="id"/>
				<input type="button" id="sbmt" onclick="return submitForm2();" value="Update">
				<span style="color:red" id="msg" >
				</span>
				</ol>
				</fieldset>
			</div>
			
		</form:form>
		<div class="clear"></div>
	</div>
</div>