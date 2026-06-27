# Drop - Application Android Native

## Description
Drop est une application Android native pour le transfert de fichiers hors ligne via WiFi Direct (P2P).

## Configuration requise
- Android 5.0+ (API 21)
- WiFi Direct support
- Android Studio Giraffe ou supérieur
- JDK 11 ou supérieur

## Installation

### 1. Cloner le projet
```bash
git clone https://github.com/aatufabdoul-jpg/Drop.git
cd Drop
git checkout android-native
```

### 2. Ouvrir dans Android Studio
```bash
open -a "Android Studio" android/
```

### 3. Construire le projet
```bash
cd android
./gradlew build
```

## Déploiement sur téléphone

### USB Debug activé
1. Brancher le téléphone en USB
2. Activer les options développeur et USB Debug
3. Accepter la confirmation de débogage

### Via command line
```bash
cd android
./gradlew installDebug
```

### Via Android Studio
1. Cliquer sur "Run" (Maj+F10)
2. Sélectionner votre appareil

## Architecture

### Structure du projet
```
android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/drop/filetransfer/
│   │   │   │   ├── ui/              # Fragments & Activities
│   │   │   │   ├── service/         # Services (WiFi Direct, File Transfer)
│   │   │   │   ├── viewmodel/       # ViewModels
│   │   │   │   └── *.kt
│   │   │   ├── res/                 # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── gradle/
├── build.gradle
└── settings.gradle
```

### Composants clés

#### Services
- **WifiDirectService**: Gère la découverte et la connexion WiFi Direct
- **FileTransferService**: Gère l'envoi et la réception de fichiers

#### UI (Fragments)
- **HomeFragment**: Écran d'accueil avec statut WiFi et appareils découverts
- **SendFragment**: Sélection et envoi de fichiers
- **ReceiveFragment**: Réception de fichiers avec écoute sur un port

#### ViewModel
- Gestion d'état de l'UI
- Liaisons entre Services et UI

## Fonctionnalités

✅ Découverte d'appareils WiFi Direct
✅ Envoi de fichiers multiples
✅ Réception de fichiers
✅ Affichage de la progression
✅ Gestion des permissions (Android 6.0+)
✅ Interface intuitive avec bottom navigation

## Permissions requises

```xml
<!-- WiFi Direct -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- Fichiers -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<!-- Réseau -->
<uses-permission android:name="android.permission.INTERNET" />
```

## Dépendances principales

- **AndroidX**: Framework moderne Android
- **Jetpack**: Navigation, ViewModel, LiveData, Room
- **Kotlin Coroutines**: Programmation asynchrone
- **Dagger Hilt**: Injection de dépendances
- **Material Components**: Composants UI Material Design

## Débogage

### Logs
```bash
adb logcat | grep "Drop"
```

### Appareils connectés
```bash
adb devices
```

## Build Variants

- **debug**: Pour développement avec logs détaillés
- **release**: Optimisé, minifié, prêt pour Play Store

## Troubleshooting

### Erreur: "WiFi Direct not supported"
- Certains appareils n'ont pas le support WiFi Direct
- Vérifier: Settings > Developer Options > WiFi Direct

### Erreur: "Permission denied"
- S'assurer que toutes les permissions sont accordées
- Android 6.0+ nécessite runtime permissions

### Erreur: "Cannot find symbol"
- Exécuter: `./gradlew clean build`
- Invalider les caches: File > Invalidate Caches/Restart

## Développement futur

- [ ] Chiffrement des transferts
- [ ] Interface graphique améliorée
- [ ] Support Bluetooth comme fallback
- [ ] Historique des transferts
- [ ] Compression de fichiers
- [ ] Support multilingue complet

## Licence
MIT License - Voir LICENSE pour plus de détails

## Contact
Auteur: aatufabdoul-jpg
