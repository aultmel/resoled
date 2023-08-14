const imageDiv = document.getElementById('imageDiv');
const prevButton = document.getElementById('prevButton');
const nextButton = document.getElementById('nextButton');

prevButton.addEventListener("click", (event) => {
        src = "../images/great-shoe.png"
       imageDiv.style.backgroundImage = `url("${src}")`;
event.preventDefault();

 });

nextButton.addEventListener("click", (event) => {
        src = "../images/active-running-shoe.png"
        imageDiv.style.backgroundImage = `url("${src}")`;
       event.preventDefault();

 });


