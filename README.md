MyFinance - Personal Finance Management System

Welcome to MyFinance, a comprehensive personal finance management system that allows you to manage your budgets, track expenses, and visualize your financial data effortlessly.


About the Project

MyFinance is designed to help users manage their personal finances by allowing them to create and manage budgets, track their spending, and visualize financial data through interactive charts. Whether you're looking to save more effectively or simply gain insights into your spending habits, MyFinance provides the tools you need. Project was submitted as a final project for the CodersLab Java bootcamp.

Features

    User Authentication: Secure login and registration system with password encryption.
    Budget Management: Create, update, and delete budgets.
    Expense Tracking: Log expenses and categorize them for detailed insights.
    Data Visualization: Interactive charts to display budget usage and expense breakdowns.
    Responsive Design: Accessible on both desktop and mobile devices.

Built With:


    Spring Boot - Backend framework for building Java applications.
    Thymeleaf - Java-based templating engine for server-side rendering.
    Chart.js - JavaScript library for creating charts.
    Spring Security - Framework for securing Spring applications.
    Hibernate - ORM framework for mapping Java objects to database tables.
    MySQL - Relational database management system.
    Maven - Dependency management and build tool.
    Bootstrap - CSS framework for responsive design.

Getting Started

To get a local copy of this project up and running on your machine, follow the steps below.

Before you begin, ensure you have met the following requirements:

    Java 17 or higher
    Maven 3.8.1 or higher
    MySQL server installed and running

Installation

    Clone the repository:

git clone https://github.com/your-username/myfinance.git

Navigate to the project directory:

cd myfinance

Configure the database:

    Create a new database in MySQL.
    Use schema.sql included to get all the correct tables.


    
Update the application.properties file with your database credentials.

spring.datasource.url=jdbc:mysql://localhost:3306/your-database-name
spring.datasource.username=your-username
spring.datasource.password=your-password

Build the project using Maven:

mvn clean install

Run the application:

    mvn spring-boot:run

    Access the application:
    
        Open your web browser and navigate to http://localhost:8080/welcome

Usage

    Register: Create an account to start managing your finances.
    Login: Access your personalized dashboard.
    Create Budgets: Add new budgets and allocate funds.
    Track Expenses: Log expenses and assign them to budgets.
    View Reports: Analyze your financial health with visual reports.


Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

    Fork the Project
    Create your Feature Branch (git checkout -b feature/AmazingFeature)
    Commit your Changes (git commit -m 'Add some AmazingFeature')
    Push to the Branch (git push origin feature/AmazingFeature)
    Open a Pull Request

License

Distributed under the MIT License. See LICENSE for more information.
Contact

Project Link: https://github.com/your-username/myfinance
