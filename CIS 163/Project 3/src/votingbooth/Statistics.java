package votingbooth;

import java.util.HashMap;

/**
 * Created by kylef_000 on 10/26/2016.
 */
public class Statistics {

    private static HashMap<String, Object> statistics = new HashMap<>();

    public static void addStatistic(String id, Object value) {
        statistics.put(id, value);
    }

    public static Object getStatistic(String id) {
        if (statistics.containsKey(id)) {
            return statistics.get(id);
        }
        return null;
    }

    public static void clearStatistics() {
        statistics.clear();
    }

}
