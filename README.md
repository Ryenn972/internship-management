# Internship Management Web Application (Java JEE)

## Project Description

This project is a Java JEE web application developed to manage internship applications for a school.
The application allows students to apply for internship offers, companies to publish offers, managers to validate applications, and administrators to manage users.

The project includes UML modeling, database design, Java JEE development, and a final presentation.

---

## Features

The application supports the following features:

* User authentication
* Role management (student, company, manager, admin)
* Internship offer management (CRUD)
* Internship offer consultation
* Internship application submission
* Application validation or rejection
* Notifications to users
* User management (admin)

---

## Project Structure

```
internship-management/
│
├── database/
│   ├── schema.sql
│   ├── data.sql
│
├── diagrams/
│   ├── usecase.puml
│   ├── activity.puml
│   ├── sequence.puml
│   ├── class.puml
│
├── src/
│   ├── model/
│   ├── dao/
│   ├── service/
│   ├── controller/
│
├── webapp/
│   ├── jsp/
│
└── README.md
```

---

## Database Setup (MariaDB)

### 1. Open MariaDB

```
mysql -u root -p
```

### 2. Create database and tables

```
source database/schema.sql
```

### 3. Insert initial data (roles)

```
source database/data.sql
```

---

## Database Structure

The database contains the following tables:

* role
* user
* internship_offer
* application
* notification

### Main Relationships

* A role can have many users
* A company (user) can create many internship offers
* A student (user) can submit many applications
* An internship offer can have many applications
* A user can receive many notifications

---

## Application Architecture

The application follows a layered architecture:

1. Presentation Layer (JSP / HTML / CSS)
2. Controller Layer (Servlets)
3. Business Layer (Services)
4. Data Access Layer (DAO)
5. Database (MariaDB)

---

## UML Diagrams

The project includes the following UML diagrams:

* Use Case Diagram
* Activity Diagram
* Sequence Diagram
* Class Diagram
* Database Schema

---

## Technologies Used

* Java JEE
* Servlets
* JSP
* MariaDB / MySQL
* JDBC
* UML (PlantUML)
* Git / GitHub

---

## Author

Student project – Internship Management System
