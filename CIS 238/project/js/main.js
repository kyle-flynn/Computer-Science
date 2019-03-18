window.addEventListener("load", initialize);

function initialize() {
  document.getElementById("mobile-menu-toggle").addEventListener("click", toggleMobileMenu);
  document.getElementById("overlay").addEventListener("click", closeMobileMenu);
  document.getElementById("mobile-overlay").addEventListener("click", closeMobileMenu);
}

function toggleMobileMenu() {
  let navElements = document.getElementById("mobile-menu").getElementsByTagName("nav");
  if (navElements.length > 0) {
    let navElement = navElements[0];
    if (navElement.classList.contains("hidden")) {
      openMobileMenu();
    } else {
      closeMobileMenu();
    }
  }
}

function openMobileMenu() {
  let navElements = document.getElementById("mobile-menu").getElementsByTagName("nav");
  let navElement = navElements[0];
  let overlay = document.getElementById("overlay");
  let mobileOverlay = document.getElementById("mobile-overlay");
  navElement.classList.remove("hidden");
  navElement.style.transform = "translateX(0%)";
  overlay.style.width = "100vw";
  overlay.style.opacity = "1.0";
  mobileOverlay.style.width = "100vw";
  mobileOverlay.style.opacity = "1.0";
}

function closeMobileMenu() {
  let navElements = document.getElementById("mobile-menu").getElementsByTagName("nav");
  let navElement = navElements[0];
  let overlay = document.getElementById("overlay");
  let mobileOverlay = document.getElementById("mobile-overlay");
  navElement.classList.add("hidden");
  navElement.style.transform = "translateX(-80vw)";
  overlay.style.width = "0";
  overlay.style.opacity = "0";
  mobileOverlay.style.width = "0";
  mobileOverlay.style.opacity = "0";
}