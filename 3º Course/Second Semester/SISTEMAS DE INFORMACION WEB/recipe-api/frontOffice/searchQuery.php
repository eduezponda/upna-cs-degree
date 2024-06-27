<?php
    require_once '../basededatosAPI/basededatos.php';

    if (isset($_POST['query'])) {
        $query = $_POST['query'] . '%';
        $con = conexion();

        $stmt = $con->prepare("SELECT DISTINCT query FROM final_comida WHERE query LIKE ?");
        $stmt->bind_param("s", $query);
        $stmt->execute();
        $result = $stmt->get_result();  

        $data = array();

        while ($row = $result->fetch_assoc()) {  
            $data[] = $row['query'];
        }

        echo json_encode($data);

        $con->close();
    }

    if(isset($_POST['querySearch'])){
        $query = $_POST['querySearch'];
        $con = conexion();

        $resultsPerPage = 9; // Resultados por página

        $stmt = $con->prepare("SELECT r.id FROM final_receta AS r JOIN final_comida AS c ON r.id_comida = c.id WHERE c.query = ?");

        $stmt->bind_param("s", $query);
        $stmt->execute();

        $result = $stmt->get_result();  

        $idRecetas = [];
        while ($row = $result->fetch_assoc()) {
            $idRecetas[] = $row['id']; 
        }

        $totalPages = ceil(count($idRecetas) / $resultsPerPage);
    
        $con->close();

        echo json_encode(array(
            'data' => $idRecetas,
            'totalPages' => $totalPages
        ));

        exit;
    }

    if (isset($_POST['diet'])) {
        $diet = $_POST['diet'] . '%';
        $con = conexion();

        $stmt = $con->prepare("SELECT DISTINCT dieta FROM final_dieta WHERE dieta LIKE ?");
        $stmt->bind_param("s", $diet);
        $stmt->execute();
        $result = $stmt->get_result();  

        $data = array();

        while ($row = $result->fetch_assoc()) {  
            $data[] = $row['dieta'];
        }

        echo json_encode($data);

        $con->close();
    }


    if(isset($_POST['dietSearch'])){
        $diet = $_POST['dietSearch'];
        $con = conexion();

        $resultsPerPage = 9; // Resultados por página

        $stmt = $con->prepare("SELECT r.id FROM final_receta AS r JOIN final_dieta AS d ON r.id = d.id_receta WHERE d.dieta = ?");

        $stmt->bind_param("s", $diet);
        $stmt->execute();

        $result = $stmt->get_result();  

        $idRecetas = [];
        while ($row = $result->fetch_assoc()) {
            $idRecetas[] = $row['id']; 
        }

        $totalPages = ceil(count($idRecetas) / $resultsPerPage);
    
        $con->close();

        echo json_encode(array(
            'data' => $idRecetas,
            'totalPages' => $totalPages
        ));
    }

    if (isset($_POST['kitchen'])) {
        $kitchen = $_POST['kitchen'] . '%';
        $con = conexion();

        $stmt = $con->prepare("SELECT DISTINCT cocina FROM final_cocina WHERE cocina LIKE ?");
        $stmt->bind_param("s", $kitchen);
        $stmt->execute();
        $result = $stmt->get_result();  

        $data = array();

        while ($row = $result->fetch_assoc()) {  
            $data[] = $row['cocina'];
        }

        echo json_encode($data);

        $con->close();
    }


    if(isset($_POST['kitchenSearch'])){
        $kitchen = $_POST['kitchenSearch'];
        $con = conexion();

        $resultsPerPage = 9; // Resultados por página

        $stmt = $con->prepare("SELECT r.id FROM final_receta AS r JOIN final_cocina AS c ON r.id = c.id_receta WHERE c.cocina = ?");

        $stmt->bind_param("s", $kitchen);
        $stmt->execute();

        $result = $stmt->get_result();  

        $idRecetas = [];
        while ($row = $result->fetch_assoc()) {
            $idRecetas[] = $row['id']; 
        }

        $totalPages = ceil(count($idRecetas) / $resultsPerPage);
    
        $con->close();

        echo json_encode(array(
            'data' => $idRecetas,
            'totalPages' => $totalPages
        ));
    }

?>