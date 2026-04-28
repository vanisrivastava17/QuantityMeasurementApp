public class UseCase6 {

    // Enum for units
    enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double toInches;

        LengthUnit(double toInches) {
            this.toInches = toInches;
        }

        public double toBase(double value) {
            return value * toInches;
        }

        public double fromBase(double inches) {
            return inches / toInches;
        }
    }

    // Length class
    static class Length {
        double value;
        LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (inches)
        private double toInches() {
            return unit.toBase(value);
        }

        // UC6 Addition
        public Length add(Length other) {
            if (other == null || other.unit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumInInches = this.toInches() + other.toInches();

            double result = unit.fromBase(sumInInches);

            return new Length(result, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // Main method
    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2);

        System.out.println("Result: " + result);

        System.out.println(new Length(12, LengthUnit.INCHES)
                .add(new Length(1, LengthUnit.FEET)));

        System.out.println(new Length(1, LengthUnit.YARDS)
                .add(new Length(3, LengthUnit.FEET)));

        System.out.println(new Length(2.54, LengthUnit.CENTIMETERS)
                .add(new Length(1, LengthUnit.INCHES)));
    }
}