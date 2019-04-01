package GameController;

import java.util.ArrayList;

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

    }
}
