/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasapalabra;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JButton;

public class CircularLayout implements LayoutManager {
    private int radius; // Radio del círculo
    private int gap; // Espacio entre los componentes
    private double startAngle; // Ángulo de inicio del círculo
    private double endAngle; // Ángulo de fin del círculo
    
    public CircularLayout(int radius, int gap) {
        this.radius = radius;
        this.gap = gap;
        this.startAngle = 0;
        this.endAngle = 2 * Math.PI;
    }
    
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }
    
    public void addLayoutComponent(String name, Component comp) {
        // No se necesita implementar en este ejemplo
    }
    
    public void removeLayoutComponent(Component comp) {
        // No se necesita implementar en este ejemplo
    }
    
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(parent.getWidth(), parent.getHeight());
    }
    
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(parent.getWidth(), parent.getHeight());
    }
    
    @Override
    public void layoutContainer(Container parent) {
        int centerX = parent.getWidth() / 2;
        int centerY = parent.getHeight() / 2;

        int componentCount = parent.getComponentCount();
        double angleIncrement = (endAngle - startAngle) / (componentCount - 1); // Restamos 1 al componente de la letra "A"
        double angle = startAngle;
        
        int letterAIndex = -1;
        for (int i = 0; i < componentCount; i++) {
            Component component = parent.getComponent(i);
            if (component instanceof JButton && ((JButton) component).getText().equals("A")) {
                letterAIndex = i;
                break;
            }
        }

        ArrayList<Component> components = new ArrayList<>();
        Component letterAComponent = null; // Almacenamos el componente de la letra "A"

        for (int i = 0; i < componentCount; i++) {
            Component component = parent.getComponent(i);
            components.add(component);

            if (component instanceof JButton && ((JButton) component).getText().equals("A")) {
                letterAComponent = component;
            }
        }

        /*if (letterAComponent != null) {
            components.remove(letterAComponent);
            components.add(0, letterAComponent); // Movemos el componente de la letra "A" al principio de la lista
        }*/

        for (Component component : components) {
            int componentWidth = component.getPreferredSize().width;
            int componentHeight = component.getPreferredSize().height;

            int x = (int) (centerX + radius * Math.cos(angle)) - componentWidth / 2;
            int y = (int) (centerY + radius * Math.sin(angle)) - componentHeight / 2;

            component.setBounds(x, y, componentWidth, componentHeight);

            angle += angleIncrement;
        }
    }
}

