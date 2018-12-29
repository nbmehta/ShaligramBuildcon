<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url var="resources" value="/resources"/>
<style>
#mycss{
color:#a82618;
font-family: 'PerspectiveSansRegular', Arial, Helvetica, sans-serif;
}

table tr
{
    border:1px solid black;
}
</style>
<script>
$(document).ready(function () {
    $('#projectChartContainer').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Booked Property'
        },
        xAxis: [{
            categories: ['Property']
        }],
        series: [{
            name: 'Total Property',
            data: [${propertyChartData}]
     	  
        }, {
           name: 'Booked Property',
            data: [${bookedPropertyChartData}]
        }]
    });
    this.close();
    $(".fancybox").fancybox({
        type: 'image',
        helpers     : {
            title   : { type : 'inside' },
            buttons : {}
        }
    });
});

</script>
<div class="fancyboxTitle">
	<span class="ui-dialog-title" id="ui-dialog-title-quickSearch">Project
		Detail</span>
</div>
<div class="fancybottomContent">
	<div id="contentWrapper" class="ninecol last">
		<form>
			<div class="fivecol extraRightPadding regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Project Name: </b> ${projects.name}</li>
						<li><b id="mycss">Code: </b>${projects.code}</li>
						<li><b id="mycss">Project Type: </b>${projects.projectType.name}</li>
						<li><b id="mycss">Start Date: </b>${projects.startDate}</li>
						<li><b id="mycss">End Date: </b>${projects.endDate}</li>
						<li><b id="mycss">Address 1: </b> ${projects.address1}</li>
						<li><b id="mycss">Address 2: </b>${projects.address2}</li>
						<li><b id="mycss">City: </b>${projects.city}</li>
						<li><b id="mycss">State: </b>${projects.state.stateName}</li>

					</ol>
				</fieldset>
			</div>
			<div class="fivecol noShadow regbox">
				<fieldset>
					<ol>
						<li><b id="mycss">Description: </b>${projects.description}</li>
						<li><b id="mycss">Plan File Path: </b>${projects.planFilePath}</li>
						<li><b id="mycss">Project Status: </b>${projects.projectStatus.name}</li>
						<li><b id="mycss">Contact Person: </b><br/><c:forEach items="${projects.contactPerson }" var="prj">- ${prj.firstName}</br></c:forEach></li>
						<li><b id="mycss">Property Type: </b><br/><c:forEach items="${projects.propertyTypes }" var="prjj">- ${prjj.name}</br></c:forEach></li>
						<li><b id="mycss">Active: </b><br/><input type='checkbox' disabled='true' <c:if test='${projects.active}'>checked</c:if>></input></li>
					</ol>
				</fieldset>
			</div>

		</form>
        <div class="threecol regbox">
            <table style="border: 1px solid;">
                <tr>
                    <td colspan="2" style="padding: 5px"><input type="button" value="Upload More" /></td>
                </tr>
                <c:forEach items="${projects.projectFiles}" var="file">
                    <tr>
                        <c:set var="URL" value='fetchProjectFile/${fn:replace(projects.name," " ,"_" )}/${file.id}'/>
                        <td  style="padding: 5px">
                            <c:choose>
                                <c:when test="${fn:contains(file.mimeType, 'image')}">
                                    <img src="${URL}">
                                    <div align="center">${fn:substring(file.filename, 0, fn:indexOf(file.filename, "."))}</div>
                                </c:when>
                                <c:otherwise>
                                    <a href="${URL}">${file.filename}</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="vertical-align: middle; padding: 5px"><input type="button" value="DELETE"></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div id="projectChartContainer">
        </div>
		<div class="clear"></div>
	</div>
</div>