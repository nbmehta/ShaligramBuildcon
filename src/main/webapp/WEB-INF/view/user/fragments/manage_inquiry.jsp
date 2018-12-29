<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	var cntr = 0;
    var REGEX_EXCEL_FILES_ONLY = /^([a-zA-Z0-9\s_\\.\-:])+(.xls|.xlsx)$/;

    var excelImpoptions = {
        success : function(data) {
            $.fallr('hide');
            loadPage(18, function() {
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
                $("#importInquiry").ajaxForm(excelImpoptions).submit();
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
            + '<form action="importInquiryData" id="importInquiry" name="importInquiry" method="post">'
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

	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_INQUIRY);
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
		$
				.ajax({
					type : "POST",
					url : "deleteInquiry",
					data : "id=" + id,
					success : function() {
						$.fallr('hide');
						loadPage(MANAGE_INQUIRY);
					},
					error : function() {
						alert("Failed to connect to server, Please try again later...");
					}
				});
	}
	function onUpdateClick(e) {
		$.fancybox({
			'href' : "fragment/fragment_edit_inquiry?id="
					+ $(e).closest('tr').find('.propID').text(),
			'autoDimensions' : false,
			'scrolling' : 'no',
			'width' : 450,
			'height' : 500,
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
		$
				.fallr(
						'show',
						{
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
		var i = 0;
		var ret = "";
		$('#projectContainer tbody tr').each(
				function() {
					ret += $(this).find(':nth-child(2)').html() + ",";
					ret += $(this).find(':nth-child(4)').html() + ",";
					ret += $("#check_" + (++i)).is(":checked") + " ,"
							+ $("#check1_" + (i)).is(":checked") + ",";
				});
		$('input[name="str"]').val(ret);
		$("#inquiryModel").ajaxForm(options).submit();
		return false;
	}
	
	function onAddProjectInquiry(e) {
		$.fancybox({
			'href' : "fragment/fragment_add_project_inquiry",
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

	$(document).ready(function() {
		$('#projectContainer').DataTable({
			"columnDefs" : [ {
				"targets" : [ 1 ],
				"sClass" : "myClass",
				"searchable" : false
			}, {
				"targets" : [ 3 ],
				"sClass" : "myClass"
			} ]
		});
	});

	$(document).ready(function(){	
		$("#inquiryModel").on("submit",	function() {
											var pattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
											var patternn = /^([0-9])*\.*([0-9])+$/;
											var ret = true;
											var ptr = /[0-9]{10}\b/;
											if (($("#inquiry\\.visitedSite").val()) == null) {
												$("#visitorsite").text("* Visited site is Required.");
												ret = false;
											}
											if (($("#inquiry\\.followUpBy").val()) == null) {
												$("#followUpBy").text("* followUpBy  is Required.");
												ret = false;
											}
											if (($("#inquiry\\.attendee").val()) == null) {
												$("#attendee").text("* Attendee Name is Required.");
												ret = false;
											}

											if (($("#inquiry\\.visitorName").val()) == '') {
												$("#visitorName").text("* Visiter Name is Required.");
												ret = false;
											}
											if (($("#inquiry\\.visitDate").val()) == '') {
												$("#visitDate").text("* Visit Date is Required.");
												ret = false;
											}
											if (($("#inquiry\\.intimeHour").val()) == '') {
												$("#intimeHour").text("* In Time  is Required.");
												ret = false;
											}
											if (($("#inquiry\\.intimeMinute").val()) == '') {	
												$("#intimeMinute").text("* Out Time  is Required.");
												ret = false;
											}
											if (($("#inquiry\\.areaOrCity").val()) == '') {
												$("#areaOrCity").text("* Field  is Required.");
												ret = false;
											}
											if (($("#inquiry\\.email").val()) == '') {
												$("#email").text("* Field  is Required.");
												ret = false;
											}
											if (!pattern.test($("#inquiry\\.email").val())) {
												$("#email").text("* Invalid email format.");
												ret = false;
											}

											if (($("#inquiry\\.contactNumber").val()) == '') {
												$("#contactNumber").text("* Field  is Required.");
												ret = false;
											} else if (!patternn.test($("#inquiry\\.contactNumber").val())) {
												$("#contactNumber")	.text("* Only ditis are allowed ");
												ret = false;
											} else if (!patternn.test($("#inquiry\\.contactNumber").val())|| ($("#inquiry\\.contactNumber")	.val().length != 10)) {
												$("#contactNumber").text("*Contact number should be of 10 digits ");
												ret = false;
											}
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
<div class="fivecol">
	<h3>Manage Inquiry</h3>
	<hr>
	<form:form modelAttribute="inquiryModel" action="addProjectInquiry"
		method="post">
		<fieldset>
			<ol>
				<li><label for="expenseName" class="mediumRed">Visit
						Site</label> <form:select path="inquiry.visitedSite">
						<c:forEach items="${projects}" var="project">
							<form:option value="${project.id }">${project.name }</form:option>
						</c:forEach>
					</form:select>
					<span id="visitorsite" class="clr"></span></li>

				<li><label for="expenseName" class="mediumRed">Visitor
						Name:</label>
				<form:input type="text" path="inquiry.visitorName"
						class="tour2" />
						<span id="visitorName" class="clr"></span></li>
						
						</li>
				<li><label for="expenseName" class="mediumRed">Visit
						Date:</label>
				<form:input type="date" path="inquiry.visitDate"
						class="tour2" />
						<span id="visitDate" class="clr"></span></li>
						
						</li>
				<li><label for="expenseName" class="mediumRed">Intime
						Hour:</label>
				<form:input type="text" path="inquiry.intimeHour"
						class="tour2" />
						<span id="intimeHour" class="clr"></span></li>
						</li>
				<li><label for="expenseName" class="mediumRed">Intime
						Minute:</label>
				<form:input type="text" path="inquiry.intimeMinute"
						class="tour2" />
						<span id="intimeMinute" class="clr"></span></li>
						
						</li>
				<li><label for="expenseName" class="mediumRed">Outtime
						Hour:</label>
				<form:input type="text" path="inquiry.outtimeHour"
						class="tour2" />
						<span id="outtimeHour" class="clr"></span>
						
						</li>
				<li><label for="expenseName" class="mediumRed">Outtime
						Minute:</label>
				<form:input type="text" path="inquiry.outtimeMinute"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Area Or
						City:</label>
				<form:input type="text" path="inquiry.areaOrCity"
						class="tour2" />
						<span id="areaOrCity" class="clr"></span>
						
						</li>

				<li>
					<label for="expenseName" class="mediumRed">FollowUp	By:</label>
					<form:select path="inquiry.followUpBy">
						<c:forEach items="${employees}" var="employee">
							<form:option value="${employee.id }">${employee.firstName }  ${employee.lastName }</form:option>
						</c:forEach>
					</form:select>
					<span id="followUpBy" class="clr"></span>
				</li>

				<li><label for="expenseName" class="mediumRed">Contact Number:</label>
					<form:input type="text" path="inquiry.contactNumber" class="tour2" />
					<span id="contactNumber" class="clr"></span>
				</li>

				<li>
					<label for="expenseName" class="mediumRed">Attendee:</label>
					<form:select path="inquiry.attendee">
						<c:forEach items="${employees}" var="employee">
							<form:option value="${employee.id }">${employee.firstName }  ${employee.lastName }</form:option>
						</c:forEach>
					</form:select>
						<span id="attendee" class="clr"></span>
				</li>
				
				<li><label for="expenseName" class="mediumRed">Email:</label>
				<form:input type="text" path="inquiry.email" class="tour2 " />
				<span id="email" class="clr"></span>
				
				</li>
				<li><label for="expenseName" class="mediumRed">Reference
						By:</label>
				<form:input type="text" path="inquiry.referenceBy"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Inquiry
						Type:</label>
				<form:input type="text" path="inquiry.inquiryType"
						class="tour2" />
						<span id="inquiryType" class="clr"></span></li>
				<li><label for="expenseName" class="mediumRed">No Of
						Visit:</label>
				<form:input type="text" path="inquiry.noOfVisit"
						class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">Remark:</label>
				<form:input type="text" path="inquiry.remark" class="tour2" /></li>
				<li><label for="expenseName" class="mediumRed">FollowUp
						Date:</label></li>
				<form:input type="date" path="inquiry.folowUpDate"
						class="tour2" />
						<span class="clr">
				${sessionScope.errorMsg }
				<c:remove var="errorMsg" scope="session"/>
				</span>

				<li><form:hidden path="str" value="ret" /></li>

                <li>
                    <input type="button" class="tour2" value="Add Project Inquiry" onclick="javascript: return onAddProjectInquiry();">
                </li>
				<li>
                    <input type="submit" name="submit" class="tour2" value="Add" onclick="javascript: return submitForm();">
                </li>
				<li>
                    <span id="bef_FileImpBut"></span>
                    <input type="button" name="import_but" class="tour2" value="Import Excel Files" onclick="javascript: return handleFileUploadRequest();">
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
					<th>ProID</th>
					<th>Project</th>
					<th>PropID</th>
					<th>Property Type</th>
					<th>Interested</th>
					<th>Show Sample House</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>