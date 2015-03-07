package exemplo.jpa;

import java.util.Date;

/**
 *
 * @author MASC
 */
public class DatasLimite {
    private Date dataMaxima;
    private Date dataMinima;

    public DatasLimite(Date dataMaxima, Date dataMinima) {
        this.dataMaxima = dataMaxima;
        this.dataMinima = dataMinima;
    }

    public Date getDataMaxima() {
        return dataMaxima;
    }

    public void setDataMaxima(Date dataMaxima) {
        this.dataMaxima = dataMaxima;
    }

    public Date getDataMinima() {
        return dataMinima;
    }

    public void setDataMinima(Date dataMinima) {
        this.dataMinima = dataMinima;
    }
    
    
}
