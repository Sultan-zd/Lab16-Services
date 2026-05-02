# Lab16 - Service de Chronomètre Professionnel (Android Java)

Ce dépôt contient le **Laboratoire 16** portant sur l'implémentation des **Services Android** (Foreground & Bound Services). L'application est un chronomètre robuste et professionnel capable de fonctionner en arrière-plan.

## Fonctionnalités
- **Foreground Service** : Le chronomètre continue de tourner même si l'application est fermée ou en arrière-plan.
- **Bound Service** : Communication bidirectionnelle entre `MainActivity` et `ChronometreService` pour une mise à jour de l'UI en temps réel.
- **Notification Interactive** : Inclut une action "Arrêter" directement dans la notification pour stopper le service sans rouvrir l'application.
- **Interface Material Design 3** : Design moderne utilisant `MaterialCardView` et `MaterialButton` avec une gestion propre du cycle de vie.
- **Gestion des Permissions** : Demande dynamique de la permission `POST_NOTIFICATIONS` pour Android 13 (API 33) et versions supérieures.
- **View Binding** : Implémentation du View Binding pour un accès sécurisé et performant aux composants graphiques.

## Technologies Utilisées
- **Langage** : Java
- **Android SDK** : Compile SDK 36 / Target SDK 36
- **Composants** : Service, NotificationManager, PendingIntent, Binder.
- **UI** : XML Layouts, Material Components, View Binding.

## Installation et Configuration
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Sultan-zd/Lab16-Services.git
   ```
2. Ouvrez le projet dans **Android Studio**.
3. Attendez la synchronisation **Gradle**.
4. Exécutez l'application sur un appareil ou émulateur (Android 7.0+ / API 24+).

## Structure du Projet
- `ChronometreService.java` : Gère la logique du chronomètre, la notification persistante et le cycle de vie du service au premier plan.
- `MainActivity.java` : Interface utilisateur gérant les interactions et se liant au service pour afficher le temps écoulé.
- `AndroidManifest.xml` : Déclarations des permissions (`FOREGROUND_SERVICE`, `DATA_SYNC`) et du service.

## Licence
Ce projet est réalisé à des fins pédagogiques dans le cadre du cours sur le développement mobile Android.

---
*Repo : [https://github.com/Sultan-zd/Lab16-Services.git](https://github.com/Sultan-zd/Lab16-Services.git)*
