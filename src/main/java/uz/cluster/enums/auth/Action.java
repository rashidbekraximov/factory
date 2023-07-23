package uz.cluster.enums.auth;

public enum Action {
    CAN_VIEW(1000),  //1000
    CAN_ADD(100),   //100
    CAN_EDIT(10),  //10
    CAN_DELETE(1); //1 ;

    final int value;

    Action(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
