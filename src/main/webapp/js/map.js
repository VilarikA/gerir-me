 function loadLocation() {
   var infowindow = new google.maps.InfoWindow({
     content: $("#full_address").val()
   });  
   var myLatLng = new google.maps.LatLng($("#lat").val(), $("#lng").val());
   var beachMarker = new google.maps.Marker({
     position: myLatLng,
     map: map,
     title: $("#name").val(),
     icon: imageToMap, // || "/images/custumer_crud.png",
     id: 1
   });
   map.setCenter(myLatLng);
   google.maps.event.addListener(beachMarker, 'click', function() {
     infowindow.open(map, beachMarker);
   });   
 }

 function initializeMap() {
   if ($("#lat").val() && window.google ) {
     var latlng = new google.maps.LatLng(-19.92098205876759, -43.937809143066374);
     var myOptions = {
       zoom: 15,
       center: latlng,
       mapTypeId: google.maps.MapTypeId.ROADMAP
     };
     window.map = new google.maps.Map(document.getElementById("map_canvas"),
       myOptions);
     loadLocation();
   }
 }
 $(function() {
   initializeMap();
 });