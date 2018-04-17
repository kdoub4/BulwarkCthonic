package com.vdom.core;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.comms.SelectCardOptions;

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
        switch (this.getKind()) {
        }
    }
    @Override
    protected void additionalCardActions(Game game, MoveContext context, Player currentPlayer) {
        switch (this.getKind()) {
            case CrystalOrb:
                spyAndScryingPool(game, context, currentPlayer);
                game.drawToHand(context, this, 1);
                break;
            case CelestialGrimoire:
                SelectCardOptions sco = new SelectCardOptions().setPickType(SelectCardOptions.PickType.SELECT)
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
                    for (int i = 1; i <= spendActions(context, currentPlayer, 2); i++) {
                        this.cardsUnder.add(currentPlayer.takeFromPile(Cards.virtualWound));
                        currentPlayer.tavern.add(cardsUnder.get(cardsUnder.size()-1));
                    }
                }
                break;
            case CelestialTome:
            case EnchantedStrike:
            case WallOfForce:
                putOnTavern(game, context, currentPlayer);
                break;
            case Fireball:
                game.addToPile(currentPlayer.playedCards.removeCard(this), true);
                actionPhaseAttack(context, currentPlayer, true, true,
                        1 + spendActions(context, currentPlayer, 2));
                break;
            case Celerity:
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
                    if (!toTrash.is(CardType.Crown)) game.addToPile(toTrash, true);
                    else game.addToPile(toTrash, false);
                    game.addToPile(toReturn, false);
                
                break;
        }
    }

    @Override
    protected void callAction(MoveContext context, Player currentPlayer) {
        switch (this.getKind()) {
        }
    }
}
