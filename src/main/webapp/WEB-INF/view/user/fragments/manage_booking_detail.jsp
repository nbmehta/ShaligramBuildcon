<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	var propertyHolder;
	var selectedPropHolder;
    var counter = 0;
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_BOOKING_DETAIL);
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
		}
	}

	function updatePropertyTypeName() {
		toggleLoadingIcon(true);
		$('#fallrContent').ajaxForm(options).submit();
	}

	function sendDeleteRequest(id) {
		toggleLoadingIcon(true);
		$.ajax({
			type : "POST",
			url : "deleteBookingDetail",
			data : "id=" + id,
			success : function() {
				$.fallr('hide');
				loadPage(MANAGE_BOOKING_DETAIL);
			},
			error : function() {
				alert("Failed to connect to server, Please try again later...");
			}
		});
	}
	function onClickDetail(e) {
		$.fancybox({
			'href' : "fragment/fragment_view_book_property?id=" + $(e).parent().parent().find('.propID').text(),
			'autoDimensions': false,
		    'scrolling': 'no',
		    'width': 650,
		    'height': 400,
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
	
	function onViewBlocks(e) {
		selectedPropHolder = $("#bookingDetail\\.propertyDetail").val();
		if($("#bookingDetail\\.project").val() == "" || $("#block").val() == "") {
			alert("Please select project and block");
			return;
		}
		$.fancybox({
			'href' : "fragment/fragment_book_property?prjid=" + $("#bookingDetail\\.project").val() + "&blkid=" + $("#block").val(),
			'autoDimensions' : false,
			'width' : 850,
			'height' : 700,
			'overlayShow' : true,
			cyclic : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			onComplete : function() {
				bottomContentHeightChange();
			},
			onClosed : function() {
				$("#bookingDetail\\.propertyDetail").val(propertyHolder);
			}
		});
	}
	
	function onUpdateClick(e) {
		$.fancybox({
			'href' : "fragment/fragment_edit_booking_detail?id="
					+ $(e).closest('tr').find('.propID').text(),
			'autoDimensions' : false,
			'scrolling' : 'no',
			'width' : 850,
			'height' : 700,
			'overlayShow' : true,
			cyclic : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			onComplete : function() {
				bottomContentHeightChange();
			},
			onClosed : function() {
				//refreshGrid("ct");
			}
		});
	}

	function onDeleteClick(e) {
		var id = $(e).closest('tr').find('.propID').text();
		$.fallr('show', {
			useOverlay : true,
			position : 'center',
			buttons : {
				button1 : {
					text : 'Delete',
					onclick : function() {
						sendDeleteRequest(id);
					}
				},
				button2 : {
					text : 'Cancel'
				}
			},
			content : '<div id="fallrHeadline">'
					+ '<h5>Warning</h5>'
					+ '</div><div id="fallrContent">'
					+ '<p class="mediumRed">Are you sure want to delete?</p></div>',
			icon : 'error'
		});
		fallrCustom();
	}
	

	function submitForm() {
		toggleLoadingIcon(true);
		$('#bookingDetailModel').ajaxForm(options).submit();
		return false;
	}
	
	function reloadBlocks() {
		toggleLoadingIcon(true);
		$.ajax({
			url : 'getBlockForProject/'+ $("#bookingDetail\\.project").val(),
			type : 'GET',
			success : function(data) {
				$('#block').find('option').remove().end();
				for (division in data)
					$('#block').append($('<option>',
					{
						value : data[$('#block > option').length].blockId,
						text : data[$('#block > option').length].block
					}));
				reloadProperties();
				toggleLoadingIcon(false);
			},
			error : function(e) {
				if (counter == 5) {
					alert("Unable to connect to server...");
					counter = 0;
				} else {
					counter++;
				}
				toggleLoadingIcon(false);
			}
		});
	}
	
	function reloadProperties() {
		toggleLoadingIcon(true);
		$.ajax({
			url : 'getPropertiesForBlockOnlyNonBooked/'+ $("#block").val(),
			type : 'GET',
			success : function(data) {
				$('#bookingDetail\\.propertyDetail').find('option').remove().end();
				for (division in data)
				$('#bookingDetail\\.propertyDetail').append($('<option>',
				{
					value : data[$('#bookingDetail\\.propertyDetail > option').length].id,
					text : data[$('#bookingDetail\\.propertyDetail > option').length].propertyNumber
				}));
				toggleLoadingIcon(false);
			},
			error : function(e) {
				if (counter == 5) {
					alert("Unable to connect to server...");
					counter = 0;
				} else {
					counter++;
				}
				toggleLoadingIcon(false);
			}
		});
	}
	
	$(document).ready(function() {
		reloadBlocks();
		$("#bookingDetailModel").on("submit", function() {
			var ret = true;
			if (($("#bookingDetail\\.project").val()) == null) {

				$("#sdate1").text("*Field is Required");
				ret = false;
			}
			if (($("#block").val()) == null) {

				$("#sdate2").text("*Field is Required");
				ret = false;
			}
			if (($("#block").val()) == null) {

				$("#sdate2").text("*Field is Required");
				ret = false;
			}
			if (($("#bookingDetail\\.propertyDetail").val()) == null) {

				$("#sdate3").text("*Field is Required");
				ret = false;
			}
			if (($("#bookingDetail\\.memberDetail").val()) == null) {

				$("#sdate4").text("*Field is Required");
				ret = false;
			}
			if (($("#bookingDetail\\.bookedByEmployee").val()) == null) {

				$("#sdate5").text("*Field is Required");
				ret = false;
			}
			if (($("#bookingDetail\\.bookingDate").val()) == '') {

				$("#sdate").text("*Starting Date is Required");
				ret = false;
			} 
			else if($("#bookingDetail\\.note").val().length>500){
		    	$("#note_span").text("*Max length should be 500 characters");
		    	ret=false;
		    }
			toggleLoadingIcon(false);
			return ret;
		})
	})
	
	$("#project").on('change',function(e) {
		reloadBlocks();
	});
	$("#block").on('change',function(e) {
		reloadProperties();
	});

    var REGEX_EXCEL_FILES_ONLY = /^([a-zA-Z0-9\s_\\.\-:])+(.xls|.xlsx)$/;

    var excelImpoptions = {
        success : function(data) {
            $.fallr('hide');
            loadPage(MANAGE_BOOKING_DETAIL, function() {
                $("#bef_FileImpBut").text("File Import Success...!!!");
            });
        },
        error : function(e) {
            $.fallr('hide');
            toggleLoadingIcon(false);
            $("#bef_FileImpBut").text("Invalid file or Server Error...!!!");
        }
    }

    function submitFile() {
        toggleLoadingIcon(true);
        var fullPath = document.getElementById('file').value;
        if (fullPath) {
            var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
            var filename = fullPath.substring(startIndex);
            if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
                filename = filename.substring(1);
            }
            if(REGEX_EXCEL_FILES_ONLY.test(filename))
                $("#importBookingDetail").ajaxForm(excelImpoptions).submit();
            else {
                $("#bef_FileImpBut").text("Please select valid excel file. File extension must be *.xls or *.xlsx");
                toggleLoadingIcon(false);
                $.fallr('hide');
            }
        }
    }

    function handleFileUploadRequest() {
        $.fallr('show', {
            useOverlay: true,
            position: 'center',
            icon        : 'document',
            content     : '<div id="fallrHeadline">'
            + '<h5>Upload</h5>'
            + '</div><div id="fallrContent">'
            + '<p class="mediumRed">Select file to upload:</p>'
            + '<form action="importBookingDetail" id="importBookingDetail" name="importBookingDetail" method="post">'
            +     '<input type="file" name="file" id="file"/>'
            + '</form></div>',
            buttons : {
                button1 : {text: 'Submit', onclick: submitFile},
                button4 : {text: 'Cancel'}
            },
        });
        fallrCustom();
        return false;
    }

    function toggleMemberDetail() {
        //var memberDetailSelector = $('#bookingDetail\\.memberDetail');
        //memberDetailSelector.prop('disabled', !memberDetailSelector.prop('disabled'));
		var isAlreadyRegistered = $("#isAlreadyRegistered");
        $("#member_data").toggle();
        $("#memberDetail_selector").toggle();
		isAlreadyRegistered.val(isAlreadyRegistered.val() === "false");
        $("#but_toggleForm").val($("#but_toggleForm").val() === "Fill Up Member Details" ? "Select Member from List" : "Fill Up Member Details");
    }
</script>

<style>
.clr {
	color: red;
}
</style>
<div class="fivecol">
	<h3>Manage Booking Detail</h3>
	<hr>
	<form:form modelAttribute="bookingDetailModel"
		action="addBookingDetail" method="post">
		<fieldset>
			<ol>
				<li>
					<label for="expenseName" class="mediumRed">Project:</label>
					<form:select path="bookingDetail.project">
						<form:options items="${projects}" itemLabel="name" itemValue="id" />
					</form:select>
					<span class="clr" id="sdate1"></span>
				</li>
					
				<li>
					<label for="expenseName" class="mediumRed">Block:</label>
					<select name="block" id="block"></select>
					<span class="clr" id="sdate2"></span>
				</li>

				<li>
					<label for="expenseName" class="mediumRed">Property	Number:</label>
					<form:select path="bookingDetail.propertyDetail"></form:select>
					<span class="clr" id="sdate3"></span>
				</li>
				
				<li><input type="button" class="tour2"
					value="View Blocks"
					onclick="javascript: return onViewBlocks();"></li>

				<li><label for="expenseName" class="mediumRed">Booking
						Date:</label> <form:input type="date" path="bookingDetail.bookingDate" class="tour2" />
				<span class="clr" id="sdate">
				
				</span>
				</li>
                <li>
                    <input type="button" id="but_toggleForm" onclick="return toggleMemberDetail();" value="Select Member from List">
                    <input type="hidden" id="isAlreadyRegistered" name="isAlreadyRegistered" value="false">
                </li>
				<li style="display: none;" id="memberDetail_selector">
					<label for="expenseName" class="mediumRed">Member Detail:</label>
                    <form:select path="bookingDetail.memberDetail">
						<c:forEach items="${members }" var="member">
							<form:option value="${member.id }">${member.firstName } ${member.lastName }</form:option>
						</c:forEach>
					</form:select>
					<span class="clr" id="sdate4"></span>
				</li>

                <div id="member_data">
                    <li>
                        <li><label for="expenseName" class="mediumRed">First Name:</label>
                        <form:input type="text" path="employee.firstName" class="tour2 memberValidation" />
                        <span class="clr" id="name_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Middle Name:</label>
                        <form:input type="text" path="employee.middleName" class="tour2 memberValidation" />
                        <span class="clr" id="name_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Last Name:</label>
                        <form:input type="text" path="employee.lastName" class="tour2 memberValidation" />
                        <span class="clr" id="name_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Permanent Address:</label>
                        <form:input type="text" path="employee.address1" class="tour2 memberValidation" />
                        <span class="clr" id="permenantAddress_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Office Address:</label>
                        <form:input type="text" path="employee.address2" class="tour2" />
                        <span class="clr" id="officeAddress_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Date Of Birth:</label>
                        <form:input type="date" path="employee.details.dateOfBirth" class="tour2 memberValidation" />
                        <span class="clr" id="dateOfBirth_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Age:</label>
                        <form:input type="text" path="employee.details.age" class="tour2 memberValidation" />
                        <span class="clr" id="age_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Mobile Number:</label>
                        <form:input type="text" path="employee.mobileNo" class="tour2 memberValidation" />
                        <span class="clr" id="contactNo1_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Phone Number:</label>
                        <form:input type="text" path="employee.phone" class="tour2 memberValidation" />
                        <span class="clr" id="contactNo2_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Emergency Contact number:</label>
                        <form:input type="text" path="employee.details.contactNo1" class="tour2 memberValidation" />
                        <span class="clr" id="contactNo3_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Email:</label>
                        <form:input type="text" path="employee.email" class="tour2 memberValidation" />
                        <span class="clr" id="email_span"></span></li>

                    <li>
                        <label for="user" class="mediumRed">Username:</label>
                        <form:input path="employee.username" class="tour3 employeeValidation" />
                        <span id="username_span" class="clr"></span>
                    </li>
                    <li>
                        <label for="pass" class="mediumRed">Password:</label>
                        <form:password path="employee.password" class="employeeValidation"/>
                    </li>
                    <li>
                        <label for="passc" class="mediumRed" >Confirm: </label>
                        <input type="password" name="passwordc" id="passwordc"  value="" />
                        <span id="password_span" class="clr"></span>
                    </li>

                    <li><label for="expenseName" class="mediumRed">Profession:</label>
                        <form:input type="text" path="employee.details.profession" class="tour2 memberValidation" />
                        <span class="clr" id="profession_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">PAN Number:</label>
                        <form:input type="text" path="employee.details.PANNumber" class="tour2 memberValidation" />
                        <span class="clr" id="PANNumber_span"></span></li>

                    <li><label for="expenseName" class="mediumRed">Marriage Anniversary Date:</label>
                        <form:input type="text" path="employee.details.marriageAnniversaryDate" class="tour2 memberValidation" />
                    </li>
                </div>

				<li><label for="expenseName" class="mediumRed">Booked
						By Employee:</label> <form:select path="bookingDetail.bookedByEmployee">
						<c:forEach items="${employees }" var="emp">
							<form:option value="${emp.id }">${emp.firstName } ${emp.lastName }</form:option>
						</c:forEach>
					</form:select>
						<span class="clr" id="sdate5"></span>
					
					</li>

				<li><label for="expenseName" class="mediumRed">note:</label> <form:input
						type="text" path="bookingDetail.note" class="tour2" /> <span class="clr"
					id="note_span"></span></li>

				<label for="expenseName" class="mediumRed">Resale:</label>
				<li><form:checkbox path="bookingDetail.resale" class="tour2" /></li>
				<br />

				<label for="expenseName" class="mediumRed">Available For
					Resale:</label>
				<li><form:checkbox path="bookingDetail.availableForResale" class="tour2" /></li>
				<br />

				<li>
					<input type="submit" name="submit" class="tour2" value="Add" onclick="javascript: return submitForm();">
					<span class="clr" id="sdate">
						${sessionScope.errorMsg }
						<c:remove var="errorMsg" scope="session"/>
					</span>
				</li>
                <li>
                    <span id="bef_FileImpBut"></span>
                    <input type="button" value="Import Excel File" onclick="return handleFileUploadRequest();">
                </li>
			</ol>
		</fieldset>
	</form:form>
	</div>
<div id="expenseGrid" class="sevencol last grid">
	<div class="creativetbl_container">
		<table id="projectContainer">
			<thead>
				<tr>
					<th>Index</th>
					<th style="display: none;"></th>
					<th>Project</th>
					<th>Property</th>
					<th>Booking Date</th>
					<th>Member Detail</th>
					<th>Booked By Employee</th>
					<th>Note</th>
					<th></th>

				</tr>
			</thead>
			<tbody>
				<c:set var="odd" value="true" />
				<c:set var="counter" value="1" />
				<c:forEach items="${bookingDetail}" var="bookingDetail">
					<tr class="<c:out value="${odd ? 'odd': 'even'}"/>">
						<td>${counter }</td>
						<td style="display: none;" class="propID">${bookingDetail.id }</td>
						<td class="propName" ><a class="invOption" rel="" onclick="onClickDetail(this);">${bookingDetail.project.name }</a></td>
						<td class="propName">${bookingDetail.propertyDetail.propertyNumber }</td>
						<td class="propName"><fmt:formatDate value="${bookingDetail.bookingDate}" pattern="dd/MM/yyyy" /></td>
						<td class="propName">${bookingDetail.memberDetail.firstName } ${bookingDetail.memberDetail.lastName }</td>
						<td class="propName">${bookingDetail.bookedByEmployee.firstName }${bookingDetail.bookedByEmployee.lastName }</td>
						<td class="propName">${bookingDetail.note }</td>
						<td width="40"><img class="invOption"
							src="../resources/images/invOption.png" rel=""
							onclick="editOptions(this);"></td>
					</tr>
					<c:set var="counter" value="${counter + 1 }" />
					<c:set var="odd" value="${!odd }" />
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
