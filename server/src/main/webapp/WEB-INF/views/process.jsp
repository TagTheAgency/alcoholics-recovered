<div id="master-container">
 	<nav>
		<div class="nav-container d-flex justify-content-between align-items-center">
			<div class="nav-logo-wrapper">
				<img src="img/logo.png" alt="Logo" class="logo">
			</div>
			<div class="search-bar-wrapper d-none d-md-block">
				<input type="text" name="Search" value="">
			</div>
			<div class="nav-items-container d-none d-md-block">
				<ul class="nav-items">
					<li class="mr-3">Home</li>
					<li class="mr-3">About</li>
					<li class="mr-3">Contact</li>
					<li>Process</li>
				</ul>
			</div>
			<div class="nav-acc-items-container d-none d-md-block">
				<div id="login-btn" class="btn btn-prm">Login</div>
				<div id="sign-up-btn" class="btn btn-prm">Sign Up</div>
			</div>
			<div id="hamburger-menu" class="d-md-none">
				<i class="fas fa-bars"></i>
			</div>
		</div>
 	</nav>


	<div class="row bg-highlight">
	    <div id="sidebar-container" class="sidebar-expanded col-2 d-none d-md-block">
					<div class="sticky-top sticky-offset">
						<div class="inset">
							<h1 class="highlight-text my-3">${currentStep.title }</h1>

							<p><i class="material-icons">schedule</i> estimated 20-25mins</p>
							<div class="progress bg-ar-secondary-light">
	  						<div class="progress-bar bg-ar-secondary" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<p>Step ${currentStep.stepNumber } of ${stepCount }</p>
							<hr class="bg-ar-primary"/>
						</div>
						<ul class="list-group">
							<li class="completed">First slide</li>
							<li class="active">Second slide</li>
							<li>Third slide</li>
							<li>Fourth slide</li>

						</ul>
					</div>

	    </div>
	    <div id="main-content-container" class="col">
				<div id="video-container">
					<video controls
								 controlsList="nodownload"
					       src="/video/Beach.webm"
					       width="600"
					       height="400">
					    Sorry, your browser doesn't support embedded videos.
					</video>
				</div>
				<hr class="bg-ar-primary"/>
				${currentStep.html }
				<p>Alcoholics are only unique by merit of their symptoms. The origin of our problem is the same as that of any other obsessive abuser of substance orbehaviour. Resentment, anger and fear &mdash; plain and simple.
					Once free of resentment, anger and fear the alcoholic's obsession to drink just falls away. We can managr our behaviour and resume a normal life.
				</p>
				<h2>The Process Video</h2>
				<h3>Guidelines for working through "The Process"</h3>
				<p>Welcome and congratulations, you have just made the biggest decision of your life and we pray you never have to go back to the pain and misery that you have endured.
					"The Process" is a program of action, we expect you to commit to "The Process" and follow the clear-cut, concise directions which we will present to you and ask you to follow.</p>


				<h3>Slide 2</h3>
				<p>Thank you for committing to being sober through "The Process".  This is a life changing program that will enable you to be free from alcohol and to live a happy and contented life without the desire to drink.
				</p>
				<p>There are just a few house rules that you need to agree to
				</p>
				<ul><li>You have read and agreed to the conditions</li>
					<li>You are willing to take responsibility for your sobriety and your actions moving forward</li>
					<li>You will complete the worksheets quickly and honestly and that you won't leave any informatiopn out.</li>
					<li><p>If you are atheist, religious, agnostic, spiritual or have other beliefs, please understand the "The Process" does not have an opinion on this, this is your business not ours.
						Some of our members to go God for direction, others trust in the universe and then others rely on their instincts once they have worked through and are practicing "The Process".
						Again, we are not here to tell you what your beliefs should be our only wish is for you to never drink again and lead a happy and contented life with freedom from alcohol.</p>
						<p>PLEASE NOTE - WE ARE NOT HERE TO TELL YOU WHAT TO BELIEVE IN OR NOT BELIEVE IN, that is your business not ours, many of the people we work with say they have a spiritual awakening,
							others notice something different within them, that is their business not ours.</p>
					</li>
					<li>We ask that you follow the instructions and do the work honestly and quickly, and again please do not leave anything out. If you know you are not being honest and leave information out, "The Process" will not work.</li>
					<li>You must be honest with yourself and all the information you provide, please do not leave any stone unturned.</li>
					<li>You accept that alcohol is causing problems in your life and when you drink, you do not have control over alcohol.</li>
				</ul>
				<p>The miracle of "The Process" is that it allows you to be able to analyse and understand yourself at any time and will ensure you will know how to handle situations that would have baffled you once.
					"The Process" is an inside out program.</p>
					<p>Please just tick the completed box that you have read and agreed to the above</p>
					<div class="form-check">
  <input class="form-check-input" type="checkbox" value="" id="agreeCheck">
  <label class="form-check-label" for="agreeCheck">
    I have read and agreed to the above
  </label>
</div>
<button class="btn btn-ar-primary previous">
Previous question</button>
<button class="btn btn-ar-secondary next disabled" id="nextButton">
Next question</button>


				
	    </div>
	</div>


 	<footer>
		<div id="footer-container" class="bg-prm py-5 mt-5">

		</div>
 	</footer>
	<script src="./node_modules/jquery/dist/jquery.min.js" charset="utf-8"></script>
	<script src="./js/script.js" charset="utf-8"></script>
	<script>
	$('#agreeCheck').click(function() {
		if ($('#agreeCheck').is(':checked')) {
			$('#nextButton').removeClass('disabled');
		} else {
			$('#nextButton').addClass('disabled');

		}
	});
	</script>
 </div>