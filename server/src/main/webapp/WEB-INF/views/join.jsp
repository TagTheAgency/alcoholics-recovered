<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link href="/css/bootstrap.4.1.3.min.css" rel="stylesheet">
<link href="/css/register.css" rel="stylesheet"/>
<script src="https://js.stripe.com/v3/"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
  <div class="container">
    <div class="row">
      <div class="col-lg-10 col-xl-9 mx-auto">
        <div class="card card-signin flex-row my-5">
          <div class="card-img-left d-none d-md-flex">
             <!-- Background image for card set in CSS! -->
          </div>
          <div class="card-body">
            <h5 class="card-title text-center">Register</h5>
            
            
            <form class="form-signin" name="f" action="${pageContext.servletContext.contextPath}/join/process" method="POST" id="payment-form">
            <c:if test="${duplicateEmail }">
            <div class="alert alert-danger" role="alert">
  				That email address is already registered. You can <a class="alert-link" href="login">login</a> or use the <a class="alert-link" href="forgotPassword">forgot password page</a>.
			</div>
            </c:if>
            <c:if test="${paymentFailed }">
            <div class="alert alert-danger" role="alert">
  				Your payment failed. The error was ${stripeCode } with a status of ${stripeStatusCode }.
			</div>
            </c:if>
            
            
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="stripeToken" value="" id="stripeToken"/>
              <div class="form-label-group">
                <input type="text" id="inputFirstname" class="form-control" placeholder="First name" required name="firstName" autofocus value="${firstName }">
                <label for="inputFirstname">First name</label>
              </div>

              <div class="form-label-group">
                <input type="text" id="inputLastname" class="form-control" placeholder="Last name" required name="firstName" autofocus value="${lastName }">
              
                <label for="inputLastname">Last name</label>
              </div>

              <div class="form-label-group">
                <input type="email" id="inputEmail" class="form-control" placeholder="Email" name="email" required value="${email }">
                <label for="inputEmail">Email</label>
              </div>
              
              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required>
                <label for="inputPassword">Password</label>
              </div>
              
              <hr class="mb4">
              
              <h4 class="mb-3">Payment</h4>
              
              <p>Your credit card will be charged a one-off payment of $495.00</p>
              

			<div class="form-row mb5">
			    <label for="card-element">
			      Credit or debit card
			    </label>
			    <div id="card-element" class="">
			      <!-- A Stripe Element will be inserted here. -->
			    </div>
			
			    <!-- Used to display form errors. -->
			    <div id="card-errors" role="alert"></div>
			</div>


              <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Register</button>
              <a class="d-block text-center mt-2 small" href="login">Sign In</a>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>


<script>
var stripe = Stripe('${apiKey}');
var elements = stripe.elements();


//Custom styling can be passed to options when creating an Element.
//(Note that this demo uses a wider set of styles than the guide below.)
var style = {
	base: {
	 color: '#32325d',
	 lineHeight: '18px',
	 fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
	 fontSmoothing: 'antialiased',
	 fontSize: '16px',
	 '::placeholder': {
	   color: '#aab7c4'
	 }
	},
	invalid: {
	 color: '#fa755a',
	 iconColor: '#fa755a'
	}
};

//Create an instance of the card Element.
var card = elements.create('card', {style: style});

//Add an instance of the card Element into the `card-element` <div>.
card.mount('#card-element');

//Handle real-time validation errors from the card Element.
card.addEventListener('change', function(event) {
var displayError = document.getElementById('card-errors');
if (event.error) {
 displayError.textContent = event.error.message;
} else {
 displayError.textContent = '';
}
});

//Handle form submission.
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
	event.preventDefault();
	
	stripe.createToken(card).then(function(result) {
		if (result.error) {
		  // Inform the user if there was an error.
		  var errorElement = document.getElementById('card-errors');
		  errorElement.textContent = result.error.message;
		} else {
		  // Send the token to your server.
		  stripeTokenHandler(result.token);
		}
	});
});

function stripeTokenHandler(token) {
	document.getElementById('stripeToken').value = token.id;
	console.log(token);
	document.querySelector('form').submit();
}

</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>