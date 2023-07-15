

const url = "http://localhost:8080/api/message";

const inputBox = document.getElementById('input-box');
const searchWrapper = document.querySelector('.wrapper');
const resultsWrapper = document.querySelector('.results');


inputBox.addEventListener("keyup", (event) => {

         if (event.isComposing || event.keyCode === 229) {
        return;
  }

    console.log("KEYUP LISTENER");
    console.log(inputBox.value);

    suggestionFetch(inputBox.value);

 });

async function suggestionFetch(inputBoxValue) {
  const response = await fetch(url + "?searchTerm=" + inputBoxValue);
  const usernameList = await response.json();
  console.log(usernameList);


  displaySuggestions(usernameList);
        if (!usernameList.length) {
                 resultsWrapper.innerHTML = '';
         }

  }


function displaySuggestions(suggestions) {
  const content = suggestions.map((list) => {
     return `<li onclick=selectInput(this)>${list}</li>`;
  });
  resultsWrapper.innerHTML = `<ul>${content.join('')}</ul>`;
}

function selectInput(list) {
  inputBox.value = list.innerHTML;
  resultsWrapper.innerHTML = "";
}
