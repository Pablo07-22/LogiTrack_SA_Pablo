CREATE DATABASE LogiTrack_SA_Pablo;

USE LogiTrack_SA_Pablo;

-- =====================================================
-- 1. TABLA CIUDAD
-- =====================================================

CREATE TABLE ciudad (
    id_ciudad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_ciudad VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- 2. TABLA USUARIO
-- =====================================================

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    rol ENUM('ADMIN', 'EMPLEADO')
        NOT NULL DEFAULT 'EMPLEADO',

    estado ENUM('PENDIENTE', 'ACTIVO', 'INACTIVO')
        NOT NULL DEFAULT 'PENDIENTE',

    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- 3. TABLA BODEGA
-- =====================================================

CREATE TABLE bodega (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,

    ciudad_fk BIGINT NOT NULL,
    usuario_fk BIGINT NOT NULL,

    capacidad DECIMAL(12,2) NOT NULL,

    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_bodega_ciudad
        FOREIGN KEY (ciudad_fk)
        REFERENCES ciudad(id_ciudad),

    CONSTRAINT fk_bodega_usuario
        FOREIGN KEY (usuario_fk)
        REFERENCES usuario(id),

    CONSTRAINT chk_bodega_capacidad
        CHECK (capacidad > 0)
);

-- =====================================================
-- 4. TABLA MARCA
-- =====================================================

CREATE TABLE marca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_marca VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- 5. TABLA CATEGORIA_PRODUCTO
-- =====================================================

CREATE TABLE categoria_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_categoria VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- 6. TABLA PRODUCTO
-- =====================================================

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,

    marca_fk BIGINT NOT NULL,
    categoria_fk BIGINT NOT NULL,

    precio_unitario DECIMAL(12,2) NOT NULL,

    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_producto_marca
        FOREIGN KEY (marca_fk)
        REFERENCES marca(id),

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_fk)
        REFERENCES categoria_producto(id),

    CONSTRAINT chk_producto_precio
        CHECK (precio_unitario >= 0)
);

-- =====================================================
-- 7. TABLA INVENTARIO_BODEGA
-- =====================================================

CREATE TABLE inventario_bodega (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    bodega_fk BIGINT NOT NULL,
    producto_fk BIGINT NOT NULL,

    stock INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_inventario_bodega
        FOREIGN KEY (bodega_fk)
        REFERENCES bodega(id),

    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (producto_fk)
        REFERENCES producto(id),

    CONSTRAINT uq_inventario_bodega_producto
        UNIQUE (bodega_fk, producto_fk),

    CONSTRAINT chk_inventario_stock
        CHECK (stock >= 0)
);

-- =====================================================
-- 8. TABLA MOVIMIENTO
-- Registra entradas, salidas y transferencias.
-- =====================================================

CREATE TABLE movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    tipo_movimiento ENUM(
        'ENTRADA',
        'SALIDA',
        'TRANSFERENCIA'
    ) NOT NULL,

    usuario_responsable_fk BIGINT NOT NULL,

    bodega_origen_fk BIGINT NULL,
    bodega_destino_fk BIGINT NULL,

    observacion VARCHAR(500),

    CONSTRAINT fk_movimiento_usuario
        FOREIGN KEY (usuario_responsable_fk)
        REFERENCES usuario(id),

    CONSTRAINT fk_movimiento_origen
        FOREIGN KEY (bodega_origen_fk)
        REFERENCES bodega(id),

    CONSTRAINT fk_movimiento_destino
        FOREIGN KEY (bodega_destino_fk)
        REFERENCES bodega(id),

    CONSTRAINT chk_movimiento_bodegas
        CHECK (
            (
                tipo_movimiento = 'ENTRADA'
                AND bodega_origen_fk IS NULL
                AND bodega_destino_fk IS NOT NULL
            )
            OR
            (
                tipo_movimiento = 'SALIDA'
                AND bodega_origen_fk IS NOT NULL
                AND bodega_destino_fk IS NULL
            )
            OR
            (
                tipo_movimiento = 'TRANSFERENCIA'
                AND bodega_origen_fk IS NOT NULL
                AND bodega_destino_fk IS NOT NULL
                AND bodega_origen_fk <> bodega_destino_fk
            )
        )
);

