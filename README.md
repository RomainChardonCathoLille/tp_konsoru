# Projet ckonsoru

## Modifications apportées - RENDU 1
Pour cette première partie du TP, j'ai décidé de scinder le programme 
en deux: j'ai fait le choix de créer deux nouvelles classes:
* App_Mode_SQL
* App_Mode_XML

Chaque classe est responsable d'un seul mode de fonctionnement: une 
classe pour le SQL, et l'autre pour le XML. Le main a été modifié
afin de faire en sorte d'appeller la classe correspondante
à ce qui est choisi dans le fichier de configuration (*config.properties*).

## Modifications apportées - RENDU 2 (18-04-2022)
Pour cette seconde partie du TP, j'ai décidé d'implémenter le design pattern DAO. Pour ce faire,
j'ai créé une interface **RendezVousDAO**. Et deux classes:
* SQL_RendezVousDAO
* XML_RendezVousDAO

Ces classes permettent de faire fonctionner le code en XML et en SQL (en fonction de ce qui est paramétré dans le fichier de
propriétés). Les deux classes ont les mêmes méthodes, avec les mêmes paramètres. Ainsi, on peut économiser un maximum de code
dans le main.
## Accès au projet
[Github](https://github.com/RomainChardonCathoLille/tp_konsoru)

## Auteur
[Romain Chardon](https://github.com/elromanov)