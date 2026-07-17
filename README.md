# ChâTop API

API REST pour la gestion des annonces de location avec système d'authentification JWT et messaging entre utilisateurs.

## Table des matières

- [Vue d'ensemble](#vue-densemble)
- [Technologies](#technologies)
- [Installation](#installation)
- [Configuration](#configuration)
- [Exécution](#exécution)
- [Documentation API](#documentation-api)
- [Structure du projet](#structure-du-projet)
- [Authentification](#authentification)

## Vue d'ensemble

ChâTop API est une application Spring Boot conçue pour :
- Gérer l'authentification et l'enregistrement des utilisateurs
- Publier et gérer des annonces de location
- Permettre la communication entre les propriétaires et les locataires via un système de messagerie

## Technologies

- **Java 21** - Langage de programmation
- **Spring Boot 4.1.0** - Framework principal
- **Spring Security** - Authentification et autorisation
- **Spring Data JPA** - Accès aux données
- **MySQL** - Base de données
- **JWT (JSON Web Token)** - Authentification stateless
- **SpringDoc OpenAPI (Swagger)** - Documentation API
- **ModelMapper** - Mapping DTO/Entity
- **Lombok** - Réduction du boilerplate
- **Maven** - Gestionnaire de dépendances

## Installation

### Prérequis

- Java 21 ou supérieur
- Maven 3.6+
- MySQL 8.0+

### Étapes

1. **Cloner le projet**
```bash
git clone <repository-url>
cd chatop-api
```

2. **Installer les dépendances**
```bash
mvn clean install
```

3. **Configurer la base de données** (voir section [Configuration](#configuration))

## Configuration

### Variables d'environnement

Créer un fichier `.env` ou configurer les variables système :

```properties
# Base de données
DB_HOST=localhost
DB_PORT=3306
DB_NAME=chatop_db
DB_USERNAME=root
DB_PASSWORD=root

# Serveur
SERVER_PORT=8080

# JWT
JWT_SECRET=your-secret-key-at-least-32-characters-long
JWT_EXPIRATION=86400000  # 24 heures en millisecondes
```

### Fichier application.yml

Les variables sont injectées dans `src/main/resources/application.yml` :

```yaml
spring:
  mvc:
    servlet:
      path: /api
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?serverTimezone=UTC&createDatabaseIfNotExist=true
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: ${SERVER_PORT}

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION}
```

## Exécution

### Démarrer l'application

```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

### Accéder à Swagger UI

```
http://localhost:8080/swagger-ui.html
```

### Accéder aux API docs (JSON)

```
http://localhost:8080/api-docs
```

## Documentation API

### Base URL

```
http://localhost:8080/api
```

### Authentification

La majorité des endpoints sécurisés nécessitent un **Bearer Token JWT** dans le header `Authorization` :

```
Authorization: Bearer <your-jwt-token>
```

---

##  Endpoints d'Authentification

### 1. Enregistrement d'un utilisateur

**POST** `/auth/register`

Enregistrer un nouvel utilisateur.

**Request Body:**
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "SecurePassword123"
}
```

**Réponses:**

- **200 OK** - Utilisateur créé avec succès
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe"
}
```

- **400 Bad Request** - Données invalides ou utilisateur existe déjà
```json
{
  "error": "Email already exists",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

---

### 2. Connexion utilisateur

**POST** `/auth/login`

Authentifier un utilisateur et recevoir un token JWT.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePassword123"
}
```

**Réponses:**

- **200 OK** - Connexion réussie
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe"
}
```

- **400 Bad Request** - Email ou mot de passe incorrect
```json
{
  "error": "Invalid email or password",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

---

### 3. Récupérer l'utilisateur connecté

**GET** `/auth/me`

Récupérer les informations de l'utilisateur actuellement authentifié.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Réponses:**

- **200 OK** - Informations utilisateur
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe"
}
```

- **401 Unauthorized** - Token invalide ou expiré
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

---

## Endpoints Rentals (Annonces de location)

### 1. Récupérer toutes les annonces

**GET** `/rentals`

Récupérer la liste de toutes les annonces de location.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Réponses:**

- **200 OK** - Liste des annonces
```json
{
  "rentals": [
    {
      "id": 1,
      "title": "Bel appartement 2 pièces",
      "description": "Appartement lumineux avec balcon",
      "surface": 65,
      "price": 1200,
      "picture": "https://example.com/images/rental1.jpg",
      "owner_id": 1,
      "created_at": "2024-07-17T10:00:00Z",
      "updated_at": "2024-07-17T10:00:00Z"
    }
  ]
}
```

- **401 Unauthorized** - Token absent ou invalide
```json
{
  "error": "Unauthorized",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

---

### 2. Récupérer une annonce par ID

**GET** `/rentals/{id}`

Récupérer les détails d'une annonce spécifique.

**Parameters:**
- `id` (path) - ID de l'annonce

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Réponses:**

- **200 OK** - Détails de l'annonce
```json
{
  "id": 1,
  "title": "Bel appartement 2 pièces",
  "description": "Appartement lumineux avec balcon",
  "surface": 65,
  "price": 1200,
  "picture": "https://example.com/images/rental1.jpg",
  "owner_id": 1,
  "created_at": "2024-07-17T10:00:00Z",
  "updated_at": "2024-07-17T10:00:00Z"
}
```

- **401 Unauthorized** - Token absent ou invalide

---

### 3. Créer une nouvelle annonce

**POST** `/rentals`

Créer une nouvelle annonce de location avec image.

**Content-Type:** `multipart/form-data`

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body (Form Data):**
```
title: "Bel appartement 2 pièces"
description: "Appartement lumineux avec balcon"
surface: 65
price: 1200
picture: [file]
```

**Réponses:**

- **200 OK** - Annonce créée
```json
{
  "id": 1,
  "message": "Rental created successfully"
}
```

- **400 Bad Request** - Données invalides
```json
{
  "error": "Validation failed",
  "details": {
    "title": "Title is required",
    "price": "Price must be greater than 0"
  }
}
```

- **401 Unauthorized** - Token absent ou invalide

---

### 4. Mettre à jour une annonce

**PUT** `/rentals/{id}`

Mettre à jour les informations d'une annonce existante.

**Content-Type:** `multipart/form-data`

**Parameters:**
- `id` (path) - ID de l'annonce à mettre à jour

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body (Form Data):**
```
title: "Bel appartement rénové 2 pièces" (optionnel)
description: "Appartement lumineux avec balcon et parking" (optionnel)
surface: 70 (optionnel)
price: 1300 (optionnel)
picture: [file] (optionnel)
```

**Réponses:**

- **200 OK** - Annonce mise à jour
```json
{
  "id": 1,
  "message": "Rental updated successfully"
}
```

- **400 Bad Request** - Données invalides ou annonce non trouvée

- **401 Unauthorized** - Non authentifié ou non propriétaire de l'annonce

---

## Endpoints Messages

### 1. Envoyer un message

**POST** `/messages`

Envoyer un message à propos d'une annonce.

**Headers:**
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "rental_id": 1,
  "message": "Bonjour, je suis intéressé par cette annonce. Puis-je visiter ?"
}
```

**Réponses:**

- **200 OK** - Message envoyé
```json
{
  "id": 1,
  "message": "Message sent successfully"
}
```

- **400 Bad Request** - Données invalides
```json
{
  "error": "Validation failed",
  "details": {
    "rental_id": "Rental ID is required",
    "message": "Message cannot be empty"
  }
}
```

- **401 Unauthorized** - Token absent ou invalide
```json
{
  "error": "Unauthorized",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

---

## Endpoints Utilisateur

### 1. Récupérer un utilisateur par ID

**GET** `/user/{id}`

Récupérer les informations d'un utilisateur spécifique.

**Parameters:**
- `id` (path) - ID de l'utilisateur

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Réponses:**

- **200 OK** - Informations utilisateur
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe"
}
```

- **401 Unauthorized** - Token absent ou invalide

---

## Structure du projet

```
chatop-api/
├── src/
│   ├── main/
│   │   ├── java/com/openclassroom/chatopapi/
│   │   │   ├── ChatopApiApplication.java          # Point d'entrée
│   │   │   ├── config/                             # Configurations
│   │   │   │   ├── OpenApiConfig.java             # Swagger/OpenAPI
│   │   │   │   ├── SecurityConfig.java            # Sécurité Spring
│   │   │   │   └── WebConfig.java                 # Configuration Web
│   │   │   ├── controller/                         # Contrôleurs REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── MessageController.java
│   │   │   │   ├── RentalController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/                                # Data Transfer Objects
│   │   │   │   ├── AuthRequest.java
│   │   │   │   ├── CreateRentalDto.java
│   │   │   │   ├── MessageRequestDto.java
│   │   │   │   ├── RegisterUserDto.java
│   │   │   │   ├── RentalDto.java
│   │   │   │   ├── UpdateRentalDto.java
│   │   │   │   └── UserDto.java
│   │   │   ├── exception/                          # Gestion d'erreurs
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── filter/                             # Filtres
│   │   │   │   └── JwtAuthFilter.java
│   │   │   ├── model/                              # Entités JPA
│   │   │   │   ├── Message.java
│   │   │   │   ├── Rental.java
│   │   │   │   └── User.java
│   │   │   ├── record/                             # Records Java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── MessageResponse.java
│   │   │   │   └── RentalUpSertResponse.java
│   │   │   ├── repository/                         # Accès données
│   │   │   │   ├── MessageRepository.java
│   │   │   │   ├── RentalRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── services/                           # Logique métier
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── MessageService.java
│   │   │   │   ├── RentalService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── impl/
│   │   │   │       ├── MessageServiceImpl.java
│   │   │   │       ├── RentalServiceImpl.java
│   │   │   │       └── UserServiceImpl.java
│   │   │   └── utils/                              # Utilitaires
│   │   │       ├── JWTTokenProvider.java           # Gestion JWT
│   │   │       └── UserPrincipal.java
│   │   └── resources/
│   │       ├── application.yml                     # Configuration
│   │       ├── static/                             # Fichiers statiques
│   │       └── templates/
│   └── test/
│       └── java/                                   # Tests unitaires
├── pom.xml                                         # Configuration Maven
└── README.md                                       # Ce fichier
```

## Authentification JWT

### Flux d'authentification

1. **Inscription/Connexion**
   - L'utilisateur envoie ses identifiants
   - Le serveur valide et génère un JWT
   - Le token est retourné au client

2. **Utilisation du token**
   - Le client inclut le token dans l'en-tête `Authorization: Bearer <token>`
   - Le filtre `JwtAuthFilter` valide le token
   - Si valide, la requête est traitée normalement
   - Si invalide/expiré, une erreur 401 est retournée

### Structure du JWT

Les tokens JWT incluent :
- **Header** : Type et algorithme
- **Payload** : ID utilisateur, email, permissions
- **Signature** : Signée avec la clé secrète

### Expiration

- Les tokens ont une durée de validité configurée par `JWT_EXPIRATION`
- Une fois expiré, l'utilisateur doit se reconnecter
- Pas de mécanisme de "refresh token" (simple JWT)

## Réponses d'erreur communes

### 400 Bad Request
```json
{
  "error": "Bad Request",
  "message": "Description détaillée de l'erreur",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "Une erreur serveur est survenue",
  "timestamp": "2024-07-17T10:30:00Z"
}
```

## Tests

Exécuter la suite de tests :

```bash
mvn test
```

## Ressources supplémentaires

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io) - Comprendre les JWT
- [SpringDoc OpenAPI](https://springdoc.org)

## Support

Pour toute question ou support technique, contactez l'équipe de support :
- Email : support-technique@email.fr

## Licence

Ce projet est propriétaire - Openclassroom 2024

---

**Dernière mise à jour** : 17 Juillet 2024  
**Version API** : 1.0.0  
**Statut** : En développement
