import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A collection of cities that allows data manipulation and drawing.
 * @author Nate Robinson
 */
public class CityDatabase extends Observable {
    private static CityDatabase instance = null;

    private CityDatabase() {
        cities = new ArrayList<>();
        paths = new HashMap<>();
    }

    final List<City> cities;
    final Map<City, City> paths;
    City selected = null;

    /**
     * Instantiates CityDatabase.
     * @return Sole instance of the CityDatabase.
     */
    public static CityDatabase getInstance() {
        if (instance == null) {
            instance = new CityDatabase();
        }
        return instance;
    }

    /**
     * Creates and adds a city to the model according to the parameter data.
     * Notifies observer.
     * @param x The X location of the city
     * @param y The Y location of the city
     * @param name The name of the city
     */
    public void createCity(int x, int y, String name) {
        cities.add(new City(x, y, name));
        sendNotifications(this);
    }
    
    /**
     * Adds cities to the collection
     * @param newCities Non-empty list of cities to add
     */
    public void addCities(City[] newCities) {
        if (newCities != null) return;
        cities.addAll(Arrays.asList(newCities));
        sendNotifications(this);
    }

    /**
     * Remove all cities and paths.
     */
    public void clear() {
        cities.clear();
        paths.clear();
        sendNotifications(this);
    }
    
    /**
     * Reset paths and send change notifications.
     */
    public void clearConnections() {
        paths.clear();
        sendNotifications(this);
    }

    /**
     * Set the paths to draw and send change notifications.
     * @param connections Paths between cities as map entries
     */
    public void addConnections(Map<City, City> connections) {
        this.paths.putAll(connections);
        sendNotifications(this);
    }
    
    /**
     * Checks if a city intersects with the given location.
     * @param x The X location of the city
     * @param y The Y location of the city
     * @return Intersecting city or null if none
     */
    public City findCityAt(int x, int y) {
        Iterator<City> iterator = cities.iterator();
        while (iterator.hasNext()) {
            City c = (City)iterator.next();
            if (c.contains(x, y)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Move given city and send change notifications.
     * @param city City to move
     * @param x The X location of the city
     * @param y The Y location of the city
     */
    public void moveCity(City city, int x, int y) {
        if (city == null) return;
        city.move(x, y);
        sendNotifications(this);
    }
}
