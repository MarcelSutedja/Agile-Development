package com.example.schoolapp;

public class ModuleData {
    double examGrade;
    double cwGrade;
    double examPercentage;
    double creditModule;
    String moduleName;
    public ModuleData(){

    }

    public ModuleData(double examGrade, double cwGrade, double examPercentage, double creditModule, String moduleName) {
        this.examGrade = examGrade;
        this.cwGrade = cwGrade;
        this.examPercentage = examPercentage;
        this.creditModule = creditModule;
        this.moduleName = moduleName;
    }

    public double getExamGrade() {
        return examGrade;
    }

    public double getCwGrade() {
        return cwGrade;
    }

    public double getExamPercentage() {
        return examPercentage;
    }

    public double getCreditModule() {
        return creditModule;
    }

    public String getModuleName() {
        return moduleName;
    }
}
