package com.vdom.comms;

import java.io.Serializable;
import java.util.ArrayList;

import com.vdom.api.Card;
import com.vdom.core.CardType;
import com.vdom.core.Cards;
import com.vdom.core.MoveContext;
import com.vdom.core.Player;
import com.vdom.core.PlayerSupplyToken;
import com.vdom.core.Util;

/**
 * Gives information about cards that are selected by the player from the table (piles, hand, play)
 *
 * This class gives information about the constrains set on the selection of cards, e.g. what type, cost, from which place
 *
 */
public class SelectCardOptions implements Serializable {
    private static final long serialVersionUID = -1473106875075390348L;

    public enum ActionType {
        REVEAL, DISCARD, DISCARDFORCOIN, DISCARDFORCARD, GAIN, TRASH, NAMECARD, OPPONENTDISCARD, SETASIDE,
        UNDER
    }

    public enum PickType {
        SELECT(""),
        SELECT_WITH_ALL(" T"),
        SELECT_IN_ORDER(" T"),
        PLAY (""),
        PLAY_IN_ORDER(" 1"),
        BUY (""),
        DISCARD (" D"),
        KEEP (" K"),
        GIVE (" P"),
        TRASH (" X"),
        UPGRADE ("X"),
        MINT ("M"),
        SWINDLE (""),
        ATTACK ("");

        private final String indicator;
        PickType(String indicator) {
            this.indicator= indicator;
        }
        public String indicator() { return indicator; }
    }

    public PickType pickType = PickType.SELECT;
    public ActionType actionType = null;
    public Card cardResponsible = null;

    public int defaultCardSelected = -1;
    public boolean fromHand = false;
    public boolean fromPlayed = false;
    public int count = Integer.MAX_VALUE;
    public int minCount = 0;
    public boolean exactCount = false;
    public boolean ordered = false;
    public boolean isNonRats = false;

    public boolean fromTable = false;
    public boolean isBuyPhase = false;
    public boolean isActionPhase = false;
    public boolean isTreasurePhase = false;
    public boolean isAttackPhase = false;
    public boolean allowEmpty = false;
    public int minCost = Integer.MIN_VALUE;
    public int maxCost = Integer.MAX_VALUE;
    public int minDebtCost = Integer.MIN_VALUE;
    public int maxDebtCost = Integer.MAX_VALUE;
    public int copperCountInPlay = 0;
    public int minPotionCost = Integer.MIN_VALUE;
    public int maxPotionCost = Integer.MAX_VALUE;
    public boolean lessThanMax = false;
    public boolean fromPrizes = false;
    public Card except = null;

    public boolean isAction = false;
    public boolean isReaction = false;
    public boolean isTreasure = false;
    public boolean isNonTreasure = false;
    public boolean isVictory = false;
    public boolean isNonVictory = false;
    public boolean isAttack = false;
    public boolean isCastle = false;
    public boolean isNonShelter = false;
    public boolean isSupplyCard = false;
    public boolean different = false;
    public boolean noTokens = false;
    public boolean passable = false;


    public boolean fromBlackMarket = false;
    public Cards.Kind upgradeCard = null;
    public int actionCost = 0;
    public boolean isWound = false;
    public boolean isNonCrown = false;
    public boolean isNonRabble = false;
    public boolean isNonHero = false;
    public boolean isNonLocation = false;
    public boolean isNonSiege = false;
    public boolean isCorpse = false;
    public boolean isManoeuvre = false;
    public boolean isSelect = false;
    public boolean isTechnique = false;
    public boolean isSpell = false;

    public boolean applyOptionsToPile = false;
    
    public PlayerSupplyToken token = null;
    
    public String header = null;
    public ArrayList<Integer> allowedCards = new ArrayList<Integer>();
    public ArrayList<Boolean> allowedEnemy = null;

    //public SelectCardOptions setType(SelectType s) {selectType = s; return this;}
    public SelectCardOptions setHeader(String s) {header = s; return this;}
    public SelectCardOptions setDifferent() {different = true; return this;}
    public SelectCardOptions setPassable() {passable = true; return this;}
    public SelectCardOptions setPickType(PickType pickType) {this.pickType = pickType;return this;}
    public SelectCardOptions setActionType(ActionType actionType) {this.actionType = actionType;return this;}
    public SelectCardOptions setCardResponsible(Card card) {this.cardResponsible = card;return this;}
    public SelectCardOptions fromHand() {fromHand = true; return this;}
    public SelectCardOptions fromPlayed() {fromPlayed = true; return this;}
    public SelectCardOptions defaultCardSelected(int c) {defaultCardSelected = c; return this;}
    public SelectCardOptions isNonShelter() {isNonShelter = true; return this;}
    public SelectCardOptions isNonRats() {isNonRats = true; return this;}
    public SelectCardOptions ordered() {ordered = true; this.pickType = PickType.SELECT_IN_ORDER; return this;}
    public SelectCardOptions setCount(int c) {count = c; return this;}
    public SelectCardOptions setMinCount(int c) {minCount = c; return this;}
    public SelectCardOptions exactCount() {exactCount = true; return this;}

