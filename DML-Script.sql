-- Step 1: Drop existing database if needed
DROP DATABASE IF EXISTS user_management_service;

-- Step 2: Create the database
CREATE DATABASE user_management_service;

USE user_management_service;

-- Insert roles into the roles table
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('MODERATOR');
INSERT INTO roles (name) VALUES ('GUEST');

-- Insert sample users into the users table (Including status)
INSERT INTO users (username, email, password, status) 
VALUES 
('johndoe', 'johndoe@example.com', 'hashedpassword123', 'ACTIVE'),
('janedoe', 'janedoe@example.com', 'hashedpassword456', 'INACTIVE'),
('adminuser', 'admin@example.com', 'hashedpassword789', 'ACTIVE');
INSERT INTO roles (name) VALUES ('ROLE_USER');


-- Assign roles to the users
INSERT INTO user_roles (user_id, role_id) 
VALUES 
(1, 2),  -- johndoe, USER role
(2, 2), (2, 3),  -- janedoe, USER and MODERATOR roles
(3, 1), (3, 3);  -- adminuser, ADMIN and MODERATOR roles
