<?php
	//$_POST['ID']='3';
	//$_POST['Day']='2022-06-04';
	if(isset($_POST['ID'])&&isset($_POST['Day'])){
		require_once '../conn.php';
		$ID=$_POST['ID'];
		$Day=$_POST['Day'];
		$sql="SELECT `Time`,`Tempature` FROM `location_tempature` WHERE `RaspberryID`='$ID' and `Day`='$Day'";
		$result=$conn->query($sql);
		
		while ($row = $result->fetch_assoc()) {
		
		$list[]=$row;
		
	  }
	  print_r(json_encode($list, JSON_UNESCAPED_UNICODE));
		
	}
?>