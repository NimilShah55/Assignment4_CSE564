import java.awt.Color;


/**
 * Factory interface for creating an object.
 * @author Nimil Shah
 */
public interface Factory {
	public City createCity(String name, int cityX, int cityY, String size, Color colour,String type);
}
