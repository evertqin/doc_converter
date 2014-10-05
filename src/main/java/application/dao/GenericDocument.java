package main.java.application.dao;

import main.java.application.constants.MetaFlags;
import main.java.application.constants.SortType;

import java.io.OutputStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * All the documents contain similar internal data structure
 */
public abstract class GenericDocument implements BaseDocument {
    public static final Logger log = Logger.getLogger(GenericDocument.class.getName());

    class Pair {
        public String fieldName;
        public SortType sortType;

        public Pair(String fieldName, SortType sortType) {
            this.fieldName = fieldName;
            this.sortType = sortType;
        }
    }

    protected Map<String, String> mFieldsToFilter;
    protected List<Pair> mFieldsToSort;

    protected List<Map<String, String>> mRawData;
    protected  List<Map<String, String>> mProcessedData;

    GenericDocument() {
        mFieldsToFilter = new HashMap<String, String>();
        mFieldsToSort = new ArrayList<Pair>();
        mRawData = new ArrayList<Map<String, String>>();
        mProcessedData = new ArrayList<Map<String, String>>();
    }

    public void process() {
        // first filter
        log.info("# filter rules: " + mFieldsToFilter.size());
        for(Map<String, String> dataMap : mRawData) {
            boolean satisfyFilter = true;
            Iterator it = mFieldsToFilter.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if(!dataMap.get(entry.getKey()).equals((String) entry.getValue())) {
                    satisfyFilter = false;
                    break;
                }
            }
            if(satisfyFilter) {
                mProcessedData.add(dataMap);
            }
        }

        // then sort the data
        log.info("# sort fields: " + mFieldsToSort.size());
        Collections.sort(mProcessedData, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> sm1, Map<String, String> sm2) {
                Iterator it = mFieldsToSort.iterator();
                return compare(sm1, sm2, it);
            }

            private int compare(Map<String, String> sm1, Map<String, String> sm2, Iterator it) {
                if(!it.hasNext()) {
                    return 0;
                }
                Pair p = (Pair) it.next();

                int val = sm1.get(p.fieldName).compareTo(sm2.get(p.fieldName));
                val = p.sortType == SortType.ASC ? val : -val;

                if(val == 0) {
                    return compare(sm1, sm2, it);
                } else {
                    return val;
                }
            }
        });
    }

    @Override
    public void setOrderRule(String fieldName, SortType sortType) {
        log.info("Setting order rule: \"" + fieldName + "\" with value \"" + sortType + "\".");
        mFieldsToSort.add(new Pair(fieldName, sortType));
    }

    @Override
    public void setFilterRule(String fieldName, String fieldStr) {
        log.info("Setting filter rule: \"" + fieldName + "\" with value \"" + fieldStr + "\".");
        mFieldsToFilter.put(fieldName, fieldStr);
    }
}
