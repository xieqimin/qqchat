import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {
    //private ServerSocket serverSocket;
    //private DataInputStream in;
    //private DataOutputStream out;



    public static  void main(String[] args){
        new Thread(()->{
            try{
                ServerSocket serverSocket=new ServerSocket(8000);

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("lianjie cg");
                    new Thread(new fuwu(socket)).start();

                    //InetAddress inetAddress=serverSocket.getInetAddress();
                }
            }catch (IOException e){
                System.err.println(e);
            }
        }).start();
    }
}


class fuwu implements Runnable {
    private Socket socket;
    private Connection connection;
    private DataOutputStream out;
    private DataInputStream in;
    private PreparedStatement preparedStatement;//
    private boolean bb=false;

    public fuwu(Socket socket) {
        this.socket = socket;
    }
    private void Login() throws IOException {
        //
        System.out.println("验证密码");
        //

        String s=in.readUTF();
        System.out.println("接收成功");
        System.out.println(s);
        String[] ss=s.split(" ");
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select password from id_name where ID="+ss[0]);
            if(resultSet.next()){
                System.out.println(resultSet.getString(1));
                if(resultSet.getString(1).equals(ss[1])) {
                    out.writeUTF("密码正确");
                    bb=true;
                    //
                    System.out.println("密码正确");
                }
                else
                    out.writeUTF("密码错误");
            }
            else {
                out.writeUTF("没有此用户");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getName() throws IOException {//不安全

        String id=in.readUTF();//id
        System.out.println(id);
        try {
            System.out.println("查询数据库");
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select name from id_name where ID="+id);
            if(resultSet.next()){
                String s=resultSet.getString(1);
                System.out.println(s);
                out.writeUTF(s);
            }
           Statement statemen=connection.createStatement();
           ResultSet resultSe=statemen.executeQuery("select fid from friend where ID="+id);
            String fn="";
            while (resultSe.next()){
                String fid=resultSe.getString(1);
                System.out.println("fid"+fid);
                fn+=fid+"\n";
                Statement stateme=connection.createStatement();
                ResultSet rsultSet=stateme.executeQuery("select name from id_name where ID="+fid);
                if(rsultSet.next())
                    fn+=rsultSet.getString(1)+"\n";
            }
            System.out.println(fn);
            out.writeUTF(fn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void  Register() throws IOException {
        String s= in.readUTF();
        String[] ss=s.split(" ");

        try {
            Statement stateme = connection.createStatement();
            ResultSet rsultSet=stateme.executeQuery("select name from id_name where ID="+ss[0]);
            if(!rsultSet.next()){
                Statement statement=connection.createStatement();
                statement.executeUpdate("insert into id_name values("+ss[0]+","+ss[1]+","+"0,"+ss[2]+")");
                rsultSet=stateme.executeQuery("select name from id_name where ID="+ss[0]);
                if(rsultSet.next())
                    out.writeUTF("注册成功");
                out.writeUTF("注册失败");

            }else {
                out.writeUTF("注册失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void addFriend() throws IOException {
        //不安全
        String[] c=in.readUTF().split(" ");
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into friend values(" + c[0] + "," + c[1] + ")");
            out.writeUTF("成功");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void fMessage() throws IOException {
        String[] id=in.readUTF().split(" ");//fid id
        String m=in.readUTF();

    }
    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            connection =SQLCon.getCon();
            while (true) {
                int x = in.readInt();
                int ii=0;
                System.out.println(x);
                switch (x) {
                    case 10:Login();break;//登陆
                    case 20:getName();break;//获取名字 好友列表
                    case 30:Register();break;//注册
                    case 40:addFriend();break;//加好友
                    case 50:fMessage();break;
                    case 100:ii=100;break;//下线等一系列操作。。。
                    //zhu che
                    //liao tian
                    //sho xi
                    //fa
                    default:break;
                }
                if(ii==100)
                    break;
            }
        } catch (IOException o) {

        }

    }
}
class SQLCon {
    private  ArrayList<Connection> connections=null;
    private static SQLCon sqlCon=new SQLCon();
    private String password;
    private SQLCon()  {
        connections=new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0;i<10;i++){
            try {
                //cs
                Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/javabook?characterEncoding=utf8&useSSL=true", "root", "xiequn110234qq");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static Connection getCon(){
        if(sqlCon.connections.size()>0) {
            Connection connection = sqlCon.connections.get(0);
            sqlCon.connections.remove(connection);
            return connection;
        }
        else{
            Connection con= null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/javabook?characterEncoding=utf8&useSSL=true", "root", "xiequn110234qq");
                //System.out.println("连接成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }
    }
    public static void removeCon(Connection con){
        sqlCon.connections.add(con);
    }
    public static void clearCon(){
        sqlCon.connections.clear();
    }
}