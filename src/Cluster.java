import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class that generates three different clusters based on the cities.
 * @author Dustin Howarth
 */
public class Cluster extends Strategy {

    /**
     * Invokes the k-means clustering algorithm.
     * @param cities List of cities to map
     * @return Paths between cities as a map
     */
    public Map<City, City> runClustering(List<City> cities) throws InterruptedException {
        if (cities == null || cities.size() < 3 ) return null;
        ArrayList<City> citiesCenter0 = new ArrayList<>();
        ArrayList<City> citiesCenter1 = new ArrayList<>();
        ArrayList<City> citiesCenter2 = new ArrayList<>();
        Map<City, City> paths = new HashMap<>();
        ArrayList<Point> centers = new ArrayList<>();
        centers.add(generateCenter(cities));
        centers.add(generateCenter(cities));
        centers.add(generateCenter(cities));

        //   Performs 10 iterations of the clustering algorithm in order to optimize the clusters.

        for (int i = 0; i < 10; i++) {
            citiesCenter0.clear();
            citiesCenter1.clear();
            citiesCenter2.clear();
            for (City thisCity : cities) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                int nearestCenter = -1;
                int centerCounter = 0;
                double shortestDistance = Double.MAX_VALUE;
                for (Point center : centers) {
                    double thisDistance = calculateDistance(thisCity, center);
                    if (thisDistance < shortestDistance) {
                        nearestCenter = centerCounter;
                        shortestDistance = thisDistance;
                    }
                    centerCounter++;
                }
                if (nearestCenter == 0)
                    citiesCenter0.add(thisCity);
                else if(nearestCenter == 1)
                    citiesCenter1.add(thisCity);
                else
                    citiesCenter2.add(thisCity);
            }
            centers = recalculateCenters(cities,
                                         citiesCenter0,
                                         citiesCenter1,
                                         citiesCenter2);
        }
        paths = getAllClusterPaths(citiesCenter0, citiesCenter1, citiesCenter2);
        return paths;
    }


    private double calculateDistance(City city, Point center) {
        if (city == null) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(city.getX() - center.getX(), 2) +
                Math.pow(city.getY() - center.getY(), 2));
    }


    private Point calculateNewCenter(List<City> cities, ArrayList<City> cityCenter) {
        if(cityCenter.size() == 0)
            return generateCenter(cities);
        int averageX = 0;
        int averageY = 0;
        for(City city: cityCenter) {
            averageX += city.getX();
            averageY += city.getY();
        }
        averageX /= cityCenter.size();
        averageY /= cityCenter.size();
        return  new Point(averageX, averageY);
    }


    private Point generateCenter(List<City> cities){
        int maxX = Integer.MIN_VALUE;;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;;
        int minY = Integer.MAX_VALUE;;
        for(City city: cities){
            if(city.getX() > maxX)
                maxX = city.getX();
            if(city.getY() > maxY)
                maxY = city.getY();
            if(city.getX() < minX)
                minX = city.getX();
            if(city.getY() < minY)
                minY = city.getX();
        }
        Random random = new Random();
        int xCoordinate0 = random.nextInt(maxX) + minX + 1;
        int yCoordinate0 = random.nextInt(maxX) + minX + 1;
        Point point = new Point(xCoordinate0, yCoordinate0);
        return point;
    }


    private HashMap<City, City> getAllClusterPaths(ArrayList<City> citiesCenter0,
                                                  ArrayList<City> citiesCenter1,
                                                  ArrayList<City> citiesCenter2) {
        HashMap<City, City> map = new HashMap<>();
        if(citiesCenter0.size() != 0) {
            City firstCity = citiesCenter0.get(0);
            for (int i = 0; i < citiesCenter0.size() - 1; i++) {
                map.put(citiesCenter0.get(i), citiesCenter0.get(i + 1));
            }
            map.put(citiesCenter0.get(citiesCenter0.size() - 1), firstCity);
        }

        if(citiesCenter1.size() != 0) {
            City firstCity = citiesCenter1.get(0);
            for (int i = 0; i < citiesCenter1.size() - 1; i++) {
                map.put(citiesCenter1.get(i), citiesCenter1.get(i + 1));
            }
            map.put(citiesCenter1.get(citiesCenter1.size() - 1), firstCity);
        }

        if(citiesCenter2.size() != 0) {
            City firstCity = citiesCenter2.get(0);
            for (int i = 0; i < citiesCenter2.size() - 1; i++) {
                map.put(citiesCenter2.get(i), citiesCenter2.get(i + 1));
            }
            map.put(citiesCenter2.get(citiesCenter2.size() - 1), firstCity);
        }
        return map;
    }

    private ArrayList<Point> recalculateCenters(List<City> cities,
                                                ArrayList<City> citiesCenter0,
                                                ArrayList<City> citiesCenter1,
                                                ArrayList<City> citiesCenter2) {
        ArrayList<Point> centers = new ArrayList<>();
        centers.add(calculateNewCenter(cities, citiesCenter0));
        centers.add(calculateNewCenter(cities, citiesCenter1));
        centers.add(calculateNewCenter(cities, citiesCenter2));
        return centers;
    }


    /**
     * Run the clustering algorithm to find the three clusters.
     * @param cityDB The CityDatabase singleton object that will be updated.
     */
    @Override
    public void createPath(CityDatabase cityDB) throws InterruptedException {
        Map<City, City> path = runClustering(cityDB.cities);
        if(path != null)
            cityDB.addConnections(path);
    }
}
