package com.vdom.core;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.comms.SelectCardOptions;

import java.util.ArrayList;

//test git
public class CardImplArcane extends CardImpl {
    private static final long serialVersionUID = 1L;

    public CardImplArcane(Builder builder) {
        super(builder);
    }

    protected CardImplArcane() {
    }

    @Override
    protected void manoeuvreCardActions(Game game, MoveContext context, Player currentPlayer) {
        SelectCardOptions sco;
        switch (this.getKind()) {
            case StarChamber:
                if (((IndirectPlayer)currentPlayer).selectBoolean(context, this)) {
                    if (numberTimesAlreadyPlayed == 0) {
                        numberTimesAlreadyPlayed++;
                        sco = new SelectCardOptions().setPickType(SelectCardOptions.PickType.SELECT)
                                .setActionType(SelectCardOptions.ActionType.UNDER).setCount(1).exactCount()
                                .setCardResponsible(this);
                        putCardUnderFromHand(game, context, currentPlayer, sco);
                    }
                }
                else {
                    for (Card c : cardsUnder) {
                        currentPlayer.trash(c,this,context);
                        currentPlayer.getTavern().remove(c);
                    }
                    this.getCardsUnder().clear();
                    currentPlayer.trash(this,this,context);
                    currentPlayer.getTavern().remove(this);
                }
                break;
            case Augury:
                int spentActions = spendActions(context, currentPlayer, 3, false);
                if (spentActions==0) return;
                ArrayList<Card> cardsReveal = new ArrayList<>();
                for (int i=0; i<spentActions; i++) {
                    cardsReveal.add(game.draw(context, this, spentActions-i));
                }
                cardsReveal.add(null);
                int selection = ((RemotePlayer)currentPlayer).selectOption(context, this, cardsReveal.toArray(), null);
                if (selection != cardsReveal.size()-1) {
                    currentPlayer.putOnTopOfDeck(cardsReveal.remove(selection), context, true);
                }
                for (Card c : cardsReveal) {
                    if (c!=null)
                        currentPlayer.discard(c, this, context);
                }
                currentPlayer.banish(currentPlayer.hand.removeCard(this), this, context);
                break;
        }
    }
    @Override
    protected void additionalCardActions(Game game, MoveContext context, Player currentPlayer) {
        SelectCardOptions sco;
        switch (this.getKind()) {
            case SpellwroughtHammer:
                this.discardMultiple(context, currentPlayer, 1);
                actionPhaseAttack(context, currentPlayer, true, false, 2);
                break;
            case SpellwroughtArrow:
                this.discardMultiple(context, currentPlayer, 1);
                actionPhaseAttack(context, currentPlayer, true, true, 1);
                break;
            case MagistersCouncil:
                for (Player p : game.getPlayersInTurnOrder())
                    if (p != currentPlayer) {
                        game.drawToHand(new MoveContext(game, p), this, 1);
                    }
                break;
            case RejuvenationCircle:
                for (Player p : game.getPlayersInTurnOrder()) {
                    //TODO make optional
                    summon(game, context, p, CardType.Wound);
                }
                for (Player p : game.getPlayersInTurnOrder()) {
                    Card[] toTrash = p.wound_cardsToTrash(context, this, 1);
                    if (toTrash != null && toTrash.length > 0) {
                        currentPlayer.hand.remove(toTrash[0]);
                        currentPlayer.banish(toTrash[0],this,context);
                    }
                }
                break;
            case SpiralLibrary:
                hunt(game, context, currentPlayer, CardType.Spell);
                break;
            case StarChamber: 
                putOnTavern(game, context, currentPlayer);
                sco = new SelectCardOptions().setPickType(SelectCardOptions.PickType.SELECT)
                        .setActionType(SelectCardOptions.ActionType.UNDER).setCount(1).exactCount()
                        .setCardResponsible(this);
                putCardUnderFromHand(game, context, currentPlayer, sco);
                break;
            case RunicStaff:
            case CedarStaff:
            case AshStaff:
                context.spellBuys++;
                break;
            case CrystalOrb:
                spyAndScryingPool(game, context, currentPlayer);
                game.drawToHand(context, this, 1);
                break;
            case CelestialGrimoire:
                sco = new SelectCardOptions().setPickType(SelectCardOptions.PickType.SELECT)
                        .setActionType(SelectCardOptions.ActionType.UNDER).isSpell()
                        .setCardResponsible(this).setCount(1).setPassable().exactCount();
                putCardUnderFromHand(game, context, currentPlayer, sco);
                break;
            case Petrify:
                putOnTavern(game, context, currentPlayer);
                sco = new SelectCardOptions().setPickType(SelectCardOptions.PickType.SELECT)
                        .fromBlackMarket().setCount(1).isSelect().setCardResponsible(this);
                int[] enemy = currentPlayer.doSelectFoe(context,sco,1, GameEvent.EventType.SelectFoe);
                if (enemy.length==1) {
                    Card enemyCard =  game.blackMarketPile.get(enemy[0]);
                    this.cardsUnder.add(enemyCard);
                    int extraTurns = spendActions(context, currentPlayer, 2, false);
                    for (int i = 1; i <= extraTurns; i++) {
                        this.cardsUnder.add(currentPlayer.takeFromPile(Cards.virtualWound));
                        currentPlayer.tavern.add(cardsUnder.get(cardsUnder.size()-1));
                    }
                }
                break;
            case WallOfForce:
                if (spendActions(context, currentPlayer, 2, true) == 2)
                    context.invincible = true;
            case AstrologersRitual:
            case CelestialTome:
            case EnchantedStrike:
                putOnTavern(game, context, currentPlayer);
                break;
            case Fireball:
                currentPlayer.banish(currentPlayer.playedCards.removeCard(this), this, context);
                actionPhaseAttack(context, currentPlayer, true, true,
                        1 + spendActions(context, currentPlayer, 2, false));
                break;
            case Celerity:
                if (context.countCardsInPlay(Cards.celerity) > 1)
                    game.consecutiveTurnCounter = 0;
                break;
            case Augury:
                Card card1 = game.getPile(Cards.virtualEnemy).removeCard();
                Card card2 = game.getPile(Cards.virtualEnemy).removeCard();
                Card[] options=new Card[]{card1, card2};
                int selection = ((RemotePlayer)currentPlayer).selectOption(context, this, options, null);
                Card toTrash = null;
                Card toReturn = null;
                    if (selection == 0) {
                        toTrash = card1;
                        toReturn = card2;
                    } else {
                        toTrash = card2;
                        toReturn = card1;
                    }
                    if (!toTrash.is(CardType.Crown)) currentPlayer.banish(toTrash, this, context);
                    else game.addToPile(toTrash, false);
                    game.addToPile(toReturn, false);
                currentPlayer.banish(currentPlayer.playedCards.removeCard(this), this, context);
                break;
        }
    }

