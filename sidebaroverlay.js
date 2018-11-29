
function openNav() {
  document.getElementById("sidebar-overlay").style.width = "200px";
  document.getElementById("sidebar-hamburger-icon-container").style.display = "none";
}

function closeNav() {
  document.getElementById("sidebar-overlay").style.width = "0";
  document.getElementById("sidebar-hamburger-icon-container").style.display = "block";
}

function openTopNav() {
  document.getElementById("top-nav").style.height = "65%";
  console.log("topNav open!")
}


function closeTopNav() {
  document.getElementById("top-nav").style.height = "0";
  console.log("topNav closed!")
}