package fizzsoftware.weathermonkeyaprs;

public class LLfromXY {
    public double calcluatedLong;
    public double calculatedLat;
    private double latSlope;
    private double longSlope;
    public double mapLatBottom = 25.0d;
    public double mapLatTop = 50.0d;
    public double mapLongLeft = -130.0d;
    public double mapLongRight = -65.0d;
    public double mapSizeX = 469.0d;
    public double mapSizeY = 247.0d;

    private void calculateScaleFactors() {
        this.latSlope = (this.mapLatTop - this.mapLatBottom) / (0.0d - this.mapSizeX);
        this.longSlope = (this.mapLongLeft - this.mapLongRight) / (0.0d - this.mapSizeY);
    }

    public LLfromXY() {
        calculateScaleFactors();
    }

    public void setMapValues(double mapHeightInPixels, double mapWidthInPixels, double topLatitude, double bottomLatitude, double leftLongitude, double rightLongitude) {
        this.mapSizeX = mapHeightInPixels;
        this.mapSizeY = mapWidthInPixels;
        this.mapLatTop = topLatitude;
        this.mapLatBottom = bottomLatitude;
        this.mapLongLeft = leftLongitude;
        this.mapLongRight = rightLongitude;
        calculateScaleFactors();
    }

    public double getLatitude(int mouseY) {
        return (this.latSlope * ((double) mouseY)) + this.mapLatTop;
    }

    public double getLongitude(int mouseX) {
        return (this.longSlope * ((double) mouseX)) + this.mapLongLeft;
    }
}
