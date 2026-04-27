/*
 * UC3 – Generic Quantity Class (DRY Principle)
 */

public class UseCase3 {

    // ----------- ENUM (Units + Conversion) -----------
    enum LengthUnit {
        FEET(12.0),     // 1 foot = 12 inches
        INCHES(1.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;   // convert to inches (base unit)
        }
    }

    // ----------- Generic Quantity Class -----------
    static class Length {
        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (inches)
        private double toBase() {
            return unit.toBase(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || this.getClass() != obj.getClass()) return false;

            Length other = (Length) obj;

            return Double.compare(this.toBase(), other.toBase()) == 0;
        }
    }

    // ----------- Demo Methods -----------
    public static void demonstrate() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length l3 = new Length(2.0, LengthUnit.FEET);

        System.out.println("1 ft == 12 in → " + l1.equals(l2)); // true
        System.out.println("1 ft == 2 ft → " + l1.equals(l3));  // false
    }

    // ----------- Main Method -----------
    public static void main(String[] args) {
        demonstrate();
    }
}