# 🚗 Smart Car Showroom System

An Android-based car showroom management application designed to provide customers with a convenient way to explore cars and help showroom administrators manage vehicles, enquiries, and test drives digitally.

## 📱 Project Overview

**Smart Car Showroom System** is an Android application developed using **Java, XML, Android Studio, and SQLite**.

The application provides two main modules:

- 👤 **Customer Module**
- 🛠️ **Admin Module**

Customers can browse available cars, view detailed specifications, add cars to favorites, compare vehicles, calculate EMI, submit enquiries, book test drives, and receive notifications.

Administrators can manage cars, view showroom statistics, and handle customer enquiries and test-drive requests through the admin dashboard.

---

## ✨ Features

### 👤 Customer Module

- 🔐 Customer Registration & Login
- 🚗 Browse Cars
- 🔎 Search and Filter Cars
- 📋 View Detailed Car Information
- ❤️ Add Cars to Favorites
- ⚖️ Compare Cars
- 💰 EMI Calculator
- 📩 Submit Car Enquiries
- 📅 Book Test Drives
- 🔔 View Notifications
- 👤 Manage Profile
- 🏢 View Showroom Information
- 📍 View Showroom Location

### 🛠️ Admin Module

- 🔐 Admin Login
- 📊 Admin Dashboard
- 🚗 Add New Cars
- ✏️ Edit Car Information
- 🗑️ Delete Cars
- 📋 View Customer Enquiries
- 📩 Respond to Enquiries
- 📅 View Test Drive Bookings
- ✅ Confirm Test Drives
- ❌ Reject Test Drives
- 🔔 Manage Notifications
- 📈 View Total Cars, Available Cars, Enquiries and Test Drives

---

## 🏗️ System Architecture

The application follows a simple Android-based architecture:

