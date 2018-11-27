<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="content">
<h1>Welcome</h1>
<p>Hi ${user.firstName } <sec:authentication property="name"/></p>
<p>Start/continue your journey <a href="${pageContext.request.contextPath}/theProcess">here</a>
</div>
