# Mise en place de l'architecture

## Installation de la VM

L'installation de la VM se réalise en suivant ces instructions : https://github.com/SergioSim/vagrant-projects/blob/staging/OracleDatabase/21.3.0/README.md.

Il est à noter que le clonage du projet peut modifier l'encodage des fin de ligne en format microsoft donc une fois le repertoire cloné il faut vérifier que les fichiers sont bien en LF et non CRLF en bas a droite sur l'éditeur visual studio code.

## Connection à distance à MongoDB

Pour se connecter à distance à mongoDB il faut d'abord modifier la configuration du serveur.

- lancer vagrant ssh dans le repertoire du projet : vagrant-projects/OracleDatabase/21.3.0
- modifier la configuration bindIp à 0.0.0.0 : sudo nano /etc/mongod.conf
```
# network interfaces
net:
  port: 27017
  bindIp: 0.0.0.0
```
- redémarrer le serveur : sudo service mongod restart
- ajouter l'interface réseau suivante dans virtual box (configuration>réseaux>Avancé>Redirection des ports)
![My Image](documentation/redirection%20mongoDB.png)

Le serveur mongoDB est accessible sur la machine hôte avec la chaine de connection suivante : mongodb://localhost:27018/