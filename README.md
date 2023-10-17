# Wallet-Service

This is an updated version of the Wallet-Service project, following the requirements and limitations described below.

## Requirements
- All entities should now be stored in a PostgreSQL database.
- Identifiers should be generated through sequences when saving to the database.
- DDL scripts for table creation and data pre-filling should only be executed using Liquibase migration tool.
- Liquibase migration scripts should be written in XML notation.
- There should be multiple migration scripts, at least one for creating all tables and another for data pre-filling.
- System tables should be stored in a separate schema.
- Entity tables should not be stored in the public schema.
- Test containers should be used for tests.
- The application should include a docker-compose.yml file, which should include instructions for deploying Postgre in Docker. The login, password, and database should be different from those specified in the default image. The application should work with the database deployed in Docker with the specified parameters.
- The application should support configuration files. Everything related to database connection and migrations should be configured through a configuration file.

## Installation and Setup
1. Clone the repository.
2. Update the configuration file (`config.properties`) with the appropriate database connection information.
3. Execute the Liquibase migration scripts using the following command: `liquibase update`
4. Build the application using the following command: `mvn clean install`
5. Run the application using the following command: `java -jar wallet-service.jar`

## Usage
You can now use the Wallet-Service application to manage wallets and perform various operations on them.

## Configuration
All configuration settings are stored in the `config.properties` file. Update this file as necessary to change the database connection details and other settings.

## Contributing
Contributions are welcome! If you have any improvements or bug fixes, feel free to submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
