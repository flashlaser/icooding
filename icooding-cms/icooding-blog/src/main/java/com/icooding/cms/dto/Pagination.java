package com.icooding.cms.dto;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {
    private int pageNo = 1;
    private int pageSize = 10;
    private List<String> searchNames = new ArrayList<>();
    private List<String> searchValues  = new ArrayList<>();

    public int total;
    private List<T> rows;


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }


    public void addSearch(String name,String value){
        searchNames.add(name);
        searchValues.add(value);
    }

    public List<String> getSearchNames() {
        return searchNames;
    }

    public List<String> getSearchValues() {
        return searchValues;
    }
}
