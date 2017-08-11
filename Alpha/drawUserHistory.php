<?php
require_once("functions.php");
dbconn();
if(!isset($_SESSION['id']))
{
	echo "<script>alert('请先登录');";
	echo "window.location='index.php';</script>";
	die();
}

stdhead();
?>

	<div style="height:600px; margin:0 auto">
		<div style=" height:auto; width:750px;margin:0 auto">
			<div class="modal-header">
				<ul class="nav nav-tabs">
					<li><a  class="active" href="#login1" data-toggle="tab">巡检员历史移动轨迹</a></li>
				</ul>
			</div>
            <div style="width:560px;margin:0 auto">         
            <div style=" width:200px;margin-left:10px" id = "returnInfo">
            </div>
            </div>
			<div class="modal-body">
				<form id="ipqcForm" name = "ipqcForm" class="form-horizontal" action="takedrawUserHistory.php" method="post">
					<div class="control-group">
						<label style="width:80px" class="control-label" >巡检员编号</label>
						<div style="margin-left:90px" class="controls">
							<input oninput="ipqckeypress(event)" onfocus="ipqcFocus()" onBlur="ipqcBlur()" id="ipqcName" type="text" required placeholder="巡检员编号" class="input-xlarge" name="ipqcName"  autocomplete="off" autofocus required>
                            <span style="display:inline-block; width:200px" id="ipqc_info"></span>
							
							<button id="ipqcSubmit" style="margin-left:15px" class="btn primary " type="submit" name="submit">搜索</button>
                            
							<div style="display:none; height:10px" id="ipqcNumInput"></div>
                        </div>
					</div
				</form>
			</div>
			<div class="modal-body">
				<div id="map" class="map"></div>
			</div>
		</div>
	</div>

</div>
</div>

	<script>
	  var LineStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({  
		  width: 4,  
          color: [255, 0, 0, 1]  
        })  
	  });
	  
	  var seIconStyle = new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
          src: 'img/startEndicon.png'
        })
      });

	$(function() {
	  var ajaxFormOption = {
		success: function(data){
		  var result = eval(data);
		  if(result == "Userempty"){
			document.getElementById('returnInfo').innerHTML = '输入巡检员编号无效';
		  }
		  else{
			var pointList = [];
			var dataLen = result.length;  
			for(var i = 0; i < dataLen; i = i + 1){
			  var x = Number(result[i][1]);
			  var y = Number(result[i][0]);
			  pointList.push([ol.proj.fromLonLat([x,y])[0],ol.proj.fromLonLat([x,y])[1]]);
			}
			
			// 画运动轨迹
			var saoguan = new ol.Feature({  
			  geometry: new ol.geom.LineString(pointList) 
			}); 
			saoguan.setStyle(LineStyle);	

			// 画起点和终点
			var x1 = Number(result[1][1]);
			var y1 = Number(result[1][0]);
			console.log(x1 + " " + y1);
			var startMarker = new ol.Feature({
			  type: 'icon',
			  geometry: new ol.geom.Point(ol.proj.fromLonLat([Number(result[0][1]),Number(result[0][0])]))
			});
			  
			var endMarker = new ol.Feature({
			  type: 'icon',
			  geometry: new ol.geom.Point(ol.proj.fromLonLat([Number(result[dataLen-1][1]),Number(result[dataLen-1][0])]))
			});
			  
			startMarker.setStyle(seIconStyle);
			endMarker.setStyle(seIconStyle);
			
			vectorSource.clear();
			vectorSource.addFeature(startMarker);
			vectorSource.addFeature(endMarker);
			vectorSource.addFeature(saoguan);
		  }
		  console.log(data);
		}
	  };
	  
	  
	  $("#ipqcForm").ajaxForm(ajaxFormOption);
	});
	
	var vectorSource = new ol.source.Vector({});
			  
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

<?php
stdfoot();
?>