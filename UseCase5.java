// UC5: Unit-to-Unit Conversion

public class UseCase5 {

    // Enum for units with conversion factors (base unit = inches)
    enum LengthUnit {
        INCHES(1.0),
        FEET(12.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // Conversion method
    public static double convert(double value, LengthUnit from, LengthUnit to) {
        if (from == null || to == null)
            throw new IllegalArgumentException("Invalid unit");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        // Step 1: convert to base unit (inches)
        double inInches = value * from.getFactor();

        // Step 2: convert to target unit
        double result = inInches / to.getFactor();

        return result;
    }

    // Main method for testing
    public static void main(String[] args) {

        System.out.println("1 FEET to INCHES = " +
                convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("3 YARDS to FEET = " +
                convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 INCHES to YARDS = " +
                convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

        System.out.println("1 CM to INCHES = " +
                convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));
    }
}