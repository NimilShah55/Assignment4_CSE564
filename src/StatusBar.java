
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nate
 */
public class StatusBar extends JLabel {
    
    private static StatusBar instance;
    
    private StatusBar() {
        super();
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setText("Hello World!");
        setVisible(true);
    }
    
    public static StatusBar getInstance() {
        if (instance == null) {
            instance = new StatusBar();
        }
        return instance;
    }
    
    public void setStatus(String message) {
        setText("" + message);
    }
    
}
