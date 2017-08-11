<?php
require_once("../functions.php");
$link = dbconn();
// 获取铁路的路径
$res = $link->query("SELECT `Latitude`,`Longitude` FROM `railwaygps` WHERE 1");
while($arr = $res->fetch_array())
{
	$result[] = $arr;
}
$dataLength = $res->num_rows;
$res->free();

?>

<!DOCTYPE html>
<html>
  <head>
    <title>画数据库读出线</title>
	<meta charset="utf-8">
    <link rel="stylesheet" href="../css/ol.css" type="text/css">
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
    <script src="../js/ol.js"></script>
  </head>
  <body>
    <div id="map" class="map"></div>
    <script>  
	  /*----------------- 显示的线 -----------------*/
	  var LineStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({  
		  width: 4,  
          color: [255, 0, 0, 1]  
        })  
	  });
	  
	  var seIconStyle = new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
          src: '../img/startEndicon.png'
        })
      });

	  var carIconStyle = new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 0.5],
          src: '../img/car.png'
        })
      });

	  
	  var start_x;
	  var start_y;
	  var end_x;
	  var end_y;
	  function addPoint(){
		var pointList = [];
		var railWay_temp = '<?php echo urlencode(json_encode($result));?>';
		var railWay = eval(decodeURIComponent(railWay_temp));
		var dataLen = <?php echo $dataLength; ?>;	
		for(var i = 0; i < dataLen; i = i + 1){
		  x = Number(railWay[i][1]);
		  y = Number(railWay[i][0]);
		  pointList.push([ol.proj.fromLonLat([x,y])[0],ol.proj.fromLonLat([x,y])[1]]);
		}
		//console.log(dataLen + " " + railWay[0][0]);
		start_x = Number(railWay[0][1]);
		start_y = Number(railWay[0][0]);
		end_x = Number(railWay[dataLen-1][1]);
		end_y = Number(railWay[dataLen-1][0]);
		return 	pointList;
	  }

	  var saoguan = new ol.Feature({  
        geometry: new ol.geom.LineString(addPoint()) 
	  }); 

      saoguan.setStyle(LineStyle);
	  /*----------------- 画线结束 -----------------*/

	  
	  
	  /*----------------- 画起点和终点 -----------------*/
      var startMarker = new ol.Feature({
        type: 'icon',
        geometry: new ol.geom.Point(ol.proj.fromLonLat([start_x,start_y])) 
      });
	  
      var endMarker = new ol.Feature({
        type: 'icon',
        geometry: new ol.geom.Point(ol.proj.fromLonLat([end_x,end_y]))
      });
	  
	  startMarker.setStyle(seIconStyle);
	  endMarker.setStyle(seIconStyle);  
	  /*----------------- 画起点和终点结束 -----------------*/
	  
	 
	  
	  var vectorSource = new ol.source.Vector({
        features: [saoguan,startMarker,endMarker]
      });
	  
      var vectorLayer = new ol.layer.Vector({
        source: vectorSource
      });
	  
	  
	  //test
	  var i = 0;
	  var flag = 0;
	  var temp;
	  function addRandomFeature() {
        var x = start_x + i ;
        var y = start_y + i ;
		i = i + 0.1;
        var feature = new ol.Feature({
          type: 'icon',
          geometry: new ol.geom.Point(ol.proj.fromLonLat([x,y]))
		});	 
		feature.setStyle(carIconStyle);
        vectorSource.addFeature(feature);
		if(flag == 0){
			flag = 1;
			console.log(i);
		}
		else{
			vectorSource.removeFeature(temp);
			console.log(i);
		}
		temp = feature;
      }
	  
	  //test
	  var map = new ol.Map({
	    layers: [
		  new ol.layer.Tile({
		    source: new ol.source.OSM()
		  }),
		  vectorLayer,
		],
		target: 'map',
		view: new ol.View({
		  // the view's initial state
          center: ol.proj.transform([114.964473724365, 38.352689743042], 'EPSG:4326', 'EPSG:3857'),
          zoom: 9
        })
	  });

	//window.setInterval(addRandomFeature, 5000);	  
    </script>
  </body>
</html>