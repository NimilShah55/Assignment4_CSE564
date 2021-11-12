
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that generates TSP paths on update.
 * @author Nate Robinson
 */
public class PathGenerator extends Observable implements Runnable {
    
    /**
     * Invokes the traveling salesman solution.
     * @param cities List of cities to map
     * @return Paths between cities as a map
     */
    public Map<City, City> runTravelingSalesman(List<City> cities) {
        if (cities == null || cities.size() < 2 ) return null;
        Map<City, City> paths = new HashMap<>();
        City firstCity = cities.iterator().next();
        City thisCity = firstCity;
        City lastCity = null;
        while (thisCity != null) {
            City nearestCity = null;
            double shortestDistance = Double.MAX_VALUE;
            for (City otherCity : cities) {
                if (thisCity == otherCity
                        || paths.containsKey(otherCity)
                        || paths.containsValue(otherCity)) continue;
                double thisDistance = calculateDistance(thisCity, otherCity);
                if (thisDistance < shortestDistance) {
                    nearestCity = otherCity;
                    shortestDistance = thisDistance;
                }
            }
            if (nearestCity != null) paths.put(thisCity, nearestCity);
            lastCity = thisCity;
            thisCity = nearestCity;
        }

        // complete loop on TSP
        if (firstCity != null && lastCity != null) {
            paths.put(lastCity, firstCity);
        }
        return paths;
    }
    
    /**
     * Uses basic distance function to create the hypotenuse between two cities.
     * @param cityA The first city
     * @param cityB The second city
     * @return The distance between given cities; if either city does not exist
     * or the cities are the same object, the max value is returned.
     */
    public double calculateDistance(City cityA, City cityB) {
        if (cityA == null || cityB == null || cityA.equals(cityB)) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(cityA.getX() - cityB.getX(), 2) +
                Math.pow(cityA.getY() - cityB.getY(), 2));
    }
    
    @Override
    public void run() {
        List<City> cities = CityDatabase.getInstance().cities;
        Map<City, City> paths = runTravelingSalesman(cities);
        CityDatabase.getInstance().setConnections(paths);
        sendNotifications(null);
    }
}
