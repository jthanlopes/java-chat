package Cliente;

import BD.ConexaoBD;
import Telas.TelaLogin;

/**
 *
 * @author jonsanto1
 */
public class Principal {

    public static void main(String[] args) {
        ConexaoBD conecta = new ConexaoBD();
        conecta.getConexaoMySQL();               
        System.out.println(conecta.statusConection());
        
        TelaLogin login = new TelaLogin();        
        login.setVisible(true);
    }
    
}
