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
                    resultArea.setText(getAll());
                }catch (SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (ClassNotFoundException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
            if ("add".equals(e.getActionCommand())){
                AddNewGui addNewGui= new AddNewGui();
                addNewGui.setVisible(true);
            }
            if ("showById".equals(e.getActionCommand())){
                try {
                    resultArea.setText(getById(Integer.parseInt(idField.getText())));
                }catch (SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (ClassNotFoundException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
            if ("deleteById".equals(e.getActionCommand())){
                try {
                    deleteById(Integer.parseInt(idField.getText()));
                    resultArea.setText(getAll());
                }catch (SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (ClassNotFoundException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        }
    };

    public String getAll() throws SQLException, ClassNotFoundException{

        String result="";
        Connection c;
        Statement stmt;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/netcracker","postgres", "tempus");
        c.setAutoCommit(false);
        String sql;
        stmt = c.createStatement();
        sql = "SELECT DISTINCT * FROM EMP, SALGRADE, dept " +
                "WHERE (SAL BETWEEN SALGRADE.LOSAL AND SALGRADE.HISAL) AND" +
                "(dept.deptno=emp.deptno)"+
                "ORDER BY empno";
        ResultSet rs =stmt.executeQuery(sql);
        while (rs.next()) {
            result+="EMPNO="+rs.getInt("EMPNO")+" NAME="+rs.getString("ename")+
                    " JOB="+rs.getString("job")+" MGR="+rs.getString("mgr") +
                    " HIREDATE=" +rs.getDate("hiredate")+" SAL="+rs.getDouble("sal")+
                    " COMM="+rs.getDouble("comm")+" DEPTNO="+rs.getInt("deptno")+
                    " DEPTNAME="+rs.getString("dname")+" DEPTLOC="+rs.getString("loc")+
                    " SALGRADE="+rs.getInt("grade")+"\n";
        }
        rs.close();
        stmt.close();
        c.commit();
        c.close();

        return result;
    }

    public String getById(int id) throws SQLException, ClassNotFoundException{
        String result="";
        Connection c;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/netcracker","postgres", "tempus");
        c.setAutoCommit(false);
//            System.out.println("-- Opened database successfully");
        PreparedStatement preparedStatement = c.prepareStatement(
                "SELECT DISTINCT * FROM EMP, SALGRADE, dept " +
                        "WHERE (emp.SAL BETWEEN SALGRADE.LOSAL AND SALGRADE.HISAL) AND" +
                        "(emp.deptno=dept.deptno) AND (emp.EMPNO=?)");
        preparedStatement.setInt(1, id);
        ResultSet rs =preparedStatement.executeQuery();
        c.commit();
//            System.out.println("-- Selected");

        while (rs.next()) {
            result += "EMPNO=" + rs.getInt("EMPNO") + " NAME=" + rs.getString("ename") +
                    " JOB=" + rs.getString("job") + " MGR=" + rs.getString("mgr") +
                    " HIREDATE=" + rs.getDate("hiredate") + " SAL=" + rs.getDouble("sal") +
                    " COMM=" + rs.getDouble("comm") + " DEPTNO=" + rs.getInt("deptno") +
                    " DEPTNAME=" + rs.getString("dname") + " DEPTLOC=" + rs.getString("loc") +
                    " SALGRADE=" + rs.getInt("grade") + "\n";
        }
        rs.close();
        c.commit();
        c.close();
        return result;
    }

    public void deleteById(int id) throws SQLException, ClassNotFoundException{
        Connection c;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/netcracker","postgres", "tempus");
        c.setAutoCommit(false);
        PreparedStatement preparedStatement = c.prepareStatement(
                "DELETE FROM emp WHERE empno=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        c.commit();
        c.close();
    }
}
