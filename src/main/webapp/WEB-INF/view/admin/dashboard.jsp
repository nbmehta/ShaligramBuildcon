<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<%@include file="includes/include_css_js.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	dropDownMenu('.menu');
});
$(function () { 
	    $('#bookingInquiryContainer').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Inquiry and Booking'
	        },
	        xAxis: {
	            categories: ${projectName}
	        },
	        yAxis:{
	        	 tickInterval: 1
	        },
	        series: [{
	            name: 'Inquiry',
	            data: ${inquiryCount}
	     	  
	        }, {
	           name: 'Booked',
	            data: ${bookingCount}
	        }]
	    });
	});
	
$(function () { 
    $('#inquiryContainer1').highcharts({
    	plotOptions: {
    		           series: {
    		               fillOpacity: 0.1
    		          }
    		       },
        chart: {
            type: 'area'
        },
        title: {
            text: 'Inquiry Per Month for Year: ${currentYear}'
        },
        xAxis: {
            categories: ['January','February','March','April','May','June','July','Auguest','September','October','November','December']
        },
        yAxis:{
        	 tickInterval: 1
        },
        series: [{
            name: 'Inquiry',
          
       	 	data: ${inquiryPerMonthValue},
             color: 'rgb(144, 237, 125)'
            }, {
           name: 'Booked',
            data:${bookingPerMonthValue},
            color: 'rgb(128, 181, 236)'
        }]
    });
});
$(function () {
    $('#inquiryProjectChartContainer').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: 'Inquiries per Project'
        },
        plotOptions: {
            pie: {
                innerSize: 100,
                depth: 45
            }
        },
        series: [{
            name: 'Number of Inquiries',
            data: [
				${inquiryCountData}
            ]
        }]
    });
});
$(function () {

    $(document).ready(function () {

        // Build the chart
        $('#inquiryProjectChartContainer1').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Inquiries per Project'
            },
           
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: 'Inquiry',
                colorByPoint: true,
                data: [
       				${inquiryCountData}
                   ]
               }]
        });
    });
});



</script>
</head>
<body>
	<div id="fullWrapper" style="width: 100%; left: 0px;">
		<div id="mainMenu">
			<div id="toppanel">
				<%@include file="includes/include_header.jsp"%>
			</div>
		</div>
		<div id="dashboard">
			<div id="wgt-html5" class="jdash-widget">
				<div class="jdash-header">Booking & Inquiries</div>
					<div class="jdash-body" id="bookingInquiryContainer" style="width:100%; height:400px;">
					<div class="clear"></div>
				</div>
			</div>
			
			<div id="wgt-html5" class="jdash-widget">
				<div class="jdash-header">Project Inquiry</div>
				<div id="inquiryProjectChartContainer1" class="jdash-body" style="width:100%; height:400px;">
					<div class="clear"></div>
				</div>
			</div>
			<div id="wgt-html5" class="jdash-widget">
				<div class="jdash-header">Project Inquiry</div>
				<div id="inquiryContainer1" class="jdash-body" style="width:100%; height:400px;">
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
	$('#dashboard').jDashboard();
</script>
</body>
</html>