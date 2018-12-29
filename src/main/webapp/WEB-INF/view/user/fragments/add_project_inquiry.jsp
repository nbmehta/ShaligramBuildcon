<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	
	var options = {
		success : function(data) {
			$.fallr('hide');
			loadPage(MANAGE_INQUIRY);
		},
		error : function(e) {
			alert("Failed to send request to server, Please try again later...!");
			$.fancybox.close();
			
		}
	}
	
	function updatePropertyTypeName() {
		toggleLoadingIcon(true);
		$('#fallrContent').ajaxForm(options).submit();
	}

	function onAdd()
	{ 	
		var check=$('#interested').prop('checked') ? "checked='true'" : "";
		var check1=$('#showSampleHouse').prop('checked')? "checked='true'" : "";
		var table = $("#projectContainer").DataTable();
		table.row.add([
			++cntr,
			$('#project').val(), 
			$('#project option:selected').text(), 
			$('#projpropertyType').val(), 
			$('#projpropertyType option:selected').text(), 
			"<input type='checkbox' id='check_"+cntr+"' disabled='true'" + check + "></input>",
			"<input type='checkbox' id='check1_"+cntr+"' disabled='true'" + check1 + "></input>", 
			"<input type='button' value='Delete' class='btnDelete'/>"
		]);
		table.draw();
		$(".btnDelete").bind("click", function() {
			var par = $(this).parent().parent(); //tr
			par.remove();
		
		});
		
		$.fancybox.close();
	};
	$("#project").on('change', function(e) {
		toggleLoadingIcon(true);
		$.ajax({
			  url: 'getPropTypesForPrj/' + $("#project").val(),
			  type: 'GET',
			  success: function(data) {
				  $('#projpropertyType').find('option').remove().end();
				  for(division in data)
					  $('#projpropertyType').append($('<option>', {
						  value: data[$('#projpropertyType > option').length].propertyTypeID,
						  text: data[$('#projpropertyType > option').length].name
					  }));
				  toggleLoadingIcon(false);
			  },
			  error: function(e) {
				if(counter == 5) {
					console.log(e.message);
					alert("Unable to connect to server...");
					counter = 0;
				} else {
					counter++;
				}
				toggleLoadingIcon(false);
			  }
		});
	});

	
</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Add Project Inquiry</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper">
		<center>
			<form modelAttribute="projectInquiry" method="post"
				action="addProjectInquiry">
				<div>
					<fieldset>
						<ul style="list-style-type: none">
							<br />

							<li><label for="expenseName" class="mediumRed">Project:</label>
								<select name="project" id="project"
								class="contact-person-multiple tour2" style="width: 100%">
									<c:forEach items="${projects}" var="project">
										<option value="${project.id}">${project.name }</option>
									</c:forEach>
									<select></li>
							<br />

							<li><label for="expenseName" class="mediumRed">Property
									Type:</label> <select name="projpropertyType" id="projpropertyType"
								class="contact-person-multiple tour2" style="width: 100%">
									<c:forEach items="${propTypes}" var="proptype">
										<option value="${proptype.propertyTypeID}">${proptype.name }</option>
									</c:forEach>
							</select></li>

							<br />
							<li><label for="expenseName" class="mediumRed">Interested:</label>
								<input type="checkbox" name="interested" id="interested"
								class="tour2" /></li>
							<br>
							<br />

							<li><label for="expenseName" class="mediumRed">Show
									Sample House:</label> <input type="checkbox" name="showSampleHouse"
								id="showSampleHouse" class="tour2" /></li>
							<br>
							<br />
							<li><input type="button" name="add" class="tour2"
								value="Add" onclick="javascript: return onAdd();"></li>
						</ul>
					</fieldset>
				</div>
			</form>
		</center>
		<div class="clear"></div>
	</div>
</div>
