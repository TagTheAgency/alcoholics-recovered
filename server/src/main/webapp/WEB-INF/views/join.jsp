<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="hero-login-container justify-content-md-center">
 <div class="feature-section-container col-md-6" id="login-container">
   <p class="header-text">Take your first step. Sign up now.</p>
     <form class="form-inline justify-content-md-center login-form" action="${pageContext.servletContext.contextPath}/join/process" method="post" id="payment-form">
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
       <input type="text" class="form-control login-form-input col-8" placeholder="First name" name="firstName" required autofocus value="${firstName }" autocomplete="false">
       <input type="text" class="form-control login-form-input col-8" placeholder="Last name" name="lastName" required value="${lastName }" autocomplete="false">
       <input type="email" class="form-control login-form-input col-8" placeholder="Email Address" required name="email" value="${email }" autocomplete="false">
       <input type="password" class="form-control login-form-input col-8" placeholder="Password" name="password">
       
       <div id="card-number" class="form-control login-form-input col-8 mb-2"></div>
         <div id="card-errors" role="alert"></div>
       <div class="col-8 mx-auto" style="display:flex; padding:0;">
         <div id="card-expiry" class="form-control login-form-input" style="margin-right: 10px"></div>
         <div id="card-errors" role="alert"></div>
         <div id="card-cvc" class="form-control login-form-input"></div>
         <div id="card-errors" role="alert"></div>
       </div>
         
         
         <div id="card-errors" role="alert"></div>
         <div class="col-8 mx-auto">
       <button id="register-btn" class="btn">Register</button>
       <p class="mt-3">Already have an account? <a href="./login.html">Login here.</a></p>
       </div>
     </form>
     </div>
</div>


<script>
var stripe = Stripe('${apiKey}');

// Create an instance of Elements.
var elements = stripe.elements();

// Custom styling can be passed to options when creating an Element.
// (Note that this demo uses a wider set of styles than the guide below.)
var style = {
  base: {
    color: '#32325d',
    fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
    fontSmoothing: 'antialiased',
    fontSize: '16px',
    width: '40px',
    '::placeholder': {
      color: '#fff'
    }
  },
  invalid: {
    color: '#fa755a',
    iconColor: '#fa755a'
  }
};

// Create an instance of the card Element.
var cardNumber = elements.create('cardNumber', {style: style, placeholder: 'Placeholder'});
cardNumber.mount('#card-number');

var cardExpiry = elements.create('cardExpiry', {style:style});
cardExpiry.mount('#card-expiry')

var cardCvc = elements.create('cardCvc', {style:style})
cardCvc.mount('#card-cvc')

// Handle real-time validation errors from the card Element.
cardNumber.addEventListener('change', function(event) {
  var displayError = document.getElementById('card-errors');
  if (event.error) {
    displayError.textContent = event.error.message;
  } else {
    displayError.textContent = '';
  }
});

// Handle form submission.
var form = document.getElementById('payment-form');
form.addEventListener('submit', function(event) {
  event.preventDefault();

  stripe.createToken(cardNumber).then(function(result) {
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

// Submit the form with the token ID.
function stripeTokenHandler(token) {
  // Insert the token ID into the form so it gets submitted to the server
  var form = document.getElementById('payment-form');
  var hiddenInput = document.createElement('input');
  hiddenInput.setAttribute('type', 'hidden');
  hiddenInput.setAttribute('name', 'stripeToken');
  hiddenInput.setAttribute('value', token.id);
  form.appendChild(hiddenInput);

  // Submit the form
  form.submit();
}

</script>
