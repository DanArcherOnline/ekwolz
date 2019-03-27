package com.danarcheronline.ekwolz;


import android.arch.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {

    private String currentInput = "";
    private float input1;
    private float input2;

    public String getCurrentInput() {
        return currentInput;
    }

    public void setCurrentInput(String currentInput) {
        this.currentInput = currentInput;
    }

    public void appendToCurrentInput(String inputToAppend) {
        if(this.currentInput.length() <= 9) {
            this.currentInput = currentInput + inputToAppend;
        }
    }

    public float getInput1() {
        return input1;
    }

    public void setInput1(float input1) {
        this.input1 = input1;
    }

    public float getInput2() {
        return input2;
    }

    public void setInput2(float input2) {
        this.input2 = input2;
    }
}
