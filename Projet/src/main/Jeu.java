/** package principal */
package main;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import librairies.AssociationTouches;
import librairies.StdDraw;
import ressources.Config;
import ressources.ParseurCartes;
import ressources.Terrain.*;
import ressources.Affichage;
import ressources.Chemins;
import ressources.Unite.*;

public class Jeu {
	/**
	 * tableau de tableau qui contien l'ensemble des case de type Terrain
	 */
	Terrain[][] mapTerrains;
	/**
	 * position actuel du curseur
	 */
	private int[]indexPos={0,0};
	/**
	 * tableau qui sert a la sauvegarde des position des joueur
	 */
	private int[][]indexPosJ={{0,0},{0,0}};
	
	/**
	 * indice du joueur actuel | 1 = rouge, 2 = bleu
	 */
	public int indexJoueurActif; //l'indice du joueur actif:  1 = rouge, 2 = bleu
	// l'indice 0 est reserve au neutre, qui ne joue pas mais peut posseder des proprietes

	/**
	 * Arrays qui contient le chemin que l'unite veut emprunter
	 */
	private ArrayList<int[]> cheminUnit= new ArrayList<int[]>();
	/**
	 * vrais si le joeur est en train de deplace une unite
	 */
	private boolean unitSelect;
	/**
	 * ensemble des unite du joueur 1
	 */
	private ArrayList<unite> Ujoueur1;
	/**
	 * ensemble des unite du joueur 2
	 */
	private ArrayList<unite> Ujoueur2;
	/**
	 * unite actuelement selectionne
	 */
	private unite select;
	/**
	 * nombres de pas restant a l'unite selectionne pour ce deplace
	 */
	private int cmpPas;
	/**
	 * ensemble des case qui peut etre attaque par l'unite selectionne
	 */
	private ArrayList<int[]> PeuvEtreAtta= new ArrayList<int[]>();
	/**
	 * vrais si l'on attend la case a attaque
	 */
	private boolean enAtta=false;
	/**
	 * argent du joueur 1
	 */
	private int monnaisJ1=0;
	/**
	 * argent du joueur 2
	 */
	private int monnaisJ2=0;
	/**
	 * vrais si le QG de l'un des joueur est capture
	 */
	private boolean fini=false;

	
	
	
	/**
	 * Constructeur
	 */
	public Jeu(String fileName) throws Exception {
		//appel au parseur, qui renvoie un tableau de String 
		String[][] carteString = ParseurCartes.parseCarte(fileName);
		mapTerrains=new Terrain[carteString.length][carteString[0].length];
		System.out.println(carteString);
		mapTerrains = new Terrain[carteString.length][carteString[0].length];
		Ujoueur1=new ArrayList<unite>();
		Ujoueur2=new ArrayList<unite>();
		for (int i = 0; i<carteString.length; i++) {
			for (int j=0; j < carteString[0].length; j++){
				String[] s =carteString[i][j].split(";");
				
				mapTerrains[i][j]=  switch (s[0]) {
					case "Plaine" 	->	new Plaine();
					case "Foret" 	->	new Foret();
					case "Montagne"	-> 	new Montagne();
					case "Eau"		->	new Eau();
					default ->{
						String[] temp = s[0].split(":");
						yield switch (temp[0]) {
							case "Ville"	->	new Ville(Integer.valueOf(temp[1]));
							case "QG" 		-> 	new QG(Integer.valueOf(temp[1]));
							default 		-> 	new Usine(Integer.valueOf(temp[1]));
						};
					}
				};

				
			if(s.length==2){
				String[] temp = s[1].split(":");
				
				mapTerrains[i][j].setUnit(switch (temp[0]) {
					case "Infanterie"	->	new Infanterie(Integer.valueOf(temp[1]));
					case "Bazooka"		->	new Bazooka(Integer.valueOf(temp[1]));
					case "Artillerie"	->	new Artillerie(Integer.valueOf(temp[1]));
					case "Convoi"		->	new Convoi(Integer.valueOf(temp[1]));
					case "Bombardier"	->	new Bombardier(Integer.valueOf(temp[1]));
					case "DCA" 			->	new DCA(Integer.valueOf(temp[1]));
					case "Tank"			->	new Tank(Integer.valueOf(temp[1]));
					default 			-> 	new Helicoptere(Integer.valueOf(temp[1]));
				}
				);
				if(temp[1].equals("1")) Ujoueur1.add(mapTerrains[i][j].act);
				else Ujoueur2.add(mapTerrains[i][j].act);
				
			}
			}
		}	
		// a vous de manipuler ce tableau de String pour le transformer en une carte avec vos propres classes, a l'aide de la methode split de la classe String


		Config.setDimension(carteString[0].length, carteString.length);
		// initialise la configuration avec la longueur de la carte
		
		indexJoueurActif = 1; // rouge commence
		debutTour(indexJoueurActif);
	}

