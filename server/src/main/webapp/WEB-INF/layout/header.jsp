<nav>
		<div class="nav-container d-flex justify-content-between align-items-center">
			<div class="nav-logo-wrapper">
				<img src="${pageContext.servletContext.contextPath}/img/logo.png" alt="Logo" class="logo">
			</div>
			<div class="nav-items-container d-none d-md-block">
				<ul class="nav-items">
					<li class="mr-3"><a href="./index.html">Home</a></li>
					<li class="mr-3"><a href="./about.html">About</a></li>
					<li class="mr-3">Contact</li>
					<li><a href="${pageContext.request.contextPath}/theProcess">Process</a></li>
				</ul>
			</div>
			<div class="nav-acc-items-container d-none d-md-block">
					<div id="login-btn" class="btn btn-prm"><a href="${pageContext.request.contextPath}/login">Login</a></div>
					<div id="sign-up-btn" class="btn btn-prm"><a href="${pageContext.request.contextPath}/join/account">Sign Up</a></div>
			</div>
			<div id="hamburger-menu" class="d-md-none">
				<i class="fas fa-bars"></i>
			</div>
		</div>
	 </nav>