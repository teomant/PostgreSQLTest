import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUI extends JFrame {

    private JTextArea resultArea = new JTextArea();
    private JButton showAllButton = new JButton("Show all");
    private JButton showByIdButton = new JButton("Show by ID");
    private JButton addButton = new JButton("ADD new");
    private JButton deleteButton = new JButton("Delete by ID");
    private JTextField idField = new JTextField();

    public GUI(){
        super("Empoyees");
        this.setBounds(100,100, 800,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2,1));
        container.add(new JScrollPane(resultArea));
        resultArea.setEditable(false);
        JPanel jPanel = new JPanel() ;
        jPanel.setLayout(new GridLayout(2,1));
        JPanel jPanel1 = new JPanel() ;
        jPanel1.setLayout(new GridLayout(1,3));
        JPanel jPanel2 = new JPanel() ;
        jPanel2.setLayout(new GridLayout(1,3));
        jPanel1.add(idField);
        jPanel1.add(showByIdButton);
        jPanel1.add(showAllButton);
        jPanel2.add(addButton);
        jPanel2.add(deleteButton);
        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        container.add(jPanel);

        showAllButton.setActionCommand("showAll");
        showByIdButton.setActionCommand("showById");
        deleteButton.setActionCommand("deleteById");
        addButton.setActionCommand("add");

        showByIdButton.addActionListener(actionListener);
        showAllButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("showAll".equals(e.getActionCommand())){
                try {
                    resultArea.setText(ClassForRequests.getAll());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
            if ("add".equals(e.getActionCommand())){
                AddNewGui addNewGui= new AddNewGui();
                addNewGui.setVisible(true);
            }
            if ("showById".equals(e.getActionCommand())){
                try {
                    resultArea.setText(ClassForRequests.getById(Integer.parseInt(idField.getText())));
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
            if ("deleteById".equals(e.getActionCommand())){
                try {
                    ClassForRequests.deleteById(Integer.parseInt(idField.getText()));
                    resultArea.setText(ClassForRequests.getAll());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        }
    };
}
