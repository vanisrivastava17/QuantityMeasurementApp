
/**
 * ============================================================
 * SINGLE FILE - UseCase9
 * Weight Measurement (kg, g, lb)
 * ============================================================
 */

// -------------------- ENUM --------------------
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    public double toBase(double value) {
        return value * toKgFactor; // convert to kg
    }

    public double fromBase(double kgValue) {
        return kgValue / toKgFactor; // convert from kg
    }
}

// -------------------- CLASS --------------------
class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // Convert to another unit
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double base = unit.toBase(value); // to kg
        double converted = targetUnit.fromBase(base);
        return new QuantityWeight(converted, targetUnit);
    }

    // Equality check
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisKg = unit.toBase(value);
        double otherKg = other.unit.toBase(other.value);

        return Math.abs(thisKg - otherKg) < 1e-6;
    }

    // Addition (same unit result)
    public QuantityWeight add(QuantityWeight other) {
        double sumKg = this.unit.toBase(this.value) +
                       other.unit.toBase(other.value);

        double result = this.unit.fromBase(sumKg);
        return new QuantityWeight(result, this.unit);
    }

    // Addition (target unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        double sumKg = this.unit.toBase(this.value) +
                       other.unit.toBase(other.value);

        double result = targetUnit.fromBase(sumKg);
        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

// -------------------- MAIN --------------------
public class UseCase9 {

    public static void main(String[] args) {

        System.out.println("Weight Measurement Operations\n");

        // Equality
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

        System.out.println("Equality Check:");
        System.out.println("1 kg == 1000 g ? " + w1.equals(w2));

        // Conversion
        QuantityWeight w3 = new QuantityWeight(2, WeightUnit.KILOGRAM);
        QuantityWeight converted = w3.convertTo(WeightUnit.POUND);

        System.out.println("\nConversion:");
        System.out.println("2 kg in pounds = " + converted);

        // Addition
        QuantityWeight w4 = new QuantityWeight(500, WeightUnit.GRAM);
        QuantityWeight w5 = new QuantityWeight(1, WeightUnit.KILOGRAM);

        QuantityWeight sum1 = w4.add(w5);
        QuantityWeight sum2 = w4.add(w5, WeightUnit.POUND);

        System.out.println("\nAddition:");
        System.out.println("500 g + 1 kg = " + sum1);
        System.out.println("500 g + 1 kg (in pounds) = " + sum2);
    }
}