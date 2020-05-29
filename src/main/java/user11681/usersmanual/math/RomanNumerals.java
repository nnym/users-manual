package user11681.usersmanual.math;

import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import net.auoeke.romeral.NumeralSystem;

public final class RomanNumerals {
    private static final Long2ReferenceOpenHashMap<String> cache = new Long2ReferenceOpenHashMap<>();

    public static String fromDecimal(long decimal) {
        return cache.computeIfAbsent(decimal, NumeralSystem.standard::toRoman);
    }
}
