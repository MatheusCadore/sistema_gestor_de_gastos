package com.gestorgastos.sistema_gestor_gastos.model;

import com.gestorgastos.sistema_gestor_gastos.service.Transactiontype;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
        private int id;
        private Transactiontype type;
        private BigDecimal value;
        private Category cat;
        private String desc;
        private Date date;

        //Constructor
        public Transaction(int id, Transactiontype type, BigDecimal value,
                           Category cat, String desc, Date date) {
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
            return type == Transactiontype.INCOME;
        }

        public boolean isExpense(){
            return type == Transactiontype.EXPENSE;
        }

        public void setType(Transactiontype type) {
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

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
}
