# ResQlink-Offline Android App

A complete offline emergency communication Android app built in Kotlin that uses **Bluetooth Classic** and **GPS** for communication between devices without requiring internet or mobile network connectivity.

## 🚀 Features

### Core Functionality
- **Completely Offline**: Works without internet or mobile network
- **Bluetooth Communication**: Uses Bluetooth Classic for device-to-device communication
- **GPS Integration**: Captures and shares location coordinates
- **Dual Role System**: Supports both Victim and Volunteer modes
- **Real-time Alerts**: Instant SOS and custom alert messaging

### Victim Mode
- **Emergency SOS**: One-tap emergency alert with high priority
- **Custom Alerts**: Send detailed alerts with specific needs and priority levels
- **Location Sharing**: Automatically includes GPS coordinates
- **Multiple Recipients**: Sends alerts to all nearby volunteers simultaneously

### Volunteer Mode
- **Background Listening**: Continuously listens for incoming alerts
- **Alert Management**: View and manage received alerts
- **SOS Notifications**: Vibration and sound alerts for emergency messages
- **Location Integration**: Open victim locations in Google Maps
- **Alert History**: Persistent storage of all received alerts

## 🏗️ Architecture

### Key Components

1. **VictimManager**: Handles scanning for volunteers and sending alert messages
2. **VolunteerManager**: Manages Bluetooth server socket and processes incoming alerts
3. **LocationHelper**: Handles GPS location fetching and updates
4. **BluetoothHelper**: Manages Bluetooth permissions and adapter operations
5. **Room Database**: Local storage for alerts and user preferences

### Data Flow

```
Victim Device                    Volunteer Device
     |                                |
     |-- Scan for volunteers ------>  |
     |                                |
     |-- Send Alert (JSON) -------->  |
     |                                |
     |<-- Process & Store Alert ----  |
     |                                |
     |<-- Trigger SOS Alert --------  |
```

## 📱 UI Components

### Activities
- **LoginActivity**: Role selection (Victim/Volunteer)
- **VictimActivity**: SOS button and custom alert form
- **VolunteerActivity**: Alert list and listening status
- **AlertDetailActivity**: Detailed alert view with map integration

### Key Features
- **Material Design**: Modern, intuitive UI following Material Design guidelines
- **Responsive Layout**: Adapts to different screen sizes
- **Real-time Updates**: Live status updates and progress indicators
- **Accessibility**: Proper content descriptions and navigation

## 🔧 Technical Implementation

### Bluetooth Communication
- **Custom UUID**: `12345678-1234-1234-1234-123456789abc`
- **JSON Messages**: Structured alert format with all necessary data
- **Connection Management**: Automatic connection handling and cleanup
- **Error Handling**: Robust error handling for connection failures

### Location Services
- **GPS Integration**: High-accuracy location fetching
- **Permission Handling**: Proper location permission management
- **Background Updates**: Continuous location monitoring
- **Fallback Support**: Handles location service unavailability

### Data Persistence
- **Room Database**: Local SQLite database for alerts
- **SharedPreferences**: User role and settings storage
- **Type Converters**: Custom converters for enum types
- **Flow Integration**: Reactive data updates with Kotlin Flow

## 📋 Permissions Required

### Bluetooth Permissions
- `BLUETOOTH_CONNECT` (Android 12+)
- `BLUETOOTH_SCAN` (Android 12+)
- `BLUETOOTH` (Android 11 and below)
- `BLUETOOTH_ADMIN` (Android 11 and below)

### Location Permissions
- `ACCESS_FINE_LOCATION`
- `ACCESS_COARSE_LOCATION`

### Additional Permissions
- `VIBRATE` (for SOS alerts)
- `INTERNET` (for Google Maps integration)

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (API level 24)
- Kotlin 1.9.22+
- Android Gradle Plugin 8.2.2+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd offline-rescue-app
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory and select it

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio
   - The app will install and launch

### Testing

1. **Install on Multiple Devices**
   - Install the app on at least 2 Android devices
   - Ensure both devices have Bluetooth enabled

2. **Set Up Roles**
   - On one device, select "Volunteer" role
   - On another device, select "Victim" role

3. **Test Communication**
   - In Victim mode, send an SOS or custom alert
   - In Volunteer mode, verify alert reception
   - Test location sharing and map integration

## 📊 Alert Message Format

```json
{
  "type": "ALERT",
  "priority": "HIGH",
  "need": "Medical Assistance",
  "lat": 13.0827,
  "lon": 80.2707,
  "timestamp": 1692345678,
  "victimId": "uuid-string",
  "message": "Additional details"
}
```

## 🔒 Security Considerations

- **Local Communication**: All communication is local via Bluetooth
- **No Internet**: App works completely offline
- **Permission-Based**: Requires explicit user consent for permissions
- **Data Privacy**: No data transmitted to external servers

## 🐛 Known Limitations

1. **Bluetooth Range**: Limited to Bluetooth Classic range (~100m)
2. **Device Discovery**: Requires devices to be discoverable
3. **Android Version**: Some features require Android 6.0+
4. **Battery Usage**: Continuous Bluetooth scanning may impact battery

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Material Design**: For the UI design system
- **Android Bluetooth API**: For Bluetooth communication
- **Google Play Services**: For location services
- **Room Database**: For local data persistence

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation for common issues

---

**Note**: This app is designed for emergency situations and should be used responsibly. Always ensure proper testing before deployment in critical environments.
