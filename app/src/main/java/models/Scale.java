package models;

public class Scale {
    private String ID;
    private String name;
    private double initialWeight;
    private double currentWeight;
    private double percentage;

    public Scale(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInitialWeight(double initialWeight) {
        if (initialWeight >= 0) {
            this.initialWeight = initialWeight;
        }
    }

    public void setCurrentWeight(double currentWeight) {
        if (currentWeight >= 0) {
            this.currentWeight = currentWeight;
        }
    }

    public double getPercentage() {
        percentage = currentWeight/initialWeight;
        return percentage;
    }

    public static boolean equals(Scale scale1, Scale scale2) {
        if (scale1.name.equals(scale2.name) && scale1.ID.equals(scale2.ID)) {
            return true;
        }
        return false;
    }
}
