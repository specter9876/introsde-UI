
package process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per addHealthMeasureResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="addHealthMeasureResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthMeasure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addHealthMeasureResponse", propOrder = {
    "healthMeasure"
})
public class AddHealthMeasureResponse {

    protected String healthMeasure;

    /**
     * Recupera il valore della proprietà healthMeasure.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthMeasure() {
        return healthMeasure;
    }

    /**
     * Imposta il valore della proprietà healthMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthMeasure(String value) {
        this.healthMeasure = value;
    }

}
