package equipment;

import java.util.Objects;

/** Базовий клас з ЛР5 (спрощений): рівність за name, weightGrams, priceCents, класом. */
public class Equipment {
    private final String name;
    private final int weightGrams;
    private final int priceCents;

    public Equipment(String name, int weightGrams, int priceCents) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (weightGrams <= 0) throw new IllegalArgumentException("weight must be > 0");
        if (priceCents < 0) throw new IllegalArgumentException("price >= 0");
        this.name = name;
        this.weightGrams = weightGrams;
        this.priceCents = priceCents;
    }

    public String getName() { return name; }
    public int getWeightGrams() { return weightGrams; }
    public int getPriceCents() { return priceCents; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;
        Equipment that = (Equipment) o;
        return weightGrams == that.weightGrams
                && priceCents == that.priceCents
                && name.equals(that.name)
                && getClass() == that.getClass();
    }

    @Override public int hashCode() {
        return Objects.hash(name, weightGrams, priceCents, getClass());
    }

    @Override public String toString() {
        return String.format("%s[%s] %.2fkg $%.2f",
                name, getClass().getSimpleName(), weightGrams / 1000.0, priceCents / 100.0);
    }
}