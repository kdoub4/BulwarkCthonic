package com.vdom.core;

import com.vdom.api.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PileCreator implements Serializable {
    public abstract CardPile create(Card template, int count);
}

class DefaultPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();
        cards.add(new CardPile.CardMultiplicity(template, count));
        return new CardPile(template, cards, true, true);
    }
}

class GoblinPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.goblinTroop,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.goblinArcher,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.goblinHeavy,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.goblinRabble,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.goblinAlchemist, 2));
        cards.add(new CardPile.CardMultiplicity(Cards.catapultBulwark,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.mgzwel,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kmblee,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kneena,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kmron,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.ghutz,    1));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}

class GatePileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.greatNorthGate,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.greatEastGate,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.greatWestGate,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.greatSouthGate,    1));

        return (new CardPile(template, cards, true, true));
    }
}

class MilitiaPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.terrifiedMilitia,    8));
        cards.add(new CardPile.CardMultiplicity(Cards.ralliedMilitia,    4));

        return (new CardPile(template, cards, true, true));
    }
}


class SpellwroughtPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        for (Card c : Cards.locationCardsSecretLoreSpellwrought) {
            cards.add(new CardPile.CardMultiplicity(c, 4));
        }

        return (new CardPile(template, cards, true, true));
    }
}


class ChorusPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        for (Card c : Cards.locationCardsSylvanChorus)
            cards.add(new CardPile.CardMultiplicity(c,    4));

        return (new CardPile(template, cards, true, true));
    }
}


class LizardPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.lizardTroop,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.lizardBombardier,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.lizardHeavy,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.lizardRabble,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.batteringRam, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.fireCatapult,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.yoonIseul,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kyung,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.giantCorpseHound,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.sang,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.hyeon,    1));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}
class elfPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.elfTroop,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.elfArcher,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.corpseVine,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.elfRabble,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.flameBallista, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.elfDruid,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.tane,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.xaphan,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kiri,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.vepor,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.ruiha,    1));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}

class winterPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.brokenCorpse,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.staleCorpse,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.freshCorpse,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.enshroudingMist,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.spectre, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.embalmingAcolyte,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.theLeftHand,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.theRightHand,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.theCatacombite,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.horrorOfFlesh,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.kangaxxTheLich,    1));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}

class messianicPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.brainwashedRabble,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.brainwashedTroop,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.footbowArcher,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.ensorcelledZealot,    5));
        cards.add(new CardPile.CardMultiplicity(Cards.rogueHumanMage, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.tuskedDeathcharger,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.heraldGranite,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.heraldPressure,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.heraldScorching,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.heraldStars,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.arcaneMeesiah,    1));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}

class WoundPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.seriousWound, 5));
        cards.add(new CardPile.CardMultiplicity(Cards.oldWound, 5));
        cards.add(new CardPile.CardMultiplicity(Cards.staggeringWound, 5));
        cards.add(new CardPile.CardMultiplicity(Cards.weakeningWound, 5));
        cards.add(new CardPile.CardMultiplicity(Cards.glancingWound, 5));
        cards.add(new CardPile.CardMultiplicity(Cards.fleshWound, 5));

        return (new CardPile(template, cards, false, false)).notInSupply();
    }
}


class HeroismPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.two,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.three, 1));

        return new CardPile(template, cards, true, true);
    }
}

class ZsuzsaBowPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.shortbow,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.crossbow, 2));

        return new CardPile(template, cards, true, true);
    }
}

class ZsuzsaDaggerPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.dualDaggers,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.nightDaggers, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.burialDaggers, 3));

        return new CardPile(template, cards, true, true);
    }
}

class JakabTomePileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.celestialTome,    2));
        cards.add(new CardPile.CardMultiplicity(Cards.celestialGrimoire, 2));

        return new CardPile(template, cards, true, true);
    }
}

class JakabSpellPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.fireball,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.wallOfForce, 1));
        cards.add(new CardPile.CardMultiplicity(Cards.celerity, 1));
        cards.add(new CardPile.CardMultiplicity(Cards.petrify,    1));
        cards.add(new CardPile.CardMultiplicity(Cards.augury, 1));
        cards.add(new CardPile.CardMultiplicity(Cards.enchantedStrike, 1));

        return new CardPile(template, cards, false, true);
    }
}

class JakabStaffPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();
        for (Card c : Cards.staffCardsJakab) {
            cards.add(new CardPile.CardMultiplicity(c,    3));
        }

        return new CardPile(template, cards, true, true);
    }
}
class WiolaBowPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.longbow,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.prestigeBow, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.enchantedBow, 3));

        return new CardPile(template, cards, true, true);
    }
}

class WiolaShotPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.pinpointShot,    4));
        cards.add(new CardPile.CardMultiplicity(Cards.comtempuousShot, 3));

        return new CardPile(template, cards, true, true);
    }
}

class SandorStaffPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.oakStaff,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.carvedStaff, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.ironShodStaff, 3));

        return new CardPile(template, cards, true, true);
    }
}

class SandorHealingPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.healingHerbs,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.healingBalm, 3));

        return new CardPile(template, cards, true, true);
    }
}


class ArpadShieldPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.shield,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.towerShield, 3));

        return new CardPile(template, cards, true, true);
    }
}
class ArpadSwordPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();

        cards.add(new CardPile.CardMultiplicity(Cards.mastersSword,    3));
        cards.add(new CardPile.CardMultiplicity(Cards.bloodstainedBlade, 3));
        cards.add(new CardPile.CardMultiplicity(Cards.hundredScimitar, 3));
        return new CardPile(template, cards, true, true);
    }
}


class CastlesPileCreator extends PileCreator {
    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();
        if (count != 8 && count != 12) {
            //TODO SPLITPILES What to do now?
            if (count < 8) count = 8;
            if (count > 8) count = 12;
        }

        cards.add(new CardPile.CardMultiplicity(Cards.humbleCastle,    count == 8 ? 1 : 2));
        cards.add(new CardPile.CardMultiplicity(Cards.crumblingCastle, 1));
        cards.add(new CardPile.CardMultiplicity(Cards.smallCastle,     count == 8 ? 1 : 2));
        cards.add(new CardPile.CardMultiplicity(Cards.hauntedCastle,   1));
        cards.add(new CardPile.CardMultiplicity(Cards.opulentCastle,   count == 8 ? 1 : 2));
        cards.add(new CardPile.CardMultiplicity(Cards.sprawlingCastle, 1));
        cards.add(new CardPile.CardMultiplicity(Cards.grandCastle,     1));
        cards.add(new CardPile.CardMultiplicity(Cards.kingsCastle,     count == 8 ? 1 : 2));

        return new CardPile(template, cards, true, true);
    }
}

class SplitPileCreator extends PileCreator {
    private Card topCard;
    private Card bottomCard;

    public SplitPileCreator(Card topCard, Card bottomCard) {
        this.topCard = topCard;
        this.bottomCard = bottomCard;
    }

    public CardPile create(Card template, int count) {
        List<CardPile.CardMultiplicity> cards = new ArrayList<CardPile.CardMultiplicity>();
        cards.add(new CardPile.CardMultiplicity(topCard, count / 2));
        cards.add(new CardPile.CardMultiplicity(bottomCard, count / 2 + (count % 2 == 1 ? 1 : 0))); //If count is not even put the extra card on bottom
        return new CardPile(template, cards, true, true);

    }
}