package GameModel.StrategyPlayer;

import GameModel.Player;

/**
 * Created by liaoxiaoyun on 2019-03-30.
 */
public interface Strategy {
    void autoReinforce(Player curPlayer);

    boolean autoAttack(Player curPlayer);

    void autoFortify(Player curPlayer);
}
