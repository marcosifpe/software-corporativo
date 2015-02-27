package ejb.teste;

import ejb.dominio.Estado;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author MASC
 */
@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EstadosBean implements EstadoBeanLocal {
    @PersistenceContext
    private EntityManager em;
    
    private List<Estado> estados;
    
    @PostConstruct   
    public void inicializar() {
        criarEstados();
        carregarEstados();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void criarEstados() {       
        try {
            Properties properties = new Properties();
            properties.load(EstadosBean.class.getResourceAsStream("estados.properties"));
            Set<Object> chaves = properties.keySet();
            
            for (Object chave : chaves) {
                Estado estado = new Estado();
                estado.setSigla(chave.toString());
                estado.setNome(properties.getProperty(chave.toString()));
                em.persist(estado);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void carregarEstados() {
        TypedQuery<Estado> query = em.createQuery("SELECT e FROM Estado e ORDER BY e.sigla", Estado.class);
        this.estados = query.getResultList();
    }

    @Override
    public List consultarEstador() {
        return estados;
    }
}
