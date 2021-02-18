-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-02-2021 a las 03:22:17
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `afa`
--
CREATE DATABASE IF NOT EXISTS `afa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `afa`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones`
--

CREATE TABLE `direcciones` (
  `id` varchar(32) NOT NULL,
  `calle` varchar(100) DEFAULT NULL,
  `localidad` varchar(100) DEFAULT NULL,
  `numero` varchar(5) DEFAULT NULL,
  `provincia` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `direcciones`
--

INSERT INTO `direcciones` (`id`, `calle`, `localidad`, `numero`, `provincia`) VALUES
('1f51f3c754eb4c94a36342f7ce785068', 'Boulevard Cecilia Grierson', 'CABA', '2050', 'Buenos Aires'),
('245da1e739cd4e0dbddaafdc61fb59d9', 'Av. Sarmiento', 'CABA', '4000', 'Buenos Aires'),
('5331ebd011514fbb99775a052771ec5f', 'Av. Sarmiento', 'CABA', '2290', 'Buenos Aires'),
('84d746d84be640479b9fe1b40c466d4c', 'Brandsen', 'CABA', '805', 'Buenos Aires'),
('91f0c2a558cf4b5d9452b9457fe9d7d9', 'Av. Belgrano', 'CABA', '3900', 'Buenos Aires'),
('aab36a43dcb94da1a280654872e97842', 'Av. Corrientes', 'CABA', '4600', 'Buenos Aires'),
('bce2949a024b494681f63077db3eb16a', 'Alicia Moreau de Justo', 'CABA', '2999', 'Buenos Aires'),
('c277540f330e4f5f840159e320da6672', 'Av. Maipu', 'CABA', '3200', 'Buenos Aires'),
('db0c0ec8e3f54be293686edebb496e71', 'Av. Pres. Figueroa Alcorta', 'CABA', '7597', 'Buenos Aires'),
('dcd4e422a0264b989735a01a204171a4', 'Av. Maipu', 'CABA', '1300', 'Buenos Aires'),
('e56197977d144adeb13cff0c2dc5295c', 'Av. Maipu', 'CABA', '1200', 'Buenos Aires'),
('ed43c2fa1e634511a715c07a88177b0e', 'Anchorena', 'CABA', '3000', 'Buenos Aires'),
('f5365c9a9b7e4ff0a79f453e45779147', 'Av. Maipu', 'CABA', '1300', 'Buenos Aires');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `cuit` varchar(11) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_fundacion` date NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `direccion_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`cuit`, `categoria`, `email`, `fecha_fundacion`, `nombre`, `telefono`, `direccion_id`) VALUES
('30525418835', 'A', 'info@cabj.com', '1905-04-03', 'Club Atletico Boca Juniors', '01157771200', '84d746d84be640479b9fe1b40c466d4c'),
('30526748448', 'A', 'info@cariverplate.com', '1901-05-25', 'Club Atletico River Plate', '01142425566', 'db0c0ec8e3f54be293686edebb496e71');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_jugador_equipo`
--

CREATE TABLE `historial_jugador_equipo` (
  `id` varchar(32) NOT NULL,
  `fecha_fin` date NOT NULL,
  `fecha_inicio` date NOT NULL,
  `posicion` varchar(55) NOT NULL,
  `equipo_cuit` varchar(11) DEFAULT NULL,
  `jugador_dni` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `historial_jugador_equipo`
--

INSERT INTO `historial_jugador_equipo` (`id`, `fecha_fin`, `fecha_inicio`, `posicion`, `equipo_cuit`, `jugador_dni`) VALUES
('01ef37c581ae4e8db9ae849b0c2e9566', '2013-02-01', '2003-02-01', 'DELANTERO', '30525418835', '12345670'),
('10270540056a4c508f435a0d7906a1fa', '2023-05-26', '2021-02-01', 'DEFENSOR', '30526748448', '87654321'),
('2a064683d6d14fa68f7bd45001671ad7', '2022-05-26', '2017-05-05', 'MEDIOCAMPISTA', '30526748448', '12034567'),
('3ae4ff6e54e645e98a0dc2b7fe023089', '2008-05-01', '2005-05-01', 'DEFENSOR', '30525418835', '87654321'),
('583328d7c4a040368676cb7af10855b2', '2023-05-21', '2019-05-05', 'DELANTERO', '30525418835', '12304567'),
('bc40442274714c1b87728cb4573ae71d', '2022-01-01', '2018-01-01', 'ARQUERO', '30525418835', '12345607'),
('bd7376b31070421b9258a85ada14b2b8', '2010-07-26', '2002-01-01', 'DELANTERO', '30526748448', '10203456'),
('c300714ed3644c01b80b9ccf7d0cc8cd', '2022-05-26', '2019-05-05', 'DELANTERO', '30526748448', '12345678'),
('c68724aa45ff4694a7336427405b8d40', '1998-11-20', '1993-11-01', 'MEDIOCAMPISTA', '30526748448', '10203045'),
('d12af49f341347629e936deb9f6dab40', '2024-05-21', '2021-01-01', 'DEFENSOR', '30525418835', '12340567'),
('e12d311df9e04415a1c7bcfca8434990', '2022-05-26', '2016-05-05', 'ARQUERO', '30526748448', '10234567'),
('f27b3ce554af4f9597cb618aeff22e90', '2022-01-01', '2018-01-01', 'MEDIOCAMPISTA', '30525418835', '12345067');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `integrantes`
--

CREATE TABLE `integrantes` (
  `dni` varchar(8) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `direccion_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `integrantes`
