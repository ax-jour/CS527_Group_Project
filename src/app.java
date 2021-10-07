import views.DatabaseView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class app {
    public static void main(String[] args) {
        String[] databaseNames={"instacart", "amazon"};
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setBounds(300,200,600,600);
        DatabaseView testView=new DatabaseView(databaseNames);
        testFrame.add(testView, BorderLayout.CENTER);
        testFrame.setVisible(true);
    }
}
