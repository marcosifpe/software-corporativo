/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.servico;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marcos
 * @param <T>
 */
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public abstract class Servico<T extends Object> {

    @PersistenceContext(name = "exemplo_09", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;

    @TransactionAttribute(NOT_SUPPORTED)
    protected void setClasse(@NotNull Class<T> classe) {
        this.classe = classe;
    }
    
    @TransactionAttribute(SUPPORTS)
    public boolean existe(@NotNull T entidade) {
        return true;
    }

    public void persistir(@Valid T entidade) {
        if (!existe(entidade)) {
            entityManager.persist(entidade);
        }
    }

    public T atualizar(@Valid T entidade) {
        if (existe(entidade)) {
            entidade = entityManager.merge(entidade);
            entityManager.flush();
        }
        
        return entidade;
    }

    @TransactionAttribute(SUPPORTS)
    public T consultarPorId(@NotNull Long id) {
        return entityManager.find(classe, id);
    }

    @TransactionAttribute(SUPPORTS)
    public T consultarEntidade(Object[] parametros, String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return (T) query.getSingleResult();
    }

    @TransactionAttribute(SUPPORTS)
    public List<T> consultarEntidades(Object[] parametros, String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }
}
