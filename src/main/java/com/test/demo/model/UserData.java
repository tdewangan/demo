package com.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    int page;
    int per_page;
    int total;
    int total_pages;
    List<User> data;
    Object ad;



    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public Object getAd() {
        return ad;
    }

    public void setAd(Object ad) {
        this.ad = ad;
    }
}
