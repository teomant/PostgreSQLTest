import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddNewGui extends JFrame {
    private JTextArea empno = new JTextArea("EMPNO");
    private JTextArea ename = new JTextArea("ENAME");
    private JTextArea job = new JTextArea("JOB");
    private JTextArea mgr = new JTextArea("MGR");
    private JTextArea hiredate = new JTextArea("HIREDATE");
    private JTextArea sal = new JTextArea("SAL");
    private JTextArea comm = new JTextArea("COMM");
    private JTextArea deptno = new JTextArea("DEPTNO");

    private JTextField empnoField = new JTextField();
    private JTextField enameField = new JTextField();
    private JTextField jobField = new JTextField();
    private JTextField mgrField = new JTextField();
    private JTextField hiredateField = new JTextField();
    private JTextField salField = new JTextField();
    private JTextField commField = new JTextField();
    private JTextField deptnoField = new JTextField();

    private JButton addButton = new JButton("ADD");

    public AddNewGui(){

        super("Add new");
        this.setBounds(100,100, 200,400);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(9,2));

        empno.setEditable(false);
        ename.setEditable(false);
        job.setEditable(false);
        mgr.setEditable(false);
        hiredate.setEditable(false);
        sal.setEditable(false);
        comm.setEditable(false);
        deptno.setEditable(false);

        container.add(empno);
        container.add(empnoField);
        container.add(ename);
        container.add(enameField);
        container.add(job);
        container.add(jobField);
        container.add(mgr);
        container.add(mgrField);
        container.add(hiredate);
        container.add(hiredateField);
        container.add(sal);
        container.add(salField);
        container.add(comm);
        container.add(commField);
        container.add(deptno);
        container.add(deptnoField);
        container.add(addButton);

        addButton.setActionCommand("add");
        addButton.addActionListener(actionListener);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("add".equals(e.getActionCommand())){
                try {
                    addNew(empnoField.getText(),
                            enameField.getText(),
                            jobField.getText(),
                            mgrField.getText(),
                            hiredateField.getText(),
                            salField.getText(),
                            commField.getText(),
                            deptnoField.getText());
                }catch (SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (ClassNotFoundException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }catch (ParseException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        }
    };

    public void addNew(String empno,
                    String ename,
                    String job,
                    String mgr,
                    String hiredate,
                    String sal,
                    String comm,
                    String deptno) throws SQLException, ClassNotFoundException, ParseException{
        Connection c;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/netcracker","postgres", "tempus");
        c.setAutoCommit(false);
        PreparedStatement preparedStatement = c.prepareStatement(
                "INSERT INTO emp (empno,ename,job,mgr,hiredate,sal,comm,deptno)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setInt(1, Integer.parseInt(empno));
        preparedStatement.setString(2, ename);
        preparedStatement.setString(3, job);
        preparedStatement.setInt(4, Integer.parseInt(mgr));
        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(hiredate, formatter);
        preparedStatement.setDate(5, java.sql.Date.valueOf(date));
        preparedStatement.setInt(6, Integer.parseInt(sal));
        preparedStatement.setInt(7, Integer.parseInt(comm));
        preparedStatement.setInt(8, Integer.parseInt(deptno));

        preparedStatement.executeUpdate();

        c.commit();
        c.close();
    }
}
