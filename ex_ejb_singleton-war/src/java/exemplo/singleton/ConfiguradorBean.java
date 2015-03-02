package exemplo.singleton;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author MASC
 */
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ConfiguradorBean {
    @PostConstruct
    public synchronized void inicializar() {
        Logger.getGlobal().info("Realizando alguma tarefa de inicialização do sistema...");
    }
}
