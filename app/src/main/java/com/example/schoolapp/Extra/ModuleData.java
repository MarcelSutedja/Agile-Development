package com.example.schoolapp.Extra;

public class ModuleData {
    double examGrade;
    double cwGrade;
    double examPercentage;
    double creditModule;
    String moduleName;
    double finalMark;
    public ModuleData(){

    }

    public ModuleData(double examGrade, double cwGrade, double examPercentage, double creditModule, String moduleName,double finalMark) {
        this.examGrade = examGrade;
        this.cwGrade = cwGrade;
        this.examPercentage = examPercentage;
        this.creditModule = creditModule;
        this.moduleName = moduleName;
        this.finalMark = finalMark;
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

    public double getFinalMark() {
        return finalMark;
    }
}
