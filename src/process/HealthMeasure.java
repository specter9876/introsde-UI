
package process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per healthMeasure complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="healthMeasure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idHealthMeasure" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idUser" type="{http://soap.document.introsde/}user" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthMeasure", namespace = "http://soap.document.introsde/", propOrder = {
    "date",
    "idHealthMeasure",
    "idUser",
    "type",
    "value"
})
public class HealthMeasure {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    protected long idHealthMeasure;
    protected User idUser;
    protected String type;
    protected double value;

    /**
     * Recupera il valore della proprieta date.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Imposta il valore della proprieta date.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Recupera il valore della proprieta idHealthMeasure.
     *
     */
    public long getIdHealthMeasure() {
        return idHealthMeasure;
    }

    /**
     * Imposta il valore della proprieta idHealthMeasure.
     *
     */
    public void setIdHealthMeasure(long value) {
        this.idHealthMeasure = value;
    }

    /**
     * Recupera il valore della proprieta idUser.
     *
     * @return
     *     possible object is
     *     {@link User }
     *
     */
    public User getIdUser() {
        return idUser;
    }

    /**
     * Imposta il valore della proprieta idUser.
     *
     * @param value
     *     allowed object is
     *     {@link User }
     *
     */
    public void setIdUser(User value) {
        this.idUser = value;
    }

    /**
     * Recupera il valore della proprieta type.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprieta type.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprieta value.
     *
     */
    public double getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprieta value.
     *
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
    	return ""+type+": "+value+"\t("+date.toString()+")";
    }

}
