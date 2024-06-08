<?php
    //incluir como si fuera un módulo en otro php
    //include clase.php;
    //tener abierto xampp
    //se puede crear las tablas a mano perfectamente en phpmyadmin
    //file_get_contents(archivo.html)
    //input<type="hidden">

    function conectarBD(){
        $servidor = "localhost";
        $bd = "siw2024";
        $user = "root";
        $password = "";

        $conexion = mysqli_connect($servidor,$user,$password,$bd);
    
        if(!$con){
            echo "Error de conexión de base de datos <br>";
            echo "Error número: " . mysqli_connect_errno();
            echo "Texto error: " . mysqli_connect_error();
        }
        return $conexion;
    }
    function insertarBD($conexion){
        $consulta = "Insert into Personas(nombre, apellido1, apellido2) values ('Maite, Ugarteburu','Uberuaga')";
        $resultado = $conexion->query($consulta);
        
        if($resultado){
            echo "Se ha insertado correctamente";
        }
        else{
            "No se han insertado los datos";
        }
    }
    function consultarBD($conexion){
        $consulta = "Select * from Personas";
        $resultado = $conexion->query($consulta);
        
        while($datos = $resultado->fetch_assoc()){ //datos es un array asociativo al poner el assoc
            echo $datos["nombre"] . " " . $datos["apellido1"] . " " . $datos["apellido2"] . "<br>";
            //el bucle te permite comprobar si hay datos y a la vez los puedes tratar
            //este metodo es menos seguro que otros, pero el no va a medir la seguridad en el trabajo
        }
    }
    function insertar2BD($conexion){
        $stmt = $conexion->prepare("Insert into Personas (nombre,apellido1,apellido2) values (?,?,?,?)");
        $stmt->bind_param("sss",$nom,$ape1,$ape2);
        $nom = "Eduardo";
        $ape1 = "Ezponda";
        $ape2 = "Igea;"
        $stmt->execute();
        
        if($stmt->affected_rows){ //invocas un atributo y no un método porque no es affected_rows()
            echo "Todo correcto. <br>";
        }
        else{
            echo "No se han insertado los datos";
        }

        //coger datos de la base de datos
        $stmt = $conexion->prepare("Select * from personas;");
        $stmt->execute();
        $resultado = $stmt->get_result();

        while($datos = $resultado->fetch_assoc()){
            echo $datos["nombre"] . " " . $datos["apellido1"] . " " . $datos["apellido2"] . "<br>";
        }
    }

?>