```text
┌──────────────────────────┐
│       XML UI Layer       │
│   Activities / Layouts   │
└─────────────┬────────────┘
              │
              ▼
┌──────────────────────────┐
│      Java Logic Layer    │
│       Activities         │
└─────────────┬────────────┘
              │
              ▼
┌──────────────────────────┐
│      DatabaseHelper      │
│    Database Operations   │
└─────────────┬────────────┘
              │
              ▼
┌──────────────────────────┐
│       SQLite Database    │
│ Users | Cars | Enquiries │
│ Favorites | TestDrives   │
│ Notifications            │
└──────────────────────────┘

🗄️ Database
The application uses SQLite for local data storage.
Main Tables
Table	Purpose
Users	Stores customer and admin account information
Cars	Stores vehicle details
Favorites	Stores customer's favourite cars
Enquiries	Stores customer enquiries
TestDrives	Stores test-drive bookings
Notifications	Stores in-app notifications


🛠️ Technologies Used
Technology	Purpose
Java	Application logic
XML	User interface design
Android Studio	Application development
SQLite	Local database
Android SDK	Android application development
Git	Version control
GitHub	Source code hosting


👥 User Roles

Customer
Customers can:
1. Register and log in
2. Browse available cars
3. Search and filter vehicles
4. View car specifications
5. Add cars to favorites
6. Compare cars
7. Calculate EMI
8. Submit enquiries
9. Book test drives
10. View notifications
11. Manage their profile

Admin
Administrators can:
1. Log in through the admin panel
2. View dashboard statistics
3. Add, edit and delete cars
4. Manage customer enquiries
5. Respond to enquiries
6. Manage test-drive bookings
7. Confirm or reject test drives
8. Manage notifications

🔄 Application Workflow
                    START
                      │
                      ▼
              ┌───────────────┐
              │ Login / Signup│
              └───────┬───────┘
                      │
             ┌────────┴────────┐
             ▼                 ▼
        ┌──────────┐      ┌─────────┐
        │ Customer │      │  Admin  │
        └────┬─────┘      └────┬────┘
             │                 │
             ▼                 ▼
       ┌───────────┐      ┌────────────┐
       │   Home    │      │ Dashboard  │
       └─────┬─────┘      └──────┬─────┘
             │                   │
       ┌─────┼──────┐      ┌─────┼──────────┐
       ▼     ▼      ▼      ▼     ▼          ▼
      Cars  EMI  Favorites Cars Enquiries Test Drives
       │           │            │          │
       ▼           ▼            ▼          ▼
   Enquiries    Compare      Respond     Confirm/
       │                      to          Reject
       ▼
   Test Drive
       │
       ▼
 Notifications
       │
       ▼
      END

📂 Project Structure
Smart-Car-Showroom/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.example.smartcarshowroom/
│           │       ├── activities/
│           │       ├── adapters/
│           │       ├── database/
│           │       ├── models/
│           │       └── utils/
│           │
│           ├── res/
│           │   ├── drawable/
│           │   ├── layout/
│           │   ├── mipmap/
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts

🔐 Login & Data Management
The application provides separate access for Customer and Admin users.
- Customer and admin roles are handled separately.
- Login credentials are verified using the SQLite database.
- Logged-in user information is maintained using Android SharedPreferences.
- Database operations are handled through DatabaseHelper.
- Application data is stored locally using SQLite.

🧪 Testing
The application was tested for major functionalities including:
- User registration
- Customer login
- Admin login
- Car browsing
- Search and filtering
- Favorites
- Car comparison
- EMI calculation
- Enquiries
- Test-drive booking
- Notifications
- Profile management
- Admin car management
- Admin enquiry management
- Admin test-drive management

🎯 Project Objectives
- Digitize basic car showroom operations.
- Provide customers with an easy way to explore vehicles.
- Allow customers to compare cars and calculate EMI.
- Simplify enquiry and test-drive booking.
- Provide administrators with centralized showroom management.
- Store application data using a lightweight local database.
- Provide a simple and user-friendly Android interface.

🚀 Future Scope
The application can be further enhanced with:
- ☁️ Cloud-based database
- 🌐 Online car purchasing
- 💳 Online payment integration
- 🔔 Firebase push notifications
- 🤖 AI-based car recommendations
- 🏢 Multiple showroom support
- 📦 Real-time inventory management
- 🏦 Loan and insurance integration
- 📊 Advanced admin analytics

📸 Screenshots
Screenshots of the application can be added here to demonstrate the major features and user interfaces.
Login
<img width="273" height="610" alt="image" src="https://github.com/user-attachments/assets/c976c46e-21fe-449b-8433-6c186a317dc5" />

Customer Home
<img width="278" height="619" alt="image" src="https://github.com/user-attachments/assets/3fa7987e-0002-4460-92c2-76e731c0bc30" />

Car Listing
<img width="287" height="641" alt="image" src="https://github.com/user-attachments/assets/66a9b93f-14e1-4ce1-89ad-1e6f013d5074" />

Car Details
<img width="284" height="633" alt="image" src="https://github.com/user-attachments/assets/4e0c0b90-c0f8-4f90-9f6b-6084a8b4f223" />

Favorites & Compare
<img width="293" height="654" alt="image" src="https://github.com/user-attachments/assets/60a9b5d5-7e4e-40a2-927c-305455c97c86" />
<img width="384" height="640" alt="Screenshot 2026-10-08 130436" src="https://github.com/user-attachments/assets/7af8e04f-8497-414b-87dd-f5e00fce5567" />

EMI Calculator
<img width="283" height="631" alt="image" src="https://github.com/user-attachments/assets/65b55f3b-3226-4c3e-ab63-405459478823" />

Enquiry & Test Drive
<img width="289" height="646" alt="image" src="https://github.com/user-attachments/assets/acaf1917-d76d-4183-9add-2fceb4ec2fb9" />
<img width="289" height="645" alt="image" src="https://github.com/user-attachments/assets/f0cacf53-29f8-4084-afb7-294cce55cb0c" />

Admin Dashboard
<img width="289" height="644" alt="image" src="https://github.com/user-attachments/assets/7fcc15d6-4f4b-4ad3-93ca-60b89abeca22" />

Car Management
<img width="289" height="645" alt="image" src="https://github.com/user-attachments/assets/866892fc-a878-4ca9-8054-10a600037152" />

Notifications
<img width="293" height="654" alt="image" src="https://github.com/user-attachments/assets/558c6b84-e65d-4004-b13a-d6de4a78dabf" />


🎓 Academic Project
Project Name: Smart Car Showroom System
Project Type: Academic Project / Micro-Project
Domain: Mobile Application Development
Platform: Android
Database: SQLite
Course: Mobile Application Development
Institution: Government Polytechnic, Pune

👨‍💻 Developer
Bhavesh Chaudhari
Diploma in Computer Engineering
Government Polytechnic, Pune
- GitHub: @bhaveshc21
- Email: bhavesh.bmc21@gmail.com
