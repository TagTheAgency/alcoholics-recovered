<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

	<div class="row bg-highlight">
		<div id="sidenav-overlay" class="sidenav-overlay">
				<span id="sidebar-hamburger-icon-container" onclick="openNav()"><img class="my-3" id="sidebar-hamburger-icon" src="${pageContext.request.contextPath}/img/hamburger-text-menu-blue.png" alt="--MENU--"></span>
				<a href="javascript:void(0)" class="sidenav-closebtn" onclick="closeNav()">&times;</a>
				<div class="inset">
					<h1 class="highlight-text my-3">${currentStep.title }</h1>

					<p><i class="material-icons">schedule</i> estimated 20-25mins</p>
					<div class="progress bg-ar-secondary-light">
						<div class="progress-bar bg-ar-secondary" role="progressbar" style="width: ${currentStep.stepNumber / stepCount * 100}%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<p>Step ${currentStep.stepNumber } of ${stepCount }</p>
					<hr class="bg-ar-primary"/>
				</div>
				<ul class="list-group">
					<c:forEach items="${steps }" var="step">
						<c:choose>
							<c:when test="${helper.active(step)}">
								<li class="active">${step.title }</li>
							</c:when>
							<c:when test="${helper.viewable(step)}">
								<li class="completed"><a href="${pageContext.request.contextPath}/theProcess/${step.phase.phaseNumber }/${step.stepNumber}">${step.title }</a></li>
							</c:when>
							<c:when test="${helper.current(step)}">
								<li class="completed notick"><a href="${pageContext.request.contextPath}/theProcess/${step.phase.phaseNumber }/${step.stepNumber}">${step.title }</a></li>
							</c:when>
							<c:otherwise>
								<li>${step.title }
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
				<script>
					function openNav() {
						document.getElementById("sidenav-overlay").style.width = "250px";
						document.getElementById("sidebar-hamburger-icon").style.display = "none";
					}
					function closeNav() {
						document.getElementById("sidenav-overlay").style.width = "0";
						document.getElementById("sidebar-hamburger-icon").style.display = "block";
					}
</script>
			</div>
	    <div id="sidebar-container" class="sidebar-expanded col-3">
					<div class="sticky-top sticky-offset">
						<div class="inset">
							<h1 class="highlight-text my-3">${currentStep.title }</h1>

							<p><i class="material-icons">schedule</i> estimated 20-25mins</p>
							<div class="progress bg-ar-secondary-light">
	  						<div class="progress-bar bg-ar-secondary" role="progressbar" style="width: ${currentStep.stepNumber / stepCount * 100}%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<p>Step ${currentStep.stepNumber } of ${stepCount }</p>
							<hr class="bg-ar-primary"/>
						</div>
						<ul class="list-group">
							<c:forEach items="${steps }" var="step">
								<c:choose>
									<c:when test="${helper.active(step)}">
										<li class="active">${step.title }</li>
									</c:when>
									<c:when test="${helper.viewable(step)}">
										<li class="completed"><a href="${pageContext.request.contextPath}/theProcess/${step.phase.phaseNumber }/${step.stepNumber}">${step.title }</a></li>
									</c:when>
									<c:when test="${helper.current(step)}">
										<li class="completed notick"><a href="${pageContext.request.contextPath}/theProcess/${step.phase.phaseNumber }/${step.stepNumber}">${step.title }</a></li>
									</c:when>
									<c:otherwise>
										<li>${step.title }
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>

	    </div>
	    <div id="main-content-container" class="col">
				<div id="video-container">
					<video controls
							controlsList="nodownload"
					       src="/video/Beach.webm"
					       width="100%"
					       height="100%">
					    Sorry, your browser doesn't support embedded videos.
					</video>
				</div>
				<hr class="bg-ar-primary"/>
				<div class="process-main-content">
					${currentStep.html }
				</div>
<form method="post" action="${pageContext.request.contextPath}/theProcess/${currentPhase.phaseNumber }/${currentStep.stepNumber }/next" >
				<c:if test="${currentStep.needsOkay}">
					<div class="form-check">
  <input class="form-check-input" type="checkbox" value="" id="agreeCheck">
  <label class="form-check-label process-tick-box" for="agreeCheck">
	${currentStep.tickBoxText }
  </label>
</div>
</c:if>
<a class="btn btn-ar-primary previous"  href="${pageContext.request.contextPath}/theProcess/${currentPhase.phaseNumber }/${currentStep.stepNumber }/previous">Previous slide</a>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<button class="btn btn-ar-secondary next ${currentStep.needsOkay ? "disabled" : "" }" id="nextButton" type="submit" ${currentStep.needsOkay ? "disabled=\"disabled\"" : "" }>
Next slide</button>
</form>

	    </div>
	</div>

	<script>
	document.addEventListener("DOMContentLoaded", function(event) { 

	<c:if test="${currentStep.needsOkay}">
	$('#agreeCheck').click(function() {
		if ($('#agreeCheck').is(':checked')) {
			$('#nextButton').removeClass('disabled');
			$('#nextButton').removeAttr('disabled');
		} else {
			$('#nextButton').addClass('disabled');
			$('#nextButton').attr("disabled", "disabled");

		}
	});
	</c:if>
	});
	</script>
 </div>