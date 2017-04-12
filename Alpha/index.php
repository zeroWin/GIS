<?php
require_once("functions.php");
dbconn();
stdhead();
?>
<!--  carousel -->
<hr>
<div class="container" style="">
<div id="myCarousel" style="" class="carousel slide">
  <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	  <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
   </ol>
				  <!-- Carousel items -->
  <div class="carousel-inner" style="">
	  <div class="active item">
	  	<img style="width:100%;height:500px" src="./img/001.jpg"/>
		<div class="carousel-caption">
			<h4>你好</h4>
			<p>
			2333
			2333
			2333
			</p>
		</div>
	  </div>
	  <div class="item">
	  	<img style="width:100%;height:500px" src="./img/002.jpg"/>
		<div class="carousel-caption">
			<h4>你好</h4>
			<p>
			2333
			2333
			2333
			</p>
		</div>
	  </div>
	  <div class="item">
	  	<img style="width:100%;height:500px" src="./img/003.jpg"/>
		<div class="carousel-caption">
			<h4>你好</h4>
			<p>
			2333
			2333
			2333
			</p>
		</div>
	  </div>
 </div>
				  <!-- Carousel nav -->
  <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
  <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>
</div>

<!--  end of carousel -->
<?php
stdfoot();
?>
