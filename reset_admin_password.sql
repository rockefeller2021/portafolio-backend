-- Script para resetear la contraseña del usuario admin
-- Contraseña: admin123
-- Hash BCrypt de 'admin123': $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

-- Actualizar la contraseña del usuario admin
UPDATE users 
SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE username = 'admin';

-- Verificar el usuario
SELECT id, username, email, role, created_at FROM users WHERE username = 'admin';
