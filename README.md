# Smart City Traveler

Smart City Traveler is a JavaFX desktop application for exploring and booking city services such as hotels, parks, libraries, destinations, and restaurants. The application provides separate user and admin functionality, allowing normal users to search places, view packages, book services, and complete payments, while admins can manage places, packages, and users.

## Features

* User signup and login
* Role-based dashboard for users and admins
* Search places by name or type
* Manage city places such as hotels, parks, libraries, destinations, and restaurants
* Add, update, and delete places as an admin
* Manage packages for different places
* Book packages with date selection
* Prevent duplicate bookings for the same package and date
* Card/payment form validation
* Store booking and payment records in PostgreSQL
* Manage user profile and personal details

## Tech Stack

* Java
* JavaFX
* FXML
* Maven
* PostgreSQL
* JDBC
* CSS

## Project Structure

```text
src/main/java/com/travel/smartcity
├── controller   # JavaFX controllers for UI screens
├── dao          # Database access classes
├── model        # Application data models
├── service      # Business logic layer
└── util         # Routing and session utilities
```

## Main Modules

* **Authentication Module**: Handles user signup, login, session management, and admin role detection.
* **Place Management Module**: Allows admins to add, update, delete, and search places.
* **Package Management Module**: Allows admins to create packages for places and users to select packages for booking.
* **Booking Module**: Allows users to book a selected package for a valid date.
* **Payment Module**: Records payment details after successful booking.
* **User Management Module**: Provides admin-side user management and user profile functionality.

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/hafizmaazsheq/smart-city.git
```

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Make sure Java 21, Maven, and PostgreSQL are installed.

4. Create a PostgreSQL database named:

```text
smartcitydb
```

5. Configure the PostgreSQL database connection in the DAO classes according to your local database username and password.

6. Run the application using Maven:

```bash
mvn clean javafx:run
```

## Database Tables Used

The application uses PostgreSQL tables for users, places, packages, bookings, payments, payees, and user personal details.

## About

This project demonstrates a desktop-based smart city travel management system built with JavaFX and PostgreSQL. It focuses on user authentication, admin management, place searching, package booking, and payment processing.
