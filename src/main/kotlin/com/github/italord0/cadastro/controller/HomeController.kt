package com.github.italord0.cadastro.controller

import com.github.italord0.cadastro.model.User
import com.github.italord0.cadastro.util.AlertBox
import com.github.italord0.cadastro.util.SampleUsers
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import java.util.Date

class HomeController {

    @FXML
    lateinit var idColumn: TableColumn<User, Int>

    @FXML
    lateinit var nameColumn: TableColumn<User, String>

    @FXML
    lateinit var emailColumn: TableColumn<User, String>

    @FXML
    lateinit var dateOfBirthColumn: TableColumn<User, String>

    @FXML
    lateinit var balanceColumn: TableColumn<User, Double>

    @FXML
    lateinit var usersTableView: TableView<User>

    fun loadData() {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        emailColumn.cellValueFactory = PropertyValueFactory("email")
        dateOfBirthColumn.cellValueFactory = PropertyValueFactory("formattedDate")
        balanceColumn.cellValueFactory = PropertyValueFactory("formattedBalance")
        usersTableView.items = FXCollections.observableList(SampleUsers.users)
    }

    fun updateUser() {

    }

    fun deleteUser() {

    }
}