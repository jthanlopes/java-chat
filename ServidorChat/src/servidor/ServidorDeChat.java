package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Jonathan Lopes
 */
public class ServidorDeChat extends Thread {
        
    private static ArrayList clientes = new ArrayList();
    private static ArrayList clientesNome = new ArrayList();
    // socket deste cliente
    private Socket conexao;
    // nome deste cliente
    private String meuNome;
        
    public static void main(String args[]) {
        
        try {
            // criando um socket que fica escutando a porta 3333.
            ServerSocket s = new ServerSocket(3333);
            // Loop principal.
            while (true) {
                // aguarda algum cliente se conectar. A execução do
                // servidor fica bloqueada na chamada do método accept da
                // classe ServerSocket. Quando algum cliente se conectar
                // ao servidor, o método desbloqueia e retorna com um
                // objeto da classe Socket, que é a porta da comunicação.
                System.out.print("Esperando alguem se conectar...");
                Socket conexao = s.accept();
                System.out.println(" Conectou!");                
                // cria uma nova thread para tratar essa conexão
                Thread t = new ServidorDeChat(conexao);
                // Inicia a thread
                t.start();
                // voltando ao loop, esperando mais alguém se conectar.
            }
        } catch (IOException e) {
            // caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Construtor que recebe o socket deste cliente
     * @param s - socket do usuário atual
     */
    public ServidorDeChat(Socket s) {
        conexao = s;
    }

    /**
     * O run() é chamado após executar o método .start() no main.
     * O método run() é sobreescrito para receber o nome do usuário e depois 
     * ficar "escutando" por novas mensagens do usuário.
     */    
    @Override
    public void run() {
        try {
            // objetos que permitem controlar fluxo de comunicação
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            // primeiramente, espera-se pelo nome do cliente
            meuNome = entrada.readLine();
            // agora, verifica se string recebida é valida, pois
            // sem a conexão foi interrompida, a string é null.
            // Se isso ocorrer, deve-se terminar a execução.
            if (meuNome == null) {
                return;
            }
            // Número de usuários conectados
            // saida.println(clientes.size());
            // Uma vez que se tem um cliente conectado e conhecido,
            // coloca-se fluxo de saída para esse cliente no vetor de
            // clientes conectados.
            clientes.add(saida);      
            clientesNome.add(meuNome);
            // clientes é objeto compartilhado por várias threads!
            // De acordo com o manual da API, os métodos são
            // sincronizados. Portanto, não há problemas de acessos
            // simultâneos.
            // Loop principal: esperando por alguma string do cliente.
            // Quando recebe, envia a todos os conectados até que o
            // cliente envie linha em branco.
            // Verificar se linha é null (conexão interrompida)
            // Se não for nula, pode-se compará-la com métodos string
            sendToAll(saida, " entrou ", "no chat");
            String linha = entrada.readLine();          
            while (linha != null && !(linha.trim().equals(""))) {
                // reenvia a linha para todos os clientes conectados
                sendToAll(saida, " disse > ", linha);
                // espera por uma nova linha.
                linha = entrada.readLine();             
            }
            // Uma vez que o cliente enviou linha em branco, retira-se
            // fluxo de saída da arrayList de clientes e fecha-se conexão.
            sendToAll(saida, " saiu ", "do chat!");
            clientes.remove(saida);
            clientesNome.remove(meuNome);
            conexao.close();
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Envia as mensagens para todos os usuários, menos para o próprio
     * @param saida - PrintStream usuário
     * @param acao - essa variável é controlada pelo servidor, possíveis valores:
     * disse, saiu.
     * @param linha - mensagem enviada pelo cliente e que será enviada para os demais.
     * @throws IOException 
     */
    public void sendToAll(PrintStream saida, String acao, String linha) throws IOException {
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(clientes.get(i));
            PrintStream chat = (PrintStream) clientes.get(i);

            if (chat != saida) {
                chat.println(meuNome + acao + linha);           
            }

        }
    }
    
     public void sendToOne(PrintStream saida, String acao, String linha, String destinatario) throws IOException {
        for (int i = 0; i < clientesNome.size(); i++) {
            System.out.println(clientes.get(i));
            PrintStream chat = (PrintStream) clientes.get(i);

            if (chat != saida) {
                chat.println(meuNome + acao + linha);  
            }

        }
    }
    
  
}
