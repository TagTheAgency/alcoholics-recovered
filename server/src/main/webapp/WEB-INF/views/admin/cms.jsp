<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<h2>Manage phases/process steps</h2>
<h3>Steps</h3>

<c:forEach items="${steps}" var="step">
	<a class="stepLoader" data-id="${step.stepNumber }" href="#" id="showStep${step.stepNumber }" >${step.stepNumber } ${step.title }</a>	
</c:forEach>

<button id="createStep">Create a new step</button>


<input type="text" name="title" id="title"/>
<textarea id="html"></textarea>
<input type="checkbox" id="needsOkay"/>

<script>

document.addEventListener("DOMContentLoaded", function() {
	document.querySelectorAll('a').forEach(a => {
		a.addEventListener('click', evt => {
			loadStep(evt.target.dataset.id);
		});
	})
});

function loadStep(step) {
	fetch('step/'+step)
		.then(function(response) {
  			return response.json();
		})
		.then(function(myJson) {
  			console.log(JSON.stringify(myJson));
		});
}
</script>