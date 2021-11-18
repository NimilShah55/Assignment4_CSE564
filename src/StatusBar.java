
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;


/**
 * A printable singleton label to communicate system messages and state to the
 * user.
 * @author Nate Robinson
 */
public class StatusBar extends JLabel {
    
    private static StatusBar instance;
    
    private StatusBar() {
        super();
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setText("Hello World!");
        setVisible(true);
    }
    
    /**
     * Get singleton instance of the StatusBar class.
     * @return Only instance of StatusBar.
     */
    public static StatusBar getInstance() {
        if (instance == null) {
            instance = new StatusBar();
        }
        return instance;
    }
    
    /**
     * Replace the status bar text with a new user message.
     * @param message The text to display.
     */
    public void setStatus(String message) {
        setText("" + message);
    }
    
}
