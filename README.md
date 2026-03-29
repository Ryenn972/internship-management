# 🎓 Internship Management System — Java JEE

A full-stack Java JEE web application for managing internship applications in a school context.
Students can browse offers and apply, companies can manage their listings, pedagogical managers review and validate applications, and administrators oversee all users.

---

## ✨ Features

| Role | Capabilities |
|------|-------------|
| **Student** | Register, browse offers, apply (once per offer), track application status, receive notifications |
| **Company** | Register, create / edit / delete their own internship offers |
| **Manager** | View all applications, validate or reject them (triggers automatic notifications) |
| **Admin** | Create, delete and assign roles to any user |

All users share: login / logout, role-based dashboard, session management.

---

## 🏗️ Architecture

The application follows a strict **4-layer architecture**:

```
Presentation  →  JSP + CSS
Controller    →  Servlets (one per feature)
Service       →  Business logic
DAO           →  SQL via JDBC
Database      →  MariaDB / MySQL
```

An `AuthFilter` intercepts every request to enforce authentication and role-based access control before any servlet is reached.

---

## 📁 Project Structure

```
internship-management/
│
├── database/
│   ├── schema.sql          # Table definitions
│   └── data.sql            # Seed data (roles + default admin)
│
├── diagrams/
│   ├── cas_util.drawio     # Use case diagram (draw.io — main reference)
│   ├── activity.uml        # Activity diagram (PlantUML)
│   ├── sequence.uml        # Sequence diagram (PlantUML)
│   └── use_case.uml        # Use case diagram (PlantUML — secondary)
│
└── src/main/
    ├── java/
    │   ├── model/          # Plain Java objects (User, InternshipOffer, Application, Notification)
    │   ├── dao/            # Database access (JDBC PreparedStatements)
    │   ├── service/        # Business rules and orchestration
    │   ├── controller/     # HttpServlets (one per resource)
    │   ├── filter/         # AuthFilter — security layer
    │   └── util/           # DatabaseConnection, PasswordUtil (SHA-256)
    │
    └── webapp/
        ├── css/style.css
        ├── jsp/            # Views (login, register, dashboard, offers, applications…)
        └── WEB-INF/web.xml # Servlet and filter mappings
```

---

## 🗄️ Database

**Tables:** `role`, `user`, `internship_offer`, `application`, `notification`

**Key relationships:**
- A `user` has one `role` (student / company / manager / admin)
- A `user` (company) creates many `internship_offer`
- A `user` (student) submits many `application`
- An `application` links one student to one offer
- A `user` receives many `notification`

Passwords are stored as **SHA-256 hashes** — never in plain text.

---

## ⚙️ Setup

### Requirements
- Java 11+
- Apache Tomcat 9+
- MariaDB or MySQL
- Maven (optional but recommended)

### 1. Database

```bash
mysql -u root -p
source database/schema.sql
source database/data.sql
```

### 2. Configuration

Edit `src/main/java/util/DatabaseConnection.java` with your credentials:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/internship_management?useSSL=false&serverTimezone=UTC";
private static final String USER     = "root";
private static final String PASSWORD = "your_password";
```

### 3. Build & Deploy

Build a `.war` file and drop it into Tomcat's `webapps/` folder, or deploy directly from your IDE (IntelliJ / Eclipse).

### 4. Default admin account

| Email | Password |
|-------|----------|
| `admin@school.fr` | `admin` |

> ⚠️ Change this password immediately in production.

---

## 🔐 Security

- All routes are protected by `AuthFilter` — unauthenticated requests are redirected to `/login`
- Role-based access: `/admin` → admin only, `/manager` → manager only, offer mutations → company only
- Passwords hashed with SHA-256 via `PasswordUtil`
- Duplicate application prevention (one student per offer)
- Company users can only edit or delete their own offers

---

## 🧪 Technologies

- **Backend:** Java JEE, Servlets, JDBC
- **Frontend:** JSP, HTML5, CSS3
- **Database:** MariaDB / MySQL
- **Modeling:** PlantUML, draw.io
- **Version control:** Git

---

## 📊 UML Diagrams

Three diagrams are included in the `/diagrams` folder:

- **Use Case Diagram** — actors, main features, `<<include>>` and `<<extend>>` relations
- **Activity Diagram** — full internship application workflow with swimlanes (Student / System / Manager)
- **Sequence Diagram** — detailed interaction flow: student login → offer consultation → application submission → manager decision → notification

---

## 👤 Author

[Ryenn](https://github.com/Ryenn972) - Student project — Internship Management System