package com.vdom.core;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;

import com.vdom.api.Card;
import com.vdom.api.CardCostComparator;
import com.vdom.api.EnemyType;
import com.vdom.api.FrameworkEvent;
import com.vdom.api.FrameworkEventHelper;
import com.vdom.api.GameEvent;
import com.vdom.api.GameEvent.EventType;
import com.vdom.api.GameEventListener;
import com.vdom.api.GameType;
import com.vdom.api.HeroType;
import com.vdom.api.LocationType;
import com.vdom.comms.SelectCardOptions;
import com.vdom.core.MoveContext.TurnPhase;
import com.vdom.core.Player.ExtraTurnOption;
import com.vdom.core.Player.WatchTowerOption;

public class Game {
    public static boolean junit = false;
    public static boolean debug = true;
    public static Integer cardSequence = 1;
    public static HashMap<String, Double> GAME_TYPE_WINS = new HashMap<String, Double>();

    public static HashMap<String, Integer> winStats = new HashMap<String, Integer>();
    public static final String QUICK_PLAY = "(QuickPlay)";
    public static final String BANE = "bane+";
    public static final String OBELISK = "obelisk+";
    public static final String BLACKMARKET = "blackMarket+";

    public static String[] cardsSpecifiedAtLaunch;
    public static ArrayList<String> unfoundCards = new ArrayList<String>();
    String cardListText = "";
    String unfoundCardText = "";
    int totalCardCountGameBegin = 0;
    int totalCardCountGameEnd = 0;

    public static int startPhaseLength = 3;
    private boolean hasTroop = false;
    private int archers = 0;

    public static EnemyType mEnemy;
    public static LocationType mLocation;
    public static HeroType mPlayer1Hero;

    public boolean woundsInHand = false;
    public boolean preventDefense = false;


    /**
     * Player classes and optionally the url to the jar on the web that the class is located in. Player class
     * is in index [0] of the array and the jar is in index [1]. Index [1] is null (but still there) if the
     * default class loader should be used.
     */
    static ArrayList<String[]> playerClassesAndJars = new ArrayList<String[]>();
    /**
     * The card set to use for the game.
     *
     * @see com.vdom.api.GameType
     */
    public static GameType gameType = GameType.Random;
    public static List<Expansion> randomExpansions = null;
    public static List<Expansion> randomExcludedExpansions = null;
    public static String gameTypeStr = null;
    public static boolean showUsage = false;

    public static boolean sheltersNotPassedIn = false;
    public static boolean sheltersPassedIn = false;
    public static boolean platColonyNotPassedIn = false;
    public static boolean platColonyPassedIn = false;
    public static double chanceForPlatColony = -1;
    public static double chanceForShelters = 0.0;

    public static enum BlackMarketSplitPileOptions {
    	NONE, ONE, ANY, ALL
    }

    public static int blackMarketCount = 25;
    public static BlackMarketSplitPileOptions blackMarketSplitPileOptions = BlackMarketSplitPileOptions.NONE;
    public static boolean blackMarketOnlyCardsFromUsedExpansions = false;
    
    public static boolean randomIncludesEvents = false;
    public static int numRandomEvents = 0;
    public static boolean randomIncludesLandmarks = false;
    public static int numRandomLandmarks = 0;
    public static boolean splitMaxEventsAndLandmarks = true;

    public static boolean quickPlay = false;
    public static boolean sortCards = false;
    public static boolean actionChains = false;
    public static boolean suppressRedundantReactions = false;
    public static boolean equalStartHands = false;
    public static boolean errataMasqueradeAlwaysAffects = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataMineForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataMoneylenderForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataPossessedTakesTokens = false; //Errata introduced May 2016 - true enables old behavior
    public static boolean errataThroneRoomForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataShuffleDeckEmptyOnly = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean startGuildsCoinTokens = false; //only for testing
    public static boolean lessProvinces = false; //only for testing
    public static boolean noCards = false; //only for testing
    public static boolean godMode = false; //only for testing
    public static boolean disableAi = false; //only for testing
    public static boolean controlAi = false; //only for testing
    public static boolean maskPlayerNames = false;

    public static final HashSet<GameEvent.EventType> showEvents = new HashSet<GameEvent.EventType>();
    public static final HashSet<String> showPlayers = new HashSet<String>();
    static boolean test = true;
    static boolean ignoreAllPlayerErrors = false;
    static boolean ignoreSomePlayerErrors = false;
    static HashSet<String> ignoreList = new HashSet<String>();

    static ArrayList<GameStats> gameTypeStats = new ArrayList<GameStats>();

    static int numGames = -1;
    static int gameCounter = 0;
    public ArrayList<GameEventListener> listeners = new ArrayList<GameEventListener>();
    public GameEventListener gameListener;
    static boolean forceDownload = false;
    static HashMap<String, Double> overallWins = new HashMap<String, Double>();

    public static Random rand = new Random(System.currentTimeMillis());
    public HashMap<String, CardPile> piles = new HashMap<String, CardPile>();
    public HashMap<String, CardPile> placeholderPiles = new HashMap<String, CardPile>();
    public HashMap<String, Integer> embargos = new HashMap<String, Integer>();
    public HashMap<String, Integer> pileVpTokens = new HashMap<String, Integer>();
    public HashMap<String, Integer> pileDebtTokens = new HashMap<String, Integer>();
    private HashMap<String, HashMap<Player, List<PlayerSupplyToken>>> playerSupplyTokens = new HashMap<String, HashMap<Player,List<PlayerSupplyToken>>>();
    public ArrayList<Card> trashPile = new ArrayList<Card>();
    public ArrayList<Card> possessedTrashPile = new ArrayList<Card>();
    public ArrayList<Card> possessedBoughtPile = new ArrayList<Card>();
    public ArrayList<Card> blackMarketPile = new ArrayList<>();
    public ArrayList<Card> blackMarketPileShuffled = new ArrayList<Card>();

    public int tradeRouteValue = 0;
    public Card baneCard = null;
    public Card obeliskCard = null;
    public boolean firstProvinceWasGained = false;
    public boolean doMountainPassAfterThisTurn = false;
    public int firstProvinceGainedBy;

    public boolean bakerInPlay = false;
    public boolean journeyTokenInPlay = false;

    private static final int kingdomCardPileSize = 10;
    public static int victoryCardPileSize = 12;

    ArrayList<Card>[] cardsObtainedLastTurn;
    static int playersTurn;

    //    public UI ui;
    int turnCount = 0;
    int consecutiveTurnCounter = 0;

    public static HashMap<String, Player> cachedPlayers = new HashMap<String, Player>();
    public static HashMap<String, Class<?>> cachedPlayerClasses = new HashMap<String, Class<?>>();
    public static Player[] players;

    public boolean sheltersInPlay = false;
    public boolean bulwarkInPlay = true;

    public int possessionsToProcess = 0;
    public Player possessingPlayer = null;
    public int nextPossessionsToProcess = 0;
    public Player nextPossessingPlayer = null;

    public static int numPlayers;
    boolean gameOver = false;

    private static HashMap<String, Player> playerCache = new HashMap<String, Player>();

    public static void main(String[] args) {
        try {
            go(args, false);
        } catch (ExitException e) {
            // This is what we would correctly need to do.
            // May break some code though which relies on vdom being less strict
            System.exit(-1);
        }
    }

    public static void go(String[] args, boolean html) throws ExitException {

        /*
         * Don't catch ExitException here. If someone throws an ExitException, it means the game is over, which should be handled by whoever owns us.
         */

        processArgs(args);

        Util.debug("");

        // Start game(s)
        if (gameTypeStr != null) {
            gameType = GameType.valueOf(gameTypeStr);            
            new Game().start();
        } else {
            for (String[] className : playerClassesAndJars) {
                overallWins.put(className[0], 0.0);
            }

            for (GameType p : GameType.values()) {
                gameType = p;
                new Game().start();
            }

            if (test) {
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomBaseGame;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomIntrigue;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomSeaside;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomDarkAges;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.Random;
                    new Game().start();
                }
            }
            if (!debug && !test) {
                Util.log("----------------------------------------------------");
            }
            printStats(overallWins, numGames * GameType.values().length, "Total");
            printStats(GAME_TYPE_WINS, GameType.values().length, "Types");
        }
        if (test) {
            printGameTypeStats();
        }


