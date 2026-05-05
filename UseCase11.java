
/**
 * ============================================================
 * UC11: Volume Measurement using Generic Quantity (UC10)
 * ============================================================
 */

/* -------------------- INTERFACE -------------------- */
interface Measurable {
    double toBase(double value);
    double fromBase(double baseValue);
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
        return value * factor; // to litres
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
public class UseCase11 {

    public static void main(String[] args) {

        System.out.println("UC11: Volume Measurement\n");

        // Equality
        Quantity<VolumeUnit> v1 = new Quantity<>(1, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000, VolumeUnit.MILLILITRE);

        System.out.println("Equality Check:");
        System.out.println("1 L == 1000 mL ? " + v1.equals(v2));

        // Conversion
        Quantity<VolumeUnit> v3 = new Quantity<>(2, VolumeUnit.LITRE);
        Quantity<VolumeUnit> converted = v3.convertTo(VolumeUnit.GALLON);

        System.out.println("\nConversion:");
        System.out.println("2 L in gallons = " + converted);

        // Addition
        Quantity<VolumeUnit> v4 = new Quantity<>(500, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v5 = new Quantity<>(1, VolumeUnit.LITRE);

        Quantity<VolumeUnit> sum1 = v4.add(v5);
        Quantity<VolumeUnit> sum2 = v4.add(v5, VolumeUnit.GALLON);

        System.out.println("\nAddition:");
        System.out.println("500 mL + 1 L = " + sum1);
        System.out.println("500 mL + 1 L (in gallons) = " + sum2);
    }
}