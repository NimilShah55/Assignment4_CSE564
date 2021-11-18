
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that generates TSP paths on update.
 * @author Nate Robinson, Dustin Howarth
 */
public class PathGenerator extends Strategy {
    
    /**
     * Invokes the traveling salesman solution.
     * @param cities List of cities to map
     * @return Paths between cities as a map
     */
    public Map<City, City> runTravelingSalesman(List<City> cities) throws InterruptedException {
        if (cities == null || cities.size() < 2 ) return null;
        Map<City, City> paths = new HashMap<>();
        City firstCity = cities.iterator().next();
        City thisCity = firstCity;
        City lastCity = null;
        while (thisCity != null) {
            City nearestCity = null;
            double shortestDistance = Double.MAX_VALUE;
            for (City otherCity : cities) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
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


    private double calculateDistance(City cityA, City cityB) {
        if (cityA == null || cityB == null || cityA.equals(cityB)) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(cityA.getX() - cityB.getX(), 2) +
                Math.pow(cityA.getY() - cityB.getY(), 2));
    }


    /**
     * Run the greedy TSP algorithm to find a potentially optimal path.
     * @param cityDB The CityDatabase singleton object that will be updated.
     */
    @Override
    public void createPath(CityDatabase cityDB) throws InterruptedException {
        Map<City, City> path = runTravelingSalesman(cityDB.cities);
        if(path != null)
            cityDB.addConnections(path);
    }
}
