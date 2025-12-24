package com.gestorgastos.sistema_gestor_gastos.controller;

import com.gestorgastos.sistema_gestor_gastos.Utils.ValidationUtils;
import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.model.Transaction;
import com.gestorgastos.sistema_gestor_gastos.service.CategoryManager;
import com.gestorgastos.sistema_gestor_gastos.service.TransactionManager;
import com.gestorgastos.sistema_gestor_gastos.model.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.gestorgastos.sistema_gestor_gastos.model.TransactionType.*;

public class MainController implements Initializable {
    private final TransactionManager txManager = new TransactionManager();
    private final CategoryManager catManager = new CategoryManager();

    //Link das variaveis com os Ids do FXML
    @FXML
    GridPane headerGrid;
    @FXML
    private TextField descEditText;
    @FXML
    private TextField valueEditText;
    @FXML
    private ComboBox<Category> catComboBox;
    @FXML
    private ComboBox<TransactionType> typeComboBox;
    @FXML
    private ListView<Transaction> txListView;
    @FXML
    public Button addCatBtn;
    @FXML
    public Button clearListBtn;
    @FXML
    public Label incomeLabel;
    @FXML
    public Label expensesLabel;
    @FXML
    public Label totalLabel;

    @FXML
    private void addBtnClicked(ActionEvent event) {

        if (descEditText.getText().isBlank()) {
            showError("Descrição é obrigatória");
            return;
        }

        if (!ValidationUtils.isNumeric(valueEditText.getText())) {
            showError("Valor inválido. Use apenas números.");
            return;
        }

        if (catComboBox.getValue() == null) {
            showError("Selecione uma categoria");
            return;
        }
        if (typeComboBox.getValue() == null) {
            showError("Selecione um tipo");
            return;
        }

        TransactionType type = typeComboBox.getValue();
        BigDecimal value = new BigDecimal(valueEditText.getText().trim().replace(",", "."));
        Category cat = catComboBox.getValue();
        String desc = descEditText.getText().trim();

        txManager.addTransaction(type, value, cat, desc);
        clearForm();
    }

    @FXML
    public void onAddCatBtnClicked(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Nova Categoria");

        ButtonType confirmBtn =
                new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes()
                .addAll(confirmBtn, ButtonType.CANCEL);

        // Campo de texto
        TextField field = new TextField();
        field.setPromptText("Nome da categoria");

        // Container com padding
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.getChildren().add(field);

        // Aplica o container no Dialog
        dialog.getDialogPane().setContent(content);

        // Define o resultado
        dialog.setResultConverter(button -> {
            if (button == confirmBtn) {
                return field.getText();
            }
            return null;
        });

        // Exibe
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name ->
                catManager.addCategory(name.trim())
        );
        upDateTotals();
    }

    @FXML
    public void onClearListClicked(ActionEvent event) {
        txManager.getTxList().clear();
    }

    @FXML
    void upDateTotals(){
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        ObservableList<Transaction> list = txManager.getTxList();

        for (Transaction tx : list) {
            if (tx.isIncome()) {
                totalIncome = totalIncome.add(tx.getValue());
            } else if (tx.isExpense()) {
                totalExpense = totalExpense.add(tx.getValue());
            }
        }

        BigDecimal total = totalIncome.subtract(totalExpense);

        incomeLabel.setText(totalIncome.toString());
        expensesLabel.setText(totalExpense.toString());
        totalLabel.setText(total.toString());

    }

    //Função para configurar as views

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txManager.loadTxList();
        configureHeader();
        configureComboBoxes();
        configureListView();
        upDateTotals();
    }

    private void configureHeader() {
        headerGrid.getColumnConstraints().addAll(buildColumns());
        headerGrid.setHgap(15);
        headerGrid.setAlignment(Pos.CENTER_LEFT);
        headerGrid.setPadding(new Insets(0, 0, 0, 7));

        headerGrid.add(new Label("Descrição"), 0, 0);
        headerGrid.add(new Label("Categoria"), 1, 0);
        headerGrid.add(new Label("Valor"), 2, 0);
    }
    //Inicializa e popula os ComboBoxes da tela

    private void configureComboBoxes() {
        typeComboBox.setItems(
                FXCollections.observableArrayList(values())
        );

        catManager.addCategory("Alimentação");
        catManager.addCategory("Lazer");
        catManager.addCategory("Transporte");
        catManager.addCategory("Moradia");
        catManager.addCategory("Saúde");
        catComboBox.setItems(catManager.getCatList());
    }
    //Configura o ListView de transações, associa a lista observável e define célula customizada

    private void configureListView() {
        txListView.setItems(txManager.getTxList());
        txListView.setFixedCellSize(-1); // permite altura dinâmica
        txListView.setCellFactory(listView -> new TransactionCell());

        txManager.getTxList().addListener(
                (javafx.collections.ListChangeListener<Transaction>) c -> upDateTotals()
        );
    }
    //Cria uma coluna com largura fixa

    private ColumnConstraints fixedCol(double width) {
        ColumnConstraints c = new ColumnConstraints();
        c.setMinWidth(width);
        c.setPrefWidth(width);
        return c;
    }
    //Monta a estrutura de colunas compartilhada entre o cabeçalho e as células do ListView.

    private List<ColumnConstraints> buildColumns() {
        ColumnConstraints colDesc = fixedCol(200);
        ColumnConstraints colCat = fixedCol(120);
        ColumnConstraints colValue = fixedCol(100);

        ColumnConstraints growCol = new ColumnConstraints();
        growCol.setHgrow(Priority.ALWAYS);

        ColumnConstraints colBtn = fixedCol(80);

        return List.of(
                colDesc,
                colCat,
                colValue,
                growCol,
                colBtn
        );
    }

    //Célula customizada do ListView
    private class TransactionCell extends ListCell<Transaction> {

        @Override
        protected void updateItem(Transaction tx, boolean empty) {
            super.updateItem(tx, empty);

            if (empty || tx == null) {
                setGraphic(null);
                setStyle("");
                return;
            }

            if (tx.isExpense()) {
                setStyle("-fx-background-color: #ffcccc;"); // vermelho claro
            } else {
                setStyle("-fx-background-color: #ccffcc;"); // verde claro
            }

            Label descLabel = new Label(tx.getDesc());
            descLabel.setWrapText(true);
            descLabel.setMaxWidth(200);

            Label catLabel = new Label(tx.getCat().getName());
            Label valueLabel = new Label("R$ " + tx.getValue());

            Button deleteBtn = new Button("Excluir");
            deleteBtn.setOnAction(e ->
                    txManager.removeTransactionById(tx.getId())
            );

            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.getColumnConstraints().addAll(buildColumns());

            grid.add(descLabel, 0, 0);
            grid.add(catLabel, 1, 0);
            grid.add(valueLabel, 2, 0);
            grid.add(deleteBtn, 4, 0);

            setGraphic(grid);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        descEditText.clear();
        valueEditText.clear();
        catComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
    }

}


