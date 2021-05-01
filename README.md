# RES | Labo SMTP

> Gaétan Zwick
>
> Marco Maziero

## Description

Cette petite application permet d'envoyer de faux emails à une liste de personnes choisies. La liste de personnes fournies est séparée en groupes, puis dans chaque groupe, une personne est choisie au hasard pour être l'émetteur du faux mail, les autres sont les récepteurs. Une fois les groups formés, un mail est choisi au hasard parmi ceux fournis et un mail est envoyé à chaque groupe.

L'application ainsi qu'un faux serveur SMTP pour Docker peuvent être téléchargés depuis la section [Releases](https://github.com/Ga-3tan/Teaching-HEIGVD-RES-2021-Labo-SMTP/releases).

## Mise en place d'un "mock server" SMTP

La mise en place d'un mock server SMTP permet de tester l'envoi d'emails sur un "faux" serveur SMTP qui n'achemine pas les email. Ceci est donc sans conséquences et permet de vérifier que tout fonctionne avant d'initier un vrai envoi d'emails vers un vrai serveur SMTP.

Pour cette application un serveur MockMock est fourni et peut être utilisé avec Docker.

> Docker permet de lancer le faux serveur SMTP MockMock dans un environnement linux virtuel accessible depuis la machine hôte. Cela permet d'avoir un serveur SMTP et une interface web fournie par MockMock qui permet de visualiser les mails reçus

Pour le lancer, assurez-vous d'avoir bien installé Docker, puis exécutez les commandes suivantes dans un terminal **depuis le dossier ou se trouve le fichier `Dockerfile`**:

*Création de l'image docker*

```sh
docker build -t res/smtp .
```

*Lancement de l'image docker créée*

```sh
docker run -d -p 8989:8989 -p 25:25 res/smtp
```

> Les ports **25** et **8989** ont été mappés pour permettre l'accès à l'interface web de MockMock sur le port 8989 et l'envoi d'emails sur le port 25.

Une fois MockMock lancé, l'interface web est disponible à l'adresse [http://localhost:8989](http://localhost:8989) et des emails peuvent être envoyés sur le serveur SMTP `localhost:25`.

## Instructions pour l'utilisation du programme

1. Télécharger la dernière release dans la section [Releases](https://github.com/Ga-3tan/Teaching-HEIGVD-RES-2021-Labo-SMTP/releases)

2. Extraire le contenu de l'archive .zip

3. Dans le dossier `configuration` se trouvent 3 fichiers modifiables :

   - `config.properties` -> Contient les informations concernant la connexion au serveur SMTP ainsi que le nombre de groupes a effectuer et les personnes à mettre en copie lors des envois d'emails.

   - `emails.utf8` -> Contient la liste des emails des victimes (une adresse par ligne). Ces adresses sont divisés en groupes pour l'envoi des emails.

   - `messages.utf8` -> Contient les messages qui seront envoyés aux groupes. 

     ​	**/!\ Attention**

     - Chaque première ligne de message doit commencer par `Subject:` pour indiquer le nom du sujet du mail.
     - Chaque message DOIT se terminer par la ligne `====` pour être bien compris par le programme

## Implémentation

### Diagramme de classes (UML)

### Choix d'implémentation

#### La classe `ConfigurationManager`

Une question s'est posée au tout début du laboratoire : Lire l'intégralité des fichiers de configuration directement au début puis stocker les données dans la classe pour éviter de devoir relire les fichiers ou alors ne lire que ce qui est nécessaire. Finalement il a été choisi de ne lire que ce qui est nécessaire pour éviter des lectures inutiles de données.

La classe `ConfigurationManager` fournit donc des méthodes permettant la récupération des données contenues dans les fichiers de configuration.

#### La classe `PrankGenerator`

`PrankGenerator` possède une méthode `generatePranks()` qui va récupérer la liste des victimes, former les groupes, répartir les messages dans chaque groupe et former les `Prank` correspondants.

#### Les classes `Prank` et `Mail`

Les classes `Prank` et `Mail` sont très semblables et la question de n'en garder que une seule s'est posée. Cependant, il a été décidé de garder les deux car cela permet de détacher complètement le client SMTP et les Mails de l'aspect de "Pranks" du programme. Les classes `SmtpClient` et `Mail` pourraient être réutilisées dans une autre contexte sans problème.

#### Le client SMTP

Le client SMTP implémenté fournit une méthode `sendMail()` permettant l'envoi d'un email sur un serveur SMTP donnée au préalable.

**/!\ Attention**: Le client ne supporte pas l'envoi de mails avec TLS sur des serveurs SMTP utilisant des ports tels que le 587.

Des avertissements sont retournés à l'utilisateur si :

- Le client n'arrive pas à se connecter au serveur donné
- Le serveur retourne des codes SMTP non attendus ou inconnus

