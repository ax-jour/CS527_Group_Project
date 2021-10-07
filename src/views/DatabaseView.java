package views;


//import com.sun.java.swing.action.DelegateAction;

import dbconns.MySQLAccess;
import models.DatabaseModel;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class DatabaseView extends JPanel{

    private DatabaseModel databaseModel;

    private JTable table;
    private JLabel databaseLabel, queryLabel, elapsedTime;
    private JComboBox databaseComboBox;
    private final JRadioButton databaseModelRadioButton1,databaseModelRadioButton2;
    private final ButtonGroup databaseModelGroup;
    private JTextArea queryBox;
    private JButton executeButton;

    public DatabaseView(String[] databaseNameList) {
        Object[] columnNames = {"姓名", "语文", "数学", "英语", "总分"};
        Object[][] rowData = {
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"Joe", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210},
                {"Jane", 80, 70, 60, 210}
        };
        DefaultTableModel tableModel=new DefaultTableModel(rowData, columnNames);
        table=new JTable(tableModel);
        table.setBorder(new LineBorder(new Color(0,0,0)));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tableScrollPane=new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(100,100));

        databaseLabel=new JLabel("Database Name");
        queryLabel=new JLabel("query");
        elapsedTime=new JLabel("elapsed time: 0 ms");
        databaseComboBox=new JComboBox(databaseNameList);
        databaseModelRadioButton1=new JRadioButton("Mysql");
        databaseModelRadioButton1.setSelected(true);
        databaseModelRadioButton2=new JRadioButton("Redshift");
        databaseModelGroup=new ButtonGroup();
        databaseModelGroup.add(databaseModelRadioButton1);
        databaseModelGroup.add(databaseModelRadioButton2);
        queryBox=new JTextArea();
        JScrollPane textScrollPane=new JScrollPane(queryBox);
        textScrollPane.setPreferredSize(new Dimension(100,100));

        executeButton=new JButton("execute");
        addButtonActionListener(executeButton);

//        JPanel jp=new JPanel();
        GridBagLayout gl=new GridBagLayout();
//        jp.setLayout(gl);
        this.setLayout(gl);

        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

        // Database Name Label
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.gridheight=2;
        gl.setConstraints(databaseLabel, gbc);

        // select box of Database Name
        gbc.gridx=2;
        gbc.gridwidth=2;

        gl.setConstraints(databaseComboBox, gbc);





        // mysql radio button
        gbc.gridx=10;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.gridheight=2;

        gl.setConstraints(databaseModelRadioButton1, gbc);

        // Redshift radio button
        gbc.gridx=17;

        gl.setConstraints(databaseModelRadioButton2, gbc);

        // query label
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.gridheight=1;

        gl.setConstraints(queryLabel, gbc);

        // query text box
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=21;
        gbc.gridheight=12;

        gl.setConstraints(textScrollPane, gbc);

        // execute button
        gbc.gridx=0;
        gbc.gridy=16;
        gbc.gridwidth=4;
        gbc.gridheight=1;

        gl.setConstraints(executeButton, gbc);

        // elapsed time
        gbc.gridx=17;

        gl.setConstraints(elapsedTime, gbc);

        // table header
        gbc.gridx=0;
        gbc.gridy=17;
        gbc.gridwidth=21;
        gbc.gridheight=11;

        gl.setConstraints(table.getTableHeader(), gbc);
        // result table
        gbc.gridx=0;
        gbc.gridy=18;
        gbc.gridwidth=21;
        gbc.gridheight=12;
//        gl.setConstraints(table, gbc);
        gl.setConstraints(tableScrollPane, gbc);


//        add(jp);
        
        this.add(databaseLabel);
        this.add(databaseComboBox);
        this.add(databaseModelRadioButton1);
        this.add(databaseModelRadioButton2);
        this.add(textScrollPane);
        this.add(executeButton);
        this.add(elapsedTime);
        this.add(queryLabel);
        this.add(table.getTableHeader());
//        this.add(table);
        this.add(tableScrollPane);

        this.setBorder(new EmptyBorder(100,50,100,50));
        this.setPreferredSize(new Dimension(100,100));
    }


    public DatabaseModel getDatabaseModel() {

        return databaseModel;
    }

    public void setDatabaseModel(DatabaseModel databaseModel) {

        this.databaseModel = databaseModel;
    }

    public String getQuery(){

        return queryBox.getText();
    }

    public String getDatabaseName(){

        return databaseComboBox.getEditor().getItem().toString();

    }


    public String getDatabaseType(){

        Enumeration<AbstractButton> radioButtons=databaseModelGroup.getElements();
        while(radioButtons.hasMoreElements()){
            AbstractButton button=radioButtons.nextElement();
            if (button.isSelected()){
                return button.getText();
            }
        }
        return "";

    }
    private void updateTable(Object[] columnNames, Object[][] rowData){

        DefaultTableModel newTableModel=new DefaultTableModel(rowData, columnNames);
        table.setModel(newTableModel);

    }

    private void updateElapsedTime(double time){

        elapsedTime.setText(String.format("elapsed time: %.2f ms", time));

    }

    private void addButtonActionListener(JButton button){
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //System.out.println(getQuery());

                MySQLAccess db = new MySQLAccess();
                try {
                    db.queryMySQL("instacart_dev", "SELECT * FROM aisles");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                System.out.println(getDatabaseType());
                System.out.println(getDatabaseName());
                Object[] columnNames = {"姓名", "语文", "数学", "英语", "总分","sds", "总分","总分","总分","总分",};
                Object[][] rowData = {
                        {"张三", 80, 80, 80, 240, 240, 240, 240, 240, 240}
                };
                updateTable(columnNames, rowData);
                updateElapsedTime(0.11);

            }
        });
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        repaint();
//    }
}
