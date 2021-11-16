import java.util.ArrayList;

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
	public void createCity(String name, int cityX, int cityY) {
		Blackboard bb = Blackboard.createInstance();
		ArrayList<City> cities = bb.getCities();
		
		if (cities.size() > 0) {
			bb.addCity(new City(name, cities.size(), cityX, cityY, 20, 20));
		} else {
			bb.addCity(new City(name, 0, cityX, cityY, 20, 20));
		}
	}
}
