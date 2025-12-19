package com.gestorgastos.sistema_gestor_gastos.service;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private final ObservableList<Category> catList =
            FXCollections.observableArrayList();
    private int nextId;

    public CategoryManager() {
        this.nextId = 0;
    }

    public void addCategory(String name){
        catList.add(new Category(nextId++, name));
    }

    //Remove uma Category da lista
    public void removeCategoryById(int Id){
        for (int i = 0; i < catList.size(); i++){
            if (catList.get(i).getId() == nextId){
                catList.remove(i);
                break;
            }
        }
    }

    //Retorna a lista completa
    public ObservableList<Category> getCatList(){
        return catList;
    }

}
