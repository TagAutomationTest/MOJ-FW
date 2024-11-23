# MOJ-Framework

This is an automation framwork created to automate web and Apis .



## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

---

## Introduction

This repository contains an automation framework built with Selenium WebDriver, Cucumber-TestNG and Restassured  The goal of this project is to automate web applications for testing purposes, ensuring higher efficiency, reliability, and faster release cycles.
### Technologies Used:
- **Language(s)**: Java, Python, JavaScript, etc.
- **Frameworks**: React, Node.js, Spring Boot, etc.
- **Libraries**: List any important libraries or dependencies used in the project.

---

## Features

- **Feature 1**: Maven project built
- **Feature 2**: Selenium
- **Feature 3**: Restassured
- **Feature 4**: Cucumber-TestNG
- **Feature 5**: Read data from feature data table
- **Feature 6**: Allure report integeration
- **Feature 7**: Connect on SQL DB using JDBC driver
- **Feature 8**: Get and Set properties from Configuration file
- **Feature 9**: Page object design pattern

---

## Installation

### Prerequisites:
- [Java](https://www.guru99.com/install-java.html) should be installed and configured.
- [Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) should be installed and configured.
- Download the files from Git repository either as zip file OR using [Git](https://phoenixnap.com/kb/how-to-install-git-windows).
- Download and install [Allure](https://allurereport.org/docs/install-for-windows/) commandline application, suitable for your environment.
  
### Steps to Install:
1. Clone the repository:
    ```bash
    git clone [https://github.com/TagAutomationTest/MOJ-FW.git]
    ```
2. Navigate into the project directory, Run below commands to Install dependencies.
    ```bash
     mvn clean install
    ```
3. Run tests using below command.
    ```bash
    mvn test
    ```
4. Allure Report: To generate the report we need to go through below steps.
```bash
allure serve target/allure-results 
---

## Usage

### Running the Project:
Provide instructions on how to run the project, whether locally or in a specific environment.

For example:
```bash
npm start    # For Node.js apps
python app.py    # For Python apps
java -jar app.jar    # For Java apps
