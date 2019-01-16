/**
 * La classe Graphe represente un graphe :   SOLUTION
 */
import java.text.*;
 
class Graphe {

	// attributs du graphe
	
int nombreSommets;		// nombre de sommets du graphe
	boolean oriente;			// le graphe est-il oriente ou non ?
	int[][] graphe;			// la matrice carree qui va contenir le graphe	
									// les dimensions de la matrice : nombreSommets x nombreSommets
									
/**
	 * Initialise le graphe.
	 * 3 initialisations sont possibles :
	 * 	- à partir du graphe qui represente une maison (oriente)
	 * 	- à partir du graphe qui represente une maison (non oriente)
	 * 	- à partir du graphe oriente presente en cours qui possède 8 sommets (a ete utilise notamment lors 
	 * 	  du parcours en profondeur)
	 * 
	 * @param nom peut valoir au choix :
	 *      - "Graphe maison (oriente)" 
     *      - "Graphe maison (non oriente)"
	 * 		- "Graphe du cours"
	 */
	Graphe (String nom) {
		if (nom.equals ("Graphe maison (oriente)")) {
			this.nombreSommets=5;	
			this.oriente=true;				    
			int[][] grapheTemporaire={ 				{0,0,0,0,0},	
													{0,0,0,1,0},	
													{0,0,0,0,1},
													{0,0,0,0,1},
													{0,0,0,1,0}};
			this.graphe = grapheTemporaire;   // allez donc faire ça en Langage C... ??!
		}
		else
		if (nom.equals ("Graphe maison (non oriente)")) {
			this.nombreSommets=5;	
			this.oriente=false;				    
			int[][] grapheTemporaire={ 	{0,1,1,0,0},	// la matrice est donc symetrique...
										{1,0,0,1,0},	
										{1,0,0,0,1},
										{0,1,0,0,1},
										{0,0,1,1,0}};
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Graphe du cours")) {
			this.nombreSommets=8;	
			this.oriente=true;		   //0 1 2 3 4 5 6 7 
			int[][] grapheTemporaire={	{0,0,1,0,1,1,1,0}, 	// 0
										{1,0,0,0,0,0,0,0}, 	// 1           
										{0,1,0,0,0,1,0,0}, 	// 2
										{0,1,0,0,0,0,0,0}, 	// 3
										{0,0,0,0,0,0,0,0}, 	// 4
										{0,0,0,0,0,0,0,0}, 	// 5
										{0,0,0,0,0,0,0,0}, 	// 6
										{0,1,0,1,1,0,0,0}};	// 7
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Un graphe fortement connexe")) {   // un petit graphe fortement connexe
			this.nombreSommets=3;	
			this.oriente=true;		  
			int[][] grapheTemporaire={	{0,1,0},   // un joli circuit : 0 -> 1 -> 2 -> 0 
										{0,0,1}, 
										{1,0,0}};
										
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Un graphe non oriente a 2 composantes connexes")) {   // un petit graphe avec 2 composantes connexes :  
			this.nombreSommets=3;												// qui sont formées des ensembles de sommets (0)   +  (1,2)
			this.oriente=false;		  
			int[][] grapheTemporaire={	{0,0,0},
										{0,0,1}, 
										{0,1,0}};
										
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Un graphe 3x3 rempli pour faire des tests")) { 
			this.nombreSommets=3;	
			this.oriente=false;		  
			int[][] grapheTemporaire={	{1,1,1},
										{1,1,1}, 
										{1,1,1}};
										
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Un graphe connexe")) {   // un petit graphe connexe
			this.nombreSommets=3;	
			this.oriente=false;		  
			int[][] grapheTemporaire={	{0,1,0},   // un graphe  : 0 - 1 - 2   
										{1,0,1}, 
										{0,1,0}};
										
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Graphe pour calcul des plus courts chemins")) {   
			this.nombreSommets=5;	
			this.oriente=true;		  //  0  1  2  3  4
			int[][] grapheTemporaire={ 	{-1,+2,+5,-1,-1},  // 0	
										{-1,-1,-1,+1,+7},  // 1	
										{-1,-1,-1,-1,+4},  // 2
										{-1,-1,+1,-1,-1},  // 3
										{-1,-1,-1,-1,-1}}; // 4
										
			this.graphe = grapheTemporaire;   
		}
		else
		if (nom.equals ("Graphe pour tester Floyd et calculer le diametre")) {   
			this.nombreSommets=6;	
			this.oriente=false;		    //0  1  2  3  4  5  
			int[][] grapheTemporaire={	{-1,+1,-1,+1,-1,-1}, 	// 0
										{+1,-1,+1,-1,+1,-1}, 	// 1           
										{-1,+1,-1,-1,+1,+1}, 	// 2
										{+1,-1,-1,-1,+1,-1}, 	// 3
										{-1,+1,+1,+1,-1,+1}, 	// 4
										{-1,-1,+1,-1,+1,-1}};	// 5
										
										
			this.graphe = grapheTemporaire;   
		}
		else
			System.out.println ("Attention, aucun nom de graphe ne correspond !!!");
	}
	
	
	/**
	 * Initialise un graphe

	 * @param le nombre de sommets
	 * @param oriente si le graphe est oriente ou non
	 */
	Graphe (int nombreSommets, boolean oriente) {
			this.nombreSommets=nombreSommets;												
			this.oriente=oriente;		  
			this.graphe = new int[nombreSommets][nombreSommets]; 
		}
									
									
									
