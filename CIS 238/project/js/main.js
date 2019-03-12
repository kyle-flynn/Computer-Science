window.addEventListener("load", initialize);

function initialize() {
  document.getElementById("mobile-toggle").addEventListener("click", toggleMobileMenu)
}

function toggleMobileMenu() {
  let navElements = document.getElementById("mobile-menu").getElementsByTagName("nav");
  if (navElements.length > 0) {
    let navElement = navElements[0];
    if (navElement.classList.contains("hidden")) {
      navElement.classList.remove("hidden");
      navElement.style.transform = "translateY(0%)";
    } else {
      navElement.classList.add("hidden");
      navElement.style.transform = "translateY(-125%)";
    }
  }
}