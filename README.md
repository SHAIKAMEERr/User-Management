# User Management Service

Welcome to the **User Management Service**, a comprehensive and secure system designed to handle user authentication, registration, and profile management. Built using **Spring Boot**, this microservice follows industry-standard practices to ensure scalability, security, and seamless integration.

## 🚀 Features

- **User Registration**: Enable users to register with secure validation.
- **User Login**: OAuth 2.0-powered authentication for enhanced security.
- **Role-Based Access Control**: Manage user roles and permissions.
- **Profile Management**: Update and manage user details with ease.
- **Login Page**: A user-friendly login interface for quick access.
- **Secure Data Handling**: HMAC SHA-256 encryption ensures data confidentiality.
- **Microservice Integration**: Communicates with Quiz Management and Quiz Attempt services.

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot
- **Security**: OAuth 2.0, HMAC SHA-256
- **Database**: MySQL with Spring Data JPA
- **Utilities**: ModelMapper, Lombok, SLF4J
- **Frontend**: Responsive login page design

## 📋 Prerequisites

- **Java Development Kit (JDK)**: Version 17 or higher
- **Maven**: Version 3.8+
- **MySQL**: Version 8.0+
- **Postman** (optional for API testing)

## 📖 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/SHAIKAMEERr/User-Management
cd User-Management
```

### 2. Configure Database
Update the `application.properties` file with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Access the Application
- The application runs on **port 8081**.
- Open your browser and navigate to:
  ```
  http://localhost:8081/
  ```
  Explore the login page and other functionalities.

## 🌐 API Endpoints

### User Management
- **POST** `/api/v1/users` - Register a new user
- **GET** `/api/v1/users/{id}` - Retrieve user details
- **PUT** `/api/v1/users/{id}` - Update user information
- **DELETE** `/api/v1/users/{id}` - Delete a user

### Authentication
- **POST** `/api/v1/auth/login` - User login
- **POST** `/api/v1/auth/logout` - User logout

## 🔗 Integration with Other Services

The User Management Service integrates seamlessly with:

- [Quiz Management Service](https://github.com/SHAIKAMEERr/Quiz-Management-Service)
- [Quiz Attempt Service](https://github.com/SHAIKAMEERr/Quiz-Attempt-Service)

This ensures a unified user experience across related services.

## 🔒 Security Highlights

- **OAuth 2.0**: Ensures secure authentication.
- **HMAC SHA-256**: Protects sensitive user data.

## 🎨 Login Page Preview

The service includes a beautifully designed login page to enhance the user experience. Navigate to [http://localhost:8081/](http://localhost:8081/) to explore it.

## 👥 Contributing

We welcome contributions to enhance this service. To contribute:
1. Fork the repository.
2. Create a new feature branch.
3. Submit a pull request for review.

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

For any queries or support, feel free to reach out to the repository maintainer.
