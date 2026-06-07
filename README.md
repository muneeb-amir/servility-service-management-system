Servility 🚪
"At Your Doorstep"

Servility is a desktop-based Service Management System developed using Java, JavaFX, and Microsoft SQL Server. The platform bridges the gap between service providers and service seekers by enabling users to discover, request, offer, and manage services conveniently from a single application.

Whether someone needs a plumber, electrician, carpenter, cleaner, or any other professional service, Servility brings the service right to their doorstep.

📌 Project Overview

Servility provides a marketplace-like environment where:

Service Providers can register, create service listings, manage applications, and earn income.
Service Seekers can search for services, post service requests, set budgets, and hire suitable providers.
Both parties can track service progress and maintain transparency throughout the service lifecycle.
✨ Key Features
👤 User Authentication
User Registration
Secure Login System
Separate dashboards for:
Service Seekers
Service Providers
🔎 Service Discovery
Search available services by expertise/category
View provider details
Compare services and prices
🛠 Service Provider Features
Create service offerings
Specify:
Service Name
Expertise
Description
Price
Manage incoming applications
Track service status
View earnings
🙋 Service Seeker Features
Browse available services
Post custom service requests
Set preferred budget
Hire providers based on expertise
Track request status
📋 Service Request Management
Post requests for required services
Accept or reject requests
Service lifecycle tracking:
Pending
Accepted
Completed
⭐ Ratings & Feedback
Provide reviews after service completion
Improve trust and service quality
💰 Wallet/Balance Management
Service Seekers maintain balances
Service Providers receive earnings
Transaction-based workflow support
🏗 System Architecture
Service Seeker Workflow
Register/Login
       ↓
Browse Services
       ↓
Post Service Request
       ↓
Select Provider
       ↓
Service Completion
       ↓
Rating & Feedback
Service Provider Workflow
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
🖥 Technology Stack
Technology	Purpose
Java	Core Application Logic
JavaFX	User Interface
FXML	UI Layout Design
CSS	Styling
JDBC	Database Connectivity
Microsoft SQL Server	Database Management
Scene Builder	JavaFX UI Design
📂 Project Structure
Servility/
│
├── App.java
├── Application.java
│
├── Controllers
│   ├── LoginController.java
│   ├── SignupController.java
│   ├── SearchServicesController.java
│   ├── SearchProvidersController.java
│   ├── UserDashboardSeekerController.java
│   ├── UserDashboardProviderController.java
│   ├── PostNewServiceRequestController.java
│   ├── ManageApplicationsProviderController.java
│   ├── ViewOpenRequestsProviderController.java
│   ├── RatingsAndFeedbackPageController.java
│   └── ...
│
├── FXML
│   ├── login.fxml
│   ├── signup.fxml
│   ├── Search_Services.fxml
│   ├── ManageProfile.fxml
│   ├── UserDashboardProvider.fxml
│   ├── user_dashboard_seeker.fxml
│   └── ...
│
├── CSS
│   └── app.css
│
├── Database
│   └── ServiceManagementSystem.sql
│
└── Resources
    ├── servility.png
    └── construction_site.jpg
🗄 Database Schema

The project uses Microsoft SQL Server with the following core tables:

ServiceSeekers
Field	Type
seeker_id	INT
name	VARCHAR
email	VARCHAR
password	VARCHAR
balance	DOUBLE
ServiceProviders
Field	Type
provider_id	INT
name	VARCHAR
email	VARCHAR
password	VARCHAR
expertise	VARCHAR
balance	DOUBLE
Services
Field	Type
service_id	INT
provider_id	INT
sname	VARCHAR
expertise	VARCHAR
description	TEXT
price	DOUBLE
status	VARCHAR

Status values:

pending
accepted
completed
ServiceRequests
Field	Type
request_id	INT
seeker_id	INT
expertise	VARCHAR
description	TEXT
budget	DOUBLE
providername	VARCHAR
status	VARCHAR

Status values:

pending
accepted
completed
🚀 Installation & Setup
Prerequisites
JDK 17 or later
JavaFX SDK
Microsoft SQL Server
SQL Server Management Studio (SSMS)
IDE (IntelliJ IDEA / Eclipse / NetBeans)
1. Clone Repository
git clone https://github.com/yourusername/servility.git
2. Create Database

Open SQL Server and execute:

CREATE DATABASE ServiceManagementSystem;

Then run the provided SQL schema file.

3. Configure Database Connection

Update database credentials in your JDBC connection file:

String url = "jdbc:sqlserver://localhost:1433;databaseName=ServiceManagementSystem";
String username = "your_username";
String password = "your_password";
4. Add JavaFX Libraries

Configure JavaFX SDK in your IDE.

VM Options:

--module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml
5. Run Application

Execute:

App.java

or

Application.java

depending on your project configuration.

📸 Screens Included
Login Page
Signup Page
Provider Dashboard
Seeker Dashboard
Service Search
Service Posting
Open Requests View
Earnings Management
Ratings & Feedback
🎯 Future Enhancements
Online payment integration
Real-time notifications
Chat between seekers and providers
Service recommendations
GPS-based provider matching
Mobile application support
Admin panel
Service history analytics
