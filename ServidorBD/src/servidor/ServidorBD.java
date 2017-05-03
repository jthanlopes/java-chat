package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Lopes
 */
public class ServidorBD extends Thread {

    // Socket deste cliente
    private Socket conexao;
    // Conexão com o banco de dados
    ConexaoBD conectaBD = new ConexaoBD();
    // Nome do usuário
    public static String nome;

    public static void main(String args[]) {
        try {
            // criando um socket que fica escutando a porta 4444.
            ServerSocket s = new ServerSocket(4444);
            // Loop principal.
            while (true) {
                // Aguarda algum cliente se conectar. A execução do
                // servidor fica bloqueada na chamada do método accept da
                // classe ServerSocket. Quando algum cliente se conectar
                // ao servidor, o método desbloqueia e retorna com um
                // objeto da classe Socket, que é a porta da comunicação.
                System.out.println("Esperando alguem se conectar...");
                Socket conexao = s.accept();
                System.out.println("Conectou!... ");
                // Cria uma nova thread para tratar essa conexão.
                Thread t = new ServidorBD(conexao);
                // Inicia a thread.
                t.start();
                // Voltando ao loop, esperando mais alguém se conectar.
            }
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Construtor que recebe o socket deste cliente
     *
     * @param s - socket do usuário atual
     */
    public ServidorBD(Socket s) {
        conexao = s;
        conectaBD.getConexaoMySQL();
        System.out.println(conectaBD.statusConection());
    }

    /**
     * O run() é chamado após executar o método .start() no main. O método run()
     * é sobreescrito e fica "escutando" por novas mensagens do usuário.     
     */
    @Override
    public void run() {
        try {
            // Objetos que permitem controlar o fluxo de comunicação.
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            while (true) {
                // Servidor fica esperando uma msg do usuário 
                // com o tipo de requisição, login ou cadastro.
                String msgUsuario = entrada.readLine();
                // Testa se a requisição é pra login ou cadastro
                if ("login".equals(msgUsuario)) {
                    // Recebe o email e senha do usuário
                    String email = entrada.readLine();
                    String senha = entrada.readLine();
                    // Chama o método de login, que irá retornar "true" se o 
                    // usuário for encontrado no banco.
                    if (conectaBD.login(email, senha)) {
                        // Retorna o nome do usuário logado para o cliente.
                        saida.println(nome);
                        System.out.println("Login efetuado com sucesso.");
                        break;
                    } else {
                        saida.println("null");
                        System.out.println("Não foi possível fazer o login.");
                    }
                } else {
                    // Recebe nome, sobrenome, email e senha para cadastro
                    String nomeUsuario = entrada.readLine();
                    String sobrenome = entrada.readLine();
                    String email = entrada.readLine();
                    String senha = entrada.readLine();
                    
                    // Chama o método de cadastro e passa os dados do cliente.
                    if (conectaBD.cadastro(nomeUsuario, sobrenome, email, senha)) {
                        saida.println("true");
                        System.out.println("Cadastro efetuado com sucesso.");
                    } else {
                        saida.println("false");
                        System.out.println("Erro ao fazer o cadastro.");
                    }
                }
            }            
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        } catch (SQLException ex) {
            Logger.getLogger(ServidorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    * Fecha a conexão com o ServidorBD
    */
    public void fecharConexao() {
        try {
            conexao.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
