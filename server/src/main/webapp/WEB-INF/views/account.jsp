<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="content container">
<div class="row">

<div class="card" style="width:100%">
  <div class="card-header">
    Account details
  </div>
  <div class="card-body">

	<h4>Change password</h4>
    <form method="post" action="/account/changePassword">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <c:if test="${not empty error }">
            <div class="alert alert-danger" role="alert">
  				${error }
			</div>
            </c:if>
            <c:if test="${not empty success }">
            <div class="alert alert-success" role="alert">
				${success }
			</div>
          </c:if>
  <div class="form-group">
    <label for="existingPassword">Current password</label>
    <input name="current" type="password" class="form-control" id="existingPassword" placeholder="Current Password">
  </div>
  <div class="form-group">
    <label for="newPassword">Enter a new password</label>
    <input name="newPassword" type="password" class="form-control" id="newPassword" placeholder="New password">
  </div>
  <input type="submit" class="btn btn-prm" value="Submit"/>
</form>
  </div>
</div>


<div class="card" style="width:100%">
  <div class="card-header">
    Community
  </div>
  <div class="card-body">
<sec:authorize access="not(hasRole('ROLE_COMMUNITY'))">
<p>You do not have access to the community.  Please finish The Process first</p>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_COMMUNITY')">
<p>You have access to the community.  Your subscription expires on {expiry-date }</p>
</sec:authorize>
</div>
</div>

</div>
</div>