	/** 
	*
	* Nous n'utiliserons que les accesseurs en lecture et nous passerons donc des accesseurs en écriture...
	*/
		
	/**
	 * Renvoie le nombre de sommets du graphe.
	 * 
	 * @return le nombre de sommets du graphe
	 */
	 
	int getNombreSommets () {
		return nombreSommets;
	}
	
	/**
	 * Indique si le graphe est orienté ou non.
	 * 
	 * @return true si le graphe est orienté, false sinon.
	 */
	 
	boolean getOriente () {
		return oriente;
	}
	
	/**
	 * Renvoie le graphe.
	 * 
	 * @return le graphe.
	 */
	 
	int[][] getGraphe () {
		return this.graphe;
	}
	

	/**
	 * Affiche dans la console les caracteristiques du graphe 
	 * puis le detail de la matrice qui contient le graphe.
	 * 
	 */
	void affiche() {
		if (this.getOriente()) 
			System.out.println ("- Graphe oriente.");
		else
			System.out.println ("- Graphe non oriente.");
		System.out.println ("- Graphe ayant "+this.getNombreSommets()+ " sommet(s).");
		System.out.println ("- Matrice du graphe :");
			
			 
		// affichage d'indices autour de la matrice afin d'y voir plus clair
		System.out.print(" ");   
		for (int i=0;i<this.getNombreSommets(); i++)
			System.out.print("  "+i);
		System.out.println();
		
		// affichage d'un trait au dessus de la matrice pour que ça soit plus joli
		for (int i=0;i<=this.getNombreSommets()*2; i++)
			System.out.print("--");
		System.out.println();

			
		// affichage du contenu de la matrice, ligne par ligne
		for (int i=0;i<this.getNombreSommets(); i++) {
			System.out.print("| ");
			for (int j=0;j<this.getNombreSommets(); j++) {
				if (this.graphe[i][j]>0)			
					System.out.print("+"+this.graphe[i][j]+" ");
				else 
				if (this.graphe[i][j]==0)
					System.out.print("00 ");
				else 
					System.out.print(this.graphe[i][j]+" ");
			}
			System.out.println("|  "+ i);
		}


		// affichage d'un trait en dessous de la matrice pour que ça soit plus joli
		for (int i=0;i<=this.getNombreSommets()*2; i++)
			System.out.print("--");
		System.out.println();
		
	}

		
	/**
	 * Pour un graphe oriente, calcule le nombre d'arcs qui arrivent sur un sommet donne
	 * (rappel : le terme consacre est "demi degre interieur" d'un sommet)
	 * 
	 * @param numeroSommet : le numero du sommet concerne
	 * @return le nombre d'arcs entrants
	 */
	int calculeNombreArcsEntrants (int numeroSommet) {
		// à completer/modifier
	
		// on aurait pu ajouter un test pour bien verifier que c'est un graphe oriente et pas un graphe non
		// oriente qui est passe en parametre, mais bon ça allourdit :-)
		
		int nombreArcsArrivants=0;
		for (int i=0;i<nombreSommets;i++)
			if (this.graphe[i][numeroSommet]==1)
				nombreArcsArrivants++;
		return nombreArcsArrivants;
	}
	
