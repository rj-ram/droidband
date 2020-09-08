package in.droidparkz.droidband;

public class SleepwindowItem {

    private String day;
    private String start;
    private String stop;
    private String duration;
    private String progress;

    public SleepwindowItem(String day, String start, String stop, String duration, String progress) {

        this.day = day;
        this.start = start;
        this.stop = stop;
        this.duration = duration;
        this.progress = progress;
    }

    public String getDay() {
        return day;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public String getDuration() {
        return duration;
    }

    public String getProgress() {
        return progress;
    }

}