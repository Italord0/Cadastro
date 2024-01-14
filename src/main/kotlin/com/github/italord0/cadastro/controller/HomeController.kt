package com.github.italord0.cadastro.controller

import com.github.italord0.cadastro.dao.UserDAO
import com.github.italord0.cadastro.model.User
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

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
        val userDao = UserDAO()
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        emailColumn.cellValueFactory = PropertyValueFactory("email")
        dateOfBirthColumn.cellValueFactory = PropertyValueFactory("formattedDate")
        balanceColumn.cellValueFactory = PropertyValueFactory("formattedBalance")
        usersTableView.items = FXCollections.observableList(userDao.listAll())
    }

    fun updateUser() {

    }

    fun deleteUser() {

    }
}