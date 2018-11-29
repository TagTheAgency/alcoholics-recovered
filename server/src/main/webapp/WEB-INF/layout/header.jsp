<nav>
	<div class="nav-container d-flex justify-content-between align-items-center">
		<div class="nav-logo-wrapper">
			<img src="${pageContext.servletContext.contextPath}/img/logo.png" alt="Logo" class="logo">
		</div>
		<div class="nav-items-container d-none d-md-block">
			<ul class="nav-items">
				<li class="mr-3"><a href="${pageContext.request.contextPath}/">Home</a></li>
				<li class="mr-3"><a href="${pageContext.request.contextPath}/public/about">About</a></li>
				<li class="mr-3"><a href="${pageContext.request.contextPath}/public/contact">Contact</a></li>
				<li><a href="${pageContext.request.contextPath}/theProcess">Process</a></li>
			</ul>
		</div>
		<div class="nav-acc-items-container d-none d-md-block">
				<div id="login-btn" class="btn btn-prm"><a href="${pageContext.request.contextPath}/login">Login</a></div>
				<div id="sign-up-btn" class="btn btn-prm"><a href="${pageContext.request.contextPath}/join/account">Sign Up</a></div>
		</div>
		<div id="top-nav-container">
				<span onclick="openTopNav()">
					<div id="hamburger-menu" class="d-md-none">
						<i class="fas fa-bars"></i>
					</div>
				</span>
					<div id="top-nav" class="overlay justify-content-center">
						<a href="javascript:void(0)" class="closebtn" onclick="closeTopNav()">&times;</a>
						<div class="overlay-content">
							<a href="${pageContext.request.contextPath}/">Home</a>
							<a href="${pageContext.request.contextPath}/public/about">About</a>
							<a href="${pageContext.request.contextPath}/theProcess">Process</a>
							<a href="${pageContext.request.contextPath}/public/contact">Contact</a>
							<a href="${pageContext.request.contextPath}/login">Login</a>
							<a href="${pageContext.request.contextPath}/join/account">Sign Up</a>
						</div>
					</div>
		</div>
	</div>
	<script>function openTopNav() {
			document.getElementById("top-nav").style.height = "60%";
			console.log("topNav open!")
		}
		
		
		function closeTopNav() {
			document.getElementById("top-nav").style.height = "0";
			console.log("topNav closed!")
		}
</script>
</nav>