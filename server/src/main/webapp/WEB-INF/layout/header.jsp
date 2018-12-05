<nav>
	<div class="nav-container d-flex justify-content-between align-items-center">
		<div class="nav-logo-wrapper">
			<a href="https://recoveredgroup.com/">
			<img src="${pageContext.servletContext.contextPath}/img/RecoveredLogo_V03_dark.png" alt="Logo" class="logo">
			</a>
		</div>
		<div class="nav-items-container d-none d-md-block">
			<ul class="nav-items">
				<li class="mr-3"><a href="https://recoveredgroup.com/">Home</a></li>
				<li class="mr-3"><a href="https://recoveredgroup.com/about">About</a></li>
				<li class="mr-3"><a href="https://recoveredgroup.com/testimonials">Testimonials</a></li>
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
							<a href="https://recoveredgroup.com/">Home</a>
							<a href="https://recoveredgroup.com/about">About</a>
							<a href="https://recoveredgroup.com/testimonials">Testimonials</a>>
							<a href="${pageContext.request.contextPath}/theProcess">Process</a>
							<a href="${pageContext.request.contextPath}/login">Login</a>
							<a href="${pageContext.request.contextPath}/join/account">Sign Up</a>
						</div>
					</div>
		</div>
	</div>
	<script>function openTopNav() {
			document.getElementById("top-nav").style.height = "45%";
		}
		
		
		function closeTopNav() {
			document.getElementById("top-nav").style.height = "0";
		}
</script>
</nav>