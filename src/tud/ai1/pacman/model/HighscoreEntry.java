package tud.ai1.pacman.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import tud.ai1.pacman.util.Consts;


/**
 * Modelliert einen einzelnen Highscore-Eintrag.
 *
 * @author Kurt Cieslinski
 * @author Thanh Son Bui
 * 
 * @version 2021-05-23
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> 
{    
    /**
     * Datum des gespielten Spiels.
     */
    private final LocalDateTime date;

    /**
     * Formatter um String in LocalDateTime zu formatieren.
     * Verwendet wird das Format Jahr-Monat-Tag Stunde:Minute
     * 
     */
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern(Consts.HS_DATE_PATTERN);

    /**
     *  Nickname des Eintrags 
     */
    private final String name;
    
    /** 
     * Punktzahl des Eintrags 
     */
    private final int points;


    /**
     * Konstruktor, der ein {@link HighscoreEntry_ML} aus einem String mit Informationen erstellt.
     * Dieser Konstruktor kann benutzt werden, um Eintraege aus Zeilen der HighScore Datei zu erstellen.
     * 
     * @param line String mit den Werten fuer den {@link HighscoreEntry_ML} im Format: datum;name;punkte
     */
    public HighscoreEntry(final String data) 
    {
        if (data == null || data.isEmpty()) 
        	throw new IllegalArgumentException("Es wurden keine Daten fuer den Highscore Eintrag gefunden!");
       
        
        String[] fields = data.split(Consts.HS_DELIMITER);
        if (fields.length != 3) 
        	throw new IllegalArgumentException("Aus den Daten kann kein Highscore Eintrag erzeugt werden!");

        try {
            this.date 		= LocalDateTime.parse(fields[0], FORMATTER);
            this.name 		= fields[1];
            this.points 	= Integer.valueOf(fields[2]);
            
            this.validate(this.date, this.name, this.points);
            
          } catch (DateTimeParseException e) {
        	  throw new IllegalArgumentException("Falsches Format fuer Datum!");
          } catch (NumberFormatException e) {
        	  throw new IllegalArgumentException("Falsches Zahlen-Format!");
          }
    }
    
    
    /**
     * Konstruktor der ein {@link HighscoreEntry_ML} aus den uebergebenen Parametern erstellt
     * Bei fehlerhaften Parametern wird eine {@link IllegalArgumentException} geworfen
     * 
     * @param date Spieldatum
     * @param name Name
     * @param points Punktzahl 
     */
    public HighscoreEntry(final LocalDateTime date, final String name, final int points) 
    {
    	this.validate(date, name, points);
        
        this.date = date;
        this.name = name;
        this.points = points;
    }

    /**
     * Hilfsmethode die ueberprueft, ob uebergebene Werte fuer einen HighScore gueltig sind.
     * 
     * @param date Datum an dem der Highscore erspielt wurde
     * @param name Der Name des Spielers muss angegeben sein und ist auf 4 Zeichen beschraenkt
     * @param points Punktzahl die der Spieler erspielt hat
     */
    public void validate(final LocalDateTime date, final String name, final int points) 
    {
    	if (date == null)
    		throw new IllegalArgumentException("Dem Highscore wurde null als Datum uebergeben!");
	    
    	if (name == null || name.trim().isEmpty())
    		throw new IllegalArgumentException("Dem Highscore wurde kein Name uebergeben!");
    	
    	if (name.length() > 4)
    		throw new IllegalArgumentException("Dem Highscore wurde ein zu langer Name uebergeben! (Laenge > 4)");
    	
	    if (points < 0)
	    	throw new IllegalArgumentException("Dem Highscore wurde keine gueltige Punktzahl uebergeben! (Punkte waren < 0)");
	}
    
    /**
     * Vergleichsmethode, die true zurueck gibt, falls das aktuelle Objekt inhaltlich gleich dem uebergebenen 
     * Objekt ist. In jedem anderen Fall wird false zurueckgegeben
     *
     * @param obj Objekt mit dem verglichen wird.
     * @return True wenn Objekte gleich sind
     */
    @Override
    public boolean equals(final Object obj) 
    {
		if (obj == null 	
		|| !(obj instanceof HighscoreEntry)) 
			return false;
		
		
		final HighscoreEntry otherEntry = (HighscoreEntry) obj;
		
		return this.date.equals(otherEntry.date)
				&& this.name == otherEntry.name
				&& this.points == otherEntry.points;
    }

    /**
     * Vergleicht zwei {@link HighscoreEntry_ML} anhand der erspielten Punkte.
     * Haben beide gleich viele Punkte wird nach Datum entschieden, das fruehere Datum kommt zuerst.
     *
     * @param other {@link HighscoreEntry_ML} mit dem dieser Eintrag verglichen werden soll.
     * @return 	Einen Wert kleiner als 0 falls dieser Eintrag mehr Punkte hat als der andere
     * 			und einen Wert groesser 0 falls dieser Eintrag weniger Punkte hat. 
     * 			Sind beide Punktzahlen gleich wird eine Zahl kleiner 0 zurueckgegeben, falls 
     * 			dieser Eintrag zeitlich frueher kommt als der andere. Kommt der andere frueher ist 
     * 			die Zahl groesser als 0. Sind beide auch zeitlich gleich wird 0 zurueckgegeben.
     * 			
     */
    @Override
    public int compareTo(final HighscoreEntry other) 
    {
    	if (other == null) 
    		throw new IllegalArgumentException("Uebergebener HighscoreEntry war null!");

    	 
    	if (other.getPoints() != this.getPoints())
			return other.getPoints() - this.getPoints();
    	else    	 
    		return this.date.compareTo(other.date);
	}

    /**
     * Diese Methode gibt die String-Repraesentation des Objektes zurueck.
     *
     * @return String-Repraesentation des Objektes im Format: datum;name;punkte.
     */
    @Override
    public String toString() 
    {
		return this.getDate() + Consts.HS_DELIMITER + this.name + Consts.HS_DELIMITER + this.points;
    }
    
    /**
     * Getter fuer date als String.
     *
     * @return Datum des Eintrags
     */
    public String getDate() {
    	return this.date.format(FORMATTER);
    }   
    
    /**
     * @return Nickname des Spielers
     */
    public String getName() {
    	return this.name;
    }

    /**
     * @return Punktzahl des Eintrags
     */
    public int getPoints() {
        return this.points;
    }

}
