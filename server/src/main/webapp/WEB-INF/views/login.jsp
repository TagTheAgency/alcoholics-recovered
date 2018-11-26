<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div class="hero-login-container justify-content-md-center">
    <div class="feature-section-container col-md-6" id="login-container">
      <p class="header-text">Welcome back</p>
            <form class="form-signin form-inline justify-content-md-center login-form" name="f" action="perform_login" method="POST">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			  	<c:if test="${not empty param.error}">
		            <div class="alert alert-danger" role="alert">
		  				Invalid username or password. You can <a class="alert-link" href="${pageContext.request.contextPath}/join/account">create a new account</a> or use the <a class="alert-link" href="forgotPassword">forgot password page</a>.
					</div>
		         </c:if>
              <div class="form-label-group">
                <input type="text" id="inputEmail" class="form-control login-form-input col-8" name="username" placeholder="Email address" required autofocus>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control login-form-input col-8" name="password" placeholder="Password" required>
              </div>

              <div class="custom-control custom-checkbox mb-3 col-8 mx-auto">
                <input type="checkbox" class="custom-control-input" id="customCheck1">
                <label class="custom-control-label justify-content-start" for="customCheck1">Remember password</label>
              </div>
              <div class="col-8 mx-auto">
                <button class="btn" id="register-btn" type="submit">Sign in</button>
                <p class="mt-3">Don't have an account? <a href="${pageContext.request.contextPath}/join/account">Sign up here.</a></p>  
              </div>
            </form>
    </div>
</div>
