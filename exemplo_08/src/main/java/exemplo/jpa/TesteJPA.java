package exemplo.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo_08");

    static {
        Logger.getGlobal().setLevel(Level.INFO);
    }
}
