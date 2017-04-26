package Socket;

import static Telas.TelaChat.jtaMsgs;
import java.io.*;
import java.net.*;

/**
 *
 * @author Jonathan Lopes
 */
public class ClienteDeChat extends Thread {
    
    // Flag que indica quando se deve terminar a execução.
    private static boolean done = false;
    // Parte que controla a recepção de mensagens deste cliente.
    private Socket conexao;  
                   
    /**
     * Construtor que recebe o socket deste cliente.
     * @param s Recebe variável do tipo socket.
     */
    public ClienteDeChat(Socket s) {
        conexao = s;
    }
     
    /**
     * Retorna a conexão do usuário.
     * @return Uma variável do tipo socket.
     */
    public Socket getConexao() {
        return conexao;
    }
    
    /**
     * Retorna o estado da flag de execução.
     * @return True ou false.
     */
    public boolean getDone() {
        return done;
    }
    
    /**
     * Seta o estado da flag de execução.
     * @param valor Recebe um boolean.
     */
    public void setDone(boolean valor) {
        done = valor;
    }
    
    /**
     * Execução da thread do cliente, fica "escutando" por novas mensagens do servidor.
     */
    public void run() {        
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
                // Pega o que o servidor enviou.
                linha = entrada.readLine();
                // Verifica se é uma linha válida. Pode ser que a conexão
                // foi interrompida. Neste caso, a linha é null. Se isso
                // ocorrer, termina-se a execução saindo com break
                if (linha == null) {
                    System.out.println("Conexão encerrada!");
                    break;
                }
                // Caso a linha não seja nula, deve-se imprimi-la.
                jtaMsgs.setText(jtaMsgs.getText() + "\n" + linha);
            }
        } catch (IOException e) {
            // Caso ocorra alguma exceção de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
        // Sinaliza para o main que a conexão encerrou.
        done = true;
    }

}
