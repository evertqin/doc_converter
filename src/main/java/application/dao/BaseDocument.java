package main.java.application.dao;

import main.java.application.constants.SortType;

import java.io.OutputStream;

/**
 * Created by ruogu on 10/3/14.
 */
public interface BaseDocument {
    public void generate(OutputStream out);
    public void setOrderRule(String fieldName, SortType sortType);
    public void setFilterRule(String fieldName, String fieldStr);
    public void parse(String content);
}
