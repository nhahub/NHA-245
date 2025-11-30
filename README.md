# 🛒 E-Commerce Automation Framework (Java)

## 📌 Overview
This repository contains a modular and maintainable Java automation framework that simulates and tests core e-commerce workflows such as login, browsing products, adding items to the cart, checkout, order placement, and logout.  
The project follows clean architecture and good automation practices to ensure scalability, readability, and reliability.

## ✨ Key Features
- 🔐 Login & Logout automation
- 🛍️ Product browsing and selection
- 🛒 Cart operations with cart state management
- 💳 Checkout workflow automation
- 📦 Order creation & validation
- 🧪 Unified test execution using TestNG/JUnit
- ⚙️ Configurable setup with BaseClass
- 🏗️ Clean, modular, and maintainable structure

## 🧰 Technologies Used
- Java  
- TestNG / JUnit  
- Maven or Gradle  
- Git / GitHub  

## 📁 Project Structure
```
/src
 └── main
      └── java
           ├── BaseClass.java
           ├── Login.java
           ├── Logout.java
           ├── Products.java
           ├── Cart.java
           ├── CartState.java
           ├── Checkout.java
           ├── Order.java
 └── test
      └── java
           ├── AppTest.java
           ├── AllTests.java
```

## ⚙️ Setup Instructions

### 1. Clone the repository
```
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```

### 2. Check Java installation
```
java -version
```

### 3. Install dependencies  
If using Maven:
```
mvn install
```

If using Gradle:
```
gradle build
```

## 📥 Installation
Ensure you have:
- Java 8 or higher  
- Maven/Gradle  
- TestNG or JUnit  

## ▶️ Running Tests

### Maven:
```
mvn test
```

### TestNG suite:
```
mvn test -DsuiteXmlFile=testng.xml
```

### Gradle:
```
./gradlew test
```

## 🔧 Configurations
Editable via:
- config.properties  
- BaseClass.java  

Configurable parameters:
- URLs  
- Browser selection (if using Selenium)  
- Timeouts  
- Login credentials  

## 🧹 Best Practices Followed
- Clean code & naming conventions  
- Separation of concerns  
- Reusable methods and modules  
- Test isolation  
- Centralized configuration  
- Scalability-first structure  

## 📈 Additional Notes
This repo can easily integrate with CI/CD (GitHub Actions, Jenkins) and support reporting tools like Allure or ExtentReports.

## 🎤 Presentation
*(Reserved for future content)*
