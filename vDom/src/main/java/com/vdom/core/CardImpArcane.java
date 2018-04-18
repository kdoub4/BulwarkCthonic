package com.vdom.core;

import com.vdom.comms.SelectCardOptions;

//test git
public class CardImpArcane extends CardImpl {
    private static final long serialVersionUID = 1L;

    public CardImpArcane(Builder builder) {
        super(builder);
    }

    protected CardImpArcane() {
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
            case CelestialTome:
                putOnTavern(game, context, currentPlayer);
                break;
            case Fireball:
                game.addToPile(currentPlayer.playedCards.removeCard(this), true);
                actionPhaseAttack(context, currentPlayer, true, true,
                        1 + spendActions(context, currentPlayer, 2));
                break;
        }
    }

    @Override
    protected void callAction(MoveContext context, Player currentPlayer) {
        switch (this.getKind()) {
        }
    }
}
