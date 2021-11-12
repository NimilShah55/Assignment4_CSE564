
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract implementation of the observable pattern. This holds a collection of
 * observers to notify.
 * @author Nate Robinson
 */
public abstract class Observable {
    private List<IObserver> observers = new ArrayList<>();
    
    /**
     * Add an observer to the collection.
     * @param observer Observer to add
     */
    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }
    
    /**
     * Clear all observers in the collection.
     */
    public void clearObservers() {
        observers.clear();
    }
    
    /**
     * Send an update notification to all observers.
     * @param data Data to send
     */
    public void sendNotifications(Object data) {
        for (IObserver ob : observers) {
            ob.update(data);
        }
    }
}
