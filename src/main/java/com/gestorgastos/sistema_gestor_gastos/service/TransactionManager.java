package com.gestorgastos.sistema_gestor_gastos.service;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.model.Transaction;
import com.gestorgastos.sistema_gestor_gastos.model.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class TransactionManager {
    private final ObservableList<Transaction> txList =
            FXCollections.observableArrayList();
    private int nextId;
    private static final String FILE_PATH = "data/transactions.txt";
    private final CategoryManager catManager = new CategoryManager();;

    public TransactionManager() {
        this.nextId = 0;
    }

    //Adiciona uma nova transação na lista
    public void addTransaction(TransactionType type, BigDecimal value, Category cat, String desc){
        LocalDate currentDate = LocalDate.now();
        txList.addLast(new Transaction(nextId++, type, value, cat, desc, currentDate));
        saveTxList();
    }

    //Remove uma transação da lista
    public void removeTransactionById(int id){
        for (int i = 0; i < txList.size(); i++){
            if (txList.get(i).getId() == id){
                txList.remove(i);
                break;
            }
        }
        saveTxList();
    }

    //Retorna a lista completa
    public ObservableList<Transaction> getTxList(){
        return txList;
    }

    //Busca transação na lista com o Id
    public Transaction getTxById(int id){
        for(Transaction tx : txList){
            if (tx.getId() == id){
                return tx;
            }
        }
        return null;
    }

    public void saveTxList(){
        File file = new File(FILE_PATH);

        // garante que a pasta exista
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Transaction tx : txList) {
                String line = String.format(
                        "%d;%s;%s;%s;%s;%s",
                        tx.getId(),
                        tx.getType().name(),
                        tx.getValue().toString(),
                        tx.getCat().getId(),
                        tx.getDesc(),
                        tx.getDate().toString()
                );

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar transações: " + e.getMessage());
        }
    }

    public void loadTxList(){
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return; // nada para carregar
        }

        txList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length != 6) {
                    continue; // linha inválida
                }

                int id = Integer.parseInt(parts[0]);
                TransactionType type = TransactionType.valueOf(parts[1]);
                BigDecimal value = new BigDecimal(parts[2]);

                int categoryId = Integer.parseInt(parts[3]);
                String description = parts[4];
                LocalDate date = LocalDate.parse(parts[5]);

                Category category = catManager.findById(categoryId);

                if (category == null) {
                    System.err.println("Categoria não encontrada (ID=" + categoryId + ")");
                    continue;
                }

                Transaction tx = new Transaction(
                        id,
                        type,
                        value,
                        category,
                        description,
                        date
                );

                txList.add(tx);
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar transações: " + e.getMessage());
        }
    }

}
