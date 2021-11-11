
/**
 * Observer pattern that updates upon observable call.
 * @author Nate Robinson
 */
public interface IObserver {
    /**
     * Updates observer with data.
     * @param data Data to update.
     */
    public void update(Object data);
}