    public SelectCardOptions isAttackFoe() {
        fromBlackMarket = true; isAttackPhase = true; this.pickType = PickType.ATTACK;
        allowedEnemy = new ArrayList<>();
        return this;}
    public SelectCardOptions fromTable() {fromTable = true;isNonShelter=true;count=1;exactCount=true; return this;}
    public SelectCardOptions isBuy() {isBuyPhase= true; this.pickType = PickType.BUY; return this;}
    public SelectCardOptions isActionPhase() {isActionPhase=true; return this;}
    public SelectCardOptions isTreasurePhase() {isTreasurePhase=true; return this;}
    public SelectCardOptions allowEmpty() {allowEmpty = true; return this;}
    public SelectCardOptions fromPrizes() {fromPrizes = true; return this;}
    public SelectCardOptions minCost(int c) {minCost = c; return this;}
    public SelectCardOptions maxCost(int c) {maxCost = c; return this;}
    public SelectCardOptions exactCost(int coin, int debt, int potion) {
    	minCost = coin; maxCost = coin;
    	minDebtCost = debt; maxDebtCost = debt;
    	minPotionCost = potion; maxPotionCost = potion;
    	return this;
    }
    public SelectCardOptions maxPotionCost(int c) {maxPotionCost = c; return this;}
    public SelectCardOptions lessThanMax() {lessThanMax = true; return this;}
    public SelectCardOptions maxDebtCost(int c) {maxDebtCost = c; return this;}
    public SelectCardOptions copperCountInPlay(int c) {copperCountInPlay = c; return this; }
    public SelectCardOptions not(Card c) {except = c; return this; }

    public SelectCardOptions isAction() {isAction = true; return this;}
    public SelectCardOptions isReaction() {isReaction = true; return this;}
    public SelectCardOptions isTreasure() {isTreasure = true; return this;}
    public SelectCardOptions isNonTreasure() {isNonTreasure = true; return this;}
    public SelectCardOptions isVictory() {isVictory = true; return this;}
    public SelectCardOptions isNonVictory() {isNonVictory = true; return this;}
    public SelectCardOptions isAttack() {isAttack = true; return this;}
    public SelectCardOptions isSupplyCard() {isSupplyCard = true; return this;}
    public SelectCardOptions noTokens() {noTokens = true; return this;}
    public SelectCardOptions isCastle() {isCastle = true; return this;}

    public SelectCardOptions isWound() {isWound = true; return this;}
    public SelectCardOptions isNonCrown() {isNonCrown = true; return this;}
    public SelectCardOptions isNonRabble() {isNonRabble = true; return this;}
    public SelectCardOptions isNonHero() {isNonHero = true;return this;}
    public SelectCardOptions isNonLocation() {isNonLocation = true;return this;}
    public SelectCardOptions isNonSiege() {isNonSiege = true;return this;}
    public SelectCardOptions isCorpse() {isCorpse = true; return this;}
    public SelectCardOptions isManoeuvre() {isManoeuvre = true; return this;}
    public SelectCardOptions isSelect() {isSelect = true; return this;}
    public SelectCardOptions isTechnique() {isTechnique = true; return this;}
    public SelectCardOptions isSpell() {isSpell = true; return this;}


    public SelectCardOptions applyOptionsToPile() {applyOptionsToPile = true; return this;}
    
    public SelectCardOptions token(PlayerSupplyToken c) {token = c; return this;}

    public SelectCardOptions allowedCards(int[] is) {
        for (int i : is)
            addValidCard(i);
        return this;
    }

    public SelectCardOptions fromBlackMarket() {
        fromBlackMarket = true; return this;}

    public PickType getPickType() {
        return pickType;
    }

    public boolean cardInList(int card) {
        if (allowedCards.size() == 0)
            return true;
        return allowedCards.contains(new Integer(card));
    }

    // Return the number of cards that have matched the filter
    public int getAllowedCardCount() {
        return allowedCards.size();
    }

    public void addValidCard(int card) {
        allowedCards.add(new Integer(card));

    }

    public boolean checkValid(MyCard c) {
        return checkValid(c, 0);
    }

    public boolean checkValid(MyCard c, int cost) {
        if (!cardInList(c.id) || cost > maxCost || cost < minCost) return false;

        return true;
    }

    public boolean checkValid(Card c, boolean cardIsVictory, MoveContext context) {
        return checkValid(c, 0, cardIsVictory, context);
    }

