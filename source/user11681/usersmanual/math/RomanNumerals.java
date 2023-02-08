package user11681.usersmanual.math;

import net.auoeke.romeral.NumeralSystem;

import java.util.HashMap;
import java.util.Map;

public final class RomanNumerals {
    private static final Map<Long, String> cache = new HashMap<>();

    public static String fromDecimal(long decimal) {
        return cache.computeIfAbsent(decimal, NumeralSystem.standard::toRoman);
    }
}