        FrameworkEvent frameworkEvent = new FrameworkEvent(FrameworkEvent.Type.AllDone);
        FrameworkEventHelper.broadcastEvent(frameworkEvent);
    }
    
    private static class ExtraTurnInfo {
		public ExtraTurnInfo() {}
		public ExtraTurnInfo(boolean canBuyCards) {
			this.canBuyCards = canBuyCards;
		}
		public boolean canBuyCards = true;
	}

    void start() throws ExitException {
        HashMap<String, Double> gameTypeSpecificWins = new HashMap<String, Double>();

        for (String[] className : playerClassesAndJars) {
            gameTypeSpecificWins.put(className[0], 0.0);
            if (!GAME_TYPE_WINS.containsKey(className[0])) {
                GAME_TYPE_WINS.put(className[0], 0.0);
            }
        }

        long turnCountTotal = 0;
        long vpTotal = 0;
        long numCardsTotal = 0;

        // if (test) {
        // System.out.print(gameType + " ");
        // }
        for (int gameCount = 0; gameCount < numGames; gameCount++) {
            // if (test) {
            // System.out.print(gameCount + " ");
            // System.out.flush();
            // }
            Util.debug("---------------------", false);

            Util.debug("New Game:" + gameType);
            initGameBoard();
            if (test) {
                Util.log(gameType.toString());
                totalCardCountGameBegin = totalCardCount();
            }

            gameOver = false;
            gameCounter++;
            playersTurn = 0;
            turnCount = 1;
            Util.log("Turn " + turnCount);
            
            Queue<ExtraTurnInfo> extraTurnsInfo = new LinkedList<ExtraTurnInfo>();

            /*
            CardPile enemyPile = getPile(Cards.virtualEnemy);
            ArrayList<Card> tempList = new ArrayList<>();
            while (tempList.size()!=4)
            for (int i=0;i<enemyPile.cards.size();i++) {
                Card c = enemyPile.cards.get(i);
                if (c.getKind() == Cards.Kind.HeavyGoblin && (tempList.size()==1 || tempList.size()==3))
                    tempList.add(enemyPile.cards.remove(i));
                else if (c.getKind() == Cards.Kind.RabbleGoblin && (tempList.size()==0))
                    tempList.add(enemyPile.cards.remove(i));
                else if (c.getKind() == Cards.Kind.KneenaGoblin && (tempList.size()==2))
                    tempList.add(enemyPile.cards.remove(i));
            }
            for (int i=0;i<tempList.size();i++) {
                enemyPile.addCard(tempList.remove(i));
                i--;
            }
*/
            while (!gameOver) {
                Player player = players[playersTurn];
                boolean canBuyCards = extraTurnsInfo.isEmpty() ? true : extraTurnsInfo.remove().canBuyCards;
                MoveContext context = new MoveContext(this, player, canBuyCards);
                context.phase = TurnPhase.Action;
                context.startOfTurn = true;
                while (player.tavern.contains(Cards.wallOfForce)) {
                    player.tavern.get(Cards.wallOfForce).callAtStartOfTurn(context);
                }
                playerBeginTurn(player, context);

                do {
                    if (godMode && !player.isAi())
                    {
                        context.buys = 999;
                        if (placeholderPiles.containsKey(Cards.potion.getName())) {
                            context.potions = 999;
                        }
                        context.addCoins(999);
                        context.actions = 999;
                    }

//TODO Check for death, if dying force flesh wound play if possible
                    playerManoeuvres(player, context);

                    context.startOfTurn = false;

                    context.phase = TurnPhase.DrawFoe;
                    // /////////////////////////////////
                    // Draw Foe
                    // /////////////////////////////////
                    if ((turnCount > startPhaseLength) &&
                        pileSize(Cards.virtualEnemy) > 0 &&
                        !player.playedCards.contains(Cards.celerity))
                            drawFoe(player, context, true);



                	context.phase = TurnPhase.Action;
                	context.returnToActionPhase = false;
                	
                    // /////////////////////////////////
                    // Actions
                    // /////////////////////////////////
	                playerAction(player, context);

                    playerManoeuvres(player, context);

                    // /////////////////////////////////
	                // Buy Phase
	                // /////////////////////////////////
	                context.phase = TurnPhase.Buy;
	                playerBeginBuy(player, context);
	                playTreasures(player, context, -1, null);
	
	                // Spend Guilds coin tokens if applicable
	                playGuildsTokens(player, context);
	
	                // /////////////////////////////////
	                // Buying cards
	                // /////////////////////////////////
                    while (player.inHand(Cards.Kind.FleshWound  ) && context.getCoins()>=3) {
                        if (player.vassal_shouldPlayCard(context, Cards.fleshWound)) {
                            Card fleshWoundCard = player.fromHand(Cards.fleshWound);
                            player.banish(player.hand.removeCard(fleshWoundCard, true), fleshWoundCard ,context);
                            context.spendCoins(3);
                        }
                        else
                            break;
                    }
                    playerBuy(player, context);
	                while (player.inHand(Cards.Kind.FleshWound  ) && context.getCoins()>=3) {
                        if (player.vassal_shouldPlayCard(context, Cards.fleshWound)) {
                            Card fleshWoundCard = player.fromHand(Cards.fleshWound);
                            player.banish(player.hand.removeCard(fleshWoundCard, true), fleshWoundCard ,context);
                            context.spendCoins(3);
                        }
                        else
                            break;
                    }

                    //TODO attack phase
                    // /////////////////////////////////
                    // Attack foes
                    // /////////////////////////////////
                    // Like Buy but using Might and from Black Market
                    context.phase = TurnPhase.Attack;
                    playerAttackFoe(player, context);
                    if (!context.attackMade) {
                        Card c = player.cardToPlay(context, Util.canReact(context, player, CardType.AttackStepEnd), Cards.weakeningWound, true, ""); //TODO
                        while (c != null) {
                            //ask if play? yes break;
                            //if (player.controlPlayer.vassal_shouldPlayCard(context, c)) {
                                switch (c.getKind()) {
                                    case WeakeningWound:
                                        player.hand.removeCard(c);
                                        player.banish(c, c, context);
                                }
                            //}
                            c = player.cardToPlay(context, Util.canReact(context, player, CardType.AttackStepEnd), Cards.weakeningWound, true, ""); //TODO
                        }
                    }
                } while (context.returnToActionPhase);
                
                if (context.totalCardsBoughtThisTurn + context.totalEventsBoughtThisTurn == 0) {
                    GameEvent event = new GameEvent(GameEvent.EventType.NoBuy, context);
                    broadcastEvent(event);
                    Util.debug(player.getPlayerName() + " did not buy a card with coins:" + context.getCoinAvailableForBuy());
                }

                // /////////////////////////////////
                // Discard, draw new hand
                // /////////////////////////////////
                context.phase = TurnPhase.CleanUp;
                player.cleanup(context);
                
                //clean up other players cards in play without future duration effects, e.g. Duplicate
                for (Player otherPlayer : getPlayersInTurnOrder()) {
                	if (otherPlayer != player) {
                		otherPlayer.cleanupOutOfTurn(new MoveContext(this, otherPlayer));
                	}
                }

                extraTurnsInfo.addAll(playerEndTurn(player, context));
                gameOver = false;                //checkGameOver();

                //bulwark testing
                //if (!gameOver) {
                {
                	playerAfterTurn(player, context);
                	if (player.isControlled()) {
                		player.stopBeingControlled();
                	}
                    setPlayersTurn(!extraTurnsInfo.isEmpty());
                }
                //TODO Foes
                context.phase = TurnPhase.Foes;
                enemyAttack(player, context);
                if (player.tavern.contains(Cards.vantagePoint) && context.meleeMade) {
                    //Card[] options = new Card[]{player.tavern.get(Cards.vantagePoint),null};
                    //if (player.call_whenTurnStartCardToCall(context, options)!=null){
                        player.discard(player.tavern.removeCard(player.tavern.get(Cards.vantagePoint)), Cards.vantagePoint, context);
                    //}
                }

                //End of turn
                for (Card c : player.tavern) {
                    c.setPlayedThisTurn(false);
                    ((CardImpl)c).numberTimesAlreadyPlayed=0;
                }

            }

            // unmask players
            maskPlayerNames = false;
            int vps[] = gameOver(gameTypeSpecificWins);
            if (test) {
                // Compute game stats
                turnCountTotal += turnCount;
                for (int i = 0; i < vps.length; i++) {
                    vpTotal += vps[i];
                    numCardsTotal += players[i].getAllCards().size();
                }
                totalCardCountGameEnd = totalCardCount();
                assert (totalCardCountGameBegin == totalCardCountGameEnd);
            }

        }

        // Java program ending
        if (!debug) {
            markWinner(gameTypeSpecificWins);
            printStats(gameTypeSpecificWins, numGames, gameType.toString());

            // Util.log("---------------------");
        }

        if (test) {
            // System.out.println();
            ArrayList<Card> gameCards = new ArrayList<Card>();
            for (CardPile pile : piles.values()) {
                Card card = pile.placeholderCard();
                if (!card.equals(Cards.copper) && !card.equals(Cards.silver) && !card.equals(Cards.gold) && !card.equals(Cards.platinum)
                    && !card.equals(Cards.estate) && !card.equals(Cards.duchy) && !card.equals(Cards.province) && !card.equals(Cards.colony)
                    && !card.equals(Cards.curse)) {
                    gameCards.add(card);
                }
            }

            GameStats stats = new GameStats();
            stats.gameType = gameType;
            stats.cards = gameCards.toArray(new Card[0]);
            stats.aveTurns = (int) (turnCountTotal / numGames);
            stats.aveNumCards = (int) (numCardsTotal / (numGames * numPlayers));
            stats.aveVictoryPoints = (int) (vpTotal / (numGames * numPlayers));

            gameTypeStats.add(stats);
        }

        FrameworkEvent frameworkEvent = new FrameworkEvent(FrameworkEvent.Type.GameTypeOver);
        frameworkEvent.setGameType(gameType);
        frameworkEvent.setGameTypeWins(gameTypeSpecificWins);
        FrameworkEventHelper.broadcastEvent(frameworkEvent);
    }

    private void playerManoeuvres(Player player, MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().isActionPhase().isManoeuvre().setPassable()
            .setPickType(SelectCardOptions.PickType.MINT);
        if (context.startOfTurn) sco.except = Cards.sneak;
        Card card;
        do {
            card = player.getCardFromHand(context, sco);
            if (card == null) break;
            switch (card.getKind()) {
                //TODO treetopSire multi player
                //TODO refactor out
                case RalliedMilitia:
                    ((CardImpl)card).manoeuvreCardActions(context.game,context,player);
                    break;
                case Sneak:
                    addToPile(player.hand.removeCard(card, true), true);
                    context.invincible = true;
                    break;
                case SanctusCharm:
                    SanctusCharmManoeuvre(context, player, card);
                    break;
                case OldWound:
                    OldWoundManoeuvre(context, player, card );
                    break;
                case GlancingWound:
                    context.actions -= 2;
                    player.banish(player.hand.removeCard(card), card, context);
                    break;
            }
            sco.allowedCards.clear();
        } while (true);
    }

    protected void drawFoe(Player player, MoveContext context, boolean useWhenDrawn) {
        Card cardDrawn = takeFromPile(Cards.virtualEnemy);
        context.game.blackMarketPile.add(context.game.blackMarketPile.size(), cardDrawn);

        if (pileSize(Cards.virtualEnemy) == 0) {
            for (int i=0; i < this.trashPile.size(); i++) {
                Card c = this.trashPile.get(i);
                if (c.is(CardType.Enemy)) {
                    if (c.is(CardType.Crown)) {
                        this.possessedBoughtPile.add(c);
                    }
                    else {
                        addToPile(c,false);
                    }
                    this.trashPile.remove(c);
                    i--;
                }
            }
            Collections.shuffle(this.possessedBoughtPile);
            Collections.shuffle(getPile(Cards.virtualEnemy).cards);
            while (!this.possessedBoughtPile.isEmpty()) {
                addToPile(this.possessedBoughtPile.remove(0),false);
            }
        }

        if (cardDrawn.is(CardType.WhenDrawn) && useWhenDrawn) {
            enemyWhenDrawn(player, context, cardDrawn);
        }
    }
    
    public int takeWounds(Player currentPlayer, int amount, MoveContext context, Card attacker, boolean inHand) {
        boolean defended = false;
        int woundsTakenHere = 0;
        for (int i=1; i <= amount; i++) {
            defended = false;
            if (!context.game.preventDefense) {
                for (Player p : context.game.getPlayersInTurnOrder()) {
                    if (currentPlayer.tavern.contains(Cards.vantagePoint) && context.game.blackMarketPile.size() < 10) {
                        defended = true;
                    }
                    if (!defended && !context.invincible) {
                        Card defendCard = currentPlayer.cardToPlay(context,
                                Util.canReact(context, p, CardType.Defend), attacker,
                                true, IndirectPlayer.OPTION_DEFEND);
                        if (defendCard != null) {
                            switch (defendCard.getKind()) {
                                case WallOfLightning:
                                    SelectCardOptions sco = new SelectCardOptions().fromHand()
                                            .setActionType(SelectCardOptions.ActionType.DISCARD).setCardResponsible(defendCard);
                                    Card toDiscard = p.getCardFromHand(context, sco);
                                    if (toDiscard != null) {
                                        currentPlayer.discard(p.hand.removeCard(toDiscard), defendCard, context);
                                        currentPlayer.discard(p.getTavern().removeCard(defendCard), defendCard, context);
                                        defended = true;
                                    }
                                    break;
                                case Sneak:
                                    currentPlayer.banish(defendCard, defendCard, context);
                                    p.hand.remove(defendCard);
                                    context.invincible = true;
                                    break;
                                case SanctusCharm:
                                    SanctusCharmManoeuvre(context, p, defendCard);
                                    break;
                                case Shield:
                                    p.discard(defendCard, defendCard, context);
                                    p.hand.removeCard(defendCard);
                                    defended = true;
                                    break;
                                case StoneWalls:
                                    p.tavern.a.remove(defendCard);
                                    p.trash(defendCard, defendCard, context);
                                    defended = true;
                                    break;
                                case TowerShield:
                                    p.tavern.a.remove(defendCard);
                                    p.discard(defendCard, defendCard, context);
                                    defended = true;
                                    break;
                            }
                        }
                    }
                }
                if ((context.sanctusCharm || currentPlayer.tavern.contains(Cards.wallOfForce)) && context.woundsTaken >= 1)
                    defended = true;
            }
            if (!defended && (!context.invincible || context.game.preventDefense) ) {
                Card gained = currentPlayer.gainNewCard(Cards.virtualWound, attacker, context); //.add(takeFromPile(Cards.virtualWound));
                if (inHand || context.game.woundsInHand) {
                    currentPlayer.discard.remove(gained);
                    currentPlayer.hand.add(gained);
                } else if (currentPlayer.tavern.contains(Cards.citadelWalls)){
                    CardImpl walls = (CardImpl) currentPlayer.tavern.get(Cards.citadelWalls);
                    currentPlayer.discard.remove(gained);
                    walls.cardsUnder.add(gained);
                    currentPlayer.tavern.add(gained);
                    GameEvent event = new GameEvent(GameEvent.EventType.CardSetAsideOnTavernMat, context);
                    event.card = gained.getControlCard();
                    context.game.broadcastEvent(event);

                    if (walls.cardsUnder.size()==3) {
                        currentPlayer.tavern.remove(walls);
                        for (Card c : walls.cardsUnder)
                        {
                            currentPlayer.tavern.removeCard(c);
                            currentPlayer.banish(c, walls, context);
                        }
                        walls.cardsUnder.clear();
                        currentPlayer.trash(walls,walls,context);
                    }

                }
                while (currentPlayer.getTavern().contains(Cards.starChamber)) {
                    Card starChamb = currentPlayer.getTavern().get(Cards.starChamber);
                    for (Card c : starChamb.getCardsUnder()) {
                        getCurrentPlayer().discard(c, attacker, context);
                        currentPlayer.getTavern().remove(c);
                    }
                    starChamb.getCardsUnder().clear();
                    currentPlayer.discard(starChamb, attacker, context);
                    currentPlayer.getTavern().remove(starChamb);
                }
                context.woundsTaken++;
                woundsTakenHere++;
            }
        }
        return woundsTakenHere;
    }

    public void OldWoundManoeuvre(MoveContext context, Player p, Card card) {
        SelectCardOptions sco = new SelectCardOptions().isWound().fromHand()
                .setActionType(SelectCardOptions.ActionType.DISCARD).setCardResponsible(card);

        Card toTrash = p.hand.removeCard(card);
        Card toDiscard = p.getCardFromHand(context, sco);
        if (toDiscard == null) {
            p.hand.add(toTrash);
        } else {
            p.discard(p.hand.removeCard(toDiscard), card, context);
            p.banish(toTrash, card, context);
        }

    }
    public void SanctusCharmManoeuvre(MoveContext context, Player p, Card defendCard) {
        p.discard(defendCard,defendCard,context);
        p.hand.remove(defendCard);
        context.sanctusCharm = true;
    }

    public int enemyCount(String identifier, CardType type) {
        return enemyCount(identifier) + enemyCount(type);
    }

    public int enemyCount(String identifier) {
        int count=0;

        for (Card c : this.blackMarketPile) {
            if (c.is(identifier) || identifier=="") {
                if (c.getKind() == Cards.Kind.RabbleElf){
                    count++;}
                count++;
            }
        }
        return count;
    }

    public int enemyCount(CardType type) {
        int count=0;

        for (Card c : this.blackMarketPile) {
            if (c.is(type)) {
                count++;
            }
        }
        return count;
    }

    private int activateEnemy(Card enemyCard, Player currentPlayer, MoveContext context, int i) {
        int wounds = 0;
        if (currentPlayer.nextTurnCards.contains(Cards.celerity)) {
            return i;
        }
        if (currentPlayer.preventActivation(context, enemyCard)) {
            if (i < this.blackMarketPile.size() && (enemyCard.getId() != context.game.blackMarketPile.get(i).getId())) {
                //must have been killed roll back i
                i--;
            }
            return i;
        }
        if (currentPlayer.tavern.contains(Cards.petrify)) {
            for (Card c : Util.copy(currentPlayer.tavern)) {
                if (c.getKind() == Cards.Kind.Petrify &&
                        ((CardImpl)c).cardsUnder.get(0).getId() == enemyCard.getId()) {
                    if (((CardImpl)c).cardsUnder.size()==1) {
                        ((CardImpl)c).cardsUnder.clear();
                        currentPlayer.banish(currentPlayer.tavern.removeCard(c), c, context);
                    }
                    else {
                        currentPlayer.banish(currentPlayer.tavern.removeCard(((CardImpl)c).cardsUnder.get(((CardImpl)c).cardsUnder.size()-1)), c, context);
                        ((CardImpl)c).cardsUnder.remove(((CardImpl)c).cardsUnder.size()-1);
                    }
                    return i;
                }
            }
        }

            switch (enemyCard.getKind()) {
                case ArcaneMessiah:
                    takeWounds(currentPlayer,1,context,enemyCard,false);
                    for(int j=1; j <=Util.getCardCount(blackMarketPile,CardType.Magical); j++) {
                        enemyCard.getCardsUnder().add(takeFromPile(Cards.virtualWound));
                        enemyCard.setDebtCost(enemyCard.getDebtCost(context)+1);
                    }
                    break;
                case TuskedDeathcharger:
                    try {
                        blackMarketPile.get(i - 1);
                        blackMarketPile.get(i + 1);
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    } catch (IndexOutOfBoundsException e) {
                    }
                    break;
                case RogueHumanMage:
                    if (((CardImpl)enemyCard).cardsUnder.size()==3) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    }
                    else {
                        ((CardImpl)enemyCard).cardsUnder.add(takeFromPile(Cards.virtualWound));
                        enemyCard.setDebtCost(enemyCard.getDebtCost(context)+1);
                    }
                    break;
                case EnsorcelledZealot:
                    for (int j=i-1; j<=i+1;j++) {
                        try {
                            if (j != i) {
                                for (Card c : blackMarketPile.get(j).getCardsUnder()) {
                                    if (c.is("Wound")) {
                                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                                        break;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {

                        } catch (NullPointerException e){

                        }
                    }
                    break;

                case ArcherFootbow:
                    if (((CardImpl)enemyCard).cardsUnder.size()>0) {
                        addToPile(((CardImpl) enemyCard).cardsUnder.remove(0),false);
                        if (takeWounds(currentPlayer, 1, context, enemyCard, false) == 1) {
                            ((CardImpl) enemyCard).cardsUnder.clear();
                            enemyCard.setDebtCost(0);
                        }
                        else {
                            ((CardImpl)enemyCard).cardsUnder.add(takeFromPile(Cards.virtualWound));
                        }
                    }
                    else {
                        ((CardImpl)enemyCard).cardsUnder.add(takeFromPile(Cards.virtualWound));
                        enemyCard.setDebtCost(enemyCard.getDebtCost(context)+1);
                    }
                    break;
                case TroopBrainwashed:
                    for (int j=i-2; j<=i+2;j++) {
                        try {
                            if (j != i) {
                                if (blackMarketPile.get(j).is("Brainwashed")) {
                                    takeWounds(currentPlayer, 1, context, enemyCard, false);
                                    j=i+2;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {

                        }
                    }
                    break;
                case BrokenCorpse:
                    if (context.woundsTaken > 0) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    }
                    break;
                case FreshCorpse:
                    if (enemyCount("corpse") >= 3) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    }
                    break;
                case StaleCorpse:
                    if (enemyCount("corpse") >= 5) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                        CardImpl tempCard = (CardImpl) enemyCard;
                        tempCard.discardMultiple(context, currentPlayer, 1);
                    }

                    break;
                case EmbalmedAcolyte:
                    Card[] cardsToDiscard = currentPlayer.controlPlayer.selectFromHand(context, enemyCard,
                            1, true, true,
                            SelectCardOptions.ActionType.DISCARD, SelectCardOptions.PickType.DISCARD);
                    if (cardsToDiscard == null) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    } else {
                        currentPlayer.discard(currentPlayer.hand.removeCard(cardsToDiscard[0]), enemyCard, context);
                    }
                    break;
                case TheLeftHandGoblin:
                    takeWounds(getNextPlayer(), 1, context, enemyCard, false);
                    break;
                case TheRightHandHuman:
                    takeWounds(getPreviousPlayer(), 1, context, enemyCard, false);
                    break;
                case TheCatacombite:
                    takeWounds(currentPlayer, 2, context, enemyCard, false);
                    break;
                case HorrorOfFlesh:
                    if (killACorpse(enemyCard, currentPlayer, context)) {
                        takeWounds(currentPlayer, 2, context, enemyCard, false);
                    }
                    break;
                case KangaxxTheLich:
                    takeWounds(currentPlayer, 1, context, enemyCard, false);
                    drawFoe(currentPlayer, context, true);
                    break;
                case XaphanDemon:
                    enemyCard.selectAndTrashFromHand(context, currentPlayer, 1);
                    break;
                case VeporDemon:
                    currentPlayer.vepor_activation(context, currentPlayer);
                    break;
                case TaneElf:
                    try {
                        blackMarketPile.get(i - 1);
                        blackMarketPile.get(i + 1);
                        takeWounds(currentPlayer, 2, context, enemyCard, false);
                    } catch (IndexOutOfBoundsException e) {
                    }
                    break;
                case KiriElf:
                    takeWounds(currentPlayer, enemyCount("corpse"), context, enemyCard, false);
                    //TODO Bulwark for now just add Kiri in manually, ie +1
                    break;
                case DruidElf:
                    for (int j = 2; j > 0; j--) {
                        Card revealed = draw(context, enemyCard, j);
                        if (revealed.is(CardType.Location))
                            takeWounds(currentPlayer, 1, context, enemyCard, false);
                        currentPlayer.discard(revealed, enemyCard, context);
                    }
                    break;
                case FlameBallista:
                    for (Card c : currentPlayer.tavern) {
                        if (c.is(CardType.Remains))
                            wounds++;
                    }
                    takeWounds(currentPlayer, wounds, context, enemyCard, true);
                    break;
                case ArcherElf:
                    if (archers >= 2) {
                        int elves = enemyCount("elf");
                        takeWounds(currentPlayer, elves > 3 ? 3 : elves, context, enemyCard, false);
                    } else {
                        archers = 0;
                        for (Card isArcher : context.game.blackMarketPile) {
                            if (isArcher.is("Archer")) {
                                archers += 1;
                                if (archers >= 2) {
                                    int elves = enemyCount("elf");
                                    takeWounds(currentPlayer, elves > 3 ? 3 : elves, context, enemyCard, false);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case TroopElf:
                    if (enemyCount("elf") >= 4) {
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    }
                    break;
                case HeavyLizard:
                    try {
                        if (context.game.blackMarketPile.get(i - 1).is("Troop"))
                            wounds++;
                    } catch (IndexOutOfBoundsException e) {
                    }
                    try {
                        if (context.game.blackMarketPile.get(i + 1).is("Troop"))
                            wounds++;
                    } catch (IndexOutOfBoundsException e) {
                    }
                    takeWounds(currentPlayer, wounds, context, enemyCard, false);
                    break;
                case YoonIseulLizard:
                    try { //if previous enemy kills itself or others go back
                        i = activateEnemy(context.game.blackMarketPile.get(i - 1), currentPlayer, context, i - 1) + 1;
                    } catch (IndexOutOfBoundsException e) {
                    }
                    try { //doesn't matter what happens to future enemies, always go to the next
                        activateEnemy(context.game.blackMarketPile.get(i + 1), currentPlayer, context, i + 1);
                    } catch (IndexOutOfBoundsException e) {
                    }

                    break;
                case HyeonLizard:
                    if (!context.attackMade) {
                        takeWounds(currentPlayer, 2, context, enemyCard, false);
                    }
                    break;
                case BombardierLizard:
                    List<Card> remaining = new ArrayList<>();
                    ArrayList<Card> under = new ArrayList<>();
                    for (int j = 0; j < currentPlayer.tavern.a.size(); j++) {//  Card enemyCard : currentPlayer.tavern.a){
                        Card aCard = currentPlayer.tavern.get(j);
                        for (Card c : ((CardImpl) aCard).cardsUnder) {
                            under.add(c);
                        }
                        if (!aCard.is(CardType.Enemy) && !under.contains(aCard))
                            remaining.add(aCard);
                        under.remove(aCard);
                    }
                    if (remaining.size() > 0) {
                        Card toDiscard = (currentPlayer.cardToPlay(context, remaining, enemyCard, false, ""));
                        if (toDiscard != null) {
                            for (Card c : ((CardImpl) toDiscard).cardsUnder) {
                                currentPlayer.discard(currentPlayer.tavern.removeCard(c), enemyCard, context);
                            }
                            ((CardImpl) toDiscard).cardsUnder.clear();
                            currentPlayer.discard(currentPlayer.tavern.removeCard(toDiscard), enemyCard, context);
                        }
                    }
                    break;
                case RabbleLizard:
                    boolean attackAlreadyMade = context.attackMade;
                    killFoe(context, enemyCard);
                    context.attackMade = attackAlreadyMade;
                    drawFoe(currentPlayer, context,true);
                    i--;
                    break;
                case FireCatapult:
                    if (currentPlayer.inHand(Cards.Kind.SeriousWound) ||
                            currentPlayer.inHand(Cards.Kind.StaggeringWound) ||
                            currentPlayer.inHand(Cards.Kind.OldWound) ||
                            currentPlayer.inHand(Cards.Kind.GlancingWound) ||
                            currentPlayer.inHand(Cards.Kind.WeakeningWound) ||
                            currentPlayer.inHand(Cards.Kind.FleshWound))
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    break;
                case BatteringRam:
                    takeWounds(currentPlayer, context.SiegeActivations, context, enemyCard, false);
                    break;
                case CatapultGob:
                    for (Player aPlayer : getPlayersInTurnOrder()) {
                        MoveContext targetContext = new MoveContext(this, aPlayer);
                        Card topCard = draw(targetContext, enemyCard, 1);
                        if (topCard != null) {
                            (aPlayer).discard(topCard, enemyCard, context);
                        }
                        if (topCard.is(CardType.Wound)) {
                            takeWounds(currentPlayer, 1, context, enemyCard, false);
                        }
                    }
                    break;
                case HeavyGoblin:
                    if (hasTroop)
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    else {
                        for (Card isTroop : context.game.blackMarketPile) {
                            if (isTroop.is("Troop")) {
                                hasTroop = true;
                                takeWounds(currentPlayer, 1, context, enemyCard, false);
                                break;
                            }
                        }
                    }
                    break;
                case ArcherGoblin:
                    if (archers >= 2)
                        takeWounds(currentPlayer, 1, context, enemyCard, false);
                    else {
                        archers = 0;
                        for (Card isArcher : context.game.blackMarketPile) {
                            if (isArcher.is("Archer")) {
                                archers += 1;
                                if (archers >= 2) {
                                    takeWounds(currentPlayer, 1, context, enemyCard, false);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case KmbleeGoblin:
                    drawFoe(currentPlayer, context, true);
                    break;
                case KmronGoblin:
                case RuihaElf:
                    takeWounds(currentPlayer, 1, context, enemyCard, false);
                    break;
                case GhutzGoblin:
                    for (Card isGoblin : context.game.blackMarketPile) {
                        if (isGoblin.is("Goblin")) {
                            takeWounds(currentPlayer, 1, context, enemyCard, false);
                        }
                    }
                    break;
                case KneenaGoblin:
                    for (Player aPlayer : getPlayersInTurnOrder()) {
                        MoveContext targetContext = new MoveContext(this, aPlayer);
                        Card topCard = draw(targetContext, enemyCard, 1);
                        if (topCard != null) {
                            aPlayer.discard(topCard, enemyCard, context);
                        }
                        if (topCard.is(CardType.Location)) {
                            takeWounds(currentPlayer, 1, targetContext, enemyCard, false);
                        }
                    }
                    break;
                case MgzwelGoblin:
                    //put his effect into getcost
                    break;
                case AlchemistGoblin:
                    Card prevCard = null;
                    Card nextCard = null;
                    boolean takeWound = false;
                    if (i > 0) {
                        prevCard = context.game.blackMarketPile.get(i - 1);
                        if (prevCard.is(new String[]{"Alchemist"}, CardType.Siege))
                            takeWound = true;
                    }
                    if (i < context.game.blackMarketPile.size() - 1) {
                        nextCard = context.game.blackMarketPile.get(i + 1);
                        if (nextCard.is(new String[]{"Alchemist"}, CardType.Siege))
                            takeWound = true;
                    }
                    if (takeWound)
                        takeWounds(currentPlayer, 2, context, enemyCard, false);
                    break;
            }
            if (enemyCard.is(CardType.Siege))
                context.SiegeActivations++;

        //If the card still exists use its index, otherwise just i
        int newI = Util.indexOfCardId(enemyCard.getId(), context.game.blackMarketPile);
        if (newI >=0)
            return newI;
        return i;
    }

    private void addToCardsUnder(Card card, int i, ArrayList<Card> listToAddTo, Card fromPile) {
        listToAddTo.add(i+1, takeFromPile(fromPile));
        card.getCardsUnder().add(listToAddTo.get(i+1));
    }

    public boolean killACorpse(Card enemyCard, Player currentPlayer, MoveContext context) {
        SelectCardOptions sco = new SelectCardOptions().isCorpse()
            .setCardResponsible(enemyCard)
            .setCount(1).fromBlackMarket().setActionType(SelectCardOptions.ActionType.TRASH);
        int[] toKill = currentPlayer.doSelectFoe(context, sco, 1, EventType.AttackingFoe);
        if (toKill != null) {
            killFoe(context,context.game.blackMarketPile.get(toKill[0]));
        }
        return (toKill != null);
    }


    protected void enemyAttack(Player currentPlayer, MoveContext context){
        hasTroop = false;
        archers = 0;
        for (int i = 0; i < context.game.blackMarketPile.size(); i++) {//  Card enemyCard : currentPlayer.tavern.a){
            Card enemyCard = context.game.blackMarketPile.get(i);
            if (enemyCard.is(CardType.Activate)) {
                i = activateEnemy(enemyCard, currentPlayer, context, i);
            }
        }
    }

    protected void setPlayersTurn(boolean takeAnotherTurn) {
        if (!takeAnotherTurn && consecutiveTurnCounter > 0) {
            // next player
            consecutiveTurnCounter = 0;
            if (++playersTurn >= numPlayers) {
                playersTurn = 0;
                Util.debug("Turn " + ++turnCount, true);
                Util.log("Turn " + turnCount);
            }
        }
    }

    public int cardsInLowestPiles (int numPiles) {
        int[] ips = new int[piles.size() - 1 - (isColonyInGame() ? 1 : 0)];
        int count = 0;
        for (CardPile pile : piles.values()) {
            if (pile.placeholderCard() != Cards.province && pile.topCard() != Cards.colony)
                ips[count++] = pile.getCount();
        }
        Arrays.sort(ips);
        count = 0;
        for (int i = 0; i < numPiles; i++) {
            count += ips[i];
        }
        return count;
    }

    protected void playTreasures(Player player, MoveContext context, int maxCards, Card responsible) {
    	// storyteller sets maxCards != -1
    	if (disableAi && player.isAi()) return;
    	
    	boolean selectingCoins = playerShouldSelectCoinsToPlay(context, player.getHand());
        if (maxCards != -1) selectingCoins = true;// storyteller
        ArrayList<Card> treasures = null;
        //auto play, hold back Two if Three available
        if (!player.tavern.contains(Cards.theDrop)) {
            treasures = player.getTreasuresInHand();
            if (!getPile(Cards.three).isEmpty()) {
                treasures.remove(Cards.two);
            }
            while (treasures != null && !treasures.isEmpty() && maxCards != 0) {
                while (!treasures.isEmpty() && maxCards != 0) {
                    Card card = treasures.remove(0);
                    if (player.hand.contains(card)) {// this is needed due to counterfeit which trashes cards during this loop
                        card.play(context.game, context, true, true);
                        maxCards--;
                    }
                }
            }
        }
        treasures = (selectingCoins) ? player.controlPlayer.treasureCardsToPlayInOrder(context, maxCards, responsible) : player.getTreasuresInHand();
        while (treasures != null && !treasures.isEmpty() && maxCards != 0) {
            while (!treasures.isEmpty() && maxCards != 0) {
                Card card = treasures.remove(0);
                if (player.hand.contains(card)) {// this is needed due to counterfeit which trashes cards during this loop
                    card.play(context.game, context, true, true);
                    maxCards--;
                }
            }
            if (maxCards != 0)
            	treasures = (selectingCoins) ? player.controlPlayer.treasureCardsToPlayInOrder(context, maxCards, responsible) : player.getTreasuresInHand();
        }
    }

    protected void playGuildsTokens(Player player, MoveContext context)
    {
    	if (disableAi && player.isAi()) return;
        int coinTokenTotal = player.getGuildsCoinTokenCount();

        if (coinTokenTotal > 0)
        {
            // Offer the player the option of "spending" Guilds coin tokens prior to buying cards
            int numTokensToSpend = player.controlPlayer.numGuildsCoinTokensToSpend(context, coinTokenTotal, false/*!butcher*/);

            if (numTokensToSpend > 0 && numTokensToSpend <= coinTokenTotal)
            {
                player.spendGuildsCoinTokens(numTokensToSpend);
                context.addCoins(numTokensToSpend);
                if(numTokensToSpend > 0)
                {
                    GameEvent event = new GameEvent(GameEvent.EventType.GuildsTokenSpend, context);
                    event.setComment(": " + numTokensToSpend);
                    context.game.broadcastEvent(event);
                }
                Util.debug(player, "Spent " + numTokensToSpend + " Guilds coin tokens");
            }
        }
    }

    private void markWinner(HashMap<String, Double> gameTypeSpecificWins) {
        double highWins = 0;
        int winners = 0;

        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) > highWins) {
                highWins = gameTypeSpecificWins.get(player);
            }
        }

        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) == highWins) {
                winners++;
            }
        }

        double points = 1.0 / winners;
        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) == highWins) {
                double playerWins = 0;
                if (GAME_TYPE_WINS.containsKey(player)) {
                    playerWins = GAME_TYPE_WINS.get(player);
                }
                playerWins += points;

                GAME_TYPE_WINS.put(player, playerWins);
            }
        }

        GAME_TYPE_WINS.toString();
    }

    protected int[] gameOver(HashMap<String, Double> gameTypeSpecificWins) {
        if (debug)
            printPlayerTurn();

        int[] vps = calculateVps();

        for (int i = 0; i < numPlayers; i++) {
            int tieCount = 0;
            boolean loss = false;
            for (int j = 0; j < numPlayers; j++) {
                if (i == j) {
                    continue;
                }
                if (vps[i] < vps[j]) {
                    loss = true;
                    break;
                }
                if (vps[i] == vps[j]) {
                    tieCount++;
                }
            }

            if (!loss) {
                double num = gameTypeSpecificWins.get(players[i].getClass().getName());
                Double overall = overallWins.get(players[i].getClass().getName());
                boolean trackOverall = (overall != null);
                if (tieCount == 0) {
                    num += 1.0;
                    if (trackOverall) {
                        overall += 1.0;
                    }
                } else {
                    num += 1.0 / (tieCount + 1);
                    if (trackOverall) {
                        overall += 1.0 / (tieCount + 1);
                    }
                }
                gameTypeSpecificWins.put(players[i].getClass().getName(), num);
                if (trackOverall) {
                    overallWins.put(players[i].getClass().getName(), overall);
                }
            }

            Player player = players[i];
            player.vps = vps[i];
            player.win = !loss;
            MoveContext context = new MoveContext(this, player);
            broadcastEvent(new GameEvent(GameEvent.EventType.GameOver, context));

        }
        int index = 0;
        for (Player player : players) {
            int vp = vps[index++];
            Util.debug(player.getPlayerName() + ":Victory Points=" + vp, true);
            GameEvent event = new GameEvent(GameEvent.EventType.VictoryPoints, null);
            event.setPlayer(player);
            event.setComment(":" + vp);
            broadcastEvent(event);
        }
        return vps;

    }

    protected void printPlayerTurn() {
        for (Player player : players) {
            Util.debug("", true);
            ArrayList<Card> allCards = player.getAllCards();
            StringBuilder msg = new StringBuilder();
            msg.append(" " + allCards.size() + " Cards: ");

            final HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
            for (Card card : allCards) {
                String key = card.getName() + " -> " + card.getDescription();
                Integer count = cardCounts.get(key);
                if (count == null) {
                    cardCounts.put(key, 1);
                } else {
                    cardCounts.put(key, count + 1);
                }
            }

            ArrayList<Card> removeDuplicates = new ArrayList<Card>();
            for (Card card : allCards) {
                if (!removeDuplicates.contains(card)) {
                    removeDuplicates.add(card);
                }
            }
            allCards = removeDuplicates;

            Collections.sort(allCards, new Comparator<Card>() {
                public int compare(Card o1, Card o2) {
                    String keyOne = o1.getName() + " -> " + o1.getDescription();
                    String keyTwo = o2.getName() + " -> " + o2.getDescription();
                    return cardCounts.get(keyTwo) - cardCounts.get(keyOne);
                }
            });

            boolean first = true;
            for (Card card : allCards) {
                String key = card.getName() + " -> " + card.getDescription();
                if (first) {
                    first = false;
                } else {
                    msg.append(", ");
                }
                msg.append("" + cardCounts.get(key) + " " + card.getName());
            }

            Util.debug(player.getPlayerName() + ":" + msg, true);
        }
        Util.debug("", true);
    }

    protected List<ExtraTurnInfo> playerEndTurn(Player player, MoveContext context) {
        int handCount = 5;

        List<ExtraTurnInfo> result = new ArrayList<Game.ExtraTurnInfo>();
        // Can only have at most two consecutive turns
        for (Card card : player.nextTurnCards) {
            Card behaveAsCard = card.behaveAsCard();
            if (behaveAsCard.takeAnotherTurn()) {
                handCount = behaveAsCard.takeAnotherTurnCardCount();
                if (consecutiveTurnCounter <= 1) {
                	result.add(new ExtraTurnInfo());
                    break;
                }
            }
        }
        
        handCount += context.totalExpeditionBoughtThisTurn;

        // draw next hand
        for (int i = 0; i < handCount; i++) {
            drawToHand(context, null, handCount - i, false);
        }

        if (player.save != null) {
            player.hand.add(player.save);
            player.save = null;
        }
        	        	
        // /////////////////////////////////
        // Reset context for status update
        // /////////////////////////////////
        context.actionsPlayedSoFar = 0;
        context.actions = 1;
        context.buys = 1;
        context.coppersmithsPlayed = 0;
        context.resetAttacks();
        context.resetMight();
        
        GameEvent event = new GameEvent(GameEvent.EventType.NewHand, context);
        broadcastEvent(event);
        event = null;

        // /////////////////////////////////
        // Turn End
        // /////////////////////////////////
        
        event = new GameEvent(GameEvent.EventType.TurnEnd, context);
        broadcastEvent(event);
        
        if (cardsObtainedLastTurn[playersTurn].size() == 0 && cardInGame(Cards.baths)) {
        	int tokensLeft = getPileVpTokens(Cards.baths);
    		if (tokensLeft > 0) {
    			int tokensToTake = Math.min(tokensLeft, 2);
    			removePileVpTokens(Cards.baths, tokensToTake, context);
    			player.addVictoryTokens(context, tokensToTake, Cards.baths);
    		}
        }
        
        if (player.isPossessed()) {
            while (!possessedTrashPile.isEmpty()) {
                player.discard(possessedTrashPile.remove(0), null, null, false, false);
            }
            possessedBoughtPile.clear();
        }
        
        if (player.isPossessed()) {
            if (--possessionsToProcess == 0)
                possessingPlayer = null;
                player.controlPlayer = player;
        } else if (nextPossessionsToProcess > 0) {
            possessionsToProcess = nextPossessionsToProcess;
            possessingPlayer = nextPossessingPlayer;
            nextPossessionsToProcess = 0;
            nextPossessingPlayer = null;
        }
        
        if (context.missionBought && consecutiveTurnCounter <= 1) {
			if (!result.isEmpty()) {
				//ask player if they want to do mission turn with three cards or do outpost turn first then mission turn
				// player not possessed because we are between turns
				
				//TODO: dominionator - integrate this with Possession turn logic
				ExtraTurnOption[] options = new ExtraTurnOption[]{ExtraTurnOption.OutpostFirst, ExtraTurnOption.MissionFirst};
				switch(player.extraTurn_chooseOption(context, options)) {
				case MissionFirst:
					result.get(0).canBuyCards = false;
					break;
				case OutpostFirst:
					result.add(new ExtraTurnInfo(false));
					break;
				case PossessionFirst:
					//TODO
					break;
				default:
					break;
				}
			} else {
				result.add(new ExtraTurnInfo(false));
			}
		}
        
        return result;
    }

    protected void playerAfterTurn(Player player, MoveContext context) {
    	while (context.donatesBought-- > 0) {
        	while(!player.deck.isEmpty()) {
        		player.hand.add(player.deck.removeLastCard());
        	}
        	while(!player.discard.isEmpty()) {
        		player.hand.add(player.discard.removeLastCard());
        	}
        	Card[] cardsToTrash = player.donate_cardsToTrash(context);
        	if (cardsToTrash != null) {
                for (Card c : cardsToTrash) {
                    Card toTrash = player.hand.get(c);
                    if (toTrash == null) {
                        Util.playerError(player, "Donate error, tried to trash card not in hand: " + c);
                    } else {
                        player.hand.remove(toTrash);
                        player.trash(toTrash, Cards.donate, context);
                    }
                }
            }
            while(!player.hand.isEmpty()) {
                player.deck.add(player.hand.removeLastCard());
            }
            player.shuffleDeck(context, Cards.donate);
            for (int i = 0; i < 5; ++i) {
                drawToHand(context, Cards.donate, 5 - i);
        	}
        }
        
        // Mountain Pass bidding
    	if (cardInGame(Cards.mountainPass) && doMountainPassAfterThisTurn) {
    		doMountainPassAfterThisTurn = false;
    		
    		int highestBid = 0;
    		Player highestBidder = null;
    		final int MAX_BID = 40;
    		int playersLeftToBid = numPlayers;
    		for (Player biddingPlayer : getPlayersInTurnOrder((firstProvinceGainedBy + 1) % numPlayers)) {
    			MoveContext bidContext = new MoveContext(this, biddingPlayer);
    			int bid = biddingPlayer.mountainPass_getBid(context, highestBidder, highestBid, --playersLeftToBid);
    			if (bid > MAX_BID) bid = MAX_BID;
    			if (bid < 0) bid = 0;
    			if (bid != 0 && bid > highestBid) {
    				highestBid = bid;
    				highestBidder = biddingPlayer;
    			}
    			GameEvent event = new GameEvent(GameEvent.EventType.MountainPassBid, bidContext);
	        	event.setAmount(bid);
	        	event.card = Cards.mountainPass;
	            context.game.broadcastEvent(event);
    			if (bid == MAX_BID) {
    				break;
    			}
    		}
    		if (highestBidder != null) {
    			MoveContext bidContext = new MoveContext(this, highestBidder);
    			highestBidder.addVictoryTokens(bidContext, 8, Cards.mountainPass);
    			highestBidder.gainDebtTokens(highestBid);
    			GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
            	event.setAmount(highestBid);
                context.game.broadcastEvent(event);
    		}
    		GameEvent winEvent = new GameEvent(GameEvent.EventType.MountainPassWinner, context);
    		winEvent.setPlayer(highestBidder == null ? context.getPlayer(): highestBidder);
    		winEvent.setAmount(highestBid);
            context.game.broadcastEvent(winEvent);
    	}
    }


    protected void enemyWhenDrawn(Player player, MoveContext context, Card cardPlayed) {
        if (context.rabblePlayed && cardPlayed.is("rabble"))
            return;
        List<Card> availableCards = Util.canReact(context,player, CardType.WhenDrawn);
        Card selectedCard = null;
        if (availableCards.size() > 0)
          selectedCard = player.cardToPlay(context, availableCards, cardPlayed, true, IndirectPlayer.OPTION_DRAWN);
        if (selectedCard !=null) {
            switch (selectedCard.getKind()) {
                case TownSquare:
                    player.discard(player.tavern.removeCard(selectedCard,true),cardPlayed,context);
                    return;
                case TheDrop:
                    if (!cardPlayed.is(CardType.Crown)) {
                        addToPile(context.game.blackMarketPile.remove(context.game.blackMarketPile.size()-1), true);
                        player.discard(player.tavern.removeCard(selectedCard,true),cardPlayed,context);
                    }
                    return;
            }
        }
        int i=0;
        switch (cardPlayed.getKind()) {
            case ArcaneMessiah:
                Card topCard = takeFromPile(Cards.virtualEnemy);
                player.reveal(topCard, cardPlayed, context);
                addToPile(topCard, false);
                if (topCard.is("Elemental")) {
                    drawFoe(player,context,true);
                }
                break;
            case PressureHeraldWaterElemental:
                context.game.preventDefense = true;
                revealForWounds(player, context, cardPlayed);
                break;
            case ScorchingHeraldFireElemental:
                context.game.woundsInHand = true;
                revealForWounds(player, context, cardPlayed);
                break;
            case StarsHeraldAirElemental:
            case GraniteHeraldEarthElemental:
                revealForWounds(player, context, cardPlayed);
                break;
            case RabbleBrainwashed:
                context.rabblePlayed = true;
                int rabbleBanished = 0;
                for (i=blackMarketPile.size()-2; i >= 0; i--) {
                    Card enemy = blackMarketPile.get(i);
                    if (enemy.is("Rabble")) { //don't need this as we skip the most recent drawn which should be this card && enemy.getId()!=cardPlayed.getId()) {
                        rabbleBanished++;
                        player.banish(blackMarketPile.remove(i), cardPlayed, context);
                    }
                }
                for (i=1; i<= rabbleBanished; i++) {
                    drawFoe(player, context, true);
                    drawFoe(player, context, true);
                }
                break;
            case TuskedDeathcharger:
            case EnshroudingMist:
                drawFoe(player,context, true);
                break;
            case TheLeftHandGoblin:
                revealKillPlay(player,context, new String[]{"goblin"} , CardType.Undead);
                break;
            case TheRightHandHuman:
                revealKillPlay(player,context, new String[]{"human"} , CardType.Undead);
                break;
            case TheCatacombite:
                takeWounds(player,1, context, cardPlayed, false);
                break;
            case KangaxxTheLich:
                revealKillPlay(player, context, new String[]{""}, CardType.Undead);
                break;
            case RabbleElf:
                SelectCardOptions sco = new SelectCardOptions().isNonCrown().isNonRabble()
                        .setCardResponsible(cardPlayed)
                        .setCount(1).fromBlackMarket().setActionType(SelectCardOptions.ActionType.DISCARD);
                int[] toBanish = player.doSelectFoe(context, sco, 1, EventType.SelectFoe);
                if (toBanish != null)
                for (int b : toBanish)
                {
                    player.banish(context.game.blackMarketPile.remove(b),cardPlayed,context);
                }
                break;
            case RabbleGoblin:
                context.rabblePlayed = true;

                sco = new SelectCardOptions().isNonCrown().setCardResponsible(cardPlayed)
                        .setCount(2).fromBlackMarket()
                        .setPickType(SelectCardOptions.PickType.SELECT_IN_ORDER);
                toBanish = player.doSelectFoe(context, sco, 2, EventType.SelectFoe);

                for (int b : toBanish) {
                    player.banish(context.game.blackMarketPile.get(b), cardPlayed, context);
                }
                Arrays.sort(toBanish);
                for (int b= toBanish.length-1;b>=0;b--){
                    context.game.blackMarketPile.remove(toBanish[b]);
                }
                drawFoe(player, context, true);
                drawFoe(player, context, true);
                context.rabblePlayed = false;
                break;
        }

    }

    private void revealForWounds(Player player, MoveContext context, Card cardPlayed) {
        Card topCard;
        topCard = takeFromPile(Cards.virtualEnemy);
        player.reveal(topCard, cardPlayed, context);
        if (topCard.is("elemental")) {
            takeWounds(player,2,context,cardPlayed,false);
        }
        addToPile(topCard,false);
    }

    private void revealKillPlay(Player player, MoveContext context, String[] identifiers, CardType... types) {
        boolean draw = false;
        Card topCard = getGamePile(Cards.virtualEnemy).topCard();
        if (topCard.is(identifiers, types)) {
            draw = true;
        }
/*        if (!draw) for (CardType t : types) {
            if (topCard.is(t)) {
                draw = true;
                break;
            }
        }
        */
        if (draw) {
            drawFoe(player,context, true);
        } else {
            drawFoe(player, context, false);
            killFoe(context, context.game.blackMarketPile.get(context.game.blackMarketPile.size()-1) );
        }
    }


    protected void playerAction(Player player, MoveContext context) {
        Card action = null;
        do {
            action = null;
            ArrayList<Card> actionCards = null;
            if (disableAi && player.isAi()) continue;
            if (!actionChains || player.controlPlayer.isAi()) {
                action = player.controlPlayer.doAction(context);
                if (action != null) {
                    actionCards = new ArrayList<Card>();
                    actionCards.add(action);
                }
            } else {
                Card[] cs = player.controlPlayer.actionCardsToPlayInOrder(context);
                if (cs != null && cs.length != 0) {
                    actionCards = new ArrayList<Card>();
                    for (int i = 0; i < cs.length; i++) {
                        actionCards.add(cs[i]);
                    }
                }
            }

            while (context.actions > 0 && actionCards != null && !actionCards.isEmpty()) {
                action = actionCards.remove(0);
                if (action != null) {
                    if (isValidAction(context, action)) {
                        GameEvent event = new GameEvent(GameEvent.EventType.Status, (MoveContext) context);
                        broadcastEvent(event);

                        try {
                            action.play(this, (MoveContext) context, true);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Util.debug("Error:Invalid action selected");
                        // action = null;
                    }
                }
            }
        } while ((context.actions > 0 && action != null));
    }
    
    public void playerPayOffDebt(Player player, MoveContext context) {
    	if (player.getDebtTokenCount() > 0 && context.getCoins() > 0) {
    		int payOffNum = player.controlPlayer.numDebtTokensToPayOff(context);
    		if (payOffNum > context.getCoins() || payOffNum < 0) {
    			payOffNum = 0;
    		}
    		if (payOffNum > player.getDebtTokenCount()) {
    			payOffNum = player.getDebtTokenCount();
    		}
    		if (payOffNum > 0) {
	    		context.spendCoins(payOffNum);
	    		player.payOffDebtTokens(payOffNum);
	    		GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensPaidOff, context);
	        	event.setAmount(payOffNum);
	            context.game.broadcastEvent(event);
    		}
    	}
    }

    protected void playerAttackFoe(Player player, MoveContext context) {
        Card foe = null;
        do {
            if (disableAi && player.isAi()) continue;
            if (context.getAttacks() <= 0) break;
            foe = null;
            try {
                    for (Player p : this.getPlayersInTurnOrder())
                    {
                        p.bolsterAttack(context);
                    }
                    foe = player.controlPlayer.doAttackFoe(context);
                    /*if (foe != null) {
                        foe = getPile(foe).topCard(); //Swap in the actual top card of the pile
                    }
                    */
                }
            catch (Throwable t) {
                Util.playerError(player, t);
            }

            if (foe != null) {
                killFoe(context, foe);
                if (context.returnToActionPhase)
                    return;
                context.reduceAttacks() ;
                context.resetMight();
                context.setMightModifier(0);
            }
            /* else {
                    // TODO report?
                    foe = null;
                }*/
        } while (context.getAttacks() > 0 && foe != null);

    }

    public void killFoe(MoveContext context, Card foe) {
        boolean dead = foe.isDying(context); //should include on removal

        GameEvent event = new GameEvent(EventType.KillingFoe, context);
        event.card = foe;
        event.newCard = false;
        broadcastEvent(event);

        context.game.blackMarketPile.remove(Util.indexOfCardId(foe.getId(), context.game.blackMarketPile));
        if (dead) {
            context.player.trash(foe,foe,context);
        /* In isDying
        if (context.phase == TurnPhase.Attack)
            context.attackMade = true;
             */
        }

    }

    protected void playerBuy(Player player, MoveContext context) {
        Card buy = null;
        do {
        	if (disableAi && player.isAi()) continue;
            if (context.buys <= 0 && context.techniqueBuys <= 0) break; //Player can enter buy phase with 0 or less buys after buying but not playing Villa
        	buy = null;
            try {
            	playerPayOffDebt(player, context);
            	if (player.getDebtTokenCount() == 0 && !player.getTavern().contains(Cards.celerity)) {
            		buy = player.controlPlayer.doBuy(context);
                    if (buy != null) {
                        buy = getPile(buy).topCard(); //Swap in the actual top card of the pile
                    }
            	}
            } catch (Throwable t) {
                Util.playerError(player, t);
            }

            if (buy != null) {
                if (isValidBuy(context, buy)) {
                	if(buy.is(CardType.Event, null)) {
                        context.totalEventsBoughtThisTurn++;
                	}
                	else
                	{
                        context.totalCardsBoughtThisTurn++;
                	}
                    GameEvent statusEvent = new GameEvent(GameEvent.EventType.Status, (MoveContext) context);
                    broadcastEvent(statusEvent);



                    playBuy(context, buy);
                    playerPayOffDebt(player, context);
                    if (context.returnToActionPhase)
                    	return;
                } else {
                    // TODO report?
                    buy = null;
                }
            }
        } while ((context.buys > 0 || context.techniqueBuys > 0 || context.spellBuys > 0) && buy != null );
//TODO also check if technique is available to buy
        //Discard Wine Merchants from Tavern
        if(context.getCoinAvailableForBuy() >= 2) {
        	int wineMerchants = 0;
            for (Card card : player.getTavern()) {
                if (Cards.wineMerchant.equals(card)) {
                	wineMerchants++;
                }
            }
            if (wineMerchants > 0) {
	            int wineMerchantsTotal = player.controlPlayer.cleanup_wineMerchantToDiscard(context, wineMerchants);
	            if (wineMerchants < 0 || wineMerchantsTotal > wineMerchants) {
	                Util.playerError(player, "Wine Merchant discard error, invalid number of Wine Merchants. Discarding all Wine Merchants.");
	                wineMerchantsTotal = wineMerchants;
	            }
	            if(wineMerchantsTotal > 0) {
	            	for (int i = 0; i < wineMerchantsTotal; i++) {
	            	    Card card = player.getTavern().get(Cards.wineMerchant);
	            	    player.getTavern().remove(card);
	                    player.discard(card, null, context, true, false); //set commandedDiscard=true and cleanup=false to force GameEvent 
	            	}
	            }
            }
        }
        
      //Discard Wine Merchants Estates from Tavern
        if(context.getCoinAvailableForBuy() >= 2) {
        	int wineMerchants = 0;
            for (Card card : player.getTavern()) {
                if (Cards.estate.equals(card) && Cards.wineMerchant.equals(card.behaveAsCard())) {
                	wineMerchants++;
                }
            }
            if (wineMerchants > 0) {
	            int wineMerchantsTotal = player.controlPlayer.cleanup_wineMerchantEstateToDiscard(context, wineMerchants);
	            if (wineMerchants < 0 || wineMerchantsTotal > wineMerchants) {
	                Util.playerError(player, "Wine Merchant estate discard error, invalid number of Wine Merchants. Discarding all Wine Merchants.");
	                wineMerchantsTotal = wineMerchants;
	            }
	            if(wineMerchantsTotal > 0) {
	            	for (int i = 0; i < wineMerchantsTotal; i++) {
	            	    Card card = player.getTavern().get(Cards.estate);
	            	    player.getTavern().remove(card);
	                    player.discard(card, null, context, true, false); //set commandedDiscard=true and cleanup=false to force GameEvent 
	            	}
	            }
            }
        }
    }

    @SuppressWarnings("unchecked")
	protected void playerBeginTurn(Player player, MoveContext context) {
        if (context.game.possessionsToProcess > 0) {
            player.controlPlayer = context.game.possessingPlayer;
        } else {
            player.controlPlayer = player;
            consecutiveTurnCounter++;
        }
        if (controlAi && player.isAi()) {
        	for (Player curPlayer : players) {
        		if (curPlayer.isAi()) continue;
        		player.startBeingControlled(curPlayer);
        		break;
        	}
        }
        
        cardsObtainedLastTurn[playersTurn].clear();
        if (consecutiveTurnCounter == 1)
            player.newTurn();
        
        player.clearDurationEffectsOnOtherPlayers();
        
        GameEvent gevent = new GameEvent(GameEvent.EventType.TurnBegin, context);
        broadcastEvent(gevent);

        /* Duration cards, horse traders, cards on prince*/
        
        /* selectOption() must know if horse traders are set aside by reaction or by haven/gear or by prince.
         * We put 2 cards in list durationEffects to differentiate:
         * Examples (Curse is here a dummy card):
         * HorseTrader - (Curse)
         * Haven - Card set aside by haven
         * Gear - List of Cards set aside by gear
         * Archive - List of Cards set aside by archive
         * Prince - Card set aside by prince
         * other Durations like Wharf - (Curse)
         */
        boolean allDurationAreSimple = true;
        ArrayList<Object> durationEffects = new ArrayList<Object>();
        ArrayList<Boolean> durationEffectsAreCards = new ArrayList<Boolean>();
        int archiveNum = 0;
        for (Card card : player.nextTurnCards) {
            Card thisCard = card.behaveAsCard();
            if (thisCard.is(CardType.Duration, player)) {
                /* Wiki:
                 * Effects that resolve at the start of your turn can be resolved in any order;
                 * this includes multiple plays of the same Duration card by a Throne Room variant.
                 * For example, if you played a Wharf and then a Throne Room on an Amulet last turn,
                 * on this turn you could choose to first gain a Silver from the first Amulet play,
                 * then draw 2 cards from Wharf (perhaps triggering a reshuffle and maybe drawing
                 * that Silver), and then choose to trash a card from the second Amulet play,
                 * now that you have more cards to choose from. 
                 */
            	int cloneCount = ((CardImpl) card).getControlCard().cloneCount;
                for (int clone = cloneCount; clone > 0; clone--) {
                    if(   thisCard.equals(Cards.amulet)
                       || thisCard.equals(Cards.dungeon)) {
                        allDurationAreSimple = false;
                    }
                    if(thisCard.equals(Cards.haven)) {
                    	if(player.haven != null && player.haven.size() > 0) {
                    		durationEffects.add(thisCard);
                    		durationEffects.add(player.haven.remove(0));
                    		durationEffectsAreCards.add(clone == cloneCount 
                    				&& !((CardImpl)card.behaveAsCard()).trashAfterPlay);
                    		durationEffectsAreCards.add(false);
                		}
                    } else if (thisCard.equals(Cards.gear)) {
                    	if(player.gear.size() > 0) {
                    		durationEffects.add(thisCard);
                    		durationEffects.add(player.gear.remove(0));
                    		durationEffectsAreCards.add(clone == cloneCount 
                    				&& !((CardImpl)card.behaveAsCard()).trashAfterPlay);
                    		durationEffectsAreCards.add(false);
                    	}
                    } else if (thisCard.equals(Cards.archive)) {
                    	if(player.archive.size() > 0) {
                    		durationEffects.add(thisCard);
                    		durationEffects.add(player.archive.get(archiveNum++));
                    		durationEffectsAreCards.add(clone == cloneCount 
                    				&& !((CardImpl)card.behaveAsCard()).trashAfterPlay);
                    		durationEffectsAreCards.add(false);
                    	}
                    } else {
                    	durationEffects.add(thisCard);
                    	durationEffects.add(Cards.curse); /*dummy*/
                    	durationEffectsAreCards.add(clone == cloneCount
                    			&& !((CardImpl)card.behaveAsCard()).trashAfterPlay);
                		durationEffectsAreCards.add(false);
                    }
                }
            } else if(isModifierCard(thisCard.behaveAsCard())) {
                GameEvent event = new GameEvent(GameEvent.EventType.PlayingDurationAction, context);
                event.card = card;
                event.newCard = true;
                broadcastEvent(event);
            }
        }
        for (Card card : player.horseTraders) {
            durationEffects.add(card);
            durationEffects.add(Cards.curse); /*dummy*/
            durationEffectsAreCards.add(true);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.prince) {
            if (!card.equals(Cards.prince)) {
                allDurationAreSimple = false;
                durationEffects.add(Cards.prince);
                durationEffects.add(card);
                durationEffectsAreCards.add(true);
        		durationEffectsAreCards.add(false);
            }
        }
        for (Card card : player.summon) {
            if (!card.equals(Cards.summon)) {
                allDurationAreSimple = false;
                durationEffects.add(Cards.summon);
                durationEffects.add(card);
                durationEffectsAreCards.add(true);
        		durationEffectsAreCards.add(false);
            }
        }
        while (!player.haven.isEmpty()) {
            durationEffects.add(Cards.haven);
            durationEffects.add(player.haven.remove(0));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        while (archiveNum < player.archive.size()) {
            durationEffects.add(Cards.archive);
            durationEffects.add(player.archive.get(archiveNum++));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        int numOptionalItems = 0;
        ArrayList<Card> callableCards = new ArrayList<Card>();
        for (Card c : player.tavern) {
        	if (c.behaveAsCard().isCallableWhenTurnStarts()) {
        		callableCards.add((Card) c);
        	}
        }
        if (!callableCards.isEmpty()) {
         	Collections.sort(callableCards, new Util.CardCostComparator());
 	        for (Card c : callableCards) {
 	        	if (c.behaveAsCard().equals(Cards.guide)
 	        		|| c.behaveAsCard().equals(Cards.ratcatcher)
 	        		|| c.behaveAsCard().equals(Cards.transmogrify)) {
 	        		allDurationAreSimple = false;
 	        	}
 	        }
        }
        if (!allDurationAreSimple) {
        	// Add cards callable at start of turn
        	for (Card c : callableCards) {
 	        	durationEffects.add(c);
 	        	durationEffects.add(Cards.curse);
 	        	durationEffectsAreCards.add(false);
 	    		durationEffectsAreCards.add(false);
 	        	numOptionalItems += 2;
 	        }
        }
        
        while (durationEffects.size() > numOptionalItems) {
        	int selection=0;
            if(allDurationAreSimple) {
            	selection=0;
            } else {
            	selection = 2*player.controlPlayer.duration_cardToPlay(context, durationEffects.toArray(new Object[durationEffects.size()]));
            }
            Card card = (Card) durationEffects.get(selection);
            boolean isRealCard = durationEffectsAreCards.get(selection);
            if (card == null) {
                Util.log("ERROR: duration_cardToPlay returned " + selection);
                selection=0;
                card = (Card) durationEffects.get(selection);
            }
            Card card2 = null;
            if (durationEffects.get(selection+1) instanceof Card) {
            	card2 = (Card) durationEffects.get(selection+1);
            }
            ArrayList<Card> setAsideCards = null;
            if (durationEffects.get(selection+1) instanceof ArrayList<?>) {
            	setAsideCards = (ArrayList<Card>) durationEffects.get(selection+1);
            }
            if (card2 == null) {
                Util.log("ERROR: duration_cardToPlay returned " + selection);
                card2 = card;
            }

            durationEffects.remove(selection+1);
            durationEffects.remove(selection);
            durationEffectsAreCards.remove(selection+1);
            durationEffectsAreCards.remove(selection);
            
            if(card.equals(Cards.prince)) {
                if (!(card2.is(CardType.Duration, player))) {
                    player.playedByPrince.add(card2);
                }
                player.prince.remove(card2);
                
                context.freeActionInEffect++;
                try {
                    card2.play(this, context, false);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                context.freeActionInEffect--;
            } else if(card.equals(Cards.summon)) {
                player.summon.remove(card2);
                
                context.freeActionInEffect++;
                try {
                    card2.play(this, context, false);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                context.freeActionInEffect--;
            } else if(card.behaveAsCard().equals(Cards.horseTraders)) {
            	//BUG: this doesn't let you call estates inheriting horse trader differently
                Card horseTrader = player.horseTraders.remove(0);
                player.hand.add(horseTrader);
                drawToHand(context, horseTrader, 1);
            } else if(card.behaveAsCard().is(CardType.Duration, player)) {
                if(card.behaveAsCard().equals(Cards.haven)) {
                    player.hand.add(card2);
                }
                if(card.behaveAsCard().equals(Cards.gear)) {
                	for (Card c : setAsideCards)
                		player.hand.add(c);
                }
                if (card.behaveAsCard().equals(Cards.archive)) {
                	CardImplEmpires.archiveSelect(this, context, player, setAsideCards);
                }
                
                Card thisCard = card.behaveAsCard();
                
                GameEvent event = new GameEvent(GameEvent.EventType.PlayingDurationAction, context);
                event.card = card;
                event.newCard = isRealCard;
                broadcastEvent(event);

                context.actions += thisCard.getAddActionsNextTurn();
                context.addCoins(thisCard.getAddGoldNextTurn());
                context.buys += thisCard.getAddBuysNextTurn();
                int addCardsNextTurn = thisCard.getAddCardsNextTurn();

                /* addCardsNextTurn are displayed like addCards but sometimes the text differs */
                if (thisCard.getKind() == Cards.Kind.Tactician) {
                    context.actions += 1;
                    context.buys += 1;
                    addCardsNextTurn = 5;
                }
                if (thisCard.getKind() == Cards.Kind.Dungeon) {
                    addCardsNextTurn = 2;
                }
                if (thisCard.getKind() == Cards.Kind.Hireling) {
                    addCardsNextTurn = 1;
                }

                for (int i = 0; i < addCardsNextTurn; i++) {
                    drawToHand(context, thisCard, addCardsNextTurn - i, true);
                }
                
                if (   thisCard.getKind() == Cards.Kind.Amulet
                	|| thisCard.getKind() == Cards.Kind.Dungeon ) {
                    context.freeActionInEffect++;
                    try {
                        ((CardImpl) thisCard).additionalCardActions(context.game, context, player);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    context.freeActionInEffect--;
                }                
            } else if(card.behaveAsCard().isCallableWhenTurnStarts()) {
            	numOptionalItems -= 2;
            	card.behaveAsCard().callAtStartOfTurn(context);
            } else {
                Util.log("ERROR: nextTurnCards contains " + card);
            }
        }
        
        ArrayList<Card> staysInPlayCards = new ArrayList<Card>();
        archiveNum = 0;
        while (!player.nextTurnCards.isEmpty()) {
            Card card = player.nextTurnCards.remove(0);
        	if(isModifierCard(card.behaveAsCard())) {
                if(!player.nextTurnCards.isEmpty()) {
                  	Card nextCard = player.nextTurnCards.get(0);
                  	int additionalModifierCards = 0;
                  	while (nextCard != null && isModifierCard(nextCard.behaveAsCard())) {
                  		additionalModifierCards++;
                  		if (player.nextTurnCards.size() > additionalModifierCards)
                  			nextCard = player.nextTurnCards.get(additionalModifierCards);
                  		else
                  			nextCard = null;
                  	}
                    if(nextCard != null && (nextCard.behaveAsCard().equals(Cards.hireling) || nextCard.behaveAsCard().equals(Cards.champion) ||
                    		(nextCard.behaveAsCard().equals(Cards.archive) && player.archive.get(archiveNum++).size() > 0))) {
                    	staysInPlayCards.add(card);
                    	for (int i = 0; i < additionalModifierCards; ++i) {
                    		staysInPlayCards.add(player.nextTurnCards.remove(0));
                    	}
                        player.nextTurnCards.remove(0);
                        staysInPlayCards.add(nextCard);
                    	continue;
                    }
                }
            }
        	
        	if(card.behaveAsCard().equals(Cards.hireling) || card.behaveAsCard().equals(Cards.champion) || 
        			(card.behaveAsCard().equals(Cards.archive) && player.archive.get(archiveNum++).size() > 0)) {
        		staysInPlayCards.add(card);
            } else {
	            CardImpl behaveAsCard = (CardImpl) card.behaveAsCard();
	            behaveAsCard.cloneCount = 1;
	            ((CardImpl)card).cloneCount = 1;
	            if (!(behaveAsCard.trashAfterPlay || ((CardImpl)card).trashAfterPlay)) {
	                player.playedCards.add(card);
                    ((CardImpl)card).setPlayedThisTurn(false);
	            } else {
	                behaveAsCard.trashAfterPlay = false;
	                ((CardImpl)card).trashAfterPlay = false;
	            }
            }
        }
        while (!staysInPlayCards.isEmpty()) {
            player.nextTurnCards.add(staysInPlayCards.remove(0));
        }
        //Clean up empty Archive lists
        Iterator<ArrayList<Card>> it = player.archive.iterator();
        while (it.hasNext()) {
        	if (it.next().isEmpty()) 
        		it.remove();
        }
        
        //TODO: Dominionator - Will require tracking duration effects independent of cards
        //       to do correctly or replacing real card with a dummy card - do this later.
        
        //TODO: integrate this into the main action selection UI if possible to make it more seamless
        //check for start-of-turn callable cards
        callableCards = new ArrayList<Card>();
        Card toCall = null;
        for (Card c : player.tavern) {
        	if (c.behaveAsCard().isCallableWhenTurnStarts() &&
                    (c.getKind() != Cards.Kind.TownSquare || !callableCards.contains(Cards.townSquare))) {
        		callableCards.add(c);
        	}
        }
        for (Card c : player.hand) {
            if (c.is(CardType.TurnStart)) {
                callableCards.add(c);
            }
        }
        if (!callableCards.isEmpty()) {
        	Collections.sort(callableCards, new Util.CardCostComparator());
	        do {
	        	toCall = null;
	        	// we want null entry at the end for None
	        	Card[] cardsAsArray = callableCards.toArray(new Card[callableCards.size() + 1]);
	        	//ask player which card to call
	        	toCall = player.controlPlayer.call_whenTurnStartCardToCall(context, cardsAsArray);
	        	if (toCall != null && callableCards.contains(toCall)) {
	        		toCall = callableCards.remove(callableCards.indexOf(toCall));
	        		if (toCall.is(CardType.TurnStart)) {
	        		    toCall.manoeuvreCardActions(context.game, context, player);
	        		    if (toCall.getKind()== Cards.Kind.SpiralLibrary)
	        		        toCall=null;
                    }
                    else {
                        toCall.behaveAsCard().callAtStartOfTurn(context);
                    }
	        	}
		        // loop while we still have cards to call
	        } while (toCall != null && !callableCards.isEmpty());
        }
    }

    protected void playerBeginBuy(Player player, MoveContext context) {
    	if (cardInGame(Cards.arena)) {
    		arena(player, context);
    	}
    }
    
    private void arena(Player player, MoveContext context) {
    	boolean hasAction = false;
		for (Card c : player.getHand()) {
			if (c.is(CardType.Action, player)) {
				hasAction = true;
				break;
			}
		}
		if (!hasAction) return;
		Card toDiscard = player.controlPlayer.arena_cardToDiscard(context);
		if (toDiscard != null && (!player.getHand().contains(toDiscard) || toDiscard.is(CardType.Action, player))) {
			Util.playerError(player, "Arena - invalid card specified, ignoring.");
		}
		if (toDiscard == null) return;
		player.discard(player.getHand().remove(player.getHand().indexOf(toDiscard)), Cards.arena, context);
		int tokensToTake = Math.min(getPileVpTokens(Cards.arena), 2);
		removePileVpTokens(Cards.arena, tokensToTake, context);
		player.addVictoryTokens(context, tokensToTake, Cards.arena);
    }
    
    public static boolean isModifierCard(Card card) {
		return card.equals(Cards.throneRoom)
	               || card.equals(Cards.disciple)
	               || card.equals(Cards.kingsCourt)
	               || card.equals(Cards.royalCarriage)
	               || card.equals(Cards.crown);
	}

	private static void printStats(HashMap<String, Double> wins, int gameCount, String gameType) {
        if (!test || gameCount == 1) {
            return;
        }

        double totalGameCount = 0;
        Iterator<Entry<String, Double>> it = wins.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Double> e = it.next();
            totalGameCount += e.getValue();
        }
        gameCount = (int) totalGameCount;

        StringBuilder sb = new StringBuilder();

        String s = gameType + ":";

        String start = "" + gameCount;

        if (gameCount > 1) {
            s = start + (gameType.equals("Types") ? " types " : " games ") + s;
        }

        if (!debug) {
            while (s.length() < 30) {
                s += " ";
            }
        }
        sb.append(s);
        String winner = null;
        double high = 0.0;
        Iterator<String> keyIter = wins.keySet().iterator();

        while (keyIter.hasNext()) {
            String className = keyIter.next();
            double num = wins.get(className);
            double val = Math.round((num * 100 / gameCount));
            if (val > high) {
                high = val;
                winner = className;
            }
        }

        keyIter = wins.keySet().iterator();
        while (keyIter.hasNext()) {
            String className = keyIter.next();
            double num = wins.get(className);

            String name;
            Player pclass = playerCache.get(className);
            if (pclass != null) {
                name = pclass.getPlayerName();
            } else {
                name = className;
            }

            double val = Math.round((num * 100 / gameCount));
            String numStr = "" + (int) val;
            while (numStr.length() < 3) {
                numStr += " ";
            }

            sb.append(" ");
            if (className.equals(winner)) {
                sb.append("*");
            } else {
                sb.append(" ");
            }
            sb.append(name + " " + numStr + "%");

            winStats.put(name, Integer.parseInt(numStr.trim()));
        }

        Util.log(sb.toString());
    }

    private static void printGameTypeStats() {
        for (int i=0; i < gameTypeStats.size(); i++) {
            GameStats stats = gameTypeStats.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(stats.gameType);
            if (stats.gameType.toString().length() < 8) {
                sb.append("\t");
            }
            if (stats.gameType.toString().length() < 16) {
                sb.append("\t");
            }
            sb.append("\tvp=" + stats.aveVictoryPoints + "\t\tcards=" + stats.aveNumCards + "\tturns=" + stats.aveTurns + "\t"
                      + Util.cardArrayToString(stats.cards));
            Util.log(sb.toString());
        }
    }

    public int calculateLead(Player player) {
        int playerVictoryPoints = -999;
        int otherHigh = -999;

        int[] vps = calculateVps();
        for (int i = 0; i < vps.length; i++) {
            if (players[i].equals(player)) {
                playerVictoryPoints = vps[i];
            } else if (vps[i] > otherHigh) {
                otherHigh = vps[i];
            }
        }

        return playerVictoryPoints - otherHigh;
    }

    private static int[] calculateVps() {
        int[] vps = new int[numPlayers];
        for (int i = 0; i < players.length; i++) {
            vps[i] = players[i].getVPs();
        }

        return vps;
    }

    protected static void processArgs(String[] args) throws ExitException {
        // dont remove tabs in following to keep easy upstream-mergeability
        processNewGameArgs(args);
        processUserPrefArgs(args);
    }

    protected static void processNewGameArgs(String[] args) throws ExitException {
        numPlayers = 0;
        cardsSpecifiedAtLaunch = null;
        overallWins.clear();
        GAME_TYPE_WINS.clear();
        gameTypeStats.clear();
        playerClassesAndJars.clear();
        playerCache.clear();
        numRandomEvents = 0;
        randomIncludesEvents = false;
        randomIncludesLandmarks = false;
        splitMaxEventsAndLandmarks = false;
        randomExpansions = null;
        randomExcludedExpansions = null;

        String gameCountArg = "-count";
        String debugArg = "-debug";
        String showEventsArg = "-showevents";
        String gameTypeArg = "-type";
        String numRandomEventsArg = "-eventcards";
        String numRandomLandmarksArg = "-landmarkcards";
        String randomExcludesArg = "-randomexcludes";
        String splitMaxEventsAndLandmarksArg = "-splitmaxeventslandmarks";
        String gameTypeStatsArg = "-test";
        String ignorePlayerErrorsArg = "-ignore";
        String showPlayersArg = "-showplayers";
        String siteArg = "-site=";
        String cardArg = "-cards=";
        String enemyArg = "-enemy";
        String locationArg = "-location";
        String playerHeroArg = "-p";

        for (String arg : args) {
            if (arg == null) {
                continue;
            }

            if (arg.startsWith("#")) {
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.toLowerCase().equals(debugArg)) {
                    debug = true;
                    if (showEvents.isEmpty()) {
                        for (GameEvent.EventType eventType : GameEvent.EventType.values()) {
                            showEvents.add(eventType);
                        }
                    }
                } else if (arg.toLowerCase().startsWith(enemyArg)) {
                    mEnemy = EnemyType.valueOf(arg.substring(enemyArg.length()));
                }
                else if (arg.toLowerCase().startsWith(locationArg)) {
                    mLocation = LocationType.valueOf(arg.substring(locationArg.length()));
                }
                else if (arg.toLowerCase().startsWith(playerHeroArg)) {
                    mPlayer1Hero = HeroType.valueOf(arg.substring(playerHeroArg.length() + 1));
                }
                else if (arg.toLowerCase().startsWith(showEventsArg)) {
                    String showEventsString = arg.substring(showEventsArg.length() + 1);
                    for (String event : showEventsString.split(",")) {
                        showEvents.add(GameEvent.EventType.valueOf(event));
                    }
                } else if (arg.toLowerCase().startsWith(showPlayersArg)) {
                    String showPlayersString = arg.substring(showPlayersArg.length() + 1);
                    for (String player : showPlayersString.split(",")) {
                        showPlayers.add(player);
                    }
                } else if (arg.toLowerCase().startsWith(ignorePlayerErrorsArg)) {
                    if (arg.trim().toLowerCase().equals(ignorePlayerErrorsArg)) {
                        ignoreAllPlayerErrors = true;
                    } else {
                        ignoreSomePlayerErrors = true;

                        try {
                            String player = arg.substring(ignorePlayerErrorsArg.length()).trim();
                            ignoreList.add(player);
                        } catch (Exception e) {
                            Util.log(e);
                            throw new ExitException();
                        }
                    }
                } else if (arg.toLowerCase().equals(gameTypeStatsArg)) {
                    test = true;
                } else if (arg.toLowerCase().startsWith(gameCountArg)) {
                    try {
                        numGames = Integer.parseInt(arg.substring(gameCountArg.length()));
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(cardArg)) {
                    try {
                        cardsSpecifiedAtLaunch = arg.substring(cardArg.length()).split("-");
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(siteArg)) {
                    try {
                        // UI.downloadSite =
                        // arg.substring(siteArg.length());
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomEventsArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomEventsArg.length()));
                        if (num != 0) {
                        	randomIncludesEvents = true;
                        	numRandomEvents = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomLandmarksArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomLandmarksArg.length()));
                        if (num != 0) {
                        	randomIncludesLandmarks = true;
                        	numRandomLandmarks = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().equals(splitMaxEventsAndLandmarksArg)) {
                    splitMaxEventsAndLandmarks = true;
                } else if (arg.toLowerCase().startsWith(gameTypeArg)) {
                    try {
                        gameTypeStr = arg.substring(gameTypeArg.length());
                        String[] parts = gameTypeStr.split("-");
                        if (parts.length > 0) {
                        	gameTypeStr = parts[0];
                        	randomExpansions = new ArrayList<Expansion>();
                    		for (int i = 1; i < parts.length; ++i) {
                        		randomExpansions.add(Expansion.valueOf(parts[i]));
                        	}
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }

                } else if (arg.toLowerCase().startsWith(randomExcludesArg)) {
                    try {
                        String exclusions = arg.substring(randomExcludesArg.length());
                        String[] parts = exclusions.split("-");
                        if (parts.length > 0) {
                        	randomExcludedExpansions = new ArrayList<Expansion>();
                    		for (int i = 1; i < parts.length; ++i) {
                        		randomExcludedExpansions.add(Expansion.valueOf(parts[i]));
                        	}
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                }
            } else {
                String options = "";
                String name = null;
                int starIndex = arg.indexOf("*");
                if (starIndex != -1) {
                    name = arg.substring(starIndex + 1);
                    arg = arg.substring(0, starIndex);
                }
                if (arg.endsWith(QUICK_PLAY)) {
                    arg = arg.substring(0, arg.length() - QUICK_PLAY.length());
                    options += "q";
                }
                int atIndex = arg.indexOf("@");
                String className = arg;
                String jar = null;
                if (atIndex != -1) {
                    className = className.substring(0, atIndex);
                    jar = arg.substring(atIndex + 1);
                }
                playerClassesAndJars.add(new String[] { className, jar, name, options });
            }
        }

        numPlayers = playerClassesAndJars.size();

        if (numPlayers < 1 || numPlayers > 6 || showUsage) {
            Util.log("Usage: [-debug][-ignore(playername)][-count(# of Games)][-type(Game type)] class1 class2 [class3] [class4]");
            throw new ExitException();
        }

        if (gameTypeStr == null) {
            if (debug) {
                gameTypeStr = "FirstGame";
            }
        }

        if (numGames == -1) {
            numGames = 1;
        }

    }

    public static void processUserPrefArgs(String[] args) throws ExitException {
        quickPlay = false;
        sortCards = false;
        maskPlayerNames = false;
        actionChains = false;
        suppressRedundantReactions = false;
        chanceForPlatColony = -1;
        chanceForShelters = -1;
        equalStartHands = false;
        blackMarketOnlyCardsFromUsedExpansions = false;
        blackMarketSplitPileOptions = BlackMarketSplitPileOptions.NONE;
        errataMasqueradeAlwaysAffects = false;
        errataMineForced = false;
        errataMoneylenderForced = false;
        errataPossessedTakesTokens = false;
        errataThroneRoomForced = false;
        errataShuffleDeckEmptyOnly = false;
        startGuildsCoinTokens = false; //only for testing
        lessProvinces = false; //only for testing
        noCards = false;
        godMode = false; //only for testing
        controlAi = false; //only for testing
        disableAi = false;

        String quickPlayArg = "-quickplay";
        String maskPlayerNamesArg = "-masknames";
        String sortCardsArg = "-sortcards";
        String actionChainsArg = "-actionchains";
        String suppressRedundantReactionsArg = "-suppressredundantreactions";
        String platColonyArg = "-platcolony";
        String useSheltersArg = "-useshelters";
        String blackMarketCountArg = "-blackmarketcount";
        String bmSplitPileArg = "-blackmarketsplitpiles-";
        String bmOnlyUsedExpArg = "-blackmarketonlycardsfromusedexpansions";
        String equalStartHandsArg = "-equalstarthands";
        String errataThroneRoomForcedArg = "-erratathroneroomforced";
        String errataMasqueradeAlwaysAffectsArg = "-erratamasqueradealwaysaffects";
        String errataMineForcedArg = "-erratamineforced";
        String errataMoneylenderForcedArg = "-erratamoneylenderforced";
        String errataPossessionArg = "-erratapossessedtakestokens";
        String errataShuffleDeckEmptyOnlyArg = "-erratashuffledeckemptyonly";
        String startGuildsCoinTokensArg = "-startguildscointokens"; //only for testing
        String lessProvincesArg = "-lessprovinces"; //only for testing
        String noCardsArg = "-nocards";
        String godModeArg = "-godmode";
        String disableAiArg = "-disableai";
        String controlAiArg = "-controlai";

        String startPhaseArg = "-startphase";

        for (String arg : args) {
            if (arg == null) {
                continue;
            }

            if (arg.startsWith("#")) {
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.toLowerCase().equals(quickPlayArg)) {
                    quickPlay = true;
                } else if (arg.toLowerCase().equals(sortCardsArg)) {
                    sortCards = true;
                } else if (arg.toLowerCase().equals(maskPlayerNamesArg)) {
                    maskPlayerNames = true;
                } else if (arg.toLowerCase().equals(actionChainsArg)) {
                    actionChains = true;
                } else if (arg.toLowerCase().equals(suppressRedundantReactionsArg)) {
                    suppressRedundantReactions = true;
                } else if (arg.toLowerCase().startsWith(platColonyArg)) {
                	chanceForPlatColony = Integer.parseInt(arg.toLowerCase().substring(platColonyArg.length())) / 100.0;
                } else if (arg.toLowerCase().startsWith(useSheltersArg)) {
                	chanceForShelters = Integer.parseInt(arg.toLowerCase().substring(useSheltersArg.length())) / 100.0;
                } else if (arg.toLowerCase().startsWith(blackMarketCountArg)) {
                    blackMarketCount = Integer.parseInt(arg.toLowerCase().substring(blackMarketCountArg.length()));
                } else if (arg.toLowerCase().startsWith(bmSplitPileArg)) {
                    blackMarketSplitPileOptions = BlackMarketSplitPileOptions.valueOf(arg.substring(bmSplitPileArg.length()).toUpperCase());
                } else if (arg.toLowerCase().startsWith(bmOnlyUsedExpArg)) {
                    blackMarketOnlyCardsFromUsedExpansions = true;
                } else if (arg.toLowerCase().equals(equalStartHandsArg)) {
                    equalStartHands = true;
                } else if (arg.toLowerCase().equals(startGuildsCoinTokensArg)) {
                    startGuildsCoinTokens = true; //only for testing
                } else if (arg.toLowerCase().equals(noCardsArg)) {
                    noCards = true; //only for testing
                } else if (arg.toLowerCase().equals(godModeArg)) {
                    godMode = true; //only for testing
                } else if (arg.toLowerCase().equals(disableAiArg)) {
                    disableAi = true; //only for testing
                } else if (arg.toLowerCase().equals(controlAiArg)) {
                    controlAi = true; //only for testing
                } else if (arg.toLowerCase().equals(lessProvincesArg)) {
                    lessProvinces = true; //only for testing
                } else if (arg.toLowerCase().equals(errataMasqueradeAlwaysAffectsArg)) {
                	errataMasqueradeAlwaysAffects = true;
                } else if (arg.toLowerCase().equals(errataMineForcedArg)) {
                	errataMineForced = true;
                } else if (arg.toLowerCase().equals(errataMoneylenderForcedArg)) {
                	errataMoneylenderForced = true;
                } else if (arg.toLowerCase().equals(errataPossessionArg)) {
                	errataPossessedTakesTokens = true;
                } else if (arg.toLowerCase().equals(errataThroneRoomForcedArg)) {
                	errataThroneRoomForced = true;
                } else if (arg.toLowerCase().equals(errataShuffleDeckEmptyOnlyArg)) {
                	errataShuffleDeckEmptyOnly = true;
                } else if (arg.toLowerCase().startsWith(startPhaseArg)) {
                    startPhaseLength = Integer.parseInt(arg.toLowerCase().substring(startPhaseArg.length()));
                }

            }
        }
    }

    public boolean isValidAction(MoveContext context, Card action) {
        if (action == null) {
            return false;
        }

        if (!(action.is(CardType.Action, context.player))) {
            return false;
        }

        for (Card card : context.getPlayer().hand) {
            if (action.equals(card)) {
                return true;
            }
        }

        return false;
    }

    public boolean isValidBuy(MoveContext context, Card card) {
        return isValidBuy(context, card, context.getCoinAvailableForBuy());
    }

    public boolean isValidBuy(MoveContext context, Card card, int gold) {
        if (card == null) {
            return true;
        }

        // TODO: Temp hack to prevent AI from buying possession, even though human player can, since it only half works
        //       (AI will make decisions while possessed, but will try to make "good" ones)
        //        if(card.equals(Cards.possession) && context != null && context.getPlayer() != null && context.getPlayer().isAi()) {
        //            return false;
        //        }

        CardPile thePile = getPile(card);
        if (thePile == null) {
            return false;
        }
        if (context.getPlayer().getDebtTokenCount() > 0) {
        	return false;
        }
        if (!context.canBuyCards && !card.is(CardType.Event, null)) {
        	return false;
        }
        if (context.blackMarketBuyPhase) {
            if (thePile.isBlackMarket() == false) {
                return false;
            }
            if (Cards.isSupplyCard(card)) {
                return false;
            }
        }
        else if (card.is(CardType.Event, null) && context.phase != TurnPhase.Buy) {
        	return false;
        }
        else if (!card.is(CardType.Event, null)) {
            if (thePile.isSupply() == false) {
                return false;
            }
            if (!Cards.isSupplyCard(card)) {
                return false;
            }
        }

        if (isPileEmpty(card)) {
            return false;
        }

        if (context.cantBuy.contains(card)) {
            return false;
        }

        if (card.equals(Cards.grandMarket) && (context.countCardsInPlay(Cards.copper) > 0)) {
            return false;
        }

        int cost = card.getCost(context, !context.blackMarketBuyPhase);

        int potions = context.getPotions();
        if (cost <= gold && (!card.costPotion() || potions > 0)) {
            return true;
        }

        return false;
    }

    Card playBuy(MoveContext context, Card buy) {
        Player player = context.getPlayer();
        if (!context.blackMarketBuyPhase) {
            if (context.techniqueBuys > 0 && buy.is(CardType.Technique)) {
                context.techniqueBuys--;
            }
            else if (context.spellBuys > 0 && buy.is(CardType.Spell)) {
                context.spellBuys--;
            } else {
                context.buys--;
            }
        }
        
        context.spendCoins(buy.getCost(context));

        if (buy.upgradeCard() != null) {
            player.trash(player.hand.remove(buy.upgradeCard()),buy,context);
        }

        if (buy.costPotion()) {
            context.potions--;
        } else if (buy.getDebtCost(context) > 0) {
        	int debtCost = buy.getDebtCost(context);
        	context.getPlayer().controlPlayer.gainDebtTokens(debtCost);
        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
        	event.setAmount(debtCost);
            context.game.broadcastEvent(event);
        }

        int embargos = getEmbargos(buy);
        for (int i = 0; i < embargos; i++) {
            player.gainNewCard(Cards.curse, Cards.embargo, context);
        }
        
        // Tax Debt tokens
        int numDebtTokensOnPile = getPileDebtTokens(buy); 
        if (numDebtTokensOnPile > 0) {
        	removePileDebtTokens(buy, numDebtTokensOnPile, context);
        	context.getPlayer().controlPlayer.gainDebtTokens(numDebtTokensOnPile);
        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
        	event.setAmount(numDebtTokensOnPile);
            context.game.broadcastEvent(event);
        }

        /* GameEvent.CardType.BuyingCard must be after overpaying! */
        
        // cost adjusted based on any cards played or card being bought
        int cost = buy.getCost(context);
        
        Card card = buy;
        if(!buy.is(CardType.Event, null)) {
            card = takeFromPileCheckTrader(buy, context);
        }

        // If card can be overpaid for, do so now
        if (buy.isOverpay(player))
        {
            int coinOverpay = player.amountToOverpay(context, buy);
            coinOverpay = Math.max(0,  coinOverpay);
            coinOverpay = Math.min(coinOverpay, context.getCoinAvailableForBuy());
            context.overpayAmount = coinOverpay;
            
            context.spendCoins(context.overpayAmount);

            if (context.potions > 0)
            {
            	int potionOverpay = player.overpayByPotions(context, context.potions);
            	potionOverpay = Math.max(0, potionOverpay);
            	potionOverpay = Math.min(potionOverpay, context.getPotions());
                context.overpayPotions = potionOverpay;
                context.potions -= context.overpayPotions;
            }

            if (context.overpayAmount > 0 || context.overpayPotions > 0)
            {
            	GameEvent event = new GameEvent(GameEvent.EventType.OverpayForCard, (MoveContext) context);
                event.card = card;
                event.newCard = true;
                broadcastEvent(event);
            }
        }
        else
        {
            context.overpayAmount  = 0;
            context.overpayPotions = 0;
        }
                
        buy.isBuying(context);
        
        if(!buy.is(CardType.Event, null)) {
        	if (player.getHand().size() > 0 && isPlayerSupplyTokenOnPile(buy, player, PlayerSupplyToken.Trashing)) {
                Card cardToTrash = player.controlPlayer.trashingToken_cardToTrash((MoveContext) context);
                if (cardToTrash != null) {
                	if (!player.getHand().contains(cardToTrash)) {
                		Util.playerError(player, "Trashing token error, invalid card to trash, ignoring.");
                	} else {
                		player.hand.remove(cardToTrash);
                		player.trash(cardToTrash, null, context);
                	}
                }
        	}
        	
	        for (int i=0; i < swampHagAttacks(player); i++) {
	            player.gainNewCard(Cards.curse, Cards.swampHag, context);                    	
	        }
	        
	        if (hauntedWoodsAttacks(player)) {
	            if(player.hand.size() > 0) {
	                Card[] order;
	                if (player.hand.size() == 1)
	                    order = player.hand.toArray();
	                else
	                    order = player.controlPlayer.mandarin_orderCards(context, player.hand.toArray());
	
	                for (int i = order.length - 1; i >= 0; i--) {
	                    Card c = order[i];
	                    player.putOnTopOfDeck(c);
	                    player.hand.remove(c);
	                }                            
	            }
	        }
        }
        
        if (card != null) {
            GameEvent event = new GameEvent(GameEvent.EventType.BuyingCard, (MoveContext) context);
            event.card = card;
            event.newCard = true;
            broadcastEvent(event);
        }

        if (!buy.costPotion() && buy.getDebtCost(context) == 0 && !(buy.is(CardType.Victory)) && cost < 5 && !buy.is(CardType.Event)) {
            for (int i = 1; i <= context.countCardsInPlay(Cards.talisman); i++) {
                if (card.equals(getPile(card).topCard())) {
                    context.getPlayer().gainNewCard(buy, Cards.talisman, context);
                }
            }
        }

        if(!buy.is(CardType.Event)) {
            player.addVictoryTokens(context, context.countGoonsInPlay(), Cards.goons);
        }

        if (!buy.is(CardType.Event) && context.countMerchantGuildsInPlayThisTurn() > 0)
        {
            player.gainGuildsCoinTokens(context.countMerchantGuildsInPlayThisTurn());
            GameEvent event   = new GameEvent(GameEvent.EventType.GuildsTokenObtained, context);
            broadcastEvent(event);
        }

        if (buy.is(CardType.Victory)) {
            context.victoryCardsBoughtThisTurn++;
            for (int i = 1; i <= context.countCardsInPlay(Cards.hoard); i++) {
                player.gainNewCard(Cards.gold, Cards.hoard, context);
            }
        }

        buy.isBought(context);
        if(!buy.is(CardType.Event)) {
        	haggler(context, buy);
        	charmWhenBuy(context, buy);
        	basilicaWhenBuy(context);
        	colonnadeWhenBuy(context, buy);
        	defiledShrineWhenBuy(context, buy);
        }
        
        return card;
    }

    private void haggler(MoveContext context, Card cardBought) {
    }
    
    private void charmWhenBuy(MoveContext context, Card buy) {
    	Player player = context.getPlayer();
    	if (context.charmsNextBuy > 0) {
    		//is there another valid card to gain?
    		boolean validCard = validCharmCardLeft(context, buy);
    		while (context.charmsNextBuy-- > 0) {
    			if (validCard) {
	        		Card toGain = player.controlPlayer.charm_cardToObtain(context, buy);
	        		if (toGain != null) {
		        		if (!isValidCharmCard(context, buy, toGain)) {
		        			Util.playerError(player, "Charm card to gain invalid, ignoring");
		        		} else {
		        			player.gainNewCard(toGain, Cards.charm, context);
		        		}
	        		}
	        		validCard = validCharmCardLeft(context, buy);
    			}
        	}
    	}
    }
    
    private boolean validCharmCardLeft(MoveContext context, Card buy) {
    	for (Card c : context.game.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
			if (isValidCharmCard(context, buy, c)) {
				return true;
			}
		}
    	return false;
    }
    
    private boolean isValidCharmCard(MoveContext context, Card buy, Card c) {
    	return !buy.equals(c) && context.game.isCardOnTop(c) &&
				!context.game.isPileEmpty(c) &&
				Cards.isSupplyCard(c) &&
				buy.getCost(context) == c.getCost(context) &&
				buy.getDebtCost(context) == c.getDebtCost(context) &&
				(buy.costPotion() == c.costPotion());
    }
    
    private void basilicaWhenBuy(MoveContext context) {
    	//TODO?: Can resolve Basilica before overpay to not get tokens in some cases (would matter with old Possession rules)
    	if (cardInGame(Cards.basilica) && (context.getCoins() + context.overpayAmount) >= 2) {
    		int tokensLeft = getPileVpTokens(Cards.basilica);
    		if (tokensLeft > 0) {
    			int tokensToTake = Math.min(tokensLeft, 2);
    			removePileVpTokens(Cards.basilica, tokensToTake, context);
    			context.getPlayer().addVictoryTokens(context, tokensToTake, Cards.basilica);
    		}
    	}
    }
    
    private void colonnadeWhenBuy(MoveContext context, Card buy) {
    	 if(buy.is(CardType.Action, context.getPlayer())) {
	    	if (cardInGame(Cards.colonnade)) {
	    		Player player = context.getPlayer();
	    		if (player.playedCards.contains(buy) || player.nextTurnCards.contains(buy)) {
	    			int tokensLeft = getPileVpTokens(Cards.colonnade);
            		if (tokensLeft > 0) {
            			int tokensToTake = Math.min(tokensLeft, 2);
            			removePileVpTokens(Cards.colonnade, tokensToTake, context);
            			player.addVictoryTokens(context, tokensToTake, Cards.colonnade);
            		}
	    		}
	    	}
	    }
    }
    
    private void defiledShrineWhenBuy(MoveContext context, Card buy) {
    	//TODO?: Can resolve Basilica before overpay to not get tokens in some cases (would matter with old Possession rules)
    	 if(buy.equals(Cards.curse)) {
	    	if (cardInGame(Cards.defiledShrine)) {
	    		int tokensLeft = getPileVpTokens(Cards.defiledShrine);
	    		if (tokensLeft > 0) {
	    			removePileVpTokens(Cards.defiledShrine, tokensLeft, context);
	    			context.getPlayer().addVictoryTokens(context, tokensLeft, Cards.defiledShrine);
	    		}
	    	}
	    }
    }
    
    public boolean buyWouldEndGame(Card card) {
        if (isColonyInGame() && card.equals(Cards.colony)) {
            if (pileSize(card) <= 1) {
                return true;
            }
        }

        if (card.equals(Cards.province)) {
            if (pileSize(card) <= 1) {
                return true;
            }
        }

        if (emptyPiles() >= 2 && pileSize(card) <= 1) {
            return true;
        }

        return false;
    }

    private boolean checkGameOver() {
        if (isColonyInGame() && isPileEmpty(Cards.colony)) {
            return true;
        }

        if (isPileEmpty(Cards.province)) {
            return true;
        }

        switch (numPlayers) {
            case 1:
            case 2:
            case 3:
            case 4:
                /* Ends game for 1, 2, 3 or 4 players */
                if (emptyPiles() >= 3) {
                    return true;
                }
                break;
            case 5:
            case 6:
                /* Ends game for 5 or 6 players */
                if (emptyPiles() >= 4) {
                    return true;
                }
        }

        return false;
    }

    // Use drawToHand when "drawing" or "+ X cards" when -1 Card token could be drawn instead
    boolean drawToHand(MoveContext context, Card responsible, int cardsLeftToDraw) {
        return drawToHand(context, responsible, cardsLeftToDraw, true);
    }

    boolean drawToHand(MoveContext context, Card responsible, int cardsLeftToDraw, boolean showUI) {
    	Player player = context.player;
    	if (player.getMinusOneCardToken()) {
    		player.setMinusOneCardToken(false, context);
    		return true;
    	}
        Card card = draw(context, responsible, cardsLeftToDraw);
        if (card == null)
            return false;

        if (responsible != null) {
            Util.debug(player, responsible.getName() + " draw:" + card.getName(), true);
        }

        player.hand.add(card, showUI);

        return true;
    }

    // Use draw when removing a card from the top of the deck without "drawing" it (e.g. look at or reveal)
    Card draw(MoveContext context, Card responsible, int cardsLeftToDraw) {
    	if (errataShuffleDeckEmptyOnly) {
	    	if (context.player.deck.isEmpty()) {
	            if (context.player.discard.isEmpty()) {
	                return null;
	            } else {
	                replenishDeck(context, responsible, cardsLeftToDraw);
	            }
	        }
    	} else {
    		if (cardsLeftToDraw > 0 && context.player.deck.size() < cardsLeftToDraw) {
    			ArrayList<Card> cardsToDraw = new ArrayList<Card>();
    			for (Card c : context.player.deck) {
    				cardsToDraw.add(c);
    			}
    			context.player.deck.clear();
    			if (!context.player.discard.isEmpty()) {
    				replenishDeck(context, responsible, cardsLeftToDraw - cardsToDraw.size());
    			}
    			if (context.player.deck.isEmpty() && cardsToDraw.isEmpty()) {
    				return null;
    			}
    			Collections.reverse(cardsToDraw);
    			for (Card c : cardsToDraw) {
    				context.player.deck.add(0, c);
    			}
    		} else if (context.player.deck.isEmpty()) {
	            if (context.player.discard.isEmpty()) {
	                return null;
	            } else {
	                replenishDeck(context, responsible, cardsLeftToDraw);
	            }
	        }
    	}
        return context.player.deck.remove(0);
    }

    public void replenishDeck(MoveContext context, Card responsible, int cardsLeftToDraw) {
        context.player.replenishDeck(context, responsible, cardsLeftToDraw);
        
        GameEvent event = new GameEvent(GameEvent.EventType.DeckReplenished, context);
        broadcastEvent(event);
    }

    private void handleShowEvent(GameEvent event) {
        if (showEvents.contains(event.getType())) {
            Player player = event.getPlayer();

            if (player == null || (player != null && !showPlayers.isEmpty() && !showPlayers.contains(player.getPlayerName()))) {
                return;
            }

            if (event.getType() == GameEvent.EventType.TurnEnd) {
                Util.debug("");
                return;
            }

            StringBuilder msg = new StringBuilder();
            msg.append(player.getPlayerName() + "::" + turnCount + ":" + event.getType());

            if (event.getType() == GameEvent.EventType.BuyingCard) {
                msg.append(":" + event.getContext().getCoinAvailableForBuy() + " gold");
                if (event.getContext().getBuysLeft() > 0) {
                    msg.append(", buys remaining: " + event.getContext().getBuysLeft() + ")");
                }
            } else if (event.getType() == GameEvent.EventType.PlayingCard || event.getType() == GameEvent.EventType.TurnBegin
                       || event.getType() == GameEvent.EventType.NoBuy) {
                msg.append(":" + getHandString(player));
            } else if (event.attackedPlayer != null) {
                msg.append(":" + event.attackedPlayer.getPlayerName() + " with " + event.card.getName());
            }

            if (event.card != null) {
                msg.append(" -> " + event.card.getName());
            }

            if (event.getComment() != null) {
                msg.append(event.getComment());
            }

            Util.debug(msg.toString());
        }
    }

    int totalCardCount() {
        HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
        for (String cardName : piles.keySet()) {
            cardCounts.put(cardName, piles.get(cardName).getCount());
        }
        for (Card card : trashPile) {
            cardCounts.put(card.getName(), cardCounts.get(card.getName()) + 1);
        }
        for (int i = 0; i < numPlayers; i++) {
            for (Card card : players[i].getAllCards()) {
                cardCounts.put(card.getName(), cardCounts.get(card.getName()) + 1);
            }
        }
        Util.log(cardCounts.toString());

        int totalCardCount = 0;
        for (Integer v : cardCounts.values()) {
            totalCardCount += v;
        }

        return totalCardCount;
    }

    void initGameBoard() throws ExitException {
        cardSequence = 1;
        baneCard = null;
        firstProvinceWasGained = false;
        doMountainPassAfterThisTurn = false;

        initGameListener();

        initCards();
        initPlayers(numPlayers);
        initPlayerCards();


        gameOver = false;
    }

    protected void initPlayers(int numPlayers) throws ExitException {
        initPlayers(numPlayers, true);
    }

    @SuppressWarnings("unchecked")
    protected void initPlayers(int numPlayers, boolean isRandom) throws ExitException {
        players = new Player[numPlayers];
        cardsObtainedLastTurn = new ArrayList[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            cardsObtainedLastTurn[i] = new ArrayList<Card>();
        }

        ArrayList<String[]> randomize = new ArrayList<String[]>();
        while (!playerClassesAndJars.isEmpty()) {
            if(isRandom) {
                randomize.add(playerClassesAndJars.remove(rand.nextInt(playerClassesAndJars.size())));
            } else {
                randomize.add(playerClassesAndJars.remove(0));
            }
        }

        playerClassesAndJars = randomize;
        playerCache.clear();
        playersTurn = 0;

        for (int i = 0; i < numPlayers; i++) {
            try {
                String[] playerStartupInfo = playerClassesAndJars.get(i);
                if (playerStartupInfo[1] == null) {
                    players[i] = (Player) Class.forName(playerStartupInfo[0]).newInstance();
                } else {
                    String key = playerStartupInfo[0] + "::" + playerStartupInfo[1];
                    // players[i] = cachedPlayers.get(key);
                    //
                    // if(players[i] == null) {
                    // URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(classAndJar[1]) });
                    // players[i] = classLoader.loadClass(classAndJar[0]).newInstance();
                    // cachedPlayers.put(key, players[i]);
                    // }

                    Class<?> playerClass = cachedPlayerClasses.get(key);

                    if (playerClass == null) {
                        URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(playerStartupInfo[1]) });
                        playerClass = classLoader.loadClass(playerStartupInfo[0]);
                        cachedPlayerClasses.put(key, playerClass);
                    }

                    players[i] = (Player) playerClass.newInstance();
                }
                if(playerStartupInfo[2] != null) {
                    players[i].setName(playerStartupInfo[2]);
                }
                //String options = playerStartupInfo[3];
                playerCache.put(playerStartupInfo[0], players[i]);
            } catch (Exception e) {
                Util.log(e);
                throw new ExitException();
            }

            players[i].game = this;
            players[i].playerNumber = i;

            // Interactive player needs this called once for each player on startup so internal counts work properly.
            players[i].getPlayerName();
            
            MoveContext context = new MoveContext(this, players[i]);
            players[i].newGame(context);
            players[i].initCards();

            context = new MoveContext(this, players[i]);
            String s = cardListText + "\n---------------\n\n";
            
            if (platColonyPassedIn || chanceForPlatColony > 0.9999) {
                s += "Platinum/Colony included...\n";
            } else if (platColonyNotPassedIn || Math.round(chanceForPlatColony * 100) == 0) {
                s += "Platinum/Colony not included...\n";
            } else {
                s += "Chance for Platinum/Colony\n   " + (Math.round(chanceForPlatColony * 100)) + "% ... " + (isPlatInGame() ? "included\n" : "not included\n");
            }

            if (baneCard != null) {
                s += "Bane card: " + baneCard.getName() + "\n";
            }

            // When Baker is included in the game, each Player starts with 1 coin token
            if (bakerInPlay)
            {
                players[i].gainGuildsCoinTokens(1);
            }
            //only for testing
            if (startGuildsCoinTokens && !players[i].isAi())
            {
                players[i].gainGuildsCoinTokens(99);
            }

            /* The journey token is face up at the start of a game.
             * It can be turned over by Ranger, Giant and Pilgrimage.
             */
            if (journeyTokenInPlay)
            {
                players[i].flipJourneyToken(null);
            }

            if (sheltersPassedIn || chanceForShelters > 0.9999) {
                s += "Shelters included...\n";
            } else if (sheltersNotPassedIn || Math.round(chanceForShelters * 100) == 0) {
                s += "Shelters not included...\n";
            }
            else {
                s += "Chance for Shelters\n   " + (Math.round(chanceForShelters * 100)) + "% ... " + (sheltersInPlay ? "included\n" : "not included\n");
            }

            s += unfoundCardText;
            context.message = s;
            broadcastEvent(new GameEvent(GameEvent.EventType.GameStarting, context));

        }
    }

    protected void initPlayerCards() {
        Player player;
        for (int i = 0; i < numPlayers; i++) {
            player = players[i];
            if (bulwarkInPlay)
            {
                switch (mPlayer1Hero) {
                    case Arpad:
                        player.discard(takeFromPile(Cards.attack), null, null);
                        player.discard(takeFromPile(Cards.attack), null, null);
                        player.discard(takeFromPile(Cards.shield), null, null);
                        /*for (int j = 0; j<15;j++)
                            player.discard(takeFromPile(Cards.virtualWound), null, null);

                        */
                        break;

                    case Sandor:
                        player.discard(takeFromPile(Cards.orison), null, null);
                        player.discard(takeFromPile(Cards.orison), null, null);
                        player.discard(takeFromPile(Cards.beat), null, null);
                        break;
                    case Wiola:
                        player.discard(takeFromPile(Cards.stalk), null, null);
                        player.discard(takeFromPile(Cards.shoot), null, null);
                        player.discard(takeFromPile(Cards.shoot), null, null);
                        break;
                    case ZsuZsa:
                        player.discard(takeFromPile(Cards.knifeInTheBack), null, null);
                        player.discard(takeFromPile(Cards.sidestep), null, null);
                        player.discard(takeFromPile(Cards.sidestep), null, null);
                        break;
                    case Jakab:
                        player.discard(takeFromPile(Cards.magicMissile), null, null);
                        player.discard(takeFromPile(Cards.magicMissile), null, null);
                        player.discard(takeFromPile(Cards.crystalOrb), null, null);
                        break;
                }
                //TODO Bulwark multiplayer
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);
                player.discard(takeFromPile(Cards.one), null, null);



            }
            else {
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);
                player.discard(takeFromPile(Cards.copper), null, null);

                if (sheltersInPlay) {
//                    player.discard(takeFromPile(Cards.necropolis), null, null);
//                    player.discard(takeFromPile(Cards.overgrownEstate), null, null);
//                    player.discard(takeFromPile(Cards.hovel), null, null);

                    // Also need to remove the Estates that were put in the pile prior to
                    // determining if Shelters would be used
                    takeFromPile(Cards.estate);
                    takeFromPile(Cards.estate);
                    takeFromPile(Cards.estate);
                } else {
                    player.discard(takeFromPile(Cards.estate), null, null);
                    player.discard(takeFromPile(Cards.estate), null, null);
                    player.discard(takeFromPile(Cards.estate), null, null);
                }
            }
            if (!equalStartHands || i == 0) {
                while (player.hand.size() < 5)
                    drawToHand(new MoveContext(this, player), null, 5 - player.hand.size(), false);
            }
            else {
                // make subsequent player hands equal
                for (int j = 0; j < 5; j++) {
                    Card card = players[0].hand.get(j);
                    player.discard.remove(card);
                    player.hand.add(card);
                }
                player.replenishDeck(null, null, 0);
            }
        }

        if (noCards) //only for testing
        {
            for (int i = 0; i < numPlayers; i++) {
                player = players[i];
                if (player.isAi())
                {
                    continue;
                }
                player.hand.clear();
                player.deck.clear();
                player.discard.clear();
            }
        }
        
        // Add tradeRoute tokens if tradeRoute in play
        tradeRouteValue = 0;
        if (cardInGame(Cards.tradeRoute)) {
            for (CardPile pile : piles.values()) {
                if ((pile.placeholderCard().is(CardType.Victory)) && pile.isSupply()) {
                    pile.setTradeRouteToken();
                }
            }
        }

    }

    protected void initCards() {
        piles.clear();
        embargos.clear();
        pileVpTokens.clear();
        playerSupplyTokens.clear();
        trashPile.clear();
        blackMarketPile.clear();
        blackMarketPileShuffled.clear();

        platColonyNotPassedIn = false;
        platColonyPassedIn = false;
        sheltersNotPassedIn = false;
        sheltersPassedIn = false;

        int provincePileSize = -1;
        int curseCount = -1;
        int treasureMultiplier = 1;

        switch (numPlayers) {
            case 1:
            case 2:
                curseCount = 10;
                provincePileSize = 8;
                victoryCardPileSize = 8;
                break;
            case 3:
                curseCount = 20;
                provincePileSize = 12;
                victoryCardPileSize = 12;
                break;
            case 4:
                curseCount = 30;
                provincePileSize = 12;
                victoryCardPileSize = 12;
                break;
            case 5:
                curseCount = 40;
                provincePileSize = 15;
                victoryCardPileSize = 12;
                treasureMultiplier = 2;
                break;
            case 6:
                curseCount = 50;
                provincePileSize = 18;
                victoryCardPileSize = 12;
                treasureMultiplier = 2;
                break;
        }
        //only for testing
        if (lessProvinces)
        {
            provincePileSize = 1; 
        }

//        addPile(Cards.gold, 30 * treasureMultiplier);
//        addPile(Cards.silver, 40 * treasureMultiplier);
//        addPile(Cards.copper, 60 * treasureMultiplier);
//
//        addPile(Cards.curse, curseCount);
//        addPile(Cards.province, provincePileSize);
//        addPile(Cards.duchy, victoryCardPileSize);
//        addPile(Cards.estate, victoryCardPileSize + (3 * numPlayers));

        unfoundCards.clear();
        int added = 0;

        if(cardsSpecifiedAtLaunch != null) {
            platColonyNotPassedIn = true;
            sheltersNotPassedIn = true;
            for(String cardName : cardsSpecifiedAtLaunch) {
                Card card = null;
                boolean bane = false;
                boolean obelisk = false;
                boolean blackMarket = false;

                if(cardName.startsWith(BANE)) {
                    bane = true;
                    cardName = cardName.substring(BANE.length());
                }
                if(cardName.startsWith(OBELISK)) {
                    obelisk = true;
                    cardName = cardName.substring(OBELISK.length());
                }
                if(cardName.startsWith(BLACKMARKET)) {
                    blackMarket = true;
                    cardName = cardName.substring(BLACKMARKET.length());
                }
                String s = cardName.replace("/", "").replace(" ", "");
                for (Card c : Cards.actionCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.eventsCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.landmarkCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                // Handle split pile / knights cards being passed in incorrectly
                for (Card c : Cards.variablePileCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.castleCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.knightsCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                if (card != null && Cards.variablePileCardToRandomizer.containsKey(card)) {
                	card = Cards.variablePileCardToRandomizer.get(card);
                }
                
                if(card != null && bane) {
                    baneCard = card;
                }
                if(card != null && obelisk) {
                    obeliskCard = card;
                }
                if(card != null && blackMarket) {
                    blackMarketPile.add(card);
                }

                if(card != null && !piles.containsKey(card.getName())) {
                    addPile(card);
                    added += 1;
                } else if ( s.equalsIgnoreCase(Cards.curse.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.estate.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.duchy.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.province.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.copper.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.silver.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.potion.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.gold.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.diadem.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.followers.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.princess.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.trustySteed.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.bagOfGold.getSafeName()) ) {
                    // do nothing
                } else if ( s.equalsIgnoreCase(Cards.platinum.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.colony.getSafeName()) ) {
                    platColonyPassedIn = true;
                } else {
                    unfoundCards.add(s);
                    Util.debug("ERROR::Could not find card:" + s);
                }
            }
            
            for(String s : unfoundCards) {
                if (added >= 10)
                    break;
                Card c = null;
                int replacementCost = -1;

                if(replacementCost != -1) {
                    ArrayList<Card> cardsWithSameCost = new ArrayList<Card>();
                    for (Card card : Cards.actionCards) {
                        if(card.getCost(null) == replacementCost && !cardInGame(card)) {
                            cardsWithSameCost.add(card);
                        }
                    }

                    if(cardsWithSameCost.size() > 0) {
                        c = cardsWithSameCost.get(rand.nextInt(cardsWithSameCost.size()));
                    }
                }

                while(c == null) {
                    c = Cards.actionCards.get(rand.nextInt(Cards.actionCards.size()));
                    if(cardInGame(c)) {
                        c = null;
                    }
                }

                Util.debug("Adding replacement for " + s + ": " + c);
                addPile(c);
                added += 1;
            }

            gameType = GameType.Specified;
        } else if (gameType == GameType.Bulwark) {
            switch (mPlayer1Hero) {
                case Sandor:

                for (Card c : Cards.heroCardsSandor) {
                    addPile(c);
                    added++;
                }
                break;
                case Arpad:
                    for (Card c : Cards.heroCardsArpad) {
                        addPile(c);
                        added++;
                    }
                    break;
                case ZsuZsa:
                    for (Card c : Cards.heroCardsZsuzsa) {
                        addPile(c);
                        added++;
                    }
                    break;
                case Wiola:
                    for (Card c : Cards.heroCardsWiola) {
                        addPile(c);
                        added++;
                    }
                    break;
                case Jakab:
                    for (Card c : Cards.heroCardsJakab) {
                        addPile(c);
                        added++;
                    }
                    break;
            }

            switch (mLocation) {
                case Citadel:

                    for (Card c : Cards.locationCardsCitadelXajorkith) {
                        addPile(c);
                        added++;
                    }
                    break;
                case Briggs:
                    for(Card c : Cards.locationCardsFortBriggs) {
                        addPile(c);
                        added++;
                    }
                    break;
                case Holtvaros:
                    for (Card c : Cards.locationCardsHoltvaros) {
                        addPile(c);
                        added++;
                    }
                    break;
                case Sylvan:
                    for (Card c: Cards.locationCardsSylvanHeights) {
                        addPile(c);
                        added++;
                    }
                    break;
                case SecretLore:
                    for (Card c: Cards.locationCardsSecretLore) {
                        addPile(c);
                        added++;
                    }
                    break;
            }

            switch (mEnemy) {
                case Goblin:
                    addPile(Cards.virtualEnemy = new CardImpl.Builder(Cards.Kind.VirtualEnemy, 3, mEnemy.getName(), CardType.Enemy).pileCreator(new GoblinPileCreator()).expansion(Expansion.Base).build(),
                            30, false);
                    break;
                case Lizard:
                    addPile(Cards.virtualEnemy = new CardImpl.Builder(Cards.Kind.VirtualEnemy, 3, mEnemy.getName(), CardType.Enemy).pileCreator(new LizardPileCreator()).expansion(Expansion.Base).build());
                    break;
                case Elf:
                    addPile(Cards.virtualEnemy = new CardImpl.Builder(Cards.Kind.VirtualEnemy, 3, mEnemy.getName(), CardType.Enemy).pileCreator(new elfPileCreator()).expansion(Expansion.Base).build());
                    break;
                case Winter:
                    addPile(Cards.virtualEnemy = new CardImpl.Builder(Cards.Kind.VirtualEnemy, 3, mEnemy.getName(), CardType.Enemy).pileCreator(new winterPileCreator()).expansion(Expansion.Base).build());
                    break;
                case Messianic:
                    addPile(Cards.virtualEnemy = new CardImpl.Builder(Cards.Kind.VirtualEnemy, 3, mEnemy.getName(), CardType.Enemy).pileCreator(new messianicPileCreator()).expansion(Expansion.Arcane).build());
                    break;
            }
            Cards.cardNameToCard.put(Cards.virtualEnemy.getName(),Cards.virtualEnemy);

            added++;

            addPile(Cards.virtualWound,30,false);

            added++;
        }
            else {
            CardSet cardSet = CardSet.getCardSet(gameType, -1, randomExpansions, randomExcludedExpansions, randomIncludesEvents, numRandomEvents, randomIncludesLandmarks, numRandomLandmarks, !splitMaxEventsAndLandmarks, true);
            if (cardSet == null) {
                cardSet = CardSet.getCardSet(CardSet.defaultGameType, -1);
            }

            for (Card card : cardSet.getCards()) {
                this.addPile(card);
            }

            if (cardSet.getBaneCard() != null) {
                this.baneCard = cardSet.getBaneCard();
                //Adding the bane card could probably be done in the CardSet class, but it seems better to call it out explicitly.
                this.addPile(this.baneCard);
            }
        }

        // Black Market
        Cards.blackMarketCards.clear();
        if (piles.containsKey(Cards.blackMarket.getName()))
        {
            List<Card> allCards;

            // get 10 cards more then needed. Extract the cards in supply
            int count = Math.max(blackMarketCount - blackMarketPile.size(), 0);
            if (blackMarketOnlyCardsFromUsedExpansions) {
                List<Expansion> expansions = new ArrayList<Expansion>();
                if (randomExpansions != null && randomExpansions.size() > 0) {
                    expansions.addAll(randomExpansions);
                } else {
                    for (CardPile pile : placeholderPiles.values()) {
                        if (pile != null &&
                                pile.placeholderCard() != null &&
                                pile.placeholderCard().getExpansion() != null &&
                                Cards.isKingdomCard(pile.placeholderCard()) &&
                                !expansions.contains(pile.placeholderCard().getExpansion()) &&
                                pile.placeholderCard().getExpansion() != Expansion.Promo) {
                            expansions.add(pile.placeholderCard().getExpansion());
                        }
                    }
                }
                allCards = CardSet.getCardSet(GameType.Random, count+10, expansions, randomExcludedExpansions, false, 0, false, 0, false, false).getCards();
            } else {
                allCards = CardSet.getCardSet(GameType.Random, count+10).getCards();
            }

            List<Card> remainingCards = new ArrayList<Card>();
            for (int i = 0; i < allCards.size(); i++) {
                if (!piles.containsKey(allCards.get(i).getName())) {
                	CardPile tempPile = allCards.get(i).getPileCreator().create(allCards.get(i), 12); //count doesn't matter as we only need templates
                    ArrayList<Card> templates = tempPile.getTemplateCards();
                	if (Cards.variablePileCards.contains(templates.get(0)) || Cards.castleCards.contains(templates.get(0))) {
                		if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ANY) {
                            for (Card card : templates) {
                                remainingCards.add(card);
                            }
                    	} else if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ONE) {
                            remainingCards.add(Util.randomCard(templates));
                        } else if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ALL) {
                            remainingCards.add(allCards.get(i));
                        }
                	} else {
                    	remainingCards.add(Util.randomCard(templates));
                    }
                }
            }
            // take count cards from the rest
            List<Card> cards = CardSet.getRandomCardSet(remainingCards, count).getCards();
            
            //Force remaining split pile cards into black market deck if one from a split pile is in
            if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ALL) {
            	ArrayList<Card> extraCards = new ArrayList<Card>();
            	for (int i = 0; i < cards.size(); ++i) {
            		Card c = cards.get(i);
            		ArrayList<Card> templates = c.getPileCreator().create(allCards.get(i), 12).getTemplateCards(); //count doesn't matter as we only need templates
            		if (templates.size() > 1) {
            			cards.set(i, templates.get(0));
            			for (int j = 1; j < templates.size(); ++j) {
            				extraCards.add(templates.get(j));
            			}
            		}
            	}
            	int cardsToRemove = extraCards.size() - (count - cards.size());
            	for (int n = 0; n < cardsToRemove; ++n) {
            		for (int i = 0; i < cards.size(); ++i) {
            			if (!Cards.variablePileCards.contains(cards.get(i)) && !Cards.castleCards.contains(cards.get(i))) {
            				cards.remove(i);
            				break;
            			}
            		}
            	}
            	for (Card c : extraCards) {
            		if (cards.size() < count)
            			cards.add(c);
            	}
            }
            
            for (int i = 0; i < cards.size(); i++) {
            	remainingCards.remove(cards.get(i));
                blackMarketPile.add(cards.get(i).instantiate());
            }

            if (this.baneCard == null && blackMarketPile.contains(Cards.youngWitch)) {
            	this.baneCard = CardSet.getBaneCard(remainingCards);
                if (this.baneCard != null) {
                    this.addPile(this.baneCard);
                }
            }
            // sort
            Collections.sort(blackMarketPile, new Util.CardCostNameComparator());
            // put all in piles
            cards.clear();
            for (int i = 0; i < blackMarketPile.size(); i++) {
                cards.add(blackMarketPile.get(i));
                addPile(blackMarketPile.get(i).getTemplateCard(), 1, false, true);
                Cards.blackMarketCards.add(blackMarketPile.get(i));
            }
            // shuffle
            while (cards.size() > 0) {
                blackMarketPileShuffled.add(cards.remove(Game.rand.nextInt(cards.size())));
            }
        }
        
        if (obeliskCard != null && !piles.containsKey(Cards.obelisk.getName())) {
        	addPile(Cards.obelisk);
        }

        //determine shelters & plat/colony use
        boolean alreadyCountedKnights = false;
        int darkAgesCards = 0;
        int prosperityCards = 0;
        int kingdomCards = 0;
        for (CardPile pile : placeholderPiles.values()) {
            if (pile != null &&
            		pile.placeholderCard() != null &&
            		pile.placeholderCard().getExpansion() != null &&
            		Cards.isKingdomCard(pile.placeholderCard())) {
            	kingdomCards++;
            	if (pile.placeholderCard.getExpansion() == Expansion.DarkAges) {
                    darkAgesCards++;
                }
            	if (pile.placeholderCard().getExpansion() == Expansion.Prosperity) {
                    prosperityCards++;
                }
            }
        }

        sheltersInPlay = false;
        if (sheltersPassedIn) {
            sheltersInPlay = true;
            chanceForShelters = 1;
        } else if (!(sheltersNotPassedIn && cardsSpecifiedAtLaunch != null)) {
            if (chanceForShelters > -0.0001) {
                sheltersInPlay = rand.nextDouble() < chanceForShelters;
            } else {
                chanceForShelters = darkAgesCards / (double) kingdomCards;

                if (rand.nextDouble() < chanceForShelters) {
                    sheltersInPlay = true;
                }
            }
        }



        // Check for PlatColony
        boolean addPlatColony = false;
        if (platColonyPassedIn) {
            addPlatColony = true;
        } else if (!(platColonyNotPassedIn && cardsSpecifiedAtLaunch != null)) {
            if (chanceForPlatColony > -0.0001) {
                addPlatColony = rand.nextDouble() < chanceForPlatColony;
            } else {
                chanceForPlatColony = prosperityCards / (double) kingdomCards;

                if (rand.nextDouble() < chanceForPlatColony) {
                    addPlatColony = true;
                }
            }
        }

        if (addPlatColony) {
            addPile(Cards.platinum, 12);
            addPile(Cards.colony);
        }

        // Add the potion if there are any cards that need them.
        outerloop:
        for (CardPile pile : piles.values()) {
            for (Card cardInPile : pile.getTemplateCards()) {
                if (cardInPile.costPotion()) {
                    addPile(Cards.potion, 16);
                    break outerloop;
                }
            }
        }

        boolean looter = false;
        for (CardPile pile : piles.values()) {
            for (Card cardInPile : pile.getTemplateCards()) {
                if (cardInPile.is(CardType.Looter, null)) {
                    looter = true;
                }
            }
        }
        if (looter) {
        //    CardPile rp = (CardPile) this.addPile(Cards.virtualRuins, Math.max(10, (numPlayers * 10) - 10));
        }


        if (piles.containsKey(Cards.tournament.getName()) && !piles.containsKey(Cards.bagOfGold.getName())) {
            addPile(Cards.bagOfGold, 1, false);
            addPile(Cards.diadem, 1, false);
            addPile(Cards.followers, 1, false);
            addPile(Cards.princess, 1, false);
            addPile(Cards.trustySteed, 1, false);
        }

        // If Bandit Camp, Pillage, or Marauder is in play, we'll need Spoils (non-supply)
        // If Page is in play, we'll need treasureHunter, warrior, hero, champion (non-supply)
        if (piles.containsKey(Cards.page.getName()))
        {
            addPile(Cards.treasureHunter, 5, false);
            addPile(Cards.warrior, 5, false);
            addPile(Cards.hero, 5, false);
            addPile(Cards.champion, 5, false);
        }

        // If Peasant is in play, we'll need soldier, fugitive, disciple, teacher (non-supply)
        if (piles.containsKey(Cards.peasant.getName()))
        {
            addPile(Cards.soldier, 5, false);
            addPile(Cards.fugitive, 5, false);
            addPile(Cards.disciple, 5, false);
            addPile(Cards.teacher, 5, false);
        }

        // If Baker is in play, each player starts with one coin token
        if (piles.containsKey(Cards.baker.getName()))
        {
            bakerInPlay = true;
        }
        
        // Setup for Landmarks starting with tokens
        Card[] landmarksWithTokens = {Cards.arena, Cards.basilica, Cards.battlefield, Cards.baths, Cards.colonnade, Cards.labyrinth};
        for (Card c : landmarksWithTokens) {
        	if (piles.containsKey(c.getName())) {
                addPileVpTokens(c, 6 * numPlayers, null);
            }
        }
        
        // Setup for Aqueduct
        if (piles.containsKey(Cards.aqueduct.getName())) {
        	addPileVpTokens(Cards.silver, 8, null);
            addPileVpTokens(Cards.gold, 8, null);
        }
        
        // Setup for Defiled Shrine
        if (piles.containsKey(Cards.defiledShrine.getName())) {
            for (CardPile pile : placeholderPiles.values()) {
                Card c = pile.placeholderCard();
        		if (pile.isSupply() && c.is(CardType.Action) && !c.is(CardType.Gathering)) {
        			addPileVpTokens(c, 2, null);
        		}
        	}
        }
        
        // Setup for Obelisk
        if (piles.containsKey(Cards.obelisk.getName())) {
        	if (obeliskCard == null) {
        		ArrayList<Card> validObeliskCards = new ArrayList<Card>();
            	for (String p : placeholderPiles.keySet()) {
                    CardPile pile = placeholderPiles.get(p);
                    Card placeholder = pile.placeholderCard();
            		if (pile.isSupply() && placeholder.is(CardType.Action)  && !validObeliskCards.contains(placeholder)) {
            			validObeliskCards.add(placeholder);
            		}
            	}
            	if (validObeliskCards.size() > 0) {
            		obeliskCard = validObeliskCards.get(rand.nextInt(validObeliskCards.size()));
            	}
        	}
        }
        
        // Setup for Tax
        if (piles.containsKey(Cards.tax.getName())) {
        	for (String cardName : placeholderPiles.keySet()) {
        		Card c = piles.get(cardName).placeholderCard();
        		if (Cards.isSupplyCard(c)) {
        			addPileDebtTokens(c, 1, null);
        		}
        	}
        }

        // If Ranger, Giant or Pilgrimage are in play, each player starts with a journey token faced up
        if (   piles.containsKey(Cards.ranger.getName())
            || piles.containsKey(Cards.giant.getName())
            || piles.containsKey(Cards.pilgrimage.getName()))
        {
            journeyTokenInPlay = true;
        } else {
        	journeyTokenInPlay = false;
        }

        boolean oldDebug = debug;
        if (!debug && !showEvents.isEmpty()) {
            debug = true;
        }
        Util.debug("");
        Util.debug("Cards in Play", true);
        Util.debug("---------------", true);
        cardListText += "Cards in play\n---------------\n";

        ArrayList<Card> cards = new ArrayList<Card>();
        ArrayList<Card> events = new ArrayList<Card>();
        ArrayList<Card> landmarks = new ArrayList<Card>();
        for (CardPile pile : placeholderPiles.values()) {
        	Card c = pile.placeholderCard();
        	if (Cards.isKingdomCard(c)) {
        		cards.add(c);
        	} else if (Cards.eventsCards.contains(c)) {
        		events.add(c);
        	} else if (Cards.landmarkCards.contains(c)) {
        		landmarks.add(c);
        	}
        }
        Collections.sort(cards, new Util.CardCostNameComparator());
        Collections.sort(events, new Util.CardCostNameComparator());
        Collections.sort(landmarks, new Util.CardCostNameComparator());
        
        for (Card c : cards) {
        	cardListText += Util.getShortText(c) + ((baneCard != null && c.equals(baneCard)) ? " (Bane)" + baneCard.getName() : "") + "\n";
        }
        if (!events.isEmpty()) {
        	 cardListText += "\nEvents in play\n---------------\n";
        	for (Card c : events) {
            	cardListText += Util.getShortText(c) + "\n";
            }
        }
        if (!landmarks.isEmpty()) {
        	cardListText += "\nLandmarks in play\n---------------\n";
        	for (Card c : landmarks) {
            	cardListText += c.getName() + (c.equals(Cards.obelisk) && obeliskCard != null ? " (" + obeliskCard.getName() + ")" : "") +  "\n";
            }
        }

        for (Entry<String, CardPile> cEntry : piles.entrySet()) {
            if (cEntry.getKey().equals(cEntry.getValue().placeholderCard().getName())) {
                Util.debug(cEntry.getKey() + ": " + cEntry.getValue().cards.toString());
            } else {

            }
        }

        Util.debug("");
        debug = oldDebug;

        if (unfoundCards != null && unfoundCards.size() > 0) {
            unfoundCardText += "\n";
            String cardList = "";
            boolean first = true;
            for (String s : unfoundCards) {
                if (first) {
                    first = false;
                } else {
                    cardList += "\n";
                }
                cardList += s;
            }
            cardList += "\n\n";
            unfoundCardText += "The following cards are not \navailable, so replacements \nhave been used:\n" + cardList;
        }

        // context = new MoveContext(this, null);
        // context.message = "" + ((int) chance * 100) + "% - " +
        // (platInPlay?"Yes":"No");
        // broadcastEvent(new GameEvent(GameEvent.CardType.PlatAndColonyChance,
        // context));
    }

    protected void initGameListener() {
        listeners.clear();

        gameListener = new GameEventListener() {

            public void gameEvent(GameEvent event) {
                handleShowEvent(event);

                if (event.getType() == GameEvent.EventType.GameStarting || event.getType() == GameEvent.EventType.GameOver) {
                    return;
                }

                if ((event.getType() == GameEvent.EventType.CardObtained || event.getType() == GameEvent.EventType.BuyingCard) &&
                		!event.card.is(CardType.Event, null)) {
                	
                    MoveContext context = event.getContext();
                    Player player = context.getPlayer();

                    if (player.isPossessed()) {
                        possessedBoughtPile.add(event.card);
                        MoveContext controlContext = new MoveContext(context.game, context.getPlayer().controlPlayer);
                        controlContext.getPlayer().gainCardAlreadyInPlay(event.card, Cards.possession, controlContext);
                        return;
                    }

                    //Start inheriting newly gained estate
                    if (event.card.equals(Cards.estate) && event.player.getInheritance() != null) {
                        ((CardImpl)event.card).startInheritingCardAbilities(player.getInheritance().getTemplateCard().instantiate());
                    }

                    if (context != null && event.card.is(CardType.Victory)) {
                        context.vpsGainedThisTurn += event.card.getVictoryPoints();
                    }


                    
                    // See rules explanation of Tunnel for what commandedDiscard means.
                    boolean commandedDiscard = true;
                    if(event.getType() == GameEvent.EventType.BuyingCard
                       || event.getType() == GameEvent.EventType.CardObtained) {
                        commandedDiscard = false;
                    } else if(event.responsible != null) {
                        Card r = event.responsible;
                        if (r.equals(Cards.estate) && player.getInheritance() != null) {
                        	r = player.getInheritance();
                        }
                        if(
                           r.equals(Cards.feast) ||
                           r.equals(Cards.remodel) ||
                           r.equals(Cards.swindler) ||
                           r.equals(Cards.ironworks) ||
                           r.equals(Cards.saboteur) ||
                           r.equals(Cards.upgrade) ||
                           r.equals(Cards.ambassador) ||
                           r.equals(Cards.smugglers) ||
                           r.equals(Cards.talisman) ||
                           r.equals(Cards.expand) ||
                           r.equals(Cards.forge) ||
                           r.equals(Cards.remake) ||
                           r.equals(Cards.hornOfPlenty) ||
                           r.equals(Cards.jester)
                           )
                        {
                            commandedDiscard = false;
                        }
                    }
                    boolean handled = false;

                    //Not sure if this is exactly right for the Trader, but it seems to be based on detailed card explanation in the rules
                    //The handling for new cards is done before taking the card from the pile in a different method below.

                    if (event.getPlayer() == players[playersTurn]) {
                        cardsObtainedLastTurn[playersTurn].add(event.card);
                    }
                    
                    if (cardsObtainedLastTurn[playersTurn].size() == 2) {
                    	if (cardInGame(Cards.labyrinth)) {
                    		int tokensLeft = getPileVpTokens(Cards.labyrinth);
                    		if (tokensLeft > 0) {
                    			int tokensToTake = Math.min(tokensLeft, 2);
                    			removePileVpTokens(Cards.labyrinth, tokensToTake, context);
                    			player.addVictoryTokens(context, tokensToTake, Cards.labyrinth);
                    		}
                    	}
                    }
                    
                    boolean hasInheritedWatchtower = Cards.watchTower.equals(player.getInheritance()) && player.hand.contains(Cards.estate);
                    boolean hasWatchtower = player.hand.contains(Cards.watchTower);
                    Card watchTowerCard = hasWatchtower ? Cards.watchTower : Cards.estate;
                    if (hasWatchtower || hasInheritedWatchtower) {
                        WatchTowerOption choice = context.player.controlPlayer.watchTower_chooseOption((MoveContext) context, event.card);

                        if (choice == WatchTowerOption.TopOfDeck) {
                            handled = true;
                            GameEvent watchTowerEvent = new GameEvent(GameEvent.EventType.CardRevealed, context);
                            watchTowerEvent.card = watchTowerCard;
                            watchTowerEvent.responsible = null;
                            context.game.broadcastEvent(watchTowerEvent);

                            player.putOnTopOfDeck(event.card, context, true);
                        } else if (choice == WatchTowerOption.Trash) {
                            handled = true;
                            GameEvent watchTowerEvent = new GameEvent(GameEvent.EventType.CardRevealed, context);
                            watchTowerEvent.card = watchTowerCard;
                            watchTowerEvent.responsible = null;
                            context.game.broadcastEvent(watchTowerEvent);

                            player.trash(event.card, Cards.watchTower, context);
                        }
                    }
                    
                    Card gainedCardAbility = event.card;
                    if (gainedCardAbility.equals(Cards.estate) && player.getInheritance() != null) {
                    	gainedCardAbility = player.getInheritance();
                    }

                    if(!handled) {
                        if (context.travellingFairBought && event.card.is(CardType.Equipment)) {
                            player.putOnTopOfDeck(event.card, context, true);
                        } else
                    	if (context.isRoyalSealInPlay() && context.player.controlPlayer.royalSealTravellingFair_shouldPutCardOnDeck((MoveContext) context, Cards.royalSeal, event.card)) {
                            player.putOnTopOfDeck(event.card, context, true);
                    	} else if (cardInGame(Cards.travellingFair) && context.travellingFairBought && context.player.controlPlayer.royalSealTravellingFair_shouldPutCardOnDeck((MoveContext) context, Cards.travellingFair, event.card)) {
                        	player.putOnTopOfDeck(event.card, context, true);
                        } else if (gainedCardAbility.equals(Cards.villa)) {
                        	player.hand.add(event.card);
							if (context.game.getCurrentPlayer() == player) {
									context.actions += 1;
									if (context.phase == TurnPhase.Buy) {
										context.returnToActionPhase = true;
									}
							}
                        } else if (event.responsible != null) {
                            Card r = event.responsible;
                            if (r.equals(Cards.estate) && player.getInheritance() != null) {
                            	r = player.getInheritance();
                            }

                            r = r.behaveAsCard(); //Get impersonated card
                            
                            if (r.equals(Cards.rocks)) {
                            	if (context.phase == TurnPhase.Buy && context.game.getCurrentPlayer() == player) {
                            		player.putOnTopOfDeck(event.card, context, true);
                            	} else {
                            		player.hand.add(event.card);
                            	}
                            } else {
                                player.discard(event.card, null, null, commandedDiscard, false);
                            }
                        } else {
                            player.discard(event.card, null, null, commandedDiscard, false);
                        }
                    }
                    
                    // check for when-gain callable cards
                    // NOTE: Technically this should be done in a loop, as you can call multiple cards for one when-gain.
                    //   However, since the only card here, Duplicate, will trigger another on-gain anyway
                    //   we don't need to.
                    ArrayList<Card> callableCards = new ArrayList<Card>();
                    for (Card c : player.tavern) {
                    	if (c.behaveAsCard().isCallableWhenCardGained()) {
                    		int callCost = c.behaveAsCard().getCallableWhenGainedMaxCost();
                    		if (callCost == -1 || (event.card.getCost(context) <= callCost && event.card.getDebtCost(context) == 0 && !event.card.costPotion())) {
                    			callableCards.add(c);
                    		}
                    	}
                    }
                    if (!callableCards.isEmpty()) {
                    	//ask player which card to call
                    	Collections.sort(callableCards, new Util.CardCostComparator());
                    	// we want null entry at the end for None
                    	Card[] cardsAsArray = callableCards.toArray(new Card[callableCards.size() + 1]);
                    	Card toCall = player.controlPlayer.call_whenGainCardToCall(context, event.card, cardsAsArray);
                    	if (toCall != null || callableCards.contains(toCall)) {
                    		toCall.behaveAsCard().callWhenCardGained(context, event.card);
                    	}
                    }
                    
                    if (event.card.equals(Cards.province) && !firstProvinceWasGained) {
                    	doMountainPassAfterThisTurn = true;
                    	firstProvinceWasGained = true;
                    	firstProvinceGainedBy = playersTurn;
                    }
                    
                    if(event.card.is(CardType.Treasure, player)) {
                    	if (cardInGame(Cards.aqueduct)) {
                    		//TODO?: you can technically choose the order of resolution for moving the VP
                    		//       tokens from the treasure after taking the tokens, but why would you ever do this?
                    		int tokensLeft = getPileVpTokens(event.card);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(event.card, 1, context);
                    			addPileVpTokens(Cards.aqueduct, 1, context);
                    		}
                    	}
                    }
                    if(event.card.is(CardType.Victory, player)) {
                    	if (cardInGame(Cards.battlefield)) {
                    		int tokensLeft = getPileVpTokens(Cards.battlefield);
                    		if (tokensLeft > 0) {
                    			int tokensToTake = Math.min(tokensLeft, 2);
                    			removePileVpTokens(Cards.battlefield, tokensToTake, context);
                    			player.addVictoryTokens(context, tokensToTake, Cards.battlefield);
                    		}
                    	}
                    	if (cardInGame(Cards.aqueduct)) {
                    		int tokensLeft = getPileVpTokens(Cards.aqueduct);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(Cards.aqueduct, tokensLeft, context);
                    			player.addVictoryTokens(context, tokensLeft, Cards.aqueduct);
                    		}
                    	}
                    	int groundsKeepers = context.countCardsInPlay(Cards.groundskeeper);
                    	player.addVictoryTokens(context, groundsKeepers, Cards.groundskeeper);
                    }
                    

                    if(event.card.is(CardType.Action, player)) {
                    	if (cardInGame(Cards.defiledShrine)) {
                    		//TODO?: you can technically choose the order of resolution for moving the VP
                    		//       tokens from the action to before taking the ones from Temple when it, 
                    		//       but why would you ever do this outside of old possession rules?
                    		int tokensLeft = getPileVpTokens(event.card);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(event.card, 1, context);
                    			addPileVpTokens(Cards.defiledShrine, 1, context);
                    		}
                    	}
                    }
                    
                    
                    // Achievement check...
                    if(event.getType() == GameEvent.EventType.BuyingCard && !player.achievementSingleCardFailed) {
                        if (Cards.isKingdomCard(event.getCard())) {
                            if(player.achievementSingleCardFirstKingdomCardBought == null) {
                                player.achievementSingleCardFirstKingdomCardBought = event.getCard();
                            }
                            else {
                                if(!player.achievementSingleCardFirstKingdomCardBought.equals(event.getCard())) {
                                    player.achievementSingleCardFailed = true;
                                    player.achievementSingleCardFirstKingdomCardBought = null;
                                }
                            }
                        }
                    }
                }

                boolean shouldShow = (debug || junit);
                if (!shouldShow) {
                    if (event.getType() != GameEvent.EventType.TurnBegin && event.getType() != GameEvent.EventType.TurnEnd
                        && event.getType() != GameEvent.EventType.DeckReplenished && event.getType() != GameEvent.EventType.GameStarting) {
                        shouldShow = true;
                    }
                }

                if (!showEvents.contains(event.getType()) && shouldShow) {
                    StringBuilder msg = new StringBuilder();
                    msg.append(event.getPlayer().getPlayerName() + ":" + event.getType());
                    if (event.card != null) {
                        msg.append(":" + event.card.getName());
                        if (event.card.getControlCard() != event.card) {
                            msg.append(" <" + event.card.getControlCard().getName() + ">");
                        }
                        if (event.card.isImpersonatingAnotherCard()) {
                            msg.append(" (as " + event.card.behaveAsCard().getName() + ")");
                        }
                    }
                    if (event.getType() == GameEvent.EventType.TurnBegin && event.getPlayer().isPossessed()) {
                        msg.append(" possessed by " + event.getPlayer().controlPlayer.getPlayerName() + "!");
                    }
                    if (event.attackedPlayer != null) {
                        msg.append(", attacking:" + event.attackedPlayer.getPlayerName());
                    }

                    if (event.getType() == GameEvent.EventType.BuyingCard) {
                        msg.append(" (with gold: " + event.getContext().getCoinAvailableForBuy() + ", buys remaining: " + event.getContext().getBuysLeft());
                    }
                    Util.debug(msg.toString(), true);
                }
            }

        };
    }

    boolean hasMoat(Player player) {
        for (Card card : player.hand) {
            if (card.equals(Cards.moat)) {
                return true;
            }
        }

        return false;
    }

    boolean hasLighthouse(Player player) {
        for (Card card : player.nextTurnCards) {
            if (card.behaveAsCard().equals(Cards.lighthouse) && !((CardImpl) card).trashAfterPlay)
                return true;
        }

        return false;
    }
    
    int countChampionsInPlay(Player player) {
    	int count = 0;
        for (Card card : player.nextTurnCards) {
            if (card.behaveAsCard().equals(Cards.champion))
                count += ((CardImpl) card).cloneCount;
        }

        return count;
    }


    /*
       Note that any cards in the supply can have Embargo coins added.
       This includes the basic seven cards (Victory, Curse, Treasure),
       any of the 10 game piles, and Colony/Platinum when included.
       However, this does NOT include any Prizes from Cornucopia.
       */

    CardPile addEmbargo(Card card) {
        if (isValidEmbargoPile(card)) {
            String name = card.getName();
            embargos.put(name, getEmbargos(card) + 1);
            return piles.get(name);
        }
        return null;
    }
    
    CardPile addPileVpTokens(Card card, int num, MoveContext context) {
    	if (Cards.isBlackMarketCard(card)) {
    		return null;
    	}
        String name = card.getName();
        pileVpTokens.put(name, getPileVpTokens(card) + num);
        if (context != null) {
        	GameEvent event = new GameEvent(GameEvent.EventType.VPTokensPutOnPile, context);
    		event.setAmount(num);
    		event.card = card;
            context.game.broadcastEvent(event);
    	}
        return piles.get(name);
    }
    
    CardPile removePileVpTokens(Card card, int num, MoveContext context) {
    	if (Cards.isBlackMarketCard(card)) {
    		return null;
    	}
        if (getPile(card) != null)
            card = getPile(card).placeholderCard();

    	num = Math.min(num, getPileVpTokens(card));
        String name = card.getName();
        if (num > 0) {
        	pileVpTokens.put(name, getPileVpTokens(card) - num);
        	if (context != null) {
	        	GameEvent event = new GameEvent(GameEvent.EventType.VPTokensTakenFromPile, context);
	    		event.setAmount(num);
	    		event.card = card;
	            context.game.broadcastEvent(event);
        	}
        }
        return piles.get(name);
    }

    CardPile addPileDebtTokens(Card card, int num, MoveContext context) {
        card = getPile(card).placeholderCard();
        String name = card.getName();
        pileDebtTokens.put(name, getPileDebtTokens(card) + num);
        if (context != null) {
        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensPutOnPile, context);
    		event.setAmount(num);
    		event.card = card;
            context.game.broadcastEvent(event);
    	}
        return piles.get(name);
    }

    CardPile removePileDebtTokens(Card card, int num, MoveContext context) {
        card = getPile(card).placeholderCard();
    	num = Math.min(num, getPileDebtTokens(card));
        String name = card.getName();
        if (num > 0) {
        	pileDebtTokens.put(name, getPileDebtTokens(card) - num);
        	if (context != null) {
	        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensTakenFromPile, context);
	    		event.setAmount(num);
	    		event.card = card;
	            context.game.broadcastEvent(event);
        	}
        }
        return piles.get(name);
    }

    public boolean isValidEmbargoPile(Card card) {
        return !(card == null || !pileInGame(getPile(card)) || !Cards.isSupplyCard(card) );
    }

    public int getEmbargos(Card card) {

        Integer count = embargos.get(getPile(card).placeholderCard().getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileVpTokens(Card card) {
    	if (Cards.isBlackMarketCard(card)) {
    		return 0;
    	}
        if (getPile(card) != null)
            card = getPile(card).placeholderCard();

        Integer count = pileVpTokens.get(card.getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileDebtTokens(Card card) {
        card = getPile(card).placeholderCard();
        Integer count = pileDebtTokens.get(card.getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileTradeRouteTokens(Card card) {
    	return piles.get(card.getName()).hasTradeRouteToken() ? 1 : 0;
    }
    
    public List<PlayerSupplyToken> getPlayerSupplyTokens(Card card, Player player) {
    	card = card.getTemplateCard();
    	if (player == null || !playerSupplyTokens.containsKey(card.getName()))
    		return new ArrayList<PlayerSupplyToken>();
    	
    	if (!playerSupplyTokens.get(card.getName()).containsKey(player)) {
        	playerSupplyTokens.get(card.getName()).put(player, new ArrayList<PlayerSupplyToken>());
    	}
    	return playerSupplyTokens.get(card.getName()).get(player);
    }
    
    public boolean isPlayerSupplyTokenOnPile(Card card, Player player, PlayerSupplyToken token) {
    	return getPlayerSupplyTokens(card, player).contains(token);
    }
    
    public void movePlayerSupplyToken(Card card, Player player, PlayerSupplyToken token) {
    	removePlayerSupplyToken(player, token);
    	getPlayerSupplyTokens(card, player).add(token);
    	
        MoveContext context = new MoveContext(this, player);
        GameEvent.EventType eventType = null;
        if (token == PlayerSupplyToken.PlusOneCard) {
        	eventType = EventType.PlusOneCardTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneAction) {
        	eventType = EventType.PlusOneActionTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneBuy) {
        	eventType = EventType.PlusOneBuyTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneCoin) {
        	eventType = EventType.PlusOneCoinTokenMoved;
        } else if (token == PlayerSupplyToken.MinusTwoCost) {
        	eventType = EventType.MinusTwoCostTokenMoved;
        } else if (token == PlayerSupplyToken.Trashing) {
        	eventType = EventType.TrashingTokenMoved;
        }
        GameEvent event = new GameEvent(eventType, context);
        event.card = card;
        broadcastEvent(event);
    }
    
    private void removePlayerSupplyToken(Player player, PlayerSupplyToken token) {
		for (String cardName : playerSupplyTokens.keySet()) {
			if (playerSupplyTokens.get(cardName).containsKey(player)) {
				playerSupplyTokens.get(cardName).get(player).remove(token);
			}
		}
	}

	// Only is valid for cards in play...
    //    protected Card readCard(String name) {
    //        CardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPile pile = piles.get(name);
    //        if (pile == null || pile.getCount() <= 0) {
    //            return null;
    //        }
    //        return pile.card();
    //    }

    protected void addToPile(Card card, boolean bottom) {
        addToPile(card, bottom, null);
    }

    protected void addToPile(Card card, boolean bottom, MoveContext context) {
        CardPile pile = getPile(card);
        if (pile != null) {
            pile.addCard(card, bottom);
        }
    }
    protected Card takeFromPile(Card card) {
        return takeFromPile(card, null);
    }
    
    protected Card takeFromPile(Card card, MoveContext context) {
        CardPile pile = getPile(card);
        if (pile == null || pile.getCount() <= 0) {
            return null;
        }

        tradeRouteValue += pile.takeTradeRouteToken();

        return pile.removeCard();
    }

    protected Card takeFromPileCheckTrader(Card cardToGain, MoveContext context) {

        //If the pile was specified instead of a card, take the top card from that pile.
        if (cardToGain.isPlaceholderCard() || cardToGain.isTemplateCard()) {
            cardToGain = getPile(cardToGain).topCard();


        //If the desired card is not on top of the pile, don't take a card
        } else if (!isCardOnTop(cardToGain)) {
            return null;
        }


        return takeFromPile(cardToGain, context);
    }

    public int pileSize(Card card) {
        CardPile pile = getPile(card);
        if (pile == null) {
            return -1;
        }

        return pile.getCount();
    }

    public boolean isPileEmpty(Card card) {
        return pileSize(card) <= 0;
    }

    public int emptyPiles() {
        int emptyPiles = 0;
        ArrayList<CardPile> alreadyCounted = new ArrayList<CardPile>();
        for (CardPile pile : piles.values()) {
            if (pile.getCount() <= 0 && pile.isSupply() && !alreadyCounted.contains(pile)) {
                emptyPiles++;
                alreadyCounted.add(pile);
            }
        }
        return emptyPiles;
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt) {
        return getCardsInGame(opt, false);
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt, boolean supplyOnly) {
        return getCardsInGame(opt, supplyOnly, null);
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt, boolean supplyOnly, CardType type) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (CardPile pile : piles.values()) {

            if (supplyOnly && !pile.isSupply) continue;

            if (opt == GetCardsInGameOptions.All || opt == GetCardsInGameOptions.Placeholders)
            {
                if ((type == null || pile.placeholderCard().is(type, null))
                        && !cards.contains(pile.placeholderCard()))
                    cards.add(pile.placeholderCard());
            }
            if (opt == GetCardsInGameOptions.All || opt == GetCardsInGameOptions.Templates) {
                for (Card c : pile.getTemplateCards()) {
                    if ((type == null || c.is(type, null))
                            && !cards.contains(c)) {
                        cards.add(c);
                    }
                }
            }
            if (opt == GetCardsInGameOptions.TopOfPiles) {
                if (pile.topCard() != null && (type == null || pile.topCard().is(type))
                        && !cards.contains(pile.topCard())) {
                    cards.add(pile.topCard());
                }
            }
            if (opt == GetCardsInGameOptions.Buyables) {
                if (pile.topCard() != null && (type == null || pile.topCard().is(type))
                        && !cards.contains(pile.topCard()) && (pile.isSupply() || pile.topCard().is(CardType.Event))) {
                    cards.add(pile.topCard());
                }
            }
        }
        return cards.toArray(new Card[0]);
    }

    public boolean cardInGame(Card c) {
        for (CardPile pile : piles.values()) {
            if(c.equals(pile.placeholderCard())) {
                return true;
            }
            for (Card template : pile.getTemplateCards()) {
                if (c.equals(template)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCardOnTop(Card card) {
        CardPile pile = getPile(card);
        if (pile == null) return false;
        Card top = pile.topCard();
        return top != null && top.equals(card);
    }

    public boolean pileInGame(CardPile p) {
        for (CardPile pile : piles.values()) {
            if(pile.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlatInGame() {
        return cardInGame(Cards.platinum);
    }

    public boolean isColonyInGame() {
        return cardInGame(Cards.colony);
    }

    public int getCardsLeftInPile(Card card) {
        CardPile pile = getPile(card);
        if (pile == null || pile.getCount() < 0) {
            return 0;
        }

        return pile.getCount();
    }

    public ArrayList<Card> GetTrashPile()
    {
        return trashPile;
    }

    public ArrayList<Card> GetBlackMarketPile()
    {
        return blackMarketPile;
    }

    protected CardPile addPile(Card card) {
    	boolean isSupply = true;
        int count = kingdomCardPileSize;
        if(card.is(CardType.Victory)) count = victoryCardPileSize;
        if(card.equals(Cards.port)) count = 12;
        if(card.is(CardType.Event) || card.is(CardType.Landmark)) {
        	count = 1;
        	isSupply = false;
        }
        if (card.getPileSize() > 0)
            return addPile(card, card.getPileSize(), isSupply);
        else
            return addPile(card, count, isSupply);
    }

    protected CardPile addPile(Card card, int count) {
        return addPile(card, count, true);
    }

    protected CardPile addPile(Card card, int count, boolean isSupply) {
        return addPile(card, count, isSupply, false);
    }

    protected CardPile addPile(Card card, int count, boolean isSupply, boolean isBlackMarket) {
        CardPile pile = card.getPileCreator().create(card, count);

        if (!isSupply) {
            pile.notInSupply();
        }
        if (isBlackMarket) {
            pile.inBlackMarket();
        }

        piles.put(card.getName(), pile);
        placeholderPiles.put(card.getName(), pile);
        HashMap<Player, List<PlayerSupplyToken>> tokenMap = new HashMap<Player, List<PlayerSupplyToken>>();
        playerSupplyTokens.put(card.getName(), tokenMap);

        //Add the to the list for each templateCard used (this replaces addLinkedPile)
        //Also add the an entry for each templateCardName to the playerSupplyTokens because at some places in the code
        //the token is checked with the actual card and not the placeholder.
        for (Card templateCard : pile.getTemplateCards()) {
            if (!piles.containsKey(templateCard.getName())) {
                piles.put(templateCard.getName(), pile);
            }
            if (!playerSupplyTokens.containsKey(templateCard.getName())) {
                playerSupplyTokens.put(templateCard.getName(), tokenMap);
            }
        }

        return pile;
    }

    private ArrayList<Card> getCardsObtainedByPlayer(int PlayerNumber) {
        return cardsObtainedLastTurn[PlayerNumber];
    }

    public ArrayList<Card> getCardsObtainedByPlayer() {
        return getCardsObtainedByPlayer(playersTurn);
    }
    
    public ArrayList<Card> getCardsObtainedByLastPlayer() {
        int playerOnRight = playersTurn - 1;
        if (playerOnRight < 0) {
            playerOnRight = numPlayers - 1;
        }
        return getCardsObtainedByPlayer(playerOnRight);
    }

    public Player getPreviousPlayer() {
        int last = playersTurn - 1;
        if (last < 0) {
            last = numPlayers - 1;
        }

        return players[last];
    }

    public Player getNextPlayer() {
        int next = playersTurn + 1;
        if (next >= numPlayers) {
            next = 0;
        }

        return players[next];
    }
    
    public Player getCurrentPlayer() {
        return players[playersTurn];
    }

    public Player[] getPlayersInTurnOrder() {
    	return getPlayersInTurnOrder(playersTurn);
    }
    
    public Player[] getPlayersInTurnOrder(Player startingPlayer) {
        int at = 0;
        for (int i = 0; i < numPlayers; i++) {
        	if (players[i] == startingPlayer) {
        		at = i;
        		break;
        	}
        }
        return getPlayersInTurnOrder(at);
    }
    
    public Player[] getPlayersInTurnOrder(int startingPlayerIdx) {
        Player[] ordered = new Player[numPlayers];

        int at = startingPlayerIdx;
        for (int i = 0; i < numPlayers; i++) {
            ordered[i] = players[at];
            at++;
            if (at >= numPlayers) {
                at = 0;
            }
        }

        return ordered;
    }

    public void broadcastEvent(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.gameEvent(event);
        }
        // notify this class' listener last for proper action/logging order
        if(gameListener != null)
            gameListener.gameEvent(event);
    }

    String getHandString(Player player) {
        String handString = null;
        Card[] hand = player.getHand().toArray();
        Arrays.sort(hand, new CardCostComparator());
        for (Card card : hand) {
            if (card == null) {
                continue;
            }
            if (handString == null) {
                handString = card.getName();
            } else {
                handString += ", " + card.getName();
            }
        }

        return handString;
    }

    public boolean playerShouldSelectCoinsToPlay(MoveContext context, CardList cards) {
        if(!quickPlay) {
            return true;
        }

        if(cards == null) {
            return false;
        }

        CardPile grandMarket = getPile(Cards.grandMarket);
        for(Card card : cards) {
            if (
                    card.equals(Cards.philosophersStone) ||
                    card.equals(Cards.bank) ||
                    card.equals(Cards.contraband) ||
                    card.equals(Cards.loan) ||
                    card.equals(Cards.quarry) ||
                    card.equals(Cards.talisman) ||
                    card.equals(Cards.hornOfPlenty) ||
                    card.equals(Cards.diadem) ||
                    (card.equals(Cards.copper) && grandMarket != null && grandMarket.getCount() > 0)
               )
            {
                return true;
            }
        }

        return false;
    }

    static boolean checkForInteractive() throws ExitException {
        for (int i = 0; i < numPlayers; i++) {
            Player player;
            try {
                String[] classAndJar = playerClassesAndJars.get(i);
                if (classAndJar[1] == null) {
                    player = (Player) Class.forName(classAndJar[0]).newInstance();
                } else {
                    URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(classAndJar[1]) });
                    player = (Player) classLoader.loadClass(classAndJar[0]).newInstance();
                }
                if(classAndJar[2] != null) {
                    player.setName(classAndJar[2]);
                }
            } catch (Exception e) {
                Util.log(e);
                throw new ExitException();
            }
        }
        return false;
    }



    public CardPile getPile(Card card) {
        if (card == null) return null;
        return piles.get(card.getName());
    }
    
    public CardPile getGamePile(Card card) {
        return getPile(card);
    }

    public boolean cardsInSamePile(Card first, Card second) {
        return getPile(first).equals(getPile(second));
    }

    public void trashHovelsInHandOption(Player player, MoveContext context, Card responsible)
    {
        // If player has a Hovel (or multiple Hovels), offer the option to trash...
        ArrayList<Card> hovelsToTrash = new ArrayList<Card>();

        for (Card c : player.hand)
        {
            if (c.getKind() == Cards.Kind.Hovel && player.controlPlayer.hovel_shouldTrash(context))
            {
                hovelsToTrash.add(c);
            }
        }

        if (hovelsToTrash.size() > 0)
        {
            for (Card c : hovelsToTrash)
            {
                player.hand.remove(c);
                player.trash(c, responsible, context);
            }
        }
    }
    
    public boolean hauntedWoodsAttacks(Player player) {
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	if (otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.HauntedWoods) > 0) {
            		return true;
            	}
            }
        }
        return false;
    }
    
    public boolean enchantressAttacks(Player player) {
    	if (getCurrentPlayer() != player) return false;
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	if (otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.Enchantress) > 0) {
            		return true;
            	}
            }
        }
        return false;
    }

    public int swampHagAttacks(Player player)
    {
    	int swampHags = 0;
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	swampHags += otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.SwampHag);
            }
        }
        return swampHags;
    }
}
