

import java.sql.ResultSet;
import java.sql.SQLException;

public class Principal {
    public static void main(String[] args) throws SQLException {
       AccessBdD bd = new AccessBdD();
        if (bd.getConnexion() != null) {
            System.out.println("Connexion réussie");
            int nb1 = bd.ajoutLivre("Etranger", "Albert Camus", "Gallimard", 1862, 1);
            //int nb2 = bd.ajoutLivre("1984", "George Orwell", "Gallimard", 1949, 1);
            bd.ajoutCategorie("Manga");
            int modif = bd.modifTitre(1, "Le Nouveau Titre");
            System.out.println("Nombre de lignes modifiées : " + modif);
            System.out.println("Nombre de livres insérés : " + (nb1));
            String nomEditeur = "Acte Sud";
            System.out.println("Liste des livres de l'éditeur : " + nomEditeur);
            ResultSet result = bd.getLesLivresUnEditeur(nomEditeur);
            boolean trouve = false; 
            while (result.next()) {
                trouve = true;
                String titre = result.getString(2);
                String auteur = result.getString(3);
                String ed = result.getString(4);
                int annee = result.getInt(5);
                System.out.println(titre + " - " + auteur + " - " + ed + " - " + annee);
            }
            if (!trouve) {
                System.out.println("Aucun livre trouvé pour cet éditeur.");
            }
}
}
}