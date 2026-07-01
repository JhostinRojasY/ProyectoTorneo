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

INSERT IGNORE INTO Rol (nombre) VALUES ('Administrador');
INSERT IGNORE INTO Rol (nombre) VALUES ('Equipo');

INSERT IGNORE INTO Usuario (username, password, rol_id) VALUES ('admin', 'admin123', 1);

INSERT IGNORE INTO Equipo (nombre) VALUES
('México'), ('Sudáfrica'), ('Corea del Sur'), ('República Checa'),
('Canadá'), ('Bosnia y Herzegovina'), ('Qatar'), ('Suiza'),
('Brasil'), ('Marruecos'), ('Haití'), ('Escocia'),
('Estados Unidos'), ('Paraguay'), ('Australia'), ('Turquía'),
('Alemania'), ('Curazao'), ('Costa de Marfil'), ('Ecuador'),
('Países Bajos'), ('Japón'), ('Suecia'), ('Túnez'),
('Bélgica'), ('Egipto'), ('Irán'), ('Nueva Zelanda'),
('España'), ('Cabo Verde'), ('Arabia Saudita'), ('Uruguay'),
('Francia'), ('Senegal'), ('Irak'), ('Noruega'),
('Argentina'), ('Argelia'), ('Austria'), ('Jordania'),
('Portugal'), ('República Democrática del Congo'), ('Uzbekistán'), ('Colombia'),
('Inglaterra'), ('Croacia'), ('Ghana'), ('Panamá');