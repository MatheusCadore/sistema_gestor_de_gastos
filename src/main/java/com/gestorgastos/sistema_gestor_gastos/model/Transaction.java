package com.gestorgastos.sistema_gestor_gastos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Transaction {
        private int id;
        private TransactionType type;
        private BigDecimal value;
        private Category cat;
        private String desc;
        private LocalDate date;

        //Constructor
        public Transaction(int id, TransactionType type, BigDecimal value,
                           Category cat, String desc, LocalDate date) {
            this.id = id;
            this.type = type;
            this.value = value;
            this.cat = cat;
            this.desc = desc;
            this.date = date;
        }


        //Setters and Getters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIncome() {
            return type == TransactionType.Entrada;
        }

        public boolean isExpense(){
            return type == TransactionType.Saida;
        }

        public void setType(TransactionType type) {
            this.type = type;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        public Category getCat() {
            return cat;
        }

        public void setCat(Category cat) {
            this.cat = cat;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public TransactionType getType() { return type; }
}