-- =====================================================
-- 9. TABLA DETALLE_MOVIMIENTO
-- Permite registrar varios productos por movimiento.
-- =====================================================

CREATE TABLE detalle_movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    movimiento_fk BIGINT NOT NULL,
    producto_fk BIGINT NOT NULL,

    cantidad INT NOT NULL,

    CONSTRAINT fk_detalle_movimiento
        FOREIGN KEY (movimiento_fk)
        REFERENCES movimiento(id),

    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (producto_fk)
        REFERENCES producto(id),

    CONSTRAINT uq_movimiento_producto
        UNIQUE (movimiento_fk, producto_fk),

    CONSTRAINT chk_detalle_cantidad
        CHECK (cantidad > 0)
);

-- =====================================================
-- 10. TABLA AUDITORIA
-- Registra cambios realizados en las entidades.
-- =====================================================

CREATE TABLE auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    tipo_operacion ENUM(
        'INSERTAR',
        'ACTUALIZAR',
        'BORRAR'
    ) NOT NULL,

    usuario_fk BIGINT NULL,

    entidad_afectada VARCHAR(100) NOT NULL,

    id_registro BIGINT NOT NULL,

    valores_anteriores JSON NULL,
    valores_nuevos JSON NULL,

    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (usuario_fk)
        REFERENCES usuario(id)
        ON DELETE SET NULL
);

-- =====================================================
-- 0. OBTENER EL USUARIO ADMINISTRADOR
-- =====================================================

SET @admin_id = (
    SELECT id
    FROM usuario
    WHERE username = 'admin'
    LIMIT 1
);

-- Comprobar que existe el administrador.
SELECT @admin_id AS administrador_id;


-- =====================================================
-- 1. CIUDADES
-- =====================================================

INSERT INTO ciudad (nombre_ciudad)
SELECT 'Medellín'
WHERE NOT EXISTS (
    SELECT 1 FROM ciudad WHERE nombre_ciudad = 'Medellín'
);

INSERT INTO ciudad (nombre_ciudad)
SELECT 'Bogotá'
WHERE NOT EXISTS (
    SELECT 1 FROM ciudad WHERE nombre_ciudad = 'Bogotá'
);

INSERT INTO ciudad (nombre_ciudad)
SELECT 'Cali'
WHERE NOT EXISTS (
    SELECT 1 FROM ciudad WHERE nombre_ciudad = 'Cali'
);


-- =====================================================
-- 2. MARCAS
-- =====================================================

INSERT INTO marca (nombre_marca)
SELECT 'Apple'
WHERE NOT EXISTS (
    SELECT 1 FROM marca WHERE nombre_marca = 'Apple'
);

INSERT INTO marca (nombre_marca)
SELECT 'Samsung'
WHERE NOT EXISTS (
    SELECT 1 FROM marca WHERE nombre_marca = 'Samsung'
);

INSERT INTO marca (nombre_marca)
SELECT 'Logitech'
WHERE NOT EXISTS (
    SELECT 1 FROM marca WHERE nombre_marca = 'Logitech'
);

INSERT INTO marca (nombre_marca)
SELECT 'Lenovo'
WHERE NOT EXISTS (
    SELECT 1 FROM marca WHERE nombre_marca = 'Lenovo'
);

INSERT INTO marca (nombre_marca)
SELECT 'HP'
WHERE NOT EXISTS (
    SELECT 1 FROM marca WHERE nombre_marca = 'HP'
);


-- =====================================================
-- 3. CATEGORÍAS
-- =====================================================

INSERT INTO categoria_producto (tipo_categoria)
SELECT 'Celulares'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto
    WHERE tipo_categoria = 'Celulares'
);

INSERT INTO categoria_producto (tipo_categoria)
SELECT 'Computadores'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto
    WHERE tipo_categoria = 'Computadores'
);

INSERT INTO categoria_producto (tipo_categoria)
SELECT 'Accesorios'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto
    WHERE tipo_categoria = 'Accesorios'
);

INSERT INTO categoria_producto (tipo_categoria)
SELECT 'Monitores'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto
    WHERE tipo_categoria = 'Monitores'
);


