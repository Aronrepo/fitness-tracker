# Fitness Tracker Application

The Fitness Tracker Application is a web-based platform that allows users to track their daily activities and food consumption. Users can log in, record their activities, and keep track of the calories burned and consumed. This README provides an overview of the application's features, how to set it up, and its usage.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- User Registration: Users can create accounts by providing their basic information.
- User Authentication: Secure user authentication with JWT tokens.
- Activity Tracking: Users can log various physical activities with associated calories burned.
- Food Consumption Tracking: Users can record meals with the number of calories consumed.
- Analytics: Provides daily calorie consumption and activity analytics for users.
- User Profile: Users can view and update their profile information.

## Getting Started

### Prerequisites

- Docker

### Installation

1. Pull the image from Dockerhub in a new directory by using a bash terminal:

   ```sh
   docker pull arondocker100/fitnesstracker
   

2. In the root directory of the repository you will find a compose file called docker-compose-app.yml. Copy this compose file into the new folder
    

3. Run the compose file in bash terminal

    ```sh
    docker-compose -f docker-compose-app.yml up


### Usage
1. Register or log in to the application.
2. Update your user profile with additional information.
3. Log your activities by providing activity type, calories burned, and date/time.
4. Log your meals by providing food type, calories consumed, and date/time.
5. View your daily analytics to track calorie consumption and activity.

### Contributing
Contributions are welcome! If you find any issues or want to enhance the application, feel free to submit pull requests.

1. Fork the repository.
2. Create a new branch for your feature/fix: git checkout -b feature-name
3. Commit your changes: git commit -m 'Add feature'
4. Push to the branch: git push origin feature-name
5. Create a pull request.

### License

This project is licensed under the [MIT License](LICENSE).