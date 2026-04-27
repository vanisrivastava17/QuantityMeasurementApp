/*
 * UseCase2 – Feet and Inches Measurement Equality
 */

public class UseCase2 {

    // ----------- Feet Class -----------
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || this.getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ----------- Inches Class -----------
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || this.getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ----------- Feet Equality Demo -----------
    public static void demonstrateFeetEquality() {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("Feet Comparison:");
        System.out.println("1.0 ft == 1.0 ft → " + f1.equals(f2));
        System.out.println("1.0 ft == 2.0 ft → " + f1.equals(f3));
        System.out.println();
    }

    // ----------- Inches Equality Demo -----------
    public static void demonstrateInchesEquality() {

        Inches i1 = new Inches(10.0);
        Inches i2 = new Inches(10.0);
        Inches i3 = new Inches(20.0);

        System.out.println("Inches Comparison:");
        System.out.println("10 in == 10 in → " + i1.equals(i2));
        System.out.println("10 in == 20 in → " + i1.equals(i3));
        System.out.println();
    }

    // ----------- Main Method -----------
    public static void main(String[] args) {

        demonstrateFeetEquality();
        demonstrateInchesEquality();
    }
}