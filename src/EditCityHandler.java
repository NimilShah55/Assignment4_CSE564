import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * EditCityHandler adds the popup when editing the cities.
 * 
 * @author Gianni Consiglio, Nate Robinson
 */
public class EditCityHandler implements ActionListener {
        int x, y;
        final JTextField name;
        final JTextField size;
        Color squareColor;
        Color circleColor;
        JComboBox<String> type;
        City change;
        JFrame popup;
        JPanel panel;
        JFrame squareFrame;
        JFrame circleFrame;
        
        /**
         * default constructor for editing the city.
         * 
         * @param change - city to edit
         * @param panel - panel to refresh
         */
        public EditCityHandler(City change, JPanel panel) {
            this.panel = panel;
            x = change.getX();
            y = change.getY();
            this.change = change;
            this.squareColor = new Color(0);
            this.circleColor = new Color(0);
            squareFrame = new JFrame();
            circleFrame = new JFrame();
            String[] selection = {
                    "base is a square",
                    "base with cross",
                    "base with circle",
                    "base with cross and circle"
            };
            type = new JComboBox<String>(selection);
            this.name = new JTextField();
            this.size = new JTextField();
            name.setFont(new Font("Courier", Font.PLAIN, 16));
            size.setFont(new Font("Courier", Font.PLAIN, 16));
            popup = new JFrame();
            popup.setLayout(new GridLayout(6, 2));
            popup.setSize(400, 300);
            popup.setVisible(false);
            JColorChooser chooseSquare = new JColorChooser();
            chooseSquare.getSelectionModel().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    squareColor = chooseSquare.getColor();
                    squareFrame.setVisible(false);
                }
            });
            JColorChooser chooseCircle = new JColorChooser();
            chooseCircle.getSelectionModel().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    circleColor = chooseCircle.getColor();
                    circleFrame.setVisible(false);
                }
            });
            Button square = new Button("Square");
            square.addActionListener(this);
            Button circle = new Button("Circle");
            circle.addActionListener(this);
            squareFrame.setSize(400, 400);
            squareFrame.add(chooseSquare);
            circleFrame.setSize(400, 400);
            circleFrame.add(chooseCircle);
            Button ok = new Button("ok");
            ok.addActionListener(this);
            popup.add(new Label("Name: "));
            popup.add(name);
            popup.add(new Label("Size: "));
            popup.add(size);
            popup.add(new Label("Type: "));
            popup.add(type);
            popup.add(new Label("Color: "));
            popup.add(square);
            popup.add(new Label("Color: "));
            popup.add(circle);
            popup.add(ok);
            name.setText("");
            popup.setVisible(true);
            popup.requestFocus();
        }
        /**
         * Edit the city double clicked on
         * @param e ActionEvent event on the ok button
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonName = ((Button)e.getSource()).getLabel();
            if(buttonName.equals("ok")) {
                okAction();
            }else if(buttonName.equals("Square")) {
                squareFrame.setVisible(true);
                squareFrame.requestFocus();
            }else if(buttonName.equals("Circle")) {
                circleFrame.setVisible(true);
                circleFrame.requestFocus();
            }
        }
        
        /**
         * ok button was selected then it will put the decorator together
         */
        public void okAction() {
            FactoryCity maker = FactoryCity.getFC();
            popup.setVisible(false);
            City created = maker.createCity(name.getText(), x, y,  size.getText(), squareColor, "Square");
            switch((String)type.getSelectedItem()) {
              case "base with cross":
                  CrossCity cross = (CrossCity) maker.createCity(name.getText(), x, y,  size.getText(), squareColor, "Cross");
                  cross.setCity(created);
                  CityDatabase.getInstance().swapInstance(change, cross);
                  break;
              case "base with circle":
                  CircleCity circle = (CircleCity) maker.createCity(name.getText(), x, y,  size.getText(), circleColor, "Circle");
                  circle.setCity(created);
                  CityDatabase.getInstance().swapInstance(change, circle);
                  break;
              case "base with cross and circle":
                  CrossCity cross2 = (CrossCity) maker.createCity(name.getText(), x, y,  size.getText(), squareColor, "Cross");
                  cross2.setCity(created);
                  CircleCity circle2 = (CircleCity) maker.createCity(name.getText(), x, y,  size.getText(), circleColor, "Circle");
                  circle2.setCity(cross2);
                  CityDatabase.getInstance().swapInstance(change, circle2);
                  break;
              default:
                  CityDatabase.getInstance().swapInstance(change, created);
                  break;
            }
            CityDatabase.getInstance().swapInstance(change, created);
            StatusBar.getInstance().setStatus("City " + name + " edited.");
            panel.repaint();
        }
    }