package tud.ai1.pacman.model.level;

import tud.ai1.pacman.exceptions.InvalidLevelCharacterException;
import tud.ai1.pacman.exceptions.InvalidLevelFormatException;
import tud.ai1.pacman.exceptions.NoPacmanSpawnPointException;
import tud.ai1.pacman.util.Consts;
import tud.ai1.pacman.util.FileOperations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Laedt einen Level aus einer Datei oder einem String.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Robert Cieslinski
 * @author Kurt Cieslinski
 */
public class LevelParser {

    /**
     * Laedt den Level aus einer Datei.
     *
     * @throws InvalidLevelCharacterException falls der eingelesene Level ein unbekanntes Zeichen enthaelt
     * @throws InvalidLevelFormatException falls der eingelesene Level nicht rechteckig ist
     * @throws NoPacmanSpawnPointException falls der eingelesene Level keinen Pac-Spawner enthaelt
     */
    public static Level fromFile(String file) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
        // level parsen
    	if(file==null)throw new IllegalArgumentException();
        Level level = fromString(FileOperations.readFile(file));
        level.setName(getLevelName(file));
        return level;
    }

    /**
     * Laedt den Level aus einem String.
     *
     * @throws InvalidLevelCharacterException falls der eingelesene Level ein unbekanntes Zeichen enthaelt
     * @throws InvalidLevelFormatException falls der eingelesene Level nicht rechteckig ist
     * @throws NoPacmanSpawnPointException falls der eingelesene Level keinen Pac-Spawner enthaelt
     */
    public static Level fromString(String content) throws IllegalArgumentException,InvalidLevelCharacterException, InvalidLevelFormatException, NoPacmanSpawnPointException {
    	if(content==null)throw new IllegalArgumentException();
    	// Text in zeilen aufteilen
        //content = content.replaceAll("\r\n", System.lineSeparator());
        String[] lines = content.split(System.lineSeparator());

        List<Point> pacmanSpawnPoints = new ArrayList<>();
        List<Point> ghostSpawnPoints = new ArrayList<>();
        Field[][] map = new Field[lines.length][lines[0].length()];

        char c;
        for (int y = 0; y < lines.length; y++) {
            // nur rechteckige level
            if (lines[y].length() != lines[0].length())
                throw new InvalidLevelFormatException();

            for (int x = 0; x < lines[y].length(); x++) {
                c = lines[y].charAt(x);

                // levelelement pruefen und speichern
                if (Field.isValid(c))
                    map[y][x] = new Field(new Coordinate(x,y),Field.findByValue(c));
                else
                    throw new InvalidLevelCharacterException(c);

                // spawnpunkte extrahieren
                if (c == MapModule.PLAYER_SPAWN.getValue())
                    pacmanSpawnPoints.add(new Point(x, y));
                if (c == MapModule.GHOST_SPAWN.getValue())
                    ghostSpawnPoints.add(new Point(x, y));

            }
        }

        if (!Consts.TEST && pacmanSpawnPoints.isEmpty())
            throw new NoPacmanSpawnPointException();

        return new Level(map, pacmanSpawnPoints.toArray(new Point[0]), ghostSpawnPoints.toArray(new Point[0]));
    }

    /**
     * @param file Dateipfad des Levels
     * @return eine lesbare Version des Levelnamens
     */
    public static String getLevelName(String file) throws IllegalArgumentException {
    	if(file==null)throw new IllegalArgumentException();
        String fname = file;
        // dateinamen filtern
        if (fname.contains("/"))
            fname = fname.substring(fname.lastIndexOf("/") + 1);
        if (fname.contains("\\"))
            fname = fname.substring(fname.lastIndexOf("\\") + 1);
        // dateiendung entfernen
        if (fname.contains("."))
            fname = fname.substring(0, fname.indexOf("."));

        return fname.replace("_", " ");
    }
}
