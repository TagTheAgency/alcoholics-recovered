<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<h2>Manage phases/process steps</h2>
<h3>Steps</h3>

<c:forEach items="${steps}" var="step">
	<a class="stepLoader" data-id="${step.stepNumber }" href="#" id="showStep${step.stepNumber }" >${step.stepNumber } ${step.title }</a>	
</c:forEach>

<button id="createStep">Create a new step</button>

<form id="stepForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<p>Title: <input type="text" name="title" id="title"/></p>
<p>Body</p>
<textarea id="html" name="html" style="display:block;min-width:1000px;height:500px"></textarea>
<p>Add okay button? <input type="checkbox" id="needsOkay"/></p>

<input id="saveButton" value="Save" type="button" data-id="-1">
</form>
<script>

document.addEventListener("DOMContentLoaded", function() {
	document.querySelectorAll('a').forEach(a => {
		a.addEventListener('click', evt => {
			loadStep(evt.target.dataset.id);
		});
	});
	document.getElementById('saveButton').addEventListener('click', evt => {
		var id = document.getElementById('saveButton').dataset.id;
		if (id === "-1") {
			return;
		}
		/*var data = {
			id: id,
			html: document.getElementById('html').value,
			title: document.getElementById('title').value,
			${_csrf.parameterName}: ${_csrf.token}
		}*/
		
		var formData = new FormData();
		formData.append('html', document.getElementById('html').value);
		formData.append('title', document.getElementById('title').value);
		formData.append('${_csrf.parameterName}', '${_csrf.token}');
		
		
		$("#stepForm").ajaxSubmit({url: 'step/'+id, type: 'post'});
		
/*		fetch('step/'+id, {
			method: "POST",
			headers: {
	            //"Content-Type": "application/json; charset=utf-8",
	             "Content-Type": "application/x-www-form-urlencoded"
	        },
	        body: formData
		})
		.then(response => response.json())
		.then(x => console.log(x)); */
	});
	
});

function loadStep(step) {
	fetch('step/'+step)
		.then(function(response) {
  			return response.json();
		})
		.then(function(myJson) {
			document.getElementById("html").value = myJson.html;
			document.getElementById("title").value = myJson.title;
			document.getElementById("saveButton").dataset.id = myJson.id;
  			console.log(JSON.stringify(myJson));
		});
}
</script>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>


