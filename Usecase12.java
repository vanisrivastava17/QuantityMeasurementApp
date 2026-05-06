import java.util.*;

/**
 * ============================================================
 * UC12: Subtraction & Division on Generic Quantity
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
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

/* -------------------- VOLUME UNIT -------------------- */
enum VolumeUnit implements Measurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
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
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    /* ---------- CONVERT ---------- */
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

    /* ---------- ADD ---------- */
    public Quantity<U> add(Quantity<U> other) {
        double sumBase = unit.toBase(value) +
                         other.unit.toBase(other.value);

        double result = unit.fromBase(sumBase);
        return new Quantity<>(result, unit);
    }

    /* ---------- SUBTRACT ---------- */
    public Quantity<U> subtract(Quantity<U> other) {
        double diffBase = unit.toBase(value) -
                          other.unit.toBase(other.value);

        double result = unit.fromBase(diffBase);
        return new Quantity<>(result, unit);
    }

    /* ---------- DIVISION ---------- */
    public double divide(Quantity<U> other) {
        double thisBase = unit.toBase(value);
        double otherBase = other.unit.toBase(other.value);

        if (Math.abs(otherBase) < 1e-12)
            throw new ArithmeticException("Division by zero");

        return thisBase / otherBase;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

/* -------------------- MAIN CLASS -------------------- */
public class UseCase12 {

    public static void main(String[] args) {

        System.out.println("UC12: Subtraction & Division\n");

        // -------- WEIGHT --------
        Quantity<WeightUnit> w1 = new Quantity<>(5, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(2000, WeightUnit.GRAM);

        System.out.println("Weight:");
        System.out.println("5 kg - 2000 g = " + w1.subtract(w2));
        System.out.println("5 kg / 2000 g = " + w1.divide(w2));

        // -------- VOLUME --------
        Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2, VolumeUnit.LITRE);

        System.out.println("\nVolume:");
        System.out.println("5 L - 2 L = " + v1.subtract(v2));
        System.out.println("5 L / 2 L = " + v1.divide(v2));
    }
}