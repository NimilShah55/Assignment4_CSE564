import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Encapsulating view class that runs the program and handles menu interaction,
 * including saving and loading a map.
 * @author Nate Robinson
 */
public class View extends JFrame {

    /**
     * Initialize the view with menu and content.
     * @param panel Main workspace panel to display as content
     */
    public View() {
        Workspace panel = new Workspace();
        add(panel);
        setTitle("City Map");
        
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.reset();
            }
        });
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                repaint();
            }
        });
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(CityModel.getInstance().cities);
            }
        });
        file.add(newItem);
        file.add(saveItem);
        file.add(loadItem);
        menubar.add(file);
        setJMenuBar(menubar);
    }
    
    /**
     * Loads the selected file with TSP
     */
    public void load() {
        JFileChooser browseFile = new JFileChooser(".");
        int value = browseFile.showOpenDialog(View.this);
        File selected = browseFile.getSelectedFile();
        if (value != JFileChooser.APPROVE_OPTION || selected == null) return;
        
        String text = "";
        try {
            text = readTextFile(selected);
        } catch (Exception ex) {
            System.out.println("Failed to load from file.");
            return;
        }
        
        String typeValue = seekHeaderValue(text, "TYPE");
        String dimensionValue = seekHeaderValue(text, "DIMENSION");
        int numOfCities = Integer.parseInt(dimensionValue);
        
        if (typeValue.equalsIgnoreCase("TSP")) {
            parseNodes(text);
        } else {
            throw new NullPointerException();
        }
    }
    
    private String readTextFile(File file) {
        String text = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = br.readLine();
            while (str != null) {
                text += str + "\n"; // adding a space serves to delimit new-lines
                str = br.readLine();
            }
            br.close();
            return text;
        } catch(IOException ex) {
            System.out.println("Reader failed to parse file.");
        }
        return text;
    }

    private String seekHeaderValue(String text, String key) {
        String line = "";
        int lineDelimiter = 0;
        do {
            lineDelimiter = text.indexOf("\n");
            if (lineDelimiter == -1) {
                throw new RuntimeException();
            }
            line = text.substring(0, lineDelimiter + 1);
            text = text.substring(lineDelimiter + 1);
        } while(!line.startsWith(key));

        int valueDelimiter = line.indexOf(":");
        if (valueDelimiter == -1) {
            return text;
        }
        return line.substring(valueDelimiter + 1, lineDelimiter).trim();
    }

    private void parseNodes(String text) {
        text = seekHeaderValue(text, "NODE_COORD_SECTION");
        String[] coords;
        String line;
        int lineDelimiter;
        do {
            lineDelimiter = text.indexOf("\n");
            if (lineDelimiter == -1) break;
            line = text.substring(0, lineDelimiter + 1);
            text = text.substring(lineDelimiter + 1);
            coords = line.trim().split(" ");
            int y = (int)Double.parseDouble(coords[1]);
            int x = (int)Double.parseDouble(coords[2]);
            String name = coords.length > 3 ? coords[3] : "";
            CityModel.getInstance().createCity(x, y, name);
        } while (!text.startsWith("EOF"));
    }
    
    /**
     * Saves the current model.
     * @param cities List of cities to save.
     */
    public void save(List<City> cities) {
        JFileChooser createFile = new JFileChooser(".");
        createFile.showSaveDialog(View.this);
        File saveFile = createFile.getSelectedFile();

        int size = cities.size();
        if (size == 0 || saveFile == null) {
            System.out.println("Could not save cities. Try adding one");
            return;
        }
        try {
            saveFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            // write header
            writer.write("TYPE : TSP\n");
            writer.write("DIMENSION : " + cities.size() + "\n");
            writer.write("NODE_COORD_SECTION\n");
            for(int i = 0; i < cities.size(); i++) {
                City city = cities.get(i);
                double x = (double) city.getX();
                double y = (double) city.getY();
                String out = String.format("%d %.4f %.4f %s\n", 
                        i + 1, x, y, city.name);
                writer.write(out);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println("Failed to save; could not load writer.");
        }
    }
    
    /**
     * Invokes the program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        View main = new View();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(700, 750);
        main.setVisible(true);
    }
    
}
