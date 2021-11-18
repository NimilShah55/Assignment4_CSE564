import java.util.ArrayList;
import java.awt.Color;

/**
 * Factory city that decided which class to instantiate.
 * @author Nimil Shah
 */
public class FactoryCity implements Factory {
	private static FactoryCity fc;

	private FactoryCity() {
		
	}
/**
 * Get City Istance.
 * @return city instance
 */	
	public static FactoryCity getFC() {
		if (fc == null) {
			fc = new FactoryCity();
		}
		return fc;
	}
	/**
 * Creates city.
 * @param Name of city.
 * @param City x.
 * @param City y.
 * @param Colour of city.
 * @param Type of city.
 * @return city instance
 */	
	@Override
	public City createCity(String name, int cityX, int cityY,int size, Color colour,String type) {
		

		if (type.equals("Square")) {
			
			return new BaseCity(cityX, cityY, name, colour, size);

		} else if(type.equals("Circle")) {
			return new BaseCircle(cityX, cityY, name, colour, size);
		}
		
		else if(type.equals("Cross")) {
			return new BaseCross(cityX, cityY, name, colour, size);
		}
		 
		 


	}
}
