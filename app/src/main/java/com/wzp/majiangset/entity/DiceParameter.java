package com.wzp.majiangset.entity;

/**
 * Created by wzp on 2017/9/11.
 */

public class DiceParameter {

    private int diceNum;
    private int useDiceTimes;
    private int useDiceMethod;
    private int startCardMethod;
    private int startCardSupplementFlowerMethod;
    private int startCardReserve1; // 起牌备用1
    private int startCardReserve2; // 起牌备用2
    private boolean isOneFiveNineGetCard;
    private boolean isEastSouthWestNorthAsColorCard;
    private boolean isZhongFaBaiAsColorCard; // 中发白当花牌
    private boolean isAllWindCardAsColorCard;
    private boolean isBankerAndLastPlayerChangePosition; // 庄家和上家互换位置
    private boolean isOpenWealthGodMode;
    private int wealthGodStartCardMethod;
    private int wealthGodUseDiceMethod;
    private int wealthGodCondition;
    private int windCardWealthGodLoopMethod;
    private int fixedWealthGod;
    private int wealthGodLastBlockNum;
    private int wealthGodStartCardPosition;
    private int wealthGodPrecedenceNum;
    private int wealthGodReserve; // 财神备用
    private boolean isZhongAsFixedWealthGod;
    private boolean isColorCardAsFixedWealthGod;
    private boolean isYiTiaoAsFixedWealthGod;
    private boolean isBaiBanAsFixedWealthGod;
    private boolean isYaojiufeng;
    private boolean isYaojiufengSuanGan; // 幺九风算杆
    private boolean isBaibanAsWealthGodSubstitute;
    private boolean isFanpaifengpaiAsWealthGod;
    private boolean is13579;
    private boolean isEastSouthWestNorthOrZhongFaBaiBusuandacha; // 东南西北/中发白不算大岔
    private boolean isWealthGodIsEastWind; // 翻花牌，财神为东风
    private boolean isRedFlowerAsFixedWealthGod;
    private boolean isBlackFlowerAsFixedWealthGod;
    private boolean isBaidaAsFixedWealthGod;
    private boolean isDabaipiAsFixedWealthGod;
    private boolean isBaidaAsFlowerCard;
    private boolean isDabaipiAsFlowerCard;
    private boolean isZhongFaBaiWealthGodAsEastWind;

    public int getDiceNum() {
        return diceNum;
    }

    public void setDiceNum(int diceNum) {
        this.diceNum = diceNum;
    }

    public int getUseDiceTimes() {
        return useDiceTimes;
    }

    public void setUseDiceTimes(int useDiceTimes) {
        this.useDiceTimes = useDiceTimes;
    }

    public int getUseDiceMethod() {
        return useDiceMethod;
    }

    public void setUseDiceMethod(int useDiceMethod) {
        this.useDiceMethod = useDiceMethod;
    }

    public int getStartCardMethod() {
        return startCardMethod;
    }

    public void setStartCardMethod(int startCardMethod) {
        this.startCardMethod = startCardMethod;
    }

    public int getStartCardSupplementFlowerMethod() {
        return startCardSupplementFlowerMethod;
    }

    public void setStartCardSupplementFlowerMethod(int startCardSupplementFlowerMethod) {
        this.startCardSupplementFlowerMethod = startCardSupplementFlowerMethod;
    }

    public boolean isOneFiveNineGetCard() {
        return isOneFiveNineGetCard;
    }

    public void setOneFiveNineGetCard(boolean oneFiveNineGetCard) {
        isOneFiveNineGetCard = oneFiveNineGetCard;
    }

    public boolean isEastSouthWestNorthAsColorCard() {
        return isEastSouthWestNorthAsColorCard;
    }

    public void setEastSouthWestNorthAsColorCard(boolean eastSouthWestNorthAsColorCard) {
        isEastSouthWestNorthAsColorCard = eastSouthWestNorthAsColorCard;
    }

    public boolean isZhongFaBaiAsColorCard() {
        return isZhongFaBaiAsColorCard;
    }

    public void setZhongFaBaiAsColorCard(boolean zhongFaBaiAsColorCard) {
        isZhongFaBaiAsColorCard = zhongFaBaiAsColorCard;
    }

    public boolean isAllWindCardAsColorCard() {
        return isAllWindCardAsColorCard;
    }

