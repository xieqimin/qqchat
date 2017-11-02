import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class Client extends Application{
    private DataInputStream in;
    private DataOutputStream out;
    private String myid;//
    private String myname;//
    private  Socket socket;
    private Stage sttage;
    @Override
    public void start(Stage primaryStage) {
        try {
            socket=new Socket("localhost",8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = out =new DataOutputStream(socket.getOutputStream());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        LoginUI chatUI =new LoginUI();
        sttage=chatUI.getUI();
        sttage.show();

    }
    private String checkpass(String ID,String pass) throws IOException {
        out.writeInt(10);
        out.writeUTF(ID+" "+pass);
        String s=in.readUTF();
        if(s.equals("密码正确")){
            out.writeInt(20);
            out.writeUTF(ID);
            myid=ID;
            String name=in.readUTF();
            Friend friend=new Friend(name);
            myname=name;
            String c=in.readUTF();
            String[] cc=c.split("\n");
            for(int x=0;x<cc.length;x+=2){
                String n=cc[x+1];
                friend.addFriend(n,cc[x]);
            }
            sttage=friend.getUI();
            sttage.show();
        }
        return s;

    }
    private void fMessage(String fid,String id,String message){
        try {
            out.writeInt(50);
            out.writeUTF(fid+" "+id);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String sMessage(String fid,String id){
        return "fff";
    }
    private boolean addFriend(String id ,String fid){
        try {
            //好友不存在
            out.writeInt(40);
            out.writeUTF(id+" "+fid);
            String s= in.readUTF();
            if(s.equals("成功"))
                return true;
            else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean Register(String id ,String name,String password){
        try {
            out.writeInt(30);
            out.writeUTF(id+" "+name+" "+password);
            String s= in.readUTF();
            if(s.equals("注册成功"))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;//
    }
    private class Friend{
        //待完善
        private Label label=new Label();
        private ArrayList<NewLable> friends=new ArrayList<>();
        public void addFriend(String ffname,String ffid){
            NewLable newLable=new NewLable(ffname,ffid);
            friends.add(newLable);
        }
        public Friend(String name){
            label.setText(name);
        }
        public Stage getUI(){
            VBox vBox=new VBox();
            for(int i=0;i<friends.size();i++){
                vBox.getChildren().add(friends.get(i));
            }
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(vBox);
            BorderPane borderPane=new BorderPane();
            borderPane.setCenter(scrollPane);
            borderPane.setTop(label);
            Scene scene=new Scene(borderPane);
            Stage stage=new Stage();
            stage.setScene(scene);
            return stage;
        }
    }
    private class NewLable extends Label{
        private String fID;
        public NewLable(String fname,String fID){
            this.setText(fname);
            this.fID=fID;
            this.setOnMouseClicked(e->{
                ChatUI chatUI=new ChatUI(myname,myid,fname,fID);
                Stage stag=chatUI.getUI();
                stag.show();
            });
        }
        public String getfID() {
            return fID;
        }
    }
    private class  LoginUI{//client
        private TextField nameText=new TextField();
        private PasswordField passwText=new PasswordField();
        private Label label=new Label("");
        public void setLabel(String text) {
            this.label.setText(text);
        }

        public  Stage getUI(){
            GridPane gridPane=new GridPane();
            //gridPane.setPadding(new Insets(11,12,15,11));
            gridPane.setVgap(5.5);
            gridPane.setHgap(6);
            gridPane.setAlignment(Pos.CENTER_LEFT);
            Label label1=new Label("账号");
            Label label2=new Label("密码");
            Button button=new Button("登陆");
            Button button1=new Button("注册账号");

            GridPane grid=new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            TextField tname=new TextField();
            TextField tid=new TextField();
            PasswordField tpas=new PasswordField();
            Button button2=new Button("注册");
            Label lreg=new Label("");
            grid.add(new Label("账号"),0,0);
            grid.add(tid,1,0);
            grid.add(new Label("用户名"),0,1);
            grid.add(tname,1,1);
            grid.add(new Label("密码"),0,2);
            grid.add(tpas,1,2);
            grid.add(button2,1,3);
            grid.add(lreg,0,4);
            Scene sce=new Scene(grid);
            Stage sta=new Stage();
            sta.setMinHeight(350);
            sta.setMinWidth(450);
            sta.setScene(sce);

            button1.setOnAction(e->{
                sta.show();
            });
            button2.setOnAction(e->{
               boolean b =Register(tid.getText(),tname.getText(),tpas.getText());
               //检查账号密码是否符合规范
               if(b){
                   lreg.setText("注册成功");
                   sta.close();
               }
               else{
                   lreg.setText("该账号已被注册");
               }
            });
            button.setPrefSize(150,40);
            button.setStyle("-fx-text-fill: rgb(49, 89, 23);-fx-background:#09a3dc;" +
                    "-fx-color: #09a3dc;" + "-fx-border-radius: 5;" + "-fx-padding: 3 6 6 6;");
            nameText.setPrefSize(260,40);
            passwText.setPrefSize(260,40);
            gridPane.add(label1,0,0);
            gridPane.add(nameText,1,0);
            gridPane.add(label2,0,1);
            gridPane.add(passwText,1,1);
            gridPane.add(button,1,2);
            gridPane.add(label,1,3);
            gridPane.add(button1,0,3);
            Scene scene=new Scene(gridPane);
            Stage stage=new Stage();
            button.setOnAction(event ->{
                String id=nameText.getText();
                String password=passwText.getText();
                try {
                    String st=checkpass(id,password);
                    setLabel(st);
                    stage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            stage.setScene(scene);
            return stage;
        }
    }

//版本二
private class ChatUI{
    //试一试滑块
        private String name;
        private String id;
        private String fid;
        private String fname;
        private TextArea chatlabel=new TextArea();
        public String getFid() {
            return fid;
        }
        public ChatUI(String name,String id,String fname,String fid){
            this.name=name;
            this.id=id;
            this.fid=fid;
            this.fname=fname;
        }
        public void addMessage(String m){
           chatlabel.appendText("\n"+fname+":"+m);
        }
        public  Stage getUI(){
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(chatlabel);
            BorderPane borderPane=new BorderPane();
            HBox hBox=new HBox();
            TextArea outText=new TextArea();
            Button button=new Button("发送");
            Label fnameLable=new Label(fname);

            outText.setPrefRowCount(4);
            button.setOnAction(e->{
                String out=outText.getText();
                fMessage(fid,id,out);
                chatlabel.appendText("\n"+name+":"+out);//加时间
                outText.setText("");
            });

            hBox.getChildren().add(outText);
            hBox.getChildren().add(button);
            borderPane.setTop(fnameLable);
            borderPane.setCenter(scrollPane);
            borderPane.setBottom(hBox);
            Scene scene=new Scene(borderPane);
            Stage stage=new Stage();
            stage.setScene(scene);
            return stage;
        }
    }
}



class AddUIFactory{
   // public static Stage getUI(){
   //     //
   // }
}
/*class MessageUIFactory{
    public static Label getLable(String m){
        Label label=new Label(m);
        label.setStyle("-fx-color:#dddddc; -fx-border-radius: 7;-fx-text-fill: #000000;");
        return label;
    }
}*/