/** 
 * Ce programme simule une partie de Wythoff entre deux joueurs
 * 
 * Les méthodes de tests pour les void sont sous la forme de démonstrations, les autres sont sous la forme de méthodes de tests classiques
 * Le problème des doubles affichages lors des SimpleInput.getChar n'a malheuresement pas été réglé chez moi après l'utilisation du nouveau fichier SimpleInput.java
 * donc j'ai été contrain de réaliser l'affichage hors du SimpleInput.getChar
 * @author Eliot
 */
 
class Wythoff1{
	
	//Permet de donner un nom à la partie
	final String nomPartie = SimpleInput.getString("Donner un nom a cette partie : ");
	
	//Permet de donner un nom au premier joueur
	final String joueur1 = SimpleInput.getString("Donner un nom au premier joueur : ");
	
	//Permet de donner un nom au second joueur
	final String joueur2 = SimpleInput.getString("Donner un nom au second joueur : ");   


	void principal(){
		
		// Permet le lancement d'une partie
		jouer();
	}
	
	/**
	* Créer un plateau de jeu carré rempli de carctere espace ' '
	* @param longueur taille du plateau
	* @return retourne le tableau de caractère à deux dimensions
	*/
 
	char[][] creerPlateau(int longueur){
		
		char[][] plateau = new char [longueur][longueur];
		
		//Permet la création du plateau avec des cases remplies de vide
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				plateau[i][j] = ' ';
			}
		}
		
		return plateau;
	}
	
	/**
	 * Affichage d'un plateau de Wythoff avec les indices de lignes et de colonnes
	 * @param plateau le plateau a afficher
	 */
 
	void affichePlateau (char[][] plateau){
		
		System.out.println(" ");
		
		// Affiche les indices des différentes lignes de façon inversée pour le jeu de Wythoff
		for (int i = 0; i < plateau.length; i++){
			System.out.print(plateau.length - 1 - i);
			
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
			System.out.print("   " + k);
			
		}
		
		// Espaces supplémentaires pour l'esthétique
		System.out.println(" ");
		System.out.println(" ");
	}
	
	/**
	 * Méthode permettant de placer le pion de façon aléatoire sur la première ligne et sur une colonne qui n'est pas gagnante en un coup de façon aléatoire
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
			
		// Positione le pion a l'endroit générer de manière aléatoire
		plateau[positionLigne][positionColonne] = 'O';
	}	
	
	/**
	 * Méthode permettant de connaitre l'emplacement du pion dans un plateau donné
	 * @param plateau le plateau dans lequel on cherche le pion
	 * @return renvoie un tableau contenant les coordonnees du pion dans le plateau
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
	 * Methode permettant de savoir si un déplacement est possible ou non dans un plateau
	 * @param plateau le plateau de jeu ou aura lieu la vérification
	 * @param direction la direction dans laquelle le deplacement veut être effectuée
	 * @param nbDeplacement le nombre de deplacement que souhaite faire le joueur 
	 * @return renvoie true si le mouvement est possible, false sinon
	 */
	 
	boolean verificationDeplacement(char[][] plateau, char direction, int nbDeplacement){
		
		boolean verification = true;
		
		// Appel de la methode "positionPion" pour pouvoir connaitre l'emplacement du pion et vérifier ensuite le déplacement 
		int[] coordonneesPion = positionPion(plateau);  
		int placementX = coordonneesPion[1];
		int placementY = coordonneesPion[0];
		
		// Vérifie si le déplacement dans la direction G est possible ou non
		if(direction == 'G'){
			
			// Vérifie si le déplacement ne dépassse pas le plateau sur la gauche
			if(placementX - nbDeplacement < 0){  
				verification = false;
			}
		}
		
		// Verifie si le déplacement est possible vers le bas
		if(direction == 'B'){
			
			// Vérifie si le déplacement ne dépasse pas le plateau vers le bas
			if(placementY + nbDeplacement > plateau.length - 1){ 
				verification = false;
			}
		}
		
		// Verifie si le déplacement est possible en diagonale
		if(direction == 'D'){
			
			// Vérifie si le déplacment en diagonale ne sors pas du plateau
			if(placementY + nbDeplacement > plateau.length - 1  || placementX - nbDeplacement < 0){ 
				verification = false;
			}
		}
		return verification;
	}
		
	
	/**
	 * Méthode permettant de deplacer le pion d'un nombre donnée de cases
	 * @param plateau le plateau ou sera effectué le déplacement
	 * @param direction la direction dans laquelle déplacer le pion
	 * @param nbDeplacement le nombre de déplacement souhaité dans cette direction 
	 */
	 
	void deplacementPion(char[][] plateau, char direction, int nbDeplacement){
		
		int[] placementPrecedent = positionPion(plateau);
		int positionX = placementPrecedent[1];
		int positionY = placementPrecedent[0];
		
		// Permet de mettre à vide la case de l'ancienne position du pion
		plateau[positionY][positionX] = ' ';
		
		// Permet de déplacer le pion vers la gauche n fois
		if(direction == 'G'){
			positionX = positionX - nbDeplacement;
		}
		// Permet de déplacer le pion vers le bas n fois
		if(direction == 'B'){
			positionY = positionY + nbDeplacement;
		}
		//Permet de déplacer le pion en diagonale n fois
		if(direction == 'D'){
			positionX = positionX - nbDeplacement;
			positionY = positionY + nbDeplacement;
		}
		
		// Place le pion sur la case voulue
		plateau[positionY][positionX] = 'O';  
	}
	
	/**
	 * Methode permettant de vérifier si la condition de victoire est respectée c'est-à-dire si le pion est sur la dernière case en bas à gauche
	 * @param plateau le tableau dans lequel on va vérifier la condition de victoire 
	 * @return renvoie le resultat de la vérification, true si le pion se trouve à la position [plateau.length - 1][0], false sinon
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
	 * Méthode permettant de changer de joueur lors de la partie
	 * @param joueurActuel le joueur qui va être remplacé
	 * @return renvoie le nom du prochain joueur 
	 */
	
	String changeJoueur(String joueurActuel){
		
		String joueurSuivant = " ";
		
		// Permet de passer du joueur 1 au joueur 2
		if(joueurActuel == joueur1){
			joueurSuivant = joueur2;
		}
		// Permet de passer du joueur 2 au joueur 1
		if(joueurActuel == joueur2){
			joueurSuivant = joueur1;
		}
		return joueurSuivant;
	}
	
	
	
	/**
	 * Methode permettant de jouer au jeu de Wythoff par une série d'appel de méthode
	 */
	void jouer(){
		
		//Permet de donner la taille de plateau que l'on souhaite
		int longueurPlateau = SimpleInput.getInt("Donner la taille du plateau (lg >= 3 et lg <= 10) : ");   
		
		//Permet de redemander la taille du plateau si la précedente n'est pas conforme
		while(longueurPlateau < 3 || longueurPlateau > 10){
			longueurPlateau = SimpleInput.getInt("Donner une taile de tableau comprise entre 3 et 10 inclus : ");
		}
		
		char[][] plateau = creerPlateau(longueurPlateau);
		boolean conditionfin = verificationVictoire(plateau);
		boolean deplacementPossible;
		String joueurActuel = joueur1;
		String joueurPrecedent = joueurActuel;
		char direction;	
		int nombreDeplacement;	
		
		placePionAleatoire(plateau);
		affichePlateau(plateau);
		
		//Permet de répéter le jeu autant de fois que nécessaire, tant que la condition de victoire n'est pas respéctée 
		while(conditionfin == false){
			
			System.out.println("Au tour du joueur " + joueurActuel);
			
			// Demande au joueur de rentrer la direction et le nombre de déplacement souhaité
			do{
				
				// Demande la direction du déplacement
				System.out.print("Donner la direction dans laquelle vous souhaitez deplacer le pion (B pour le bas, G pour la gauche, D pour la diagonale) : ");
				direction = SimpleInput.getChar(" ");
				
				// Redemande la direction si celle rensignée est fausse
				while(direction != 'B' && direction != 'G' && direction != 'D'){
					System.out.println("La direction est invalide !");
					System.out.print("Donner une direction valide (B pour le bas, G pour la gauche, D pour la diagonale) : ");
					direction = SimpleInput.getChar(" ");
				}
			    
			    // Demande le nombre de déplacement dans cette direction
				nombreDeplacement = SimpleInput.getInt("Donner le nombre de deplacement que vous souhaitez faire dans la direction " + direction + " : ");
				
				// Redemande le nombre si celui-ci est négatif ou égal à 0
				if(nombreDeplacement <= 0){
					nombreDeplacement = SimpleInput.getInt("Donner un nombre de deplacement superieur a 0");
				}
				
				// Vérifie si le déplacement est possible
				deplacementPossible = verificationDeplacement(plateau, direction, nombreDeplacement);
				
				// Affiche un message d'erreur si le déplacement est impossible
				if(deplacementPossible == false){
					System.out.println("Votre deplacement semble impossible, veuillez reesayer");
				}
			
			// Redemande la même démarche tant que le déplacement est impossible
			}while(deplacementPossible == false);
				
			deplacementPion(plateau, direction, nombreDeplacement);
			affichePlateau(plateau);
			joueurPrecedent = joueurActuel;  // Permet de stocker le joueur précédent pour pouvoir annoncer le gagnant
			conditionfin = verificationVictoire(plateau);
			joueurActuel = changeJoueur(joueurActuel);
		}
		System.out.println("Fin de la partie");
		System.out.println("Felicitation le joueur " + joueurPrecedent + " a gagnee ");
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
	 * Indique si deux tableaux 2D sont identiques
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
		System.out.print("Exemple avec le plateau : ");
		displayTab2D(test);
		System.out.println(" : ");
		affichePlateau(test);
	}

	
													//***Methode de test pour placePionAleatoire()***	
	
	/**
	 * Demonstration d'un appel de la fonction "placePionAleatoire"
	 */
	 
	 void demonstrationPlacePionAleatoire(){
		
		System.out.println("Demonstration d'un appel de la fonction placePionAleatoire() : ");
		char[][] test = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		System.out.print("Exemple avec le plateau : ");
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
	* Teste un appel de positionPion()
	* @param plateau le plateau où regarder la position du pion
	* @param result le résultat attendu
	*/

	void testCasPositionPion (char[][] plateau, int[] result) {
		
		// Arrange
		System.out.print("La position du pion dans le tableau ");
		displayTab2D(plateau);
		System.out.print(" est donnee par les coordonnees : ");
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
	 * Indique si deux tableaux 1D sont identiques
	 * @param tableau1
	 * @param tableau2
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
	* @param directionDeplacement la direction du déplacement
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
		System.out.print("La condition de victoire est-elle respectee dans le plateau  ");
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
	
														// ***Methode de test changeJoueur()***

	/**
	 * Teste la méthode changeJoueur()
	 */
	 
	void testChangeJoueur(){
		
		String joueurTest = joueur1;
		String joueurTest2 = joueur2;
		System.out.println ("*** testChangeJoueur()");
		testCasChangeJoueur (joueurTest, joueur2);
		testCasChangeJoueur (joueurTest2, joueur1);
	}
	
	/**
	* Teste un appel de changeJoueur()
	* @param plateau le plateau où vérifier la condition de victoire
	* @param result le résultat attendu
	*/

	void testCasChangeJoueur (String joueur, String result){
		
		// Arrange
		System.out.print("Si " + joueur + " est le joueur actuel alors le prochain joueur est " + result );
		System.out.print ("\t = ");
		// Act
		String resExec = changeJoueur(joueur);
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
	*/

	void testCasTableauxIdentiquesChar2D (char[][] tableau1, char[][] tableau2, boolean result){
		
		// Arrange
		System.out.print("Le premier tableau ");
		displayTab2D(tableau1);
		System.out.print(" et le deuxieme tableau  ");
		displayTab2D(tableau2);
		System.out.print(" sont t'ils identiques : " + result);
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
	* @param tableau2 le deuxième tableau
	* @return result le résultat attendu
	*/

	void testCasTableauxIdentiquesInt1D (int[] tableau1, int[] tableau2, boolean result){
		
		// Arrange
		System.out.print("Le premier tableau ");
		displayTab(tableau1);
		System.out.print(" et le deuxieme tableau  ");
		displayTab(tableau2);
		System.out.print(" sont t'ils identiques : " + result);
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
