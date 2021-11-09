package com.devworm.edufree;

public class Model {
    String nameofthecourse;
    String coursethubnail;
    String link;
    String search;
    String category;
    String about;

    public String getNameofthecourse() {
        return nameofthecourse;
    }

    public void setNameofthecourse(String nameofthecourse) {
        this.nameofthecourse = nameofthecourse;
    }

    public String getCoursethubnail() {
        return coursethubnail;
    }

    public void setCoursethubnail(String coursethubnail) {
        this.coursethubnail = coursethubnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Model(String nameofthecourse, String coursethubnail, String link, String search, String category, String about) {
        this.nameofthecourse = nameofthecourse;
        this.coursethubnail = coursethubnail;
        this.link = link;
        this.search = search;
        this.category = category;
        this.about = about;
    }
    public Model() {
    }
}
