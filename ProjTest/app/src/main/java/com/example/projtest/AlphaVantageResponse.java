package com.example.projtest;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class AlphaVantageResponse {

    @SerializedName("Meta Data")
    private MetaData metaData;

    @SerializedName("Time Series (Daily)")
    private Map<String, DailyData> timeSeriesDaily;

    public MetaData getMetaData() {
        return metaData;
    }

    public Map<String, DailyData> getTimeSeriesDaily() {
        return timeSeriesDaily;
    }

    public static class MetaData {
        @SerializedName("1. Information")
        private String information;

        @SerializedName("2. Symbol")
        private String symbol;

        @SerializedName("3. Last Refreshed")
        private String lastRefreshed;

        @SerializedName("4. Output Size")
        private String outputSize;

        @SerializedName("5. Time Zone")
        private String timeZone;

        // Getters
        public String getInformation() { return information; }
        public String getSymbol() { return symbol; }
        public String getLastRefreshed() { return lastRefreshed; }
        public String getOutputSize() { return outputSize; }
        public String getTimeZone() { return timeZone; }
    }

    public static class DailyData {
        @SerializedName("1. open")
        private String open;

        @SerializedName("2. high")
        private String high;

        @SerializedName("3. low")
        private String low;

        @SerializedName("4. close")
        private String close;

        @SerializedName("5. volume")
        private String volume;

        // Getters
        public String getOpen() { return open; }
        public String getHigh() { return high; }
        public String getLow() { return low; }
        public String getClose() { return close; }
        public String getVolume() { return volume; }
    }
}