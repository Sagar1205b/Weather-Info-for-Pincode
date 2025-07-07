# 🌦️ Weather Info for Pincode

A Spring Boot REST API to fetch and store weather data for a specific Indian pincode and date using the OpenWeatherMap API. The service stores data in a PostgreSQL database to avoid redundant API calls.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA (Hibernate)
- PostgreSQL
- OpenWeatherMap API
- RestTemplate
- JUnit + Mockito

---

## 📦 Features

- Fetches weather data by `pincode` and `date`
- Caches data locally in DB to avoid repeated API calls
- Handles invalid/past date entries gracefully
- Simple and testable via Postman or Swagger
- Test cases written for service layer using Mockito

---

## 📌 Requirements

- Java 17
- Maven 3.8+
- PostgreSQL 13+
- OpenWeatherMap API Key

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/Weather-Info-for-Pincode.git
cd Weather-Info-for-Pincode
```

### 2. Configure Environment Variables

Set your **OpenWeatherMap API Key** in an environment variable:

```bash
export OPEN_WEATHER_API_KEY=your_api_key_here
```

Or you can modify `application.properties` (not recommended for production).

---

### 3. PostgreSQL Setup

Ensure your PostgreSQL instance is running.

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
openweather.api.key=YOUR_OPEN_WEATHER_API_KEY
```

---

### 4. Run the Application
Right click on pom.xml and open in terminal
```bash
mvn spring-boot:run
```

Application runs at:  
`http://localhost:8080`

---

### 5. Sample API Usage

**Endpoint:**

```http
GET http://localhost:8080/weather?pincode=580003&for_date=2025-07-07
```


---

## 🔐 API Key Usage Note

This project uses the **free tier** of OpenWeatherMap, which only allows fetching **current weather** — not historical.  
If a past date is provided, the API will return a meaningful error:  
> `"Fetching weather for past dates is not supported. Please enter today's date."`

You may provide your own valid OpenWeatherMap key to test functionality.

---

## 🧪 Running Tests
Right click on pom.xml and open in terminal
```
mvn test
```

Unit tests are written for the core `WeatherService` using JUnit & Mockito.

---



---

## 📄 References

- [OpenWeatherMap API](https://openweathermap.org/current)

---

## 🧑‍💻 Author
sbadammanavar@gmail.com
Sagar Badammanavar  
