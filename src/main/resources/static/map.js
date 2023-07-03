let map;
let marker;
function initMap() {
//Create new map
map = new google.maps.Map(document.getElementById('map'), {
center: { lat: 38.630416, lng: -90.286394 },
zoom: 11})
//Create initial marker
let originMarker = new google.maps.Marker ({
position: { lat: 38.651280, lng: -90.259150},
map: map,
title: 'LaunchCode'
})
}

function addMarker() {
//Get user inputs
let address = document.getElementById('addressInput').value;
let brand = document.getElementById('brand').value;
let size = document.getElementById('size').value;
let style = document.getElementById('style').value;
let image = document.getElementById('photo').files[0];
let imageSrc = URL.createObjectURL(image);

//Create tools
let geocoder = new google.maps.Geocoder();
let reader = new FileReader();

//Geocoding function
geocoder.geocode({ address: address}, function (results, status) {
    //if valid response from geocoder
    if ( status == 'OK'  && results.length > 0) {

        //Grab LatLong of response
        let location = results[0].geometry.location;

        //Create marker
        marker = new google.maps.Marker({
        position: location,
        map: map,
        title: address,
        animation : google.maps.Animation.DROP
        });

        //Center map on new marker
        map.setCenter(location);
        map.setZoom(13);

        //Create infoWindow for marker
        let infoWindow = new google.maps.InfoWindow ({
                content: "<div style='float:left'><img src='" + imageSrc + "' alt='shoe' width='50' style='margin-top:35px'></div> <div style='float:right; padding: 10px;'>" +
                              "<p>Brand: " + brand + "</p>" +
                              "<p>Size: " + size + "</p>" +
                              "<p>Style: " + style + "</p></div>" +
                              "<div style='margin-left: 28%'><button>See Listing</button></div>"
            });

        //On marker click
        google.maps.event.addListener(marker, "click", function() {

            //Open infoWindow
            infoWindow.open(map, marker);
        });
        } else {
        console.error('Geocode was not successful for the following reason: ' + status);
        }
    });

}