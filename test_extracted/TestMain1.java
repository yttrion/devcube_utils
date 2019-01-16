public class TestMain1 {

	public static void main(String[] args) {

		Graphe valeursPlusCourtsCheminsFord = new Graphe("Graphe pour calcul des plus courts chemins");
		valeursPlusCourtsCheminsFord.affiche();
		
		int[] poids = new int [valeursPlusCourtsCheminsFord.getNombreSommets()];
		
		poids=valeursPlusCourtsCheminsFord.valeursPlusCourtsCheminsFord();
		
		System.out.println("\nOn devrait obtenir ceci :");
		System.out.println("de 0 a 1 distance = 2");
		System.out.println("de 0 a 2 distance = 4");
		System.out.println("de 0 a 3 distance = 3");
		System.out.println("de 0 a 4 distance = 8");
		
		System.out.println("\nVoici ce que l'on obtient :");
		
		for (int i=1; i<valeursPlusCourtsCheminsFord.getNombreSommets(); i++)
			System.out.println ("de 0 a "+i+ " distance = "+poids[i]);
		
	}	

}
