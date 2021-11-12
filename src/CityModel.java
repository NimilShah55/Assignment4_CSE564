import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A collection of cities that allows data manipulation and drawing.
 * @author Nate Robinson
 */
public class CityModel {
    private static CityModel instance = null;

    private CityModel() {
        cities = new ArrayList<>();
        pather = new PathGenerator();
    }

    final PathGenerator pather;
    final List<City> cities;
    Map<City, City> paths = null;
    City selected = null;

    /**
     * Instantiates CityModel.
     * @return Sole instance of the CityModel.
     */
    public static CityModel getInstance() {
        if (instance == null) {
            instance = new CityModel();
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
        pather.run();
    }

    /**
     * Adds cities to the collection
     * @param newCities Non-empty list of cities to add
     */
    public void addCities(City[] newCities) {
        if (newCities != null) return;
        cities.addAll(Arrays.asList(newCities));
        pather.run();
    }

    /**
     * Remove all cities and paths.
     */
    public void clear() {
        cities.clear();
        paths = null;
    }

    /**
     * Set the paths to draw.
     * @param connections Paths between cities as map entries
     */
    public void setConnections(Map<City, City> connections) {
        this.paths = connections;
    }
    
    /**
     * Checks if a city intersects with the given location.
     * @param x The X location of the city
     * @param y The Y location of the city
     * @return Intersecting city or null if none
     */
    public City findCityAt(int x, int y) {
        for (City c : cities) {
            if (c.contains(x, y)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Set the selected city for moving.
     * @param selected Selected city
     */
    public void setSelected(City selected) {
        this.selected = selected;
    }
    
    /**
     * Move selected city if any is selected.
     * @param x The X location of the city
     * @param y The Y location of the city
     */
    public void moveSelectedCity(int x, int y) {
        if (selected == null) return;
        selected.move(x, y);
        pather.run();
    }
}
