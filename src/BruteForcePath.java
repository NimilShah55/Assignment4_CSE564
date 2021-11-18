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
     * Invokes the brute force tsp algorithm.
     * @param cities List of cities that will be visited and mapped.
     * @return Guaranteed best paths between cities as a map
     */
    public Map<City, City> runBruteForcePath(List<City> cities) throws InterruptedException {
        if(cities == null || cities.size() < 2)
            return null;
        Map<City, City> path = new HashMap<>();
        City firstCity = cities.get(0);
        currentDistance = Double.MAX_VALUE;
        path = recursiveBruteForceHelper(cities, path, firstCity, cities.get(0));
        return path;
    }


    private double calculateDistance(City sourceCity, City destCity) {
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


    private Map<City, City> recursiveBruteForceHelper(List<City> cities,
                                                     Map<City, City> path,
                                                     City firstCity,
                                                     City currentCity) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Map<City, City> bestPath = null;
        if(cities.size() <= 1) {
            path.put(cities.get(0), firstCity);
            double distance = calculatePathDistance(path);
            if(distance < currentDistance) {
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

    /**
     * Run the brute force algorithm to find the optimal path.
     * @param cityDB The CityDatabase singleton object that will be updated.
     */
    @Override
    public void createPath(CityDatabase cityDB) throws InterruptedException {
        Map<City, City> path = runBruteForcePath(cityDB.cities);
        if(path != null)
            cityDB.addConnections(path);
    }
}
