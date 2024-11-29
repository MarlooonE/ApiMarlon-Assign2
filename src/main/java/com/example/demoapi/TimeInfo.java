package com.example.demoapi;

public class TimeInfo {
    private String timezone;
    private String utcOffset;
    private String datetime;
    private int dayOfWeek;
    private int dayOfYear;
    private int weekNumber;
    private boolean dst;
    private String abbreviation;

    // Constructor
    public TimeInfo(String timezone, String utcOffset, String datetime, int dayOfWeek, int dayOfYear, int weekNumber, boolean dst, String abbreviation) {
        this.timezone = timezone;
        this.utcOffset = utcOffset;
        this.datetime = datetime;
        this.dayOfWeek = dayOfWeek;
        this.dayOfYear = dayOfYear;
        this.weekNumber = weekNumber;
        this.dst = dst;
        this.abbreviation = abbreviation;
    }

    // Getters
    public String getTimezone() {
        return timezone;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public boolean isDst() {
        return dst;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return "Timezone: " + timezone + "\n" +
                "UTC Offset: " + utcOffset + "\n" +
                "Datetime: " + datetime + "\n" +
                "Day of Week: " + dayOfWeek + "\n" +
                "Day of Year: " + dayOfYear + "\n" +
                "Week Number: " + weekNumber + "\n" +
                "DST: " + (dst ? "Yes" : "No") + "\n" +
                "Abbreviation: " + abbreviation;
    }
}
