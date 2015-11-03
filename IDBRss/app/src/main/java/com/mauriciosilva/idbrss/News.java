package com.mauriciosilva.idbrss;

/**
 * Created by Mauricio on 11/3/2015.
 */
public class News {
    private String name;
    private String author;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Author: " + getAuthor() + "\n" +
                "Link: " + getLink() + "\n";
    }
}
