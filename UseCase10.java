
/**
 * ============================================================
 * UC10: Generic Quantity Class using Interface
 * ============================================================
 */

/* -------------------- INTERFACE -------------------- */
interface Measurable {
    double toBase(double value);
    double fromBase(double baseValue);
}

/* -------------------- WEIGHT UNIT -------------------- */
enum WeightUnit implements Measurable {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor; // to kg
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

/* -------------------- LENGTH UNIT -------------------- */
enum LengthUnit implements Measurable {
    METER(1.0),
    CENTIMETER(0.01),
    FOOT(0.3048);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor; // to meters
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

/* -------------------- GENERIC CLASS -------------------- */
class Quantity<U extends Measurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
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

    public U getUnit() {
        return unit;
    }

    /* ---------- CONVERSION ---------- */
    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.toBase(value);
        double converted = targetUnit.fromBase(base);
        return new Quantity<>(converted, targetUnit);
    }

    /* ---------- EQUALITY ---------- */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        double thisBase = unit.toBase(value);
        double otherBase = other.unit.toBase(other.value);

        return Math.abs(thisBase - otherBase) < 1e-6;
    }

    /* ---------- ADDITION ---------- */
    public Quantity<U> add(Quantity<U> other) {
        double sumBase = unit.toBase(value) +
                         other.unit.toBase(other.value);

        double result = unit.fromBase(sumBase);
        return new Quantity<>(result, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        double sumBase = unit.toBase(value) +
                         other.unit.toBase(other.value);

        double result = targetUnit.fromBase(sumBase);
        return new Quantity<>(result, targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

/* -------------------- MAIN CLASS -------------------- */
public class UseCase10 {

    public static void main(String[] args) {

        System.out.println("UC10: Generic Quantity System\n");

        /* ----------- WEIGHT ----------- */
        Quantity<WeightUnit> w1 = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000, WeightUnit.GRAM);

        System.out.println("Weight Equality:");
        System.out.println("1 kg == 1000 g ? " + w1.equals(w2));

        Quantity<WeightUnit> w3 = new Quantity<>(2, WeightUnit.KILOGRAM);
        System.out.println("2 kg to pound = " + w3.convertTo(WeightUnit.POUND));

        Quantity<WeightUnit> w4 = new Quantity<>(500, WeightUnit.GRAM);
        System.out.println("500g + 1kg = " + w4.add(w1));
        System.out.println("500g + 1kg in pound = " + w4.add(w1, WeightUnit.POUND));


        /* ----------- LENGTH ----------- */
        Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.METER);
        Quantity<LengthUnit> l2 = new Quantity<>(100, LengthUnit.CENTIMETER);

        System.out.println("\nLength Equality:");
        System.out.println("1 m == 100 cm ? " + l1.equals(l2));

        Quantity<LengthUnit> l3 = new Quantity<>(2, LengthUnit.METER);
        System.out.println("2 m to foot = " + l3.convertTo(LengthUnit.FOOT));

        Quantity<LengthUnit> l4 = new Quantity<>(50, LengthUnit.CENTIMETER);
        System.out.println("50cm + 1m = " + l4.add(l1));
    }
}