	/**
	 * Pour un graphe oriente, affiche à l'écran les sommets sur lesquels aucun arc n'arrive
	 * (autrement dit, de demi degré intérieur nul).  
	 * Rappel : en théorie des graphes, on appelle cela  les sources du graphe.
	 * 
	 * Donc pour le problème qui nous concerne, affiche les numéros associés aux personnes
	 * qui ne sont "aimées" de personne...
	 *
	 */
	void afficheSources () {
		// à completer
		
		for (int i=0;i<this.getNombreSommets();i++)
			if (this.calculeNombreArcsEntrants (i)==0) // autrement dit, une source
				System.out.print(" "+i+" ");
	}
	
	
	/**
	 * Pour un graphe oriente calcule le nombre d'arcs qui sortent d'un sommet donne
	 * (le terme consacre est "demi degre exterieur" d'un sommet)
	 * 
	 * 
	 * @param numeroSommet : le numero du sommet concerne
	 * @return le nombre d'arcs sortants
	 */
	 
	int calculeNombreArcsSortants (int numeroSommet) {
		// à completer/modifier
		
		int nombreArcsSortants=0;
		for (int i=0;i<this.getNombreSommets();i++)
			if (this.graphe[numeroSommet][i]==1)
				nombreArcsSortants++;
		return nombreArcsSortants;
	}
	
	
	/**
	 * Pour un graphe oriente, affiche à l'écran les sommets sur desquels aucun arc ne part
	 * (autrement dit, de demi degré extérieur nul).  
	 * Rappel : en théorie des graphes, on appelle cela les puits du graphe.
	 * 
	 * Donc pour le problème qui nous concerne, affiche les numéros associés aux personnes
	 * qui "n'aiment" personne...
	 *
	 */
	 
	void affichePuits () {
		// à completer
		
		for (int i=0;i<this.getNombreSommets();i++)
			if (this.calculeNombreArcsSortants (i)==0) // autrement dit, un puits
				System.out.print(" "+i+" ");
	}
	
	/**
	 * Pour un graphe donne, effectue le parcours en profondeur 
	 * 
	 * 
	 * @param numeroSommet : le numero du sommet a partir duquel on appelle le parcours en profondeur
	 * @param marques : le tableau de marques qui permet d'eviter de tourner en rond...
	 */
	 
	 
	void parcoursProfondeur (int numeroSommet, boolean[] marques) {  
		// à completer/modifier
        marques[numeroSommet] = true;
        //System.out.print("S" + numeroSommet+" "); 	// ligne qui sera à mettre en commentaires 
													//  dès qu'on attaque la méthode sommetAtteignable 
        for (int i=0; i < this.getNombreSommets(); i++) {
            if ((this.graphe[numeroSommet][i] != 0) && (!marques[i])) {
                this.parcoursProfondeur (i,marques);
            }
        }
	}
	
	/**
	 * Pour un graphe donne, va tester si le sommet numeroSommetArrivee est accessible a partir
	 * du sommet numeroSommetDepart
	 * 
	 * @param numeroSommetDepart : le numero du sommet de depart
	 * @param numeroSommetArrivee : le numero du sommet d'arrivee
	 *	 
	 * @return true si le sommet est accessible, false sinon
	 */
	 
	boolean sommetAtteignable(int numeroSommetDepart, int numeroSommetArrivee) {
		// à completer/modifier
      boolean[] marques = new boolean[this.getNombreSommets()];
	
	  this.parcoursProfondeur (numeroSommetDepart, marques);
      return (marques[numeroSommetArrivee]);
    }

	/**
	 * Pour un graphe donne, va tester s'il comporte un circuit ou non.
	 * 
	 * @return true s'il y a un circuit dans le graphe, false sinon
	 */
	 	 
