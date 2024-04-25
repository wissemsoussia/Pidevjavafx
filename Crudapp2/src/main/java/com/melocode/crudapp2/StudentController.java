package com.melocode.crudapp2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class StudentController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;


    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tDescription;

    @FXML
    private TextField tFTitle;

    @FXML
    private TextField tText;
    @FXML
    private TableColumn<Student, String> colDescription;

    @FXML
    private TableColumn<Student, String > colTitle;

    @FXML
    private TableColumn<Student, Integer > colid;

    @FXML
    private TableView<Student> table;
    int id=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    @FXML
    void clearPost(ActionEvent event) {
clear();
    }

    @FXML
    void createPost(ActionEvent event) {
String insert = "insert into student(Title,Description) values(?,?)";
con = DBConnexion.getCon();
        try {
            st= con.prepareStatement(insert);
            st.setString(1, tFTitle.getText());
            st.setString(2, tDescription.getText());
            st.executeUpdate();
            clear();
            showStudent();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
Student student = table.getSelectionModel().getSelectedItem();
id = student.getId();
tFTitle.setText(student.getTitle());
tDescription.setText(student.getDescription());
btnSave.setDisable(true);

    }
void clear() {
        tFTitle.setText(null);
        tDescription.setText(null);
        btnSave.setDisable(false);
}
    @FXML
    void deletePost(ActionEvent event) {
        String delete = "delete from student where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareCall(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showStudent();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updatePost(ActionEvent event) {
String update = "update student set Title = ?, Description = ? where id = ?";
con = DBConnexion.getCon();
        try {
            st = con.prepareCall(update);
            st.setString(1, tFTitle.getText());
            st.setString(2, tDescription.getText());
            st.setInt(3,id);
            st.executeUpdate();

            showStudent();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
