<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">
<div class="hero-login-container justify-content-md-center">
    <div class="feature-section-container col-md-6" id="login-container">
      <p class="header-text">Choose a new password?</p>
            <form class="form-signin form-inline justify-content-md-center login-form" name="f" action="forgotPassword" method="POST">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

              <div class="form-label-group">
                <input type="password" id="password" class="form-control login-form-input col-8" name="password" placeholder="Enter password" required autofocus>
              </div>
              <div class="form-label-group">
                <input type="password" id="password"1 class="form-control login-form-input col-8" name="password1" placeholder="Confirm password" required>
              </div>

              <div class="col-8 mx-auto">
                <button class="btn" id="register-btn" type="submit">Reset password</button>
              </div>
            </form>
    </div>
</div>
</sec:authorize>
