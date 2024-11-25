package com.example.androidaddandfetchfirestore.books;

public class books {
    private String title;
    private String author;

    private String genre;

    public books(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;

    }

    public books() {}

    //Getter and Setter for the book activity
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.author = genre;
    }
}
