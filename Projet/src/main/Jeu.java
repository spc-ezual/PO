/** package principal */
package main;
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
	Terrain[][] mapTerrains;
	private int[]indexPos={0,0};
	private int indexJoueurActif; //l'indice du joueur actif:  1 = rouge, 2 = bleu
	// l'indice 0 est reserve au neutre, qui ne joue pas mais peut posseder des proprietes

	ArrayList<int[]> cheminUnit= new ArrayList<int[]>();
	boolean unitSelect;
	ArrayList<unite> Ujoueur1;
	ArrayList<unite> Ujoueur2;
	unite select;
	int cmpPas;
	
	
	
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
		return false;
	}

	public void afficheStatutJeu() {
		Affichage.videZoneTexte();
		Affichage.afficheTexteDescriptif("Au tour du joueur n°"+indexJoueurActif);
		}


	public void display() {
		StdDraw.clear();
		afficheStatutJeu();
		Affichage.dessineImageDansCase(1, 1, Chemins.getCheminTerrain(Chemins.FICHIER_FORET)); //exemple d'affichage d'une image de forêt dans la case (1,1)
		for (int i = 0; i<mapTerrains.length; i++) {
			for (int j=0; j < mapTerrains[0].length; j++){
			
				Affichage.dessineImageDansCase(j, i, mapTerrains[i][j].getChemin());
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

	public void drawGameCursor() {
		Affichage.dessineCurseur(indexPos[0],indexPos[1]); //affiche le curseau en (0,0), a modifier
	}

	public void update() {
		AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur
		
		
		
		if (toucheSuivante.isHaut()) {
			if(indexPos[1]<mapTerrains.length-1){
				if(unitSelect)
				{
					int[] futureCase= new int[]{indexPos[0],indexPos[1]+1};
					if(estPresent(futureCase)){
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
					if(estPresent(futureCase)){
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
					if(estPresent(futureCase)){
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
					if(estPresent(futureCase)){
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
			if(mapTerrains[indexPos[1]][indexPos[0]].act!=null&&mapTerrains[indexPos[1]][indexPos[0]].act.app==indexJoueurActif&&!unitSelect&&mapTerrains[indexPos[1]][indexPos[0]].act.dispo){
				cheminUnit.add(indexPos.clone());
				unitSelect=true;
				select=mapTerrains[indexPos[1]][indexPos[0]].act;
				cmpPas = select.nbPas;
				//System.out.println(mapTerrains[indexPos[1]][indexPos[0]].act);
			}else if(unitSelect){
				if(indexPos[0]!=cheminUnit.get(0)[0]||indexPos[1]!=cheminUnit.get(0)[1]){
					System.out.println(".....");
					mapTerrains[cheminUnit.get(cheminUnit.size()-1)[1]][cheminUnit.get(cheminUnit.size()-1)[0]].act=select;
					System.out.println(".....");
					mapTerrains[cheminUnit.get(0)[1]][cheminUnit.get(0)[0]].act=null;
					System.out.println(".....");
					select.FinDeTour();
				}
				unitSelect=false;
				select=null;
				cheminUnit.clear();
				
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
				unitSelect=false;
				cheminUnit.clear();
				indexJoueurActif=indexJoueurActif%2+1;
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

	private boolean estPresent(int[]pos3){
		return cheminUnit.stream().anyMatch(a -> Arrays.equals(a, pos3));
	}
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
}
