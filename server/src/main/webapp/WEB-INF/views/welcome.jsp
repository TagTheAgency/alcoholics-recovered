<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="principal" property="principal" />
<!DOCTYPE html>
<html>
<body>
<h1>Welcome</h1>
<p>Hi ${principal.username}</p>
</body>
</html>
