<?php
	
	//$_POST['ID']=3;
	//$_POST['Day']='2022-06-04';
	if($_POST['ID'] && $_POST['Day']){
		require_once "../conn.php";
		$ID=$_POST['ID'];
		$Day=$_POST['Day'];
		$sql="SELECT `Growth` FROM `tomato_growth` WHERE '$ID'=`RaspberryID` and '$Day'=`Day` ORDER BY `Day`";
		$result=$conn->query($sql);
		
		$row = $result->fetch_assoc();
		switch($row['Growth']){
			case '發芽期':
			echo 0;
			break;
			case '成長期':
			echo 1;
			break;
			case '開花期':
			echo 2;
			break;
			case '結果期':
			echo 3;
			break;
		}
		  //print_r($row['Growth']);
		}
?>