	boolean circuitExiste () {   
		// à completer/modifier
		
        boolean presenceDeCircuit=false;
			
		// Pour tous sommets i et j différents ; si on peut aller de i à j et de j à i, alors nous avons un cycle partant de i et arrivant en i.
		// i ----> ... ----> ... j 
		// j ----> ... ----> ... i 
		
		int i,j;
		i=0;
		j=0;                  // algorithme assez gourmand, mais relativement simple...
		while ((i<this.getNombreSommets()) && (!presenceDeCircuit)) {
			while ((j<this.getNombreSommets()) && (!presenceDeCircuit)) {
				if ((i!=j) && sommetAtteignable(i,j) && sommetAtteignable(j,i))
					presenceDeCircuit=true;
				j++;
			}
			i++;
		}
					
		return presenceDeCircuit;
	}
	
	/**
	 * Va tester si le graphe est connexe ou non.
	 * 
	 * @return true si le graphe est connexe, false sinon
	 */
	
	boolean estConnexe () {
		if (getOriente()) {
			System.out.println ("Attention vous testez un graphe oriente !!!"); // on pourrait aussi faire un throw...
			return false;
		}
		else {
			boolean [] marques = new boolean [this.getNombreSommets()];

			// lancer un parcours en profondeur sur n'importe quel sommet
			// prenons arbitrairement le premier...
			this.parcoursProfondeur(0,marques);

			// Si l'on atteint tous les sommets, le graphe est connexe.
			// Autrement dit, le tableau de marques doit etre entierement a true.			
			int i=0; 
			boolean grapheConnexe=true;   
			while (i<this.getNombreSommets()&&grapheConnexe) {
				grapheConnexe=marques[i];   
				i++;
			}
			return grapheConnexe;		
		}
		
	}

	/**
	 * Va tester si le graphe est fortement connexe ou non.
	 * 
	 * @return true si le graphe est fortement connexe, false sinon
	 */
	 	
	boolean estFortementConnexe () {
		if (!getOriente()) {
			System.out.println ("Attention vous testez un graphe non oriente !!!"); // on pourrait aussi faire un throw...
			return false;
		}
		else {
			// pour tous sommets i et j, il faut qu'on puisse aller de i a j et reciproquement de j a i
			boolean grapheFortementConnexe=true;
			int i=0;
			int j=0;
			// bon cette initialisation du j sera faite 2 fois, mais cousu 2 fois, ca tient mieux :-)
				//proverbe lorrain...

			while (i<this.getNombreSommets() && grapheFortementConnexe) {
				j=0;  
				while (j<this.getNombreSommets() && grapheFortementConnexe) {
					grapheFortementConnexe=this.sommetAtteignable(i,j)&&this.sommetAtteignable(j,i);
					j++;
				}
				i++;
			}
			return grapheFortementConnexe;
		}
					
					
	}

	/**
	 * Renvoie le nombre de composantes connexes.
	 * 
	 * @return le nombre de composantes connexes.
	 */
	 
	int nombreComposantesConnexes () {
   // Dans le projet on parle de l'algorithme de la pieuvre : on cherche
   // à avoir le plus possible de routes, tout en restant connexe.
		if (getOriente()) {
			System.out.println ("Attention vous testez un graphe oriente !!!"); // on pourrait aussi faire un throw...
			return -1;
		}
		else {
			// Comme explique en cours, lancer un parcours en profondeur donne la composante connexe associee a un sommet donne.
			// Donc, tant que tous les sommets ne sont pas atteints, on relance.
			// Chaque fois que l'on doit relancer, ca fait une composante connexe en plus...
			boolean [] marques = new boolean [this.getNombreSommets()];

			int nombreComposantesConnexes=0;  
			for (int i=0; i<this.getNombreSommets(); i++) 
				if (!marques[i]) {
					parcoursProfondeur(i,marques);
					nombreComposantesConnexes++;
				}
			
			return nombreComposantesConnexes;
		}	
	}

