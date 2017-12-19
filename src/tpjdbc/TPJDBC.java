/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpjdbc;

import java.sql.*;


/**
 *
 * @author fvanrell
 */
public class TPJDBC {
/*jdbc:oracle:thin:@licinfo.univ-jfc.fr:1521:pedago*/
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try {
            Class.forName((String) "oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Ok");
        
        }catch (java.lang.ClassNotFoundException e)
            {System.err.println("erreur : " + e.getMessage());}
        
        String url = "jdbc:oracle:thin:@licinfo.univ-jfc.fr:1521:pedago" ;
        String user = "ISIS3_16";
        String mdp = "isis";
       
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Système charge et connecté au serveur");
        }
        catch (ClassNotFoundException cnfe)
            {System.out.println("Driver manquant");}
        
        Connection connexion = null;
        
        try {
            connexion= DriverManager.getConnection(url, user, mdp);
            if (connexion != null) {
                System.out.println("Vous êtes connecté");
            }
            else {
                System.out.println("Vous êtes déjà connecté");
            }
        }
        catch (SQLException se)
            {System.out.println("Erreur : bd manquante ou connexion invalide");}  
        
        /*AfficheTableAvion(connexion);
        AfficheTablePilote(connexion);
        SalaireMoyenPilotes(connexion);
        CapaciteAvions(connexion);
        MajLocalisation(connexion);
        afficheAvion(connexion);*/
        insertionAvion(11, "A380", 366, connexion);

        if (connexion != null) {
            System.out.println("Vous êtes connecté");
            try{
                connexion.close();
                System.out.println("Vous allez être déconnecté");
            }
            catch (Exception e)
                {System.out.println("Deconnexion impossible");}
        }
          
    }  
    
    public static void AfficheTableAvion(Connection connexion) throws SQLException{
        String requete = "select avnum, avnom, capacite, localisation from avion";
        try {
            Statement st = null;
            st = connexion.createStatement();
            ResultSet rset = st.executeQuery(requete);
            
            if (rset != null){
                while (rset.next()) {
                    System.out.println("\t Numéro : " + rset.getInt("avnum") + "\t");
                    System.out.println("\t Nom : " + rset.getString("avnom") + "\t");
                    System.out.println("\t Capacité : " + rset.getInt("capacite") + "\t");
                    System.out.println("\t Localisation : " + rset.getString("localisation") + "\t");
                    System.out.println("\n---------------------------------\n");
                }
            }
            else {
                throw new SQLException("Il n'y a pas de résultat à la requête");
            }
            
            rset.close(); //fermeture de l'instance ResultSet
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }      
    }
    
    public static void AfficheTablePilote(Connection connexion) throws SQLException{
        String requete = "select plnum, plnom, plprenom, ville, datenaiss, salaire from pilote";
        try {
            Statement st = null;
            st = connexion.createStatement();
            ResultSet rset = st.executeQuery(requete);
            
            if (rset != null){
                while (rset.next()) {
                    System.out.println("\t Numéro : " + rset.getInt("plnum") + "\t");
                    System.out.println("\t Nom : " + rset.getString("plnom") + "\t");
                    System.out.println("\t Prenom : " + rset.getString("plprenom") + "\t");
                    System.out.println("\t Ville : " + rset.getString("ville") + "\t");
                    System.out.println("\t Date naissance : " + rset.getDate("datenaiss") + "\t");
                    System.out.println("\t Salaire : " + rset.getInt("salaire") + "\t");
                    System.out.println("\n---------------------------------\n");
                }
            }
            else {
                throw new SQLException("Il n'y a pas de résultat à la requête, ou une erreur");
            }
            
            rset.close(); //fermeture de l'instance ResultSet
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }      
    }
    
    public static void SalaireMoyenPilotes(Connection connexion)throws SQLException{
        String requete = "select avg(salaire) from pilote";
        try {
            Statement st = null;
            st = connexion.createStatement();
            ResultSet rset = st.executeQuery(requete);
            
            if (rset != null){
                while (rset.next()) {
                    System.out.println("\t Salaire moyen : " + rset.getDouble("avg(salaire)") + "\t");
                    System.out.println("\n---------------------------------\n");
                }
            }
            else {
                throw new SQLException("Il n'y a pas de résultat à la requête, ou une erreur");
            }
            
            rset.close(); //fermeture de l'instance ResultSet
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }      
    }
    
    public static void CapaciteAvions(Connection connexion)throws SQLException{
        String requete = "select sum(capacite) from Avion";
        try {
            Statement st = null;
            st = connexion.createStatement();
            ResultSet rset = st.executeQuery(requete);
            
            if (rset != null){
                while (rset.next()) {
                    System.out.println("\t Capacite totale : " + rset.getInt("sum(capacite)") + "\t");
                    System.out.println("\n---------------------------------\n");
                }
            }
            else {
                throw new SQLException("Il n'y a pas de résultat à la requête, ou une erreur");
            }
            
            rset.close(); //fermeture de l'instance ResultSet
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }
    }
    
    public static void MajLocalisation(Connection connexion)throws SQLException{
        String requete = "update Avion set localisation = 'Toulouse' where avnom = 'A300'";
        try {
            Statement st = null;
            st = connexion.createStatement();
            int rset = st.executeUpdate(requete);
            
            System.out.println("nb de mise à jour = " + rset);
            
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }
    }
    
    public static void afficheAvion(Connection connexion)throws SQLException{
        String requete = "select localisation from Avion where avnom = 'A300'";
        try {
            Statement st = null;
            st = connexion.createStatement();
            ResultSet rset = st.executeQuery(requete);
            
            if (rset != null){
                while (rset.next()) {
                    System.out.println("\t Localisation : " + rset.getString("localisation") + "\t");
                    System.out.println("\n---------------------------------\n");
                }
            }
            else {
                throw new SQLException("Il n'y a pas de résultat à la requête, ou une erreur");
            }
            
            rset.close(); //fermeture de l'instance ResultSet
            st.close(); //fermeture de l'instance Statement
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }
    }
    
    public static void insertionAvion(int no_av, String nom, int capacite, Connection connexion) throws SQLException{
        String requetep = "insert into Avion values ("+no_av+",'"+nom+"',"+capacite+",null)";
        try {
            PreparedStatement pst = connexion.prepareStatement(requetep);
            pst.setInt(1, no_av);
            pst.setString(2, nom);
            pst.setInt(3,capacite);
            int count = pst.executeUpdate();
                
            if (count==0){
                throw new SQLException("Insertion impossible");
            }
            
            pst.close(); //fermeture de l'instance Statement
            
        } catch(SQLException e) {
            System.err.println("Impossible!");
        }
    }
}
