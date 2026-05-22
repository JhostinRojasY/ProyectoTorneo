CREATE DATABASE IF NOT EXISTS torneo_db;
USE torneo_db;

CREATE TABLE IF NOT EXISTS Rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id INT,
    FOREIGN KEY (rol_id) REFERENCES Rol(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    usuario_id INT,
    estado VARCHAR(20) DEFAULT 'INSCRITO',
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS Llave;
CREATE TABLE IF NOT EXISTS Llave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipo1 VARCHAR(100),
    equipo2 VARCHAR(100),
    ganador VARCHAR(100),
    previo1_id INT,
    previo2_id INT,
    FOREIGN KEY (previo1_id) REFERENCES Llave(id) ON DELETE SET NULL,
    FOREIGN KEY (previo2_id) REFERENCES Llave(id) ON DELETE SET NULL
);

-- Insertar roles por defecto
INSERT IGNORE INTO Rol (nombre) VALUES ('Administrador');
INSERT IGNORE INTO Rol (nombre) VALUES ('Equipo');

-- Insertar un administrador por defecto (admin / admin123)
-- Nota: En un entorno real la contraseña debería estar hasheada.
INSERT IGNORE INTO Usuario (username, password, rol_id) VALUES ('admin', 'admin123', 1);
