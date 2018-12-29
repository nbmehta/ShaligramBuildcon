<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
function onViewInquiry(e) {
	$.fancybox({
		'href' : 'fragment/fragement_view_all_inquiry?id=' + e,
		'autoDimensions': false,
	    'scrolling': 'no',
	    'width': 850,
	    'height': 700,
	    'overlayShow': true,
	    cyclic: false,
	    'transitionIn': 'none',
	    'transitionOut': 'none',
	    onComplete: function() {
	        bottomContentHeightChange();
	    },
	    onClosed: function() {
	        //refreshGrid("ct");
	    }
	});
}


function sendDeleteRequest(id) {
	toggleLoadingIcon(true);
	$.ajax({
	   type: "POST",
	   url: "deleteInquiry",
	   data: "id=" + id,
	   success: function(){
			$.fallr('hide');
			loadPage(VIEW_INQUIRY);
	   },
	   error: function() {
		   alert("Failed to connect to server, Please try again later...");
	   }
	});
}

function onDeleteClick(e) {
	$.fallr('show', {
		useOverlay: true,
		position: 'center',
		buttons: {
			button1: {
				text: 'Delete',
				onclick: function() {
					sendDeleteRequest(e);
				}
			},
			button2: {
				text: 'Cancel'
			}
		},
		content : '<div id="fallrHeadline">'
				+ '<h5>Warning</h5>'
				+ '</div><div id="fallrContent">'
				+ '<p class="mediumRed">Are you sure want to delete?</p></div>',
		icon: 'error'
	});
	fallrCustom();
}
</script>
<div class="elevencol">
    <div class="creativetbl_container">
    	<table id="inquiryContainer">
			<thead>
				<tr>
					<th style="display:none;"></th>
					<th>Visit Site </th>
					<th>Visitor Name</th>
					<th>Visit Date</th>
					<th>Contact Number</th>
					<th>Remark</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
    </div>
</div>
