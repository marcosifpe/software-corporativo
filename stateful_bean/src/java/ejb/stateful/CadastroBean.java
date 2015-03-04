package ejb.stateful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.sql.DataSource;

/**
 *
 * @author MASC
 */
@Stateful
public class CadastroBean implements CadastroBeanRemote {

    @Resource(name = "jdbc/__ejb_singleton")
    private DataSource ds;
    private Connection conexao;
    private Usuario usuario;

    @PostConstruct
    @PostActivate
    public void inicializar() {
        try {
            conexao = ds.getConnection();
        } catch (SQLException ex) {
            criarRuntimException(ex);
        }
    }

    @Override
    public boolean conexaoAtiva() {
        try {
            return conexao.isValid(0);
        } catch (SQLException ex) {
            criarRuntimException(ex);
        }

        return false;
    }

    @Override
    public void criarUsuario(String login, String senha) {
        this.usuario = new Usuario(login, senha);
    }

    @Override
    public void criarEnderecoUsuario(String cep, String logradouro, Integer numero) {
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }

        this.usuario.criarEndereco(cep, logradouro, numero);
    }
    
    @Remove
    public void cancelar() {
        usuario = null;
    }

    @Remove
    @Override
    public void cadastrarUsuario() {
        StringBuilder sql = new StringBuilder("INSERT INTO tb_usuario ");
        sql.append("(txt_login, txt_senha, txt_cep, txt_logradouro, nm_numero) ");
        sql.append("VALUES (?, ?, ?, ?, ?)");
        PreparedStatement pstmt = null;
        Endereco endereco = usuario.getEndereco();

        try {
            pstmt = conexao.prepareStatement(sql.toString());
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, endereco.getCep());
            pstmt.setString(4, endereco.getLogradouro());
            pstmt.setInt(5, endereco.getNumero());
            pstmt.executeUpdate();
            conexao.commit();
        } catch (SQLException ex) {
            rollback();
            criarRuntimException(ex);
        } finally {
            fechar(pstmt);
        }
    }

    private void fechar(PreparedStatement pstmt) throws RuntimeException {
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException ex) {
            criarRuntimException(ex);
        }
    }

    private void rollback() throws RuntimeException {
        try {
            conexao.rollback();
        } catch (SQLException ex) {
            criarRuntimException(ex);
        }
    }

    @PreDestroy
    @PrePassivate
    public void liberarRecursos() {
        try {
            conexao.close();
            conexao = null;
            ds = null;
        } catch (SQLException ex) {
            criarRuntimException(ex);
        }
    }

    private void criarRuntimException(SQLException ex) throws RuntimeException {
        Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        throw new RuntimeException(ex);
    }

}
