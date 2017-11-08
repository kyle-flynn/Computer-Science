package edu.gvsu.cis357.geocalculatorapp.dummy;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HistoryContent {
    public static final List<HistoryItem> ITEMS = new ArrayList<HistoryItem>();

    static {
        DateTime now = DateTime.now();
        addItem(new HistoryItem("43.12444", "-85.3523", "42.1234", "85.3555", now.minusDays(1)));
        addItem(new HistoryItem("42.12444", "-77.3523", "42.1234", "85.3555", now.minusDays(1)));
        addItem(new HistoryItem("43.12444", "-85.3523", "42.1234", "85.3555", now.plusDays(1)));
        addItem(new HistoryItem("55.12444", "-85.3523", "42.1234", "85.3555", now.plusDays(1)));
    }


    public static void addItem(HistoryItem item) {
        ITEMS.add(item);
    }

    public static class HistoryItem {
        public final String origLat;
        public final String origLng;
        public final String destLat;
        public final String destLng;
        public final DateTime timestamp;

        public HistoryItem(String origLat, String origLng, String destLat,
                           String destLng, DateTime timestamp) {
            this.origLat = origLat;
            this.origLng = origLng;
            this.destLat = destLat;
            this.destLng = destLng;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "(" + this.origLat + "," + this.origLat + ")";
        }
    }
}

