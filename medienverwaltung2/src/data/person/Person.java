package data.person;

public class Person {
	private	int		id				=	0;
	private	String	nachname		=	"";
	private	String	vorname			=	"";
	private	String	kuenstlername	=	"";
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the kuenstlername
	 */
	public String getKuenstlername() {
		return kuenstlername;
	}
	/**
	 * @param kuenstlername the kuenstlername to set
	 */
	public void setKuenstlername(String kuenstlername) {
		this.kuenstlername = kuenstlername;
	}
	
	@Override
	public String toString() {
		return "Ausgabe Person: Vorname: " + vorname + " Nachname: " + nachname + " Künstlername: " + kuenstlername;
	}
}
