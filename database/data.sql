USE internship_management;

INSERT INTO role (role_name) VALUES
                                 ('student'),
                                 ('company'),
                                 ('manager'),
                                 ('admin');

-- Default admin user: email=admin@school.fr / password=admin
-- Password stored as SHA-256 hash (handled by the app via PasswordUtil)
-- Insert manually only for first setup: password hash of "admin" with SHA-256
INSERT INTO user (first_name, last_name, email, password, id_role) VALUES
    ('Admin', 'System', 'admin@3wacademy.fr', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 4);