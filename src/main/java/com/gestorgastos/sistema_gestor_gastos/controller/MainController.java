package com.gestorgastos.sistema_gestor_gastos.controller;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.service.TransactionManager;
import com.gestorgastos.sistema_gestor_gastos.service.Transactiontype;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class MainController {
    private final TransactionManager txManager;

    public MainController() {
        this.txManager = new TransactionManager();
    }

    @FXML
    private Label incomeLabel;
    private Label expensesLabel;
    private Label totalLabel;

    @FXML
    private TextField descEditText;
    private TextField valueEditText;

    @FXML
    private ComboBox<Category> catComboBox;
    private ComboBox<Transactiontype> typeComboBox;

    @FXML
    private Button addBtn;

    @FXML
    private void addBtnClicked(ActionEvent event){
        System.out.println("Add Button Clicked");

        Transactiontype type = typeComboBox.getValue();
        BigDecimal value = new BigDecimal(valueEditText.getText().trim().replace(",","."));
        Category cat = catComboBox.getValue();
        String desc = descEditText.getText().trim();

        txManager.addTransaction(type,value,cat,desc);
    }

}


