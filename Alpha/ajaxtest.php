<!DOCTYPE html>
<html>
  <head>
    <title>测试</title>
	<meta charset="utf-8">
    <link rel="stylesheet" href="css/ol.css" type="text/css">
    <!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>
    <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.1.custom.min.js"></script>
	<script src="js/ol.js"></script>
  </head>
  <body>
	<script>
/* function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();
	console.log(temp);
    return temp;        
}        
       
//调用方法 如        
post('mapDataFunction.php', {action:'RailWayRoute'}); */
function test(){
$.post('mapDataFunction.php','action=RailWayLocal',function(d){
  console.log(d);
})
}
window.setInterval(test, 1000);
    </script>
  </body>
</html>