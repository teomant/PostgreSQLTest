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
                    ClassForRequests.addNew(empnoField.getText(),
                            enameField.getText(),
                            jobField.getText(),
                            mgrField.getText(),
                            hiredateField.getText(),
                            salField.getText(),
                            commField.getText(),
                            deptnoField.getText());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        }
    };
}