-- =====================================================
-- 4. PRODUCTOS
-- =====================================================

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'iPhone 14',
    m.id,
    c.id,
    2500000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'Apple'
AND c.tipo_categoria = 'Celulares'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'iPhone 14'
);

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'Samsung Galaxy S24',
    m.id,
    c.id,
    3200000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'Samsung'
AND c.tipo_categoria = 'Celulares'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Samsung Galaxy S24'
);

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'Lenovo ThinkPad',
    m.id,
    c.id,
    4200000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'Lenovo'
AND c.tipo_categoria = 'Computadores'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Lenovo ThinkPad'
);

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'Mouse Logitech M185',
    m.id,
    c.id,
    65000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'Logitech'
AND c.tipo_categoria = 'Accesorios'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Mouse Logitech M185'
);

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'Monitor Samsung 24',
    m.id,
    c.id,
    850000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'Samsung'
AND c.tipo_categoria = 'Monitores'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Monitor Samsung 24'
);

INSERT INTO producto
(nombre, marca_fk, categoria_fk, precio_unitario)
SELECT
    'HP ProBook',
    m.id,
    c.id,
    3800000.00
FROM marca m
CROSS JOIN categoria_producto c
WHERE m.nombre_marca = 'HP'
AND c.tipo_categoria = 'Computadores'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'HP ProBook'
);


-- =====================================================
-- 5. BODEGAS
-- =====================================================

INSERT INTO bodega
(nombre, ciudad_fk, usuario_fk, capacidad)
SELECT
    'Bodega Medellín',
    c.id_ciudad,
    @admin_id,
    5000.00
FROM ciudad c
WHERE c.nombre_ciudad = 'Medellín'
AND @admin_id IS NOT NULL
AND NOT EXISTS (
    SELECT 1 FROM bodega WHERE nombre = 'Bodega Medellín'
);

INSERT INTO bodega
(nombre, ciudad_fk, usuario_fk, capacidad)
SELECT
    'Bodega Bogotá',
    c.id_ciudad,
    @admin_id,
    7000.00
FROM ciudad c
WHERE c.nombre_ciudad = 'Bogotá'
AND @admin_id IS NOT NULL
AND NOT EXISTS (
    SELECT 1 FROM bodega WHERE nombre = 'Bodega Bogotá'
);

INSERT INTO bodega
(nombre, ciudad_fk, usuario_fk, capacidad)
SELECT
    'Bodega Cali',
    c.id_ciudad,
    @admin_id,
    10000.00
FROM ciudad c
WHERE c.nombre_ciudad = 'Cali'
AND @admin_id IS NOT NULL
AND NOT EXISTS (
    SELECT 1 FROM bodega WHERE nombre = 'Bodega Cali'
);


-- =====================================================
-- 6. INVENTARIOS
-- =====================================================

-- BODEGA MEDELLÍN

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 100
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Medellín'
AND p.nombre = 'iPhone 14'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 50
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Medellín'
AND p.nombre = 'Mouse Logitech M185'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 20
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Medellín'
AND p.nombre = 'Lenovo ThinkPad'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);


-- BODEGA BOGOTÁ

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 25
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Bogotá'
AND p.nombre = 'Lenovo ThinkPad'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 40
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Bogotá'
AND p.nombre = 'Samsung Galaxy S24'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);


-- BODEGA CALI

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 20
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Cali'
AND p.nombre = 'iPhone 14'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 10
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Cali'
AND p.nombre = 'Monitor Samsung 24'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);

INSERT INTO inventario_bodega (bodega_fk, producto_fk, stock)
SELECT b.id, p.id, 15
FROM bodega b
CROSS JOIN producto p
WHERE b.nombre = 'Bodega Cali'
AND p.nombre = 'HP ProBook'
AND NOT EXISTS (
    SELECT 1 FROM inventario_bodega i
    WHERE i.bodega_fk = b.id AND i.producto_fk = p.id
);


-- =====================================================
-- 7. MOVIMIENTOS DE EJEMPLO
-- =====================================================

-- ENTRADA DE 120 IPHONE 14 A MEDELLÍN

