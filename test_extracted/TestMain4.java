public class TestMain4 {

	public static void main(String[] args) {
		Graphe grapheParcoursGraphe = new Graphe("Graphe pour calcul des plus courts chemins");

		System.out.println ("Voici le chemin de retour a partir du sommet 4");
		System.out.println ("On doit obtenir :");
		System.out.println ("sommet d'arrivee =  4 <--- 2 <--- 3 <--- 1 <--- sommet de depart = 0");
		System.out.println ("Voici ce que l'on obtient :");
		grapheParcoursGraphe.afficheCheminParcouru (4);
	}	
}
