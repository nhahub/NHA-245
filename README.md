# 🛒 E-Commerce Automation Framework

## 🎤 Presentation
https://prezi.com/view/eWNk1CRycjlOXtWTUi5N/?referral_token=fAXriGlnB3FN


# 🐞 Bughunters Automation Framework

## 📌 Overview  
This repository contains a **modular, scalable, and maintainable Java automation testing framework** built to simulate and validate **core e-commerce workflows** on the **Swag Labs Demo Application**.  
The framework automates real user scenarios such as **login, product browsing, cart operations, checkout, order placement, and logout**, following **clean architecture principles and industry best practices**.

The project is implemented using the **Page Object Model (POM)** design pattern to ensure **high reusability, readability, and long-term maintainability**.

---

## ✨ Key Features  
- 🔐 **Login & Logout automation**
- 🛍️ **Product browsing and selection**
- 🛒 **Cart operations with cart state validation**
- 💳 **Checkout workflow automation**
- 📦 **Order creation & validation**
- 🧪 **Unified test execution using TestNG**
- ⚙️ **Centralized setup via BaseTest & DriverFactory**
- 🏗️ **Clean, modular, and scalable project structure**
- 🔄 **Full end-to-end user journey testing**

---

## 🧰 Technologies Used  
- **Java**  
- **Selenium WebDriver**  
- **TestNG**  
- **Maven**  
- **WebDriverManager**  
- **Git / GitHub**  
- **Page Object Model (POM)** Design Pattern  

---

## 📁 Project Structure  

```
/src
 └── main
      └── java
           ├── base
           │    ├── BaseTest.java
           │    └── DriverFactory.java
           │
           ├── pages
           │    ├── LoginPage.java
           │    ├── ProductsPage.java
           │    ├── CartPage.java
           │    ├── CheckoutPage.java
           │    ├── OrderPage.java
           │    └── LogoutPage.java
           │
           └── utils
                ├── WaitUtils.java
                └── CartState.java
 └── test
      └── java
           ├── LoginTest.java
           ├── ProductsTest.java
           ├── CartTest.java
           ├── CheckoutTest.java
           ├── OrderTest.java
           ├── LogoutTest.java
           └── FullJourneyTest.java

testng.xml
pom.xml
```

---

## ⚙️ Setup Instructions  

### 1. Clone the Repository  
```
git clone https://github.com/your-username/Bughunters-Automation.git
cd Bughunters-Automation
```

### 2. Check Java Installation  
```
java -version
```

### 3. Install Dependencies  
Using Maven:
```
mvn clean install
```

---

## 📥 Installation Requirements  
Ensure you have the following installed on your system:

- ✅ Java 8 or higher  
- ✅ Maven  
- ✅ Google Chrome / Firefox  
- ✅ Internet connection for WebDriverManager  

---

## ▶️ Running Tests  

### ✅ Run All Tests Using Maven  
```
mvn test
```

### ✅ Run Using TestNG Suite  
```
mvn test -DsuiteXmlFile=testng.xml
```

---

## 🔧 Configurations  

Editable through:
- `BaseTest.java`
- Browser setup via `DriverFactory.java`

Configurable parameters include:
- 🌐 Application URL  
- 🌍 Browser type  
- ⏱️ Timeouts  
- 👤 Login credentials  

---

## 🧹 Best Practices Followed  
- ✅ Clean code and clear naming conventions  
- ✅ Page Object Model (POM) architecture  
- ✅ Separation of concerns  
- ✅ Reusable utilities  
- ✅ Test isolation  
- ✅ Centralized browser management  
- ✅ Scalable framework design  

---

## 📈 Additional Notes  

- 🚀 The framework is **CI/CD ready** and can be integrated easily with:
  - GitHub Actions  
  - Jenkins  
- 📊 Supports future integration with:
  - Allure Reports  
  - Extent Reports  
- 🔐 Designed for **full regression and smoke testing**  

---

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