INSERT INTO movimiento (
    tipo_movimiento,
    usuario_responsable_fk,
    bodega_origen_fk,
    bodega_destino_fk,
    observacion
)
SELECT
    'ENTRADA',
    @admin_id,
    NULL,
    b.id,
    'Entrada inicial de productos para demostración'
FROM bodega b
WHERE b.nombre = 'Bodega Medellín'
AND @admin_id IS NOT NULL;

SET @movimiento_entrada = LAST_INSERT_ID();

INSERT INTO detalle_movimiento (
    movimiento_fk,
    producto_fk,
    cantidad
)
SELECT
    @movimiento_entrada,
    p.id,
    120
FROM producto p
WHERE p.nombre = 'iPhone 14';


-- TRANSFERENCIA DE 20 IPHONE 14 DE MEDELLÍN A CALI

INSERT INTO movimiento (
    tipo_movimiento,
    usuario_responsable_fk,
    bodega_origen_fk,
    bodega_destino_fk,
    observacion
)
SELECT
    'TRANSFERENCIA',
    @admin_id,
    origen.id,
    destino.id,
    'Transferencia de iPhone 14 entre bodegas'
FROM bodega origen
CROSS JOIN bodega destino
WHERE origen.nombre = 'Bodega Medellín'
AND destino.nombre = 'Bodega Cali'
AND @admin_id IS NOT NULL;

SET @movimiento_transferencia = LAST_INSERT_ID();

INSERT INTO detalle_movimiento (
    movimiento_fk,
    producto_fk,
    cantidad
)
SELECT
    @movimiento_transferencia,
    p.id,
    20
FROM producto p
WHERE p.nombre = 'iPhone 14';


-- SALIDA DE 5 LENOVO THINKPAD DE BOGOTÁ

INSERT INTO movimiento (
    tipo_movimiento,
    usuario_responsable_fk,
    bodega_origen_fk,
    bodega_destino_fk,
    observacion
)
SELECT
    'SALIDA',
    @admin_id,
    b.id,
    NULL,
    'Salida de productos para despacho'
FROM bodega b
WHERE b.nombre = 'Bodega Bogotá'
AND @admin_id IS NOT NULL;

SET @movimiento_salida = LAST_INSERT_ID();

INSERT INTO detalle_movimiento (
    movimiento_fk,
    producto_fk,
    cantidad
)
SELECT
    @movimiento_salida,
    p.id,
    5
FROM producto p
WHERE p.nombre = 'Lenovo ThinkPad';


-- =====================================================
-- 8. CONSULTAS PARA COMPROBAR LOS DATOS
-- =====================================================

-- BODEGAS Y CAPACIDADES

SELECT
    b.id,
    b.nombre,
    c.nombre_ciudad,
    b.capacidad
FROM bodega b
INNER JOIN ciudad c ON c.id_ciudad = b.ciudad_fk;


-- INVENTARIO COMPLETO

SELECT
    b.nombre AS bodega,
    p.nombre AS producto,
    i.stock,
    b.capacidad
FROM inventario_bodega i
INNER JOIN bodega b ON b.id = i.bodega_fk
INNER JOIN producto p ON p.id = i.producto_fk
ORDER BY b.nombre, p.nombre;


-- MOVIMIENTOS REGISTRADOS

SELECT
    m.id,
    m.fecha,
    m.tipo_movimiento,
    origen.nombre AS bodega_origen,
    destino.nombre AS bodega_destino,
    u.username AS responsable,
    m.observacion
FROM movimiento m
LEFT JOIN bodega origen ON origen.id = m.bodega_origen_fk
LEFT JOIN bodega destino ON destino.id = m.bodega_destino_fk
INNER JOIN usuario u ON u.id = m.usuario_responsable_fk
ORDER BY m.id DESC;


-- DETALLES DE LOS MOVIMIENTOS

SELECT
    m.id AS movimiento_id,
    m.tipo_movimiento,
    p.nombre AS producto,
    d.cantidad
FROM detalle_movimiento d
INNER JOIN movimiento m ON m.id = d.movimiento_fk
INNER JOIN producto p ON p.id = d.producto_fk
ORDER BY m.id;
