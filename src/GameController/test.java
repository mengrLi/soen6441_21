package GameController;

import GameModel.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by liaoxiaoyun on 2019-04-01.
 */
public class test
{
    public static void main(String[] args){
        int cc = 0;
        ArrayList<Integer> aa = new ArrayList<>();
        aa.add(cc);

        ArrayList<Integer> bb = aa;

        aa.add(3);

        System.out.println(bb);

        Color ss = Color.black;

        int  name = ss.getRGB();
        System.out.println(name);

        GameState state = GameState.END;
       System.out.println(state.name());

       String player = "0,Cheater0,,Country5 Country2,4,Cheater";

       String[] playerInfo = player.split(",");
       System.out.println(playerInfo.length);
        System.out.println("playerInfo[2] :"+playerInfo[2]);

        System.out.println(Boolean.parseBoolean("true"));






    }
}
