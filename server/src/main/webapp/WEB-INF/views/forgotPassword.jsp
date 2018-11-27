<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div class="hero-login-container justify-content-md-center">
    <div class="feature-section-container col-md-6" id="login-container">
      <p class="header-text">Forgotten password?</p>
      <p>Enter your email address and we'll send you a reset link</p>
            <form class="form-signin form-inline justify-content-md-center login-form" name="f" action="forgotPassword" method="POST">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			  	<c:if test="${not empty param.sent}">
		            <div class="alert alert-primary" role="alert">
		  				An email has been sent to that address. Please follow the instructions to reset your password.
					</div>
		         </c:if>
              <div class="form-label-group">
                <input type="text" id="inputEmail" class="form-control login-form-input col-8" name="username" placeholder="Email address" required autofocus>
              </div>

              <div class="col-8 mx-auto">
                <button class="btn" id="register-btn" type="submit">Send reset link</button>
                <p class="mt-3">Don't have an account? <a href="${pageContext.request.contextPath}/join/account">Sign up here.</a></p>  
              </div>
            </form>
    </div>
</div>
