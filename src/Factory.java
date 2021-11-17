import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.Color;

public interface Factory {
	public City createCity(String name, int cityX, int cityY, int size, Color colour, String type);
}
