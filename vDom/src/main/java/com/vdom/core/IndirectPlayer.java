package com.vdom.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.comms.SelectCardOptions;
import com.vdom.comms.SelectCardOptions.ActionType;
import com.vdom.comms.SelectCardOptions.PickType;

/**
 * Class that you can use to play remotely.
 */
public abstract class IndirectPlayer extends QuickPlayPlayer {
    public abstract Card intToCard(int i);
    public abstract int cardToInt(Card card);
    public abstract int[] cardArrToIntArr(Card[] cards);
    public Card nameToCard(String o, Card[] cards) {
        for (Card c : cards)
            if (c.getName().equals(o))
                return c;
        return null;
    }

    abstract protected boolean selectBoolean(MoveContext context, Card cardResponsible, Object[] extras);
    public boolean selectBoolean(MoveContext context, Card cardResponsible) {
        return selectBoolean(context, cardResponsible, null);
    }

    public static final String OPTION_DEFEND = "DEFEND";
    public static final String OPTION_ACTIVATION = "ACTIVATION";
    public static final String OPTION_DRAWN = "DRAWN";
    public static final String OPTION_MANOEUVRE = "MANOEUVRE";

    public static final String OPTION_REACTION = "REACTION";
    public static final String OPTION_PUTBACK = "PUTBACK";
    public static final String OPTION_SPEND_GUILD_COINS = "GUILDCOINS";
    public static final String OPTION_OVERPAY = "OVERPAY";
    public static final String OPTION_OVERPAY_POTION = "OVERPAYP";
    public static final String OPTION_PAY_DEBT = "PAYDEBT";
    public static final String OPTION_CALL_WHEN_GAIN = "CALLWHENGAIN";
    public static final String OPTION_CALL_RESOLVE_ACTION = "CALLAFTERACTION";
    public static final String OPTION_START_TURN_EFFECT = "STARTTURN";
    public static final String OPTION_STASH = "PLACESTASH";
    public static final String OPTION_STASH_POSITION = "PLACESTASHPOS";

    abstract protected int selectOption(MoveContext context, Card card, Object[] options, int[] amounts);
    abstract protected int[] orderCards(MoveContext context, int[] cards);

    abstract protected Card[] pickCards(MoveContext context, SelectCardOptions sco, int count, boolean exact);
    private Card pickACard(MoveContext context, SelectCardOptions sco) {
        Card[] cs = pickCards(context, sco, 1, true);
        return (cs == null ? null : cs[0]);
    }

    abstract protected int[] pickSpecificCards(MoveContext context, SelectCardOptions sco, int count, boolean exact, GameEvent.EventType eType);
    private Card pickSpecificCard(MoveContext context, SelectCardOptions sco, ArrayList<Card> list) {
        Card cs = list.get(pickSpecificCards(context, sco,1, true, GameEvent.EventType.AttackingFoe)[0]);
        return (cs == null ? null : cs);
    }

    @Override
    public boolean bolsterAttack(MoveContext context) {
        return false;
    }

    @Override
    public boolean isAi() {
        return false;
    }

    @Override
    public Card getCardFromHand(MoveContext context, SelectCardOptions sco) {
        Card[] cs = getFromHand(context, sco.setCount(1).exactCount());
        return (cs == null ? null : cs[0]);
    }

    //@Override
    public Card[] getFromHand(MoveContext context, SelectCardOptions sco) {
    	return getFromHandOrPlayed(true, context, sco);    
    }
    
    private Card[] getFromPlayed(MoveContext context, SelectCardOptions sco) {
    	return getFromHandOrPlayed(false, context, sco);    
    }
    
    private Card[] getFromHandOrPlayed(boolean fromTheHand, MoveContext context, SelectCardOptions sco) {
        CardList localHand = context.player.getHand();
    	if (fromTheHand) {
            sco = sco.fromHand();
    	} else {
    		localHand = new CardList(context.getPlayer(), context.getPlayer().getPlayerName());
    		for (Card card : context.player.playedCards) {
    			localHand.add(card);
    		}
    		for (Card card : context.player.nextTurnCards) {
    			localHand.add(card);
    		}
    		sco = sco.fromPlayed();
    	}
    	
    	int totalKindsOfCards = 0;
    	Card[] differentCards = new Card[0];
    	if (sco.isDifferent()) {
    		Set<String> cardNames = new HashSet<String>();
    		Set<Card> cards = new HashSet<Card>();
    		for (Card c : localHand) {
    			cardNames.add(c.getName());
    			cards.add(c);
    		}
    		totalKindsOfCards = cardNames.size();
    		differentCards = cards.toArray(differentCards);
    	}
    	
        if (localHand.size() == 0) {
            return null;
        } else if (localHand.size() == 1 && sco.minCount == 1) {
        	return new Card[]{localHand.get(0)};
        } else if (sco.isDifferent() && totalKindsOfCards <= sco.minCount) {
        	return differentCards;
        } else if (sco.count == Integer.MAX_VALUE) {
            sco.setCount(localHand.size());
        } else if (sco.count < 0) {
            sco.setCount(localHand.size() + sco.count).exactCount();
        } else if (localHand.size() < sco.count && sco.exactCount) {
            sco.setCount(localHand.size());
        }

        ArrayList<Card> handList = new ArrayList<Card>();

        for (Card card : localHand) {
            if (sco.checkValid(card, card.getCost(context), card.is(CardType.Victory), context)) {
                    //||                    (card.getKind() == Cards.Kind.GlancingWound && context.actions >= 2)) {
                handList.add(card);
                sco.addValidCard(cardToInt(card));
            }
        }

        if (sco.allowedCards.size() == 0)
            return null;
        /*Select no Card by default if TRASH and not forced*/
        else if (      sco.allowedCards.size() == 1
                    && (sco.actionType != ActionType.TRASH || !sco.passable)
                 || (   (   sco.isAction
                         || sco.pickType == PickType.MINT               //Mint (passable)
                         || (   (   sco.actionType == ActionType.TRASH
                                 || sco.actionType == ActionType.REVEAL //Ambassador
                                 || sco.pickType == PickType.UPGRADE    //Mine, Remodel
                                 || sco.pickType == PickType.GIVE)      //Masquerade
                             && !sco.passable
                            )
                        )
                     && Collections.frequency(sco.allowedCards, sco.allowedCards.get(0)) ==
                                                         sco.allowedCards.size() //all the same
                    )
                )
            sco.defaultCardSelected = sco.allowedCards.get(0);

        Card[] tempCards = pickCards(context, sco, sco.count, sco.exactCount);
        if (tempCards == null)
            return null;

        // Hack to notify that "All" was selected
        if(tempCards.length == 0) {
            return tempCards;
        }

        for (int i=0; i<tempCards.length; i++)
            for (Card c : handList)
                if (c.equals(tempCards[i])) {
                    tempCards[i] = c;
                    handList.remove(c);
                    break;
                }

        return tempCards;
    }

    private Card getFromBlackMarket(MoveContext context, SelectCardOptions sco) {
        Card[] cards = context.game.blackMarketPile.toArray(new Card[context.game.blackMarketPile.size()]);
        //int currentMight = context.getMight() + context.getMightModifier();
        ArrayList<Card> enemyLine = context.game.blackMarketPile;
        for (Card card :cards) {
            try {
                //if (card.getCost(context) <= currentMight &&
                if (        !card.isInvincible(enemyLine, context ) &&
                        sco.checkValid(card, card.getCost(context), false, context)) {
                    sco.addValidCard(cardToInt(card));
                    sco.allowedEnemy.add(true);
                }
                else {
                    sco.allowedEnemy.add(false);
                }
            }
            catch (IndexOutOfBoundsException e) {
            }
        }
        Card pickedCard = pickSpecificCard(context, sco,context.game.blackMarketPile);

        return (pickedCard);
    }


    private Card getFromTable(MoveContext context, SelectCardOptions sco) {
        return getFromTable(context, sco, false);
    }

    private Card getFromTable(MoveContext context, SelectCardOptions sco, boolean getPlaceholder) {
        sco.fromTable();
        Card[] cards = context.getCardsInGame(sco.applyOptionsToPile ? GetCardsInGameOptions.Placeholders : GetCardsInGameOptions.TopOfPiles);

        for (Card card : cards) {
        	boolean hasTokens = context.game.getPlayerSupplyTokens(card, context.getPlayer()).size() > 0;
            if ((sco.allowEmpty || !context.game.isPileEmpty(card))) {
                if (   sco.checkValid(card, card.getCost(context), card.is(CardType.Victory), null)
                	&& (!(sco.noTokens && hasTokens))
                    && (   (!context.cantBuy.contains(card) && (context.getPlayer().getDebtTokenCount() == 0 &&(context.canBuyCards || card.is(CardType.Event, null))))
                        || !sco.pickType.equals(PickType.BUY))
                    && !(   !Cards.isSupplyCard(card)
                         && sco.actionType != null
                         && sco.actionType.equals(ActionType.GAIN) )
                        && ((card.upgradeCard()==null) || context.getPlayer().inHand(card.upgradeCard())) )
                {
                    //TODO SPLITPILES When the variablecardpile syncing to the UI is refactored this should not be necessary anymore.
                    //Swap cards for the placeholdercards because on UI side piles always have the id of the placeholder, not the actual top card
                    if (!card.isPlaceholderCard()) {
                        CardPile pile = game.getPile(card);
                        if (pile.topCard().equals(card)) {
                            card = game.getPile(card).placeholderCard();
                        }
                    }
                    sco.addValidCard(cardToInt(card));
                    /*For Swindler: Default=Curse if Cost=0 */
                    if(card.equals(Cards.curse))
                    {
                       sco.defaultCardSelected = cardToInt(card);
                    }
                }
            }
        }

        if (sco.getAllowedCardCount() == 0) {
            // No cards fit the filter, so return early
            return null;
        }
        else if (sco.getAllowedCardCount() == 1 && !sco.isPassable()) {
            // Only one card available and player can't pass...go ahead and return
            return intToCard(sco.allowedCards.get(0));
        }

        //TODO SPLITPILES When the variablecardpile syncing to the UI is refactored this should not be necessary anymore.
        //Swap the placeholder for the actual topCard
        Card pickedCard = pickACard(context, sco);
        if (pickedCard == null) {
            return pickedCard;
        }
        if (pickedCard.isPlaceholderCard() && !getPlaceholder) {
            pickedCard = game.getPile(pickedCard).topCard();
        } else if (!pickedCard.isPlaceholderCard() && getPlaceholder) {
            pickedCard = game.getPile(pickedCard).placeholderCard();
        }
        return pickedCard;
    }

    public int selectInt(MoveContext context, Card responsible, int maxInt) {
        return selectInt(context, responsible, maxInt, null);
    }

    public int selectInt(MoveContext context, Card responsible, int maxInt, Object extra) {
        Integer[] int_options = new Integer[maxInt + 1];
        for (int i=0; i<=maxInt; i++)
            int_options[i] = i;
        Object[] options = int_options;
        if (extra != null) {
            options = new Object[1 + int_options.length];
            options[0] = extra;
            for (int i = 0; i < int_options.length; i++) {
                options[i + 1] = int_options[i];
            }
        }
        int choice = selectOption(context, responsible, options, null);
        if (choice >= 0 && choice < int_options.length) {
            return int_options[choice];
        } else {
            return int_options[0];
        }

    }

