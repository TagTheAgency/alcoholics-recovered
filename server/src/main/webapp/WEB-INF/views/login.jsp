<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  <div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center">Sign In</h5>
            <form class="form-signin" name="f" action="perform_login" method="POST">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			  	<c:if test="${not empty param.error}">
		            <div class="alert alert-danger" role="alert">
		  				Invalid username or password. You can <a class="alert-link" href="join/account">create a new account</a> or use the <a class="alert-link" href="forgotPassword">forgot password page</a>.
					</div>
		         </c:if>
              <div class="form-label-group">
                <input type="text" id="inputEmail" class="form-control" name="username" placeholder="Email address" required autofocus>
                <label for="inputEmail">Email address</label>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control" name="password" placeholder="Password" required>
                <label for="inputPassword">Password</label>
              </div>

              <div class="custom-control custom-checkbox mb-3">
                <input type="checkbox" class="custom-control-input" id="customCheck1">
                <label class="custom-control-label" for="customCheck1">Remember password</label>
              </div>
              <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
              </form>
          </div>
        </div>
      </div>
    </div>
  </div>
