package tud.ai1.pacman.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tud.ai1.pacman.util.Consts;
import tud.ai1.pacman.util.FileOperations;

/**
 * Speichert den Spiel Highscore.
 *
 * @author Kurt Cieslinski
 * 
 * @version 2021-05-23
 */
public class Highscore
{
    /** 
     * Highscore-Eintraege
     */
    private final List<HighscoreEntry> highscoreEntries;

	/**
	 * Einzige Instanz eines {@link Highscore_ML} 
	 * auch Singelton genannt
	 */
	private static Highscore instance;
    
    /**
     * Methode die genutzt wird um das Highscore Objekt zu nutzen
     * @return das Highscore Objekt
     */
    public static Highscore getInstance() 
	{
		if (instance == null)
			instance = new Highscore();
		
		return instance;
	}
    
    /**
     * Erzugt ein {@link Highscore_ML} Objekt mit den Eintraegen der HighScore Datei. 
     * Falls keine Datei existiert wird sie neu angelegt.
     * Der Konstruktor ist privat, da es 
     * nur eine Instanz geben soll.
     */
    private Highscore() 
    {
        this.highscoreEntries = new ArrayList<>();
        initFile();
        initHighscores();
    }
  
    /**
     * Hilfsmethode die eine neue Highscore Datei anlegt, 
     * falls keine gefunden werden konnte
     */
    private void initFile()
    {
    	File highscoreFile = new File(Consts.HIGHSCORE_FILE);
        if (!highscoreFile.isFile())
        {
			try {
				highscoreFile.createNewFile();
			} catch (IOException e) {
				System.err.println("IOException: Datei zum speichern der Highscores konnte nicht erstellt werden: " + Consts.HIGHSCORE_FILE);
				e.printStackTrace();
			}
        }
    }
    
    /**
     * Hilfsmethode die Eintraege der Highscore Datei der Liste von Highscores hinzufuegt 
     * und dann die Liste sortiert. Falls in der Datei zu viele Eintraege stehen,
     * werden nur so viele Eintraege hinzugefuegt, bis die Liste 
     * ihre maximale Laenge erreicht hat.
     *
     */
    private void initHighscores()
    {   
        String fileContent = FileOperations.readFile(Consts.HIGHSCORE_FILE); 
        String[] lines = fileContent.split(System.lineSeparator());
        
        if (fileContent.trim().isEmpty())
        	return;
        
        for (String line : lines) 
            if (this.highscoreEntries.size() < Consts.HIGHSCORE_DISPLAYED_ENTRIES)
            	this.highscoreEntries.add(new HighscoreEntry(line));
        
        Collections.sort(this.highscoreEntries);
    }
    
    /**
     * Diese Methode prueft, ob fuer die erspielte Punktzahl 
     * ein Eintrag erstellt werden wuerde
     * 
     * @param points erspielte Punktzahl
     * @return true wenn die Punktzahl ein neuer Highscore ist
     */
	public boolean checkNewEntry(int points) 
	{
		if (this.highscoreEntries.isEmpty() 
			|| this.highscoreEntries.size() < Consts.HIGHSCORE_DISPLAYED_ENTRIES)
			return true;
		
		int threshold = this.highscoreEntries.get(this.highscoreEntries.size() - 1).getPoints();
		
		return points > threshold;
	}

    /**
     * Fuegt den HighscoreEntries einen Eintrag hinzu und sortiert die Liste.
     * Falls die maximal Anzahl an Eintraegen ueberschritten wurde, 
     * wird die Liste wieder auf die maximale Anzahl gekuerzt.
     * Danach wird die neue Liste in die Datei gespeichert.
     *
     * @param entry der neue Highscore Eintrag
     */
    public void addHighscore(final HighscoreEntry entry) 
    {
    	if (entry == null)
    		throw new IllegalArgumentException("Es wurde null als HighscoreEntry uebergeben!");
    	
    	this.highscoreEntries.add(entry);

        Collections.sort(this.highscoreEntries);
        
        if (this.highscoreEntries.size() > Consts.HIGHSCORE_DISPLAYED_ENTRIES)
        	this.highscoreEntries.remove(this.highscoreEntries.size() - 1);
    }

    /**
     * Speichert die Highscore-Eintraege in der dafuer vorgesehenen Datei
     * 
     * @param filepath Ein in einem String uebergebener Dateiname oder Pfad.
     */
    public void saveToFile(final String filepath) 
    {
    	if (filepath == null || filepath.trim().isEmpty())
    		throw new IllegalArgumentException("Es wurde kein Pfad uebergeben!");
    		
        String highscores = "";
        for (HighscoreEntry entry : this.highscoreEntries)
        	highscores += entry.toString() + System.lineSeparator();

        FileOperations.writeFile(filepath, highscores);
    }
    
    /**
     * Getter Methode fuer alle gespeicherten Highscores.
     *
     * @return Gibt die Highscore-Liste zurueck.
     */
    public List<HighscoreEntry> getAllEntries() {
      return this.highscoreEntries;
    }

}
