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
				<li><a href="${pageContext.request.contextPath}/theProcess">Process</a></li>
			</ul>
		</div>
		<div class="nav-acc-items-container d-none d-md-block">
			<form method="post" action="${pageContext.servletContext.contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button type="submit" class="btn btn-prm">Logout</button>
			</form>
		</div>
		<div id="hamburger-menu" class="d-md-none">
			<i class="fas fa-bars"></i>
		</div>
	</div>
</nav>