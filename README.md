# Suivie_requetes_clients


Nom du projet : Application de gestion et de traitement des requêtes clients


Description du projet :

Le projet vise à développer un système de gestion et le traitement des requêtes clients pour une entreprise spécialisée dans le développement des logiciels taillées pour les entreprises de toutes tailles. En effet, les clients de cette entreprise utilisent les logiciels déjà déployés dans leurs serveurs, et de temps en temps ces clients rencontrent differents besoins lors de l'utilisation de ces logiciels à savoir : des besoins liés à l'ajout ou la modification d'une nouvelle fonctionnalité, la correction des bugs, ou le traitement de données, et bien d'autres encore...

Après la soummisson des requêtes par les clients, l'application permet de les traiter de manière efficaces, tout en définissant des tâches spécifiques liées à ces requêtes et les affectent aux équipes de développeurs de l'entreprise. L'application permet au client de suivre aisemment l'état d'avancement de leurs requêtes en toute transparence. De plus, cette application permet également à l'entreprise de mieux organiser le traitement des requêtes à travers les tâches qui en découleront. Ces tâches seront attribuées aux développeurs directement dans l'application, et le product owner pourra facilement suivre l'évolution des tâches et pourra mieux évaluer les équipes de développeurs.

Les clients peuvent s'authentifier, et soumettre des requêtes via une interface utilisateur Angular, tout en de téléversant potentiellement certains fichiers afin de mieux spécifier l'objet de leurs requêtes. Ces requêtes seront traitées par un serveur Java Spring Boot, avec un base de données basée sur PostgreSQL.Le serveur sera responsable de la gestion des requêtes clients, de la communication avec la base de données et de la réponse aux requêtes des clients. Avec springSecurity, la gestion de l'authentification et des rôles des utilisateurs ont pu être définit au sein de l'application.


Composants du projet :


Interface utilisateur Angular : 
 -Une interface utilisateur uniquement reservée aux clients permettant aux clients de soumettre des requêtes tout en téléversant des fichiers via un formulaire en ligne. Les clients peuvent également consulter la liste et le statut de leurs requêtes précédentes;
 -Une interface utilisateur permettant à l'administrateur de créer un utilisateur tout en lui attribuant un rôle spécifique. L'administrateur peut également consulter la liste des utilistateurs de l'application tout en modifiant leur rôle;
 -Une interface utilisateur permettant aux utilisateurs possédant le rôle "collaborateur" ou "admin" de créer des tâches spécifiques aux requêtes. Lors de l'évolution du traitement de tâche, celle ci change de statut progressivement;
 -Une interface utilisateur permettant à l'utilisateur "admin" d'ajouter des commentaires dans le descriptif de la tâche affectée à un développeur, dans le cas où il n'a pas pris en compte certaines modifications;
 
Serveur Java Spring Boot : 
Un serveur qui gère :
 -L'authentification des utilisateurs et la sécurité de l'application en utilisant les JWT (Json Web Token);
 -Le serveur expose des API REST pour la communication avec l'interface utilisateur Angular.
 -Il gère également l'upload des fichiers.
 -La création, le traitement requêtes des clients et des tâches. La création et le traitement commentaires; 
 -Il communique avec la base de données basée sur PostgrSQL;

Base de données : Une base de données pour stocker les informations sur les requêtes clients, les tâches, des commentaires, et des fichiers etc.



Fonctionnalités du projet :

 -La création des utilisateurs : l'administrateur peut créer ou modifier un utilisateur tout en lui attribuant un rôle spécifique dans un formulaire en ligne à travers l'interface utilisateur Angular;
 -L'authentification des des utilisateurs;
 -Soumission de requêtes clients : Les clients peuvent soumettre des requêtes via l'interface utilisateur Angular en remplissant un formulaire en ligne. Les informations de la requête, telles que les détails de la requête et les informations du client, sont envoyées au serveur Java Spring Boot pour traitement;
 -La création des tâches : à partir d'une requette soumise par le client, l'administrateur peut alors créer une tache associée à cette requête dans le but de traiter celle ci. Lors de la création de la tâche, elle prend un status "Non planifiée" car elle n'a pas encore été affectée à un développeur;
 -Planifier une tâche : dans un formulaire en ligneà travers l'interface Angular, l'administrateur définit le développeur qui sera chargé de traiter la tâche, ainsi que les dates de début et fin prévisionnelles de la taches;
 -L'ajout des commentaire dans les taches : si le développeur n'a pas bien fait son travail, l'administrateur peut alors lui laissé un commentaire au niveau de sa tâche. Lors de la création d'un commentaire, celui détient un status "non traité";
-Gestion des requêtes clients : Le serveur Java Spring Boot gère les requêtes clients reçues en les stockant dans la base de données, en générant un identifiant de requête unique et en attribuant un statut initial à la requête.
-Communication avec la base de données : Le serveur Java Spring Boot communique avec la base de données pour stocker et récupérer les informations sur les requêtes clients, les tâches, des utilisateurs, et des commentaires. Il gère également les mises à jour du statut des requêtes, de à mesure qu'elles sont traitées.
-Réponse aux requêtes clients : Le serveur Java Spring Boot génère des réponses aux requêtes clients en fonction de leur statut et de leur progression dans le processus de traitement. Les réponses sont renvoyées à l'interface utilisateur Angular pour affichage aux clients.