    private void summon(Game game, MoveContext context, Player p, CardType... types) {
        Card topCard;
        if (p.deck.size()>0) {
            topCard = game.draw(context, this, p.deck.size());
            do {
                if (topCard.is(types)) {
                    p.hand.add(topCard, true);
                    break;
                }
                else {
                    p.discard.add(topCard);
                    if (p.deck.size() > 0)
                        topCard = game.draw(context, this, p.deck.size());
                }
            } while (p.deck.size() > 0);
        }
    }

    @Override
    protected void callAction(MoveContext context, Player currentPlayer) {
        switch (this.getKind()) {
            case SpiralLibrary:
                currentPlayer.discardRemainingCardsFromHand(context, null, this, 0);

                for (int i = 5; i>0; i--) {
                    context.game.drawToHand(context, this, i);
                }
                break;
            case WallOfForce:
                currentPlayer.banish(currentPlayer.playedCards.removeCard(this), this, context);
                break;
            case CelestialGrimoire:
                for (Card c : this.cardsUnder) {
                    currentPlayer.hand.add(c);
                    currentPlayer.tavern.removeCard(c);
                }
                this.cardsUnder.clear();
            case CelestialTome:
                context.actions++;
                currentPlayer.banish(currentPlayer.playedCards.removeCard(this), this, context);
                break;
        }
    }
}