	public boolean isOver() {
		return fini;
	}

	/**
	 * Permet l'affichage des principal information
	 */
	public void afficheStatutJeu() {
		Affichage.videZoneTexte();
		String InfoCase,InfoApp="",Action;
		if(mapTerrains[indexPos[1]][indexPos[0]].act!=null){
			InfoCase=mapTerrains[indexPos[1]][indexPos[0]].act.toString();
			InfoApp = "Appartient à :"+mapTerrains[indexPos[1]][indexPos[0]].act.app;
		}
		else{
			InfoCase=mapTerrains[indexPos[1]][indexPos[0]].toString();
			if(mapTerrains[indexPos[1]][indexPos[0]].appart()!=-1){
				InfoApp= "Appartient à :"+mapTerrains[indexPos[1]][indexPos[0]].appart();
			}
		}
		if(enAtta)Action="En attaque";
		else if(unitSelect)Action="En deplacement";
		else Action ="En attente de selection";

		Affichage.afficheTexteInfo("J1: "+monnaisJ1,"J2: "+monnaisJ2,"Au tour du joueur n°"+indexJoueurActif,InfoCase,InfoApp,Action);
		}


	/**
	 * Fontion qui affiche mapTerrain
	 */
	public void display() {
		StdDraw.clear();
		afficheStatutJeu();
		for (int i = 0; i<mapTerrains.length; i++) {
			for (int j=0; j < mapTerrains[0].length; j++){
				Affichage.dessineImageDansCase(j, i, mapTerrains[i][j].getChemin(enAtta&&estPresent(PeuvEtreAtta, new int[]{j,i})));
				if(mapTerrains[i][j].act!=null){
					if(!(mapTerrains[i][j].act.pv>0))mapTerrains[i][j].act=null;
					else Affichage.dessineImageDansCase(j, i, mapTerrains[i][j].act.getChemin());
				}

			}
		}
		//Fontion d'affichage des fleches
		for (int i = 0; i < cheminUnit.size(); i++) {
			int x1 = cheminUnit.get(i)[0];
			int y1 = cheminUnit.get(i)[1];
			String direction = "";
			String direction2 = "";
			if (i > 0) {
				int x2 = cheminUnit.get(i - 1)[0];
				int y2 = cheminUnit.get(i - 1)[1];
				if (x2 < x1) {
					direction = Chemins.DIRECTION_GAUCHE;
				} else if (x2 > x1) {
					direction = Chemins.DIRECTION_DROITE;
				} else if (y2 < y1) {
					direction = Chemins.DIRECTION_BAS;
				} else if (y2 > y1) {
					direction = Chemins.DIRECTION_HAUT;
				}
			}
			if (i < cheminUnit.size() - 1) {
				int x3 = cheminUnit.get(i + 1)[0];
				int y3 = cheminUnit.get(i + 1)[1];
				if (x1 < x3) {
					direction2 = Chemins.DIRECTION_DROITE;
				} else if (x1 > x3) {
					direction2 = Chemins.DIRECTION_GAUCHE;
				} else if (y1 < y3) {
					direction2 = Chemins.DIRECTION_HAUT;
				} else if (y1 > y3) {
					direction2 = Chemins.DIRECTION_BAS;
				}
			}
			if (i == 0) {
				direction = Chemins.DIRECTION_DEBUT;
			}
			if (i == cheminUnit.size() - 1) {
				direction2 = Chemins.DIRECTION_FIN;
			}
			Affichage.dessineImageDansCase(x1, y1, Chemins.getCheminFleche(direction,direction2));
		}
		

		
		
			
		


        //String arrowPath = Chemins.getCheminFleche(startDirection, endDirection);
		Affichage.dessineGrille(); //affiche une grille, mais n'affiche rien dans les cases		
		drawGameCursor();
		StdDraw.show(); //montre a l'ecran les changement demandes
	}

	public void initialDisplay() {
		StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide: tout draw est mis en buffer et ne s'affiche qu'au prochain StdDraw.show();
		display();
	}

