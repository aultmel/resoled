

// This can be recreated for different suggestion types.
// To use this script you will need to create a hidden input field in your template
// <input type="hidden" th:hidden="*{whatEverNameYouPick}" />
// then in your controller model.addAttribute("whatEverNameYouPick", http://localhost:8080/api/ whatEverYouPick"

const brandSuggestionsUrlField = document.getElementById('brandSuggestionsUrl')
const brandSearchBox = document.getElementById('brand-search-box');
const brandResultsWrapper = document.querySelector('.brand-results');


const styleSuggestionsUrlField = document.getElementById('styleSuggestionsUrl')
const styleSearchBox = document.getElementById('style-search-box');
const styleResultsWrapper = document.querySelector('.style-results');




// Event Listener for the brandSearchBox keyup events.
//This will fire everytime a user types a character in the brandSearchBox.
brandSearchBox.addEventListener("keyup", (event) => {
         if (event.isComposing || event.keyCode === 229) {
        return;
        }
        else if (brandSearchBox.value.trim() === "") {
                 brandResultsWrapper.innerHTML = '';
            return;
        }
    suggestionBrandFetch(brandSearchBox.value);
 });


// Event Listener for the brandSearchBox keyup events.
//This will fire everytime a user types a character in the brandSearchBox.
styleSearchBox.addEventListener("keyup", (event) => {
         if (event.isComposing || event.keyCode === 229) {
        return;
        }
        else if (styleSearchBox.value.trim() === "") {
                 styleResultsWrapper.innerHTML = '';
            return;
        }
    suggestionStyleFetch(styleSearchBox.value);
 });


//Function sends the searchTerm as a GET request and receives JSON list back.
//Then sends the list to displaySuggestions.  If the list is empty it will not display anything.
async function suggestionBrandFetch(brandSearchBoxValue) {

  const response = await fetch(brandSuggestionsUrlField.value + "?searchTerm=" + brandSearchBoxValue);
  const suggestionsList = await response.json();

  displayBrandSuggestions(suggestionsList);
        if (!suggestionsList.length) {
                 brandResultsWrapper.innerHTML = '';
         }
  }

//Maps out the suggestions list with onclick for the selection and pushes it into the select input function
function displayBrandSuggestions(suggestions) {
  const content = suggestions.map((list) => {
     return `<li onclick=selectBrandInput(this)>${list}</li>`;
  });
  brandResultsWrapper.innerHTML = `<ul>${content.join('')}</ul>`;
}

function selectBrandInput(list) {
  brandSearchBox.value = list.innerHTML;
  brandResultsWrapper.innerHTML = "";
}



//Function sends the searchTerm as a GET request and receives JSON list back.
//Then sends the list to displaySuggestions.  If the list is empty it will not display anything.
async function suggestionStyleFetch(styleSearchBoxValue) {

  const response = await fetch(styleSuggestionsUrlField.value + "?searchTerm=" + styleSearchBoxValue);
  const suggestionsList = await response.json();

  displayStyleSuggestions(suggestionsList);
        if (!suggestionsList.length) {
                 styleResultsWrapper.innerHTML = '';
         }
  }

//Maps out the suggestions list with onclick for the selection and pushes it into the select input function
function displayStyleSuggestions(suggestions) {
  const content = suggestions.map((list) => {
     return `<li onclick=selectStyleInput(this)>${list}</li>`;
  });
  styleResultsWrapper.innerHTML = `<ul>${content.join('')}</ul>`;
}

function selectStyleInput(list) {
  styleSearchBox.value = list.innerHTML;
  styleResultsWrapper.innerHTML = "";
}

