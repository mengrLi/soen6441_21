package model;

public class ResultMsg {
    private String msg;

    public static void printLog(String msg){
        System.out.println(msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
