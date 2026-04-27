// UC7: Addition with Target Unit Specification

class Length {

    enum Unit {
        FEET, INCHES, YARDS, CENTIMETERS
    }

    private double value;
    private Unit unit;

    public Length(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    // Convert everything to base unit (inches)
    private double toInches() {
        switch (unit) {
            case FEET: return value * 12;
            case YARDS: return value * 36;
            case CENTIMETERS: return value / 2.54;
            case INCHES: return value;
        }
        return 0;
    }

    // Convert inches to target unit
    private double fromInches(double inches, Unit target) {
        switch (target) {
            case FEET: return inches / 12;
            case YARDS: return inches / 36;
            case CENTIMETERS: return inches * 2.54;
            case INCHES: return inches;
        }
        return 0;
    }

    // ✅ UC7 Method (main part)
    public Length add(Length other, Unit targetUnit) {

        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double totalInches = this.toInches() + other.toInches();
        double result = fromInches(totalInches, targetUnit);

        return new Length(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

public class UseCase7{
    public static void main(String[] args) {

        Length l1 = new Length(1, Length.Unit.FEET);
        Length l2 = new Length(12, Length.Unit.INCHES);

        // Example 1
        System.out.println(l1.add(l2, Length.Unit.FEET));      // 2 feet

        // Example 2
        System.out.println(l1.add(l2, Length.Unit.INCHES));    // 24 inches

        // Example 3
        System.out.println(l1.add(l2, Length.Unit.YARDS));     // ~0.667 yards

        // Example 4
        Length l3 = new Length(2.54, Length.Unit.CENTIMETERS);
        Length l4 = new Length(1, Length.Unit.INCHES);

        System.out.println(l3.add(l4, Length.Unit.CENTIMETERS)); // ~5.08 cm
    }
}