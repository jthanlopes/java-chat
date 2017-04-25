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
    // parte que controla a recepção de mensagens deste cliente
    private Socket conexao;  
                   
     // construtor que recebe o socket deste cliente
    public ClienteDeChat(Socket s) {
        conexao = s;
    }
     
    public Socket getConexao() {
        return conexao;
    }
    
    public boolean getDone() {
        return done;
    }
    
    public void setDone(boolean valor) {
        done = valor;
    }
    
    // execução da thread
    public void run() {        
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
                // pega o que o servidor enviou
                linha = entrada.readLine();
                // verifica se é uma linha válida. Pode ser que a conexão
                // foi interrompida. Neste caso, a linha é null. Se isso
                // ocorrer, termina-se a execução saindo com break
                if (linha == null) {
                    System.out.println("Conexão encerrada!");
                    break;
                }
                // caso a linha não seja nula, deve-se imprimi-la                      
                jtaMsgs.setText(jtaMsgs.getText() + "\n" + linha);
            }
        } catch (IOException e) {
            // caso ocorra alguma exceção de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
        // sinaliza para o main que a conexão encerrou.
        done = true;
    }

}
