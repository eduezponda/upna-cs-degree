<!DOCTYPE html>
<html>
<head>
	<title>Esta es una prueba</title>
</head>
<body>

	<?php

		if (isset($_GET["var1"]) && isset($_GET["var2"]) && isset($_GET["var3"]) && isset($_GET["var4"])) {
			echo "<table border=1>";
			echo "<tr>";
			echo "<th>Cabecera1</th>";
			echo "<th>Cabecera2</th>";
			echo "<th>Cabecera3</th>";
			echo "<th>Cabecera4</th>";
			echo "</t>";

			for($i = 0; $i < 10; $i++) {
				echo "<tr>";
				for ($j = 0; $j < 4; $j++) {
					echo "<td>";
					echo $_GET["var" . $j+1] . " $j";
					echo "</td>";
				}
				echo "</tr>";
			}
			echo "</table>";
		} else
		{
			echo "Faltan variables";
		}
	?>

</body>
</html>