    public void setAllWindCardAsColorCard(boolean allWindCardAsColorCard) {
        isAllWindCardAsColorCard = allWindCardAsColorCard;
    }

    public boolean isBankerAndLastPlayerChangePosition() {
        return isBankerAndLastPlayerChangePosition;
    }

    public void setBankerAndLastPlayerChangePosition(boolean bankerAndLastPlayerChangePosition) {
        isBankerAndLastPlayerChangePosition = bankerAndLastPlayerChangePosition;
    }

    public boolean isOpenWealthGodMode() {
        return isOpenWealthGodMode;
    }

    public void setOpenWealthGodMode(boolean openWealthGodMode) {
        isOpenWealthGodMode = openWealthGodMode;
    }

    public int getWealthGodStartCardMethod() {
        return wealthGodStartCardMethod;
    }

    public void setWealthGodStartCardMethod(int wealthGodStartCardMethod) {
        this.wealthGodStartCardMethod = wealthGodStartCardMethod;
    }

    public int getWealthGodUseDiceMethod() {
        return wealthGodUseDiceMethod;
    }

    public void setWealthGodUseDiceMethod(int wealthGodUseDiceMethod) {
        this.wealthGodUseDiceMethod = wealthGodUseDiceMethod;
    }

    public int getWealthGodCondition() {
        return wealthGodCondition;
    }

    public void setWealthGodCondition(int wealthGodCondition) {
        this.wealthGodCondition = wealthGodCondition;
    }

    public int getWindCardWealthGodLoopMethod() {
        return windCardWealthGodLoopMethod;
    }

    public void setWindCardWealthGodLoopMethod(int windCardWealthGodLoopMethod) {
        this.windCardWealthGodLoopMethod = windCardWealthGodLoopMethod;
    }

    public int getFixedWealthGod() {
        return fixedWealthGod;
    }

    public void setFixedWealthGod(int fixedWealthGod) {
        this.fixedWealthGod = fixedWealthGod;
    }

    public int getWealthGodLastBlockNum() {
        return wealthGodLastBlockNum;
    }

    public void setWealthGodLastBlockNum(int wealthGodLastBlockNum) {
        this.wealthGodLastBlockNum = wealthGodLastBlockNum;
    }

    public int getWealthGodStartCardPosition() {
        return wealthGodStartCardPosition;
    }

    public void setWealthGodStartCardPosition(int wealthGodStartCardPosition) {
        this.wealthGodStartCardPosition = wealthGodStartCardPosition;
    }

    public int getWealthGodPrecedenceNum() {
        return wealthGodPrecedenceNum;
    }

    public void setWealthGodPrecedenceNum(int wealthGodPrecedenceNum) {
        this.wealthGodPrecedenceNum = wealthGodPrecedenceNum;
    }

    public boolean isZhongAsFixedWealthGod() {
        return isZhongAsFixedWealthGod;
    }

    public void setZhongAsFixedWealthGod(boolean zhongAsFixedWealthGod) {
        isZhongAsFixedWealthGod = zhongAsFixedWealthGod;
    }

    public boolean isColorCardAsFixedWealthGod() {
        return isColorCardAsFixedWealthGod;
    }

    public void setColorCardAsFixedWealthGod(boolean colorCardAsFixedWealthGod) {
        isColorCardAsFixedWealthGod = colorCardAsFixedWealthGod;
    }

    public boolean isYiTiaoAsFixedWealthGod() {
        return isYiTiaoAsFixedWealthGod;
    }

    public void setYiTiaoAsFixedWealthGod(boolean yiTiaoAsFixedWealthGod) {
        isYiTiaoAsFixedWealthGod = yiTiaoAsFixedWealthGod;
    }

    public boolean isBaiBanAsFixedWealthGod() {
        return isBaiBanAsFixedWealthGod;
    }

    public void setBaiBanAsFixedWealthGod(boolean baiBanAsFixedWealthGod) {
        isBaiBanAsFixedWealthGod = baiBanAsFixedWealthGod;
    }

    public boolean isYaojiufeng() {
        return isYaojiufeng;
    }

    public void setYaojiufeng(boolean yaojiufeng) {
        isYaojiufeng = yaojiufeng;
    }

    public boolean isYaojiufengSuanGan() {
        return isYaojiufengSuanGan;
    }

    public void setYaojiufengSuanGan(boolean yaojiufengSuanGan) {
        isYaojiufengSuanGan = yaojiufengSuanGan;
    }

