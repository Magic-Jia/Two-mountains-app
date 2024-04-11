package com.example.ble;

public class Article {
    private String title;
    private int number;

    public Article(String title, int number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }
}