--

INSERT INTO `integrantes` (`dni`, `apellido`, `email`, `nombre`, `telefono`, `direccion_id`) VALUES
('10203045', 'Gallardo', 'marcelogallardo@gmail.com', 'Marcelo', '01130303030', 'e56197977d144adeb13cff0c2dc5295c'),
('10203456', 'Ortega', 'arielortega@gmail.com', 'Ariel', '01120202020', 'aab36a43dcb94da1a280654872e97842'),
('10234567', 'Armani', 'francoarmani@gmail.com', 'Franco', '01144444444', 'c277540f330e4f5f840159e320da6672'),
('12034567', 'Fernández', 'nachofernandez@gmail.com', 'Ignacio Martín', '01155555555', '91f0c2a558cf4b5d9452b9457fe9d7d9'),
('12304567', 'Tevez', 'carlostevez@gmail.com', 'Carlos', '01166666666', '245da1e739cd4e0dbddaafdc61fb59d9'),
('12340567', 'Rojo', 'marcosrojo@gmail.com', 'Marcos', '01177777777', 'bce2949a024b494681f63077db3eb16a'),
('12345067', 'González', 'diegogonzalez@gmail.com', 'Diego Hernan', '01188888888', 'f5365c9a9b7e4ff0a79f453e45779147'),
('12345607', 'Andrada', 'estebanandrada@gmail.com', 'Esteban', '01199999999', 'dcd4e422a0264b989735a01a204171a4'),
('12345670', 'Riquelme', 'romanriquelme@gmail.com', 'Roman', '01110101010', '5331ebd011514fbb99775a052771ec5f'),
('12345678', 'Borré', 'rafaelsantosborre@gmail.com', 'Rafael Santos', '01122222222', 'ed43c2fa1e634511a715c07a88177b0e'),
('87654321', 'Maidana', 'jonatanmaidana@gmail.com', 'Jonatan', '01133333333', '1f51f3c754eb4c94a36342f7ce785068');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugadores`
--

CREATE TABLE `jugadores` (
  `dni` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `jugadores`
--

INSERT INTO `jugadores` (`dni`) VALUES
('10203045'),
('10203456'),
('10234567'),
('12034567'),
('12304567'),
('12340567'),
('12345067'),
('12345607'),
('12345670'),
('12345678'),
('87654321');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD PRIMARY KEY (`cuit`),
  ADD KEY `FKkpbswb9oeno68etgtbsasi2e7` (`direccion_id`);

--
-- Indices de la tabla `historial_jugador_equipo`
--
ALTER TABLE `historial_jugador_equipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhq5rlw6hn9fenn335k4sjlv1i` (`equipo_cuit`),
  ADD KEY `FKs634fwl4sfgd55h76r1yeyuaw` (`jugador_dni`);

--
-- Indices de la tabla `integrantes`
--
ALTER TABLE `integrantes`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `FKj16slvmvnr5b4wc5jvgg9783v` (`direccion_id`);

--
-- Indices de la tabla `jugadores`
--
ALTER TABLE `jugadores`
  ADD PRIMARY KEY (`dni`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD CONSTRAINT `FKkpbswb9oeno68etgtbsasi2e7` FOREIGN KEY (`direccion_id`) REFERENCES `direcciones` (`id`);

--
-- Filtros para la tabla `historial_jugador_equipo`
--
ALTER TABLE `historial_jugador_equipo`
  ADD CONSTRAINT `FKhq5rlw6hn9fenn335k4sjlv1i` FOREIGN KEY (`equipo_cuit`) REFERENCES `equipos` (`cuit`),
  ADD CONSTRAINT `FKs634fwl4sfgd55h76r1yeyuaw` FOREIGN KEY (`jugador_dni`) REFERENCES `jugadores` (`dni`);

--
-- Filtros para la tabla `integrantes`
--
ALTER TABLE `integrantes`
  ADD CONSTRAINT `FKj16slvmvnr5b4wc5jvgg9783v` FOREIGN KEY (`direccion_id`) REFERENCES `direcciones` (`id`);

--
-- Filtros para la tabla `jugadores`
--
ALTER TABLE `jugadores`
  ADD CONSTRAINT `FK1j0m5j1qkwe616cquyvlkh1lt` FOREIGN KEY (`dni`) REFERENCES `integrantes` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