	/**
	 * Fonction qui dessine le curseur
	 */
	public void drawGameCursor() {
		Affichage.dessineCurseur(indexPos[0],indexPos[1]); //affiche le curseau en (0,0), a modifier
	}

	
	/**
	 * Fonction qui effectue une action selon la touche presse par l'utilisateur
	 */
	public void update() {
		AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur
		
		
		
		if (toucheSuivante.isHaut()) {
			if(indexPos[1]<mapTerrains.length-1){
				if(unitSelect)
				{
					int[] futureCase= new int[]{indexPos[0],indexPos[1]+1};
					if(estPresent(cheminUnit,futureCase)){
						retourArriere(futureCase);
						indexPos[1]+=1;
					}
					else{
					Terrain futurTerrain =mapTerrains[indexPos[1]+1][indexPos[0]];
					if(futurTerrain.act==null&&cmpPas>0){
					switch (futurTerrain.getClass().getName()) {
						case "ressources.Terrain.Eau" : 
						if((select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							indexPos[1]+=1;
							cmpPas--;
							cheminUnit.add(indexPos.clone());
						}break;
						case "ressources.Terrain.Montagne":
						if((select.typeDeplacement==deplacement.Pied&&cmpPas>1)||(select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							if(select.typeDeplacement==deplacement.Aerien)cmpPas--;
							else cmpPas-=2;
							indexPos[1]+=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						case "ressources.Terrain.Foret":
						if((select.typeDeplacement!=deplacement.Chenille&&cmpPas>0)||cmpPas>1)
						{
							if(select.typeDeplacement!=deplacement.Chenille)cmpPas--;
							else cmpPas-=2;
							indexPos[1]+=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						default :
						if(cmpPas>0){
							indexPos[1]+=1;
							cheminUnit.add(indexPos.clone());
							cmpPas--;
						}break;
					}}}
				}
				else{
					indexPos[1]+=1;
				}
				
				System.out.println("Touche HAUT");
				display();
				
				
			}
			
			}



		if (toucheSuivante.isBas()){ 
			if(indexPos[1]>0){
				if(unitSelect){
					int[] futureCase= new int[]{indexPos[0],indexPos[1]-1};
					if(estPresent(cheminUnit,futureCase)){
						retourArriere(futureCase);
						indexPos[1]-=1;
					}
					else{
				Terrain futurTerrain =mapTerrains[indexPos[1]-1][indexPos[0]];
				if(futurTerrain.act==null&&cmpPas>0){
				switch (futurTerrain.getClass().getName()) {
					case "ressources.Terrain.Eau" : 
					if((select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
						indexPos[1]-=1;
						cmpPas--;
						cheminUnit.add(indexPos.clone());
					}break;
					case "ressources.Terrain.Montagne":
					if((select.typeDeplacement==deplacement.Pied&&cmpPas>1)||(select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
						if(select.typeDeplacement==deplacement.Aerien)cmpPas--;
						else cmpPas-=2;
						indexPos[1]-=1;
						cheminUnit.add(indexPos.clone());
					}
					break;
					case "ressources.Terrain.Foret":
					if((select.typeDeplacement!=deplacement.Chenille&&cmpPas>0)||cmpPas>1)
					{
						if(select.typeDeplacement!=deplacement.Chenille)cmpPas--;
						else cmpPas-=2;
						indexPos[1]-=1;
						cheminUnit.add(indexPos.clone());
					}
					break;
					default :
					if(cmpPas>0){
						indexPos[1]-=1;
						cheminUnit.add(indexPos.clone());
						cmpPas--;
					}break;
				}}}
			}else{
				indexPos[1]-=1;
			}
				System.out.println("Touche BAS");
				display();
			}
			
		}
		
		
		
		
		if (toucheSuivante.isGauche()) {
			if(indexPos[0]>0){
				if(unitSelect){
					int[] futureCase= new int[]{indexPos[0]-1,indexPos[1]};
					if(estPresent(cheminUnit,futureCase)){
						retourArriere(futureCase);
						indexPos[0]-=1;
					}
					else{
					Terrain futurTerrain =mapTerrains[indexPos[1]][indexPos[0]-1];
					if(futurTerrain.act==null&&cmpPas>0){
					switch (futurTerrain.getClass().getName()) {
						case "ressources.Terrain.Eau" : 
						if((select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							indexPos[0]-=1;
							cmpPas--;
							cheminUnit.add(indexPos.clone());
						}break;
						case "ressources.Terrain.Montagne":
						if((select.typeDeplacement==deplacement.Pied&&cmpPas>1)||(select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							if(select.typeDeplacement==deplacement.Aerien)cmpPas--;
							else cmpPas-=2;
							indexPos[0]-=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						case "ressources.Terrain.Foret":
						if((select.typeDeplacement!=deplacement.Chenille&&cmpPas>0)||cmpPas>1)
						{
							if(select.typeDeplacement!=deplacement.Chenille)cmpPas--;
							else cmpPas-=2;
							indexPos[0]-=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						default :
						if(cmpPas>0){
							indexPos[0]-=1;
							cheminUnit.add(indexPos.clone());
							cmpPas--;
						}break;
					}}}
				}else{
					indexPos[0]-=1;
				}
			System.out.println("Touche GAUCHE");
			display();}
		}
		
		
		
		
		
		if 	(toucheSuivante.isDroite()) { 
			if(indexPos[0]<mapTerrains[0].length-1){
				if(unitSelect){
					int[] futureCase= new int[]{indexPos[0]+1,indexPos[1]};
					if(estPresent(cheminUnit,futureCase)){
						retourArriere(futureCase);
						indexPos[0]+=1;
					}
					else{
					Terrain futurTerrain =mapTerrains[indexPos[1]][indexPos[0]+1];
					if(futurTerrain.act==null&&cmpPas>0){
					switch (futurTerrain.getClass().getName()) {
						case "ressources.Terrain.Eau" : 
						if((select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							indexPos[0]+=1;
							cmpPas--;
							cheminUnit.add(indexPos.clone());
						}break;
						case "ressources.Terrain.Montagne":
						if((select.typeDeplacement==deplacement.Pied&&cmpPas>1)||(select.typeDeplacement==deplacement.Aerien&&cmpPas>0)){
							if(select.typeDeplacement==deplacement.Aerien)cmpPas--;
							else cmpPas-=2;
							indexPos[0]+=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						case "ressources.Terrain.Foret":
						if((select.typeDeplacement!=deplacement.Chenille&&cmpPas>0)||cmpPas>1)
						{
							if(select.typeDeplacement!=deplacement.Chenille)cmpPas--;
							else cmpPas-=2;
							indexPos[0]+=1;
							cheminUnit.add(indexPos.clone());
						}
						break;
						default :
						if(cmpPas>0){
							indexPos[0]+=1;
							cheminUnit.add(indexPos.clone());
							cmpPas--;
						}break;
					}}}
				}else{
					indexPos[0]+=1;
				}
			System.out.println("Touche DROITE");
			display();}
		}

		if(toucheSuivante.isEntree()){
			System.out.println("Touche Entree");
			Terrain selectionne=mapTerrains[indexPos[1]][indexPos[0]];
			if(selectionne.act!=null&&selectionne.act.app==indexJoueurActif&&!unitSelect&&selectionne.act.dispo&&!enAtta){
				cheminUnit.add(indexPos.clone());
				unitSelect=true;
				select=selectionne.act;
				cmpPas = select.nbPas;
			}
			
			else if(unitSelect){
				String[] options = {"Oui", "Non"};
				String[] options2 ;
				if(Affichage.popup("Déplacee la troupe ici?", options, true, 1)==0){ 
					if(peutCap(select, cheminUnit.get(cheminUnit.size()-1))){
						options2 = new String[]{"Attendre","Attaque","Capture"};
					}else{
						options2 = new String[] {"Attendre","Attaque"};
					}
					int rep2 =Affichage.popup("Que voulez-vous faire ?", options2, true, 1);
					if(rep2!=-1){
						int[] coo=cheminUnit.get(cheminUnit.size()-1);
						mapTerrains[coo[1]][coo[0]].act=select;
						if(cheminUnit.size()!=1)mapTerrains[cheminUnit.get(0)[1]][cheminUnit.get(0)[0]].act=null;
						select.FinDeTour();
						if(rep2==1){
							PeuvEtreAtta=peutAttaquee(indexPos);
							enAtta=true;
							select=selectionne.act;
							System.out.println(PeuvEtreAtta);
						}
						else if(rep2==2){
							fini=mapTerrains[coo[1]][coo[0]].capture(select);
						}
				}
				}
				unitSelect=false;
				if(!enAtta)select=null;
				cheminUnit.clear();
				
			}else if(enAtta&&estPresent(PeuvEtreAtta,indexPos)){
				if(selectionne.act!=null&&selectionne.act.app!=indexJoueurActif&&select.peutAttaque(selectionne.act)){
					select.attaquer(selectionne.act, select.porteMax>1);
				}
					PeuvEtreAtta.clear();
					enAtta=false;
				
			}

			//si Usine
			else if(selectionne.act==null&&selectionne.getClass()==Usine.class&&selectionne.appart()==indexJoueurActif){
				ArrayList<Class<unite>> listeUni=getSubclasses("ressources.Unite", unite.class);
				String[] LUniStr=new String[listeUni.size()];
				Class<unite> u;
				String PhraseArgent="Vous avez :";
				if(indexJoueurActif==1)PhraseArgent+=monnaisJ1;
				else PhraseArgent+=monnaisJ2;
				
				
				for( int i =0 ; i<listeUni.size();i++){
					u=listeUni.get(i);
					unite instance = null;
					try {
						instance = u.getConstructor(int.class).newInstance(indexJoueurActif);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					LUniStr[i]=u.getSimpleName()+" pour "+instance.getPrix();
				}
				int voulu=Affichage.popup(PhraseArgent, LUniStr, true, 0);
				if(voulu!=-1){
				unite instance=null;
				try {
					instance = listeUni.get(voulu).getConstructor(int.class).newInstance(indexJoueurActif);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				if(instance != null){
					if(indexJoueurActif==1&&instance.getPrix()<=monnaisJ1){
						selectionne.setUnit(instance);
						monnaisJ1-=instance.getPrix();
						Ujoueur1.add(instance);
					}
					else if (indexJoueurActif==2&&instance.getPrix()<=monnaisJ2){
						selectionne.setUnit(instance);
						monnaisJ2-=instance.getPrix();
						Ujoueur2.add(instance);
					}
				}}
				
			}
			display();
		}
		
		//  ATTENTION ! si vous voulez detecter d'autres touches que 't',
		//  vous devez les ajouter au tableau Config.TOUCHES_PERTINENTES_CARACTERES
		if (toucheSuivante.isCaractere('t')) {
			String[] options = {"Oui", "Non"};
			if (Affichage.popup("Finir le tour du joueur n°"+indexJoueurActif+ " ?", options, true, 1) == 0) {
				//le choix 0, "Oui", a été selectionné
				FinTour(indexJoueurActif);
				AjoutArgentVerifCap();
				unitSelect=false;
				enAtta=false;
				cheminUnit.clear();
				PeuvEtreAtta.clear();
				indexPosJ[indexJoueurActif-1]=indexPos.clone();
				indexJoueurActif=indexJoueurActif%2+1;
				indexPos=indexPosJ[indexJoueurActif-1].clone();
				debutTour(indexJoueurActif);
				System.out.println("FIN DE TOUR");
			}
			
			display();
		}
	}
	
	/**
	 * 	@param a Entier qui represente le joueur dont le tour fini
	 * 	@note  Met toutes les unité du joueur a en non disponible
	 */
	private void FinTour(int a){
		if(a==1&&Ujoueur1.size()!=0){
			Ujoueur1.forEach((x)->x.FinDeTour());
		}
		else if(Ujoueur2.size()!=0){
			Ujoueur2.forEach((x)->x.FinDeTour());
		}
	}
	/**
	 * 	@param a Entier qui represente le joueur dont le tour commence
	 * 	@note  Met toutes les unité du joueur a en disponible
	 */
	private void debutTour(int a){
		if(a==1&&Ujoueur1.size()!=0){
			Ujoueur1.forEach((x)->x.NouveauTours());
		}
		else if(Ujoueur2.size()!=0){
			Ujoueur2.forEach((x)->x.NouveauTours());
		}
	}

	/**
	 * @param verif List a verfier
	 * @param pos3 Element a rechercher
	 * @return vrais si l'on trouve pos3 recherche dans verif
	 */
	private boolean estPresent(ArrayList<int[]> verif,int[]pos3){
		return verif.stream().anyMatch(a -> Arrays.equals(a, pos3));
	}

	/**
	 * @param pos3 Possition a la quel l'on souhaite retourne
	 */
	private void retourArriere(int[] pos3){
		int[] rem;
		int i= cheminUnit.size()-1;
		while(!Arrays.equals(cheminUnit.get(i),pos3)){
			rem =cheminUnit.remove(i);
			i--;
			switch (mapTerrains[rem[1]][rem[0]].getClass().getName()) {
				case "ressources.Terrain.Eau" : 
				cmpPas++;
				break;
				case "ressources.Terrain.Montagne":
				if(select.typeDeplacement==deplacement.Aerien)cmpPas++;
				else cmpPas+=2;
				break;
				case "ressources.Terrain.Foret":
				if(select.typeDeplacement==deplacement.Chenille)cmpPas+=2;
				else cmpPas+=1;
				break;
				default :
				cmpPas++;
				break;
			}
		}
	}
	/**
	 * @param coo Coordonner d'ou l'on suhaite attaque
	 * @return Les case qui peuvent etre attaque par l'unité sur la case coo
	 */
	private ArrayList<int[]> peutAttaquee(int[]coo){
		ArrayList<int[]> rep= new ArrayList<int[]>();
		int MaxAtta=mapTerrains[coo[1]][coo[0]].act.porteMax;
		int MinAtta=mapTerrains[coo[1]][coo[0]].act.porteMin;
		for(int i =0;i<=MaxAtta;i++){
			for(int j =0;j<=MaxAtta;j++){
				System.out.println(i+" "+j);
				
				if((i+j>=MinAtta&&i+j<=MaxAtta)){
					if(i==0){
						rep.add(new int[]{coo[0],coo[1]+j});
						rep.add(new int[]{coo[0],coo[1]-j});

					}
					else if(j==0){
						rep.add(new int[]{coo[0]+i,coo[1]});
						rep.add(new int[]{coo[0]-i,coo[1]});
					}else{
						rep.add(new int[]{coo[0]+i,coo[1]+j});
						rep.add(new int[]{coo[0]-i,coo[1]-j});
						rep.add(new int[]{coo[0]+i,coo[1]-j});
						rep.add(new int[]{coo[0]-i,coo[1]+j});

					}
					
				}
			}
		}
		return rep;
	}

	/**
	 * Ajoute l'argent au joeur qui fini sont tours
	 * / verifie que tous les batiement sont toujours en capture 
	 */
	public void AjoutArgentVerifCap(){
		for (int i = 0; i<mapTerrains.length; i++) {
			for (int j=0; j < mapTerrains[0].length; j++){
				if(mapTerrains[i][j].appart()==indexJoueurActif){
					if(indexJoueurActif==1)monnaisJ1+=1000;
					else monnaisJ2+=1000;
				}
				if(mapTerrains[i][j].act==null||peutCap(mapTerrains[i][j].act, new int[]{j,i})){
					mapTerrains[i][j].RemiseAzero();
				}
				
			}
		}
	}

	/**
	 * @param u unite qui souhaite capture
	 * @param coo Case a capture
	 * @return true si u  peut capture la case coo
	 */
	public boolean peutCap(unite u, int[] coo){
		if(u==null)return false;
		boolean aPied=u.typeDeplacement==deplacement.Pied;
		boolean estPropEnem=mapTerrains[coo[1]][coo[0]].getClass()==QG.class||mapTerrains[coo[1]][coo[0]].getClass()==Ville.class||mapTerrains[coo[1]][coo[0]].getClass()==Usine.class;
		if(estPropEnem){
			Propriete t =(Propriete) mapTerrains[coo[1]][coo[0]];
			return t.appartient!=indexJoueurActif&&aPied;
		}
		return false;
	}

	/**
	 * @param <A> Type de la class mere
	 * @param packageName Dossier ou se trouve la class mere
	 * @param parentClass Class mere
	 * @return L'ensemble des class fille de parentClass contenue dans packageName
	 */
	public static <A> ArrayList<Class<A>> getSubclasses(String packageName, Class<A> parentClass) {
		ArrayList<Class<A>> subclasses = new ArrayList<>();
		String packagePath = packageName.replace(".", "/");
		URL packageURL = ClassLoader.getSystemClassLoader().getResource(packagePath);
		if (packageURL != null) {
			File packageDir = new File(packageURL.getFile());
			if (packageDir.exists() && packageDir.isDirectory()) {
				for (File file : packageDir.listFiles()) {
					if (file.isFile() && file.getName().endsWith(".class")) {
						String className = file.getName().substring(0, file.getName().length() - 6);
						try {
							Class<?> cls = Class.forName(packageName + "." + className);
							if (parentClass.isAssignableFrom(cls) && !parentClass.equals(cls)) {
								subclasses.add((Class<A>) cls);
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return subclasses;
	}
	

}