	/**
	 * Va tester si le graphe est "connexe ressitant".
	 * 
	 * @return true si le graphe est "connexe ressitant", false sinon.
	 */
	boolean estConnexeResistant () {
		// principe: 
		//    pour toutes les arêtes     et    tant que le graphe reste connexe
		//       détruire temporairement l'arete courante
		//       tester si le graphe est connexe
		//       remettre l'arete precedemment detruite
		// 
		
		int i=0;
		int j=0;
		boolean estConnexeResistant=true;
		while ((i < this.getNombreSommets() ) &&  estConnexeResistant) {
			j=0;
			while ((j < this.getNombreSommets() ) &&  estConnexeResistant) {
				if (this.graphe[i][j]==1) {
					this.graphe[i][j]=0;   // détruire temporairement l'arete courante
					this.graphe[j][i]=0; 
					estConnexeResistant=this.estConnexe(); // le graphe reste-t-il connexe ?
					this.graphe[i][j]=1;   // remettre l'arete précédemment détruite
					this.graphe[j][i]=1; 
				}
				j++;
			}
			i++;
		}
		return estConnexeResistant;
	}
	
	/** 
	*
	* Servira pour le dernier exercice "points de coupure"
	* 
	* crée un second graphe copie conforme du graphe courant mais sans le sommet de numero numSommet
	*
	* @param numSommet le numero du sommet à supprimer
	* @return le graphe sans le sommet de numero numSommet
	*/

	Graphe dupliqueEtSupprimeSommet (int numSommet) {      
	 	// creer un graphe avec this.getNombreSommets() -1
			Graphe grapheTemporaire = new Graphe(this.getNombreSommets()-1,false);  
			
			int iGrapheDepart=0;   // indice ligne dans la matrice de départ
			int jGrapheDepart=0;   // indice colonne dans la matrice de départ
			int iGrapheTemporaire=0; // indice ligne dans la matrice temporaire (à un moment, il va prendre 1 unité de retard, 
			                         // ce qui va autoriser la suppression de la ieme ligne)
			int jGrapheTemporaire=0; // indice colonne dans la matrice temporaire (à un moment, il va prendre 1 unité de retard, 
			                         // ce qui va autoriser la suppression de la jeme colonne)			
									 
			while (iGrapheDepart<this.getNombreSommets()) {
				jGrapheDepart=0; 
				jGrapheTemporaire=0;
				if (iGrapheDepart==numSommet)
					iGrapheDepart++;// ici, on saute une ligne : iGrapheTemporaire reste inchangé
					
				while (jGrapheDepart<this.getNombreSommets()) {
					if (jGrapheDepart==numSommet) 
						jGrapheDepart++;  // ici, on saute une colonne : jGrapheTemporaire reste inchangé
						
						// le double test sert à éviter que l'on sorte de la matrice à recopier...
						// risque qui est induit par le fait que l'on fait iGrapheDepart++  et jGrapheDepart++
						// bon y a peut-être plus simple, mais c'est pas si trivial que ça...
						if ((iGrapheDepart<this.getNombreSommets()) && (jGrapheDepart<this.getNombreSommets()) ) 
							grapheTemporaire.getGraphe()[iGrapheTemporaire][jGrapheTemporaire]=this.graphe[iGrapheDepart][jGrapheDepart];

						jGrapheDepart++;
						jGrapheTemporaire++;
					}
				iGrapheDepart++;
				iGrapheTemporaire++;
			}
			return grapheTemporaire;
	}			
				
	
   /**
	 * Va tester si le graphe resiste aux points de coupure.
	 * 
	 * @return true si le graphe resiste aux points de coupure, false sinon.
	 */
	boolean resisteAuxPointsDeCoupure () {
		// principe: 
		//    Pour tous les sommets
		//        supprimer le ieme sommet : cela passe par la creation d'un nouveau graphe temporaire avec un sommet de moins
		//        tester si le graphe temporaire est connexe ou non 
		//        on s'arrete si par hasard le graphe temporaire n'est plus connexe.
		
		int i=0;
		boolean resisteAuxPointsDeCoupure=true;
		Graphe grapheTemporaire;
		
		while ((i < this.getNombreSommets() ) &&  resisteAuxPointsDeCoupure) {   // pour chaque sommet du graphe, le ième donc 
				grapheTemporaire=this.dupliqueEtSupprimeSommet(i);               // duplication avec le sommet i en moins
				resisteAuxPointsDeCoupure=grapheTemporaire.estConnexe(); 
				i++;
		}
			
		return resisteAuxPointsDeCoupure;
	}
	
