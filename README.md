Nom du dépôt
firestation-management

Description
Ce projet est une API Java qui gère les informations relatives aux casernes de pompiers et aux résidents desservis. Il permet d'accéder à diverses informations sur les habitants, y compris les détails des enfants, des alertes téléphoniques, et des antécédents médicaux.

Fonctionnalités
Liste des résidents par caserne : Récupère les habitants couverts par une caserne de pompiers spécifique.
Alertes pour enfants : Retourne les informations des enfants vivant à une adresse donnée.
Alertes téléphoniques : Récupère les numéros de téléphone des résidents desservis par une caserne.
Informations sur les habitants : Retourne les informations complètes des habitants d'une adresse spécifique.
Gestion des inondations : Liste tous les foyers desservis par une ou plusieurs casernes.
Informations personnelles : Récupère les informations d'un habitant spécifique par son nom.
Emails communautaires : Retourne les adresses email de tous les habitants d'une ville.

API Endpoints

```bash
GET /firestation?stationNumber=<station_number>
GET /childAlert?address=<address>
GET /phoneAlert?firestation=<firestation_number>
GET /fire?address=<address>
GET /flood/stations?stations=<list_of_station_numbers>
GET /personinfo?firstName=<firstName>&lastName=<lastName>
GET /communityEmail?city=<city>
```
#Technologies utilisées : 
Java
Spring Boot
Maven

#Clonez le dépôt : 
```bash
git clone https://github.com/votre-utilisateur/firestation-management.git
```
Accédez au répertoire du projet 
```bash
cd firestation-management
```

Installez les dépendances 

mvn install

Exécution
mvn spring-boot:run
