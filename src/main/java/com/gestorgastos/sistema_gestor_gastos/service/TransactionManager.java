package com.gestorgastos.sistema_gestor_gastos.service;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.model.Transaction;
import com.gestorgastos.sistema_gestor_gastos.model.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionManager {
    private final ObservableList<Transaction> txList =
            FXCollections.observableArrayList();
    private int nextId;

    public TransactionManager() {
        this.nextId = 0;
    }

    //Adiciona uma nova transação na lista
    public void addTransaction(TransactionType type, BigDecimal value, Category cat, String desc){
        Date currentDate = new Date();
        txList.addLast(new Transaction(nextId++, type, value, cat, desc, currentDate));
    }

    //Remove uma transação da lista
    public void removeTransactionById(int id){
        for (int i = 0; i < txList.size(); i++){
            if (txList.get(i).getId() == id){
                txList.remove(i);
                break;
            }
        }
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

    //Printa a txList para debug
    public void printTxList(){
        for (Transaction tx : txList){
            System.out.println(tx.getDesc());
        }
    }

}
