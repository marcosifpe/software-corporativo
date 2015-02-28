package exemplo.singleton;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author MASC
 */
@Singleton
@LocalBean
public class ConfiguradorBean {
    @PostConstruct
    public void inicializar() {
        Logger.getGlobal().info("Realizando alguma tarefa de inicialização do sistema...");
    }
}
