Information importante :
     La version Java utilisé pour se projet les la 18.0.2.1

Class créée :
     -    unite
               Artillerie
               Bazooka
               Bombardier
               Convoi
               DCA
               Helicoptere
               Infanterie
               Tank
               AChasse -> avion de chasse equipé de missile air air   avec 2 de porte
               Drone -> equipe de missile air air et d'une mitrailleuse lourde, mais subi beaucoup plus de degat que les autre unité
               APolyvalent -> avion polyvalent equipe d'un canon (lance roquette) et d'une mitrailleuse legere 

     -    armes (enum)
               MitraLeg
               Canon
               MitraLourde
               MissileAirSol
               Bombes
               Mortier
               MissileAirAir
     -    deplacement (enum)
               Pied
               Chenille
               Aerien
     -    Terrain 
               Eau
               Foret
               Montagne
               Plaine
               Propriete
                    QG
                    Usine
                    Ville
     Pour plus d'info aller voir dans class mere la documentation


     
modification apporte :
     -    Affichage 
               afficheTexteInfo -> affiche des Information importante tel que l'argent, le joueur actif, l'action actuel, ...
     -    Chemins
               Nouvelles variables
                    DOSSIER_ATTAQUE_TERRAINS -> Chemin vers le dossier qui contient les images des terrains si peut y avoir une attaque dessus
                    DOSSIER_ATTAQUE_BATIMENT -> Chemin vers le dossier qui contient les images des batiments si peut y avoir une attaque dessus
               getCheminTerrain -> nouvelle signature: ajout d'un boolean si la case peut etre attaque
               getCheminPropriete -> nouvelle signature: ajout d'un boolean si la case peut etre attaque
     -    main
               Ajout d'une popup de fin de partie qui demande si les joeur veullent rejoues ou voir la configuration de fin
     -    Jeu
               Nouvelles variables 
                    mapTerrain -> tableau de tableau qui contien l'ensemble des case de type Terrain
                    indexPos -> position actuel du curseur
                    indexPosJ -> tableau qui sert a la sauvegarde des position des joueur 
                    indexJoueurActif -> indice du joueur actuel
                    cheminUnit -> Arrays qui contient le chemin que l'unite veut emprunter
                    unitSelect -> vrais si le joeur est en train de deplace une unite
                    Ujoueur1 -> ensemble des unites du joueur 1
                    Ujoueur2 -> ensemble des unite du joueur 2
                    select -> unite actuelement selectionne
                    cmpPas -> nombres de pas restant a l'unite selectionne pour ce deplace
                    PeuvEtreAtta -> ensemble des case qui peut etre attaque par l'unite selectionne
                    enAtta -> vrais si l'on attend la case a attaque
                    monnaisJ1 -> argent du joueur 1
                    monnaisJ2 -> argent du joueur 2
                    fini -> vrais si le QG de l'un des joueur est capture
               nouvelle fonction
                    FinTour -> met toutes les unité d'un joeur en non disponible
                    debutTour -> met toutes les unite d'un joueur en disponible
                    estPresent -> vrais si l'on trouve l'element recherche dans l'arrayList
                    retourArriere -> permet de retourné a la case en entre et remet les pas qui avait ete utlise
                    peutAttaquee -> permet de recupere les case qui peut etre attaque par l'unite qui est au coordonne en entre
                    AjoutArgentVerifCap -> ajoute l'argent au joueur qui vien de finir sont tour et verifie que tous les batiement sont toujours en capture 
                    peutCap -> vrais si l'unite en entre peut capture la case en entre
                    getSubClasses -> retourne toute les sous class d'une class en entre en parcourant les fichier du meme repertoire 
                         -> import de URL et de file
               Fonction modifier
                    Constructeur :
                         -    Utilisation d'un switch pour créé les cases 
                         -    Utilsation de split succesif afin de de recupere les differentes partie (unite , type de terrain, ....)
                         -    Ajout des unite dans une arrayList pour facilité les mise en disponible (cf FinTour , debutTour)
                    isOver :
                         -    Retourn vrais si l'un des QG est capture
                    afficheStatutJeu :
                         -    Permet l'affichage des principal information (cf afficheTexteInfo)
                    display :
                         -    Double boucle sur mapTerrain pour affiche les terrain et les unites
                         -    Parcours des valeur dans cheminUnit pour affiche les fleches corespondante
                    drawGameCursor :
                         -    Affichge du curseur au coordonne dans indexPos
                    update :
                         -    Gauche / Droite / Haut/ Bas
                              -    Verification que l'on peut bien se deplace dans la direction demande
                              -    Si une unite est en train de se deplacé alors :
                                   -    Si l'on est deja passe par cette case l'on revien en arriere (cf retourArriere) et l'on se deplace
                                   -    Sinon l'on utilise un switch afin de savoir combien de poin le deplacement nous coute et si il est possible
                              -    Sinon l'on modifie juste indexPos
                         -    Entre
                              -    Si l'on ne fait rien l'on selection l'unite de la case pour un deplacement
                              -    Si l'on se deplacais :
                                   -    Demande une confirmation de deplacement
                                   -    L'on demande a l'utilisateur ce qu'il souhaite faire apres 
                                   -    Si l'on veut attendre l'unite est juste deplace
                                   -    Si l'on veut attaque l'unite est deplace,l'on affiche passe en mode attaque ( cf enAtta) et l'on affiche les case que l'on peut attaque (cf peutAttaquee)
                                   -    Si l'on veut capture l'unite est  deplace, lance une capture sur la case de l'unite (uniquement dispo si la case est une propriete cf peutCap)
                              -    Si l'on etais en attaque alors l'on attaque l'unite que est sur la case selectionne, si il n'y a pas d'unite sur la case cela annule l'attaque
                              -    Si l'on est sur une usine cela ouvre une popup qui montre les unites pouvant etre acheter et leur cout (cf getSubclasses) puis l'on verifie si le joeur a l'argent avant de la place
                         -    't'
                              -    Met fin au tours ( arrete les mode deplacement, attaque, ajoute l'argent,... ect) 



Bonus fait:
     -    Affichage des cases pouvant être attaqués
     -    Les armes multiple
     -    L'Artillerie et le mortier
     -    Recuperation de la derniere position du curseur du indexJoueurActif
     -    Unite aerienne suplementaire dont une a distance


By Foin MArcus
and Baudy Simon