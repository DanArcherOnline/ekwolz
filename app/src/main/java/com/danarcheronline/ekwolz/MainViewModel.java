package com.danarcheronline.ekwolz;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {

    Calculator calculator;


    public MainViewModel() {
        this.calculator = new Calculator();
    }



    public void operate(String operator) {
        calculator.operate(operator);
    }

    public void saveInput(String inputToAppend) {
        calculator.saveInput(inputToAppend);
    }

    public void clear() {
        calculator.clear();
    }

    public void equate() {
        calculator.equate();
    }



    public LiveData<String> getExpressionOutput() {
        return calculator.getExpressionOutput();
    }

    public String getOperator() {
        return calculator.getOperator();
    }

    public boolean isClearAll() {
        return calculator.isAllClear();
    }
}
