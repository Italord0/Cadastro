package com.github.italord0.cadastro.controller

import com.github.italord0.cadastro.dao.UserDAO
import com.github.italord0.cadastro.model.User
import com.github.italord0.cadastro.util.AlertBox
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    private var selectedIds: List<Long?> = listOf()

    fun setupListeners() {
        //region buttons
        btnSave.setOnAction {
            saveUser()
        }

        btnDelete.setOnAction {
            try {
                if (selectedIds.size != 1) {
                    throw IllegalArgumentException("Select a item to delete")
                }
                userDao.delete(selectedIds.first() ?: 0)
                clearFields()
                refreshList()
            } catch (e: Exception) {
                AlertBox.display("Error", e.message.orEmpty())
            }
        }
        //endregion

        //region TableView
        usersTableView.selectionModel.selectedItems.addListener(ListChangeListener { change ->
            while (change.next()) {
                if (change.wasAdded()) {
                    selectedIds = change.addedSubList.toList().map(User::id)
                    if (selectedIds.size == 1) {
                        with(change.addedSubList[0]) {
                            txtName.text = name
                            txtEmail.text = email
                            txtDateOfBirth.text = convertLongToDateAndString(dateOfBirth)
                            txtBalance.text = balance.toString()
                        }
                    }
                }
            }
        })
        //endregion


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

    private fun saveUser() {
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

            if (selectedIds.size > 1) {
                throw IllegalArgumentException("Please select only one user to save")
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

            // Insert/update the user if all validations pass
            val user = User(selectedIds[0], name, email, dateOfBirthMillis, balance)
            if (selectedIds[0] != null) userDao.update(user) else userDao.insert(user)

            // Refresh the list and show success message
            refreshList()
            selectedIds = listOf()
            clearFields()

            AlertBox.display("Success", "User successfully registered!")
        } catch (e: Exception) {
            AlertBox.display("Error", e.message.orEmpty())
        }
    }

    private fun deleteUser() {

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

    private fun convertLongToDateAndString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(timestamp)
        return dateFormat.format(date)
    }

    private fun clearFields() {
        txtName.clear()
        txtEmail.clear()
        txtDateOfBirth.clear()
        txtBalance.clear()
    }
}