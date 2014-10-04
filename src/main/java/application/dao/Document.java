package main.java.application.dao;

import main.java.application.constants.SortType;

/**
 * Created by ruogu on 10/3/14.
 */
public interface Document {
    public StringBuffer generate(Document srcDoc);
    public void setOrderRule(String fieldName, SortType sortType);
    public void setFilterRule(String fieldName, String fieldStr);
    public void setHeader(String header);
    public void setContent(String content);
}
