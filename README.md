# Fitness Tracker Application

The Fitness Tracker Application is a web-based platform that allows users to track their daily activities and food consumption. Users can log in, record their activities, and keep track of the calories burned and consumed. This README provides an overview of the application's features, how to set it up, and its usage.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Features

- User Registration: Users can create accounts by providing their basic information.
- User Authentication: Secure user authentication with JWT tokens.
- Activity Tracking: Users can log various physical activities with associated calories burned.
- Food Consumption Tracking: Users can record meals with the number of calories consumed.
- Analytics: Provides daily calorie consumption and activity analytics for users.
- User Profile: Users can view and update their profile information.

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- Spring Boot
- Database (e.g., MySQL, PostgreSQL)
- Node.js and npm (for the frontend)

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/your-username/fitness-tracker.git
   cd fitness-tracker

2. Configure the Backend:
- Set up your database configuration in application.properties.
- Run the Spring Boot application using your IDE or Gradle/Maven.

3. Configure the Frontend:
- Navigate to the frontend directory: cd frontend
- Install dependencies: npm install
- Start the frontend: npm start

### Configuration
- Database Configuration: Open src/main/resources/application.properties and update the database settings.

- JWT Configuration: Update JWT-related settings in the configuration files.

### Usage
1. Register or log in to the application.
2. Log your activities by providing activity type, calories burned, and date/time.
3. Log your meals by providing food type, calories consumed, and date/time.
4. View your daily analytics to track calorie consumption and activity.
5. Update your user profile with additional information.

### Contributing
Contributions are welcome! If you find any issues or want to enhance the application, feel free to submit pull requests.

1. Fork the repository.
2. Create a new branch for your feature/fix: git checkout -b feature-name
3. Commit your changes: git commit -m 'Add feature'
4. Push to the branch: git push origin feature-name
5. Create a pull request.