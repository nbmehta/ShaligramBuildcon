<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
        <title>Invalid URL !!!</title>
        <c:url var="resources" value="/resources"></c:url>
        <c:url var="home" value="/home"></c:url>
        <!--ADDING THE REQUIRED STYLE SHEETS-->

        <link type="text/css" href="${resources}/error/css/bootstrap.css" rel="stylesheet">  <!--BOOTSTRAP 3 CSS FILE-->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'> <!-- Google web font link-->
        <link type="text/css" href="${resources}/error/css/font-awesome.css" rel="stylesheet"> <!--Font Awesome CSS FILE-->
        <link type="text/css" href="${resources}/error/css/custom.css" rel="stylesheet">  <!--CUSTOM CSS FILE-->
        <link type="text/css" href="${resources}/error/css/animate.css" rel="stylesheet">  <!--animate.css FILE-->



        <!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->



    </head>
    <body>

        <section>
            <div class="container">
                <div class="row row1">
                    <div class="col-md-12">
                        <h3 class="center capital f1 wow fadeInLeft" data-wow-duration="2s">Oops! You hit the wrong place</h3>
                        <h1 id="error" class="center wow fadeInRight" data-wow-duration="2s">0</h1>
                        <p class="center wow bounceIn" data-wow-delay="2s">Requested resource was not found on given URL!</p>
                        <p class="center wow bounceIn" data-wow-delay="2s">Go back to <a href="${home}">Home</a>!</p>
                    </div>
                </div>
        </section>

        <!--ADDING THE REQUIRED SCRIPT FILES-->
        <script type="text/javascript" src="${resources}/error/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="${resources}/error/js/countUp.js"></script>
        <script type="text/javascript" src="${resources}/error/js/wow.js"></script>

        <!--Initiating the CountUp Script-->
        <script type="text/javascript">
            "use strict";
            var count = new countUp("error", 0, 404, 0, 3);

            window.onload = function() {
                // fire animation
                count.start();
            }
        </script>

        <!--Initiating the Wow Script-->
        <script>  
            "use strict";
            var wow = new WOW(
            {
                animateClass: 'animated',
                offset:       100
            }
        );
            wow.init();
        </script>


    </body>
</html>
