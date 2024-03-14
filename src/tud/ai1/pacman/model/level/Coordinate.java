package tud.ai1.pacman.model.level;

/**
 * Die Klasse repraesentiert die Koordinaten. Sie hat die Werte x und y (kartesisches
 * Koordinatensystem).
 * 
 * @author Maximilian Kratz
 * @version 2020-05-13
 */
public class Coordinate implements ICoordinate {
  /**
   * X-Koordinate.
   */
  private int xval;

  /**
   * Y-Koordinate.
   */
  private int yval;

  /**
   * Konstruktor fuer das Initialisieren einer {@link Coordinate}.
   * 
   * @param x X-Wert der Koordinate.
   * @param y Y-Wert der Koordinate.
   */
  public Coordinate(final int x, final int y) {
    this.xval = x;
    this.yval = y;
  }

  @Override
  public int getX() {
    return xval;
  }

  @Override
  public void setX(final int x) {
    this.xval = x;
  }

  @Override
  public int getY() {
    return yval;
  }

  @Override
  public void setY(final int y) {
    this.yval = y;
  }

}
