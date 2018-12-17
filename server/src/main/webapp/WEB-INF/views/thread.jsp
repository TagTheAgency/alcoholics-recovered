<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ROLE_COMMUNITY')">
	<div class="container content">
		<div class="row">
			<div class="col-12 hero-container mb-4" id="community-header">
				<h1 class="page-title-text">Recovered Community</h1>
		</div>
		
		<h2><e:forHtml value="${thread.subject }"/></h2>
		</div>
		<c:forEach items="${thread.forumMessages }" var="message">
		<div class="container forum-row">
			<div class="row ">
				<div class="col-md-3 thread-author">
					<p title="${message.creationDate}">${service.timeSincePost(message) }</p>
					<img src="${service.getGravatarImage(message.author) }" class="gravatar"/>
					<p>By ${service.getAnonymisedEmailAddress(message.author) }</p>
			    </div>
			    <div class="col-md-9 user-content">
			    ${viewHelper.showBody(message)}
			    </div>
			</div>
		</div>
		
		</c:forEach>
		<p id="reply-p">
<button class="btn btn-prm" id="reply-btn">Reply</button>
</p>
	


<div class="container forum-row hidden" id="reply-form" >
	<div class="row ">
		<div class="col-md-3 thread-author">
			Post a reply. Please remember the community rules.
		</div>
		<div class="col-md-9 user-content">
			<form method="post" action="/community/${thread.threadId }" >
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<textarea name="body" style="width: 100%; height: 100px;"></textarea>
			<input type="submit" class="btn btn-prm"/>
			
			</form>		
		</div>
	</div>
</div>


		</div>

 	</div>
 	<script>
 	document.getElementById("reply-btn").addEventListener('click', function() {
		$('#reply-form').show();
		$('#reply-p').hide();
 	});
 	</script>
</sec:authorize>
 	