<?php

    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: POST, GET, OPTIONS, PUT, DELETE");
    header("Access-Control-Allow-Headers: Content-Type, X-Auth-Token, Origin, Authorization");
    
    $server = "localhost";
    $db = "labajax";
    
    $departure = "";
    if ($_SERVER["REQUEST_METHOD"] === "GET" && isset($_GET["departure"])) {
        $departure = $_GET["departure"];
    } else if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["departure"])) {
        $departure = $_POST["departure"];
    } else {
        die("Invalid request!");
    }

    $conn = new mysqli($server, "root", "", $db);
    if ($conn->connect_error) {
        die("Connection failed to MySQL ".$conn->connect_error);
    }
    
    $sql = "SELECT DISTINCT destination FROM `routes` WHERE departure = \"$departure\"";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $destination = $row['destination'];
            echo "<option value=\"$destination\">$destination</option>";
        }
    }
    $conn->close();
?>