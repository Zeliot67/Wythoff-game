/**
 * Permet de simuler une partie de Wythoff avec un ordinateur
 * 
 * Les méthodes de tests pour les void sont sous la forme de démonstrations, les autres sont sous la forme de méthodes de tests classiques
 * Le problème des doubles affichages lors des SimpleInput.getChar n'a malheuresement pas été réglé chez moi après l'utilisation du nouveau fichier SimpleInput.java
 * donc j'ai été contrain de réaliser l'affichage hors du SimpleInput.getChar
 * @author Eliot
 */
 
class Wythoff2{
	
	//Permet de donner un nom au joueur
	final String joueur = SimpleInput.getString("Donner votre nom : ");
	
	void principal(){
		
		// Permet de lancement d'une partie
		jouer();
	}
	
	/**
	* Créer un plateau de jeu carré rempli de carctere espace ' '
	* @param lg taille du plateau
	* @return tableau de caractère en deux dimensions
	*/
 
	char[][] creerPlateau(int lg){
		
		char[][] plateau = new char [lg][lg];
		
		// Permet la création d'un plateau avec des cases remplies de vide
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				plateau[i][j] = ' ';
			}
		}
		
		return plateau;
	}
	
	/**
	 * Affichage d'un plateau de Wythoff avec les indices de lignes
	 * et de colonnes
	 * @param plateau le tableau a afficher
	 */
 
	void affichePlateau (char[][] plateau){
		
		System.out.println(" ");
		
		// Affiche les indices des différentes lignes de façon inversée pour le jeu de Wythoff
		for (int i = 0; i < plateau.length; i++){
			System.out.print(-i + plateau.length - 1);
			
			// Affiche le plateau en séparant chaque cases avec "|" 
			for (int j = 0; j < plateau[i].length; j++){
				System.out.print(" | ");
				System.out.print(plateau[i][j]);
			}
		 
			System.out.print(" |");
			System.out.println(" ");
		}
		System.out.print (" ");
		
		// Affiche les indices de la longueur
		for (int k = 0; k < plateau.length; k++){
			System.out.print("   " + k );
			
		}
		
		// Espaces supplémentaires pour l'esthétique de l'affichage
		System.out.println(" ");
		System.out.println(" ");
	}
	
	/**
	 * Méthode permettant de placer le pion de façon aléatoire sur la première ligne et sur une colonne qui n'est pas gagnante en un coup
	 * @param plateau le plateau de jeu sur lequel on va placer le pion
	 */
	 
	void placePionAleatoire(char[][] plateau){
		
		int positionLigne;
		int positionColonne;
		
		// Permet de trouver une position aléatoire sur la première ligne
		do{
			positionLigne = 0;
			positionColonne = (int) (Math.random() * plateau.length - 1);
			
			// Permet d'empécher le placement sur la première colonne et sur la diagonale gagante
			}while(positionColonne == 0 || positionColonne == plateau.length - 1);
			
		// Positione le pion à l'endroit généré de manière aléatoire
		plateau[positionLigne][positionColonne] = 'O';
	}	
	
	/**
	 * Méthode permettant de connaitre l'emplacement du pion dans un plateau donné
	 * @param plateau le plateau dans lequel on cherche le pion
	 * @return renvoie un tableau contenant les coordonnées du pion dans le plateau
	 */
	 
	int [] positionPion(char[][] plateau){
		
		int indiceLigne = 0;
		int indiceColonne = 0;
		
		// Création du tableau qui va acceuillir les deux coordonnées
		int[] coordonneesPlateau = new int [2];  
		
		// Permet de chercher dans l'ensemble du tableau la position du pion
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				if(plateau[i][j] == 'O'){
					indiceLigne = i;
					indiceColonne = j;
				}
			}
		}
		
		// Stocke dans un tableau les coordonnées du pion du plateau
		coordonneesPlateau[0] = indiceLigne;
		coordonneesPlateau[1] = indiceColonne;
		return coordonneesPlateau;
	}	
	/**
	 * Methode permettant de faire réaliser à l'IA un déplacement aléatoire
	 * @param plateau le plateau dans lequel va être réalisé le déplacement
	 */
	
	void deplacementAleatoireIA(char[][] plateau){
		
		int [] coordonneesPion = positionPion(plateau);
		int positionX = coordonneesPion[1];
		int positionY = coordonneesPion[0];
		int rouletteDirection;
		int nbDeplacement = 0;
		char choixDirection = ' ';
		boolean deplacementRealisable = true;	
		
		// Permet la création d'un déplacement de l'IA de façon aléatoire
		do{
			
			// Va donner la direction de manière aléatoire en fonction du chiffre tiré, ici les chiffres seront 0 (pour gauche) 1 (pour bas) et 2 (pour diagonale)
			rouletteDirection = (int) (Math.random() * 3);
			
			if(rouletteDirection == 0){
				choixDirection = 'G';
				
				// Permet de donner un déplacment possible vers la gauche avec un (positionX - 1) pour éviter la première colonne du plateau et un + 1 pour éviter le cas 0 
				nbDeplacement = (int) (Math.random() * (positionX - 1) + 1);
			}
			if(rouletteDirection == 1){
				choixDirection = 'B';
				
				// Permet de donner un déplacement possible vers le bas avec les mêmes conditions que précédemment sauf qu'ici il faut modifier les indices pour être en accord avec le plateau d'un jeu de Wythoff
				nbDeplacement = (int) (Math.random() * ((plateau.length - 1) - (positionY - 1)) + 1);
			}
			if(rouletteDirection == 2){
				choixDirection = 'D';
				
				// Permet de donner un déplacement possible vers la diagonale avec les mêmes conditions que pour le déplacement vers la gauche
				nbDeplacement = (int) (Math.random() * (positionX - 1) + 1);
			}
			
			// Une sécurité qui permet de vérifier si le déplacement donné est bel et bien réalisable
			deplacementRealisable = verificationDeplacement(plateau, choixDirection, nbDeplacement);
		
		// Répétition du processus tant que le déplacement est impossible	
		}while(deplacementRealisable == false);
		
		// Avec cette direction et le nombre de déplacement on effectue le déplacement du pion
		deplacementPion(plateau, choixDirection, nbDeplacement);
	}
	
	/**
	 * Permet à l'IA de vérifier si une position gagante est à sa portée
	 * Si c'est le cas alors elle la rejoint
	 * @param plateau le plateau à analyser par L'IA
	 */
	 
	void analyseEtActionIA(char[][] plateau){
		
		int [] placementPion = positionPion(plateau);
		int placementX = placementPion[1];
		int placementY = placementPion[0];
		boolean positionGagnante = false;
		int k = placementY;
		int t = placementX;
		
		// Permet à l'IA de vérifier si une position gagnante est présente sur la gauche ou non
		for(int j = 0; j < placementX; j++){
				if(plateau[placementY][j] == 'X'){
					plateau[placementY][placementX] = ' ';   // Permet de mettre à vide la case précédente
					plateau[placementY][j] = 'O';
					positionGagnante = true;
			}
		}
		
		// Tant qu'une position gagnante n'aura pas été trouvée le processus continuera 
		if(positionGagnante == false){
			
			// Permet à l'IA de vérfier si une position gagnante est présente vers la bas	
			for(int i = plateau.length - 1; i > placementY; i--){
				if(plateau[i][placementX] == 'X'){
					plateau[placementY][placementX] = ' ';
					plateau[i][placementX] = 'O';
					positionGagnante = true;
				}
			}
		}
		
		// Tant qu'une position gagnante n'aura pas été trouvée le processus continuera
		if(positionGagnante == false){
			
			// Permet à l'IA de vérifier si une position gagnante est présente sur la diagonale avec k qui représente la position Y et t qui représente la position X
			while(k < plateau.length && t >= 0){
				if(plateau[k][t] == 'X'){
					plateau[placementY][placementX] = ' ';
					plateau[k][t] = 'O';
					positionGagnante = true;
				}
				t--;
				k++;
			}
		}
		
		// Si aucune position gagnante n'est présente dans le rayon d'action de l'IA, on réalise un déplacement aléatoire
		if(positionGagnante == false){
			deplacementAleatoireIA(plateau);
		}
	}
	
	/**
	 * Méthode permettant de deplacer le pion d'un nombre donnée de cases
	 * @param plateau le plateau ou sera effectué le déplacement
	 * @param direction la direction dans laquelle déplacer le pion
	 * @param nbDeplacement le nombre de déplacement souhaité dans cette direction 
	 */
	 
	void deplacementPion(char[][] plateau, char direction, int nbDeplacement){
		
		int[] placementPrecedent = positionPion(plateau);
		int coordonneeX = placementPrecedent[1];
		int coordonneeY = placementPrecedent[0];
		
		// Permet de mettre à vide la case de l'ancienne position du pion
		plateau[coordonneeY][coordonneeX] = ' ';
		
		// Permet de déplacer le pion vers la gauche n fois
		if(direction == 'G'){
			coordonneeX = coordonneeX - nbDeplacement;
		}
		// Permet de déplacer le pion vers le bas n fois
		if(direction == 'B'){
			coordonneeY = coordonneeY + nbDeplacement;
		}
		//Permet de déplacer le pion en diagonale n fois
		if(direction == 'D'){
			coordonneeX = coordonneeX - nbDeplacement;
			coordonneeY = coordonneeY + nbDeplacement;
		}
		
		// Place le pion sur la case voulue
		plateau[coordonneeY][coordonneeX] = 'O';  
	}	
		
	
	/**
	 * Methode permettant de savoir si un déplacement est possible ou non dans un platau
	 * @param plateau le plateau de jeu ou aura lieu la vérification
	 * @param direction la direction dans laquelle le deplacement veut être effectuée
	 * @param nbDeplacement le nombre de deplacement que souhaite faire le joueur 
	 * @return renvoie true si le mouvement est possible, false sinon
	 */
	 
	boolean verificationDeplacement(char[][] plateau, char direction, int nbDeplacement){
		
		boolean verification = true;
		
		// Appel de la methode "positionPion" pour pouvoir connaitre l'emplacement du pion et vérifier ensuite le déplacement 
		int[] emplacementPion = positionPion(plateau);  
		int emplacementX = emplacementPion[1];
		int emplacementY = emplacementPion[0];
		
		// Vérifie si le déplacement dans la direction G est possible ou non
		if(direction == 'G'){
			
			// Vérifie si le déplacement ne dépassse pas le plateau sur la gauche
			if(emplacementX - nbDeplacement < 0){  
				verification = false;
			}
		}
		
		// Verifie si le déplacement est possible vers le bas
		if(direction == 'B'){
			
			// Vérifie si le déplacement ne dépasse pas le plateau vers le bas
			if(emplacementY + nbDeplacement > plateau.length - 1){ 
				verification = false;
			}
		}
		
		// Verifie si le déplacement est possible en diagonale
		if(direction == 'D'){
			// Vérifie si le déplacment en diagonale ne sors pas du plateau
			if(emplacementY + nbDeplacement > plateau.length - 1  || emplacementX - nbDeplacement < 0){ 
				verification = false;
			}
		}
		return verification;
	}	
	
			
	/**
	 * Methode permettant de calculer le nombre d'or.
	 * @param nombre le nombre d'opération du nombre d'or a réaliser.
	 * @return la valeur du nombre d'or calculée 
	 */
	 
	double calculNombreDOr (int nombre){
		
        double mathsPhi = 1;
        int i = 0;
        
        // Permet de calculer le nombre d'or 
        while (i != nombre){
            mathsPhi = 1 + (1/mathsPhi);
            i++;
        }
		return mathsPhi;
    }
    
	/**
	 * Methode permettant d'afficher toute les positions gagnantes sur le plateau d'un jeu de Wythoff.
	 * @param le plateau sur lequel on veut placer les positions gagnantes
	 * @return le nouveau plateau avec les positions gagantes indiquées dessus.
	 */
	 
	char [][] affichagePositionGagnante (char [][] plateau){ 

        char signePositionGagnante = 'X';
        int lg = plateau.length;
        int y;
        int x = 1;
        int i = 0;
        
        // Le tableau qui va être créé avec les positions gagnantes
        char [][] plateauPositionsGagnantes = creerPlateau(lg);
        
        // Permet de déterminer les positions gagnantes en utilisant le nombre d'or
        while(i < plateau.length){
            y = (plateau.length - 1) - (int)(i * calculNombreDOr(plateau.length));
            x = (int)(i * calculNombreDOr(plateau.length)) + i;
            
            // Permet d'arrêter le processus pour éviter de sortir du tableau, en utilisant un break car je n'ai pas trouvé une solution viable avec un boolean
            if (x > plateau.length - 1 || y < 0){
                break;
            }
            
            // Permet de palcer le marqueur aux positions gagnantes 
            plateauPositionsGagnantes[y][x] = 'X';
            
            // Permet de placer de façon de façon symétrique les autres positions gagnantes
            plateauPositionsGagnantes[(plateau.length - 1) - x][(int)(i * calculNombreDOr(plateau.length))] = 'X';
            i++;
        }
        return plateauPositionsGagnantes;
    }
    
	/**
	 * Methode permettant de vérifier si la condition de victoire est respectée c'est-à-dire si le pion est sur la dernière case en bas à gauche
	 * @param plateau le tableau dans lequel on va vérifier la condition de victoire 
	 * @return renvoie le resultat de la vérification, true si le pion se trouve à la position [FINAL_LG - 1][0], false sinon
	 */
	 
	boolean verificationVictoire(char[][] plateau){
		
		boolean resultat = true;
		
		//Permet de vérifier si le pion est présent à [plateau.length - 1][0] ou non
		if(plateau[plateau.length - 1][0] != 'O'){
			resultat = false;
		}
		return resultat;
	}
    
    /**
     * Methode permettant de lancer une partie de Wythoff
     */
     
    void jouer(){
		
		// Permet de demander une taille au plateau
		int FINAL_LG = SimpleInput.getInt("Donner la taille du plateau (lg >= 3 et lg <= 10) : ");
		
		// Permet de redemander une taille de plateau tant que celle-ci n'est pas conforme 
		while(FINAL_LG < 3 || FINAL_LG > 10){
			FINAL_LG = SimpleInput.getInt("Donner une taille de tableau comprise entre 3 et 10 inclus : ");
		}
		
		char[][] plateau = creerPlateau(FINAL_LG);
		boolean conditionFin = verificationVictoire(plateau);
		char direction;
		int nombreDeplacement;
		boolean deplacementPossible;
		String joueurActuel = " ";
		
		// Permet de demander qui doit jouer en premier
		System.out.print("Qui doit jouer en premier ? (O pour ordinateur, J pour joueur) : ");
		char choixCommencement = SimpleInput.getChar(" ");
		
		// Permet de redemander qui doit jouer en premier tant que la valeur donnée est incorrecte 
		while(choixCommencement != 'O' && choixCommencement != 'J'){
			System.out.println("Dite qui commence (O pour ordinateur, J pour joueur) : ");
			choixCommencement = SimpleInput.getChar(" ");
		}
		
		plateau = affichagePositionGagnante(plateau);
		placePionAleatoire(plateau);
		affichePlateau(plateau);
		
		// Permet de réaliser les actions de l'ordinateur si c'est à son tour de commencer	 
		if(choixCommencement == 'O'){
			System.out.println("Deplacement ordinateur : ");
			System.out.println(" ");
			analyseEtActionIA(plateau);
			affichePlateau(plateau);
		}
		
		// Permet de répéter le jeu autant de fois que nécessaire tant que la condition de fin n'est pas respectée
		while(conditionFin == false){
			System.out.println("A votre tour " + joueur);
			
			// Permet de demander à l'utilisateur le déplacement qu'il souhaite faire
			do{
				
				// Permet de demander la direction
				System.out.print("Donner la direction dans laquelle vous souhaitez deplacer le pion (B pour le bas, G pour la gauche, D pour la diagonale) : ");
				direction = SimpleInput.getChar(" ");
				System.out.print(" ");
			
				// Redemande tant que la direction donnée est fausse
				while(direction != 'B' && direction != 'G' && direction != 'D'){
					System.out.println("La direction est invalide !");
					System.out.print("Donner une direction valide (B pour le bas, G pour la gauche, D pour la diagonale) : ");
					direction = SimpleInput.getChar(" ");
					System.out.println(" ");
				}
				
				// Permet de demander le nombre de déplacement souhaité
				nombreDeplacement = SimpleInput.getInt("Donner le nombre de deplacement que vous souhaitez faire dans la direction " + direction + " : ");
				
				// Redemande le nombre de déplacement tant que celui-ci est négatif ou égal à 0
				while(nombreDeplacement <= 0){
					nombreDeplacement = SimpleInput.getInt("Donner un nombre de deplacement superieur a 0");
				}
				
				// Vérifie si le déplacement est possible dans cette direction
				deplacementPossible = verificationDeplacement(plateau, direction, nombreDeplacement);
				
				// Indique que le déplacement est impossible si c'est le cas
				if(deplacementPossible == false){
					System.out.println("Votre deplacement semble impossible, veuillez reesayer");
				}
				
			// Réalise la même démarche tant que le déplacement est impossible
			}while(deplacementPossible == false);	
				 		 
			deplacementPion(plateau, direction, nombreDeplacement);
			affichePlateau(plateau);
			conditionFin = verificationVictoire(plateau);
			joueurActuel = joueur;
			
			// Permet de réaliser les actions de l'IA tant que la condition de fin n'est pas réalisé
			if(conditionFin == false){
				analyseEtActionIA(plateau);
				System.out.println("Deplacement ordinateur : ");
				System.out.println(" ");
				affichePlateau(plateau);
				conditionFin = verificationVictoire(plateau);
				joueurActuel = "ordinateur";
			}
		}
		System.out.println("Fin de la partie");
		System.out.print("Felicitations " + joueurActuel + " a gagnee");
	}
	
													//***Methode de test pour creerPlateau***
	
	/**
	* Teste la méthode creerPlateau()
	*/

	void testCreerPlateau () {
		
		char[][] testPlateau1 = {{' ',' '},{' ',' '}};
		char[][] testPlateau2 = {{' '}};
		char[][] testPlateau3 = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		System.out.println ();
		System.out.println ("*** testCreerPlateau()");
		testCasCreerPlateau (2, testPlateau1);
		testCasCreerPlateau (1, testPlateau2);
		testCasCreerPlateau (3, testPlateau3);
	}
	
	/**
	 * teste un appel de creerPlateau()
	 * @param taille la taille du plateau souhaité
	 * @param result le résultat attendu
	 */

	void testCasCreerPlateau (int taille, char[][] result) {
		
		// Arrange
		System.out.print("La creation d'un plateau de taille " + taille + ", cree le plateau : " );
		displayTab2D(result);
		System.out.print ("\t = ");
		// Act
		char[][] resExec = creerPlateau(taille);
		// Assert
		if (tableauxIdentiquesChar2D(resExec, result) == true){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	 * Methode permettant d'afficher de façon linéaire un tableau en 2D
	 * @param tab tableau à afficher
	 */
	 
	void displayTab2D(char[][] tab) {
		
		System.out.print("{");

		for (int i = 0; i < tab.length; i++) {
			System.out.print("{");
			for (int j = 0; j < tab[i].length - 1; j++) {
				System.out.print(tab[i][j] + ",");
			}
			System.out.print(tab[i][tab[i].length - 1] + "}");

			if (i < tab.length - 1) {
				System.out.print(",");
			}
		}
		System.out.print("}");
	}
	 
	/** 
	 * Indique si deux tableau sont identiques
	 * @param tableau1 le premier tableau
	 * @param tableau2 le deuxième tableau
	 * @return renvoie true si les deux tableaux sont identiques, false sinon
	 */
	 
	 boolean tableauxIdentiquesChar2D(char[][] tableau1, char[][] tableau2){
		 
		 boolean resultat = true;
		 
		 if(tableau1.length != tableau2.length){
			 resultat = false;
		 }
		 
		 for(int i = 0; i < tableau1.length && resultat == true; i++){
			 for(int j = 0; j < tableau1.length; j++){
				 if(tableau1[i][j] != tableau2[i][j]){
					 resultat = false;
				 }
			 }
		 }
		 return resultat;
	 }
			
													//***Methode de test pour affichePlateau***
	
	/**
	 * Demonstration d'un appel de la fonction "affichePlateau"
	 */
	 
	void demonstrationAffichePlateau(){
	
		System.out.println("Demonstration d'un appel de la fonction affichePlateau() : ");
		char[][] test = {{' ','O',' '},{' ','X',' '},{' ','A',' '}};
		System.out.print("Exemple avec ");
		displayTab2D(test);
		System.out.println(" : ");
		affichePlateau(test);
	}

	
													//***Methode de test pour placePionAleatoire***	
	
	/**
	 * Demonstration d'un appel de la fonction "placePionAleatoire"
	 */
	 
	 void demonstrationPlacePionAleatoire(){
		
		System.out.println("Demonstration d'un appel de la fonction placePionAleatoire() : ");
		char[][] test = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		System.out.print("Exemple avec ");
		displayTab2D(test);
		System.out.println(" : ");
		placePionAleatoire(test);
		affichePlateau(test);
	}

													//*** Methode de test pour positionPion() ***

	/**
	 * Teste la méthode positionPion()
	 */

	void testPositionPion () {
		
		char[][] testPlateau4 = {{' ','O'},{' ',' '}};
		char[][] testPlateau5 = {{'O'}};
		char[][] testPlateau6 = {{' ',' ',' '},{' ',' ','O'},{' ',' ',' '}};
		int[] resultat1 = {0,1};
		int[] resultat2 = {0,0};
		int[] resultat3 = {1,2};
		System.out.println ("*** testPositionPion()");
		testCasPositionPion (testPlateau4, resultat1);
		testCasPositionPion (testPlateau5, resultat2);
		testCasPositionPion (testPlateau6, resultat3);
	}
	
	/**
	* teste un appel de positionPion()
	* @param plateau le plateau où regarder la position du pion
	* @param result le résultat attendu
	*/

	void testCasPositionPion (char[][] plateau, int[] result) {
		
		// Arrange
		System.out.print("La position du pion dans le plateau ");
		displayTab2D(plateau);
		System.out.print(" est donne par les coordonnees : ");
		displayTab(result);
		System.out.print ("\t =  ");
		// Act
		int[] resExec = positionPion(plateau);
		// Assert
		if (tableauxIdentiquesInt1D(resExec, result) == true){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	/**
	 * Methode permettant d'afficher un tableau 1D
	 * @param t le tableau que l'on souhaite afficher
	 */
	 
	void displayTab(int[] t){ 
		
		int i = 0;
		System.out.print("t = {");
		
		while(i<t.length-1){
			System.out.print(t[i] + ",");
			i=i+1;
		}
		
		System.out.print(t[i]+"}");
	}
	
	/** 
	 * Indique si deux tableau sont identiques
	 * @param tableau1 le premier tableau
	 * @param tableau2 le deuxième tableau
	 * @return renvoie true si les deux tableaux sont identiques, false sinon
	 */
	 
	boolean tableauxIdentiquesInt1D(int[] tableau1, int[] tableau2){
		 
		boolean resultat = true;
		 
		if(tableau1.length != tableau2.length){
			 resultat = false;
		}
		for(int i = 0; i < tableau1.length; i++){
			if(tableau1[i] != tableau2[i]){
					resultat = false;
				}
			}
		return resultat;
		
	}
	
												// ***Methode de test pour verificationDeplacement()***
	
	/**
	 * Teste la méthode vérificationDeplacement()
	 */
	
	void testVerificationDeplacement () {
		
		char[][] testPlateau4 = {{' ','O'},{' ',' '}};
		char[][] testPlateau5 = {{'O'}};
		char[][] testPlateau6 = {{' ',' ',' '},{' ',' ','O'},{' ',' ',' '}};
		System.out.println ("*** testVerificationDeplacement()");
		testCasVerificationDeplacement (testPlateau4, 'G', 1, true);
		testCasVerificationDeplacement (testPlateau5,'D', 1, false);
		testCasVerificationDeplacement (testPlateau6, 'B', 1, true);
	}
	
	/**
	* Teste un appel de verificationDeplacement()
	* @param plateau le plateau où tester le déplacement
	* @param directionDeplacement le direction du déplacement
	* @param nombreDeplacement le nombre de déplacement dans cette direction
	* @param result le résultat attendu
	*/

	void testCasVerificationDeplacement (char[][] plateau, char directionDeplacement, int nombreDeplacement, boolean result) {
		
		// Arrange
		System.out.print("Le deplacement du pion dans la direction " + directionDeplacement + " avec comme nombre de deplacement " + nombreDeplacement + " dans le plateau ");
		displayTab2D(plateau);
		System.out.print(" a comme resultat : " + result);
		System.out.print ("\t = ");
		// Act
		boolean resExec = verificationDeplacement(plateau, directionDeplacement, nombreDeplacement);
		// Assert
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
													// ***Methode de test deplacementPion()***

	/**
	 * Demonstration de l'appel de deplacementPion()
	 */
	
	void demonstrationDeplacementPion(){
		
		System.out.print("Demonstration d'un appel de deplacementPion() : ");
		char [][] test3 = {{' ',' ','O'},{' ',' ',' '},{' ',' ',' '}};
		affichePlateau(test3);
		System.out.print("Un deplacment sur la gauche de 1 :");
		if (verificationDeplacement(test3, 'G', 1) == true){
			deplacementPion(test3, 'G', 1);
		}
		affichePlateau(test3);
		System.out.print("Un deplacement vers le bas de 1 : ");
		if(verificationDeplacement(test3, 'B', 1) == true){
			deplacementPion(test3, 'B', 1);
		}
		affichePlateau(test3);
		System.out.print("Un deplacement en diagonale de 1 : ");
		if(verificationDeplacement(test3, 'D', 1) == true){
			deplacementPion(test3, 'D', 1);
		}
		affichePlateau(test3);
	}
	
												//***Methode de test pour verificationVictoire()***

	/**
	 * Teste la méthode vérificationVictoire()
	 */
	
	void testVerificationVictoire(){
		
		char[][] testPlateau7 = {{' ',' '},{' ',' '}};
		char[][] testPlateau8 = {{'O'}};
		char[][] testPlateau9 = {{' ',' ',' '},{' ',' ',' '},{'O',' ',' '}};
		System.out.println ("*** testVerificationVictoire()");
		testCasVerificationVictoire (testPlateau7, false);
		testCasVerificationVictoire (testPlateau8, true);
		testCasVerificationVictoire (testPlateau9, true);
	}
	
	/**
	* Teste un appel de verificationVictoire()
	* @param plateau le plateau où vérifier la condition de victoire
	* @param result le résultat attendu
	*/

	void testCasVerificationVictoire (char[][] plateau, boolean result){
		
		// Arrange
		System.out.print("La condition de victoire est-elle respectee dans le plateau ");
		displayTab2D(plateau);
		System.out.print(" : " + result);
		System.out.print ("\t = ");
		// Act
		boolean resExec = verificationVictoire(plateau);
		// Assert
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
												// ***Methode de test pour deplacementAleatoireIA***

	/** 
	 * Demonstration d'un appel de deplacementAleatoireIA()
	 */ 
	
	void demonstrationDeplacementAleatoireIA(){
		
		System.out.println("Demonstration d'un appel de la fonction deplacementAleatoireIA() : ");
		char[][] test4 = {{' ',' ',' '},{' ','O',' '},{' ',' ',' '}};
		affichePlateau(test4);
		deplacementAleatoireIA(test4);
		affichePlateau(test4);
	}

												// ***Methode de test pour analyseEtActionIA()***

	/**
	 * Demonstration d'un appel de analyseEtActionIA()
	 */
	
	void demonstrationAnalyseEtActionIA(){
		
		System.out.println("Demonstration d'un appel de la fonction analyseEtActionIA() : ");
		char[][] test5 = creerPlateau(5);
		test5 = affichagePositionGagnante(test5);
		placePionAleatoire(test5);
		affichePlateau(test5);
		analyseEtActionIA(test5);
		affichePlateau(test5);
	}
	
												// ***Methode de test affichagePositionGagnante()***

	/**
	 * Teste la méthode affichagePositionGagnante()
	 */
	
	void testAffichagePositionGagnante(){
		
		char[][] testPlateau10 = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		char[][] testPlateau11 = {{' '}};
		char[][] resultatTest10 = {{' ','X',' '},{' ',' ','X'},{'X',' ',' '}};
		char[][] resultatTest11 = {{'X'}};
		System.out.println ("*** testAffichagePositionGagnante()");
		testCasAffichagePositionGagnante (testPlateau10, resultatTest10);
		testCasAffichagePositionGagnante (testPlateau11, resultatTest11);
	}
	
	/**
	* Teste un appel de affichagePositionGagnante()
	* @param plateau le plateau où placer les positions gagnantes
	* @param result le résultat attendu
	*/

	void testCasAffichagePositionGagnante (char[][] plateau, char[][] result){
		
		// Arrange
		System.out.print("Le plateau ");
		displayTab2D(plateau);
		System.out.print(" a comme position gagnante ce plateau : ");
		displayTab2D(result);
		System.out.print ("\t = ");
		// Act
		char[][] resExec = affichagePositionGagnante(plateau);
		// Assert
		if (tableauxIdentiquesChar2D(resExec, result) == true){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}

												// ***Methode de test pour calculNombreDOr()***
	/**
	 * Teste la méthode calculnombreDOr()
	 */
	
	void testCalculNombreDOr(){
		
		System.out.println ("*** testCalculNombreDOr()");
		testCasCalculNombreDOr (0, 1);
		testCasCalculNombreDOr (1, 2);
		testCasCalculNombreDOr (2, 1.5);
	}
	
	/**
	* Teste un appel de calculNombreDOr()
	* @param nombre le nombre de fois où réaliser l'opération
	* @param result le résultat attendu
	*/

	void testCasCalculNombreDOr (int nombre, double result){
		
		// Arrange
		System.out.print("L' operation du nombre d'or a ete fait " + nombre);
		System.out.print(" fois et ceci a comme resultat " + result + " : ");
		System.out.print ("\t = ");
		// Act
		double resExec = calculNombreDOr(nombre);
		// Assert
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
											// ***Methode de test tableauxIdententiquesChar2D()***
	
	/**
	 * Teste la méthode tableauxIdentiquesChar2D()
	 */
	
	void testTableauxIdentiquesChar2D(){
		
		System.out.println ("*** testTableauxIdentiquesChar2D()");
		char[][] tableauTest12 = {{' '}};
		char[][] tableauTest13 = {{' '}};
		char[][] tableauTest14 = {{' ',' '},{'R',' '}};
		char[][] tableauTest15 = {{' ','Y'},{'R',' '}};
		testCasTableauxIdentiquesChar2D (tableauTest12, tableauTest13, true);
		testCasTableauxIdentiquesChar2D (tableauTest14, tableauTest15, false);
	}
	
	/**
	* Teste un appel de tableauxIdentiquesChar2D()
	* @param tableau1 le premier tableau 
	* @param tableau2 le deuxième tableau
	* @param result le résultat obtenu
	*/

	void testCasTableauxIdentiquesChar2D (char[][] tableau1, char[][] tableau2, boolean result){
		
		// Arrange
		System.out.print("Le premier tableau ");
		displayTab2D(tableau1);
		System.out.print(" et le deuxieme tableau  ");
		displayTab2D(tableau2);
		System.out.print(" sont t'ils identique : " + result);
		System.out.print ("\t = ");
		// Act
		boolean resExec = tableauxIdentiquesChar2D(tableau1, tableau2);
		// Assert
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
												// ***Methode de test tableauxIdententiquesInt1D()***
	
	/**
	 * Teste la méthode tableauxIdentiquesInt1D()
	 */
	
	void testTableauxIdentiquesInt1D(){
		
		System.out.println ("*** testTableauxIdentiquesInt1D()");
		int[] tableauTest16 = {1,3,2};
		int[] tableauTest17 = {1,2,3};
		int[] tableauTest18 = {1,2,3,4,5,6};
		int[] tableauTest19 = {1,2,3,4,5,6};
		testCasTableauxIdentiquesInt1D (tableauTest16, tableauTest17, false);
		testCasTableauxIdentiquesInt1D (tableauTest18, tableauTest19, true);
	}
	
	/**
	* Teste un appel de tableauxIdentiquesInt1D()
	* @param tableau1 le premier tableau 
	* @param tableau2 le deuxième 
	* @param result le résultat attendu
	*/

	void testCasTableauxIdentiquesInt1D (int[] tableau1, int[] tableau2, boolean result){
		
		// Arrange
		System.out.print("Le premier tableau ");
		displayTab(tableau1);
		System.out.print(" et le deuxieme tableau  ");
		displayTab(tableau2);
		System.out.print(" sont t'ils identique : " + result);
		System.out.print ("\t = ");
		// Act
		boolean resExec = tableauxIdentiquesInt1D(tableau1, tableau2);
		// Assert
		if (resExec == result){
			System.out.println ("OK");
		} else {
			System.err.println ("ERREUR");
		}
	}		
}
