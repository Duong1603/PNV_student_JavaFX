package com.example.pnstudentmanagement;

import com.example.pnstudentmanagement.Data.DBconnection;
import com.example.pnstudentmanagement.Data.models.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//import static javax.swing.text.SimpleAttributeSet.EMPTY;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DBconnection db = new DBconnection();       //CONNECT DATABASE
//       ArrayList<Student> stdlist = db.getStudent();        //GÁN DATABASE VÀO ARRAY
//       System.out.println("Size "+ stdlist.size());         //SHOW TỔNG GIÁ TRỊ
//        db.add(new Student("Linh", 10));              // THÊM 1 VALUE MỚI
//        db.update(2,new Student("giang",10));              // SỬA 1 VALUE
//        db.delete(btndelete);

        VBox vBox = new VBox();

        getDB(vBox,db);
        Scene scene = new Scene( vBox,700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    //GIAO DIỆN SHOW VÀ LẤY DỮ LIỆU
    void getDB(VBox vBox, DBconnection db){
        ArrayList<Student> stdlist = db.getStudent();
        System.out.println("Size "+ stdlist.size());

        for(int i = 0; i<stdlist.size();i++){

            TextField idTxt = new TextField();
            idTxt.setPromptText("id");
            TextField nameTxt = new TextField();
            nameTxt.setPromptText("name");
            TextField scoreTxt = new TextField();
            scoreTxt.setPromptText("score");
            int finalI = i;

            //add
            Button btnadd =new Button("ADD");
            btnadd.setOnAction(ActionEvent->{
                String name = nameTxt.getText();
                float score = Float.parseFloat(scoreTxt.getText());
                db.add(new Student(name,score));
                vBox.getChildren().clear();
                getDB(vBox,db);
            });


            // UPDATE
            Button btnupdate = new Button("EDIT");
            btnupdate.setOnAction(ActionEvent ->{
                for (int x = 0; x==finalI;x++) {
                    idTxt.setText(""+stdlist.get(x).getId());
                    nameTxt.setText(stdlist.get(x).getName());
                    scoreTxt.setText("" + stdlist.get(x).getScore());
                }
            });
            Button newbtnupdate = new Button("UPDATE");
            newbtnupdate.setOnAction(actionEvent -> {
                int newid = Integer.parseInt(idTxt.getText());
                String newname = nameTxt.getText();
                float newscore = Float.parseFloat(scoreTxt.getText());
                db.update(newid, new Student(newname, newscore));
                vBox.getChildren().clear();
                getDB(vBox,db);
            });



            //DELETE
            Button btndelete = new Button("DELETE");
            btndelete.setOnAction(ActionEvent ->{
                db.delete(stdlist.get(finalI).id);
                vBox.getChildren().clear();
                getDB(vBox, db);
            });

            //show giao diện
            HBox hBoxdesign =new HBox();
            for (int j=0;j==i;j++){
                if(j>=1){
                    vBox.getChildren().removeAll(btnadd,hBoxdesign);
                }
                else {
                    vBox.getChildren().addAll(btnadd,newbtnupdate,hBoxdesign);
                }
            }
            //show
            HBox hBoxStudent = new HBox();
            hBoxStudent.setSpacing(30);
            Label lbid = new Label(""+stdlist.get(i).id);
            Label lbname = new Label(""+stdlist.get(i).name);
            Label lbscore = new Label(""+stdlist.get(i).score);

            hBoxdesign.getChildren().addAll(idTxt,nameTxt,scoreTxt);
            hBoxStudent.getChildren().addAll(lbid,lbname,lbscore,btndelete,btnupdate);
            vBox.getChildren().addAll(hBoxStudent);
        }
    }



    public static void main(String[] args) {
        launch();
    }
}