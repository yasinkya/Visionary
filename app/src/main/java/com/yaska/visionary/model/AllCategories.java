package com.yaska.visionary.model;

import java.util.List;

public class AllCategories {

    Integer categoryId;
    String categoryTitle;
    private List<Movie> categoryItemList=null;

    public AllCategories(Integer categoryId, String categoryTitle, List<Movie> categoryItemList) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryItemList = categoryItemList;
    }

    public List<Movie> getCategoryItemList() {
        return categoryItemList;
    }
    public void setCategoryItemList(List<Movie> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
    public String getCategoryTitle() {
        return categoryTitle;
    }
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
