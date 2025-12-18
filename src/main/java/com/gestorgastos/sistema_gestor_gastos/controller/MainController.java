package com.gestorgastos.sistema_gestor_gastos.controller;

import com.gestorgastos.sistema_gestor_gastos.service.TransactionManager;

public class MainController {
    private final TransactionManager txManager;

    public MainController() {
        this.txManager = new TransactionManager();
    }
}
