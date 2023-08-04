

// This can be recreated for different suggestion types.
// To use this script you will need to create a hidden input field in your template
// <input type="hidden" th:hidden="*{whatEverNameYouPick}" />
// then in your controller model.addAttribute("whatEverNameYouPick", http://localhost:8080/api/ whatEverYouPick"

const suggestionsUrlField = document.getElementById('styleSuggestionsUrl')
const inputSearchBox = document.getElementById('style-search-box');
const resultsWrapper = document.querySelector('.style-results');



// Event Listener for the inputSearchBox keyup events.
//This will fire everytime a user types a character in the inputSearchBox.
inputSearchBox.addEventListener("keyup", (event) => {
         if (event.isComposing || event.keyCode === 229) {
        return;
        }
        else if (inputSearchBox.value.trim() === "") {
                 resultsWrapper.innerHTML = '';
            return;
        }

    suggestionFetch(inputSearchBox.value);
 });


//Function sends the searchTerm as a GET request and receives JSON list back.
//Then sends the list to displaySuggestions.  If the list is empty it will not display anything.
async function suggestionFetch(inputSearchBoxValue) {

  const response = await fetch(suggestionsUrlField.value + "?searchTerm=" + inputSearchBoxValue);
  const suggestionsList = await response.json();

  displaySuggestions(suggestionsList);
        if (!suggestionsList.length) {
                 resultsWrapper.innerHTML = '';
         }
  }

//Maps out the suggestions list with onclick for the selection and pushes it into the select input function
function displaySuggestions(suggestions) {
  const content = suggestions.map((list) => {
     return `<li onclick=selectInput(this)>${list}</li>`;
  });
  resultsWrapper.innerHTML = `<ul>${content.join('')}</ul>`;
}

function selectInput(list) {
  inputSearchBox.value = list.innerHTML;
  resultsWrapper.innerHTML = "";
}

