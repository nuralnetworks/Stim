package interfaces;

import models.Player;

public interface Purchasable {
    boolean purchase(Player player);
    boolean refund(Player player);
}
