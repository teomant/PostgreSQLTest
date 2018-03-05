import java.sql.*;

public class TestMain {

    public static void main(String args[]){
        jdbcTest();
    }

    public static void jdbcTest(){
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "tempus");
            c.setAutoCommit(false);
            System.out.println("-- Opened database successfully");
            String sql;
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " CNAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        VARCHAR(50), " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            System.out.println("-- Table created successfully");
            //--------------- INSERT ROWS ---------------
            stmt = c.createStatement();
            sql = "INSERT INTO COMPANY (ID,CNAME,AGE,ADDRESS,SALARY) VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,CNAME,AGE,ADDRESS,SALARY) VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,CNAME,AGE,ADDRESS,SALARY) VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,CNAME,AGE,ADDRESS,SALARY) VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            System.out.println("-- Records created successfully");

            /*stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println(String.format("ID=%s NAME=%s AGE=%s ADDRESS=%s SALARY=%s",id,name,age,address,salary));
            }
            rs.close();
            stmt.close();
            c.commit();
            System.out.println("-- Operation SELECT done successfully");*/

            PreparedStatement preparedStatement = null;

            // ? - место вставки нашего значеня
            preparedStatement = c.prepareStatement(
                    "SELECT * FROM COMPANY where POSITION (? IN UPPER(CNAME))>?");
            //Устанавливаем в нужную позицию значения определённого типа
            preparedStatement.setString(1, "A");
            preparedStatement.setInt(2, 0);
            //выполняем запрос
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println("ID="+rs.getInt("ID")+" NAME="+rs.getString("CNAME")+
                        " AGE="+rs.getInt("age")+" ADDRESS="+rs.getString("Address")+" SALATY="
                        +rs.getFloat("salary"));
            }
            rs.close();
            stmt.close();
            c.commit();



        } catch (SQLException ex) {
            System.out.println("SQL exception");
            System.out.println(ex.getMessage());
        }catch (ClassNotFoundException ex) {

        }finally {
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "tempus");
                c.setAutoCommit(false);
                stmt = c.createStatement();
                String sql = "DROP TABLE COMPANY ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();
                System.out.println("-- Table dropped successfully");
            }catch (SQLException ex) {
                System.out.println("SQL exception");
                System.out.println(ex.getMessage());
            }catch (ClassNotFoundException ex) {

            }
        }
    }
}
