/*
 * UC4 – Extended Unit Support
 */

public class UseCase4 {

    // ----------- ENUM (Extended Units) -----------
    enum LengthUnit {
        FEET(12.0),        // 1 ft = 12 in
        INCHES(1.0),       // base unit
        YARDS(36.0),       // 1 yard = 36 in
        CENTIMETERS(0.393701); // 1 cm = 0.393701 in

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor; // convert to inches
        }
    }

    // ----------- Length Class -----------
    static class Length {
        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

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

    // ----------- Demo -----------
    public static void main(String[] args) {

        Length a = new Length(1, LengthUnit.YARDS);
        Length b = new Length(3, LengthUnit.FEET);
        System.out.println("1 yard == 3 feet → " + a.equals(b)); // true

        Length c = new Length(1, LengthUnit.YARDS);
        Length d = new Length(36, LengthUnit.INCHES);
        System.out.println("1 yard == 36 inches → " + c.equals(d)); // true

        Length e = new Length(1, LengthUnit.CENTIMETERS);
        Length f = new Length(0.393701, LengthUnit.INCHES);
        System.out.println("1 cm == 0.393701 inch → " + e.equals(f)); // true

        Length g = new Length(2, LengthUnit.YARDS);
        Length h = new Length(2, LengthUnit.YARDS);
        System.out.println("2 yard == 2 yard → " + g.equals(h)); // true
    }
}