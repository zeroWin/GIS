<!DOCTYPE html>
<html>
  <head>
    <title>画点</title>
	<meta charset="utf-8">
    <link rel="stylesheet" href="../css/ol.css" type="text/css">
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
    <script src="../js/ol.js"></script>
  </head>
<body>
    <div id="map" class="map"></div>
    <script>
		  var seIconStyle = new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
          src: '../img/startEndicon.png'
        })
      });
			var startMarker = new ol.Feature({
			  type: 'icon',
			  geometry: new ol.geom.Point(ol.proj.fromLonLat([114.964473724365, 38.352689743042])) 
			});
			
			  startMarker.setStyle(seIconStyle);
			var vectorSource = new ol.source.Vector({
			  features: [startMarker]
			});
			  
			var vectorLayer = new ol.layer.Vector({
			  source: vectorSource
			});	

			var map = new ol.Map({
			  layers: [
				new ol.layer.Tile({
				  source: new ol.source.OSM()
				}),
				vectorLayer
			  ],
			  target: 'map',
			  view: new ol.View({
			  // the view's initial state
			    center: ol.proj.transform([114.964473724365, 38.352689743042], 'EPSG:4326', 'EPSG:3857'),
			    zoom: 8
			  })
			});			  	
    </script>
  </body>
</html>