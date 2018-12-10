<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
	<div class="container content">
		<div class="row">

		<sec:authorize access="not(hasRole('ROLE_COMMUNITY'))">
			<div class="col-12">
			<h1>Join the community</h1>
			<p>The online <strong>RECOVERED COMMUNITY</strong> is an anonymous community that allows people who have been 
			through 'The Process' to be able to seek support, knowledge and friendship in a private online environment without 
			having to go off to meetings, or to councillors, psychologists but have a place where they can firstly use 
			'The Process' and then if still having problems they can share their problem with the community anonymously and without judgement</p>
			
			<c:if test="${paymentFailed }">
            <div class="alert alert-danger" role="alert">
  				Your payment failed. The error was ${stripeCode } with a status of ${stripeStatusCode }.
			</div>
          </c:if>
			</div>
			
	<div class="col-md-4 col-sm-6 col-xs-12">
		<div class="pt pt-simple">
			<div class="pt-name">Pay monthly</div>
			<h4 class="pt-price">
				<i class="color-text">$</i>
				<b>20</b>
				<span class="color-text">/ month</span>
			</h4>
			<ul>
				<li>No lock in</li>
				<li>Your credit card will be charged each month</li>
			</ul>
			<div id="monthly-signup" class="btn pt-btn btn-prm color-border color-text color-bg-hover">Sign Up</div>
		</div>
	</div>
	<div class="col-md-4 col-sm-6 col-xs-12">
		<div class="pt pt-simple">
			<div class="pt-name color-text">Prepay 1 Year</div>
			<h4 class="pt-price">
				<i class="color-dark">$</i>
				<b class="color-text">18</b>
				<span class="color-dark">/ month</span>
			</h4>
			<ul>
				<li>Save 10%</li>
				<li>You'll be invoiced annually</li>
			</ul>
			<div id="annual-signup" class="btn pt-btn btn-prm color-bg color-border color-bg-hover">Sign Up</div>
		</div>
	</div>
	<div class="col-md-4 col-sm-6 col-xs-12 offset-sm-3 offset-md-0 offset-xs-0">
		<div class="pt pt-simple">
			<div class="pt-name">Prepay 3 Years</div>
			<h4 class="pt-price">
				<i class="color-text">$</i>
				<b>15</b>
				<span class="color-text">/ month</span>
			</h4>
			<ul>
				<li>Save 25%</li>
				<li>All the community benefits for three years</li>
			</ul>
			<div id="triannual-signup" class="btn pt-btn btn-prm  color-border color-text color-bg-hover">Sign Up</div>
		</div>
	</div>
	
	<form action="/community-subscription" method="POST" id="signup-form">
		<input type="hidden" name="subscription" id="subscription"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <script src="https://checkout.stripe.com/checkout.js">
  </script>
  <script>
	document.getElementById("monthly-signup").addEventListener('click', function() {
		doSignup("Subscription for 1 month", 2000, "month");
	});
	document.getElementById("annual-signup").addEventListener('click', function() {
		doSignup("Subscription for 1 year", 21600, "annual");
	});	
	document.getElementById("triannual-signup").addEventListener('click', function() {
		doSignup("Subscription for 3 years", 54000, "3year");
	});
	function doSignup(description, amount, which) {
		$('#subscription').val(which);
        var token = function(res){
            var $id = $('<input type=hidden name=stripeToken />').val(res.id);
            var $email = $('<input type=hidden name=stripeEmail />').val(res.email);
            $('#signup-form').append($id).append($email).submit();
          };
		/* TODO paramaterise!! */
		StripeCheckout.open({
            key:         "pk_test_H6yLP3A5wYlsdIQRFLAOb7yB",
            amount:      amount,
            name:        "Recovered Community",
            image:       "https://recoveredgroup.com/wp-content/uploads/2018/12/RecoveredLogo_V03_dark_icon.png",
            description: description,
            panelLabel:  "Sign me up",
            token:		 token,
            email:		"<sec:authentication property="name"/>"
          });

          return false;
		
		
	}
  </script>
</form>
			
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_COMMUNITY')">
		<div class="col-12">
		<h1>Recovered Community</h1>
		<p>Please remember the rules!</p>
		</div>
		<table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">Subject</th>
      <th scope="col">Replies</th>
      <th scope="col">Last reply</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${threads.content }" var="thread">
    <tr>
      <th scope="row"><a href="/community/${thread.threadId }"><e:forHtml value="${thread.subject }"/></a></th>
      <td>${thread.messageCount - 1}</td>
      <td>${users.timeSincePost(viewHelper.getMostRecentMessage(thread).creationDate) } by <strong>${viewHelper.getMostRecentMessage(thread).author.email }</strong></td>
    </tr>
	</c:forEach>
  </tbody>
</table>
<p>
<button class="btn btn-prm">Start a new thread</button>
</p>
	
<form method="post" action="/community">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<input type="text" name="title"/>
<textarea name="body">

</textarea>
<input type="submit"/>
</form>		

</sec:authorize>

		</div>

 	</div>