    @Override
    public Card[] topOfDeck_orderCards(MoveContext context, Card[] cards) {
        if (context.isQuickPlay() && shouldAutoPlay_topOfDeck_orderCards(context, cards)) {
            return super.topOfDeck_orderCards(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }

    private Card[] doAction(MoveContext context, boolean singleCard) {
        int actionCount = 0;
        Card actionCard = null;
        for (Card card : context.player.getHand()) {
            if (card.is(CardType.Action, context.player)) {
                actionCount++;
                actionCard = card;
            }
        }
        if (actionCount == 0)
            return null;

        SelectCardOptions sco = new SelectCardOptions().isActionPhase().isAction().setPassable();
        if (singleCard)
            sco.setCount(1).setPickType(PickType.PLAY);
        else
            sco.setCount(actionCount).ordered().setPickType(PickType.PLAY_IN_ORDER);

        Card[] cards = getFromHand(context, sco);

        if (cards == null)
            return null;
        // Hack that tells us that "Play the only one card" was selected
        else if (actionCount == 1 && cards.length == 0) {
            cards = new Card[1];
            cards[0] = actionCard;
        }
        return cards;
    }

    @Override
    public Card doAction(MoveContext context) {
        Card[] cards = doAction(context, true);
        return (cards == null ? null : cards[0]); 
    }

    @Override
    public Card[] actionCardsToPlayInOrder(MoveContext context) {
        return doAction(context, false);
    }

    @Override
    public int[] doSelectFoe(MoveContext context, SelectCardOptions sco, int amount, GameEvent.EventType eType) {
        //SelectCardOptions sco = new SelectCardOptions().isNonCrown().isNonRabble();

        for (Card c : context.game.blackMarketPile)
        if (sco.checkValid(c,false,context))
                sco.addValidCard(cardToInt(c));
        if (sco.allowedCards.size()==0)
            return null;
        if (sco.allowedCards.size()<amount){
            amount = sco.allowedCards.size();
        }
        return pickSpecificCards(context,sco,amount,true, eType);
        /*
        int[] selectedIndexes = pickSpecificCards(context,sco,1,true);
        ArrayList<Card> returnCards = new ArrayList<>();
            returnCards.add(context.game.blackMarketPile.get(i));
        }
        return returnCards.toArray(new Card[0]);
        */
    }

    @Override
    public boolean preventActivation(MoveContext context, Card Enemy) {
        return false;
    }

    @Override
    public Card doAttackFoe(MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().isAttackFoe()
                .maxCost(context.getMight() + context.getMightModifier())
                .setPassable().setCount(1);
        if (context.game.blackMarketPile.contains(Cards.sang)) {
            sco = sco.isNonSiege();
        }
        return getFromBlackMarket(context, sco);
    }

    @Override
    public Card doBuy(MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().isBuy()
                .maxCost(context.getCoinAvailableForBuy())
                .copperCountInPlay(context.countCardsInPlay(Cards.copper))
                .maxPotionCost(context.getPotions())
                .setPassable()
                .setPickType(PickType.BUY);
        if (context.buys==0) {
            if (context.techniqueBuys>0) {
                sco = sco.isTechnique();
            }
            if (context.spellBuys > 0) {
                sco = sco.isSpell();
            }
        }
        if (context.game.blackMarketPile.contains(Cards.kyung)) {
            sco = sco.isNonHero();
        }
        if (context.game.blackMarketPile.contains(Cards.corpseVine)) {
            sco = sco.isNonLocation();
        }
        return getFromTable(context, sco);
    }

    @Override
    public ArrayList<Card> treasureCardsToPlayInOrder(MoveContext context, int maxCards, Card responsible) {
    	// storyteller sets maxCards != -1
        if(context.isQuickPlay()) {
            return super.treasureCardsToPlayInOrder(context, maxCards, responsible);
        }

        int treasureCount = 0;
        Player player = context.player;
        for (Card card : player.getHand()) {
            if (card.is(CardType.Treasure, player)) {
                treasureCount++;
            }
        }
        if (maxCards > -1 && treasureCount > maxCards) {
        	treasureCount = maxCards;
        }

        SelectCardOptions sco = new SelectCardOptions().isTreasure().setPassable().isTreasurePhase()
                .setCount(treasureCount).ordered().setPickType(PickType.SELECT_WITH_ALL)
                .setCardResponsible(responsible);
        Card[] cards = getFromHand(context, sco);
        if (cards == null) {
            return null;
        }

        // Hack that tells us that "All" was selected
        if(cards.length == 0) {
        	if (maxCards != -1) { // storyteller
                ArrayList<Card> treasures = new ArrayList<Card>();
                for (Card c : context.getPlayer().getHand()) {
                    if(c.is(CardType.Treasure, context.getPlayer())) {
                    	treasures.add(c);
                    }
                }
                return treasures;
        	} else
                return super.treasureCardsToPlayInOrder(context, maxCards, responsible);
        }

        ArrayList<Card> treasures = new ArrayList<Card>();
        for (int i = 0; i < cards.length; i++) {
            treasures.add(cards[i]);
        }
        return treasures;
    }

    // ////////////////////////////////////////////
    // Card interactions - cards from the base game
    // ////////////////////////////////////////////
    @Override
    public Card[] selectFromHand(MoveContext context, Card responsible, int amount, boolean exact, boolean passable, ActionType aType, PickType pickType) {
        SelectCardOptions sco = new SelectCardOptions().setPickType(pickType).setActionType(aType)
                .setCardResponsible(responsible).setCount(amount);
        if (exact)
            sco.exactCount();
        if (passable)
            sco.setPassable();
        return getFromHand(context, sco);
    }

    @Override
    public Card workshop_cardToObtain(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_workshop_cardToObtain(context)) {
            return super.workshop_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.workshop).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public Card feast_cardToObtain(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_feast_cardToObtain(context)) {
            return super.feast_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(5).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.feast).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public Card remodel_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_remodel_cardToTrash(context)) {
            return super.remodel_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.UPGRADE)
                .setCardResponsible(Cards.remodel).setActionType(ActionType.TRASH);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card remodel_cardToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_remodel_cardToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.remodel_cardToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(maxDebtCost).maxPotionCost(potion ? 1 : 0)
                .setCardResponsible(Cards.remodel).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public Card mine_treasureToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_mine_treasureToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.mine_treasureToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure().maxCost(maxCost).maxDebtCost(maxDebtCost)
                .maxPotionCost(potion ? 1 : 0).setActionType(ActionType.GAIN).setCardResponsible(Cards.mine);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] militia_attack_cardsToKeep(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_militia_attack_cardsToKeep(context)) {
            return super.militia_attack_cardsToKeep(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.KEEP).setCardResponsible(Cards.militia);
        return getFromHand(context, sco);
    }

