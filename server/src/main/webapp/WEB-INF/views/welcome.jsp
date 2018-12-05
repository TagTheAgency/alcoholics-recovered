<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="content container">
<h1>Welcome ${newUser ? "" : "back" }</h1>
<p>Hi ${user.firstName }</p>

<p>The Process consists of three phases, each containing a number of steps.  On each step, you can listen to Paul explain the process, and read along.</p>
<p>On some steps you will be asked to download and fill in some worksheets.  On others, you will need to indicate your understanding before you can proceed. Take your time and ensure you complete each stage. When you are ready to move on, select the checkbox and the 'next step' button will activate</p>
<p>You can logout at anytime, and next time you log in you can continue from where you stopped.</p>
<p>When you are ready to start your journey, click below. </p>
<c:choose>
<c:when test="${newUser }">
<a href="${pageContext.request.contextPath}/theProcess"><button type="button" class="btn btn-prm">Start your journey</button></a>
</c:when>
<c:otherwise>
<a href="${pageContext.request.contextPath}/theProcess"><button type="button" class="btn btn-prm">Continue your journey</button></a>
</c:otherwise>
</c:choose>

</div>
