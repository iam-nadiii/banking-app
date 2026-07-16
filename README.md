# 💳 Bank Ledger Revision

> **Skills Enhancement Project | LTCA Bootcamp | Spring Boot Banking Application**

## 📖 Overview

The **Bank Ledger Revision** project was developed as part of the LTCA Bootcamp Skills Enhancement Project. Rather than creating an application from scratch, our team was assigned an existing Capstone Bank Ledger application and challenged to improve it using modern software engineering practices.

Our objective was to identify issues within the original application, implement new features, improve the architecture, migrate data to a relational database, and enhance the overall maintainability of the project while collaborating as a development team using GitHub.

This project provided valuable experience working in a collaborative Agile environment similar to what software engineers experience in industry.

---

# 🎯 Project Objectives

- Enhance an existing banking application
- Improve maintainability and scalability
- Fix bugs discovered in the original project
- Introduce new backend functionality
- Integrate a MySQL relational database
- Practice Git collaboration and version control
- Simulate an enterprise software development workflow

---

# 🔄 Revisions Made

During the enhancement phase, our team implemented several significant improvements to the original project.

## ✅ Database Migration

One of the largest revisions was migrating the application's data management to **MySQL**.

This included:

- Designing a relational database
- Creating SQL schema files
- Adding foreign key relationships
- Creating user, profile, transaction, and search tables
- Replacing temporary data storage with persistent database storage

---

## ✅ User & Profile Management

We introduced user and profile functionality to make the application more realistic.

Features added include:

- User profiles
- Profile services
- Profile repository
- User validation
- Profile retrieval
- Profile updates

---

## ✅ Service Layer Improvements

The application was reorganized using Spring Boot best practices.

Business logic was separated into dedicated Service classes, improving:

- Maintainability
- Readability
- Scalability
- Code organization

---

## ✅ Repository Layer

Repository classes were implemented to communicate directly with the MySQL database.

This improved:

- Database abstraction
- Cleaner code
- Easier testing
- Separation of concerns

---

## ✅ Transaction Management

The transaction system was improved by:

- Updating the Transaction Controller
- Improving transaction services
- Organizing transaction logic
- Better transaction history management

---

## ✅ Search History

Search functionality was expanded to include:

- Search history
- Persistent search records
- Search repository
- Database storage for searches

---

## ✅ Exception Handling

Custom exceptions were implemented to improve application stability.

Examples include:

- Invalid input exceptions
- Database operation exceptions
- Resource not found exceptions

This allows the application to provide meaningful error messages instead of crashing unexpectedly.

---

## ✅ Git Collaboration

This project emphasized collaborative software engineering using GitHub.

Our workflow included:

- Feature branches
- Pull requests
- Code reviews
- Commit history
- GitHub Projects
- Team collaboration

Each feature was developed independently before being merged into the main branch.

---

# ✨ Features

### User Features

- User Profiles
- Profile Management
- Authentication
- Input Validation

### Banking Features

- Deposit Funds
- Withdraw Funds
- Transaction History
- Search Transactions
- Search History

### Database

- MySQL
- SQL Schema
- Foreign Keys
- Persistent Storage

### Backend

- Spring Boot
- REST APIs
- MVC Architecture
- Repository Pattern
- Service Layer
- Dependency Injection
- Exception Handling

---

# 🛠 Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Git
- GitHub
- REST APIs
- SQL
- CSV

---

# 📂 Project Structure

```text
banking-app
│
├── database
├── src
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── exception
│   └── config
│
├── finance_db_current.sql
├── searches.csv
├── transactions.csv
├── README.md
└── pom.xml
```

---

# 👥 Team Members

- Naod Asmelash
- Ezra Teshale
- Uyen Nguyen
- **Richard Tougeekay**
- Kevin Nguyen
- Chris Torres

---

# 🤝 Team Collaboration

A GitHub Project board was created to organize development tasks, assign responsibilities, and monitor project progress throughout development.

The team collaborated using:

- GitHub Projects
- Git Feature Branches
- Pull Requests
- Code Reviews
- Commit History
- Merge Requests

The frontend required very few modifications since the project focused primarily on backend improvements and database integration.

---

# 💼 Skills Demonstrated

This project demonstrates experience with:

### Programming

- Java
- Object-Oriented Programming

### Backend Development

- Spring Boot
- REST APIs
- MVC Architecture
- Repository Pattern
- Service Layer

### Database

- MySQL
- SQL
- Database Design

### Software Engineering

- Git
- GitHub
- Agile Development
- Team Collaboration
- Version Control
- Debugging
- Code Refactoring

---

# 🚀 Future Improvements

Potential future enhancements include:

- JWT Authentication
- Money Transfers
- Loan Services
- Savings Accounts
- Credit Card Accounts
- Email Notifications
- Swagger Documentation
- Docker Support
- Azure Deployment
- AWS Deployment
- Unit Testing

---

# 📸 Screenshots

### Home Screen

*(Insert application screenshot here)*
<img width="883" height="787" alt="image" src="https://github.com/user-attachments/assets/22a123d7-e6f2-4c91-8d77-d34a4292ba7d" />


### Database Schema

*(Insert MySQL screenshots here)*


### API Testing

*(Insert Postman screenshots here)*

---

# 📌 Project Status

**Status:** ✅ Completed

The Bank Ledger Revision project successfully modernized an existing Java banking application into a scalable Spring Boot application backed by MySQL. Through collaborative development, our team gained practical experience with enterprise software architecture, Git workflows, database integration, backend development, and Agile software engineering practices.

---

# 🙏 Acknowledgements

Special thanks to the LTCA Bootcamp instructors and every team member whose collaboration, code reviews, and contributions helped make this project successful. Working together provided valuable experience that mirrors real-world software engineering environments.






Revision

Description

Database Schema

Migrated application data to MySQL and created relational tables

User & Security

Added user profiles, validation, and backend security features

Transaction Controller

Refactored transaction endpoints and business logic

Repository & Service Layer

Implemented Spring Boot layered architecture

CSV Migration

Imported legacy transaction and search data

Documentation

Updated project documentation and setup instructions

