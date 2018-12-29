<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="resources" value="/resources"></c:url>
<html>
	<head>
		<link href="${resources}/css/jquery.seat-charts.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${resources}/css/style1.css">
		<script type="text/javascript" src="${resources}/js/jquery.seat-charts.js"></script>
		
		<script type="text/javascript">
			var firstSeatLabel = 0;
			var bStatusStr = "${bookingStatus}";
			
 			var bStatus, sc;
			$(document).ready(function() {
				if(bStatusStr.length > 0) {
					bStatus = bStatusStr.split(",");
					sc = $('#seat-map').seatCharts({
						map: [
						      ${pattern}
						],
						naming : {
							top : false
						},
						legend : {
							node : $('#legend'),
						    items : [
								[ 'f', 'unavailable',   'Booked' ],
								[ 'e', 'available',   'Available']
						    ]					
						},
						click: function () {
							if (this.status() == 'available') {
								// Allow at most one block selected 
								sc.find('selected').status('available');
								return 'selected';
							} else if (this.status() == 'selected') {
								return 'available';
							} else if (this.status() == 'unavailable') {
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});

                    // Disable already booked properties
                    for(var i = 0; i < bStatus.length; i++) {
                        if(bStatus[i] == "true") {
                            sc.get(sc.seatIds[i]).status('unavailable');
                        }
                    }
					/*
					for(var i=1; i < bStatus.length + 1; i++) {
						if(bStatus[(i-1)] == "true") {
							if(i % 12 == 0)
								continue;
							sc.get(Math.ceil(i / 11) + "_" + (i % 11)).status('unavailable');
						}
					}
					var alreadySelected = $.inArray(selectedPropHolder, idarr) + 1;
					sc.get(Math.ceil(alreadySelected / 11) + "_" + (alreadySelected % 12)).status('selected');
					*/
				}
			});
			
			function setProperty() {
				var str = sc.find('selected').seatIds[0];
				if(str) {
					propertyHolder = str;
					$.fancybox.close();
				}
			}
		</script>
	</head>
	<body>
		<div class="wrapper">
			<div class="container" style="width: 1000px;color: blue;">
				<div id="seat-map"  style="width: 50%;float: left;">
				<h1 align="center">Book Property Blocks</h1>
				<h5 align="center">${title}</h5>
				<br>
				<br>
				</div>
				<div class="booking-details" >
					<div id="legend" ></div><br/><br/>
<%--					Do not make graph dynamic for now
						<label for="state" class="mediumRed" style="color:black">Select Block:</label>
						<select name="state">
							<c:forEach items="${blocks }" var="blk">
								<option value="${blk.blockId }">${blk.block }</option>
							</c:forEach>
						</select>
--%>
				</div>
				<input type="button" value="Select" style="width: 20%; margin-top: 300px" onclick="return setProperty();">
			</div>
		</div>
	</body>
</html>