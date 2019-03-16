window.addEventListener("load", initialize);

function initialize() {
  document.getElementById("mobile-menu-toggle").addEventListener("click", toggleMobileMenu)
}

function toggleMobileMenu() {
  let navElements = document.getElementById("mobile-menu").getElementsByTagName("nav");
  if (navElements.length > 0) {
    let navElement = navElements[0];
    let container = document.getElementById("container");
    if (navElement.classList.contains("hidden")) {
      navElement.classList.remove("hidden");
      navElement.style.transform = "translateX(0%)";
      container.style.opacity = "0.4";
    } else {
      navElement.classList.add("hidden");
      navElement.style.transform = "translateX(-80vw)";
      container.style.opacity = "1.0";
    }
  }
}