package exemplo.singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

/**
 * @Startup: O bean será instanciado quando a aplicação inicializar.
 * @DependsOn("ConfiguradorBean"): EstadoBean depende de ConfiguradorBean.
 * Em outras palavras, ConfiguradorBean deverá ser instanciado e inicializado 
 * antes de EstadoBean.
 */
@Singleton
@Startup
@DependsOn("ConfiguradorBean")
public class EstadoBean implements EstadoBeanLocal {
    @Resource(name = "jdbc/__ejb_singleton")
    private DataSource dataSource;
    private List<Estado> estados = new ArrayList<>();

    @PostConstruct
    public void inicializar() {
        lerEstados();
    }

    private void lerEstados() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;    
        try {
            con = dataSource.getConnection();            
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT txt_sigla as sigla, txt_nome as nome FROM tb_estado ORDER BY txt_sigla");
            while (rs.next()) {
                Estado estado = new Estado();
                estado.setSigla(rs.getString(1));
                estado.setNome(rs.getString(2));
                estados.add(estado);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            fecharRecursos(rs, stmt, con);
        }
    }

    @Override
    public List<Estado> consultarEstados() {
        return this.estados;
    }
    
    private void fecharRecursos(ResultSet rs, Statement stmt, Connection con) throws RuntimeException {
        try {
            if (rs != null) {
                rs.close();
            }
            
            if (stmt != null) {
                stmt.close();
            }
            
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }



}
