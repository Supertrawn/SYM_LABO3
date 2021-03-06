# SYM Laboratoire 3 - Utilisation de données environementales
Thomas Léchaire, Michael Brouchoud et Kevin Pradervand
22.11.2018

## Introduction
Dans ce laboratoire, il est demandé d'implémenter la prise en considération d'elément extérieurs ou environmentaux.
- Lecture de balise NFC
- Lecture de code barre
- Lecture de Balise iBeacon
- Utilisation des capteurs de l'appareil

## Utilisation NFC
Le bouton login est grisé par défaut, il faut l'activer en scannant une fois la balise NFC pour l'activer.
Login : heig
Mot de passe : sym2018

Une fois la zone sécurisée atteinte, il est possible d'augmenter le timer et le niveau de sécurité en scannant à nouveau le tag NFC.

## Questions
### 2.4 Balise NFC
Dans la manipulation ci-dessus, les tags NFC utilisés contiennent 4 valeurs textuelles codées en UTF-8 dans un format de message NDEF. 
Une personne malveillante ayant accès au porte-clés peut aisément copier les valeurs stockées dans celui-ci et les répliquer sur une autre puce NFC.

- A partir de l’API Android concernant les tags NFC4, pouvez-vous imaginer une autre approche pour rendre plus compliqué le clonage des tags NFC ?

```
Lors de la vérifiacation (lecture de la balise) on peut vérifier l'id du tag nfc et l'id constructeur du tag nfc, car chaque balise NFC contient un tag unique provenant du contructeur. Cela permettrait d'avoir une double vérification, certains système de contrôle utilisent cette manière pour empêcher le clonnage, mais cela n'empêche pas la lecture du tag.
```

- Est-ce possible sur toutes les plateformes (Android et iOS), existe-il des limitations ? 

```
Alors cela va dépendre des authorisations disponibles sur IOS et quels tag le lecteur nfc est capable de lire. Mais en imaginant qu'il possède les même capacités que sur android, cela est tout à fait faisable.
```

- Voyez-vous d’autres possibilités ?

```
Les tags peuvent contenir une clef asymétrique ou un forme d'encryption qui permet lors du scan de la clef ou de la balise NFC d'avoir une forme d'authentification (vérification) sur le tag nfc qui est lu. Cela offre une couche de protéction supplémentaire, mais il y a toujours moyen de récupérer la clef et d'effectuer un clonage. 

```

### 3.2 Code Barre
Comparer la technologie à codes-barres et la technologie NFC, du point de vue d'une utilisation dans des applications pour smartphones, dans une optique :
- Professionnelle (Authentification, droits d’accès, stockage d’une clé)
  - Difficile à implémenter avec un code-barre car le QR code ou le code-barre peut être accessible par tout le monde du moment qu'il le possède sous format papier. Ce qui est donc trop dangereux (possibilité de perdre le code, se le faire voler, etc...).
  - NFC est une bonne option, il ne faut pour autant ne pas perdre le badge. Si une authentification par login et mot de passe est ajoutée, le fait de perdre le badge n'est pas un problème car il faut encore avoir accès à ces informations pour accéder aux informations sensibles.
- Grand public (Billetterie, contrôle d’accès, e-paiement)
  - NFC utile pour les contrôle d'accès si couplé avec une authentification avec login et mot de passe, pareil pour e-paiement.
  - Technologie overkill pour ce qui est de la billetterie. Il vaut mieux privilégier un QR code ou un code-barre dans ce cas là.
- Ludique (Preuves d'achat, publicité, etc.)
  - Sans conteste, la technologie la plus adaptée ici est le code-barre / QR code car très simple d'utilisation. De plus le fait de perdre le code n'implique en principe aucunes conséquences car dans le cas de la preuve d'achat par exemple, le vendeur doit conserver les preuves de ventes et il est donc possible de retrouver la preuve d'achat, grâce au ticket de caisse.
- Financier (Coûts pour le déploiement de la technologie, possibilités de recyclage, etc.)
  - QR code / code-barre : coûts faible car possibilité d'imprimer sur papier ou de l'afficher sur l'écran d'un périphérique.
  - NFC : coût moyennement élevé lié à la technologie entre le matériel et les coûts de R&D à amortir.
### 4.2 Balise iBeacon
Les iBeacons sont très souvent présentés comme une alternative à NFC. Pouvez-vous commenter cette affirmation en vous basant sur 2-3 exemples de cas d’utilisations (use-cases) concrets (par exemple e-paiement, second facteur d’identification, accéder aux horaires à un arrêt de bus, etc...).

Etant donné la petit rayon d'action des iBeacon, il est donc possible de l'utiliser pour remplacer la technologie NFC par exemple pour le paiement, au lieu de passer son téléphone sur une borne NFC, il serait possible interagir avec le iBeacon pour effectuer le paiement sans effectuer de geste mais en interagissant directement avec une notification de demande de paiement sur le téléphone par exemple.

Il est donc possible d'effectuer la même démarche pour le second facteur d'identification et pour obtenir les horaires d'un arrêt de bus.

Cela présente biensûr un problème au niveau sécurité étant donné le rayon d'action plus grand on pourrait imaginer un hacker sniffer le réseau et récupérer des informations lors des échanges de données, lors d'un payement par exemple, puis répliquer le même flux avec les mêmes id et payer en utilisant des données clonées (sniffées)

### 4.4 Capteurs
Une fois la manipulation effectuée, vous constaterez que les animations de la flèche ne sont pas fluides, il va y avoir un tremblement plus ou moins important même si le téléphone ne bouge pas. Veuillez expliquer quelle est la cause la plus probable de ce tremblement et donner une manière (sans forcément l’implémenter) d’y remédier.

```
Selon nous, il s'agit d'un délai entre la récupération des données et l'affichage (repaint()) des flèches. Lors que la flèche reçoit les données du capteur, il les affiches, mais lorsque nous bougeons l'aimant, il y a un décalage entre l'affichage et le changement des données qui fait que les données "intermédiaire" (entre 2 déplacements de l'aimant) ne sont pas toutes prises en comptes et pas toutes affichées (mise à jour de la boussole).
Cela sans prendre en compte les autres sources de perturbations éléctromagnétiques ou des déplacements du smartphone.
```
