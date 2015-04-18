package volley.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fayez Abu Helow on 4/18/15.
 */
public class WeatherEntity implements Serializable {

    private List<WeatherRow> results = null;


    public List<WeatherRow> getResults() {
        return results;
    }


    public class WeatherRow implements Serializable
    {
        private int maxTemp;
        private int lowTemp;
        private String status;
        private String dayName;
        private String date;
        private String iconUrl;


        public int getMaxTemp() {
            return maxTemp;
        }

        public int getLowTemp() {
            return lowTemp;
        }

        public String getStatus() {
            return status;
        }

        public String getDayName() {
            return dayName;
        }

        public String getDate() {
            return date;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }
}
