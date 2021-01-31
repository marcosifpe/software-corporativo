/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
[java:global/test-classes/ejb/vendedorServico, java:global/test-classes/ejb/vendedorServico!exemplo.servico.VendedorServico]
 */
package exemplo.servico;

import exemplo.jpa.Vendedor;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;

/**
 *
 * @author masc1
 */
@Stateless(name = "ejb/vendedorServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class VendedorServico extends Servico<Vendedor> {

    @PostConstruct
    public void init() {
        super.setClasse(Vendedor.class);
    }

    @Override
    public Vendedor criar() {
        return new Vendedor();
    } 
}
