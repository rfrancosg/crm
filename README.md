# Project Title
CRM proyect allow you to handle a set of users and customers.
It has a crud for users and customers.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Installation
1. Clone the repository:
```bash
 git clone https://github.com/rfrancosg/crm.git
```

2. Install dependencies:
```bash
 mvn clean install
```
## Usage
To run the project, do the following:
```bash
 1. Generate the package
   mvn clean install or mvn clean package
   This will generate the package needed to deploy on docker.
 2. Install docker. You have to install docker in order to execute the project as a docker image. If you dont want to execute it this way you can set the required properties in application properties and execute it as a springboot aplication
 3. Run docker compose file
   If you want to execute the project via docker you have to run the docker-compose file desired. 
   1. In local configuration it will create the database, tables and admin user. But once the database is created you will have to create an schema with crm name.
   2. For other configuration it will run against qa or pro databases hosted in aws.
   3. To execute docker compose file you can execute the following command: docker-compose -f docker-compose-{{the desired environment}}.yml up
   4. You can also execute the docker compose file from your ide tools.
  
 4.Once the project is running you will have to authenticate. It will create an admin user that you can use to start setting up users and customers.
  Admin user credentials are: 
   email: admin@gmail.com
   password: admin
   You will have to authenticate against: http://localhost:8080/auth/login. This will return you a token that you will have to use for the others requests.
   Here is a sample curl request for auth:
      curl --location --request POST 'http://localhost:8080/auth/login' \
     --header 'Content-Type: application/json' \
     --header 'Cookie: JSESSIONID=5D0B2F260A311A6F7F7EE9ECAB21CFC2' \
     --data-raw '{
         "email": "admin@gmail.com",
         "password": "admin"
     }'
 Once authenticated you will have to add the Authorization header to your requests and add as value: Bearer {{Your token}}

 With all this setup you can start using the project features.
```
## Contributing
```bash
 1. Fork the repository.
 2. Create a new branch: `git checkout -b feature-name`.
 3. Make your changes.
 4. Push your branch: `git push origin feature-name`.
 5. Create a pull request.
```
