/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.Oferta;
import exemplo.jpa.Vendedor;
import static exemplo.jpa.test.GenericTest.logger;
import java.text.SimpleDateFormat;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcos
 */
public class QueryUpdateDeleteTest extends GenericTest {

    @Test
    public void queryUpdate() {
        logger.info("Executando queryUpdate()");
        Long id = 6L;
        Query update = em.createQuery("UPDATE Vendedor AS v SET v.dataNascimento = ?1 WHERE v.id = ?2");
        update.setParameter(1, getData(10, 10, 1983));
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT v FROM Vendedor v WHERE v.id = :id";
        TypedQuery<Vendedor> query = em.createQuery(jpql, Vendedor.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Vendedor vendedor = query.getSingleResult();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(simpleDateFormat.format(getData(10, 10, 1983)), simpleDateFormat.format(vendedor.getDataNascimento()));
        logger.info(vendedor.getDataNascimento().toString());
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        logger.info("Executando queryDelete()");
        Long id = (long) 6;
        Query delete = em.createQuery("DELETE FROM Oferta AS o WHERE o.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT o FROM Oferta o WHERE o.id =?1";
        TypedQuery<Oferta> query = em.createQuery(jpql, Oferta.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }
}
