import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;

/**
 * Encapsulating view class that runs the program and handles menu interaction,
 * including saving and loading a map.
 * @author Nate Robinson, Gianni Consiglio
 */
public class View extends JFrame {

    /**
     * Initialize the view with menu and content.
     * @param panel Main workspace panel to display as content
     */
    public View() {
        Workspace panel = new Workspace();
        add(panel, BorderLayout.CENTER);
        add(StatusBar.getInstance(), BorderLayout.SOUTH);
        setTitle("City Map");
        
        JMenuBar menubar = new JMenuBar();
        menubar.add(initFileMenu(panel));
        menubar.add(initConnectionsMenu(panel));
        menubar.add(initActionsMenu(panel));
        setJMenuBar(menubar);
    }
    
    private JMenu initFileMenu(Workspace panel) {
        JMenu fileMenu = new JMenu("File");
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
                save(CityDatabase.getInstance().cities, CityDatabase.getInstance().paths);
            }
        });
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        return fileMenu;
    }

    private JMenu initConnectionsMenu(Workspace panel) {
        JMenu connMenu = new JMenu("Connections");
        JMenuItem optTSPGreedy = new JMenuItem("TSP Nearest Neighbor");
        optTSPGreedy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setConnectionState(Workspace.ConnectionMode.TSP_GREEDY);
            }
        });
        JMenuItem optTSPBrute = new JMenuItem("TSP Pro");
        optTSPBrute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setConnectionState(Workspace.ConnectionMode.TSP_PRO);
            }
        });
        JMenuItem optCluster = new JMenuItem("Clusters");
        optCluster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setConnectionState(Workspace.ConnectionMode.CLUSTERS);
            }
        });
        JMenuItem optUserConn = new JMenuItem("User Connect");
        optUserConn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setConnectionState(Workspace.ConnectionMode.USER_CONNECT);
            }
        });
        connMenu.add(optTSPGreedy);
        connMenu.add(optTSPBrute);
        connMenu.add(optCluster);
        connMenu.add(optUserConn);
        return connMenu;
    }
    
    private JMenu initActionsMenu(Workspace panel) {
        JMenu actionsMenu = new JMenu("Actions");
        JMenuItem optMove = new JMenuItem("Move");
        optMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setActionState(Workspace.ActionMode.MOVE);
            }
        });
        JMenuItem optConnect = new JMenuItem("Connect");
        optConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setConnectionState(Workspace.ConnectionMode.USER_CONNECT);
                panel.setActionState(Workspace.ActionMode.CONNECT);
            }
        });
        JMenuItem optCreate = new JMenuItem("Create");
        optCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setActionState(Workspace.ActionMode.CREATE);
            }
        });
        actionsMenu.add(optMove);
        actionsMenu.add(optConnect);
        actionsMenu.add(optCreate);
        return actionsMenu;
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
        String line = "";
        int lineDelimiter;
        do {
            lineDelimiter = text.indexOf("\n");
            if (lineDelimiter == -1) break;
            line = text.substring(0, lineDelimiter + 1);
            text = text.substring(lineDelimiter + 1);
            if(line.contains("EOFCoordinates")) {
                break;
            }
            coords = line.split(" ");
            int y = (int)Double.parseDouble(coords[2]);
            int x = (int)Double.parseDouble(coords[1]);
            String name = coords.length > 3 ? coords[3] : "";
            CityDatabase.getInstance().createCity(x, y, name, new Color(1), "");
        } while (text.contains("EOFCoordinates"));
        parseConnections(text);
    }
    
    /**
     * this creates the connections from a file.
     * @param text
     */
    private void parseConnections(String text){
        CityDatabase find = CityDatabase.getInstance();
        String[] coords;
        String line = "";
        int lineDelimiter;
        Map<City, City> connections = new HashMap<>();
        while(text.contains("EOFLines")) {
            lineDelimiter = text.indexOf("\n");
            if (lineDelimiter == -1) break;
            line = text.substring(0, lineDelimiter + 1);
            if(line.contains("EOFLines")) {
                break;
            }
            text = text.substring(lineDelimiter + 1);
            coords = line.split(" ");
            City one = find.findCityAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            City two = find.findCityAt(Integer.parseInt(coords[2]), Integer.parseInt(coords[3]));
            if(one!=null&&two!=null) {
                connections.put(one, two);
            }
        }
        find.addConnections(connections);
    }
    
    /**
     * Saves the current model.
     * @param cities List of cities to save.
     */
    public void save(List<City> cities, Map<City, City> connects) {
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
            writer.write("EOFCoordinates\n");
            for(Map.Entry<City, City> map: connects.entrySet()) {
                writer.write(map.getKey().getX() + " " + map.getKey().getY() + " " + map.getValue().getX() + " " + map.getValue().getY()+" \n");
            }
            writer.write("EOFLines\n");
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
