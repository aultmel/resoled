var slide = document.querySelectorAll('.slide');
var currentIndex = 0;

function showSlide(index) {
  slide.forEach(item => {
    item.style.display = 'none';
  });

  slide[index].style.display = 'block';
}

function nextSlide() {
  currentIndex = (currentIndex + 1) % slide.length;
  showSlide(currentIndex);
}

function previousSlide() {
  currentIndex = (currentIndex - 1 + slide.length) % slide.length;
  showSlide(currentIndex);
}

document.getElementById('nextBtn').addEventListener('click', nextSlide);
document.getElementById('prevBtn').addEventListener('click', previousSlide);

// Show the first slide initially
showSlide(currentIndex);
