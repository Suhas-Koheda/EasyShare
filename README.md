# EasyShare - Kotlin Multiplatform Application

![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-0078D7?logo=windows&logoColor=white)
![Server](https://img.shields.io/badge/Server-000000?logo=ktor&logoColor=white)

EasyShare is a Kotlin Multiplatform project targeting:
- Android (Compose)
- Desktop (Compose)
- Server (Ktor)

## Project Structure

```
.
├── composeApp/          # Shared Compose Multiplatform code
│   ├── commonMain/      # Common code for all platforms
│   ├── androidMain/     # Android-specific code
│   └── desktopMain/     # Desktop-specific code
├── server/              # Ktor server application
└── shared/              # Shared code between all targets
├── commonMain/      # Common platform-agnostic code
├── androidMain/     # Android platform implementations
└── jvmMain/         # JVM platform implementations
```

## Features

- **Multiplatform UI**: Shared Compose UI across Android and Desktop
- **Network Utilities**: Platform-specific network implementations
- **Server Backend**: Ktor-based server for shared business logic

## Prerequisites

- Kotlin 1.9.20
- Android Studio (for Android development)
- JDK 11+
- Gradle 8.9

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/EasyShare.git
   cd EasyShare
   ```

2. **Run Android App**
  - Open project in Android Studio
  - Select `composeApp` Android configuration
  - Run on emulator or device

3. **Run Desktop App**
   ```bash
   ./gradlew :composeApp:run
   ```

4. **Run Server**
   ```bash
   ./gradlew :server:run
   ```

## Building

- **Android APK**:
  ```bash
  ./gradlew :composeApp:assembleDebug
  ```

- **Desktop Distribution**:
  ```bash
  ./gradlew :composeApp:packageDistributionForCurrentOS
  ```

## Dependencies

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) - UI framework
- [Ktor](https://ktor.io/) - Server framework
- [Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime) - Date/time utilities

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
```