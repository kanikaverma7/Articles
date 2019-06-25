package com.dexter.newsapp.ui;


public class ItemData {

    private String text;
    private String value;

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemData(String text, String value){
        this.text=text;
     this.value=value;
    }

    public String getText(){
        return text;
    }

}