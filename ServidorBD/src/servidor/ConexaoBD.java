package servidor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import servidor.ServidorBD;

// Início da classe de conexão.
public class ConexaoBD {

    private static String status = "Não conectou...";
    // Atributo do tipo Connection.
    Connection connection = null;

    /**
     * Método de Conexão com o banco de dados.
     *
     * @return Uma váriável com a conexão.
     */
    public java.sql.Connection getConexaoMySQL() {

        try {
            // Carregando o JDBC Driver padrão.
            String driverName = "com.mysql.jdbc.Driver";
            // Configurando a nossa conexão com um banco de dados.
            Class.forName(driverName);

            // Caminho do servidor do BD.
            String serverName = "localhost";

            // Nome do seu banco de dados.
            String mydatabase;
            mydatabase = "projeto_redes";

            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            // Nome de um usuário do BD.
            String username = "root";

            // Senha de acesso
            String password = "";

            connection = DriverManager.getConnection(url, username, password);

            // Testa a conexão
            if (connection != null) {
                status = ("STATUS>Conectado com sucesso!");
            } else {
                status = ("STATUS>Não foi possivel realizar conexão");
            }

            return connection;

        } catch (ClassNotFoundException e) {  // Driver não encontrado.
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            // Não conseguindo se conectar ao banco.
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");

            return null;
        }
    }

    /**
     * Método que retorna o status da conexão.
     *
     * @return Uma String: conectado com sucesso ou Erro na conexão.
     */
    public String statusConection() {
        return status;
    }

    /**
     * Método que fecha a conexão.
     *
     * @return True se conseguir fechar a conexão, ou false se não conseguir
     * fechar.
     */
    public boolean FecharConexao() {
        try {
            getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Método que reinicia a conexão.
     *
     * @return Para o método getConexaoMySQL().
     */
    public java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return getConexaoMySQL();
    }

    /**
     * Verifica se o email e senha do usuário estão no banco de dados.
     *
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @return True se o usuário existir no banco, ou false caso não esteja no
     * banco.
     * @throws SQLException
     */
    public boolean login(String email, String senha) throws SQLException {
        getConexaoMySQL();
        String sql = "SELECT nome, email, senha FROM usuarios "
                + "where email='" + email + "' AND senha='" + senha + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet != null && resultSet.next()) {
            ServidorBD.nome = resultSet.getString("nome");
            return true;
        }

        return false;
    }

    /**
     * Cadastra o usuário no banco de dados.
     *
     * @param nome Nome do usuário.
     * @param sobrenome Sobrenome do usuário.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @return True se o cadastro for efetuado com sucesso.
     * @throws SQLException
     */
    public boolean cadastro(String nome, String sobrenome, String email, String senha) throws SQLException {
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyyMMdd");
        //System.out.print(formatarDate.format(data));
        getConexaoMySQL();
        String sql = "INSERT INTO usuarios() "
                + "VALUES(null, '" + nome + "', '" + sobrenome + "', '" + email + "', '" + senha + "', '" + formatarDate.format(data) + "' )";
        System.out.println(sql);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            return true;
        }
    }
}
