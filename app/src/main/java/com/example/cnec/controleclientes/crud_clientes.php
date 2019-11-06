<?php
/*

create database controle_clientes;

use  controle_clientes;

CREATE table cliente(
    id INT( 11 ) AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(250),
 rua VARCHAR(150),
 numero int,
 bairro varchar(100)
 );
*/


//file_put_contents('entrada.txt', $_GET['nome'], FILE_APPEND);

try {
    $pdo = new PDO("mysql:host=localhost;dbname=controle_clientes;
                       charset=utf8", 'root', '');


    $metodo = $_SERVER['REQUEST_METHOD'];

    switch ($metodo) {
        case 'GET':
            if (isset($_GET['nome'])) {
                $consulta = $pdo->query('SELECT * FROM cliente WHERE nome like "%' . $_GET['nome'] . '%" ');
            } else {
                $consulta = $pdo->query('SELECT * FROM cliente');
            }
            $clientes = $consulta->fetchAll(PDO::FETCH_ASSOC);

            header('Content-Type: application/json; charset=utf-8');

            echo json_encode($clientes);
            break;

        case 'POST':
            $entrada = file_get_contents('php://input');
            $cliente = json_decode($entrada);

            $pdo->query("INSERT INTO cliente(nome, rua, numero, bairro) values('$cliente->nome', '$cliente->rua', $cliente->numero, '$cliente->bairro'); ");
            break;

        case 'PUT':
            $entrada = file_get_contents('php://input');
            $cliente = json_decode($entrada);

            $sql = "UPDATE cliente SET nome = '$cliente->nome', rua = '$cliente->rua', numero = $cliente->numero, bairro = '$cliente->bairro'  WHERE id = $cliente->id; ";

            $pdo->query($sql);
            break;

        case 'DELETE':
            $entrada = file_get_contents('php://input');
            $cliente = json_decode($entrada);

            $sql = "DELETE FROM cliente WHERE id = '$cliente->id'; ";

            $pdo->query($sql);

            $consulta = $pdo->query('SELECT * FROM cliente');

            $clientes = $consulta->fetchAll(PDO::FETCH_ASSOC);

            header('Content-Type: application/json; charset=utf-8');

            echo json_encode($clientes);
            break;
    }

} catch (Exception $e) {
    file_put_contents('exception.txt', print_r($e, true), FILE_APPEND);
}

