
# Planning Poker – Application Web

## Introduction
Ce projet de classe a été réalisé dans le cadre de la gestion de projet agile.  
Il permet de simuler des sessions de Planning Poker pour estimer des tâches de backlog en équipe.  
**Auteurs :** Serigne Saliou Thiam et Khadim Ngom – Master 1 Informatique

---

## Description
Planning Poker est une application web permettant de réaliser des estimations de tâches (backlog) en équipe selon la méthode Planning Poker.  
L'application gère différents modes d'estimation et permet à un Scrum Master de contrôler la révélation des votes et le passage à la fonctionnalité suivante.

---

## Fonctionnalités principales

1. **Création de session**  
   - Accéder à : [http://localhost:8081/create.html](http://localhost:8081/create.html)  
   - Créer une session en entrant un nom et en uploadant un backlog (JSON ou CSV).  
   - Par défaut, le mode d'estimation est **STRICT**.  
   - Après création, un code de session est généré et un bouton "Aller à la session" apparaît pour le Scrum Master.  
   - Possibilité de **restaurer une session** en uploadant un fichier JSON précédemment sauvegardé.

2. **Rejoindre une session**  
   - Accéder à : [http://localhost:8081/join](http://localhost:8081/join)  
   - Entrer le code de session et votre pseudo pour rejoindre la session.  
   - Tous les votes sont synchronisés en temps réel.

3. **Estimation des fonctionnalités (jeu)**  
   - Les participants votent pour chaque fonctionnalité avec des valeurs prédéfinies.  
   - Les votes sont masqués jusqu'à ce que le Scrum Master révèle le résultat.  
   - Deux modes sont disponibles :
     - **STRICT** : tous les votes doivent être égaux pour valider l'estimation.
     - **AVERAGE** : la moyenne arrondie des votes est calculée comme résultat.
   - Le Scrum Master peut passer à la fonctionnalité suivante uniquement si les conditions du mode sont respectées.

4. **Pause café / sauvegarde**  
   - Cliquer sur le bouton **☕ Café** permet de télécharger un fichier JSON contenant l'état complet de la session (votes, participants, backlog, index courant).  
   - Ce fichier peut être utilisé ultérieurement pour restaurer la session.

---

## Lancer l'application

1. Assurez-vous d'avoir Java et Maven installés.  
2. Compiler et lancer le projet :
   ```bash
   mvn clean install
   mvn spring-boot:run


3. Accéder à l'application via les URLs suivantes :

   * Création/restauration de session : [http://localhost:8081/create.html](http://localhost:8081/create.html)
   * Rejoindre une session : [http://localhost:8081/join](http://localhost:8081/join)

---

## Tests unitaires

Pour lancer les tests unitaires du backend (SessionService, etc.) :

```bash
mvn test
```

* Les tests vérifient le comportement des méthodes principales comme :

  * `setVote()`
  * `nextFeature()`
  * `generateCode()`
* Les résultats des tests s'affichent dans la console.

---

## Documentation Javadoc

* La documentation Javadoc est générée automatiquement depuis le code source.
* Pour générer la Javadoc :

```bash
mvn javadoc:javadoc
```

* Les fichiers se trouvent ensuite dans le répertoire :

```
target/site/apidocs/index.html
```

* Ouvrir `index.html` dans un navigateur pour naviguer dans la documentation.

---

## Structure des fichiers

* `src/main/java/com/example/ppocker/controller` : contrôleurs REST et WebSocket
* `src/main/java/com/example/ppocker/service` : logique métier (SessionService)
* `src/main/java/com/example/ppocker/model` : classes modèle (Session, Player, Vote, PlanningMode)
* `src/main/java/com/example/ppocker/dto` : objets de transfert de données (CreateSessionRequest/Response, JoinRequest)
* `src/main/resources/static` : pages HTML frontend (`create.html`, `join.html`, `game.html`)

---

## Notes supplémentaires

* Les votes sont synchronisés en temps réel grâce à WebSocket (STOMP + SockJS).
* Dans le mode **STRICT**, le bouton "Next" est désactivé tant que les votes ne sont pas tous égaux.
* Le fichier JSON téléchargé via le bouton Café contient toutes les informations nécessaires pour restaurer une session complète.

---

## License

* Ce projet est fourni à des fins éducatives et peut être adapté selon vos besoins.


