package com.gestorgastos.sistema_gestor_gastos.service;

import com.gestorgastos.sistema_gestor_gastos.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private static final String FILE_PATH = "C:\\Users\\mathe\\Documents\\JavaProjects\\sistema_gestor_gastos\\src\\main\\java\\com\\gestorgastos\\sistema_gestor_gastos\\data\\categories.txt";
    private final ObservableList<Category> catList =
            FXCollections.observableArrayList();
    private int nextId;

    public CategoryManager() {
        this.nextId = 0;
    }

    public void addCategory(String name) {
        catList.add(new Category(nextId++, name));
    }

    //Remove uma Category da lista
    public void removeCategoryById(int id) {
        for (int i = 0; i < catList.size(); i++) {
            if (catList.get(i).getId() == id) {
                catList.remove(i);
                break;
            }
        }
    }

    public Category findCategoryById(int id) {
        for (Category cat : catList) {
            if (cat.getId() == id) {
                return cat;
            }
        }
        return null;
    }

    //Retorna a lista completa
    public ObservableList<Category> getCatList() {
        return catList;
    }

    public void saveCategoryList() {
        File file = new File(FILE_PATH);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Category cat : catList) {
                writer.write(cat.getId() + ";" + cat.getName());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCategoryList() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        catList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length != 2) continue;

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];

                catList.add(new Category(id, name));
            }

            nextId = catList.stream()
                    .mapToInt(Category::getId)
                    .max()
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

