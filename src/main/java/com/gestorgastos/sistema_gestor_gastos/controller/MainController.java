package com.gestorgastos.sistema_gestor_gastos.controller;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.service.CategoryManager;
import com.gestorgastos.sistema_gestor_gastos.service.TransactionManager;
import com.gestorgastos.sistema_gestor_gastos.service.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final TransactionManager txManager;
    private final CategoryManager catManager;

    public MainController() {
        this.txManager = new TransactionManager();
        this.catManager = new CategoryManager();
    }

    @FXML
    private Label incomeLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private Label totalLabel;

    @FXML
    private TextField descEditText;
    @FXML
    private TextField valueEditText;

    @FXML
    private ComboBox<Category> catComboBox;
    @FXML
    private ComboBox<TransactionType> typeComboBox;

    @FXML
    private Button addBtn;

    //Temporario
    private ObservableList<Category> catList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeComboBox.setItems(
                FXCollections.observableArrayList(TransactionType.values())
        );


        catManager.addCategory("Alimentação");
        catComboBox.setItems(catManager.getCatList());

    }



    @FXML
    private void addBtnClicked(ActionEvent event){
        System.out.println("Add Button Clicked");

        TransactionType type = typeComboBox.getValue();
        BigDecimal value = new BigDecimal(valueEditText.getText().trim().replace(",","."));
        Category cat = catComboBox.getValue();
        String desc = descEditText.getText().trim();

        txManager.addTransaction(type,value,cat,desc);
        txManager.printTxList();
    }

}


