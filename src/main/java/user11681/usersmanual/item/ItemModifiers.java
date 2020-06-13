package user11681.usersmanual.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ItemModifiers {
    public static final List<UUID> RESERVED_IDENTIFIERS = new ArrayList<>();

    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    static {
        RESERVED_IDENTIFIERS.add(ItemModifiers.ATTACK_DAMAGE_MODIFIER_ID);
        RESERVED_IDENTIFIERS.add(ItemModifiers.ATTACK_SPEED_MODIFIER_ID);
    }
}
