package uz.cluster.enums.forms;

import lombok.Getter;
import lombok.Setter;

public enum FormEnum {
    FORM(100),
    FORM_1(1),
    FORM_2(2),
    FORM_3(3),
    FORM_4(4),
    FORM_5(5),
    FORM_6(6),
    FORM_7(7),
    FORM_8(8),
    FORM_9(9),
    FORM_10(10),
    FORM_11(11),
    FORM_12(12),
    FORM_13(13),
    FORM_14(14),
    FORM_15(15),
    FORM_16(16),
    FORM_17(17),
    FORM_18(18),
    FORM_19(19),
    FORM_20(20),
    FORM_21(21),
    FORM_22(22),
    FORM_23(23),
    FORM_24(24),
    FORM_25(25),
    FORM_26(26),
    Form_27(27),
    Form_28(28),
    Form_29(29),


    REPORT(100),
    TOTAL_COST_CALCULATION(101),
    SELECT_COST_CALCULATION(102),
    AGRO_CLUSTER_TOTAL_COST(103),
    REFERENCES(110),
    SIMILAR_INFORMATION(111),
    BRIGADE_LIST(112),
    TERRITORY_LIST(113),
    FORM_LIST(114),
    TECHNIQUE_LIST(115),
    TECHNIQUE_MODEL_STANDARD(116),
    WATER_CONSUMPTION(117),
    CROP_RATIO(118),
    COUNTER_OF_MEASURING_DEVICES(119),
    MIN_PRICE(120),
    CURRENCY(121),
    ROlE_LIST(122),

    ADMIN_PANEL(130),
    ADMIN_PANEL_USER_LIST(131),
    ADMIN_PANEL_FORM_USER(132),
    ADMIN_PANEL_ADD_ADMIN(133);


    private final int value;

    @Getter
    @Setter
    private String name;

    FormEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    FormEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