    @Override
    public boolean chancellor_shouldDiscardDeck(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_chancellor_shouldDiscardDeck(context)) {
            return super.chancellor_shouldDiscardDeck(context);
        }
        return selectBoolean(context, Cards.chancellor);
    }

    @Override
    public Card mine_treasureFromHandToUpgrade(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_mine_treasureFromHandToUpgrade(context)) {
            return super.mine_treasureFromHandToUpgrade(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure().setPickType(PickType.UPGRADE)
                .setCardResponsible(Cards.mine);
        if (!Game.errataMineForced) {
        	sco.setPassable();
        }
        return getCardFromHand(context, sco);
    }
    
    @Override
    public boolean moneylender_shouldTrashCopper(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_moneylender_shouldTrashCopper(context)) {
            return super.moneylender_shouldTrashCopper(context);
        }
    	return selectBoolean(context, Cards.moneyLender);
    }

    @Override
    public Card[] chapel_cardsToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_chapel_cardsToTrash(context)) {
            return super.chapel_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(4)
                .setPassable().setPickType(PickType.TRASH)
                .setCardResponsible(Cards.chapel).setActionType(ActionType.TRASH);
        return getFromHand(context, sco);
    }


    @Override
    public String getPlayerName(boolean maskName) {
        return null;
    }

    @Override
    public Card[] wound_cardsToTrash(MoveContext context, Card responsible, int count) {
        SelectCardOptions sco = new SelectCardOptions().setPassable().setCount(count)
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH).isWound()
                .setCardResponsible(responsible);
        return getFromHand(context, sco);
    }
    @Override
    public Card[] generic_cardsToTrash(MoveContext context, Card responsible, int count) {
              /*
        if(context.isQuickPlay() && shouldAutoPlay_cellar_cardsToDiscard(context)) {
            return super.cellar_cardsToDiscard(context);
        }
        */
        SelectCardOptions sco = new SelectCardOptions().setPassable().setCount(count).exactCount()
                    .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(responsible);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] cellar_cardsToDiscard(MoveContext context, Card rsponsible) {
        if(context.isQuickPlay() && shouldAutoPlay_cellar_cardsToDiscard(context)) {
            return super.cellar_cardsToDiscard(context, rsponsible);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(rsponsible);
        return getFromHand(context, sco);
    }

    @Override
    public boolean library_shouldKeepAction(MoveContext context, Card action) {
        if(context.isQuickPlay() && shouldAutoPlay_library_shouldKeepAction(context, action)) {
            return super.library_shouldKeepAction(context, action);
        }
        Object[] extras = new Object[2];
        extras[0] = Cards.library;
        extras[1] = action;
        return selectBoolean(context, Cards.library, extras);
    }

    @Override
    public boolean reveal_shouldDiscard(MoveContext context, Player targetPlayer, Card card, Card controlCard) {
        if(context.isQuickPlay() && shouldAutoPlay_spy_shouldDiscard(context, targetPlayer, card)) {
            return super.reveal_shouldDiscard(context, targetPlayer, card, controlCard);
        }
        Object[] extras = new Object[3];
        extras[0] = targetPlayer.getPlayerName();
        extras[1] = controlCard;
        extras[2] = card;
        return selectBoolean(context, controlCard, extras);
    }
    
    @Override
    public Card artisan_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_workshop_cardToObtain(context)) {
            return super.artisan_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(5).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.artisan).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card artisan_cardToReplace(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_mandarin_cardToReplace(context)) {
            return super.artisan_cardToReplace(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(Cards.artisan);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card bandit_treasureToTrash(MoveContext context, Card[] treasures) {
    	if(context.isQuickPlay() && shouldAutoPlay_bandit_treasureToTrash(context, treasures)) {
            return super.bandit_treasureToTrash(context, treasures);
        }
        return treasures[selectOption(context, Cards.bandit, treasures, null)];
    }
    
    @Override
    public Card cardToPutBackOnDeck(MoveContext context, Card controlCard) {
    	CardList localDiscard = context.player.getDiscard();
        if (localDiscard.isEmpty())
            return null;
        Set<Card> uniqueCards = new HashSet<Card>(localDiscard.toArrayList());
        List<Card> options = new ArrayList<Card>(uniqueCards);
        Collections.sort(options, new Util.CardCostNameComparator());
        options.add(null);

        return options.get(selectOption(context, controlCard, options.toArray(), null));
    }

    @Override
    public Card cardToPlay(MoveContext context, List<Card> options, Card controlCard, boolean passable, String header) {
        if (options.size() == 0) return null;
        ArrayList<Object> headerOptions = new ArrayList<>();
        if (passable) options.add(null);

        if (header != "") {
            headerOptions.add(header);
        }
        for (Card c : options) {
            headerOptions.add(c);
        }

        return (options.get(selectOption(context, controlCard, headerOptions.toArray(), null)));
    }

    @Override
    public Card[] poacher_cardsToDiscard(MoveContext context, int numToDiscard) {
    	if(context.isQuickPlay() && shouldAutoPlay_poacher_cardsToDiscard(context, numToDiscard)) {
            return super.poacher_cardsToDiscard(context, numToDiscard);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(numToDiscard).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.poacher);
        return getFromHand(context, sco);
    }
    
    @Override
    public SentryOption sentry_chooseOption(MoveContext context, Card card, Card[] cards) {
    	if(context.isQuickPlay() && shouldAutoPlay_sentry_chooseOption(context, card, cards)) {
            return super.sentry_chooseOption(context, card, cards);
        }
    	SentryOption[] sentryOptions = SentryOption.values();
    	Object[] options = new Object[2 + sentryOptions.length];
        options[0] = card;
        options[1] = cards;
        for (int i = 0; i < sentryOptions.length; i++) {
            options[i + 2] = sentryOptions[i];
        }
        return sentryOptions[selectOption(context, Cards.sentry, options, null)];
    }
    
    @Override
    public Card[] sentry_cardOrder(MoveContext context, Card[] cards) {
    	if(context.isQuickPlay() && shouldAutoPlay_sentry_cardOrder(context, cards)) {
            return super.sentry_cardOrder(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }
    
    @Override
    public boolean vassal_shouldPlayCard(MoveContext context, Card card) {
    	if(context.isQuickPlay() && shouldAutoPlay_vassal_shouldPlayCard(context, Cards.vassal)) {
            return super.vassal_shouldPlayCard(context, card);
        }
        return selectBoolean(context, Cards.vassal, new Object[]{card});
    }

    // ////////////////////////////////////////////
    // Card interactions - cards from the Intrigue
    // ////////////////////////////////////////////
    @Override
    public Card[] secretChamber_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_secretChamber_cardsToDiscard(context)) {
            return super.secretChamber_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.secretChamber);
        return getFromHand(context, sco);
    }

    @Override
    public PawnOption[] pawn_chooseOptions(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_pawn_chooseOptions(context)) {
            return super.pawn_chooseOptions(context);
        }
        // There's probably some code that could be shared between this and the Trusty Steed
        // method, though it would be better if there were some Option superclass that I could use
        // instead of Object.
        PawnOption[] choices = new PawnOption[2];

        PawnOption[] options = PawnOption.values();
        int choiceOne = selectOption(context, Cards.pawn, options, null);
        choices[0] = options[choiceOne];
        PawnOption[] secondOptions = new PawnOption[options.length - 1];
        int j = 0;
        for (int i=0; i<options.length; i++, j++) {
            if (i == choiceOne) {
                i++;
                if (i == options.length) break;
            }
            secondOptions[j] = options[i];
        }
        int choiceTwo = selectOption(context, Cards.pawn, secondOptions, null);
        choices[1] = secondOptions[choiceTwo];
        return choices;
    }

    @Override
    public SacredVaultOption sacredVault_chooseOption(MoveContext context){
        SacredVaultOption[] options = SacredVaultOption.values();
        return options[selectOption(context, Cards.sacredVault, options, null)];
    }

    @Override
    public int ironStaff_chooseOption(MoveContext context) {
        IronStaffOption[] options = IronStaffOption.values();
        return options[selectOption(context, Cards.ironShodStaff, options, null)].ordinal();
    }


    @Override
    public TorturerOption torturer_attack_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_torturer_attack_chooseOption(context)) {
            return super.torturer_attack_chooseOption(context);
        }
        TorturerOption[] options = TorturerOption.values();
        return options[selectOption(context, Cards.torturer, options, null)];
    }

    @Override
    public StewardOption steward_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_steward_chooseOption(context)) {
            return super.steward_chooseOption(context);
        }
        StewardOption[] options = StewardOption.values();
        return options[selectOption(context, Cards.steward, options, null)];
    }

    @Override
    public Card swindler_cardToSwitch(MoveContext context, int cost, int debtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_swindler_cardToSwitch(context, cost, debtCost, potion)) {
            return super.swindler_cardToSwitch(context, cost, debtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(cost, debtCost, potion ? 1 : 0)
                .isSupplyCard()
                .setCardResponsible(Cards.swindler);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] steward_cardsToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_steward_cardsToTrash(context)) {
            return super.steward_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.steward);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] torturer_attack_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_torturer_attack_cardsToDiscard(context)) {
            return super.torturer_attack_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.torturer);
        return getFromHand(context, sco);
    }

    @Override
    public Card action_cardToPutBackOnDeck(MoveContext context, Card responsible) {
        if(context.isQuickPlay() && shouldAutoPlay_courtyard_cardToPutBackOnDeck(context)) {
            return super.action_cardToPutBackOnDeck(context, responsible);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(responsible);
        return getCardFromHand(context, sco);
    }

    @Override
    public boolean baron_shouldDiscardEstate(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_baron_shouldDiscardEstate(context)) {
            return super.baron_shouldDiscardEstate(context);
        }
        return selectBoolean(context, Cards.baron);
    }

    @Override
    public Card ironworks_cardToObtain(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_ironworks_cardToObtain(context)) {
            return super.ironworks_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxPotionCost(0).maxCost(4).maxDebtCost(0)
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.ironworks);
        return getFromTable(context, sco);
    }

    @Override
    public Card masquerade_cardToPass(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_masquerade_cardToPass(context)) {
            return super.masquerade_cardToPass(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.GIVE)
                .setCardResponsible(Cards.masquerade);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card bureaucrat_cardToReplace(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_bureaucrat_cardToReplace(context)) {
            return super.bureaucrat_cardToReplace(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isVictory()
                .setCardResponsible(Cards.bureaucrat);
        return getCardFromHand(context, sco);
    }


    @Override
    public Card secretPassage_cardToPutInDeck(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_secretPassage_cardToPutInDeck(context)) {
            return super.secretPassage_cardToPutInDeck(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(Cards.secretPassage);
        return getCardFromHand(context, sco);
    }

    @Override
    public int secretPassage_positionToPutCard(MoveContext context, Card card) {
        if(context.isQuickPlay() && shouldAutoPlay_secretPassage_positionToPutCard(context, card)) {
            return super.secretPassage_positionToPutCard(context, card);
        }

        Object[] options = new Object[context.getPlayer().deck.size() + 2];
        options[0] = card;
        for (int i = 1; i < context.getPlayer().deck.size()+1; i++) {
            options[i] = i-1;
        }
        return selectOption(context, Cards.secretPassage, options, null);
    }

    @Override
    public LurkerOption lurker_selectChoice(MoveContext context, LurkerOption[] options) {
        if(context.isQuickPlay() && shouldAutoPlay_lurker_selectChoice(context, options)) {
            return super.lurker_selectChoice(context, options);
        }
        return options[selectOption(context, Cards.lurker, options, null)];
    }

    @Override
    public Card lurker_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_lurker_cardToTrash(context)) {
            return super.lurker_cardToTrash(context);
        }

        SelectCardOptions sco = new SelectCardOptions().isAction()
                .isSupplyCard().setActionType(ActionType.TRASH).setPickType(PickType.TRASH)
                .setCardResponsible(Cards.lurker);
        return getFromTable(context, sco);
    }

    @Override
    public Card kiri_onDeath(MoveContext context) {

        ArrayList<Card> options = new ArrayList<Card>();
        Set<Card> inTrashPile = new HashSet<Card>();
        for (Card c : game.trashPile) {
            if (c.is(CardType.Enemy) && (c.is(new String[]{"elf"}, CardType.Undead)))
                inTrashPile.add(c);
        }
        options.addAll(inTrashPile);
        Collections.sort(options, new Util.CardNameComparator());

        if (options.isEmpty()) {
            return null;
        }
        return options.get(selectOption(context, Cards.kiri, options.toArray(), null));
    }
    @Override
    public Card lurker_cardToGainFromTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_lurker_cardToGainFromTrash(context)) {
            return super.lurker_cardToGainFromTrash(context);
        }

        ArrayList<Card> options = new ArrayList<Card>();
        Set<Card> inTrashPile = new HashSet<Card>();
        for (Card c : game.trashPile) {
            if (c.is(CardType.Action))
                inTrashPile.add(c);
        }
        options.addAll(inTrashPile);
        Collections.sort(options, new Util.CardNameComparator());

        if (options.isEmpty()) {
            return null;
        }
        return options.get(selectOption(context, Cards.lurker, options.toArray(), null));
    }

    @Override
    public Card masquerade_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_masquerade_cardToTrash(context)) {
            return super.masquerade_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.masquerade);
        return getCardFromHand(context, sco);
    }

    @Override
    public boolean miningVillage_shouldTrashMiningVillage(MoveContext context, Card responsible) {
        if(context.isQuickPlay() && shouldAutoPlay_miningVillage_shouldTrashMiningVillage(context, responsible)) {
            return super.miningVillage_shouldTrashMiningVillage(context, responsible);
        }
        return selectBoolean(context, responsible.behaveAsCard());
    }

    @Override
    public Card saboteur_cardToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_saboteur_cardToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.saboteur_cardToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .maxCost(maxCost).maxDebtCost(maxDebtCost).maxPotionCost(potion ? 1 : 0).setCardResponsible(Cards.saboteur);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] scoutPatrol_orderCards(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_scoutPatrol_orderCards(context, cards)) {
            return super.scoutPatrol_orderCards(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }

    @Override
    public Card replace_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_replace_cardToTrash(context)) {
            return super.replace_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.UPGRADE)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.replace);
        return getCardFromHand(context, sco);
    }

    public Card replace_cardToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_replace_cardToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.replace_cardToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(maxDebtCost).maxPotionCost(potion ? 1 : 0)
                .setCardResponsible(Cards.replace).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }


    @Override
    public CardsOrActionsOption cardsOrActions_choose(MoveContext context, Card theCard, int amountCards, int amountActions) {
        if(context.isQuickPlay() && shouldAutoPlay_nobles_chooseOptions(context)) {
            return super.cardsOrActions_choose(context, theCard, amountCards, amountActions);
        }
        CardsOrActionsOption[] options = CardsOrActionsOption.values();
        int[] amounts = {amountCards, amountActions};
        return options[selectOption(context, theCard, options, amounts)];
    }

    // Either return two cards, or null if you do not want to trash any cards.
    @Override
    public Card[] tradingPost_cardsToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_tradingPost_cardsToTrash(context)) {
            return super.tradingPost_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.tradingPost);
        return getFromHand(context, sco);
    }

    @Override
    public Card wishingWell_cardGuess(MoveContext context, ArrayList<Card> cardList) {
        if(context.isQuickPlay() && shouldAutoPlay_wishingWell_cardGuess(context)) {
            return super.wishingWell_cardGuess(context, cardList);
        }
        ArrayList<Card> options = new ArrayList<Card>();
        options.add(null);
        for (Card c : cardList) {
            options.add(c);
        }
        return options.get(selectOption(context, Cards.wishingWell, options.toArray(), null));
    }

    @Override
    public Card upgrade_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_upgrade_cardToTrash(context)) {
            return super.upgrade_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.upgrade);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card upgrade_cardToObtain(MoveContext context, int exactCost, int debtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_upgrade_cardToObtain(context, exactCost, debtCost, potion)) {
            return super.upgrade_cardToObtain(context, exactCost, debtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(exactCost, debtCost, potion ? 1 : 0)
        		.setActionType(ActionType.GAIN)
                .setCardResponsible(Cards.upgrade);
        return getFromTable(context, sco);
    }

    @Override
    public MinionOption minion_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_minion_chooseOption(context)) {
            return super.minion_chooseOption(context);
        }
        MinionOption[] options = MinionOption.values();
        return options[selectOption(context, Cards.minion, options, null)];
    }

    @Override
    public Card[] action_cardsToPutOnDeck(MoveContext context, Card responsible, int amount) {
        if(context.isQuickPlay() && shouldAutoPlay_secretChamber_cardsToPutOnDeck(context)) {
            return super.action_cardsToPutOnDeck(context, responsible, amount);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(amount).exactCount().ordered()
                .setCardResponsible(responsible);
        return getFromHand(context, sco);
    }

    @Override
    public Card courtier_cardToReveal(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_courtier_cardToReveal(context)) {
            return super.courtier_cardToReveal(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setActionType(ActionType.REVEAL)
                .setCardResponsible(Cards.courtier);
        return getCardFromHand(context, sco);
    }

    @Override
    public CourtierOption[] courtier_chooseOptions(MoveContext context,  CourtierOption[] options, int numOptions) {
        if (numOptions <= 0) return null;
        if (numOptions >= 4) return CourtierOption.values();

        if(context.isQuickPlay() && shouldAutoPlay_courtier_chooseOptions(context, options, numOptions)) {
            return super.courtier_chooseOptions(context, options, numOptions);
        }

        CourtierOption[] ret = new CourtierOption[numOptions];
        List<CourtierOption> optionList = new ArrayList<CourtierOption>(Arrays.asList(CourtierOption.values()));

        for (int i = 0; i < numOptions; i++) {
            int choice = selectOption(context, Cards.courtier, optionList.toArray(), null);
            ret[i] = optionList.remove(choice);
        }
        return ret;
    }

    @Override
    public Card[] diplomat_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_diplomat_cardsToDiscard(context)) {
            return super.diplomat_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.diplomat);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] mill_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_mill_cardsToDiscard(context)) {
            return super.mill_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD).setPassable()
                .setCardResponsible(Cards.mill);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] cardsToDiscard(MoveContext context, Card responsible, int amount, boolean exact, boolean passable) {

        SelectCardOptions sco = new SelectCardOptions().setCount(amount)
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(responsible);
        if (exact) {
            sco = sco.exactCount();
        }
        if (passable) {
            sco = sco.setPassable();
        }
        return getFromHand(context, sco);
    }

    // ////////////////////////////////////////////
    // Card interactions - cards from the Seaside
    // ////////////////////////////////////////////
    @Override
    public Card[] ghostShip_attack_cardsToPutBackOnDeck(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_ghostShip_attack_cardsToPutBackOnDeck(context)) {
            return super.ghostShip_attack_cardsToPutBackOnDeck(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(-3).ordered()
                .setCardResponsible(Cards.ghostShip);
        return getFromHand(context, sco);
    }

    @Override
    public Card salvager_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_salvager_cardToTrash(context)) {
            return super.salvager_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.salvager);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card[] warehouse_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_warehouse_cardsToDiscard(context)) {
            return super.warehouse_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.warehouse);
        return getFromHand(context, sco);
    }

    @Override
    public boolean pirateShip_takeTreasure(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_pirateShip_takeTreasure(context)) {
            return super.pirateShip_takeTreasure(context);
        }
        Object[] extras = new Object[1];
        extras[0] = this.getPirateShipTreasure();
        return selectBoolean(context, Cards.pirateShip, extras);
    }

    @Override
    public boolean nativeVillage_takeCards(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_nativeVillage_takeCards(context)) {
            return super.nativeVillage_takeCards(context);
        }
        return selectBoolean(context, Cards.nativeVillage);
    }

    @Override
    public Card smugglers_cardToObtain(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_smugglers_cardToObtain(context)) {
            return super.smugglers_cardToObtain(context);
        }
        ArrayList<Card> options = new ArrayList<Card>();
        Card[] cards = context.getCardsObtainedByLastPlayer().toArray(new Card[0]);
        for (Card c : cards)
            if (!c.costPotion() && c.getCost(context) <= 6 && !(c.getDebtCost(context) > 0) && !c.is(CardType.Prize, null) && context.isCardOnTop(c))
                options.add(c);

        if (options.size() > 0) {
            int o = selectOption(context, Cards.smugglers, options.toArray(), null);
            return options.get(o);
        } else
            return null;
    }

    @Override
    public Card island_cardToSetAside(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_island_cardToSetAside(context)) {
            return super.island_cardToSetAside(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(Cards.island);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card prince_cardToSetAside(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_prince_cardToSetAside(context)) {
            return super.prince_cardToSetAside(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isAction().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setPassable().setCardResponsible(Cards.prince);
        return getCardFromHand(context, sco);
    }

    @Override
    public int duration_cardToPlay(MoveContext context, Object[] cards) {
        /* includes cards on prince */
        if(context.isQuickPlay() && shouldAutoPlay_duration_cardToPlay(context)) {
            return super.duration_cardToPlay(context, cards);
        }
        if (cards == null || cards.length <= 2) {
            return 0;
        }
        return selectOption(context, Cards.prince, cards, null);
    }

    @Override
    public Card blackMarket_chooseCard(MoveContext context, ArrayList<Card> cardList) {
        if(context.isQuickPlay() && shouldAutoPlay_blackMarket_chooseCard(context)) {
            return super.blackMarket_chooseCard(context, cardList);
        }
        
        ArrayList<Card> options = new ArrayList<Card>();
        options.add(null);
        for (Card c : cardList) {
            options.add(c);
        }
        return options.get(selectOption(context, Cards.blackMarket, options.toArray(), null));
    }

    @Override
    public Card[] blackMarket_orderCards(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_blackMarket_orderCards(context, cards)) {
            return super.blackMarket_orderCards(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }

    @Override
    public Card haven_cardToSetAside(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_haven_cardToSetAside(context)) {
            return super.haven_cardToSetAside(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(Cards.haven);
        return getCardFromHand(context, sco);
    }

    @Override
    public boolean navigator_shouldDiscardTopCards(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_navigator_shouldDiscardTopCards(context, cards)) {
            return super.navigator_shouldDiscardTopCards(context, cards);
        }
        return selectBoolean(context, Cards.navigator, cards);
    }

    @Override
    public Card[] navigator_cardOrder(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_navigator_cardOrder(context, cards)) {
            return super.navigator_cardOrder(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order) {
            orderedCards.add(cards[i]);
        }
        return orderedCards.toArray(new Card[0]);
    }

    @Override
    public Card embargo_supplyToEmbargo(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_embargo_supplyToEmbargo(context)) {
            return super.embargo_supplyToEmbargo(context);
        }
        SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .setCardResponsible(Cards.embargo);
        return getFromTable(context, sco, true);
    }

    // Will be passed all three cards
    @Override
    public Card lookout_cardToTrash(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_lookout_cardToTrash(context, cards)) {
            return super.lookout_cardToTrash(context, cards);
        }
        Object[] options = new Object[1 + cards.length];
        options[0] = ActionType.TRASH;
        for (int i = 0; i < cards.length; i++) {
            options[i + 1] = cards[i];
        }
        return cards[selectOption(context, Cards.lookout, options, null)];
    }

    // Will be passed the two cards leftover after trashing one
    @Override
    public Card lookout_cardToDiscard(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_lookout_cardToDiscard(context, cards)) {
            return super.lookout_cardToDiscard(context, cards);
        }
        Object[] options = new Object[1 + cards.length];
        options[0] = ActionType.DISCARD;
        for (int i = 0; i < cards.length; i++) {
            options[i + 1] = cards[i];
        }
        return cards[selectOption(context, Cards.lookout, options, null)];
    }

    @Override
    public Card ambassador_revealedCard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_ambassador_revealedCard(context)) {
            return super.ambassador_revealedCard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setActionType(ActionType.REVEAL)
                .setCardResponsible(Cards.ambassador);
        return getCardFromHand(context, sco);
    }

    @Override
    public int ambassador_returnToSupplyFromHand(MoveContext context, Card card) {
        if(context.isQuickPlay() && shouldAutoPlay_ambassador_returnToSupplyFromHand(context, card)) {
            return super.ambassador_returnToSupplyFromHand(context, card);
        }
        int numCards = 0;
        for (Card c : context.player.getHand())
            if (c.equals(card))
                numCards++;

        return selectInt(context, Cards.ambassador, Math.min(2, numCards), card);
    }

    @Override
    public boolean shouldMoveToTop(MoveContext context, Card card, Card responsible) {
        if(context.isQuickPlay() && shouldAutoPlay_pearlDiver_shouldMoveToTop(context, card)) {
            return super.shouldMoveToTop(context, card, responsible);
        }

        Object[] extras = new Object[2];
        extras[0] = Cards.pearlDiver;
        extras[1] = card;
        return selectBoolean(context, Cards.pearlDiver, extras);
    }

    @Override
    public boolean explorer_shouldRevealProvince(MoveContext context) {
        if (context.isQuickPlay() && shouldAutoPlay_explorer_shouldRevealProvince(context)) {
            super.explorer_shouldRevealProvince(context);
        }
        return selectBoolean(context, Cards.explorer);
    }

    @Override
    public Card transmute_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_transmute_cardToTrash(context)) {
            return super.transmute_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.transmute);
        return getCardFromHand(context, sco);
    }

    @Override
    public ArrayList<Card> apothecary_cardsForDeck(MoveContext context, ArrayList<Card> cards) {
        if(context.isQuickPlay() && shouldAutoPlay_apothecary_cardsForDeck(context, cards)) {
            return super.apothecary_cardsForDeck(context, cards);
        }

        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards.toArray(new Card[0])));
        for (int i : order)
            orderedCards.add(cards.get(i));
        return orderedCards;
    }

    @Override
    public boolean alchemist_backOnDeck(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_alchemist_backOnDeck(context)) {
            return super.alchemist_backOnDeck(context);
        }
        return selectBoolean(context, Cards.alchemist);
    }

    @Override
    public Card herbalist_backOnDeck(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_herbalist_backOnDeck(context, cards)) {
            return super.herbalist_backOnDeck(context, cards);
        }
        return cards[selectOption(context, Cards.herbalist, cards, null)];
    }

    @Override
    public Card apprentice_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_apprentice_cardToTrash(context)) {
            return super.apprentice_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.apprentice);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card university_actionCardToObtain(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_university_actionCardToObtain(context)) {
            return super.university_actionCardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(5).maxDebtCost(0).maxPotionCost(0).isAction()
                .setPassable().setCardResponsible(Cards.university);
        return getFromTable(context, sco);
    }

    @Override
    public boolean scryingPool_shouldDiscard(MoveContext context, Player targetPlayer, Card card) {
        if(context.isQuickPlay() && shouldAutoPlay_scryingPool_shouldDiscard(context, targetPlayer, card)) {
            return super.scryingPool_shouldDiscard(context, targetPlayer, card);
        }
        Object[] extras = new Object[3];
        extras[0] = targetPlayer.getPlayerName();
        extras[1] = Cards.scryingPool;
        extras[2] = card;
        return selectBoolean(context, Cards.scryingPool, extras);
    }

    @Override
    public Card[] golem_cardOrder(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_golem_cardOrder(context, cards)) {
            return super.golem_cardOrder(context, cards);
        }

        if (cards == null || cards.length < 2) {
            return cards;
        }

        int o = selectOption(context, Cards.golem, cards, null);
        if (o == 0) {
            return cards;
        }
        return new Card[]{ cards[1], cards[0] };
    }

    @Override
    public Card bishop_cardToTrashForVictoryTokens(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_bishop_cardToTrashForVictoryTokens(context)) {
            return super.bishop_cardToTrashForVictoryTokens(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setCardResponsible(Cards.bishop);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card bishop_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_bishop_cardToTrash(context)) {
            return super.bishop_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.bishop);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card contraband_cardPlayerCantBuy(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_contraband_cardPlayerCantBuy(context)) {
            return super.contraband_cardPlayerCantBuy(context);
        }
        SelectCardOptions sco = new SelectCardOptions().allowEmpty()
                .setCardResponsible(Cards.contraband);
        return getFromTable(context, sco);
    }

    @Override
    public int countingHouse_coppersIntoHand(MoveContext context, int coppersTotal) {
        if(context.isQuickPlay() && shouldAutoPlay_countingHouse_coppersIntoHand(context)) {
            return super.countingHouse_coppersIntoHand(context, coppersTotal);
        }
        return selectInt(context, Cards.countingHouse, coppersTotal);
    }

    @Override
    public Card expand_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_expand_cardToTrash(context)) {
            return super.expand_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.expand);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card expand_cardToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_expand_cardToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.expand_cardToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(maxDebtCost).maxPotionCost(potion ? 1 : 0)
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.expand);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] forge_cardsToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_forge_cardsToTrash(context)) {
            return super.forge_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.forge);
        return getFromHand(context, sco);
    }

    @Override
    public Card forge_cardToObtain(MoveContext context, int exactCost) {
        if(context.isQuickPlay() && shouldAutoPlay_forge_cardToObtain(context, exactCost)) {
            return super.forge_cardToObtain(context, exactCost);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(exactCost, 0, 0)
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.forge);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] goons_attack_cardsToKeep(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_goons_attack_cardsToKeep(context)) {
            return super.goons_attack_cardsToKeep(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.KEEP).setCardResponsible(Cards.goons);
        return getFromHand(context, sco);
    }

    @Override
    public Card kingsCourt_cardToPlay(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_kingsCourt_cardToPlay(context)) {
            return super.kingsCourt_cardToPlay(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isAction()
                .setPassable().setPickType(PickType.PLAY)
                .setCardResponsible(Cards.kingsCourt);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card throneRoom_cardToPlay(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_throneRoom_cardToPlay(context)) {
            return super.throneRoom_cardToPlay(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isAction()
                .setPickType(PickType.PLAY)
                .setCardResponsible(Cards.throneRoom);
        if (!Game.errataThroneRoomForced) {
        	sco.setPassable();
        }
        return getCardFromHand(context, sco);
    }

    @Override
    public boolean loan_shouldTrashTreasure(MoveContext context, Card treasure) {
        if(context.isQuickPlay() && shouldAutoPlay_loan_shouldTrashTreasure(context, treasure)) {
            return super.loan_shouldTrashTreasure(context, treasure);
        }
        Object[] extras = new Object[2];
        extras[0] = Cards.loan;
        extras[1] = treasure;
        return selectBoolean(context, Cards.loan, extras);
    }

    @Override
    public Card mint_treasureToMint(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_mint_treasureToMint(context)) {
            return super.mint_treasureToMint(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure().isSupplyCard()
                .setPassable().setPickType(PickType.MINT)
                .setCardResponsible(Cards.mint);
        return getCardFromHand(context, sco);
    }

    @Override
    public boolean mountebank_attack_shouldDiscardCurse(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_mountebank_attack_shouldDiscardCurse(context)) {
            return super.mountebank_attack_shouldDiscardCurse(context);
        }
        return selectBoolean(context, Cards.mountebank);
    }

    @Override
    public Card[] rabble_attack_cardOrder(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_rabble_attack_cardOrder(context, cards)) {
            return super.rabble_attack_cardOrder(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }

    @Override
    public boolean royalSealTravellingFair_shouldPutCardOnDeck(MoveContext context, Card responsible, Card card) {
    	if(context.isQuickPlay() && shouldAutoPlay_royalSealTravellingFair_shouldPutCardOnDeck(context, responsible, card)) {
            return super.royalSealTravellingFair_shouldPutCardOnDeck(context, responsible, card);
        }
        Object[] extras = new Object[2];
        extras[0] = responsible; /* royalSeal or travellingFair */
        extras[1] = card;
        return selectBoolean(context, responsible, extras);
    }

    @Override
    public Card tradeRoute_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_tradeRoute_cardToTrash(context)) {
            return super.tradeRoute_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.tradeRoute);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card[] vault_cardsToDiscardForGold(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_vault_cardsToDiscardForGold(context)) {
            return super.vault_cardsToDiscardForGold(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setCardResponsible(Cards.vault);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] vault_cardsToDiscardForCard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_vault_cardsToDiscardForCard(context)) {
            return super.vault_cardsToDiscardForCard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPassable().setPickType(PickType.DISCARD)
                .setCardResponsible(Cards.vault);
        return getFromHand(context, sco);
    }

    @Override
    public WatchTowerOption watchTower_chooseOption(MoveContext context, Card card) {
        if(context.isQuickPlay() && shouldAutoPlay_watchTower_chooseOption(context, card)) {
            return super.watchTower_chooseOption(context, card);
        }
        WatchTowerOption[] watchTower_options = WatchTowerOption.values();
        Object[] options = new Object[1 + watchTower_options.length];
        options[0] = card;
        for (int i = 0; i < watchTower_options.length; i++) {
            options[i + 1] = watchTower_options[i];
        }
        return watchTower_options[selectOption(context, Cards.watchTower, options, null)];
    }

    @Override
    public Card hamlet_cardToDiscardForAction(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_hamlet_cardToDiscardForAction(context)) {
            return super.hamlet_cardToDiscardForAction(context);
        }
        // WARNING: This is a total hack!  We need to differentiate the "discard for action" from
        // the "discard for buy", but we don't have any way in the SelectCardOptions to do that.
        // So we set a fake action type here, and handle this as a special case in Strings.java.
        // This is fragile and could easily break if the rest of the code changes and we aren't
        // careful.  TODO(matt): come up with a better way to do this.
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.hamlet);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card hamlet_cardToDiscardForBuy(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_hamlet_cardToDiscardForBuy(context)) {
            return super.hamlet_cardToDiscardForBuy(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setCardResponsible(Cards.hamlet);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card hornOfPlenty_cardToObtain(MoveContext context, int maxCost) {
        if(context.isQuickPlay() && shouldAutoPlay_hornOfPlenty_cardToObtain(context, maxCost)) {
            return super.hornOfPlenty_cardToObtain(context, maxCost);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(0).maxPotionCost(0)
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.hornOfPlenty);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] discardMultiple_cardsToDiscard(MoveContext context, Card responsible, int numToDiscard) {
        if(context.isQuickPlay() && shouldAutoPlay_discardMultiple_cardsToDiscard(context)) {
            return super.discardMultiple_cardsToDiscard(context, responsible, numToDiscard);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(numToDiscard).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(responsible);
        return getFromHand(context, sco);
    }

    @Override
    public JesterOption jester_chooseOption(MoveContext context, Player targetPlayer, Card card) {
        if(context.isQuickPlay() && shouldAutoPlay_jester_chooseOption(context, targetPlayer, card)) {
            return super.jester_chooseOption(context, targetPlayer, card);
        }
        JesterOption[] jester_options = JesterOption.values();
        Object[] options = new Object[2 + jester_options.length];
        options[0] = targetPlayer.getPlayerName();
        options[1] = card;
        for (int i = 0; i < jester_options.length; i++) {
            options[i + 2] = jester_options[i];
        }
        return jester_options[selectOption(context, Cards.jester, options, null)];
    }

    @Override
    public Card remake_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_remake_cardToTrash(context)) {
            return super.remake_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.remake);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card remake_cardToObtain(MoveContext context, int exactCost, int debtCost, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_remake_cardToObtain(context, exactCost, debtCost, potion)) {
            return super.remake_cardToObtain(context, exactCost, debtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(exactCost, debtCost, potion ? 1 : 0)
        		.setActionType(ActionType.GAIN)
                .setCardResponsible(Cards.remake);
        return getFromTable(context, sco);
    }

    @Override
    public boolean tournament_shouldRevealProvince(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_tournament_shouldRevealProvince(context)) {
            return super.tournament_shouldRevealProvince(context);
        }
        return selectBoolean(context, Cards.tournament);
    }

    @Override
    public TournamentOption tournament_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_tournament_chooseOption(context)) {
            return super.tournament_chooseOption(context);
        }
        TournamentOption[] options = TournamentOption.values();
        return options[selectOption(context, Cards.tournament, options, null)];
    }

    @Override
    public Card tournament_choosePrize(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_tournament_choosePrize(context)) {
            return super.tournament_choosePrize(context);
        }
        SelectCardOptions sco = new SelectCardOptions().fromPrizes()
                .setCardResponsible(Cards.tournament);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] youngWitch_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_youngWitch_cardsToDiscard(context)) {
            return super.youngWitch_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.youngWitch);
        return getFromHand(context, sco);
    }

    @Override
    public Card[] followers_attack_cardsToKeep(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_followers_attack_cardsToKeep(context)) {
            return super.followers_attack_cardsToKeep(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.KEEP).setCardResponsible(Cards.followers);
        return getFromHand(context, sco);
    }

    @Override
    public TrustySteedOption[] trustySteed_chooseOptions(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_trustySteed_chooseOptions(context)) {
            return super.trustySteed_chooseOptions(context);
        }
        TrustySteedOption[] choices = new TrustySteedOption[2];

        TrustySteedOption[] options = TrustySteedOption.values();
        int choiceOne = selectOption(context, Cards.trustySteed, options, null);
        choices[0] = options[choiceOne];
        TrustySteedOption[] secondOptions = new TrustySteedOption[options.length - 1];
        int j = 0;
        for (int i=0; i<options.length; i++, j++) {
            if (i == choiceOne) {
                i++;
                if (i == options.length) break;
            }
            secondOptions[j] = options[i];
        }
        int choiceTwo = selectOption(context, Cards.trustySteed, secondOptions, null);
        choices[1] = secondOptions[choiceTwo];
        return choices;
    }

    @Override
    public Card thief_treasureToTrash(MoveContext context, Card[] treasures) {
        if(context.isQuickPlay() && shouldAutoPlay_thief_treasureToTrash(context, treasures)) {
            return super.thief_treasureToTrash(context, treasures);
        }
        return treasures[selectOption(context, Cards.thief, treasures, null)];
    }

    @Override
    public Card[] thief_treasuresToGain(MoveContext context, Card[] treasures) {
        if(context.isQuickPlay() && shouldAutoPlay_thief_treasuresToGain(context, treasures)) {
            return super.thief_treasuresToGain(context, treasures);
        }
        ArrayList<Card> options = new ArrayList<Card>();
        options.add(null);
        for (Card c : treasures)
            options.add(c);

        ArrayList<Card> toGain = new ArrayList<Card>();

        while (options.size() > 1) {
            int o = selectOption(context, Cards.thief, options.toArray(), null);
            if (o == 0) break;
            toGain.add(options.get(o));
            options.remove(o);
        }

        return toGain.toArray(new Card[0]);
    }

    @Override
    public Card pirateShip_treasureToTrash(MoveContext context, Card[] treasures) {
        if(context.isQuickPlay() && shouldAutoPlay_pirateShip_treasureToTrash(context, treasures)) {
            return super.pirateShip_treasureToTrash(context, treasures);
        }
        return treasures[selectOption(context, Cards.pirateShip, treasures, null)];
    }


    @Override
    public boolean shouldDiscardCard(MoveContext context, Card card, Card responsible) {
        if(context.isQuickPlay() && shouldAutoPlay_duchess_shouldDiscardCardFromTopOfDeck(context, card)) {
            return super.shouldDiscardCard(context, card, responsible);
        }
        Object[] extras = new Object[3];
        extras[0] = responsible;
        extras[1] = card;
        extras[2] = card.is(CardType.Crown);
        return !selectBoolean(context, responsible, extras);
    }

    @Override
    public Card[] develop_orderCards(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_develop_orderCards(context, cards)) {
            return super.develop_orderCards(context, cards);
        }
        ArrayList<Card> orderedCards = new ArrayList<Card>();
        int[] order = orderCards(context, cardArrToIntArr(cards));
        for (int i : order)
            orderedCards.add(cards[i]);
        return orderedCards.toArray(new Card[0]);
    }

    @Override
    public Card getAttackReaction(MoveContext context, Card responsible, boolean defended, Card lastCard) {
        ArrayList<Card> reactionCards = new ArrayList<Card>();
        for (Card c : getAttackReactionCards(defended)) {
        	reactionCards.add(c);
        }
        if (reactionCards.size() > 0) {
            ArrayList<Card> cards = new ArrayList<Card>();
            for (Card c : reactionCards) {
            	Card a = c;
            	if (c.equals(Cards.estate)) {
            		a = getInheritance();
            	}
                if (lastCard == null
                        || !Game.suppressRedundantReactions
                        || a.getName() != lastCard.getName()
                        || a.equals(Cards.horseTraders)
                        || a.equals(Cards.caravanGuard)
                        || a.equals(Cards.diplomat)
                        ) {
                    cards.add(c);
                }
            }
            if (cards.size() > 0) {
                cards.add(null);
                Object[] options = new Object[1 + cards.size()];
                options[0] = OPTION_REACTION;
                for (int i = 0; i < cards.size(); i++) {
                    options[i + 1] = cards.get(i);
                }
                return cards.get(selectOption(context, responsible, options, null));
            }
        }
        return null;
    }

    @Override
    public boolean revealBane(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_revealBane(context)) {
            return super.revealBane(context);
        }
        Object[] extras = new Object[1];
        extras[0] = game.baneCard;
        return selectBoolean(context, Cards.youngWitch, extras);
    }

    @Override
    public PutBackOption selectPutBackOption(MoveContext context, List<PutBackOption> putBacks) {
        if(context.isQuickPlay() && shouldAutoPlay_selectPutBackOption(context, putBacks)) {
            return super.selectPutBackOption(context, putBacks);
        }
        Collections.sort(putBacks);
        putBacks.add(PutBackOption.None);
        Object[] options = new Object[1 + putBacks.size()];
        options[0] = OPTION_PUTBACK;
        for (int i = 0; i < putBacks.size(); i++) {
            options[i + 1] = putBacks.get(i);
        }
        return putBacks.get(selectOption(context, null, options, null));
    }


    @Override
    public GovernorOption governor_chooseOption(MoveContext context) {
        GovernorOption[] options = GovernorOption.values();
        return options[selectOption(context, Cards.governor, options, null)];
    }
    
    @Override
    public Card governor_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_governor_cardToTrash(context)) {
            return super.governor_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
        	.setPassable().setActionType(ActionType.TRASH).setCardResponsible(Cards.governor);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card governor_cardToObtain(MoveContext context, int exactCost, int debt, boolean potion) {
    	if(context.isQuickPlay() && shouldAutoPlay_governor_cardToObtain(context, exactCost, debt, potion)) {
            return super.governor_cardToObtain(context, exactCost, debt, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(exactCost, debt, potion ? 1 : 0)
        		.setActionType(ActionType.GAIN)
                .setCardResponsible(Cards.governor);
        return getFromTable(context, sco);
    }

    @Override
    public Card envoy_cardToDiscard(MoveContext context, Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_envoy_opponentCardToDiscard(context)) {
            return super.envoy_cardToDiscard(context, cards);
        }
        Object[] options = new Object[1 + cards.length];
        options[0] = context.getPlayer().getPlayerName();
        for (int i = 0; i < cards.length; i++) {
            options[i + 1] = cards[i];
        }
        return cards[selectOption(context, Cards.envoy, options, null)];
    }

    public enum StashOption {
    	PlaceOnTop,
    	PlaceAfterCardsToDraw,
    	PlaceOther
    }
    
    @Override
    public int stash_chooseDeckPosition(MoveContext context, Card responsible, int deckSize, int numStashes, int cardsToDraw) {
    	if(context.isQuickPlay() && shouldAutoPlay_stash_chooseDeckPosition(context, deckSize, numStashes, cardsToDraw)) {
            return super.stash_chooseDeckPosition(context, responsible, deckSize, numStashes, cardsToDraw);
        }
    	// Simple options first
    	Object[] options = new Object[8];
    	options[0] = OPTION_STASH;
    	options[1] = deckSize;
    	options[2] = numStashes;
    	options[3] = cardsToDraw;
    	options[4] = responsible != null ? responsible.behaveAsCard() : null;
    	options[5] = StashOption.PlaceOnTop;
    	options[6] = StashOption.PlaceAfterCardsToDraw;
    	options[7] = StashOption.PlaceOther;
    	
    	StashOption option = (StashOption) options[selectOption(context, Cards.stash, options, null) + 5];
    	if (option == StashOption.PlaceOnTop) {
    		return 0;
    	} else if (option == StashOption.PlaceAfterCardsToDraw) {
    		if (cardsToDraw > deckSize)
    			return deckSize;
    		return cardsToDraw;
    	}
    	// advanced options if player chose other
    	if (numStashes > 1) {
	    	options = new Object[deckSize + 7];
	    	options[0] = OPTION_STASH_POSITION;
	    	options[1] = deckSize;
	    	options[2] = numStashes;
	    	options[3] = responsible != null ? responsible.behaveAsCard() : null;
	    	for (int i = 4; i < options.length; ++i) {
	    		options[i] = i - 6;
	    	}
	    	return (Integer) options[selectOption(context, Cards.stash, options, null) + 4];
    	}
    	
    	options = new Object[deckSize + 6];
    	options[0] = OPTION_STASH_POSITION;
    	options[1] = deckSize;
    	options[2] = numStashes;
    	options[3] = responsible != null ? responsible.behaveAsCard() : null;
    	for (int i = 4; i < options.length; ++i) {
    		options[i] = i - 5;
    	}
    	return (Integer) options[selectOption(context, Cards.stash, options, null) + 4];
    }

    @Override
    public boolean sauna_shouldPlayAvanto(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_sauna_shouldPlayAvanto(context)) {
            return super.sauna_shouldPlayAvanto(context);
        }
        return selectBoolean(context, Cards.sauna);
    }
    
    @Override
    public Card sauna_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_sauna_cardToTrash(context)) {
            return super.sauna_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.sauna);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public boolean avanto_shouldPlaySauna(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_avanto_shouldPlaySauna(context)) {
            return super.avanto_shouldPlaySauna(context);
        }
        return selectBoolean(context, Cards.avanto);
    }

    @Override
    public Card taxman_treasureToObtain(MoveContext context, int maxCost, int debt, boolean potion) {
        if(context.isQuickPlay() && shouldAutoPlay_taxman_treasureToObtain(context, maxCost, debt, potion)) {
            return super.taxman_treasureToObtain(context, maxCost, debt, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure().maxCost(maxCost).maxDebtCost(debt)
                .maxPotionCost(potion ? 1 : 0)
                .setCardResponsible(Cards.taxman);
        return getFromTable(context, sco);
    }

    @Override
    public Card plaza_treasureToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_stables_treasureToDiscard(context)) {
           Card card = super.plaza_treasureToDiscard(context);
            if (card != null) {
                return card;
            }
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure()
                .setPassable().setPickType(PickType.DISCARD)
                .setActionType(ActionType.DISCARD).setCardResponsible(Cards.plaza);
        return getCardFromHand(context, sco);
    }

    @Override
    public int numGuildsCoinTokensToSpend(MoveContext context, int coinTokenTotal, boolean butcher) {
        return selectInt(context, null, coinTokenTotal, OPTION_SPEND_GUILD_COINS);
    }
    
    @Override
    public int numDebtTokensToPayOff(MoveContext context) {
        if (context.isQuickPlay() && shoudlAutoPlay_payoffDebt(context)) {
            return super.numDebtTokensToPayOff(context);
        }
    	return selectInt(context, null, Math.min(context.getCoins(), context.getPlayer().getDebtTokenCount()), OPTION_PAY_DEBT);
    }

    @Override
    public int amountToOverpay(MoveContext context, Card card) {
        int availableAmount = context.getCoinAvailableForBuy();
        if (availableAmount <= 0) {
            return 0;
        }
        else {
            return selectInt(context, null, availableAmount, OPTION_OVERPAY);
        }
    }

    @Override
    public int overpayByPotions(MoveContext context, int availablePotions) {
        if (availablePotions > 0) {
            return selectInt(context, null, availablePotions, OPTION_OVERPAY_POTION);
        }
        else {
            return 0;
        }
    }

    @Override
    public Card butcher_cardToTrash(MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setPassable().setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.butcher);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card butcher_cardToObtain(MoveContext context, int maxCost, int maxDebt, boolean potion) {
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(maxDebt).maxPotionCost(potion ? 1 : 0)
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.butcher);
        return getFromTable(context, sco);
    }

    @Override
    public Card advisor_cardToDiscard(MoveContext context, Card[] cards) {
        Object[] options = new Object[1 + cards.length];
        options[0] = context.getPlayer().getPlayerName();
        for (int i = 0; i < cards.length; i++) {
            options[i + 1] = cards[i];
        }
        return cards[selectOption(context, Cards.advisor, options, null)];
    }

    @Override
    public Card journeyman_cardToPick(MoveContext context, List<Card> cardList) {
        List<Card> options = new ArrayList<Card>();
        options.add(null);
        for (Card c : cardList) {
            options.add(c);
        }
        return options.get(selectOption(context, Cards.journeyman, options.toArray(), null));
    }

    @Override
    public Card stonemason_cardToTrash(MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH).allowEmpty()
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.stonemason);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card stonemason_cardToGain(MoveContext context, int maxCost, int maxDebt, boolean potion) {
    	SelectCardOptions sco = new SelectCardOptions()
				.maxCost(maxCost).maxDebtCost(maxDebt).maxPotionCost(potion?1:0).lessThanMax()
				.setActionType(ActionType.GAIN).setCardResponsible(Cards.stonemason);
        return getFromTable(context, sco);
    }

    @Override
    public Card stonemason_cardToGainOverpay(MoveContext context, int overpayAmount, boolean potion) {
        SelectCardOptions sco = new SelectCardOptions().exactCost(overpayAmount, 0, potion ? 1 : 0)
                .isAction()
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.stonemason);
        return getFromTable(context, sco);
    }

    @Override
    public Card doctor_cardToPick(MoveContext context, List<Card> cardList) {
        List<Card> options = new ArrayList<Card>();
        options.add(null);
        for (Card c : cardList) {
            options.add(c);
        }
        return options.get(selectOption(context, Cards.doctor, options.toArray(), null));
    }

    @Override
    public ArrayList<Card> doctor_cardsForDeck(MoveContext context, ArrayList<Card> cards) {
        ArrayList<Card> orderedCards = new ArrayList<Card>();

        int[] order = orderCards(context, cardArrToIntArr(cards.toArray(new Card[0])));

        for (int i : order) {
            orderedCards.add(cards.get(i));
        }

        return orderedCards;
    }

    @Override
    public DoctorOverpayOption doctor_chooseOption(MoveContext context, Card card) {
        DoctorOverpayOption[] doctor_options = DoctorOverpayOption.values();
        Object[] options = new Object[1 + doctor_options.length];
        options[0] = card;
        for (int i = 0; i < doctor_options.length; i++) {
            options[i + 1] = doctor_options[i];
        }
        return doctor_options[selectOption(context, Cards.doctor, options, null)];
    }

    @Override
    public Card herald_cardTopDeck(MoveContext context, Card[] cardList) {
        ArrayList<Card> options = new ArrayList<Card>();

        // Remove first Herald from this list (representing the most recent one bought)
        boolean heraldRemoved = false;

        for (Card c : cardList) {
            if (!heraldRemoved && c.getName().equalsIgnoreCase("herald")) {
                heraldRemoved = true;
            }
            else {
                options.add(c);
            }
        }

        if (options.isEmpty()) {
            return null;
        }

        return options.get(selectOption(context, Cards.herald, options.toArray(), null));
    }
    
    /*Adventures*/
    @Override
    public AmuletOption amulet_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_amulet_chooseOption(context)) {
            return super.amulet_chooseOption(context);
        }
        AmuletOption[] options = AmuletOption.values();
        return options[selectOption(context, Cards.amulet, options, null)];
    }

    @Override
    public Card amulet_cardToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_amulet_cardToTrash(context)) {
            return super.amulet_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(1).exactCount()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.amulet);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card[] artificer_cardsToDiscard(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_artificer_cardsToDiscard(context)) {
            return super.artificer_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.artificer);
        return getFromHand(context, sco);
    }

    @Override
    public Card artificer_cardToObtain(MoveContext context, int cost) {
        if(context.isQuickPlay() && shouldAutoPlay_artificer_cardToObtain(context, cost)) {
            return super.artificer_cardToObtain(context, cost);
        }
        SelectCardOptions sco = new SelectCardOptions().exactCost(cost, 0, 0).setPassable()
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.artificer);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card call_whenGainCardToCall(MoveContext context, Card gainedCard, Card[] possibleCards) {
    	if(context.isQuickPlay() && shouldAutoPlay_call_whenGainCardToCall(context, gainedCard, possibleCards)) {
            return super.call_whenGainCardToCall(context, gainedCard, possibleCards);
        }

        Object[] options = new Object[1 + possibleCards.length];
        options[0] = OPTION_CALL_WHEN_GAIN;
        for (int i = 0; i < possibleCards.length; i++) {
            options[i + 1] = possibleCards[i];
        }
        return possibleCards[selectOption(context, gainedCard, options, null)];
    }
    
    @Override
    public Card call_whenActionResolveCardToCall(MoveContext context, Card resolvedAction, Card[] possibleCards) {
    	if(context.isQuickPlay() && shouldAutoPlay_call_whenActionResolveCardToCall(context, resolvedAction, possibleCards)) {
            return super.call_whenActionResolveCardToCall(context, resolvedAction, possibleCards);
        }

        Object[] options = new Object[1 + possibleCards.length];
        options[0] = OPTION_CALL_RESOLVE_ACTION;
        for (int i = 0; i < possibleCards.length; i++) {
            options[i + 1] = possibleCards[i];
        }
        return possibleCards[selectOption(context, resolvedAction, options, null)];
    }
    
    @Override
    public Card call_whenTurnStartCardToCall(MoveContext context, Card[] possibleCards) {
    	if(context.isQuickPlay() && shouldAutoPlay_call_whenTurnStartCardToCall(context, possibleCards)) {
            return super.call_whenTurnStartCardToCall(context, possibleCards);
        }

        Object[] options = new Object[1 + possibleCards.length];
        options[0] = OPTION_START_TURN_EFFECT;
        for (int i = 0; i < possibleCards.length; i++) {
            options[i + 1] = possibleCards[i];
        }
        return possibleCards[selectOption(context, null, options, null)];
    }
    
    @Override
    public Card disciple_cardToPlay(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_disciple_cardToPlay(context)) {
            return super.disciple_cardToPlay(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isAction()
                .setPassable().setPickType(PickType.PLAY)
                .setCardResponsible(Cards.disciple);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card fugitive_cardToDiscard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_fugitive_cardToDiscard(context)) {
            return super.fugitive_cardToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.DISCARD)
                .setActionType(ActionType.DISCARD).setCardResponsible(Cards.fugitive);
        return getCardFromHand(context, sco);
    }

    @Override
    public Card[] gear_cardsToSetAside(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_haven_cardToSetAside(context)) {
            return super.gear_cardsToSetAside(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable().setCount(2)
                .setCardResponsible(Cards.gear);
        return getFromHand(context, sco);
    }
    
    @Override
    public Card hero_treasureToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_hero_treasureToObtain(context)) {
            return super.hero_treasureToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isTreasure()
        		.setActionType(ActionType.GAIN)
        		.setCardResponsible(Cards.hero);
        return getFromTable(context, sco);
    }
    
    @Override
    public boolean messenger_shouldDiscardDeck(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_messenger_shouldDiscardDeck(context)) {
            return super.messenger_shouldDiscardDeck(context);
        }
        return selectBoolean(context, Cards.messenger);
    }
    
    @Override
    public Card messenger_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_messenger_cardToObtain(context)) {
            return super.messenger_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.messenger).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public boolean miser_shouldTakeTreasure(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_miser_shouldTakeTreasure(context)) {
            return super.miser_shouldTakeTreasure(context);
        }
        Object[] extras = new Object[1];
        extras[0] = this.getMiserTreasure();
        return selectBoolean(context, Cards.miser, extras);
    }
    
    @Override
    public Card ratcatcher_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_ratcatcher_cardToTrash(context)) {
            return super.ratcatcher_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
        		.setActionType(ActionType.TRASH).setCardResponsible(Cards.ratcatcher);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public boolean raze_shouldTrashRazePlayed(MoveContext context, Card responsible) {
    	if(context.isQuickPlay() && shouldAutoPlay_raze_shouldTrashRazePlayed(context, responsible)) {
            return super.raze_shouldTrashRazePlayed(context, responsible);
        }
    	return selectBoolean(context, responsible.behaveAsCard());
    }
    
    @Override
    public Card raze_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_raze_cardToTrash(context)) {
            return super.raze_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
        		.setActionType(ActionType.TRASH).setCardResponsible(Cards.raze);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card raze_cardToKeep(MoveContext context, Card[] cards) {
    	if(context.isQuickPlay() && shouldAutoPlay_raze_cardToKeep(context)) {
            return super.raze_cardToKeep(context, cards);
        }
        return cards[selectOption(context, Cards.raze, cards, null)];
    }
    
    @Override
    public Card soldier_cardToDiscard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_soldier_cardToDiscard(context)) {
            return super.soldier_cardToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.DISCARD)
                .setActionType(ActionType.DISCARD).setCardResponsible(Cards.soldier);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public PlayerSupplyToken teacher_tokenTypeToMove(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_teacher_tokenTypeToMove(context)) {
            return super.teacher_tokenTypeToMove(context);
        }
        PlayerSupplyToken[] options = new PlayerSupplyToken[]{
        		PlayerSupplyToken.PlusOneCard,
        		PlayerSupplyToken.PlusOneAction,
        		PlayerSupplyToken.PlusOneBuy,
        		PlayerSupplyToken.PlusOneCoin
        };
        return options[selectOption(context, Cards.teacher, options, null)];
    }
    
    @Override
    public Card teacher_actionCardPileToHaveToken(MoveContext context,
    		PlayerSupplyToken token) {
    	if(context.isQuickPlay() && shouldAutoPlay_teacher_actionCardPileToHaveToken(context, token)) {
            return super.teacher_actionCardPileToHaveToken(context, token);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty().token(token)
                .isAction().noTokens().setCardResponsible(Cards.teacher);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public Card transmogrify_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_transmogrify_cardToTrash(context)) {
            return super.transmogrify_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
        		.setActionType(ActionType.TRASH).setCardResponsible(Cards.transmogrify);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card transmogrify_cardToObtain(MoveContext context, int maxCost, int maxDebtCost, boolean potion) {
    	if(context.isQuickPlay() && shouldAutoPlay_transmogrify_cardToObtain(context, maxCost, maxDebtCost, potion)) {
            return super.transmogrify_cardToObtain(context, maxCost, maxDebtCost, potion);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(maxCost).maxDebtCost(maxDebtCost).maxPotionCost(potion?1:0)
                .setCardResponsible(Cards.transmogrify).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public boolean traveller_shouldExchange(MoveContext context, Card traveller, Card exchange) {
        if(context.isQuickPlay() && shouldAutoPlay_traveller_shouldExchange(context, traveller, exchange)) {
            return super.traveller_shouldExchange(context, traveller, exchange);
        }
        Object[] extras = new Object[1];
        extras[0] = exchange;
        return selectBoolean(context, traveller.behaveAsCard(), extras);
    }

    @Override
    public int cleanup_wineMerchantToDiscard(MoveContext context, int wineMerchantTotal) {
        if(context.isQuickPlay() && shouldAutoPlay_cleanup_wineMerchantToDiscard(context)) {
            return super.cleanup_wineMerchantToDiscard(context, wineMerchantTotal);
        }
        if (wineMerchantTotal  == 1) {
        	return selectBoolean(context, Cards.wineMerchant) ? 1 : 0;
        }
        
        return selectInt(context, Cards.wineMerchant, wineMerchantTotal);
    }
    
    @Override
    public int cleanup_wineMerchantEstateToDiscard(MoveContext context, int wineMerchantTotal) {
    	if(context.isQuickPlay() && shouldAutoPlay_cleanup_wineMerchantEstateToDiscard(context)) {
            return super.cleanup_wineMerchantEstateToDiscard(context, wineMerchantTotal);
        }
        if (wineMerchantTotal  == 1) {
        	return selectBoolean(context, Cards.estate) ? 1 : 0;
        }
        
        return selectInt(context, Cards.estate, wineMerchantTotal);
    }
    
    @Override
    public Card alms_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_alms_cardToObtain(context)) {
            return super.alms_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.alms).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card ball_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_ball_cardToObtain(context)) {
            return super.ball_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.ball).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }

    @Override
    public Card[] bonfire_cardsToTrash(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_chapel_cardsToTrash(context)) {
            return super.bonfire_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2)
                .setPassable().setPickType(PickType.TRASH)
                .setCardResponsible(Cards.bonfire).setActionType(ActionType.TRASH);
        return getFromPlayed(context, sco);
    }
    
    @Override
    public Card ferry_actionCardPileToHaveToken(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_ferry_actionCardPileToHaveToken(context)) {
            return super.ferry_actionCardPileToHaveToken(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .isAction().setCardResponsible(Cards.ferry);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public Card inheritance_actionCardTosetAside(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_inheritance_actionCardTosetAside(context)) {
            return super.inheritance_actionCardTosetAside(context);
        }
    	SelectCardOptions sco = new SelectCardOptions()
                .isAction().isNonVictory().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setActionType(ActionType.SETASIDE).setCardResponsible(Cards.inheritance);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card lostArts_actionCardPileToHaveToken(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_lostArts_actionCardPileToHaveToken(context)) {
            return super.lostArts_actionCardPileToHaveToken(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .isAction().setCardResponsible(Cards.lostArts);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public Card pathfinding_actionCardPileToHaveToken(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_pathfinding_actionCardPileToHaveToken(context)) {
            return super.pathfinding_actionCardPileToHaveToken(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .isAction().setCardResponsible(Cards.pathfinding);
        return getFromTable(context, sco, true);
    }
    
    @Override
	public Card[] pilgrimage_cardsToGain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_pilgrimage_cardsToGain(context)) {
    		return super.pilgrimage_cardsToGain(context);
    	}
    	SelectCardOptions sco = new SelectCardOptions().setCount(3).setDifferent()
    			.setPassable().setPickType(PickType.SELECT)
    			.setCardResponsible(Cards.pilgrimage).setActionType(ActionType.GAIN);
		return getFromPlayed(context, sco);
	}
    
    @Override
    public Card plan_actionCardPileToHaveToken(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_plan_actionCardPileToHaveToken(context)) {
            return super.plan_actionCardPileToHaveToken(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .isAction().setCardResponsible(Cards.plan);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public QuestOption quest_chooseOption(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_quest_chooseOption(context)) {
            return super.quest_chooseOption(context);
        }
        QuestOption[] options = QuestOption.values();
        return options[selectOption(context, Cards.quest, options, null)];
    }
    
    @Override
    public Card quest_attackCardToDiscard(MoveContext context, Card[] attacks) {
    	if(context.isQuickPlay() && shouldAutoPlay_quest_attackCardToDiscard(context, attacks)) {
            return super.quest_attackCardToDiscard(context, attacks);
        }
        return attacks[selectOption(context, Cards.quest, attacks, null)];
    }
    
    @Override
    public Card[] quest_cardsToDiscard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_quest_cardsToDiscard(context)) {
            return super.quest_cardsToDiscard(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().setCount(6).exactCount()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.quest);
        return getFromHand(context, sco);
    }
    
    @Override
    public Card save_cardToSetAside(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_save_cardToSetAside(context)) {
            return super.save_cardToSetAside(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCardResponsible(Cards.save);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card scoutingParty_cardToDiscard(MoveContext context,  Card[] cards) {
        if(context.isQuickPlay() && shouldAutoPlay_scoutingParty_cardToDiscard(context)) {
            return super.scoutingParty_cardToDiscard(context, cards);
        }
        Object[] options = new Object[1 + cards.length];
        options[0] = ActionType.DISCARD;
        for (int i = 0; i < cards.length; i++) {
            options[i + 1] = cards[i];
        }
        return cards[selectOption(context, Cards.scoutingParty, options, null)];
    }
    
    @Override
    public Card seaway_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_seaway_cardToObtain(context)) {
            return super.seaway_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.seaway).isAction().setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card summon_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_summon_cardToObtain(context)) {
            return super.summon_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.summon).isAction().setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card[] trade_cardsToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_trade_cardsToTrash(context)) {
    		return super.trade_cardsToTrash(context);
		}
		SelectCardOptions sco = new SelectCardOptions().setCount(2)
			.setPassable().setPickType(PickType.TRASH)
			.setCardResponsible(Cards.trade).setActionType(ActionType.TRASH);
    	return getFromHand(context, sco);
	}

    @Override
    public Card training_actionCardPileToHaveToken(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_training_actionCardPileToHaveToken(context)) {
            return super.training_actionCardPileToHaveToken(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
                .isAction().setCardResponsible(Cards.training);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public Card trashingToken_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_trashingToken_cardToTrash(context)) {
            return super.trashingToken_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.plan);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public ExtraTurnOption extraTurn_chooseOption(MoveContext context, ExtraTurnOption[] options) {
    	if(context.isQuickPlay() && shouldAutoPlay_extraTurn_chooseOption(context, options)) {
            return super.extraTurn_chooseOption(context, options);
        }
        return options[selectOption(context, null, options, null)];
    }
    
    @Override
    public Card advance_actionToTrash(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().isAction()
                .setPassable().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.advance);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card advance_cardToObtain(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().maxCost(6).maxDebtCost(0).maxPotionCost(0).isAction()
                .setActionType(ActionType.GAIN).setCardResponsible(Cards.advance);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card annex_cardToKeepInDiscard(MoveContext context, Card[] cards, int cardsLeft) {
    	List<Object> options = new ArrayList<Object>();
    	options.add(cardsLeft);
        for (Card c : cards) {
            options.add(c);
        }
        options.add(null);
        return (Card) options.get(selectOption(context, Cards.annex, options.toArray(), null) + 1);
    }
    
    @Override
    public Card archive_cardIntoHand(MoveContext context, Card[] cards) {
    	return cards[selectOption(context, Cards.archive, cards, null)];
    }
    
    @Override
    public Card arena_cardToDiscard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_arena_cardToDiscard(context)) {
            return super.arena_cardToDiscard(context);
        }
    	SelectCardOptions sco = new SelectCardOptions().setPassable().isAction()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.arena);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card banquet_cardToObtain(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().maxCost(5).maxDebtCost(0).maxPotionCost(0).isNonVictory()
    			.setActionType(ActionType.GAIN).setCardResponsible(Cards.banquet);
        return getFromTable(context, sco);
    }
    
    @Override
    public boolean bustlingVillage_settlersIntoHand(MoveContext context, int coppers, int settlers) {
    	if(context.isQuickPlay() && shouldAutoPlay_bustlingVillage_settlersIntoHand(context)) {
            return super.bustlingVillage_settlersIntoHand(context, coppers, settlers);
        }
    	return selectBoolean(context, Cards.bustlingVillage);
    }

    @Override
    public Card catapult_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_catapult_cardToTrash(context)) {
            return super.catapult_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.catapult);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card[] catapult_attack_cardsToKeep(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_catapult_attack_cardsToKeep(context)) {
            return super.catapult_attack_cardsToKeep(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3).exactCount()
                .setPickType(PickType.KEEP).setCardResponsible(Cards.catapult);
        return getFromHand(context, sco);
    }
    
    @Override
    public CharmOption charm_chooseOption(MoveContext context) {
    	CharmOption[] options = CharmOption.values();
    	return options[selectOption(context, Cards.charm, options, null)];
    }
    
    @Override
    public Card charm_cardToObtain(MoveContext context, Card boughtCard) {
    	 SelectCardOptions sco = new SelectCardOptions()
    	 	.exactCost(boughtCard.getCost(context), boughtCard.getDebtCost(context), boughtCard.costPotion()?1:0)
    	 	.not(boughtCard).setPassable().setActionType(ActionType.GAIN).setCardResponsible(Cards.charm);
         return getFromTable(context, sco);
    }
    
    @Override
    public Card crown_actionToPlay(MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().isAction()
                .setPassable().setPickType(PickType.PLAY)
                .setCardResponsible(Cards.crown);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card crown_treasureToPlay(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().isTreasure()
                .setPassable().setPickType(PickType.PLAY)
                .setCardResponsible(Cards.crown);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card[] donate_cardsToTrash(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().setPassable()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.donate);
        return getFromHand(context, sco);
    }
    
    @Override
    public EncampmentOption encampment_chooseOption(MoveContext context, EncampmentOption[] options) {
    	if(context.isQuickPlay() && shouldAutoPlay_encampment_chooseOption(context, options)) {
            return super.encampment_chooseOption(context, options);
        }
    	return options[selectOption(context, Cards.encampment, options, null)];
    }
    
    @Override
    public Card engineer_cardToObtain(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_engineer_cardToObtain(context)) {
            return super.engineer_cardToObtain(context);
        }
        SelectCardOptions sco = new SelectCardOptions().maxCost(4).maxDebtCost(0).maxPotionCost(0)
                .setCardResponsible(Cards.engineer).setActionType(ActionType.GAIN);
        return getFromTable(context, sco);
    }
    
    @Override
    public boolean engineer_shouldTrashEngineerPlayed(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_engineer_shouldTrashEngineerPlayed(context)) {
            return super.engineer_shouldTrashEngineerPlayed(context);
        }
    	return selectBoolean(context, Cards.engineer);
    }

    @Override
    public Card[] hauntedCastle_gain_cardsToPutBackOnDeck(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_hauntedCastle_gain_cardsToPutBackOnDeck(context)) {
            return super.hauntedCastle_gain_cardsToPutBackOnDeck(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount().ordered()
                .setCardResponsible(Cards.hauntedCastle);
        return getFromHand(context, sco);
    }
    
    @Override
    public Card gladiator_revealedCard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_gladiator_revealedCard(context)) {
            return super.gladiator_revealedCard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setActionType(ActionType.REVEAL)
                .setCardResponsible(Cards.gladiator);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public boolean gladiator_revealCopy(MoveContext context, Player revealingPlayer, Card card) {
    	if(context.isQuickPlay() && shouldAutoPlay_gladiator_revealCopy(context, card)) {
            return super.gladiator_revealCopy(context, revealingPlayer, card);
        }
    	Object[] extras = new Object[3];
        extras[0] = revealingPlayer.getPlayerName();
        extras[1] = Cards.gladiator;
        extras[2] = card;
        return selectBoolean(context, Cards.gladiator, extras);
    }
    
    @Override
    public boolean legionary_revealGold(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_legionary_revealGold(context)) {
            return super.legionary_revealGold(context);
        }
    	return selectBoolean(context, Cards.legionary);
    }
    
    @Override
    public Card[] legionary_attack_cardsToKeep(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_legionary_attack_cardsToKeep(context)) {
            return super.legionary_attack_cardsToKeep(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(2).exactCount()
                .setPickType(PickType.KEEP).setCardResponsible(Cards.legionary);
        return getFromHand(context, sco);
    }
    
    @Override
    public int mountainPass_getBid(MoveContext context, Player highestBidder, int highestBid, int playersLeftToBid) {
    	Object[] options = new Object[42 - highestBid];
    	options[0] = highestBidder != null ? highestBidder.getPlayerName() : null;
    	options[1] = null;
    	for (int i = 2; i < options.length; ++i) {
    		options[i] = highestBid + i - 1;
    	}
    	int idx = selectOption(context, Cards.mountainPass, options, null) + 1;
        Integer bid = 0;
    	try {
            bid = (Integer) options[idx];
        } catch (ClassCastException e) {
            bid = 0;
        }
    	if (bid == null) bid = 0;
        return bid;
    }
    
    @Override
    public Card[] opulentCastle_cardsToDiscard(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_opulentCastle_cardsToDiscard(context)) {
            return super.opulentCastle_cardsToDiscard(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isVictory().setPassable()
                .setPickType(PickType.DISCARD).setActionType(ActionType.DISCARD)
                .setCardResponsible(Cards.opulentCastle)
				.setCount(getVictoryInHand().size());
        return getFromHand(context, sco);
    }
    
    @Override
    public Card overlord_actionCardToImpersonate(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_overlord_actionCardToImpersonate(context)) {
            return super.overlord_actionCardToImpersonate(context);
        }
        SelectCardOptions sco = new SelectCardOptions()
                .maxCost(5).maxDebtCost(0).maxPotionCost(0).isAction().isSupplyCard()
                .setCardResponsible(Cards.overlord);
        return getFromTable(context, sco);
    }
    
    @Override
    public Card ritual_cardToTrash(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.ritual);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card sacrifice_cardToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_sacrifice_cardToTrash(context)) {
            return super.sacrifice_cardToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions()
                .setPickType(PickType.TRASH).setActionType(ActionType.TRASH)
                .setCardResponsible(Cards.sacrifice);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public Card saltTheEarth_cardToTrash(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().isVictory()
                .isSupplyCard().setActionType(ActionType.TRASH).setPickType(PickType.TRASH)
                .setCardResponsible(Cards.saltTheEarth);
        return getFromTable(context, sco);
    }
    
    @Override
    public boolean settlers_copperIntoHand(MoveContext context, int coppers, int settlers) {
    	if(context.isQuickPlay() && shouldAutoPlay_settlers_copperIntoHand(context)) {
            return super.settlers_copperIntoHand(context, coppers, settlers);
        }
    	return selectBoolean(context, Cards.settlers);
    }

    @Override
    public boolean smallCastle_shouldTrashSmallCastlePlayed(MoveContext context, Card responsible) {
        if (context.isQuickPlay() && shouldAutoPlay_smallCastle__shouldTrashSmallCastlePlayed(context, responsible)) {
            return super.smallCastle_shouldTrashSmallCastlePlayed(context, responsible);
        }
        return selectBoolean(context, responsible.behaveAsCard());
    }

    @Override
    public Card smallCastle_castleToTrash(MoveContext context) {
        if (context.isQuickPlay() && shouldAutoPlay_smallCastle_castleToTrash(context)) {
            return super.smallCastle_castleToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().isCastle().setPickType(PickType.TRASH)
                .setActionType(ActionType.TRASH).setCardResponsible(Cards.smallCastle);
        return getCardFromHand(context, sco);
    }
    
    @Override
    public HuntingGroundsOption sprawlingCastle_chooseOption(MoveContext context) {
        if(context.isQuickPlay() && shouldAutoPlay_sprawlingCastle_chooseOption(context)) {
            return super.sprawlingCastle_chooseOption(context);
        }
    	HuntingGroundsOption[] options = HuntingGroundsOption.values();
        return options[selectOption(context, Cards.sprawlingCastle, options, null)];
    }
    
    @Override
    public Card tax_supplyToTax(MoveContext context) {
    	SelectCardOptions sco = new SelectCardOptions().applyOptionsToPile().allowEmpty()
            .setCardResponsible(Cards.tax);
        return getFromTable(context, sco, true);
    }
    
    @Override
    public Card[] temple_cardsToTrash(MoveContext context) {
    	if(context.isQuickPlay() && shouldAutoPlay_temple_cardsToTrash(context)) {
            return super.temple_cardsToTrash(context);
        }
        SelectCardOptions sco = new SelectCardOptions().setCount(3)
                .setMinCount(1).setDifferent().setPickType(PickType.TRASH)
                .setCardResponsible(Cards.temple).setActionType(ActionType.TRASH);
        return getFromHand(context, sco);
    }
    
    @Override
    public WildHuntOption wildHunt_chooseOption(MoveContext context) {
    	WildHuntOption[] options = WildHuntOption.values();
    	return options[selectOption(context, Cards.wildHunt, options, null)];
    }

    @Override
    public ArrayList<Integer> rabble_cardsToBanish(MoveContext context, int amount, String restriction) {
        /*
        if(context.isQuickPlay() && shouldAutoPlay_tournament_choosePrize(context)) {
                    return super.tournament_choosePrize(context);
        }
        */
        ArrayList<Integer> selectedCards = new ArrayList<>();
        ArrayList<Card> options = new ArrayList<Card>();
        Hashtable<Integer,Integer> inEnemyStack = new Hashtable<>();// noncrown index, Original index
        int nonCrownIndex = 0;
        for (int i=0; i< context.game.blackMarketPile.size(); i++) {
            Card c = context.game.blackMarketPile.get(i);
            if(!(c.is(CardType.Crown))){
                if (!c.is(restriction)) {
                    inEnemyStack.put(nonCrownIndex, i);
                    options.add(c);
                    nonCrownIndex++;
                }
            }
        }

        if (options.isEmpty()) {
            return null;
        }
        int returnedIndex = selectOption(context, Cards.goblinRabble, options.toArray(), null);
        selectedCards.add(returnedIndex);
        options.remove(returnedIndex);
        selectedCards.set(0,inEnemyStack.get(selectedCards.get(0)));
        if (options.size()>0) {
            int returnedIndex2 = selectOption(context, Cards.goblinRabble, options.toArray(), null);
            if (returnedIndex2>=returnedIndex)
                returnedIndex2++;
            selectedCards.add(inEnemyStack.get(returnedIndex2));
        }
        return selectedCards;
    }
}
