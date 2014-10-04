package main.java.application.constants;

/**
 * This class contains a series of flags that indicate actions such as sort order
 * Making a class so we can easily add other criteria in the future
 */
public final class MetaFlags {
    public SortType sortType;
    public String  filterStr;

    public MetaFlags() {
        sortType = SortType.NO_SORT;
        filterStr = null;
    }
}
