
package process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per addHealthMeasure complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="addHealthMeasure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idUser" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="healthMeasureIn" type="{http://soap.document.introsde/}healthMeasure" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addHealthMeasure", propOrder = {
    "idUser",
    "healthMeasureIn"
})
public class AddHealthMeasure {

    protected Long idUser;
    protected HealthMeasure healthMeasureIn;

    /**
     * Recupera il valore della proprietà idUser.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * Imposta il valore della proprietà idUser.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdUser(Long value) {
        this.idUser = value;
    }

    /**
     * Recupera il valore della proprietà healthMeasureIn.
     * 
     * @return
     *     possible object is
     *     {@link HealthMeasure }
     *     
     */
    public HealthMeasure getHealthMeasureIn() {
        return healthMeasureIn;
    }

    /**
     * Imposta il valore della proprietà healthMeasureIn.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthMeasure }
     *     
     */
    public void setHealthMeasureIn(HealthMeasure value) {
        this.healthMeasureIn = value;
    }

}
