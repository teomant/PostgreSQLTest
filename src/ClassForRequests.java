import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClassForRequests {

    public static String getAll() throws SQLException, ClassNotFoundException{
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

    public static String getById(int id) throws SQLException, ClassNotFoundException{
        String result="";
        Connection c;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/netcracker","postgres", "tempus");
        c.setAutoCommit(false);
        PreparedStatement preparedStatement = c.prepareStatement(
                "SELECT DISTINCT * FROM EMP, SALGRADE, dept " +
                        "WHERE (emp.SAL BETWEEN SALGRADE.LOSAL AND SALGRADE.HISAL) AND" +
                        "(emp.deptno=dept.deptno) AND (emp.EMPNO=?)");
        preparedStatement.setInt(1, id);
        ResultSet rs =preparedStatement.executeQuery();
        c.commit();

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

    public static void deleteById(int id) throws SQLException, ClassNotFoundException{
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

    public static void addNew(String empno,
                       String ename,
                       String job,
                       String mgr,
                       String hiredate,
                       String sal,
                       String comm,
                       String deptno) throws SQLException, ClassNotFoundException, ParseException {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
