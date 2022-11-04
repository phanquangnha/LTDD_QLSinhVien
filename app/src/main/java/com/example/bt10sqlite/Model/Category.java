package com.example.bt10sqlite.Model;

public class Category {
    String idCategory,nameCategory;

    public Category() {
    }

    public Category(String idCategory, String nameCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "ID: "+ idCategory +"\n"+"Name: "+nameCategory;
    }
}
