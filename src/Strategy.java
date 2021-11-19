/**
 * Class that represents the strategy pattern that Cluster, PathGenerator, and BruteForcePath use.
 * @author Dustin Howarth
 */
public abstract class Strategy implements Runnable {

    public abstract void createPath(CityDatabase cityDB) throws InterruptedException;

    /**
     * Call createPath() (as specified by child classes) and have them generate the given city paths.
     */
    @Override
    public void run() {
        CityDatabase cityDB = CityDatabase.getInstance();
        try {
            createPath(cityDB);
        } catch (InterruptedException e) {
        }
    }
}