	/**
	 * calcul des plus courts chemins en utilisant l'algorithme de Ford.
	 * 
	 * @return : le tableau des poids, c'est-a-dire la valeur des chemins les plus courts
	 *  du sommet de départ (le numero 0) a tous les autres sommets
	 */
	 
	 int[] valeursPlusCourtsCheminsFord () {                 // traduction telle quelle de l'algorithme du cours
		int[] poids = new int [this.getNombreSommets()];
		
		return poids;
	}	
	
	/**
	 * Effectue une duplication du graphe courant.
	 *
	 * @param g : le graphe à dupliquer
	 * 
	 * @return : le graphe duplique.
	 */
	
	Graphe dupliqueGraphe () {  
		Graphe dupliqueGraphe = new Graphe(this.getNombreSommets(),this.getOriente());
		for (int i=0; i<this.getNombreSommets()  ; i++)
			for (int j=0; j<this.getNombreSommets() ; j++)
				dupliqueGraphe.graphe[i][j]=this.graphe[i][j];
		
		return dupliqueGraphe;
	}
	
		
	/**
	 * calcul des plus courts chemins selon l'algorithme de Floyd.
	 * 
	 * @return : la matrice de Floyd contenant les chemins les plus courts de tout sommet i à tout sommet j
	 */ 

	Graphe valeursPlusCourtsCheminsFloyd () {  
		// on commence par dupliquer la matrice de départ, on obtient la matrice intitulee matriceM
		Graphe matriceM = this.dupliqueGraphe();
		
		// changer la diagonale : matriceM[i][i] a 0                           
		// ...
		// ...
		// ...
		
		// toutes les valeurs < 0 donc sans arcs sont passées à + l'infini
		// ...
		// ...
		// ...
					
		// la triple boucle en "blindant" pour éviter des calculs du type Integer.MAX_VALUE + autre chose qui feraient
		// un depassement de capacite
		// ...
		// ...
		// ...

		return matriceM;
	}

	/**
	 * calcul du diametre du graphe.
	 * 
	 * @return : diametre du graphe.
	 */ 

	 int diametreGraphe () {
		// modifier/completer :
		return 0;
	}
	
	/**
	 * calcul du sommet precedent le sommet sommetArrivee dans le chemin le plus court.
	 * 
	 * @return : le sommet precedent le sommet sommetArrivee.
	 */ 
	 
	int rechercheSommetDepart (int sommetArrivee , int[] valeursPlusCourtsChemins) {
		boolean sommetTrouve=false; // sert à localiser le sommet de départ
		int i=0; 
		while ((i<this.getNombreSommets()) && (!sommetTrouve)) {
			if ((this.graphe[i][sommetArrivee]>0) && (valeursPlusCourtsChemins[i]!=Integer.MAX_VALUE) && ((valeursPlusCourtsChemins[i]+this.graphe[i][sommetArrivee])==valeursPlusCourtsChemins[sommetArrivee]))
				sommetTrouve=true;
			else 
				i++;
		}
		
		
		if (!sommetTrouve) {
			System.out.println ("Problème dans le graphe, impossible de remonter le chemin en arrière !!!");
			return -1;
		}
		else
			return i;
	}
	
	
	/**
	 * Affiche le chemin parcouru en partant du sommet d'arrivee.
	 *
	 * @param sommetArrivee : le sommet dont on part pour retrouver le sommet qui l'a precede.
	 * 
	 */
	void afficheCheminParcouru (int sommetArrivee) {
		int [] valeursPlusCourtsCheminsFord = this.valeursPlusCourtsCheminsFord();
		int sommetDepart; // servira à remonter le chemin dans le graphe à partir du sommet d'arrivée

		// completer :
		// ...
		// ...
		// ...

	}
}


