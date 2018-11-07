<nav>
<div class="nav-container d-flex justify-content-between align-items-center">
	<div class="nav-logo-wrapper">
		<img src="${pageContext.servletContext.contextPath}/img/logo.png" alt="Logo" class="logo">
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
		<div id="login-btn" class="btn btn-prm"><a href="${pageContext.servletContext.contextPath}/login">Login</a></div>
		<div id="sign-up-btn" class="btn btn-prm"><a href="${pageContext.servletContext.contextPath}/join/account">Sign up</a></div>
	</div>
	<div id="hamburger-menu" class="d-md-none">
		<i class="fas fa-bars"></i>
	</div>
</div>
</nav>