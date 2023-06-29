let map;
function initMap() {
map = new google.maps.Map(document.getElementById('map'), {
center: { lat: 38.630416, lng: -90.286394 },
zoom: 11})

let marker = new google.maps.Marker ({
position: { lat: 38.651280, lng: -90.259150},
map: map,
title: 'LaunchCode'
})
}

function addMarker() {
//Get address entered by user

let address = document.getElementById('addressInput').value;
console.log(address);


let geocoder = new google.maps.Geocoder();

geocoder.geocode({ address: address}, function (results, status) {
    if ( status == 'OK'  && results.length > 0) {
        let location = results[0].geometry.location;

        let marker = new google.maps.Marker({
        position: location,
        map: map,
        title: address
        });

        map.setCenter(location);
        map.setZoom(13);
        } else {
        console.error('Geocode was not successful for the following reason: ' + status);
        }
    });
}