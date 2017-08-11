<?php
require_once("../functions.php");
?>
<!DOCTYPE html>
<html>
  <head>
    <title>画火车实时位置</title>
	<meta charset="utf-8">
    <link rel="stylesheet" href="../css/ol.css" type="text/css">
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
    <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="../js/jquery-ui-1.10.1.custom.min.js"></script>
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

	  var manIconStyle = new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
          src: '../img/people.ico'
        })
      });

	  
	  var start_x;
	  var start_y;
	  var end_x;
	  var end_y;
	  function addPoint(){
		var pointList = [];
		$.ajax({  
          type : "post",  
          url : "../mapDataFunction.php",  
          data : "action=RailWayRoute",  
          async : false,
		  timeout: 10000,
          success : function(data){  
			var railWay = eval(data);
			var dataLen = railWay.length;
			for(var i = 0; i < dataLen; i = i + 1){
			  x = Number(railWay[i][1]);
			  y = Number(railWay[i][0]);
			  pointList.push([ol.proj.fromLonLat([x,y])[0],ol.proj.fromLonLat([x,y])[1]]);
			}
			start_x = Number(railWay[0][1]);
			start_y = Number(railWay[0][0]);
			end_x = Number(railWay[dataLen-1][1]);
			end_y = Number(railWay[dataLen-1][0]);
			//console.log(start_x + " " + start_y); 
          },
          error: function (msg) {
			alert(msg);
          }  
		}); 
		

		//console.log(dataLen + " " + railWay[0][0]);
		
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
	  var flag = 0;
	  var temp = [];
	  function addRandomFeature() {
		var x;
        var y;
		var carList = [];
		var manList = [];
		// 获取火车位置
		$.ajax({  
          type : "post",  
          url : "../mapDataFunction.php",  
          data : "action=RailWayLocal",  
          async : false,
		  timeout: 10000,
          success : function(data){  
			var carLocal = eval(data);
			x = Number(carLocal[1]);
			y = Number(carLocal[0]);
			carList.push([x,y]);
			//console.log(carLocal);
          },
          error: function (msg) {
			alert(msg);
          }  
		}); 
		
		// 获取巡检员位置
		$.ajax({  
          type : "post",  
          url : "../mapDataFunction.php",  
          data : "action=IPQCLocal",  
          async : false,
		  timeout: 10000,
          success : function(data){  
			var manLocal = eval(data);
			var manLocalLength = manLocal.length;
			for(var i = 0; i < manLocalLength; i = i + 1){
			  x = Number(manLocal[i][2]);
			  y = Number(manLocal[i][1]);
			  manList.push([x,y]);				
			}
			//console.log(carLocal[0]);
          },
          error: function (msg) {
			alert(msg);
          }  
		}); 



		// 删除之前的画的内容
		for(var i = 0; i < temp.length; i = i + 1){
			vectorSource.removeFeature(temp[i]);
		}
		temp = [];
		
		
		// 画新的火车的点
		for(var i = 0; i < carList.length; i = i + 1){
          var feature = new ol.Feature({
            type: 'icon',
            geometry: new ol.geom.Point(ol.proj.fromLonLat([carList[i][0],carList[i][1]]))
		  });	 
		  feature.setStyle(carIconStyle);
          vectorSource.addFeature(feature);
		  temp.push(feature);
		  //console.log(carList.length + " " + carList[i][0] + " " + carList[i][1]);
		}
		
		// 画新的巡检员的点
		for(var i = 0; i < manList.length; i = i + 1){
          var feature = new ol.Feature({
            type: 'icon',
            geometry: new ol.geom.Point(ol.proj.fromLonLat([manList[i][0],manList[i][1]]))
		  });	 
		  feature.setStyle(manIconStyle);
          vectorSource.addFeature(feature);
		  temp.push(feature);
		  console.log(manList.length + " " + manList[i][0] + " " + manList[i][1]);
		}		
      }
	  addRandomFeature();
	  
	  
	  //test
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
          zoom: 9
        })
	  });

	window.setInterval(addRandomFeature, 1000);	  
    </script>
  </body>
</html>