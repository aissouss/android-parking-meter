# Mini-Projet Parc-mètre

Application Android de gestion d'un parc-mètre avec Kotlin et Jetpack Compose.

## Arborescence du projet
```
.
├── app
│   ├── build.gradle.kts
│   └── src
│       └── main
│           ├── AndroidManifest.xml
│           ├── java/com/example/parcmetreaissyal3si
│           │   ├── MainActivity.kt
│           │   └── ui/theme
│           │       ├── Color.kt
│           │       ├── Theme.kt
│           │       └── Type.kt
│           └── res
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew
```

## Fichiers Kotlin principaux
- `MainActivity.kt` : Contient toute la logique applicative
  - Data classes : Config, Session
  - AppData : Gestion des données en mémoire
  - Screens : Configuration, Client, Administration

## Composables Jetpack Compose
- `MainContent` : Navigation entre les écrans
- `ConfigurationScreen` : Configuration des paramètres du parc-mètre
- `ClientScreen` : Interface client pour insertion de pièces
- `AdministrationScreen` : Interface d'administration avec accès contrôlé

## Fonctionnalités

### Configuration
- Prix par minute : 10 millimes
- Timeout : 1 minute
- Montant minimum : 1 dinar
- État : En service / Hors service
- Adresse IP : 10.10.2.25
- Localisation GPS
- Tickets par bobine : 100
- Titre personnalisable

### Client
- Insertion de pièces : 100, 200, 500 millimes et 1 dinar
- Bouton rouge "Annuler" : annule la transaction
- Bouton vert "Valider" : valide si montant ≥ 1 dinar
- Timeout automatique avec signal sonore

### Administration
- Code d'accès : `1234`
- Affichage de l'état de la machine
- Montant total encaissé
- Nombre de tickets restants
- Réinitialisation de la caisse
- Réinitialisation des tickets
- Simulation de changement de bobine papier

## Lancement du projet
1. Ouvrir le projet dans **Android Studio**
2. Synchroniser le projet avec Gradle
3. Lancer sur un émulateur Android (API 24+) ou un appareil physique

## Technologies utilisées
- Kotlin
- Jetpack Compose
- Architecture simple (données en mémoire)
- Aucune dépendance externe
