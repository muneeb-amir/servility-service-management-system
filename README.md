# 🚪 Servility
### *At Your Doorstep*

Servility is a JavaFX-based Service Management System that connects **Service Seekers** with **Service Providers** through a centralized platform. The application allows users to discover services, post service requests, manage applications, track service progress, and provide feedback after completion.

The goal of Servility is to make professional services easily accessible while providing providers with a platform to showcase their expertise and connect with potential customers.

---

## 🌟 Features

### 🔐 Authentication & User Management
- User Registration and Login
- Separate accounts for:
  - Service Seekers
  - Service Providers
- Profile Management

### 👨‍🔧 Service Provider Features
- Create and publish service listings
- Specify:
  - Service Name
  - Expertise
  - Description
  - Price
- View open requests
- Manage applications
- Track earnings
- Update service status

### 🙋 Service Seeker Features
- Search available services
- Browse providers by expertise
- Post service requests
- Set custom budgets
- Track request progress
- Hire suitable providers

### 📋 Request Management
- Create service requests
- Accept or reject applications
- Service status tracking:
  - Pending
  - Accepted
  - Completed

### ⭐ Ratings & Feedback
- Submit reviews after service completion
- Improve transparency and service quality

### 💰 Balance Management
- Service Seekers maintain account balances
- Service Providers earn through completed services

---

## 🛠️ Technology Stack

| Technology | Purpose |
|------------|----------|
| Java | Core Application Logic |
| JavaFX | User Interface |
| FXML | UI Layout Design |
| CSS | Styling |
| JDBC | Database Connectivity |
| Microsoft SQL Server | Database Management |
| Scene Builder | JavaFX UI Development |

---

## 🏗️ System Workflow

### Service Seeker

```text
Register/Login
      ↓
Search Services
      ↓
Post Service Request
      ↓
Select Provider
      ↓
Service Completion
      ↓
Rating & Feedback
```

### Service Provider

```text
Register/Login
      ↓
Create Service Listing
      ↓
Receive Applications
      ↓
Accept Request
      ↓
Complete Service
      ↓
Receive Earnings
```

---

## 📁 Project Structure

```text
Servility
│
├── App.java
├── Application.java
├── ServiceManagementSystem.java
│
├── Controllers
│   ├── LoginController.java
│   ├── SignupController.java
│   ├── SearchProvidersController.java
│   ├── SearchServicesController.java
│   ├── UserDashboardSeekerController.java
│   ├── UserDashboardProviderController.java
│   ├── PostNewServiceRequestController.java
│   ├── ManageApplicationsProviderController.java
│   ├── ViewOpenRequestsProviderController.java
│   ├── RatingsAndFeedbackPageController.java
│   └── ...
│
├── FXML Files
│   ├── login.fxml
│   ├── signup.fxml
│   ├── Search_Services.fxml
│   ├── ManageProfile.fxml
│   ├── UserDashboardProvider.fxml
│   ├── user_dashboard_seeker.fxml
│   └── ...
│
├── Resources
│   ├── servility.png
│   └── construction_site.jpg
│
├── app.css
│
└── Database
    └── ServiceManagementSystem.sql
```

---

## 🗄️ Database Design

### ServiceSeekers

| Column | Type |
|----------|----------|
| seeker_id | INT |
| name | VARCHAR |
| email | VARCHAR |
| password | VARCHAR |
| balance | DOUBLE |

### ServiceProviders

| Column | Type |
|----------|----------|
| provider_id | INT |
| name | VARCHAR |
| email | VARCHAR |
| password | VARCHAR |
| expertise | VARCHAR |
| balance | DOUBLE |

### Services

| Column | Type |
|----------|----------|
| service_id | INT |
| provider_id | INT |
| sname | VARCHAR |
| expertise | VARCHAR |
| description | TEXT |
| price | DOUBLE |
| status | VARCHAR |

### ServiceRequests

| Column | Type |
|----------|----------|
| request_id | INT |
| seeker_id | INT |
| expertise | VARCHAR |
| description | TEXT |
| budget | DOUBLE |
| providername | VARCHAR |
| status | VARCHAR |

### Service Status Values

```text
pending
accepted
completed
```

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17+ (or compatible version)
- JavaFX SDK
- Microsoft SQL Server
- SQL Server Management Studio (SSMS)
- IntelliJ IDEA / Eclipse / NetBeans

---

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/servility-service-management-system.git
```

---

### 2️⃣ Create the Database

```sql
CREATE DATABASE ServiceManagementSystem;
```

Execute the provided SQL schema to create all required tables.

---

### 3️⃣ Configure Database Connection

Update the JDBC configuration in your project:

```java
String url = "jdbc:sqlserver://localhost:1433;databaseName=ServiceManagementSystem";
String username = "your_username";
String password = "your_password";
```

---

### 4️⃣ Configure JavaFX

Add JavaFX SDK to your IDE.

VM Options:

```bash
--module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml
```

---

### 5️⃣ Run the Application

Run:

```text
App.java
```

or

```text
Application.java
```

depending on your project setup.

---

## 📸 Application Modules

- Login System
- Signup System
- Service Search
- Provider Search
- Service Posting
- Request Management
- User Dashboards
- Earnings Management
- Ratings & Feedback
- Profile Management

---

## 🎯 Future Improvements

- Online Payment Integration
- Real-Time Notifications
- In-App Messaging
- GPS-Based Service Discovery
- Recommendation System
- Admin Dashboard
- Mobile Application Version
- Service Analytics & Reporting

---

## 💡 Motivation

Finding trustworthy service providers can often be time-consuming and inconvenient. Servility aims to simplify this process by providing a platform where users can easily discover services and connect with skilled professionals, bringing quality services directly **"At Your Doorstep."**

---

