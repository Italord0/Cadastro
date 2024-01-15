package com.github.italord0.cadastro.controller

import com.github.italord0.cadastro.connection.ConnectionFactory
import com.github.italord0.cadastro.dao.UserDAO
import com.github.italord0.cadastro.model.User
import com.github.italord0.cadastro.util.AlertBox
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import java.sql.Savepoint
import java.text.ParseException
import java.text.SimpleDateFormat

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

    @FXML
    lateinit var txtName: TextField

    @FXML
    lateinit var txtEmail: TextField

    @FXML
    lateinit var txtDateOfBirth: TextField

    @FXML
    lateinit var txtBalance: TextField

    @FXML
    lateinit var btnSave: Button

    @FXML
    lateinit var btnDelete: Button

    private val userDao = UserDAO()

    fun setupListeners() {
        //button create
        btnSave.setOnAction {
            try {
                // Validate name
                val name = txtName.text
                if (name.isBlank()) {
                    throw IllegalArgumentException("Name cannot be empty")
                }

                // Validate email
                val email = txtEmail.text
                if (!isValidEmail(email)) {
                    throw IllegalArgumentException("Invalid email address")
                }

                // Validate date of birth
                val dateOfBirthText = txtDateOfBirth.text
                val dateOfBirthMillis = validateAndConvertDateOfBirth(dateOfBirthText)

                // Validate balance
                val balanceText = txtBalance.text
                val balance = try {
                    balanceText.replace(",", ".").toDouble()
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException("Balance must be a valid number")
                }

                // Insert the user if all validations pass
                userDao.insert(User(null, name, email, dateOfBirthMillis, balance))

                // Refresh the list and show success message
                refreshList()
                AlertBox.display("Success", "User successfully registered!")
            } catch (e: Exception) {
                AlertBox.display("Error", e.message.orEmpty())
            }
        }

        btnDelete.setOnAction {
            println("Delete clicked")
        }

        //table select
        usersTableView.selectionModel.selectedItems.addListener(ListChangeListener {

        })
    }

    fun loadData() {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        emailColumn.cellValueFactory = PropertyValueFactory("email")
        dateOfBirthColumn.cellValueFactory = PropertyValueFactory("formattedDate")
        balanceColumn.cellValueFactory = PropertyValueFactory("formattedBalance")
    }

    fun refreshList() {
        usersTableView.items = FXCollections.observableList(userDao.listAll())
    }

    fun updateUser() {

    }

    fun deleteUser() {

    }

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    // Function to validate and convert date of birth
    private fun validateAndConvertDateOfBirth(dateOfBirthText: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        try {
            val date = dateFormat.parse(dateOfBirthText)
            return date.time
        } catch (e: ParseException) {
            throw IllegalArgumentException("Invalid date of birth format. Use dd/MM/yyyy")
        }
    }
}