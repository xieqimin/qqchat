import javafx.application.Application;
import javafx.geometry.HPos;
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
    @Override
    public void start(Stage primaryStage) {
        /*try {
            socket=new Socket("47.95.215.5",8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = out =new DataOutputStream(socket.getOutputStream());
        }catch (IOException ex){
            ex.printStackTrace();
        }*/
        LoginUI chatUI =new LoginUI();
        primaryStage=chatUI.getUI();
        primaryStage.show();

    }
    private void checkpass(){
        //
    }
    private void Message(String fid,String id,String message){
        //
    }
    private boolean Register(String id ,String name,String password){
        //
        return false;//
    }
    private class Friend{
        private ArrayList<NewLable> friends=new ArrayList<>();
        public void addFriend(String ffname,String ffid){
            NewLable newLable=new NewLable(ffname,ffid);
            friends.add(newLable);
        }
        public Stage getUI(){
            VBox vBox=new VBox();
            for(int i=0;i<friends.size();i++){
                vBox.getChildren().add(friends.get(i));
            }
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(vBox);
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
        private String id;
        private String password;
        private TextField nameText=new TextField();
        private PasswordField passwText=new PasswordField();
        private Label label=new Label("");

        public String getId() {
            return id;
        }
        public String getPassword() {
            return password;
        }
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
            button.setOnAction(event ->{
                id=nameText.getText();
                password=passwText.getText();
                checkpass();
            });
            button1.setOnAction(e->{
                sta.show();
            });
            button2.setOnAction(e->{
               boolean b =Register(tid.getText(),tname.getText(),tpas.getText());
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
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            stage.setScene(scene);
            return stage;
        }
    }
   /* 版本一
    private class ChatUI{
        private String name;
        private String id;
        private String fid;
        private String fname;
        private int n=0;
        private GridPane gridPane=new GridPane();
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
            Label label= MessageUIFactory.getLable(m);
            gridPane.add(label,0,n);
            n++;
        }

        public  Stage getUI(){
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(gridPane);
            BorderPane borderPane=new BorderPane();
            HBox hBox=new HBox();
            TextArea outText=new TextArea();
            Button button=new Button("发送");
            Label fnameLable=new Label(fname);

            outText.setPrefRowCount(4);
            button.setOnAction(e->{
                String out=outText.getText();
                Message(fid,id,out);
                Label labe=MessageUIFactory.getLable(out);
                labe.setStyle("");//
                gridPane.add(labe,1,n);
                n++;
                gridPane.setHalignment(labe,HPos.RIGHT);
            });

            //
            //
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
}*/
//版本二
private class ChatUI{
    //试一试滑块
        private String name;
        private String id;
        private String fid;
        private String fname;
        private Label chatlabel=new Label();
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
           String s=chatlabel.getText();
           chatlabel.setText(s+"/n"+fname+":"+m);
        }
        public  Stage getUI(){
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(chatlabel);
            BorderPane borderPane=new BorderPane();
            HBox hBox=new HBox();
            TextArea outText=new TextArea();
            Button button=new Button("发送");
            Label fnameLable=new Label(fname);
            String s=chatlabel.getText();
            chatlabel.setText(s+"/n"+name+":"+out);
            outText.setPrefRowCount(4);
            button.setOnAction(e->{
                String out=outText.getText();
                Message(fid,id,out);
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