# Fitness Tracker Application

The Fitness Tracker Application is a web-based platform that allows users to track their daily activities and food consumption. Users can log in, record their activities, and keep track of the calories burned and consumed. This README provides an overview of the application's features, how to set it up, and its usage.

![home page][home page]

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
- 3rd Party Calorie Database  
![spoonacular][spoonacular]


## Technologies

[![Java][Java]][Java-url]
[![Spring][Spring]][Spring-url]
[![Postgres][Postgres]][Postgres-url]
[![Javascript][Javascript]][Javascript-url]
[![React][React.js]][React-url]
[![React Router][React Router]][React Router-url]
[![MUI][MUI]][MUI-url]
[![Bootstrap][Bootstrap]][Bootstrap-url]

## Getting Started

### Prerequisites

✅ Docker Desktop installed on your system. [![Docker][Docker]][Docker-url]

✅ Git installed on your system. [![Git][Git]][Git-url]

### Installation

1. Pull the image from Dockerhub by using a bash terminal:

   ```sh
   docker pull arondocker100/fitnesstracker


   ```

2. In the root directory of the repository you will find a compose file called docker-compose-app.yml. Copy this compose file into a new folder

3. Copy the .env file to the new directory as well

4. Register Spoonacular to get your own api key: https://spoonacular.com/food-api/console#Dashboard

5. Update the env file with your api key (replace xxx)

![env][apikey]

6. Update SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD and SPRING_DATABASE with an arbitrary name (replace xxx)

![password][password]

7. Run the compose file in bash terminal

   ```sh
   docker-compose -f docker-compose-app.yml up
   ```

### Usage

1. Load web application with http://localhost:8080/
2. Register or log in to the application.
3. Update your user profile with additional information.
4. Log your activities by providing activity type, calories burned, and date/time.
5. Log your meals by providing food type, calories consumed, and date/time.
6. View your daily analytics to track calorie consumption and activity.

### Tests

Services has 58% code coverage.  
Classes which have been tested so far:

- DailyAnalytics
- ActivityService
- ActivityTypeService
- AnalyzeService
- CalorieService
- DataInitializationService

### Contributing

Contributions are welcome! If you find any issues or want to enhance the application, feel free to submit pull requests.

1. Fork the repository.
2. Create a new branch for your feature/fix: git checkout -b feature-name
3. Commit your changes: git commit -m 'Add feature'
4. Push to the branch: git push origin feature-name
5. Create a pull request.

### License

This project is licensed under the [MIT License](LICENSE).

<!-- Image references -->

[apikey]: /contrib/apikey.jpg
[password]: /contrib/password.jpg
[home page]: /contrib/home_page.jpg
[spoonacular]: /contrib/spoonacular.jpg

<!-- Badge links -->

[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Spring]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Postgres]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[JavaScript]: https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React Router]: https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white
[MUI]: https://img.shields.io/badge/MUI-%230081CB.svg?style=for-the-badge&logo=mui&logoColor=white
[Docker]: https://img.shields.io/badge/docker-%230db7ed.svg?style=plastic&logo=docker&logoColor=white
[Git]: https://img.shields.io/badge/git-%23F05033.svg?style=plastic&logo=git&logoColor=white
[Bootstrap]: https://img.shields.io/badge/Bootstrap-7431fa?style=for-the-badge&logo=bootstrap&logoColor=white 


<!-- Project url -->
[Java-url]: https://docs.oracle.com/en/java/javase/17/ 
[Spring-url]: https://spring.io/
[Postgres-url]: https://www.postgresql.org/
[JavaScript-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript
[React-url]: https://reactjs.org/
[React Router-url]: https://reactrouter.com/en/main 
[MUI-url]: https://mui.com/
[Docker-url]: https://docs.docker.com/get-docker/
[Git-url]: https://git-scm.com/downloads
[Bootstrap-url]: https://getbootstrap.com/