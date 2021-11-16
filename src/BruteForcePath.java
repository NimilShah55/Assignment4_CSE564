import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The TSP Pro algorithm that performs a bruteforce search for the absolute best path to take.
 * @author Dustin Howarth
 */
public class BruteForcePath extends Strategy{

        private static double currentDistance = 0;


    /**
     * Invokes the brute force tsp solution.
     * @param cities List of cities that will be visited and mapped.
     * @return Guaranteed best paths between cities as a map
     */
    public Map<City, City> runBruteForcePath(List<City> cities) {
        if (cities == null || cities.size() < 2 )
            return null;
        Map<City, City> path = new HashMap<>();
        City firstCity = cities.get(0);
        currentDistance = Double.MAX_VALUE;
        path = recursiveBruteForceHelper(cities, path, firstCity, cities.get(0));
        double distance = calculatePathDistance(path);
        System.out.print("TSP PRo: ");
        System.out.println(distance);
        return path;
    }

    /**
     * Determines the Euclidean (2 dimensional) distance between two cities.
     * @param sourceCity The first city (source or starting city).
     * @param destCity The second city (destination city).
     * @return The distance between given cities; if either city does not exist
     * or the cities are the same object, the max value is returned.
     */
    public double calculateDistance(City sourceCity, City destCity) {
        if (sourceCity == null || destCity == null || sourceCity.equals(destCity)) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(sourceCity.getX() - destCity.getX(), 2) +
                Math.pow(sourceCity.getY() - destCity.getY(), 2));
    }


    private double calculatePathDistance(Map<City, City> path) {
        double totalDistance = 0;
        for (Map.Entry<City, City> pair : path.entrySet()) {
            totalDistance += calculateDistance(pair.getKey(), pair.getValue());
        }
        return totalDistance;
    }


    private Map<City, City> recursiveBruteForceHelper(java.util.List<City> cities,
                                                     Map<City, City> path,
                                                     City firstCity,
                                                     City currentCity) {
        Map<City, City> bestPath = null;
        if(cities.size() <= 1) {
            path.put(cities.get(0), firstCity);
            double distance = calculatePathDistance(path);
            if(distance < currentDistance) {
                System.out.print(distance);
                System.out.print(" vs current distance: ");
                System.out.println(currentDistance);
                bestPath = new HashMap<City, City>();
                bestPath.putAll(path);
                currentDistance = distance;
            }
            return bestPath;
        }

        //   This removes the current city (the source city or the one that is being traveled from the list) and
        // recursively tries all of the potential remaining cities as the next path.

        List<City> citiesLeft = new ArrayList<>(cities);
        citiesLeft.remove(currentCity);
        for(int i  = 0; i < citiesLeft.size(); i++) {
            path.put(currentCity, citiesLeft.get(i));
            Map<City, City> tmpPath = recursiveBruteForceHelper(citiesLeft, path, firstCity, citiesLeft.get(i));
            if(tmpPath != null)
                bestPath = tmpPath;
        }
        return bestPath;
    }


    @Override
    public void createPath() {
        CityDatabase cityDB = CityDatabase.getInstance();
        cityDB.addConnections(runBruteForcePath(cityDB.cities));
    }
}
