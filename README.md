# 🛒 E-Commerce Automation Framework

## 🎤 Presentation
https://prezi.com/view/eWNk1CRycjlOXtWTUi5N/?referral_token=fAXriGlnB3FN

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
git clone https://github.com/nhahub/NHA-245.git
cd NHA-245
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
- Browser selection for Selenium WebDriver Interfaces  
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

# 📄 End of Automation Documentation

# 📡 API Documentation

This document describes all API endpoints used in the project, based on the uploaded Postman collections for Users, Products, Cart, and Checkout.

---

# 🌍 Environment Variables

| Variable | Description | Example |
|---------|-------------|---------|
| **BaseURL** | Base DummyJSON API | https://dummyjson.com |
| **UsersURL** | User API Base URL | https://dummyjson.com/users |
| **PostURL** | Posts/Tags API Base URL | https://dummyjson.com/posts |
| **token** | Login token stored after authentication | dynamic |
| **username** | Username from GetAllUsers | dynamic |
| **password** | Password from GetAllUsers | dynamic |
| **ID** | User ID stored from GetAllUsers | dynamic |
| **searchTerm** | Dynamic search string | Emily |

---

# 1️⃣ User API

## Create New User  
**POST** `{{UsersURL}}/add`

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "password": "mypassword",
  "age": 18
}
```

---

## Get All Users  
**GET** `{{UsersURL}}`  
Stores: username, password, ID

---

## Login  
**POST** `{{UsersURL}}/login`

```json
{
  "username": "{{username}}",
  "password": "{{password}}"
}
```

Saves: `token`

---

## Get Authenticated User  
**GET** `{{UsersURL}}/me`  
Auth: Bearer token

---

## Update User  
**PATCH** `{{UsersURL}}/{{ID}}`

---

## Delete User  
**DELETE** `{{UsersURL}}/{{ID}}`

---

## Get User by ID  
**GET** `{{UsersURL}}/1`

---

## Search Users  
**GET** `{{UsersURL}}/search?q={{searchTerm}}`

---

## Filter Users  
**GET** `{{UsersURL}}/filter?key=firstName&value=Emily`

---

## Sort Users  
**GET** `{{BaseURL}}/users?sortBy=age&order=asc`

---

## Pagination  
**GET** `{{BaseURL}}/users?limit=5&skip=10&select=firstName,id`

---

## Get All Tags  
**GET** `{{PostURL}}/tags`

---

## Get Posts by Tag  
**GET** `{{PostURL}}/tag/life`

---

# 2️⃣ Products API

## Get All Products  
**GET** `https://dummyjson.com/products`

---

## Get Product by ID  
**GET** `https://dummyjson.com/products/1`

---

## Search Products  
**GET** `https://dummyjson.com/products/search?q=phone`

---

## Add Product  
**POST** `https://dummyjson.com/products/add`

```json
{
  "title": "Gaming Mouse",
  "price": 350
}
```

---

## Update Product  
**PUT** `https://dummyjson.com/products/1`

---

## Delete Product  
**DELETE** `https://dummyjson.com/products/1`

---

## Pagination  
**GET** `https://dummyjson.com/products?limit=10&skip=10`

---

# 3️⃣ Cart API

## Get Cart by ID  
**GET** `https://dummyjson.com/carts/1`

---

## Add to Cart  
**POST** `https://dummyjson.com/carts/add`

```json
{
  "userId": 33,
  "products": [
    { "id": 50, "quantity": 2 }
  ]
}
```

---

## Remove Item / Update Cart  
**DELETE** `https://dummyjson.com/carts/1`

---

# 4️⃣ Checkout API

## Get Checkout Summary  
**GET** `https://dummyjson.com/carts/1`

---

## Submit Checkout  
**POST** `https://dummyjson.com/carts/add`

```json
{
  "userId": 33,
  "products": [
    { "id": 168, "quantity": 3 }
  ],
  "checkout": true
}
```

---

## Clear Cart  
**PUT** `https://dummyjson.com/carts/1`

```json
{
  "products": []
}
```

---

# 📄 End of API Documentation


