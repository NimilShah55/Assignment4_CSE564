import java.awt.Color;

/**
 * Factory city decides which class to instantiate.
 * @author Nimil Shah
 */
public class FactoryCity implements Factory {
	private static FactoryCity fc;

	private FactoryCity() {}
	
        /**
         * Get City Instance.
         * @return city instance
         */	
	public static FactoryCity getFC() {
		if (fc == null) {
			fc = new FactoryCity();
		}
		return fc;
	}
	
	/**
         * Creates and returns a new city instance.
         * @param name Name of city
         * @param cityX X pos of city
         * @param cityY Y pos of city
         * @param size Size of city
         * @param colour Color of city
         * @param type Type of city
         * @return New city instance.
         */	
	@Override
	public City createCity(String name, int cityX, int cityY, String size, Color colour,String type) {
            if (type.equals("Square")) {
                    return new BaseCity(cityX, cityY, name, colour, size);
            } else if(type.equals("Circle")) {
                    return new CircleCity(cityX, cityY, name, colour, size);
            }else if(type.equals("Cross")) {
                    return new CrossCity(cityX, cityY, name, colour, size);
            }
            return null;
	}
}
