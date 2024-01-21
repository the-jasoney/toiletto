-- Create roles (runs after initialization)
INSERT INTO roles(name) VALUES('ROLE_USER') ON CONFLICT DO NOTHING