    public boolean checkValid(Card c, int cost, boolean cardIsVictory, MoveContext context) {
    	
    	if (c.is(CardType.Landmark, null)) return false;
    	
    	Player p = context != null ? context.player : null;
    	p = fromTable ? null : p;
    	int debtCost = c.getDebtCost(context); //TODO - incorporate this
    	int potionCost = c.costPotion() ? 1 : 0;

    	if (except != null && except.equals(c)) return false;
        if ((maxCost >= 0) && (cost > maxCost)) return false;
        if ((minCost >= 0) && (cost < minCost)) return false;
        if ((maxDebtCost >= 0) && (debtCost > maxDebtCost)) return false;
        if ((minDebtCost >= 0) && (debtCost < minDebtCost)) return false;
        if ((maxPotionCost >= 0) && (potionCost > maxPotionCost)) return false;
        if ((minPotionCost >= 0) && (potionCost < minPotionCost)) return false;
        if ((minDebtCost >= 0) && (debtCost < minDebtCost)) return false;
        if (lessThanMax && cost >= maxCost && debtCost >= maxDebtCost && potionCost >= maxPotionCost) return false;
        if (isReaction && !(c.is(CardType.InHandReaction, p))) return false;
        if (isTreasure && !(c.is(CardType.Treasure, p))) return false;
        if (isNonTreasure && (c.is(CardType.Treasure, p))) return false;
        if (isVictory && !cardIsVictory) return false;
        if (isNonVictory && cardIsVictory) return false;
        if (fromPrizes && !c.is(CardType.Prize, null)) return false;
        if (fromTable && !fromPrizes && c.is(CardType.Prize, null)) return false;
        if (c.equals(Cards.grandMarket) && copperCountInPlay > 0) return false;
        if (isNonShelter && c.is(CardType.Shelter, p)) return false;
        if (isAttack && !c.is(CardType.Attack, p)) return false;
        if (isAction && !c.is(CardType.Action, p)) return false;
        if (!isBuyPhase && c.is(CardType.Event, null)) return false;
        if (isCastle && !c.is(CardType.Castle, null)) return false;
        if (applyOptionsToPile && !c.isPlaceholderCard()) return false;
        if (!applyOptionsToPile && c.isPlaceholderCard()) return false;
        
        if (isBuyPhase && !Cards.isSupplyCard(c) && !c.is(CardType.Event, null)) return false;
        if (isSupplyCard && !Cards.isSupplyCard(c)) return false;

        if (isWound && !(c.is(CardType.Wound, p))) return false;
        if (isWound && c.is(CardType.Wound, p)) return true;
        if (isNonCrown && c.is(CardType.Crown,p)) return false;
        if (isNonRabble && c.is("rabble")) return false;
        if (isNonLocation && c.is(CardType.Location,p)) return false;
        if (isNonHero && c.is(CardType.Hero,p)) return false;
        if (isNonSiege && c.is(CardType.Siege, p)) return false;
        if (isCorpse && !c.is("corpse")) return false;
        if (isManoeuvre && !c.is(CardType.InHandManoeuvre)) return false;
        if (isTechnique && !c.is(CardType.Technique)) return false;
        if (isSpell && !c.is(CardType.Spell)) return false;

        if (p != null && (isManoeuvre || isAction)) {
            if (c.getKind() == Cards.Kind.OldWound && p.getCardCount(CardType.Wound, p.getHand().toArrayList()) <= 1)
                return false;
            if (c.getKind() == Cards.Kind.GlancingWound && context.actions<2)
                return false;
        }
        if (context != null && (context.phase == MoveContext.TurnPhase.Attack ||
                   context.phase == MoveContext.TurnPhase.Action)
                && c.is(CardType.Enemy) &&
                (!properAttackType(context, c) && !isSelect)) return false;

        return true;
    }
    public boolean isDifferent() {
    	return different;
	}
    public boolean isPassable() {
        return passable;
    }

    private boolean properAttackType(MoveContext context, Card c) {
        ArrayList<Card> enemyLine = context.game.blackMarketPile;

        if (c.is(CardType.Range)) {
            if (context.isRange()) return true;
            try {
                if (!enemyLine.get(Util.indexOfCardId(c.getId(), enemyLine) - 1).is(CardType.Range))
                    return false;
            }
            catch (IndexOutOfBoundsException e){
            }
            try {
                if (!enemyLine.get(Util.indexOfCardId(c.getId(), enemyLine) + 1).is(CardType.Range))
                    return false;
            }
            catch (IndexOutOfBoundsException e){
            }
            //neighbours either don't exist or are ranged
            return true;
        }
        else { //must be melee
            if (context.isMelee())
                return true;
        }
        return false;
    }

    public String potionString() {
        String potionString = "";
        if (maxPotionCost == 1) {
            potionString = "p";
        } else if (maxPotionCost > 1) {
            potionString = "p" + maxPotionCost;
        }
        return potionString;
    }
    
    public String debtString() {
    	return (maxDebtCost >= 1 && maxDebtCost != Integer.MAX_VALUE) ? "d" + maxDebtCost : "";
    }
}
