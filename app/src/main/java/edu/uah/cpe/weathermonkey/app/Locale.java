package fizzsoftware.weathermonkeyaprs;

public class Locale {
    private boolean key;
    private double latit;
    private String locale;
    private double longit;

    public Locale() {
        setLocale("custom");
    }

    public Locale(String name, double lat, double lng, boolean ky) {
        this.locale = name;
        this.latit = lat;
        this.longit = lng;
        this.key = ky;
    }

    public boolean getKey() {
        return this.key;
    }

    public double getLatit() {
        return this.latit;
    }

    public double getLongit() {
        return this.longit;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLatit(double latit2) {
        this.latit = latit2;
    }

    public void setLongit(double longit2) {
        this.longit = longit2;
    }

    public void setLocale(String locale2) {
        this.locale = locale2;
    }

    public void setKey(boolean key2) {
        this.key = key2;
    }

    public String toString() {
        if (getLatit() == 0.0d && getLongit() == 0.0d) {
            return getLocale();
        }
        return getLocale() + "  Lat = " + getLatit() + "  Long = " + getLongit();
    }
}
