package votingbooth;

import java.util.HashMap;

/*****************************************************************
Statistics helper class that essentially acts a database with a key
and value search/receive response. We need this class so that other
classes may access the data without creating new instances of it.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class Statistics {

    /** Static HashMap to store keys and values as statistics */
    private static HashMap<String, Object> statistics = new HashMap<>();

    /*****************************************************************
    Static method that adds a statistic to the HashMap, so that other
    classes may access the data.
    @param id The key at which to identify the statistic.
    @param value The value of the statistic, can be (almost) anything.
    *****************************************************************/
    public static void addStatistic(String id, Object value) {
        statistics.put(id, value);
    }

    /*****************************************************************
    Static method that simply clears the statistics, and resets the
    contents of the HashMap.
    *****************************************************************/
    public static void clearStatistics() {
        statistics.clear();
    }

    /*****************************************************************
    Static getter that returns the desired statistic's value from the
    HashMap.
    @return The desired statistic if found, as an Object. Null if it
    was not found.
    *****************************************************************/
    public static Object getStatistic(String id) {
        if (statistics.containsKey(id)) {
            return statistics.get(id);
        }

        /* If the statistic wasn't found, return nothing, or null, */
        return null;
    }

}
