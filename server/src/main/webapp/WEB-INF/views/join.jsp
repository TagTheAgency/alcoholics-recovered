<!DOCTYPE html>
<html>
<head>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

  <div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center">Sign In</h5>
            <form class="form-signin" name="f" action="${pageContext.servletContext.contextPath}/join/process" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <script
    src="https://checkout.stripe.com/checkout.js"
    class="stripe-button"
    data-key="${apiKey}"
    data-image="/square-image.png"
    data-name="Demo Site"
    data-description="2 widgets ($20.00)"
    data-amount="2000">
  </script>
              </form>
          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>