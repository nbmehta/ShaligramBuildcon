<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
function onChangePassword(e) {
	$.fancybox({
		'href' : "changePassword",
		'autoDimensions' : false,
		'scrolling' : 'no',
		'width' : 450,
		'height' : 400,
		'overlayShow' : true,
		cyclic : false,
		'transitionIn' : 'none',
		'transitionOut' : 'none',
		onComplete : function() {
			bottomContentHeightChange();
		},
		onClosed : function() {

		}
	});
}
</script>
<sec:authentication property="principal.employee" var="employee"/>
<div id="toppanel">
	<div id="panel" class="topBlueMenu">
		<div class="wrapper_menu">
			<ul class="menu">
				<li class="nodrop"><a id="dashboardLink" class="tabletSize" href="">Home</a></li>
				<li class="right"><a href="#" class="drop" address="true">${employee.firstName } ${employee.lastName }</a>
					<div class="dropdown_1column align_right">
						<div class="">
							<ul class="subMenu">
								<li><a class="iframe newCloseButton" href="#" onclick="javascript: return onChangePassword();" address="true">Change Password</a></li>
								<c:url var="logout_url" value="/logout" />
								<li><a href="javascript:document.getElementById('logoutForm').submit();" class="iframe newCloseButton" address="true">Logout</a></li>
							</ul>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
<!-- 
	<div id="favoriteLinks">
		<div class="row">
			<ul>
				<li class="floatLeft favoriteLink"><a href="idPrint.php" id="a_fav1" address="true">Cloud Clock<span	class="hideExtraText"> ID Printing</span><span class="hide">...</span></a></li>
				<li class="floatLeft favoriteLink"><a href="projectTimecard.php" id="a_fav2" address="true">Project's T<span class="hideExtraText">imecard</span><span class="hide">...</span></a></li>
				<li class="floatLeft favoriteLink"><a href="invoicePayment.php"	id="a_fav3" address="true">Record New <span	class="hideExtraText">Payment</span><span class="hide">...</span></a></li>
				<li class="floatLeft favoriteLink"><a href="hourlyRate.php"	id="a_fav4" address="true">Billing Rat<span class="hideExtraText">es</span><span class="hide">...</span></a></li>
				<li class="floatLeft favoriteLink"><a href="clientGrid.php" id="a_fav5" address="true">Client Grid<span	class="hideExtraText"> Dashboard Panel</span><span class="hide">...</span></a></li>
				<li class="floatLeft favoriteLink"><a href="cloudClockScanTimes.php" id="a_fav6" address="true">Cloud Clock<span class="hideExtraText"> Times</span><span class="hide">...</span></a></li>
				<li class="floatRight tabletSize"><a class="quickSearch" address="true">Quick Search</a></li>
			</ul>
		</div>
	</div>
-->
	<form action="${logout_url }" method="post" id="logoutForm" style="display: none;">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</div>