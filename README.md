# GameVerdict - Plateforme d'Avis de Jeux Vidéo

## 📖 Description
GameVerdict est une application web full-stack qui permet aux joueurs de parcourir, noter et commenter des jeux vidéo. La plateforme offre une expérience communautaire modérée où les utilisateurs peuvent découvrir de nouveaux jeux, partager leurs opinions et lire des critiques authentiques.

## ✨ Fonctionnalités
### 🔐 Authentification et Profils Utilisateurs
* Inscription et connexion sécurisée avec JWT

* Profils personnalisables avec historique des avis et commentaires

* Système de rôles (Utilisateur, Administrateur)

## ⭐ Système d'Avis et Notation
* Notation sur 5 étoiles avec commentaires détaillés

* Modification et suppression des avis personnels

* Calcul de notes moyennes pondérées

* Classement des jeux les mieux notés

## 💬 Commentaires et Discussions
* Commentaires organisés en threads sous chaque avis

* Système de réponses aux commentaires

* Modération des contenus abusifs

## 👨‍💻 Tableau de Bord Administrateur
* Gestion complète CRUD des jeux

* Modération des utilisateurs (bannissement, changement de rôles)

* Supervision des avis et commentaires

## 🛠️ Stack Technique
### Frontend
* Framework: Angular 16

* Gestion d'état: NgRx

* UI/UX: Angular Material & Bootstrap

* Services: HTTP Client pour les appels API

### Backend
* Framework: Spring Boot 3

* Sécurité: Spring Security avec JWT

* API: RESTful API

* Base de données: MySQL 8

* ORM: JPA/Hibernate

### Outils de Développement
* Gestion de projet: Trello

* Versionning: Git/GitHub

* Tests API: Postman

## 🚀 Installation et Démarrage
### Prérequis
* Node.js 18+

* Java 17+

* MySQL 8+

* Angular CLI 16+

* Maven 3.6+

## Installation Frontend
bash

git clone https://github.com/votre-username/gameverdict.git

### Accéder au dossier frontend
cd gameverdict/frontend

### Installer les dépendances
npm install

### Démarrer le serveur de développement
ng serve
## Installation Backend
### Accéder au dossier backend
cd gameverdict/backend

## Configurer la base de données dans application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/gameverdict

spring.datasource.username=root

spring.datasource.password=votre_mot_de_passe

## Compiler et exécuter l'application
mvn spring-boot:run

Configuration de la Base de Données

Créer une base de données MySQL nommée gameverdict

Exécuter le script SQL fourni dans /database/schema.sql

Importer les données initiales depuis /database/initial_data.sql

# 🔐 Sécurité
* L'application utilise JSON Web Tokens (JWT) pour l'authentification:

* Tokens signés avec algorithme HS256

* Expiration après 24 heures

* Refresh tokens implémentés

* Protection contre les attaques CSRF et XSS

* Les routes sont protégées par des guards Angular côté frontend et Spring Security côté backend.

## Lien swagger
http://localhost:8080/swagger-ui/index.html#/