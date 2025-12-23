-- Script de inicialización de la base de datos
-- Portfolio Rafael Alvarado García
-- Autor: Rafael Alvarado García

-- Crear usuario administrador por defecto
-- Contraseña: admin123 (encriptada con BCrypt)
-- IMPORTANTE: Cambiar esta contraseña en producción

INSERT INTO users (username, email, password, role, created_at)
VALUES (
    'admin',
    'admin@rafaelalvarado.com',
    '$2a$10$BPc67YkbeDXnQuP.7SJIZ.TY2hKm7rqNFnPdDqOXe7bXI6m5XMS6G',
    'ADMIN',
    NOW()
);

-- Insertar posts de ejemplo para el blog
INSERT INTO blog_posts (title, excerpt, content, category, read_time, published, author_id, created_at, updated_at)
VALUES
(
    'Automatización de Pruebas con Playwright',
    'Descubre cómo implementar pruebas E2E robustas y escalables utilizando Playwright en proyectos modernos.',
    '<h2>Introducción a Playwright</h2><p>Playwright es una herramienta moderna de automatización de pruebas...</p>',
    'Testing',
    '5 min',
    TRUE,
    1,
    NOW(),
    NOW()
),
(
    'Angular 20: Zoneless Applications',
    'Explorando las nuevas características de Angular 20, incluyendo aplicaciones sin Zone.js y Signals mejorados.',
    '<h2>¿Qué son las Zoneless Applications?</h2><p>Angular 20 introduce la posibilidad de crear aplicaciones sin Zone.js...</p>',
    'Frontend',
    '8 min',
    TRUE,
    1,
    NOW(),
    NOW()
),
(
    'Spring Boot 3.4: Novedades y Mejoras',
    'Análisis de las nuevas características de Spring Boot 3.4 y cómo aprovecharlas en tus proyectos.',
    '<h2>Nuevas características de Spring Boot 3.4</h2><p>Spring Boot 3.4 trae importantes mejoras...</p>',
    'Backend',
    '6 min',
    TRUE,
    1,
    NOW(),
    NOW()
),
(
    'Inteligencia Artificial con Gemini',
    'Cómo integrar Gemini AI en tus aplicaciones para crear asistentes inteligentes y automatizar tareas.',
    '<h2>Introducción a Gemini AI</h2><p>Gemini es el modelo de IA más avanzado de Google...</p>',
    'IA',
    '10 min',
    TRUE,
    1,
    NOW(),
    NOW()
);

-- Insertar tags para los posts
INSERT INTO blog_post_tags (post_id, tag)
VALUES
(1, 'Playwright'),
(1, 'Testing'),
(1, 'Automation'),
(2, 'Angular'),
(2, 'Signals'),
(2, 'Zoneless'),
(3, 'Spring Boot'),
(3, 'Java'),
(3, 'Backend'),
(4, 'Gemini'),
(4, 'AI'),
(4, 'Python');
