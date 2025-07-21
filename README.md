# Tastify

Tastify is a food ordering application built for Android using Jetpack Compose with a modular architecture.

## 🚀 Key Features

- Product catalog with filters and search
- Shopping cart with real-time stock management
- Registration and login with validation
- Simulated payment with card validation
- Editable user profile
- Unit tests for ViewModels and Repositories
- Image upload via Cloudinary
- Support for dark and light themes

## 🧱 Architecture

The project follows a modular architecture:

```bash
Tastify/
├── app/
├── core/
│   ├── db/
│   ├── model/
│   ├── navigation/
│   ├── network/
│   ├── session/
│   └── theme/
├── data/
│   ├── remotes/
│   └── worker/
├── domain/
│   └── useCase/
├── feature/
│   ├── authentication/
│   ├── cart/
│   ├── common/
│   ├── home/
│   ├── login/
│   ├── menu/
│   ├── orderDetail/
│   ├── orderHistory/
│   ├── orderPay/
│   ├── productFilter/
│   ├── productList/
│   ├── profile/
│   └── register/
└── library/
    └── utils/
```

## ⚙️ Technologies and Libraries

- Kotlin
- Jetpack Compose
- Room
- Retrofit
- Coroutines + Flow
- Hilt
- Mockito / JUnit for testing
- Material3
- Cloudinary for remote image storage

## 🧪 Testing

The project includes unit tests for:

- ViewModels (using kotlinx.coroutines.test, mockito-kotlin)
- Repositories (mocking local and remote dependencies)

Run tests:

```bash
./gradlew testDebugUnitTest
```

## 🔧 Local Setup

To run the project locally:

1. Clone the repository:

```bash
git clone https://github.com/tu_usuario/tastify.git
```

2. Open it in Android Studio (Giraffe or newer recommended).
3. Configure local.properties:

```properties
API_BASE_URL="https://peyademoappapi-wuwd.onrender.com"
CLOUDINARY_UPLOAD_URL="https://api.cloudinary.com/v1_1/drioaxhhw/image/upload"
```

4. Sync dependencies and build:

```bash
./gradlew build
```
