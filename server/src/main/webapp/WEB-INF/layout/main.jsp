<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-68230113-30"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-68230113-30');
	</script>
	  
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link href="https://fonts.googleapis.com/css?family=Nunito:300,400,600,700,800" rel="stylesheet">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/bootstrap.4.1.3.min.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css">
	<!-- link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/register.css" -->
	<script src="https://js.stripe.com/v3/"></script>
	<link rel="icon" href="https://recoveredgroup.com/wp-content/uploads/2018/12/RecoveredLogo_V03_dark_icon.png" sizes="32x32" />
	<link rel="icon" href="https://recoveredgroup.com/wp-content/uploads/2018/12/RecoveredLogo_V03_dark_icon.png" sizes="192x192" />
	<link rel="apple-touch-icon-precomposed" href="https://recoveredgroup.com/wp-content/uploads/2018/12/RecoveredLogo_V03_dark_icon.png" />
	<title>Alcoholics Recovered</title>
</head>
<body>

	<div id="master-container">  	
	<sec:authorize access="!isFullyAuthenticated()">
	<tiles:insertAttribute name="header" />
	</sec:authorize>
	<sec:authorize access="isFullyAuthenticated()">
	<tiles:insertAttribute name="headerAuthenticated" />
	</sec:authorize>
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
   	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>