    public boolean isBaibanAsWealthGodSubstitute() {
        return isBaibanAsWealthGodSubstitute;
    }

    public void setBaibanAsWealthGodSubstitute(boolean baibanAsWealthGodSubstitute) {
        isBaibanAsWealthGodSubstitute = baibanAsWealthGodSubstitute;
    }

    public boolean isFanpaifengpaiAsWealthGod() {
        return isFanpaifengpaiAsWealthGod;
    }

    public void setFanpaifengpaiAsWealthGod(boolean fanpaifengpaiAsWealthGod) {
        isFanpaifengpaiAsWealthGod = fanpaifengpaiAsWealthGod;
    }

    public boolean is13579() {
        return is13579;
    }

    public void setIs13579(boolean is13579) {
        this.is13579 = is13579;
    }

    public boolean isEastSouthWestNorthOrZhongFaBaiBusuandacha() {
        return isEastSouthWestNorthOrZhongFaBaiBusuandacha;
    }

    public void setEastSouthWestNorthOrZhongFaBaiBusuandacha(boolean eastSouthWestNorthOrZhongFaBaiBusuandacha) {
        isEastSouthWestNorthOrZhongFaBaiBusuandacha = eastSouthWestNorthOrZhongFaBaiBusuandacha;
    }

    public boolean isWealthGodIsEastWind() {
        return isWealthGodIsEastWind;
    }

    public void setWealthGodIsEastWind(boolean wealthGodIsEastWind) {
        isWealthGodIsEastWind = wealthGodIsEastWind;
    }

    public void setRedFlowerAsFixedWealthGod(boolean redFlowerAsFixedWealthGod) {
        isRedFlowerAsFixedWealthGod = redFlowerAsFixedWealthGod;
    }

    public void setBlackFlowerAsFixedWealthGod(boolean blackFlowerAsFixedWealthGod) {
        isBlackFlowerAsFixedWealthGod = blackFlowerAsFixedWealthGod;
    }

    public void setBaidaAsFixedWealthGod(boolean baidaAsFixedWealthGod) {
        isBaidaAsFixedWealthGod = baidaAsFixedWealthGod;
    }

    public void setDabaipiAsFixedWealthGod(boolean dabaipiAsFixedWealthGod) {
        isDabaipiAsFixedWealthGod = dabaipiAsFixedWealthGod;
    }

    public void setBaidaAsFlowerCard(boolean baidaAsFlowerCard) {
        isBaidaAsFlowerCard = baidaAsFlowerCard;
    }

    public void setDabaipiAsFlowerCard(boolean dabaipiAsFlowerCard) {
        isDabaipiAsFlowerCard = dabaipiAsFlowerCard;
    }

    public boolean isRedFlowerAsFixedWealthGod() {
        return isRedFlowerAsFixedWealthGod;
    }

    public boolean isBlackFlowerAsFixedWealthGod() {
        return isBlackFlowerAsFixedWealthGod;
    }

    public boolean isBaidaAsFixedWealthGod() {
        return isBaidaAsFixedWealthGod;
    }

    public boolean isDabaipiAsFixedWealthGod() {
        return isDabaipiAsFixedWealthGod;
    }

    public boolean isBaidaAsFlowerCard() {
        return isBaidaAsFlowerCard;
    }

    public boolean isDabaipiAsFlowerCard() {
        return isDabaipiAsFlowerCard;
    }

    public boolean isZhongFaBaiWealthGodAsEastWind() {
        return isZhongFaBaiWealthGodAsEastWind;
    }

    public void setZhongFaBaiWealthGodAsEastWind(boolean zhongFaBaiWealthGodAsEastWind) {
        isZhongFaBaiWealthGodAsEastWind = zhongFaBaiWealthGodAsEastWind;
    }

    public int getStartCardReserve1() {
        return startCardReserve1;
    }

    public void setStartCardReserve1(int startCardReserve1) {
        this.startCardReserve1 = startCardReserve1;
    }

    public int getStartCardReserve2() {
        return startCardReserve2;
    }

    public void setStartCardReserve2(int startCardReserve2) {
        this.startCardReserve2 = startCardReserve2;
    }

    public int getWealthGodReserve() {
        return wealthGodReserve;
    }

    public void setWealthGodReserve(int wealthGodReserve) {
        this.wealthGodReserve = wealthGodReserve;
    }
}
