package GameModel.StrategyPlayer;

import GameModel.Player;
import GameModel.PlayerEngine;

/**
 * Created by liaoxiaoyun on 2019-03-30.
 */
public class Cheater implements Strategy{
    private PlayerEngine playerEngine;

    Cheater(){
        playerEngine = new PlayerEngine();
    }

    public void autoReinforce(Player curPlayer){

    };

    public boolean autoAttack(Player curPlayer){
        boolean ifWinned = false;

        return  ifWinned;
    };

    public void autoFortify(Player curPlayer){

    };
}
