# 🅿️ Android Parking Meter

> **Academic Project** — A native Android application simulating a smart parking meter with separate client and admin interfaces.

---

## ✨ Features

### 👤 Client Interface
- Insert virtual coins (100, 200, 500, 1000 millimes)
- Real-time countdown timer with progress bar
- Audio feedback on coin insertion
- Auto-timeout with sound alerts in the last 10 seconds
- Displays current balance and date/time
- "Out of service" screen when meter is disabled

### 🔐 Admin Interface
- Secure admin login
- Dashboard with total collected amount and session count
- Session history list
- Toggle meter status (In service / Out of service)
- Empty cash register (vider la caisse)
- Change paper roll (bobine)
- Toggle coin jam fault (panne monnaie)
- Alerts for paper jam (no tickets remaining)

### ⚙️ Configuration
- Price per minute (millimes)
- Timeout duration
- Minimum amount
- Parking zone and title
- GPS coordinates
- IP address

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| [Kotlin](https://kotlinlang.org/) | Primary language |
| [Android SDK 35](https://developer.android.com/) | Target platform (min SDK 24) |
| [Room Database](https://developer.android.com/training/data-storage/room) | Local persistence |
| [ViewModel + LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel) | MVVM architecture |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Async operations |
| [View Binding](https://developer.android.com/topic/libraries/view-binding) | UI binding |
| [Material Design](https://material.io/) | UI components |

---

## 🏗️ Architecture

This app follows the **MVVM** (Model-View-ViewModel) pattern:

```
app/src/main/java/com/example/parcmetreaissyal3si/
├── data/               # Room entities & DAOs
│   ├── Session.kt
│   ├── SessionDao.kt
│   ├── Config.kt
│   ├── ConfigDao.kt
│   └── AppDatabase.kt
├── repository/         # Data layer
│   └── ParcmetreRepository.kt
├── viewmodel/          # Business logic
│   ├── ClientViewModel.kt
│   ├── AdminViewModel.kt
│   └── ConfigViewModel.kt
└── ui/                 # Activities & Adapters
    ├── client/
    ├── admin/
    ├── config/
    └── adapter/
```

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Hedgehog or later)
- Android device or emulator with API 24+

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/aissouss/android-parking-meter.git

# 2. Open in Android Studio
File → Open → select the cloned folder

# 3. Sync Gradle
Android Studio will prompt you — click "Sync Now"

# 4. Run the app
Click ▶ Run or press Shift + F10
```

---

## 📋 How It Works

1. The **client** opens the app and sees the parking meter interface
2. They insert coins using the on-screen buttons
3. After inserting enough (minimum amount), they press **Valider** to get a ticket
4. A countdown timer starts; a beep warns them in the last 10 seconds
5. The **admin** logs in separately to monitor sessions, collected amounts, and manage the meter's state

---

## 🎓 Academic Context

This project was developed as a mobile development assignment (Devoir Mobile) during the L3 Software Engineering program.

**Authors:** Aissous & Boukraa  
[GitHub](https://github.com/aissouss)

---

## 📄 License

This project is for educational use only.
