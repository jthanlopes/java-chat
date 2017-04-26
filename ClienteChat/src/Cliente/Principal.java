package Cliente;

import BD.ConexaoBD;
import Telas.TelaLogin;

/**
 *
 * @author Jonathan Lopes
 */
public class Principal {

    /** 
     * Classe principal que faz a conexão com o banco de dados, também
     * instância e exibe a tela de login.
     * @param args 
     */
    public static void main(String[] args) {
        ConexaoBD conecta = new ConexaoBD();
        conecta.getConexaoMySQL();               
        System.out.println(conecta.statusConection());
        
        TelaLogin login = new TelaLogin();        
        login.setVisible(true);
    }
    
}
