package com.melocode.crudapp2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherController {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private TableColumn<Student, String> colDescription;

    @FXML
    private TableColumn<Student, String > colTitle;

    @FXML
    private TableColumn<Student, Integer > colid;

    @FXML
    private TableView<Student> table;
    int id=0;


   public void initialize(){
    showStudent();

}

    public ObservableList<Student> getStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "select * from student";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setTitle(rs.getString("title"));
                st.setDescription(rs.getString("description"));
                students.add(st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public void showStudent() {
        ObservableList<Student> list = getStudents();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Student, String>("Title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Student, String>("Description"));
    }









}
