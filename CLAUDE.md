# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

### Building the Project
```bash
mvn clean compile
```

### Packaging
The project builds two separate JAR files:
```bash
mvn clean package
```
This creates:
- `target/bsk-server-jar-with-dependencies.jar` (Server application)
- `target/bsk-client-jar-with-dependencies.jar` (Client application)

### Running Applications
```bash
# Run the server
java -jar target/bsk-server-jar-with-dependencies.jar

# Run the client (requires server to be running)
java -jar target/bsk-client-jar-with-dependencies.jar
```

### Development Mode
```bash
# Run server in development
mvn exec:java -Dexec.mainClass="BsK.server.Server"

# Run client in development  
mvn exec:java -Dexec.mainClass="BsK.client.Client"
```

## Architecture Overview

### Client-Server Model
This is a **desktop clinic management system** with a client-server architecture:

- **Server**: Netty-based WebSocket server handling database operations, file storage, and Google Drive integration
- **Client**: Java Swing desktop application connecting to server via WebSocket
- **Database**: SQLite database (`database/BSK.db`) managed by the server
- **Communication**: Custom packet-based protocol over WebSocket

### Core Package Structure

```
BsK/
├── client/           # Desktop client application
│   ├── ui/           # Swing UI components organized by page
│   └── network/      # Client-side networking handlers
├── server/           # Server application and services  
│   ├── network/      # Server-side handlers and session management
│   ├── database/     # Database entities and operations
│   └── service/      # External services (Google Drive)
└── common/           # Shared code between client and server
    ├── entity/       # Data models
    ├── packet/       # Request/response packets
    └── util/         # Utilities (date, network, reflection, etc.)
```

### UI Architecture
The client follows a page-based navigation pattern:
- `MainFrame.java`: Main window container
- Page components: `DashboardPage`, `CheckUpPage`, `QueueViewPage`, etc.
- Shared components in `common/` package for dialogs and custom UI elements
- Navigation via `NavBar.java` with consistent styling

### Network Protocol
- Custom packet-based communication over WebSocket
- All requests/responses extend the `Packet` interface
- `PacketSerializer` handles JSON serialization using Gson
- Request packets in `common/packet/req/`
- Response packets in `common/packet/res/`

### Data Management
- **Patient Management**: Patient records, medical history, checkup data
- **Inventory**: Medicine and service management
- **User Management**: Staff authentication and role-based access
- **File Storage**: Medical images, PDFs stored locally and optionally synced to Google Drive

## Key Technologies and Dependencies

- **Java 21**: Target JDK version
- **Maven**: Build system
- **Netty**: Async client-server networking
- **Swing/SwingX**: Desktop UI framework
- **SQLite**: Embedded database
- **Gson**: JSON serialization
- **Lombok**: Code generation
- **JasperReports**: PDF report generation
- **iText7**: PDF manipulation
- **Google Drive API**: Cloud file storage
- **Log4j2**: Logging framework

## Configuration

### Server Configuration
Edit `config/config.properties`:
- `server.address` and `server.port`: Server binding
- `clinic.*`: Clinic information for reports
- `google.drive.*`: Google Drive integration settings
- `storage.*`: File storage paths

### Development Setup
1. Ensure Java 21 is installed
2. Configure server settings in `config/config.properties`
3. Start server first, then client
4. Database will be created automatically in `database/BSK.db`

## Important Development Notes

### Client-Server Communication
- All data operations must go through the server
- Client maintains session state in `LocalStorage.java`
- Use appropriate request/response packet pairs for new features
- Server handles authentication and session management

### UI Development
- Follow existing page structure in `client/ui/component/`
- Use shared components from `common/` package
- Maintain consistent styling with existing pages
- UI updates should be thread-safe (use SwingUtilities)

### Database Operations
- All database operations handled server-side in `ServerHandler.java`
- Use parameterized queries to prevent SQL injection
- Database schema managed through server initialization

### File Handling
- Medical images stored in `image/checkup_media/`
- PDF reports generated using JasperReports templates
- Google Drive integration for backup/sync (optional)

### Testing
Tests are currently disabled in Maven configuration (`<skipTests>true</skipTests>`).

## Critical Dependencies for New Features

When adding features that involve:
- **PDF generation**: Use JasperReports templates in `print_forms/`
- **Network requests**: Create new packet classes in appropriate req/res packages
- **Database operations**: Add handlers in `ServerHandler.java`
- **UI components**: Extend existing page patterns and use shared components
- **File operations**: Use server-side file management with optional Google Drive sync

## Resource Loading (IMPORTANT)

**All assets now properly organized in `src/main/resources/`**:
- **JRXML templates**: `src/main/resources/print_forms/`
- **UI assets**: `src/main/resources/assets/` (icon, img, font, gif subdirectories)
- **Fonts**: `src/main/resources/fonts/` (JasperReports fonts)

**Use ResourceLoader utility class** for all asset loading:
```java
import BsK.client.ui.util.ResourceLoader;

// Load icons
ImageIcon icon = ResourceLoader.loadAssetIcon("logo.jpg");

// Load images  
ImageIcon image = ResourceLoader.loadAssetImage("background.jpeg");

// Load any resource stream
InputStream stream = ResourceLoader.loadResourceStream("/print_forms/template.jrxml");
```

**Important Notes**:
- Never use file system paths (`System.getProperty("user.dir")`) - they won't work in JAR
- All asset references now use proper classpath resource loading
- ResourceLoader handles null checks and provides fallbacks
- Assets work consistently in both IDE and packaged JAR files