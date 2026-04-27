/*
 * UseCase1 – Feet Measurement Equality
 * Quantity Measurement App
 */

public class UseCase1 {

    // Inner class to represent Feet
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals() method
        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) return true;

            // Null or different class
            if (obj == null || this.getClass() != obj.getClass()) return false;

            // Cast
            Feet other = (Feet) obj;

            // Compare values
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("Comparing 1.0 ft and 1.0 ft: " + f1.equals(f2));
        System.out.println("Comparing 1.0 ft and 2.0 ft: " + f1.equals(f3));
        System.out.println("Comparing with null: " + f1.equals(null));
        System.out.println("Same reference: " + f1.equals(f1));
    }
}