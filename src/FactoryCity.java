import java.util.ArrayList;
import java.awt.Color;
public class FactoryCity implements Factory {
	private static FactoryCity fc;

	private FactoryCity() {
		
	}
	
	public static FactoryCity getFC() {
		if (fc == null) {
			fc = new FactoryCity();
		}
		return fc;
	}
	
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
