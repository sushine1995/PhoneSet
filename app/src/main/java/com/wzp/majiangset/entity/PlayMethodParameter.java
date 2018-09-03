package com.wzp.majiangset.entity;

/**
 * Created by wzp on 2017/9/12.
 */

public class PlayMethodParameter {

    private BasicParameter basicParameter;
    private ChooseCardParameter chooseCardParameter;
    private DiceParameter diceParameter;

    public BasicParameter getBasicParameter() {
        return basicParameter;
    }

    public void setBasicParameter(BasicParameter basicParameter) {
        this.basicParameter = basicParameter;
    }

    public ChooseCardParameter getChooseCardParameter() {
        return chooseCardParameter;
    }

    public void setChooseCardParameter(ChooseCardParameter chooseCardParameter) {
        this.chooseCardParameter = chooseCardParameter;
    }

    public DiceParameter getDiceParameter() {
        return diceParameter;
    }

    public void setDiceParameter(DiceParameter diceParameter) {
        this.diceParameter = diceParameter;
    }
}
