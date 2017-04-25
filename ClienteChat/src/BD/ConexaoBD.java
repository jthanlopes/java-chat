package BD;

import Telas.TelaLogin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

//Início da classe de conexão
public class ConexaoBD {

    private static String status = "Não conectou...";
    //atributo do tipo Connection
    Connection connection = null;

    //Método de Conexão//
    public java.sql.Connection getConexaoMySQL() {

        try {
            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.jdbc.Driver";
            // Configurando a nossa conexão com um banco de dados
            Class.forName(driverName);

            //caminho do servidor do BD
            String serverName = "localhost";

            //nome do seu banco de dados
            String mydatabase;
            mydatabase = "projeto_redes";

            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            //nome de um usuário de seu BD
            String username = "root";

            //sua senha de acesso
            String password = "";

            connection = DriverManager.getConnection(url, username, password);

            //Testa sua conexão//  
            if (connection != null) {
                status = ("STATUS--->Conectado com sucesso!");
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");

            return null;
        }
    }

    //Método que retorna o status da sua conexão//
    public String statusConection() {
        return status;
    }

    //Método que fecha sua conexão//
    public boolean FecharConexao() {
        try {
            getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Método que reinicia sua conexão//
    public java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return getConexaoMySQL();
    }

    public void cadastro() throws SQLException {
        String sql = "SELECT al_cod, al_nome, al_idade, al_curso FROM aluno "
                + "where al_idade > '18' AND al_idade < '27'order by al_curso";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
    }

    public boolean login(String email, String senha) throws SQLException {
        getConexaoMySQL();
        String sql = "SELECT nome, email, senha FROM usuarios "
                + "where email='" + email + "' AND senha='" + senha + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet != null && resultSet.next()) {
            TelaLogin.nomeUsuario = resultSet.getString("nome");
            return true;
        }

        return false;
    }

    public boolean cadastro(String nome, String sobrenome, String email, String senha) throws SQLException {
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
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
