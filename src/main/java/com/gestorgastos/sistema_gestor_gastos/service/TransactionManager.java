package com.gestorgastos.sistema_gestor_gastos.service;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import com.gestorgastos.sistema_gestor_gastos.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionManager {
    private final List<Transaction> txList;
    private final int nextId;

    public TransactionManager() {
        this.txList = new ArrayList<>();
        this.nextId = 0;
    }

    //Adiciona uma nova transação na lista
    public void addTransaction(Transactiontype type, BigDecimal value, Category cat, String desc){
        Date currentDate = new Date();
        txList.addLast(new Transaction(nextId, type, value, cat, desc, currentDate));
    }

    //Remove uma transação da lista
    public void removeTransactionById(int Id){
        for (int i = 0; i < txList.size(); i++){
            if (txList.get(i).getId() == nextId){
                txList.remove(i);
                break;
            }
        }
    }

    //Retorna a lista completa
    public List<Transaction> getTxList(){
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
}
