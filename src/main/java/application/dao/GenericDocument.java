package main.java.application.dao;

import main.java.application.constants.MetaFlags;
import main.java.application.constants.SortType;
import java.util.Map;

/**
 * All the documents contain similar internal data structure
 */
public abstract class GenericDocument implements Document {
    protected Map<String, MetaFlags> mDocStore;
    protected String mContent;


    @Override
    public void setOrderRule(String fieldName, SortType sortType) {
        MetaFlags metaFlags = mDocStore.get(fieldName);

        if(metaFlags == null) {
            metaFlags = new MetaFlags();
        }

        metaFlags.sortType = sortType;
        mDocStore.put(fieldName, metaFlags);
    }

    @Override
    public void setFilterRule(String fieldName, String fieldStr) {
        MetaFlags metaFlags = mDocStore.get(fieldName);

        if(metaFlags == null) {
            metaFlags = new MetaFlags();
        }

        metaFlags.filterStr = fieldStr;
        mDocStore.put(fieldName, metaFlags);
    }
}
