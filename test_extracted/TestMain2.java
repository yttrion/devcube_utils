public class TestMain2 {

	public static void main(String[] args) {

		Graphe graphePourTesterFloyd  = new Graphe("Graphe pour tester Floyd et calculer le diametre");
		
		Graphe valeursPlusCourtsCheminsFloyd;
		valeursPlusCourtsCheminsFloyd=graphePourTesterFloyd.valeursPlusCourtsCheminsFloyd();
		
		valeursPlusCourtsCheminsFloyd.affiche();
		
		
		
	}	

}
