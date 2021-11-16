import java.util.List;
import java.util.Map;

public abstract class Strategy implements Runnable {
    public abstract void createPath();


    @Override
    public void run() {
        createPath();
    }
}
