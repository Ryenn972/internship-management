CREATE DATABASE IF NOT EXISTS internship_management;
USE internship_management;

CREATE TABLE role (
                      id_role INT AUTO_INCREMENT PRIMARY KEY,
                      role_name VARCHAR(50) NOT NULL
);

CREATE TABLE user (
                      id_user INT AUTO_INCREMENT PRIMARY KEY,
                      first_name VARCHAR(100),
                      last_name VARCHAR(100),
                      email VARCHAR(150) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      id_role INT,
                      FOREIGN KEY (id_role) REFERENCES role(id_role)
);

CREATE TABLE internship_offer (
                                  id_offer INT AUTO_INCREMENT PRIMARY KEY,
                                  title VARCHAR(150) NOT NULL,
                                  description TEXT,
                                  publication_date DATE,
                                  company_id INT,
                                  FOREIGN KEY (company_id) REFERENCES user(id_user)
);

CREATE TABLE application (
                             id_application INT AUTO_INCREMENT PRIMARY KEY,
                             application_date DATE,
                             status VARCHAR(50) DEFAULT 'PENDING',
                             student_id INT,
                             offer_id INT,
                             FOREIGN KEY (student_id) REFERENCES user(id_user),
                             FOREIGN KEY (offer_id) REFERENCES internship_offer(id_offer)
);

CREATE TABLE notification (
                              id_notification INT AUTO_INCREMENT PRIMARY KEY,
                              message TEXT,
                              notification_date DATE,
                              user_id INT,
                              FOREIGN KEY (user_id) REFERENCES user(id_user)
);