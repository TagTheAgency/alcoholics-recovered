// var sidebar = document.getElementById("sidebar-overlay")

// console.log(sidebar)

function openNav() {
  document.getElementById("sidebar-overlay").style.width = "200px";
  document.getElementById("sidebar-hamburger-icon-container").style.display = "none";
}

function closeNav() {
  document.getElementById("sidebar-overlay").style.width = "0";
  document.getElementById("sidebar-hamburger-icon-container").style.display = "block";
}