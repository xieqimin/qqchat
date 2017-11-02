import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;
    public static  void main(String[] args){

    }
}

class Linkc implements Runnable{
    private String name;
    public Linkc(String n){
        name=n;
    }
    @Override
    public void run() {
    }
}

class SQLCon {
    private static ArrayList<Connection> connections=null;
    private static SQLCon sqlCon=new SQLCon();
    private String password;
    private SQLCon()  {
        connections=new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0;i<10;i++){
            try {
                Connection connection= DriverManager.getConnection("jdbc.mysql://47.95.215.58/javabook","root","ccccccccc");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static Connection getCon(){
        if(connections.size()>0) {
            Connection connection = connections.get(0);
            connections.remove(connection);
            return connection;
        }
        else{
            Connection con= null;
            try {
                con = DriverManager.getConnection("jdbc.mysql://47.95.215.58/javabook","root","ccccccccc");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }
    }
    public void removeCon(Connection con){
        connections.add(con);
    }
    public void clearCon(){
        connections.clear();
    }
}
class fuwu implements Runnable {
    private Socket socket;
    private Connection connection;
    private DataOutputStream out;
    private DataInputStream in;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                int x = in.readInt();
                switch (x) {
                    case 1:
                        int o = 1;
                        break;//登陆
                    //jian hao y
                    //zhu che
                    //liao tian
                    //sho xi
                    //fa
                    //hy
                }
            }
        } catch (IOException o) {

        }

    }
}