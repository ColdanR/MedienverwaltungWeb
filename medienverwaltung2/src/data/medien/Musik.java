package data.medien;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.Person;
import data.SpeicherFormatInterface;

public class Musik {
	private	int								id				=	0;
	private	Person							interpret		=	null;
	private String							titel			=	null;
	// private List<Album>						alben			=	new ArrayList<Album>();
	private	LocalDate						ersterscheinung	=	null;
	// private	List<Person>					mitwirkende		=	new ArrayList<Person>();
	private	List<SpeicherFormatInterface>	speicherformate	=	new ArrayList<SpeicherFormatInterface>();
	private	boolean							live			=	false;
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
	 * @return the interpret
	 */
	public Person getInterpret() {
		return interpret;
	}
	/**
	 * @param interpret the interpret to set
	 */
	public void setInterpret(Person interpret) {
		this.interpret = interpret;
	}
	/**
	 * @return the titel
	 */
	public String getTitel() {
		return titel;
	}
	/**
	 * @param titel the titel to set
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}
	/**
	 * @return the alben
	 */
	/*public List<Album> getAlben() {
		return alben;
	}*/
	/**
	 * @param alben the alben to set
	 */
	/*public void setAlben(List<Album> alben) {
		this.alben = alben;
	}*/
	/**
	 * @return the ersterscheinung
	 */
	public LocalDate getErsterscheinung() {
		return ersterscheinung;
	}
	/**
	 * @param ersterscheinung the ersterscheinung to set
	 */
	public void setErsterscheinung(LocalDate ersterscheinung) {
		this.ersterscheinung = ersterscheinung;
	}
	/**
	 * @return the mitwirkende
	 */
	/*public List<Person> getMitwirkende() {
		return mitwirkende;
	}*/
	/**
	 * @param mitwirkende the mitwirkende to set
	 */
	/*public void setMitwirkende(List<Person> mitwirkende) {
		this.mitwirkende = mitwirkende;
	}*/
	/**
	 * @return the speicherformate
	 */
	public List<SpeicherFormatInterface> getSpeicherformate() {
		return speicherformate;
	}
	/**
	 * @param speicherformate the speicherformate to set
	 */
	public void setSpeicherformate(List<SpeicherFormatInterface> speicherformate) {
		this.speicherformate = speicherformate;
	}
	/**
	 * @return the live
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * @param live the live to set
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
}
