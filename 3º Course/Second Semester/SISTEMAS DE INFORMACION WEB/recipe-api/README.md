# Recipe Web Application

## Project Overview
This project is a PHP-based web application that integrates a public recipe API and a text translation API. The website allows users to browse, filter, and translate recipe information. It features functionalities for both anonymous and registered users.

## Features

### Public Recipe API Integration
- Fetch and display recipes from a public API.
- Filter recipes based on various criteria using AJAX.

### Text Translation API Integration
- Translate recipe information into multiple languages.

### User Functionality

#### Anonymous Users
- View recipes and apply filters without registration.
- Register on the website to access additional features.

#### Registered Users
- Access a personal profile to view and update information.
- Export recipe information to PDF with dynamically generated content.
- View translated recipes.

#### Administrator Users
- Manage user data and access.
- Refresh and clear API data in the database.

## Setup and Installation
1. **Clone the repository to your local machine:**
    ```sh
    git clone https://github.com/eduezponda/php-recipe-api.git
    cd php-recipe-api
    ```
2. **Set up the database with the required tables using the provided SQL scripts.**
3. **Configure the database and API connections in the PHP scripts.**
4. **Deploy the project to your web server.**

## Usage
- Visit the main page to explore and filter recipes.
- Register or log in to translate recipes and export them to PDF.
- Admin users can manage the site content and user data through the back office.
