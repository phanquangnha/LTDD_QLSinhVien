package com.example.bt10sqlite.Model;

public class Computer {
    String idComputer,nameComputer,idCategory;


    public Computer(String idComputer, String nameComputer, String idCategory) {
        this.idComputer = idComputer;
        this.nameComputer = nameComputer;
        this.idCategory = idCategory;
    }

    public String getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(String idComputer) {
        this.idComputer = idComputer;
    }

    public String getNameComputer() {
        return nameComputer;
    }

    public void setNameComputer(String nameComputer) {
        this.nameComputer = nameComputer;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
}
