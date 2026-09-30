import java.io.FileInputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Properties;

public class AccessBdD {

    private Connection connexion;

    public AccessBdD() {
        Properties config = new Properties();
        try (FileInputStream fichier = new FileInputStream("config.properties")) {
            config.load(fichier);   
            String url = config.getProperty("db.url");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");
            connexion = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");
            
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier de configuration : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public Connection getConnexion() {
        return connexion;
    }

    public ResultSet getLesLivresUneCategorie(String libelleCategorie) {
    ResultSet result = null;
    try {
        String sql = "SELECT livre.* FROM livre JOIN categorie ON livre.categorie = categorie.id WHERE categorie.libelle = ?";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, libelleCategorie);
        result = statement.executeQuery();
    } catch (SQLException ex) {
        Logger.getLogger(AccessBdD.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
    }
    public ResultSet getLesLivresUnEditeur(String editeur) {
    ResultSet result = null;
    try {
        String sql = "SELECT * FROM livre WHERE editeur = ?";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, editeur);
        result = statement.executeQuery();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return result;
    }
    
    public int ajoutLivre(String titre, String auteur, String editeur, int annee, int categorie) {
    int rowsInserted = 0;
    try {
        String sql = "INSERT INTO livre (titre, auteur, editeur, annee, categorie) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, titre);
        statement.setString(2, auteur);
        statement.setString(3, editeur);
        statement.setInt(4, annee);
        statement.setInt(5, categorie);
        rowsInserted = statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return rowsInserted;
    }
    public int ajoutCategorie(String libelle) {
    int rowsInserted = 0;
    try {
        String sql = "INSERT INTO categorie (libelle) VALUES (?)";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, libelle);
        rowsInserted = statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return rowsInserted;
    }   
    
   public int modifTitre(int id, String nouveauTitre) {
    int rowsUpdated = 0;
    try {
        String sql = "UPDATE livre SET titre = ? WHERE id = ?";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, nouveauTitre);
        statement.setInt(2, id);
        rowsUpdated = statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return rowsUpdated;
    }   
   public int modifLivre(int id, String titre, String auteur, String editeur, int annee) {
    int rowsUpdated = 0;
    try {
        String sql = "UPDATE livre SET titre = ?, auteur = ?, editeur = ?, annee = ? WHERE id = ?";
        PreparedStatement statement = connexion.prepareStatement(sql);
        statement.setString(1, titre);
        statement.setString(2, auteur);
        statement.setString(3, editeur);
        statement.setInt(4, annee);
        statement.setInt(5, id);
        rowsUpdated = statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return rowsUpdated;
    }
}
