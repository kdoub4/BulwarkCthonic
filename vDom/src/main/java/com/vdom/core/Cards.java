package com.vdom.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.vdom.api.Card;

public class Cards {

//To add a card
//-add to Kind
    //-create a public static card to hold the template
    //-add to the CardNametoCard map either directly or by adding to ArrayList you create below
    //Add set to a CardSet
    //If it requires a prompt add to SimpleActionStrings or actionStringMap
    public static ArrayList<Card> heroCardsArpad = new ArrayList<Card>();
    public static ArrayList<Card> swordCardsArpad = new ArrayList<Card>();
    public static ArrayList<Card> shieldCardsArpad = new ArrayList<Card>();
    public static ArrayList<Card> heroCardsSandor = new ArrayList<Card>();
    public static ArrayList<Card> staffCardsSandor = new ArrayList<Card>();
    public static ArrayList<Card> healingCardsSandor = new ArrayList<Card>();
    public static ArrayList<Card> heroCardsWiola = new ArrayList<Card>();
    public static ArrayList<Card> bowCardsWiola = new ArrayList<Card>();
    public static ArrayList<Card> shotCardsWiola = new ArrayList<Card>();
    public static ArrayList<Card> heroCardsZsuzsa = new ArrayList<Card>();
    public static ArrayList<Card> bowCardsZsuzsa = new ArrayList<Card>();
    public static ArrayList<Card> daggerCardsZsuzsa = new ArrayList<Card>();

    public static ArrayList<Card> locationCardsFortBriggs = new ArrayList<Card>();
    public static ArrayList<Card> locationCardsCitadelXajorkith = new ArrayList<>();
    public static ArrayList<Card> locationCardsCitadelXajorkithGate = new ArrayList<>();
    public static ArrayList<Card> locationCardsHoltvaros = new ArrayList<>();
    public static ArrayList<Card> locationCardsHoltvarosMilitia = new ArrayList<>();
    public static ArrayList<Card> locationCardsSylvanHeights = new ArrayList<>();
    public static ArrayList<Card> locationCardsSylvanChorus = new ArrayList<>();

    public static ArrayList<Card> cardsElf = new ArrayList<Card>();
    public static ArrayList<Card> cardsWinter = new ArrayList<>();
    public static ArrayList<Card> cardsGoblin = new ArrayList<Card>();
    public static ArrayList<Card> cardsLizard = new ArrayList<Card>();
    public static ArrayList<Card> woundCards = new ArrayList<Card>();
    public static ArrayList<Card> woundPile = new ArrayList<Card>();

    public static ArrayList<Card> actionCardsBaseGame = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsBaseGame2E = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsBaseGameAll = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigue = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigue2E = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigueAll = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsSeaside = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsAlchemy = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsProsperity = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsCornucopia = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsHinterlands = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsDarkAges = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsGuilds = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsAdventures = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsAdventures = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> landmarkCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsPromo = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsPromo = new ArrayList<Card>();
    public static ArrayList<Card> actionCards = new ArrayList<Card>();
    public static ArrayList<Card> prizeCards = new ArrayList<Card>();
    public static ArrayList<Card> nonSupplyCards = new ArrayList<Card>();
    public static ArrayList<Card> variablePileCards = new ArrayList<Card>();
    public static ArrayList<Card> knightsCards = new ArrayList<Card>();
    public static ArrayList<Card> ruinsCards = new ArrayList<Card>();
    public static ArrayList<Card> castleCards = new ArrayList<Card>();
    public static ArrayList<Card> eventsCards = new ArrayList<Card>();
    public static ArrayList<Card> landmarkCards = new ArrayList<Card>();
    public static ArrayList<Card> blackMarketCards = new ArrayList<Card>();
    public static HashSet<Card> nonKingdomCards = new HashSet<Card>();
    public static HashSet<Card> locationCards = new HashSet<Card>();

    public static HashSet<Card> heroismCards = new HashSet<Card>();


    public static HashMap<String, Card> cardNameToCard = new HashMap<String, Card>();
    public static HashMap<Card, Card> variablePileCardToRandomizer = new HashMap<Card, Card>();

    static final String KNIGHTS_TEXT = "Each other player reveals the top 2 cards of his deck, " +
            "trashes one of them costing from 3 to 6 coins, and discards the rest. If a Knight " +
            "is trashed by this, trash this card.";
    static final String TRAVELLERS_TEXT = "TRAVELLERS_TEXT.";

    public enum Kind {
        VirtualEnemy, VirtualWound, VirtualAttack,
        //Treasure
        One, Two, Three,
        //Arpad of Lhun
        Attack, Shield, TowerShield, MastersSword, BloodstainedBlade, HundredScimitar,
        LamellarArmour, Charge, VirtualHeroism, VirtualSword, VirtualShield,

        //Sandor the Wise
        OakStaff, CarvedStaff, IronShodStaff, VirtualStaff, SanctusCharm, Prayer, Bless, Beat,
        HealingHerbs, HealingBalm, VirtualHealing, Orison,

        //Wiola Wildborne
        Shoot, Stalk, PatientHunter, PinpointShot, ContempuousShot, Longbow, PrestigeBow,
        EnchangtedBow, VirtualBow, VirtualShot, VantagePoint,

        //ZsuZsa Slatewalker
        Sidestep, KnifeInTheBack, Shortbow, Crossbow, DualDaggers, NightDaggers, BurialDaggers,
        FlankAttack, Sneak, TheDrop, VirtualDaggers,

        //Goblin Horde
        GoblinTroop, GoblinAlchemist, GoblinRabble, GobCatapult, GoblinHeavy, GoblinArcher,
        KneenaGoblin, KmbleeGoblin, GhutzGoblin, KmronGoblin, MgzwelGoblin,
        //TODO add in subtitle to card and remove Goblin from Crown foe names
        
        //Fire Lizard Crusade
        FireCatapult, LizardTroop, YoonIseulLizard, KyungLizard,SangLizard, LizardRabble,
        LizardBombardier, LizardHeavy, BatteringRam, HyeonLizard, GiantCorpseHound,

        //Fallen Elf Vengenace
        TaneElf, VeporDemon, XaphanDemon, KiriElf, RuihaElf, ElfDruid, ElfTroop, ElfRabble,
        ElfArcher, FlameBallista, CorpseVine,

        //Winter of Death
        BrokenCorpse, StaleCorpse, FreshCorpse, EnshroudingMist, Spectre, EmbalmedAcolyte,
        TheLeftHandGoblin, TheRightHandHuman, TheCatacombite, HorrorOfFlesh, KangaxxTheLich,
        
        //Wound
        SeriousWound, StaggeringWound, WeakeningWound, GlancingWound, OldWound, FleshWound,

        // Fort Briggs
        Mess, RockFallTrap, StoneWalls, Armoury, GuardTower,Sanitorium,

        // Citadel
        OracularTurret, SacredVault, GreatNorthGate, GreatSouthGate, GreatWestGate, GreatEastGate,
        VirtualGate, CitadelWalls, Barracks, DefensiveTrebuchet,

        // Holtvaros
        Crypt, Well, TownSquare, TerrifiedMilitia, RalliedMilitia, VirtualMilitia, Watermill,
        Belltower,

        // Sylvan Heights
        SamuGarden, ElvenChorus, CelestialChorus, VirtualChorus, SilkenSnare, TreetopSpire,
        RadiantPool, GalleryOfLeaves,

        // Non-Kingdom Cards
        Platinum, Gold, Silver, Copper, Potion, Colony, Province, Duchy, Estate, Curse,
        // Base Set
        Gardens, Moat, Adventurer, Bureaucrat, Cellar, Chancellor, Chapel, CouncilRoom, Feast,
        Festival, Laboratory, Library, Market, Militia, Mine, Moneylender, Remodel, Smithy, Spy,
        Thief, ThroneRoom, Village, Witch, Woodcutter, Workshop,
        // Base Set Second Edition
        Artisan, Bandit, Harbinger, Merchant, Poacher, Sentry, Vassal,
		
        // Intrigue Expansion
        Duke, SecretChamber, Nobles, Coppersmith, Courtyard, Torturer, Harem, Baron, Bridge,
        Conspirator, Ironworks, Masquerade, MiningVillage, Minion, Pawn, Saboteur, ShantyTown,
        Scout, Steward, Swindler, TradingPost, WishingWell, Upgrade, Tribute, GreatHall,
        // Intrigue Second Edition
        Courtier, Diplomat, Lurker, Mill, Patrol, Replace, SecretPassage,

        // Seaside Expansion
        Haven, SeaHag, Tactician, Caravan, Lighthouse, FishingVillage, Wharf, MerchantShip,
        Outpost, GhostShip, Salvager, PirateShip, NativeVillage, Island, Cutpurse, Bazaar,
        Smugglers, Explorer, PearlDiver, TreasureMap, Navigator, Treasury, Lookout, Ambassador,
        Warehouse, Embargo,
        // Alchemy Expansion
        Alchemist, Apothecary, Apprentice, Familiar, Golem, Herbalist, PhilosophersStone,
        Possession, ScryingPool, Transmute, University, Vineyard,
        // Prosperity Expansion
        Bank, Bishop, City, Contraband, CountingHouse, Expand, Forge, Goons, GrandMarket, Hoard,
        KingsCourt, Loan, Mint, Monument, Mountebank, Peddler, Quarry, Rabble, RoyalSeal, Talisman,
        TradeRoute, Vault, Venture, WatchTower, WorkersVillage,
        // Cornucopia Expansion
        HornofPlenty, Fairgrounds, FarmingVillage, FortuneTeller, Hamlet, Harvest, HorseTraders,
        HuntingParty, Jester, Menagerie, Remake, Tournament, YoungWitch, BagofGold, Diadem,
        Followers, Princess, TrustySteed,
        // Hinterlands Expansion
        BorderVillage, Cache, Cartographer, Crossroads, Develop, Duchess, Embassy, Farmland,
        FoolsGold, Haggler, Highway, IllGottenGains, Inn, JackofallTrades, Mandarin, Margrave,
        NobleBrigand, NomadCamp, Oasis, Oracle, Scheme, SilkRoad, SpiceMerchant, Stables, Trader,
        Tunnel,
        // Dark Ages Expansion
        Altar, Armory, BandOfMisfits, BanditCamp, Beggar, Catacombs, Count, Counterfeit, DeathCart,
        Feodum, Forager, Fortress, Graverobber, HuntingGrounds, Ironmonger, JunkDealer,
        MarketSquare, Mystic, Pillage, PoorHouse, Procession, Rats, Rebuild, Rogue, Sage,
        Scavenger, Spoils, Squire, Storeroom, WanderingMinstrel, Necropolis, Hovel,
        OvergrownEstate, AbandonedMine, RuinedLibrary, RuinedMarket, RuinedVillage, Survivors,
        Cultist, Urchin, Mercenary, Marauder, Hermit, Madman, Vagrant, DameAnna, DameJosephine,
        DameMolly, DameNatalie, DameSylvia, SirBailey, SirDestry, SirMartin, SirMichael, SirVander,
        VirtualRuins, VirtualKnight,
        // Guilds Expansion
        Advisor, Baker, Butcher, CandlestickMaker, Doctor, Herald, Journeyman, Masterpiece, MerchantGuild, Plaza, StoneMason, Soothsayer, Taxman,

        // Adventures Expansion
        CoinOfTheRealm, Page, Peasant, Ratcatcher, Raze, Amulet, CaravanGuard, Dungeon, Gear, Guide,
        Duplicate, Magpie, Messenger, Miser, Port, Ranger, Transmogrify, Artificer, BridgeTroll, DistantLands,
        Giant, HauntedWoods, LostCity, Relic, RoyalCarriage, Storyteller, SwampHag, TreasureTrove, WineMerchant, Hireling, 
        
        Soldier, TreasureHunter, Fugitive, Warrior, Disciple, Hero, Champion, Teacher, 
        
        Alms, Borrow, Quest, Save, ScoutingParty, TravellingFair, Bonfire, Expedition, Ferry, Plan, Mission, Pilgrimage,
        Ball, Raid, Seaway, Trade, LostArts, Training, Inheritance, Pathfinding,
        
        // Empires Expansion
        Archive, BustlingVillage, Capital, Catapult, ChariotRace, Charm, CityQuarter, Crown, Emporium, Encampment, Enchantress, 
        Engineer, FarmersMarket, Fortune, Forum, Gladiator, Groundskeeper, Legionary, Overlord, Patrician, Plunder, Rocks, 
        RoyalBlacksmith, Sacrifice, Settlers, Temple, Villa, WildHunt,
        HumbleCastle, CrumblingCastle, SmallCastle, HauntedCastle, OpulentCastle, SprawlingCastle, GrandCastle, KingsCastle,
        
        CatapultRocks, Castles, EncampmentPlunder, GladiatorFortune
        , PatricianEmporium, SettlersBustlingVillage,
        
        Advance, Annex, Banquet, Conquest, Dominate, Delve, Donate, Ritual, SaltTheEarth, Tax, Triumph, Wedding, Windfall,
        Aqueduct, Arena, BanditFort, Basilica, Baths, Battlefield, Colonnade, DefiledShrine, Fountain, Keep, Labyrinth, MountainPass, Museum, Obelisk, Orchard, Palace, Tomb, Tower, TriumphalArch, Wall, WolfDen,
        
        // Promo Cards
        Envoy, Governor, WalledVillage, Prince, BlackMarket, Stash, Summon, Sauna, Avanto,
        SaunaAvanto,
        // Promo Cards (not yet implemented)
        // Stash
        // Victory token card container
        VictoryTokens
    }

    public static final Card one;
    public static final Card two;
    public static final Card three;

    public static final Card attack;
    public static final Card shield;
    public static final Card towerShield;
    public static final Card virtualShield;
    public static final Card mastersSword;
    public static final Card bloodstainedBlade;
    public static final Card hundredScimitar;
    public static final Card virtualSword;
    public static final Card lamellarArmour;
    public static final Card charge;
    public static Card virtualHeroism;

    public static final Card oakStaff;
    public static final Card carvedStaff;
    public static final Card ironShodStaff;
    public static final Card sanctusCharm;
    public static final Card prayer;
    public static final Card bless;
    public static final Card beat;
    public static final Card healingHerbs;
    public static final Card healingBalm;
    public static final Card virtualStaff;
    public static final Card virtualHealing;
    public static final Card orison;

    public static final Card shoot;
    public static final Card stalk;
    public static final Card patientHunter;
    public static final Card pinpointShot;
    public static final Card comtempuousShot;
    public static final Card longbow;
    public static final Card prestigeBow;
    public static final Card enchantedBow;
    public static final Card vantagePoint;
    public static final Card virtualBowW;
    public static final Card virtualShot;

    public static final Card sidestep;
    public static final Card knifeInTheBack;
    public static final Card shortbow;
    public static final Card crossbow;
    public static final Card dualDaggers;
    public static final Card burialDaggers;
    public static final Card nightDaggers;
    public static final Card flankAttack;
    public static final Card sneak;
    public static final Card theDrop;
    public static final Card virtualBowZ;
    public static final Card virtualDaggers;

    public static Card virtualEnemy;
    public static final Card goblinRabble;
    public static final Card catapultBulwark;
    public static final Card mgzwel;
    public static final Card ghutz;
    public static final Card goblinHeavy;
    public static final Card kmron;
    public static final Card kmblee;
    public static final Card kneena;
    public static final Card goblinArcher;
    public static final Card goblinTroop;
    public static final Card goblinAlchemist;
    
    public static final Card fireCatapult;
    public static final Card lizardTroop;
    public static final Card yoonIseul;
    public static final Card kyung;
    public static final Card sang;
    public static final Card lizardRabble;
    public static final Card lizardBombardier;
    public static final Card lizardHeavy;
    public static final Card batteringRam;
    public static final Card hyeon;
    public static final Card giantCorpseHound;

    public static final Card elfTroop;
    public static final Card elfRabble;
    public static final Card elfArcher;
    public static final Card elfDruid;
    public static final Card flameBallista;
    public static final Card corpseVine;
    public static final Card xaphan;
    public static final Card tane;
    public static final Card ruiha;
    public static final Card kiri;
    public static final Card vepor;

    public static final Card brokenCorpse;
    public static final Card staleCorpse;
    public static final Card freshCorpse;
    public static final Card spectre;
    public static final Card embalmingAcolyte;
    public static final Card enshroudingMist;
    public static final Card theLeftHand;
    public static final Card theRightHand;
    public static final Card theCatacombite;
    public static final Card horrorOfFlesh;
    public static final Card kangaxxTheLich;

    public static final Card virtualWound;
    public static final Card seriousWound;
    public static final Card glancingWound;
    public static final Card oldWound;
    public static final Card weakeningWound;
    public static final Card staggeringWound;
    public static final Card fleshWound;

    public static final Card sanitorium;
    public static final Card guardTower;
    public static final Card rockFallTrap;
    public static final Card stoneWalls;
    public static final Card mess;
    public static final Card armoury;

    public static final Card oracularTurrent;
    public static final Card sacredVault;
    public static final Card greatNorthGate;
    public static final Card greatSouthGate;
    public static final Card greatWestGate;
    public static final Card greatEastGate;
    public static final Card virtualGate;
    public static final Card citadelWalls;
    public static final Card barracks;
    public static final Card defensiveTrebuchet;

    public static final Card belltower;
    public static final Card watermill;
    public static final Card ralliedMilitia;
    public static final Card terrifiedMilitia;
    public static final Card virtualMilitia;
    public static final Card townSquare;
    public static final Card well;
    public static final Card crypt;

    public static final Card samuGarden;
    public static final Card silkenSnare;
    public static final Card elvenChorus;
    public static final Card celestialChorus;
    public static final Card virtualChorus;
    public static final Card treetopSpire;
    public static final Card radiantPool;
    public static final Card galleryOfLeaves;

    public static final Card platinum;
    public static final Card gold;
    public static final Card silver;
    public static final Card copper;
    public static final Card potion;

    public static final Card victoryTokens;
    public static final Card colony;
    public static final Card province;
    public static final Card duchy;
    public static final Card estate;

    public static final Card curse;

    // Dominion base
    public static final Card gardens;
    public static final Card moat;
    public static final Card adventurer;
    public static final Card bureaucrat;
    public static final Card cellar;
    public static final Card chancellor;
    public static final Card chapel;
    public static final Card councilRoom;
    public static final Card feast;
    public static final Card festival;
    public static final Card laboratory;
    public static final Card library;
    public static final Card market;
    public static final Card militia;
    public static final Card mine;
    public static final Card moneyLender;
    public static final Card remodel;
    public static final Card smithy;
    public static final Card spy;
    public static final Card thief;
    public static final Card throneRoom;
    public static final Card village;
    public static final Card witch;
    public static final Card woodcutter;
    public static final Card workshop;
    
    // Dominion base, second edition
    public static final Card artisan;
    public static final Card bandit;
    public static final Card harbinger;
    public static final Card merchant;
    public static final Card poacher;
    public static final Card sentry;
    public static final Card vassal;

    // Intrigue expansion
    public static final Card duke;
    public static final Card nobles;
    public static final Card courtyard;
    public static final Card torturer;
    public static final Card harem;
    public static final Card baron;
    public static final Card bridge;
    public static final Card conspirator;
    public static final Card ironworks;
    public static final Card masquerade;
    public static final Card miningVillage;
    public static final Card minion;
    public static final Card pawn;
    public static final Card shantyTown;
    public static final Card steward;
    public static final Card swindler;
    public static final Card tradingPost;
    public static final Card wishingWell;
    public static final Card upgrade;

    // Intrigue first Edition (removed in second Edition)
    public static final Card coppersmith;
    public static final Card greatHall;
    public static final Card saboteur;
    public static final Card scout;
    public static final Card secretChamber;
    public static final Card tribute;

    // Intrigue second Edition
    public static final Card courtier;
    public static final Card diplomat;
    public static final Card lurker;
    public static final Card mill;
    public static final Card patrol;
    public static final Card replace;
    public static final Card secretPassage;

    // Seaside expansion
    public static final Card haven;
    public static final Card seaHag;
    public static final Card tactician;
    public static final Card caravan;
    public static final Card lighthouse;
    public static final Card fishingVillage;
    public static final Card wharf;
    public static final Card merchantShip;
    public static final Card outpost;
    public static final Card ghostShip;
    public static final Card salvager;
    public static final Card pirateShip;
    public static final Card nativeVillage;
    public static final Card island;
    public static final Card cutpurse;
    public static final Card bazaar;
    public static final Card smugglers;
    public static final Card explorer;
    public static final Card pearlDiver;
    public static final Card treasureMap;
    public static final Card navigator;
    public static final Card treasury;
    public static final Card lookout;
    public static final Card ambassador;
    public static final Card warehouse;
    public static final Card embargo;

    // Alchemy expansion
    public static final Card alchemist;
    public static final Card apothecary;
    public static final Card apprentice;
    public static final Card familiar;
    public static final Card golem;
    public static final Card herbalist;
    public static final Card philosophersStone;
    public static final Card possession;
    public static final Card scryingPool;
    public static final Card transmute;
    public static final Card university;
    public static final Card vineyard;

    // Prosperity expansion
    public static final Card bank;
    public static final Card bishop;
    public static final Card city;
    public static final Card contraband;
    public static final Card countingHouse;
    public static final Card expand;
    public static final Card forge;
    public static final Card goons;
    public static final Card grandMarket;
    public static final Card hoard;
    public static final Card kingsCourt;
    public static final Card loan;
    public static final Card mint;
    public static final Card monument;
    public static final Card mountebank;
    public static final Card peddler;
    public static final Card quarry;
    public static final Card rabble;
    public static final Card royalSeal;
    public static final Card talisman;
    public static final Card tradeRoute;
    public static final Card vault;
    public static final Card venture;
    public static final Card watchTower;
    public static final Card workersVillage;

    // Cornucopia expansion
    public static final Card hornOfPlenty;
    public static final Card fairgrounds;
    public static final Card farmingVillage;
    public static final Card fortuneTeller;
    public static final Card hamlet;
    public static final Card harvest;
    public static final Card horseTraders;
    public static final Card huntingParty;
    public static final Card jester;
    public static final Card menagerie;
    public static final Card remake;
    public static final Card tournament;
    public static final Card youngWitch;
    public static final Card bagOfGold;
    public static final Card diadem;
    public static final Card followers;
    public static final Card princess;
    public static final Card trustySteed;

    // Guilds expansion
    public static final Card advisor;
    public static final Card baker;
    public static final Card butcher;
    public static final Card candlestickMaker;
    public static final Card doctor;
    public static final Card herald;
    public static final Card journeyman;
    public static final Card masterpiece;
    public static final Card merchantGuild;
    public static final Card plaza;
    public static final Card soothsayer;
    public static final Card stonemason;
    public static final Card taxman;

    // Adventures expansion
    public static final Card coinOfTheRealm;
    public static final Card page;
    public static final Card peasant;
    public static final Card ratcatcher;
    public static final Card raze;
    public static final Card amulet;
    public static final Card caravanGuard;
    public static final Card dungeon;
    public static final Card gear;
    public static final Card guide;
    public static final Card duplicate;
    public static final Card magpie;
    public static final Card messenger;
    public static final Card miser;
    public static final Card port;
    public static final Card ranger;
    public static final Card transmogrify;
    public static final Card artificer;
    public static final Card bridgeTroll;
    public static final Card distantLands;
    public static final Card giant;
    public static final Card hauntedWoods;
    public static final Card lostCity;
    public static final Card relic;
    public static final Card royalCarriage;
    public static final Card storyteller;
    public static final Card swampHag;
    public static final Card treasureTrove;
    public static final Card wineMerchant;
    public static final Card hireling;

    public static final Card soldier;
    public static final Card treasureHunter;
    public static final Card fugitive;
    public static final Card warrior;
    public static final Card disciple;
    public static final Card hero;
    public static final Card champion;
    public static final Card teacher;

    public static final Card alms;
    public static final Card borrow;
    public static final Card quest;
    public static final Card save;
    public static final Card scoutingParty;
    public static final Card travellingFair;
    public static final Card bonfire;
    public static final Card expedition;
    public static final Card ferry;
    public static final Card plan;
    public static final Card mission;
    public static final Card pilgrimage;
    public static final Card ball;
    public static final Card raid;
    public static final Card seaway;
    public static final Card trade;
    public static final Card lostArts;
    public static final Card training;
    public static final Card inheritance;
    public static final Card pathfinding;
    
    // Empires expansion
    public static final Card archive;
    public static final Card bustlingVillage;
    public static final Card capital;
    public static final Card catapult;
    public static final Card chariotRace;
    public static final Card charm;
    public static final Card cityQuarter;
    public static final Card crown;
    public static final Card emporium;
    public static final Card encampment;
    public static final Card enchantress;
    public static final Card engineer;
    public static final Card farmersMarket;
    public static final Card fortune;
    public static final Card forum;
    public static final Card gladiator;
    public static final Card groundskeeper;
    public static final Card legionary;
    public static final Card overlord;
    public static final Card patrician;
    public static final Card plunder;
    public static final Card rocks;
    public static final Card royalBlacksmith;
    public static final Card sacrifice;
    public static final Card settlers;
    public static final Card temple;
    public static final Card villa;
    public static final Card wildHunt;
    
    public static final Card virtualCatapultRocks;
    public static final Card virtualEncampmentPlunder;
    public static final Card virtualGladiatorFortune;
    public static final Card virtualPatricianEmporium;
    public static final Card virtualSettlersBustlingVillage;
        
    public static final Card humbleCastle;
    public static final Card crumblingCastle;
    public static final Card smallCastle;
    public static final Card hauntedCastle;
    public static final Card opulentCastle;
    public static final Card sprawlingCastle;
    public static final Card grandCastle;
    public static final Card kingsCastle;
    public static final Card virtualCastle;
    
    public static final Card advance;
    public static final Card annex;
    public static final Card banquet;
    public static final Card conquest;
    public static final Card dominate;
    public static final Card delve;
    public static final Card donate;
    public static final Card ritual;
    public static final Card saltTheEarth;
    public static final Card tax;
    public static final Card triumph;
    public static final Card wedding;
    public static final Card windfall;
    
    public static final Card aqueduct;
    public static final Card arena;
    public static final Card banditFort;
    public static final Card basilica;
    public static final Card baths;
    public static final Card battlefield;
    public static final Card colonnade;
    public static final Card defiledShrine;
    public static final Card fountain;
    public static final Card keep;
    public static final Card labyrinth;
    public static final Card mountainPass;
    public static final Card museum;
    public static final Card obelisk;
    public static final Card orchard;
    public static final Card palace;
    public static final Card tomb;
    public static final Card tower;
    public static final Card triumphalArch;
    public static final Card wall;
    public static final Card wolfDen;
    
    // Promo Cards
    public static final Card walledVillage;
    public static final Card governor;
    public static final Card envoy;
    public static final Card prince;
    public static final Card blackMarket;
    public static final Card stash;
    public static final Card summon;
    public static final Card sauna;
    public static final Card avanto;
    
    public static final Card virtualSaunaAvanto;

    public static final Card virtualPlayerAttack;

    static {
        // nonKingdomCards
        virtualPlayerAttack = new CardImpl.Builder(Kind.VirtualAttack,0).build();
        heroismCards.add(one = new CardImpl.Builder(Cards.Kind.One, 0, CardType.Treasure).setPileSize(7).addHeroism(1).build());
        heroismCards.add(two = new CardImpl.Builder(Cards.Kind.Two, 2, CardType.Hero, CardType.Treasure).addHeroism(2).build());
        heroismCards.add(three = new CardImpl.Builder(Cards.Kind.Three, 2, CardType.Hero, CardType.Treasure).addHeroism(3).addBuys(1).upgradeCard(Kind.Two).build());

        nonKingdomCards.add(platinum = new CardImpl.Builder(Cards.Kind.Platinum, 9, CardType.Treasure).addHeroism(5).build());
        nonKingdomCards.add(gold = new CardImpl.Builder(Cards.Kind.Gold, 6, CardType.Treasure).addHeroism(3).build());
        nonKingdomCards.add(silver = new CardImpl.Builder(Cards.Kind.Silver, 3, CardType.Treasure).addHeroism(2).build());
        nonKingdomCards.add(copper = new CardImpl.Builder(Cards.Kind.Copper, 0, CardType.Treasure).addHeroism(1).build());
        nonKingdomCards.add(potion = new CardImpl.Builder(Cards.Kind.Potion, 4, CardType.Treasure).providePotion().build());

        nonKingdomCards.add(curse = new CardImpl.Builder(Cards.Kind.Curse, 0, CardType.Curse).vp(-1).build());

        nonKingdomCards.add(victoryTokens = new CardImpl(Cards.Kind.VictoryTokens, 0));

        nonKingdomCards.add(colony = new CardImpl.Builder(Cards.Kind.Colony, 11, CardType.Victory).vp(10).build());
        nonKingdomCards.add(province = new CardImpl.Builder(Cards.Kind.Province, 8, CardType.Victory).vp(6).build());
        nonKingdomCards.add(duchy = new CardImpl.Builder(Cards.Kind.Duchy, 5, CardType.Victory).vp(3).build());
        nonKingdomCards.add(estate = new CardImpl.Builder(Cards.Kind.Estate, 2, CardType.Victory).vp(1).build());


        // Arpad
        shieldCardsArpad.add(shield = new CardImpl.Builder(Kind.Shield, 2, CardType.Hero, CardType.Action, CardType.Defend, CardType.Equipment, CardType.InHandReaction).addCards(1).description("Put a card from hand on top of your deck. Reaction: Discard this to defend any player.").expansion(Expansion.Base).build());
        shieldCardsArpad.add(towerShield = new CardImpl.Builder(Kind.TowerShield, 1, CardType.Hero, CardType.Action, CardType.Defend, CardType.Equipment, CardType.Remains, CardType.RemainsReaction).addCards(2).upgradeCard(Kind.Shield).description("Put 2 cards from hand on top of your deck. Reaction: Discard this to defend any player.").expansion(Expansion.Base).build());
        swordCardsArpad.add(mastersSword = new CardImpl.Builder(Kind.MastersSword, 4, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack).addCards(1).addActions(2).setAttacks(1).expansion(Expansion.Base).build());
        swordCardsArpad.add(bloodstainedBlade = new CardImpl.Builder(Kind.BloodstainedBlade, 2, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack).addActions(2).addCards(1).setAttacks(1).setMight(2).upgradeCard(Kind.MastersSword).expansion(Expansion.Base).build());
        swordCardsArpad.add(hundredScimitar = new CardImpl.Builder(Kind.HundredScimitar, 1, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack).addActions(2).addCards(2).setAttacks(1).setMight(2).upgradeCard(Kind.BloodstainedBlade).expansion(Expansion.Base).build());
        heroCardsArpad.add(attack = new CardImpl.Builder(Kind.Attack, 0, CardType.Hero, CardType.Action, CardType.Attack).setPileSize(2).addCards(1).addActions(1).setAttacks(1).description("Put a card from hand on top of your deck").expansion(Expansion.Base).build());
        heroCardsArpad.add(virtualShield = new CardImpl.Builder(Kind.VirtualShield, 2, CardType.Hero, CardType.Action, CardType.InHandReaction).pileCreator(new ArpadShieldPileCreator()).expansion(Expansion.Base).build());
        heroCardsArpad.add(virtualSword = new CardImpl.Builder(Kind.VirtualSword, 4, CardType.Hero, CardType.Action, CardType.Attack).pileCreator(new ArpadSwordPileCreator()).expansion(Expansion.Base).build());
        heroCardsArpad.add(lamellarArmour = new CardImpl.Builder(Kind.LamellarArmour, 3, CardType.Hero, CardType.Action, CardType.Equipment, CardType.InHandReaction).setPileSize(4).addCards(2).description("Reaction: When you put this card on top of your draw deck, you may Trash 1.").expansion(Expansion.Base).build());
        heroCardsArpad.add(charge = new CardImpl.Builder(Cards.Kind.Charge, 5, CardType.Hero, CardType.Action, CardType.InHandReaction, CardType.OnAttack, CardType.Technique).setPileSize(3).addCards(2).setActions(0).setMight(2).description("You may not play any more actions, Might 2.  Reaction: When another player attacks, Banish this to give them Might 2").expansion(Expansion.Base).build());
        heroCardsArpad.add(virtualHeroism = new CardImpl.Builder(Kind.VirtualHeroism, 2, CardType.Hero, CardType.Treasure).pileCreator(new HeroismPileCreator()).expansion(Expansion.Base).build());
        heroCardsArpad.add(one);

        //Wiola
        heroCardsWiola.add(stalk = new CardImpl.Builder(Kind.Stalk, 0, CardType.Hero, CardType.Action).setPileSize(1).addActions(2).description("Reveal the top card of your deck, discard it or return it.").expansion(Expansion.Base).build());
        shotCardsWiola.add(pinpointShot = new CardImpl.Builder(Kind.PinpointShot, 3, CardType.Hero, CardType.Action, CardType.Technique, CardType.Remains, CardType.RemainsReaction, CardType.OnAttack).addActions(1).description("While Remaining: Reaction: When you make an attack, Banish this for +1 Might.").expansion(Expansion.Base).build());
        shotCardsWiola.add(comtempuousShot = new CardImpl.Builder(Kind.ContempuousShot, 0, CardType.Hero, CardType.Action, CardType.Technique, CardType.Remains, CardType.RemainsReaction, CardType.OnActivation, CardType.OnAttack).addActions(1).upgradeCard(Kind.PinpointShot).description("While Remaining: Reaction: When you attack, Discard this for +1 Might.  Reaction: Trash this to prevent a Foe from activating.").expansion(Expansion.Base).build());
        bowCardsWiola.add(longbow = new CardImpl.Builder(Kind.Longbow, 4, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack, CardType.Range).addActions(1).addCards(1).setAttacks(1).expansion(Expansion.Base).build());
        bowCardsWiola.add(prestigeBow = new CardImpl.Builder(Kind.PrestigeBow, 2, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack, CardType.Range).addActions(1).addCards(1).setAttacks(1).upgradeCard(Kind.Longbow).description("Get Might 2 if you have already played a bow this turn.").expansion(Expansion.Base).build());
        bowCardsWiola.add(enchantedBow= new CardImpl.Builder(Kind.EnchangtedBow, 2, CardType.Hero, CardType.Action, CardType.Attack, CardType.Equipment, CardType.Range).addActions(1).addCards(1).setAttacks(1).upgradeCard(Kind.PrestigeBow).description("Get Might 2 if you have already played a a bow this turn. You may discard any number of cards to draw the same number of cards.").expansion(Expansion.Base).build());
        heroCardsWiola.add(virtualShot = new CardImpl.Builder(Kind.VirtualShot, 3, CardType.Hero, CardType.Action, CardType.Technique).pileCreator(new WiolaShotPileCreator()).expansion(Expansion.Base).build());
        heroCardsWiola.add(virtualBowW = new CardImpl.Builder(Kind.VirtualBow, 4, CardType.Hero, CardType.Action, CardType.Attack, CardType.Range, CardType.Equipment).pileCreator(new WiolaBowPileCreator()).expansion(Expansion.Base).build());
        heroCardsWiola.add(vantagePoint = new CardImpl.Builder(Kind.VantagePoint, 5, CardType.Hero, CardType.Action, CardType.Remains).setPileSize(2).description("While Remaining: You are Invincible as long as there are more than 10 Foes. Discard this at the end of a turn where you make a Melee attack. When in Hand: Reaction: When another player makes an attack, Banish this to give them +1 Might.").expansion(Expansion.Base).build());
        heroCardsWiola.add(shoot = new CardImpl.Builder(Kind.Shoot, 0, CardType.Action, CardType.Hero, CardType.Range ).setAttacks(1).setPileSize(2).addCards(1).expansion(Expansion.Base).build());
        heroCardsWiola.add(patientHunter = new CardImpl.Builder(Kind.PatientHunter, 2, CardType.Hero, CardType.Action).setPileSize(4).addActions(2).description("Hunt for a bow or a shot.").expansion(Expansion.Base).build());
        heroCardsWiola.add(virtualHeroism = new CardImpl.Builder(Kind.VirtualHeroism, 2, CardType.Hero, CardType.Treasure).pileCreator(new HeroismPileCreator()).expansion(Expansion.Base).build());
        heroCardsWiola.add(one);

        //ZsuZsa
        heroCardsZsuzsa.add(sidestep = new CardImpl.Builder(Kind.Sidestep, 0, CardType.Hero, CardType.Action).setPileSize(2).description("+2 Actions or +1 Card.").expansion(Expansion.Base).build());
        bowCardsZsuzsa.add(shortbow = new CardImpl.Builder(Kind.Shortbow, 2, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Range).setAttacks(1).addCards(1).description("Reaction: During the Attack step of another player, Discard this to give them Range..").expansion(Expansion.Base).build()); //TODO Bulwark multiplayer
        bowCardsZsuzsa.add(crossbow = new CardImpl.Builder(Kind.Crossbow, 2, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack, CardType.Range).setAttacks(1).addCards(2).upgradeCard(Kind.Shortbow).description("Reaction: During the Attack step of another player, Discard this to give them Range..").expansion(Expansion.Base).build()); //TODO Bulwark multiplayer
        daggerCardsZsuzsa.add(dualDaggers = new CardImpl.Builder(Kind.DualDaggers, 3, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack).addActions(1).addCards(1).setAttacks(1).expansion(Expansion.Base).build());
        daggerCardsZsuzsa.add(nightDaggers = new CardImpl.Builder(Kind.NightDaggers, 1, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack).addActions(1).addCards(1).setAttacks(2).upgradeCard(Kind.DualDaggers).description("You may attack twice.").expansion(Expansion.Base).build());
        daggerCardsZsuzsa.add(burialDaggers= new CardImpl.Builder(Kind.BurialDaggers, 1, CardType.Hero, CardType.Action, CardType.Attack, CardType.Equipment).addActions(1).addCards(2).setAttacks(2).upgradeCard(Kind.NightDaggers).description("You may attack twice.").expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(virtualDaggers = new CardImpl.Builder(Kind.VirtualDaggers, 3, CardType.Hero, CardType.Action, CardType.Equipment).pileCreator(new ZsuzsaDaggerPileCreator()).expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(virtualBowZ = new CardImpl.Builder(Kind.VirtualBow, 2, CardType.Hero, CardType.Action, CardType.Attack, CardType.Range, CardType.Equipment).pileCreator(new ZsuzsaBowPileCreator()).expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(flankAttack = new CardImpl.Builder(Kind.FlankAttack, 4, CardType.Hero, CardType.Action, CardType.Remains, CardType.RemainsReaction, CardType.OnAttack, CardType.Technique).setPileSize(4).description("While Remaining: The next other player to attack gets +1 Might, discard this.").expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(knifeInTheBack= new CardImpl.Builder(Kind.KnifeInTheBack, 0, CardType.Action, CardType.Hero, CardType.Attack ).setAttacks(1).setPileSize(1).addCards(1).expansion(Expansion.Base).build());
        //Sneak should be turnend
        heroCardsZsuzsa.add(sneak = new CardImpl.Builder(Kind.Sneak, 5, CardType.Hero, CardType.Action, CardType.InHandManoeuvre, CardType.Defend).setPileSize(4).addActions(2).setMight(2).description("Manoeuvre: Banish this to be Invincible for one turn.").expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(virtualHeroism = new CardImpl.Builder(Kind.VirtualHeroism, 2, CardType.Hero, CardType.Treasure).pileCreator(new HeroismPileCreator()).expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(theDrop= new CardImpl.Builder(Kind.TheDrop, 4, CardType.Action, CardType.Hero, CardType.Remains, CardType.RemainsReaction, CardType.OnAttack, CardType.WhenDrawn, CardType.Technique).addCards(1).setPileSize(5).addActions(1).description("While Remaining: Reaction: When you Draw a Foe, Trash this to Banish it, ignoring When Drawn and Flyby abilities.  Reaction: Trash this, Trash 1 for +1 Might.").expansion(Expansion.Base).build());
        heroCardsZsuzsa.add(one);

        //Sandor
        staffCardsSandor.add(oakStaff= new CardImpl.Builder(Kind.OakStaff, 3, CardType.Hero, CardType.Action, CardType.Attack, CardType.Equipment).setAttacks(1).description("+2 Actions or +2 Cards.").expansion(Expansion.Base).build());
        staffCardsSandor.add(carvedStaff= new CardImpl.Builder(Kind.CarvedStaff, 0, CardType.Hero, CardType.Action, CardType.Attack, CardType.Range, CardType.Equipment).setAttacks(1).upgradeCard(Kind.OakStaff).description("+2 Actions or +2 Cards.").expansion(Expansion.Base).build());
        staffCardsSandor.add(ironShodStaff= new CardImpl.Builder(Kind.IronShodStaff, 1, CardType.Hero, CardType.Action, CardType.Equipment, CardType.Attack, CardType.Range).setAttacks(1).upgradeCard(Kind.CarvedStaff).description("+2 Actions or +2 Cards. You may take X Wounds for +X Might.").expansion(Expansion.Base).build());
        heroCardsSandor.add(virtualStaff = new CardImpl.Builder(Kind.VirtualStaff, 3, CardType.Hero, CardType.Action, CardType.Attack, CardType.Equipment).pileCreator(new SandorStaffPileCreator()).expansion(Expansion.Base).build());
        heroCardsSandor.add(sanctusCharm= new CardImpl.Builder(Kind.SanctusCharm, 4, CardType.Hero, CardType.Action, CardType.InHandManoeuvre, CardType.Defend).setPileSize(3).addActions(2).addCards(1).description("Another player may draw a card.  Manoeuvre: Discard this, you take at most 1 Wound this turn.").expansion(Expansion.Base).build());
        heroCardsSandor.add(prayer  = new CardImpl.Builder(Kind.Prayer, 5, CardType.Hero, CardType.Action, CardType.Remains, CardType.RemainsManoeuvre, CardType.Technique).setPileSize(4).addActions(1).callWhenActionResolved().description("Remains.  While Remaining : Any player may discard this at any time for +2 Cards. If it was not thier turn, Banish this.").expansion(Expansion.Base).build());
        heroCardsSandor.add(bless = new CardImpl.Builder(Kind.Bless, 0, CardType.Hero, CardType.Action).setPileSize(2).description("Pick another player, they may Discard 1 for +1 Card.").expansion(Expansion.Base).build());
        heroCardsSandor.add(beat = new CardImpl.Builder(Kind.Beat, 0, CardType.Hero, CardType.Action, CardType.Attack).setAttacks(1).setPileSize(1).addActions(2).expansion(Expansion.Base).build());
        healingCardsSandor.add(healingHerbs= new CardImpl.Builder(Kind.HealingHerbs, 2, CardType.Hero, CardType.Action, CardType.Equipment).addCards(1).description("Every player may Trash 1.").expansion(Expansion.Base).build());
        healingCardsSandor.add(healingBalm= new CardImpl.Builder(Kind.HealingBalm, 2, CardType.Hero, CardType.Action, CardType.Equipment).upgradeCard(Kind.HealingHerbs).addCards(1).description("A player reveals 5 cards from their deck, Banish all Wounds revealed and Discard the rest.").expansion(Expansion.Base).build());
        heroCardsSandor.add(virtualHealing = new CardImpl.Builder(Kind.VirtualHealing, 2, CardType.Hero, CardType.Action, CardType.Equipment).pileCreator(new SandorHealingPileCreator()).expansion(Expansion.Base).build());
        heroCardsSandor.add(virtualHeroism = new CardImpl.Builder(Kind.VirtualHeroism, 2, CardType.Hero, CardType.Treasure).pileCreator(new HeroismPileCreator()).expansion(Expansion.Base).build());
        heroCardsSandor.add(orison = new CardImpl.Builder(Kind.Orison, 0, CardType.Hero, CardType.Action, CardType.Remains, CardType.RemainsReaction).callWhenTurnStarts().setPileSize(2).description("While Remaining: Reaction: At the start of your turn, you may Discard this for +1 Card.").expansion(Expansion.Base).build());
        heroCardsSandor.add(one);

        cardsGoblin.add(goblinTroop = new CardImpl.Builder(Kind.GoblinTroop, 1, CardType.Enemy).build());
        cardsGoblin.add(goblinAlchemist = new CardImpl.Builder(Kind.GoblinAlchemist, 2, CardType.Enemy, CardType.Activate).description("Activate: If adjacent to an alchemist of a blast, take 2 Wounds.").build());
        cardsGoblin.add(goblinRabble = new CardImpl.Builder(Kind.GoblinRabble, 1, CardType.Enemy, CardType.WhenDrawn).description("When Drawn: Banish 2 Foes, draw 2 Foes, ignore the 'When Drawn' text of any Rabbles thus drawn.").build());
        cardsGoblin.add(catapultBulwark= new CardImpl.Builder(Kind.GobCatapult, 2, CardType.Enemy, CardType.Activate, CardType.Range, CardType.Blast).description("Activate: Each player discards the top card of their deck. If it was a Wound, they take a Wound.").build());
        cardsGoblin.add(goblinHeavy = new CardImpl.Builder(Kind.GoblinHeavy, 2, CardType.Enemy, CardType.Activate).description("Activate: Take a Wound if a troop is in play.").build());
        cardsGoblin.add(goblinArcher = new CardImpl.Builder(Kind.GoblinArcher, 1, CardType.Enemy, CardType.Activate, CardType.Range).description("Activate: If there are 2 or more archers in play, take a Wound.").build());
        cardsGoblin.add(kneena = new CardImpl.Builder(Kind.KneenaGoblin, 2, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Range).description("The Goblin Druid - Activate: Each player discards the top card of their deck. If it was a Location card, they take a Wound.").build());
        cardsGoblin.add(kmblee = new CardImpl.Builder(Kind.KmbleeGoblin, 2, CardType.Enemy, CardType.Crown, CardType.Activate).description("The Goblin Scout - Activate: Draw a foe.").build());
        cardsGoblin.add(kmron = new CardImpl.Builder(Kind.KmronGoblin, 3, CardType.Enemy, CardType.Crown, CardType.Activate).description("The Goblin Chief - Activate: Take a Wound.").build());
        cardsGoblin.add(ghutz = new CardImpl.Builder(Kind.GhutzGoblin, 1, CardType.Enemy, CardType.Crown, CardType.Activate).description("The Goblin Berserker - Activate: Take a Wound for each Goblin in play.").build());
        cardsGoblin.add(mgzwel = new CardImpl.Builder(Kind.MgzwelGoblin, 2, CardType.Enemy, CardType.Crown).description("The Goblin Armourer - While in Play: Goblins up to 2 spaces away get +1 Armour. MgzwelGoblin does not increase his own Armour.").build());
        //cardsGoblinHorde.add(virtualEnemy = new CardImpl.Builder(Kind.VirtualEnemy, 3, CardType.Enemy).pileCreator(new GoblinPileCreator()).expansion(Expansion.Base).build());

        cardsLizard.add(lizardTroop = new CardImpl.Builder(Kind.LizardTroop, 1, CardType.Enemy).description("+1 Armour if adjacent to a Heavy.").build());
        cardsLizard.add(fireCatapult = new CardImpl.Builder(Kind.FireCatapult, 2, CardType.Enemy, CardType.Activate, CardType.Blast, CardType.Range).description("Activate: Reveal your hand. If a Wound is revealed or you have a Wound underneath a card Remaining in play, take a Wound.").build());
        cardsLizard.add(lizardRabble = new CardImpl.Builder(Kind.LizardRabble, 1, CardType.Enemy, CardType.Activate).description("Activate: Kill this, draw a Foe.").build());
        cardsLizard.add(batteringRam= new CardImpl.Builder(Kind.BatteringRam, 2, CardType.Enemy, CardType.Activate, CardType.Blast).description("Activate: Take a Wound for each Blast activation there has already been this turn..").build());
        cardsLizard.add(lizardHeavy = new CardImpl.Builder(Kind.LizardHeavy, 2, CardType.Enemy, CardType.Activate).description("Activate: Take a Wound for each adjacent troop.").build());
        cardsLizard.add(lizardBombardier = new CardImpl.Builder(Kind.LizardBombardier, 1, CardType.Enemy, CardType.Activate).description("Activate: Discard a card you have Remaining in play.").build());
        cardsLizard.add(giantCorpseHound= new CardImpl.Builder(Kind.GiantCorpseHound, 2, CardType.Enemy, CardType.Crown, CardType.Undead).description("The current player takes a Wound every time a Foe other than a corpse hound is killed.").build());
        cardsLizard.add(kyung = new CardImpl.Builder(Kind.KyungLizard, 2, CardType.Enemy, CardType.Crown, CardType.Range).description("The Lizard Mystic - You cannot buy Hero cards").build());
        cardsLizard.add(yoonIseul = new CardImpl.Builder(Kind.YoonIseulLizard, 3, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Horseman).description("The Lizard Queen - Activate: Activate the Foe before her, then activate the Foe after her. She cannot be activated more than once a turn.").build());
        cardsLizard.add(sang = new CardImpl.Builder(Kind.SangLizard, 2, CardType.Enemy, CardType.Crown).description("The Lizard Alchemist - Blast are Invincible.").build());
        cardsLizard.add(hyeon = new CardImpl.Builder(Kind.HyeonLizard, 3, CardType.Enemy, CardType.Crown, CardType.Activate).description("The Lizard Champion - Activate : If you did not kill a Foe this turn, take 2 Wounds").build());
        //cardsLizardCrusade.add(virtualEnemy = new CardImpl.Builder(Kind.VirtualEnemy, 3, CardType.Enemy).pileCreator(new LizardPileCreator()).expansion(Expansion.Base).build());

        cardsElf.add(elfTroop = new CardImpl.Builder(Kind.ElfTroop, 1, CardType.Enemy, CardType.Activate).description("If there are 4 or more Elves in play, take a Wound").build());
        cardsElf.add(flameBallista = new CardImpl.Builder(Kind.FlameBallista, 1, CardType.Enemy, CardType.Activate, CardType.Blast, CardType.Range).description("Activate: Take a Wound in hand for each card you have Remaining in play.").build());
        cardsElf.add(elfRabble = new CardImpl.Builder(Kind.ElfRabble, 1, CardType.Enemy, CardType.WhenDrawn).description("When Drawn: Banish a non rabble Foe.  While In Play: Counts of 2 Elves.").build());
        cardsElf.add(elfDruid = new CardImpl.Builder(Kind.ElfDruid, 2, CardType.Enemy, CardType.Activate, CardType.Range).description("Activate: Discard the top 2 cards of your deck, take a Wound for each Location card revealed.").build());
        cardsElf.add(corpseVine = new CardImpl.Builder(Kind.CorpseVine, 1, CardType.Enemy).description("While In Play: You cannot buy Location cards.").build());
        cardsElf.add(elfArcher = new CardImpl.Builder(Kind.ElfArcher, 1, CardType.Enemy, CardType.Activate, CardType.Range).description("Activate: If there are 2 or more archers in play, take a Wound for each elf in play, up to a maximum of 3.").build());
        cardsElf.add(kiri= new CardImpl.Builder(Kind.KiriElf, 2, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Undead, CardType.Range, CardType.Necromancer).description("The Elf Necromancer - Activate: Take a Wound for each corpse in play.  When Killed: Resurrect 2, elves or corpses.").build());
        cardsElf.add(ruiha = new CardImpl.Builder(Kind.RuihaElf, 4, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Horseman).description("The Fallen Elf - Activate: Take a Wound.  While In Play: Adjacent elves and demons are invincible.  When Killed: Draw 2 Foes.").build());
        cardsElf.add(tane = new CardImpl.Builder(Kind.TaneElf, 2, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Horseman).description("The Elf Lancer - Activate: If there is a Foe on both sides of him, take 2 Wounds.").build());
        cardsElf.add(vepor = new CardImpl.Builder(Kind.VeporDemon, 2, CardType.Enemy, CardType.Crown, CardType.Activate).description("Demon of Water - Activate: Reveal the top 2 cards of your deck, Trash one of them, Discard the other.  When Killed: Take a Wound.").build());
        cardsElf.add(xaphan = new CardImpl.Builder(Kind.XaphanDemon, 2, CardType.Enemy, CardType.Crown, CardType.Activate).description("Demon of Fire- Activate : Trash 1.  When Killed: Take 2 Wounds.").build());

        cardsWinter.add(brokenCorpse = new CardImpl.Builder(Kind.BrokenCorpse, 1, CardType.Enemy, CardType.Activate, CardType.Undead).description("If you have taken a Wound this turn, take a Wound.  When Killed: Kill a corpse.").build());
        cardsWinter.add(staleCorpse = new CardImpl.Builder(Kind.StaleCorpse, 1, CardType.Enemy, CardType.Activate, CardType.Undead).description("If there are 5 or more corpses in play, take a Wound and Discard 1.").build());
        cardsWinter.add(freshCorpse = new CardImpl.Builder(Kind.FreshCorpse, 1, CardType.Enemy, CardType.Activate, CardType.Undead).description("If there are 3 or more corpses in play, take a Wound.").build());
        cardsWinter.add(enshroudingMist = new CardImpl.Builder(Kind.EnshroudingMist, 1, CardType.Enemy, CardType.WhenDrawn, CardType.Range).description("When Drawn: Draw a Foe.  While in Play: Adjacent corpses and rabbles get +2 Armour.").build());
        cardsWinter.add(spectre = new CardImpl.Builder(Kind.Spectre, 2, CardType.Enemy, CardType.Undead).description("While In Play: Each player takes a Wound for each card they Trash.").build());
        cardsWinter.add(embalmingAcolyte = new CardImpl.Builder(Kind.EmbalmedAcolyte, 3, CardType.Enemy, CardType.Activate, CardType.Undead).description("Activate: Discard 1 or take a Wound.").build());
        cardsWinter.add(theLeftHand= new CardImpl.Builder(Kind.TheLeftHandGoblin, 2, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.WhenDrawn, CardType.Undead, CardType.Range, CardType.Necromancer).description("Goblin Necromancer - When Drawn: Reveal a Foe. If a goblin or Undead put it into play, otherwise kill it.  Activate: The player to your left takes a Wound.").build());
        cardsWinter.add(theRightHand= new CardImpl.Builder(Kind.TheRightHandHuman, 2, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.WhenDrawn, CardType.Undead, CardType.Range, CardType.Necromancer).description("Human Necromancer - When Drawn: Reveal a Foe. If a human or Undead put it into play, otherwise kill it.  Activate: The player to your right takes a Wound.").build());
        cardsWinter.add(theCatacombite = new CardImpl.Builder(Kind.TheCatacombite, 1, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.WhenDrawn, CardType.Undead).description("When Drawn: Take a Wound.  Activate: Take 2 Wounds.  When Killed: If this was killed by Might 1, put it on top of the Foe deck.").build());
        cardsWinter.add(horrorOfFlesh = new CardImpl.Builder(Kind.HorrorOfFlesh, 3, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.Undead).description("Activate: Kill a corpse. If you did, take 2 Wounds.").build());
        cardsWinter.add(kangaxxTheLich = new CardImpl.Builder(Kind.KangaxxTheLich, 3, CardType.Enemy, CardType.Crown, CardType.Activate, CardType.WhenDrawn, CardType.Undead).description("When Drawn: Reveal a Foe.  If Undead put it into play, otherwise kill it.  Activate: Take a Wound, draw a Foe.  While in Player: It is Invincible if a necromancer is in play.  When Killed: Kill all Undead Foes.").build());

        woundPile.add(virtualWound = new CardImpl.Builder(Kind.VirtualWound, 0, "Wounds", CardType.Wound).pileCreator(new WoundPileCreator()).expansion(Expansion.Base).build());
        woundCards.add(seriousWound = new CardImpl.Builder(Kind.SeriousWound, 0, CardType.Wound).expansion(Expansion.Base).build());
        woundCards.add(glancingWound = new CardImpl.Builder(Kind.GlancingWound, 0, CardType.Wound, CardType.InHandManoeuvre, CardType.Action).trashOnUse().description("Manoeuvre: You may spend 2 Actions to Banish this.").expansion(Expansion.Base).build());
        woundCards.add(oldWound = new CardImpl.Builder(Kind.OldWound, 0, CardType.Wound, CardType.InHandManoeuvre, CardType.Action).description("Manoeuvre: You may Discard another Wound to Banish this.").expansion(Expansion.Base).build());
        woundCards.add(staggeringWound = new CardImpl.Builder(Kind.StaggeringWound, 0, CardType.Wound, CardType.InHandReaction).expansion(Expansion.Base).description("Reaction: When this is Trashed or Banished, +2 Cards.").build());
        woundCards.add(weakeningWound = new CardImpl.Builder(Kind.WeakeningWound, 0, CardType.Wound, CardType.InHandReaction, CardType.AttackStepEnd).description("Reaction: If your Attack Step ends without you making an attack, you may Banish this.").expansion(Expansion.Base).build());
        //flesh wound isn't an action just as a workaround for not implementing coin value from hand
        woundCards.add(fleshWound = new CardImpl.Builder(Kind.FleshWound, 0, CardType.Wound).description("Manoeuvre: You may Discard a total of 3 Heroism to Banish this.").expansion(Expansion.Base).build());

        locationCardsFortBriggs.add(sanitorium = new CardImpl.Builder(Kind.Sanitorium, 3, CardType.Location, CardType.Action).setPileSize(4).description("Trash 2.  +1 Card.").expansion(Expansion.Base).build());
        locationCardsFortBriggs.add(guardTower = new CardImpl.Builder(Kind.GuardTower, 3, CardType.Location, CardType.Action).setPileSize(4).addActions(1).addCards(1).description("Reveal the top Foe card and Banish it or put it back on top of the Foe deck.").expansion(Expansion.Base).build());
        locationCardsFortBriggs.add(rockFallTrap = new CardImpl.Builder(Kind.RockFallTrap, 2, CardType.Location, CardType.Action, CardType.Fortification).setPileSize(8).trashOnUse().description("Trash this. Attack with Might 2 melee.").expansion(Expansion.Base).build());
        locationCardsFortBriggs.add(stoneWalls   = new CardImpl.Builder(Kind.StoneWalls, 4, CardType.Location, CardType.Action, CardType.Fortification, CardType.RemainsReaction, CardType.OnAttack, CardType.Defend, CardType.Remains).setPileSize(8).addActions(2).addCards(1).description("While Remaining: Reaction: Trash this to defend any player.  Reaction: When you make an attack, Trash this for +1 Might.").expansion(Expansion.Base).build());
        locationCardsFortBriggs.add(mess = new CardImpl.Builder(Kind.Mess, 4, CardType.Location, CardType.Action).setPileSize(4).addCards(3).expansion(Expansion.Base).build());
        locationCardsFortBriggs.add(armoury = new CardImpl.Builder(Kind.Armoury, 5, CardType.Location, CardType.Action).setPileSize(4).addActions(2).addBuys(1).description("Any Equipment you buy this turn goes on top of your deck.").expansion(Expansion.Base).build());

        locationCardsCitadelXajorkith.add(oracularTurrent = new CardImpl.Builder(Kind.OracularTurret, 4, CardType.Location, CardType.Action).setPileSize(4).addCards(2).description("You may reorder the top 3 Foes on the deck. Location cards cost 1 less Heroism.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkith.add(sacredVault = new CardImpl.Builder(Kind.SacredVault, 4, CardType.Location, CardType.Action, CardType.Remains, CardType.RemainsManoeuvre).callWhenTurnStarts().callWhenActionResolved().setPileSize(4).description("Put a card from hand under this card.  While Remaining : Manoeuvre : Discard this, take the card underneath into hand.  Manoeuvre : Discard this, Trash the card underneath, Trash 1.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkithGate.add(greatNorthGate = new CardImpl.Builder(Kind.GreatNorthGate, 5, CardType.Location, CardType.Action, CardType.Fortification).addActions(2).description("You may discard 2 cards for +1 Might.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkithGate.add(greatEastGate = new CardImpl.Builder(Kind.GreatEastGate, 5, CardType.Location, CardType.Action, CardType.Fortification).addActions(2).upgradeCard(Kind.GreatNorthGate).description("You may discard 1 card for +1 Might.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkithGate.add(greatWestGate = new CardImpl.Builder(Kind.GreatWestGate, 5, CardType.Location, CardType.Action, CardType.Fortification).addActions(2).upgradeCard(Kind.GreatEastGate).description("You may discard X card for +X Might.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkithGate.add(greatSouthGate = new CardImpl.Builder(Kind.GreatSouthGate, 5, CardType.Location, CardType.Action, CardType.Fortification).addActions(2).addMight(1).upgradeCard(Kind.GreatWestGate).description("You get +1 Might.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkith.add(virtualGate= new CardImpl.Builder(Kind.VirtualGate, 5, CardType.Location, CardType.Action, CardType.Fortification).pileCreator(new GatePileCreator()).expansion(Expansion.Base).build());
        locationCardsCitadelXajorkith.add(citadelWalls   = new CardImpl.Builder(Kind.CitadelWalls, 5, CardType.Location, CardType.Action, CardType.Fortification, CardType.Remains).setPileSize(8).addActions(2).addCards(1).description("While Remaining : Any time you would receive a Wound, not in your hand, place it underneath this. If there are 3 Wounds underneath this, Trash this and Banish the Wounds.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkith.add(barracks = new CardImpl.Builder(Kind.Barracks, 6, CardType.Location, CardType.Action).setPileSize(8).addCards(3).addActions(1).description("Discard 1.").expansion(Expansion.Base).build());
        locationCardsCitadelXajorkith.add(defensiveTrebuchet = new CardImpl.Builder(Kind.DefensiveTrebuchet, 6, CardType.Location, CardType.Action).setPileSize(4).description("Banish a Wound. Attack with Might 2 melee or range. You may not attack in your Attack Step this turn.").expansion(Expansion.Base).build());

        locationCardsHoltvaros.add(crypt = new CardImpl.Builder(Kind.Crypt, 3, CardType.Location, CardType.Action).setPileSize(4).addActions(1).description("Trash 1. Discard 1. +1 Card.").expansion(Expansion.Base).build());
        locationCardsHoltvaros.add(townSquare = new CardImpl.Builder(Kind.TownSquare, 4, CardType.Location, CardType.Action, CardType.Remains, CardType.RemainsManoeuvre, CardType.RemainsReaction, CardType.WhenDrawn).callWhenTurnStarts().callWhenActionResolved().setPileSize(4).addActions(2).addCards(1).description("While Remaining: Reaction: Discard this to cancel a When Drawn ability on a Foe.  Manoeuvre: Banish all squares Remaining in play.  X is how many were Banished. All players who lost a square get +X cards.").expansion(Expansion.Base).build());
        locationCardsHoltvarosMilitia.add(terrifiedMilitia = new CardImpl.Builder(Kind.TerrifiedMilitia, 4, CardType.Location, CardType.Action, CardType.Defenders).addActions(1).addCards(1).description("You may now Banish this to attack with Might 1 Melee.  Any Time: Reaction: If this is Discarded, get +1 Might.").expansion(Expansion.Base).build());
        locationCardsHoltvarosMilitia.add(ralliedMilitia = new CardImpl.Builder(Kind.RalliedMilitia, 0, CardType.Location, CardType.Action, CardType.Defenders, CardType.InHandManoeuvre).addActions(1).addCards(1).upgradeCard(Kind.TerrifiedMilitia).description("Manoeuvre: Discard this to attack with Might 1 Melee and get +1 Might.").expansion(Expansion.Base).build());
        locationCardsHoltvaros.add(virtualMilitia= new CardImpl.Builder(Kind.VirtualMilitia, 4, CardType.Location, CardType.Action, CardType.Defenders).pileCreator(new MilitiaPileCreator()).expansion(Expansion.Base).build());
        locationCardsHoltvaros.add(watermill   = new CardImpl.Builder(Kind.Watermill, 5, CardType.Location, CardType.Action).setPileSize(4).addActions(2).addCards(2).description("Discard 2.").expansion(Expansion.Base).build());
        locationCardsHoltvaros.add(belltower = new CardImpl.Builder(Kind.Belltower, 2, CardType.Location, CardType.Action, CardType.InHandReaction, CardType.OnTrash).setPileSize(4).addCards(1).description("You may move a Foe to the end of the Foe line. You may Discard 1.  Reaction: When you Trash another card, Discard this for +3 Cards.").expansion(Expansion.Base).build());
        locationCardsHoltvaros.add(well = new CardImpl.Builder(Kind.Well, 3, CardType.Location, CardType.Action).setPileSize(4).description("Reveal the bottom card of any player deck, put it on top or Discard it.  +2 Cards.").expansion(Expansion.Base).build());

        locationCardsSylvanHeights.add(samuGarden = new CardImpl.Builder(Kind.SamuGarden,2, CardType.Location, CardType.Action).setPileSize(4).addActions(2).description("+1 Buy for Techniques only. All players Hunt for a Technique.").expansion(Expansion.Base).build());
        locationCardsSylvanHeights.add(virtualChorus = new CardImpl.Builder(Kind.VirtualChorus,3, CardType.Location, CardType.Action, CardType.Technique).pileCreator(new ChorusPileCreator()).expansion(Expansion.Base).build());
        locationCardsSylvanChorus.add(elvenChorus = new CardImpl.Builder(Kind.ElvenChorus,3, CardType.Location, CardType.Action, CardType.Technique).addActions(1).description("+1 Might if you have already played a chorus this turn.").expansion(Expansion.Base).build());
        locationCardsSylvanChorus.add(celestialChorus = new CardImpl.Builder(Kind.CelestialChorus,5, CardType.Location, CardType.Action, CardType.Technique).addActions(1).description("+2 Might if you have already played a chorus this turn.").expansion(Expansion.Base).build());
        //TODO maybe onDrawn as well
        locationCardsSylvanHeights.add(silkenSnare = new CardImpl.Builder(Kind.SilkenSnare,3, CardType.Location, CardType.Action, CardType.Fortification, CardType.Remains, CardType.RemainsManoeuvre, CardType.OnActivation).callWhenActionResolved().setPileSize(8).description("While Remaining: Manoeuvre - Banish this to Banish a Foe. Manoeuvre - If you have another snare in play, Banish this to attack with Might 2 Range.").expansion(Expansion.Base).build());
        //TODO treetopspire , CardType.InHandManoeuvre
        locationCardsSylvanHeights.add(treetopSpire = new CardImpl.Builder(Kind.TreetopSpire,3, CardType.Location, CardType.Action).setPileSize(4).addCards(2).description("You may discard a Heroism card for +1 Action. While in Hand: Manoeuvre - Discard this, another player may Discard a Heroism card for +2 Cards.").expansion(Expansion.Base).build());
        locationCardsSylvanHeights.add(radiantPool = new CardImpl.Builder(Kind.RadiantPool,4, CardType.Location, CardType.Action).setPileSize(4).description("Reveal the top 2 cards of your deck - either Trash 1 of them and put the other back on your deck, or Discard both.").expansion(Expansion.Base).build());
        locationCardsSylvanHeights.add(galleryOfLeaves = new CardImpl.Builder(Kind.GalleryOfLeaves,5, CardType.Location, CardType.Action).addActions(2).addCards(1).setPileSize(4).description("Look at the top card of every deck. Banish or replace the top card of the Foe and Wound decks, Discard or replace the top card of each player deck.").expansion(Expansion.Base).build());

        actionCardsBaseGame.add(bureaucrat = new CardImpl.Builder(Cards.Kind.Bureaucrat, 4, CardType.Action, CardType.Attack).description("Gain a Silver onto your deck. Each other player reveals a Victory card from their hand and puts it onto their deck (or reveals a hand with no Victory cards).").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(cellar = new CardImpl.Builder(Cards.Kind.Cellar, 2, CardType.Action).addActions(1).description("Discard any number of cards, then draw that many.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(chapel = new CardImpl.Builder(Cards.Kind.Chapel, 2, CardType.Action).description("Trash up to 4 cards from your hand.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(councilRoom = new CardImpl.Builder(Cards.Kind.CouncilRoom, 5, CardType.Action).addCards(4).addBuys(1).description("Each other player draws a card.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(festival = new CardImpl.Builder(Cards.Kind.Festival, 5, CardType.Action).addActions(2).addBuys(1).addHeroism(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(gardens = new CardImpl.Builder(Cards.Kind.Gardens, 4, CardType.Victory).description("Worth 1 Victory Point for every 10 cards in your deck (rounded down).").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(laboratory = new CardImpl.Builder(Cards.Kind.Laboratory, 5, CardType.Action).addActions(1).addCards(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(library = new CardImpl.Builder(Cards.Kind.Library, 5, CardType.Action).description("Draw until you have 7 cards in hand, skipping any Action cards you choose to; set those aside, discarding them afterwards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(market = new CardImpl.Builder(Cards.Kind.Market, 5, CardType.Action).addActions(1).addBuys(1).addHeroism(1).addCards(1).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(militia = new CardImpl.Builder(Cards.Kind.Militia, 4, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player discards down to 3 cards in hand.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(mine = new CardImpl.Builder(Cards.Kind.Mine, 5, CardType.Action).description("You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to (3) Coins more than it.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(moat = new CardImpl.Builder(Cards.Kind.Moat, 2, CardType.Action, CardType.InHandReaction).addCards(2).description("When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack.").description("When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(moneyLender = new CardImpl.Builder(Cards.Kind.Moneylender, 4, CardType.Action).description("You may trash a Copper from your hand for +(3) Coins.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(remodel = new CardImpl.Builder(Cards.Kind.Remodel, 4, CardType.Action).trashForced().description("Trash a card from your hand. Gain a card costing up to (2) Coins more than it.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(smithy = new CardImpl.Builder(Cards.Kind.Smithy, 4, CardType.Action).addCards(3).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(throneRoom = new CardImpl.Builder(Cards.Kind.ThroneRoom, 4, CardType.Action).description("You may play an Action card from your hand twice.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(village = new CardImpl.Builder(Cards.Kind.Village, 3, CardType.Action).addCards(1).addActions(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(witch = new CardImpl.Builder(Cards.Kind.Witch, 5, CardType.Action, CardType.Attack).addCards(2).description("Each other player gains a Curse.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(workshop = new CardImpl.Builder(Cards.Kind.Workshop, 3, CardType.Action).description("Gain a card costing up to 4 Coins.").expansion(Expansion.Base).build());

        actionCardsBaseGame2E.add(artisan = new CardImpl.Builder(Cards.Kind.Artisan, 6, CardType.Action).description("Gain a card to your hand costing up to (5) Coins. Put a card from your hand onto your deck.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(bandit = new CardImpl.Builder(Cards.Kind.Bandit, 5, CardType.Action, CardType.Attack).description("Gain a Gold. Each other player reveals the top two cards of their deck, trashes a revealed Treasure other than Copper, and discards the rest.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(harbinger = new CardImpl.Builder(Cards.Kind.Harbinger, 3, CardType.Action).addCards(1).addActions(1).description("Look through your discard pile. You may put a card from it onto your deck.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(merchant = new CardImpl.Builder(Cards.Kind.Merchant, 3, CardType.Action).addCards(1).addActions(1).description("The first time you play a Silver this turn, +(1) Coin.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(poacher = new CardImpl.Builder(Cards.Kind.Poacher, 4, CardType.Action).addCards(1).addActions(1).addHeroism(1).description("Discard a card per empty Supply pile.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(sentry = new CardImpl.Builder(Cards.Kind.Sentry, 5, CardType.Action).addCards(1).addActions(1).description("Look at the top 2 cards of your deck. Trash and/or discard any number of them. Put the rest back on top on any order.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(vassal = new CardImpl.Builder(Cards.Kind.Vassal, 3, CardType.Action).addHeroism(2).description("Discard the top card of your deck. If it's an Action card, you may play it.").expansion(Expansion.Base).build());
        actionCardsBaseGameAll.addAll(actionCardsBaseGame2E);
        actionCardsBaseGame2E.addAll(actionCardsBaseGame);

        actionCardsBaseGame.add(adventurer = new CardImpl.Builder(Cards.Kind.Adventurer, 6, CardType.Action).description("Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(chancellor = new CardImpl.Builder(Cards.Kind.Chancellor, 3, CardType.Action).addHeroism(2).description("You may immediately put your deck into your discard pile.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(feast = new CardImpl.Builder(Cards.Kind.Feast, 4, CardType.Action).trashOnUse().description("Trash this card. Gain a card costing up to 5 coin.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(spy = new CardImpl.Builder(Cards.Kind.Spy, 4, CardType.Action, CardType.Attack).addCards(1).addActions(1).description("Each player (including you) reveals the top card of his deck and either discards it or puts it back, your choice.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(thief = new CardImpl.Builder(Cards.Kind.Thief, 4, CardType.Action, CardType.Attack).description("Each other player reveals the top 2 cards of his deck. If they revelaed any Treasure cards, they trash one of them that you choose. You may gain any or all of these trashed cards. They discard the other revealed cards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(woodcutter = new CardImpl.Builder(Cards.Kind.Woodcutter, 3, CardType.Action).addBuys(1).addHeroism(2).expansion(Expansion.Base).build());

        actionCardsBaseGameAll.addAll(actionCardsBaseGame);

        //actionCardsBaseGameAll.addAll(heroCardsArpad);
        
        // The Goblin Horde
        actionCardsIntrigue.add(baron = new CardImpl.Builder(Cards.Kind.Baron, 4, CardType.Action).addBuys(1).description("You may discard an Estate for +(4) Coins. If you don't, gain an Estate.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(bridge = new CardImpl.Builder(Cards.Kind.Bridge, 4, CardType.Action).addBuys(1).addHeroism(1).description("This turn, cards (everywhere) cost (1) Coin less, but not less than (0) Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(conspirator = new CardImpl.Builder(Cards.Kind.Conspirator, 4, CardType.Action).addHeroism(2).description("If you've played 3 or more Actions this turn (counting this): +1 Card, +1 Action.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(courtyard = new CardImpl.Builder(Cards.Kind.Courtyard, 2, CardType.Action).addCards(3).description("Put a card from your hand onto your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(duke = new CardImpl.Builder(Cards.Kind.Duke, 5, CardType.Victory).description("Worth 1 Victory Point per Duchy you have.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(harem = new CardImpl.Builder(Cards.Kind.Harem, 6, CardType.Treasure, CardType.Victory).addHeroism(2).vp(2).expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(ironworks = new CardImpl.Builder(Cards.Kind.Ironworks, 4, CardType.Action).description("Gain a card costing up to 4 Coins. If the gained card is an... Action card, +1 Action. Treasure card, +1 Coin. Victory card, +1 Card.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(masquerade = new CardImpl.Builder(Cards.Kind.Masquerade, 3, CardType.Action).addCards(2).description("Each player with any cards in hand passes one to the next such player to their left, at once. Then you may trash a card from your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(miningVillage = new CardImpl.Builder(Cards.Kind.MiningVillage, 4, CardType.Action).addCards(1).addActions(2).description("You may trash this for +(2) Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(minion = new CardImpl.Builder(Cards.Kind.Minion, 5, CardType.Action, CardType.Attack).addActions(1).description("Choose one: +(2) Coins; or discard your hand, +4 Cards, and each other player with at least 5 cards in hand discards their hand and draws 4 cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(nobles = new CardImpl.Builder(Cards.Kind.Nobles, 6, CardType.Action, CardType.Victory).vp(2).description("Choose one: +3 Cards; or +2 Actions.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(pawn = new CardImpl.Builder(Cards.Kind.Pawn, 2, CardType.Action).description("Choose two: +1 Card; +1 Action; +1 Buy; +(1) Coin. The choices must be different.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(shantyTown = new CardImpl.Builder(Cards.Kind.ShantyTown, 3, CardType.Action).addActions(2).description("Reveal you hand. If you have no Action cards in hand, +2 Cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(steward = new CardImpl.Builder(Cards.Kind.Steward, 3, CardType.Action).description("Choose one: +2 Cards; or +2 Coins; or trash 2 cards from your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(swindler = new CardImpl.Builder(Cards.Kind.Swindler, 3, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player trashes the top card of their deck and gains a card with the same cost that you choose.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(torturer = new CardImpl.Builder(Cards.Kind.Torturer, 5, CardType.Action, CardType.Attack).addCards(3).description("Each other player either discards 2 cards or gains a Curse to their hand, their choice. (They may pick an option they can't do.)").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(tradingPost = new CardImpl.Builder(Cards.Kind.TradingPost, 5, CardType.Action).trashForced().description("Trash 2 cards from your hand. If you did, gain a Silver to your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(upgrade = new CardImpl.Builder(Cards.Kind.Upgrade, 5, CardType.Action).trashForced().addCards(1).addActions(1).description("Trash a card from your hand. Gain a card costing exactly 1 Coin more than it.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(wishingWell = new CardImpl.Builder(Cards.Kind.WishingWell, 3, CardType.Action).addCards(1).addActions(1).description("Name a card, then reveal the top card of your deck. If you named it, put it into your hand.").expansion(Expansion.Intrigue).build());

        actionCardsIntrigue2E.add(courtier = new CardImpl.Builder(Cards.Kind.Courtier, 5, CardType.Action).description("Reveal a card from your hand.  For each type it has (Action, Attack, etc.), choose one: +1 Action, or +1 Buy, or +, or gain a Gold.  The choices must be different.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(patrol = new CardImpl.Builder(Cards.Kind.Patrol, 5, CardType.Action).addCards(3).description("Reveal the top 4 cards of your deck. Put the Victory cards and Curse cards into your hand. Put the rest back in any order.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(replace = new CardImpl.Builder(Cards.Kind.Replace, 5, CardType.Action, CardType.Attack).description("Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card. If the gained card is an Action or Treasure, put it onto your deck. If it's a Victory card, each other player gains a Curse.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(diplomat = new CardImpl.Builder(Cards.Kind.Diplomat, 4, CardType.Action, CardType.InHandReaction).addCards(2).description("If you have 5 or fewer cards in hand (after drawing), +2 Actions.  When another player plays an Attack card, you may first reveal this from a hand of 5 or more cards, to draw 2 cards then discard 3.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(mill = new CardImpl.Builder(Cards.Kind.Mill, 4, CardType.Action, CardType.Victory).addActions(1).addCards(1).vp(1).description("You may discard 2 cards for 2 Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(secretPassage = new CardImpl.Builder(Kind.SecretPassage, 4, CardType.Action).addCards(2).addActions(1).description("Take a card from your hand and put it anywhere in your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(lurker = new CardImpl.Builder(Cards.Kind.Lurker, 2, CardType.Action).addActions(1).description("Choose one: Trash an Action card from the Supply, or gain an Action card from the trash.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigueAll.addAll(actionCardsIntrigue2E);
        actionCardsIntrigue2E.addAll(actionCardsIntrigue);
        
        actionCardsIntrigue.add(coppersmith = new CardImpl.Builder(Cards.Kind.Coppersmith, 4, CardType.Action).description("Copper produces an extra 1 coin this turn.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(greatHall = new CardImpl.Builder(Cards.Kind.GreatHall, 3, CardType.Action, CardType.Victory).addCards(1).addActions(1).vp(1).expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(saboteur = new CardImpl.Builder(Cards.Kind.Saboteur, 5, CardType.Action, CardType.Attack).description("Each other player reveals cards from the top of his deck until revealing one costing 3 Coins or more. He trashes that card and may gain a card costing at most 2 Coins less than it. He discards the other revealed cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(scout = new CardImpl.Builder(Cards.Kind.Scout, 4, CardType.Action).addActions(1).description("Reveal the top 4 cards of your deck. Put the revealed Victory cards into your hand. Put the other cards on top of your deck in any order.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(secretChamber = new CardImpl.Builder(Cards.Kind.SecretChamber, 2, CardType.Action, CardType.InHandReaction).description("Discard any number of cards. +1 Coin per card discarded. When another player plays an Attack card, you may reveal this from your hand. If you do, +2 Cards, then put 2 cards from your hand on top of your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(tribute = new CardImpl.Builder(Cards.Kind.Tribute, 5, CardType.Action).description("The player to your left reveals then discards the top 2 cards of his deck. For each differently named card revealed, if it is an... Action Card, +2 Actions. Treasure Card, +2 Coins. Victory Card, +2 Cards.").expansion(Expansion.Intrigue).build());
        
        actionCardsIntrigueAll.addAll(actionCardsIntrigue);
        
        // Seaside
        actionCardsSeaside.add(ambassador = new CardImpl.Builder(Cards.Kind.Ambassador, 3, CardType.Action, CardType.Attack).trashForced().description("Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(bazaar = new CardImpl.Builder(Cards.Kind.Bazaar, 5, CardType.Action).addCards(1).addActions(2).addHeroism(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(caravan = new CardImpl.Builder(Cards.Kind.Caravan, 4, CardType.Action, CardType.Duration).addCardsNextTurn(1).addCards(1).addActions(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(cutpurse = new CardImpl.Builder(Cards.Kind.Cutpurse, 4, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player discards a Copper card (or reveals a hand with no Copper).").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(embargo = new CardImpl.Builder(Cards.Kind.Embargo, 2, CardType.Action).addHeroism(2).trashOnUse().description("Trash this card. Put an Embargo token on top of a Supply pile. When a player buys a card, he gains a Curse card per Embargo token on that pile.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(explorer = new CardImpl.Builder(Cards.Kind.Explorer, 5, CardType.Action).description("You may reveal a Province card from your hand. If you do, gain a Gold card, putting it into your hand. Otherwise, gain a Silver card, putting it into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(fishingVillage = new CardImpl.Builder(Cards.Kind.FishingVillage, 3, CardType.Action, CardType.Duration).addGoldNextTurn(1).addActionsNextTurn(1).addActions(2).addHeroism(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(ghostShip = new CardImpl.Builder(Cards.Kind.GhostShip, 5, CardType.Action, CardType.Attack).addCards(2).description("Each other player with 4 or more cards in hand puts cards from his hand on top of his deck until he has 3 cards in his hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(haven = new CardImpl.Builder(Cards.Kind.Haven, 2, CardType.Action, CardType.Duration).addCards(1).addActions(1).description("Set aside a card from your hand face down. At the start of your next turn, put it into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(island = new CardImpl.Builder(Cards.Kind.Island, 4, CardType.Action, CardType.Victory).vp(2).description("Set aside this and another card from your hand. Return them to your deck at the end of the game.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(lighthouse = new CardImpl.Builder(Cards.Kind.Lighthouse, 2, CardType.Action, CardType.Duration).addGoldNextTurn(1).addActions(1).addHeroism(1).description("While this is in play, when another player plays an Attack card, it doesn't affect you.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(lookout = new CardImpl.Builder(Cards.Kind.Lookout, 3, CardType.Action).addActions(1).description("Look at the top 3 cards of your deck. Trash one of them. Discard one of them. Put the other one on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(merchantShip = new CardImpl.Builder(Cards.Kind.MerchantShip, 5, CardType.Action, CardType.Duration).addGoldNextTurn(2).addHeroism(2).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(nativeVillage = new CardImpl.Builder(Cards.Kind.NativeVillage, 2, CardType.Action).addActions(2).description("Choose one: Set aside the top card of your deck face down on your Native Village mat; or put all the cards from your mat into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(navigator = new CardImpl.Builder(Cards.Kind.Navigator, 4, CardType.Action).addHeroism(2).description("Look at the top 5 cards of your deck. Either discard all of them, or put them back on top of your deck in any order.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(outpost = new CardImpl.Builder(Cards.Kind.Outpost, 5, CardType.Action, CardType.Duration).takeAnotherTurn(3).description("You only draw 3 cards (instead of 5) in this turn's Clean-up phase. Take an extra turn after this one. This can't cause you to take more than two consecutive turns.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(pearlDiver = new CardImpl.Builder(Cards.Kind.PearlDiver, 2, CardType.Action).addCards(1).addActions(1).description("Look at the bottom card of your deck. You may put it on top.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(pirateShip = new CardImpl.Builder(Cards.Kind.PirateShip, 4, CardType.Action, CardType.Attack).description("Choose one: Each other player reveals the top 2 cards of his deck, trashes a revealed Treasure that you choose, discards the rest, and if anyone trashed a Treasure you take a Coin token; or, +1 Coin per Coin token you've taken with Pirate Ships this game.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(salvager = new CardImpl.Builder(Cards.Kind.Salvager, 4, CardType.Action).trashForced().addBuys(1).description("Trash a card from your hand. + Coins equal to its cost.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(seaHag = new CardImpl.Builder(Cards.Kind.SeaHag, 4, CardType.Action, CardType.Attack).description("Each other player discards the top card of his deck, then gains a Curse card, putting it on top of his deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(smugglers = new CardImpl.Builder(Cards.Kind.Smugglers, 3, CardType.Action).description("Gain a copy of a card costing up to 6 Coins that the player to your right gained on his last turn.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(tactician = new CardImpl.Builder(Cards.Kind.Tactician, 5, CardType.Action, CardType.Duration).description("Discard your hand. If you discarded any cards this way, then at the start of your next turn, +5 Cards, +1 Buy, and +1 Action.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(treasureMap = new CardImpl.Builder(Cards.Kind.TreasureMap, 4, CardType.Action).description("Trash this and another copy of Treasure Map from your hand. If you do trash two Treasure Maps, gain 4 Gold cards putting them on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(treasury = new CardImpl.Builder(Cards.Kind.Treasury, 5, CardType.Action).addCards(1).addActions(1).addHeroism(1).description("When you discard this from play, if you didn't buy a Victory card this turn, you may put this on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(warehouse = new CardImpl.Builder(Cards.Kind.Warehouse, 3, CardType.Action).addCards(3).addActions(1).description("Discard 3 cards.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(wharf = new CardImpl.Builder(Cards.Kind.Wharf, 5, CardType.Action, CardType.Duration).addCardsNextTurn(2).addBuysNextTurn(1).addCards(2).addBuys(1).expansion(Expansion.Seaside).build());

        // Alchemy
        actionCardsAlchemy.add(alchemist = new CardImpl.Builder(Cards.Kind.Alchemist, 3, CardType.Action).addActions(1).addCards(2).costPotion().description("When you discard this from play, you may put this on top of your deck if you have a Potion in play.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(apothecary = new CardImpl.Builder(Cards.Kind.Apothecary, 2, CardType.Action).addActions(1).addCards(1).costPotion().description("Reveal the top 4 cards of your deck.  Put the revealed Coppers and Potions into your hand.  Put the other cards back on top of your deck in any order.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(apprentice = new CardImpl.Builder(Cards.Kind.Apprentice, 5, CardType.Action).trashForced().addActions(1).description("Trash a card from your hand.  +1 Card per Coin it costs.  +2 Cards if it has a Potion in its cost.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(familiar = new CardImpl.Builder(Cards.Kind.Familiar, 3, CardType.Action, CardType.Attack).addCards(1).addActions(1).costPotion().description("Each other player gains a Curse.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(golem = new CardImpl.Builder(Cards.Kind.Golem, 4, CardType.Action).costPotion().description("Reveal cards from your deck until you reveal 2 Action cards other than Golem cards.  Discard the other cards, then play the Action cards in either order.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(herbalist = new CardImpl.Builder(Cards.Kind.Herbalist, 2, CardType.Action).addBuys(1).addHeroism(1).description("When you discard this from play, you may put one of your Treasures from play on top of your deck.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(philosophersStone = new CardImpl.Builder(Cards.Kind.PhilosophersStone, 3, CardType.Treasure).costPotion().description("When you play this, count your deck and discard pile.  Worth (1) coin per 5 cards total between them (rounded down).").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(possession = new CardImpl.Builder(Cards.Kind.Possession, 6, CardType.Action).costPotion().description("The player to your left takes an extra turn after this one, in which you can see all cards he can and make all decisions for him. Any cards he would gain on that turn, you gain instead; any cards of his that are trashed are set aside and returned to his discard pile at end of turn.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(scryingPool = new CardImpl.Builder(Cards.Kind.ScryingPool, 2, CardType.Action, CardType.Attack).addActions(1).costPotion().description("Each player (including you) reveals the top card of his deck and either discards it or puts it back, your choice.  Then reveal cards from the top of your deck until you reveal one that is not an Action.  Put all of your revealed cards into your hand.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(transmute = new CardImpl.Builder(Cards.Kind.Transmute, 0, CardType.Action).trashForced().costPotion().description("Trash a card from your hand.  If it is an . . . Action card, gain a Duchy; Treasure card, gain a Transmute; Victory card gain a Gold.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(university = new CardImpl.Builder(Cards.Kind.University, 2, CardType.Action).addActions(2).costPotion().description("You may gain an Action card costing up to 5.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(vineyard = new CardImpl.Builder(Cards.Kind.Vineyard, 0, CardType.Victory).costPotion().description("Worth 1 Victory Point for every 3 Action cards in your deck (rounded down).").expansion(Expansion.Alchemy).build());

        // Prosperity
        actionCardsProsperity.add(bank = new CardImpl.Builder(Cards.Kind.Bank, 7, CardType.Treasure).description("When you play this, it's worth 1 coin per Treasure card you have in play (counting this).").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(bishop = new CardImpl.Builder(Cards.Kind.Bishop, 4, CardType.Action).trashForced().addHeroism(1).addVictoryTokens(1).description("Trash a card from your hand.  Gain Victory tokens equal to half its cost in coins, rounded down.  Each other player may trash a card from his hand.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(city = new CardImpl.Builder(Cards.Kind.City, 5, CardType.Action).addActions(2).addCards(1).description("If there are one or more empty Supply piles, +1 Card.  If there are two or more, +1 Coin and +1 Buy.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(contraband = new CardImpl.Builder(Cards.Kind.Contraband, 5, CardType.Treasure).addHeroism(3).addBuys(1).description("When you play this, the player to your left names a card.  You can't buy that card this turn.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(countingHouse = new CardImpl.Builder(Cards.Kind.CountingHouse, 5, CardType.Action).description("Look through your discard pile, reveal any number of Copper cards from it, and put them into your hand.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(expand = new CardImpl.Builder(Cards.Kind.Expand, 7, CardType.Action).trashForced().description("Trash a card from your hand.  Gain a card costing up to 3 coins more than the trashed card.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(forge = new CardImpl.Builder(Cards.Kind.Forge, 7, CardType.Action).trashForced().description("Trash any number of cards from your hand.  Gain a card with cost exactly equal to the total cost in coins of the trashed cards.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(goons = new CardImpl.Builder(Cards.Kind.Goons, 6, CardType.Action, CardType.Attack).addBuys(1).addHeroism(2).description("Eash other player discards down to 3 cards in hand.  While this is in play, when you buy a card, +1 Victory token.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(grandMarket = new CardImpl.Builder(Cards.Kind.GrandMarket, 6, CardType.Action).addCards(1).addActions(1).addBuys(1).addHeroism(2).description("You can't buy this if you have any Copper in play.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(hoard = new CardImpl.Builder(Cards.Kind.Hoard, 6, CardType.Treasure).addHeroism(2).description("While this is in play, when you buy a Victory card, gain a Gold.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(kingsCourt = new CardImpl.Builder(Cards.Kind.KingsCourt, 7, CardType.Action).description("You may choose an Action card in your hand.  Play it three times.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(loan = new CardImpl.Builder(Cards.Kind.Loan, 3, CardType.Treasure).addHeroism(1).description("When you play this, reveal cards from your deck until you reveal a Treasure.  Discard it or trash it.  Discard the other cards.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(mint = new CardImpl.Builder(Cards.Kind.Mint, 5, CardType.Action).description("You may reveal a Treasure card from your hand.  Gain a copy of it.  When you buy this, trash all Treasures you have in play.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(monument = new CardImpl.Builder(Cards.Kind.Monument, 4, CardType.Action).addHeroism(2).addVictoryTokens(1).expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(mountebank = new CardImpl.Builder(Cards.Kind.Mountebank, 5, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player may discard a Curse.  If he doesn't, he gains a Curse and a Copper.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(peddler = new CardImpl.Builder(Cards.Kind.Peddler, 8, CardType.Action).addActions(1).addCards(1).addHeroism(1).description("During your Buy phase, this costs 2 coins less per Action card you have in play, but not less than 0 coins.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(quarry = new CardImpl.Builder(Cards.Kind.Quarry, 4, CardType.Treasure).addHeroism(1).description("While this is in play, Action cards cost 2 coins less, but not less than 0 coins.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(rabble = new CardImpl.Builder(Cards.Kind.Rabble, 5, CardType.Action, CardType.Attack).addCards(3).description("Each other player reveals the top 3 cards of his deck, discards the revealed Actions and Treasures, and puts the rest back on top in any order he chooses.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(royalSeal = new CardImpl.Builder(Cards.Kind.RoyalSeal, 5, CardType.Treasure).addHeroism(2).description("While this is in play, when you gain a card, you may put that card on top of your deck.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(talisman = new CardImpl.Builder(Cards.Kind.Talisman, 4, CardType.Treasure).addHeroism(1).description("While this is in play, when you buy a card costing 4 coins or less that is not a Victory card, gain a copy of it.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(tradeRoute = new CardImpl.Builder(Cards.Kind.TradeRoute, 3, CardType.Action).trashForced().addBuys(1).description("+1 Coin per token on the Trade Route mat.  Trash a card from your hand.  Setup: Put a token on each Victory card Supply pile.  When a card is gained from that pile, move the token to the Trade Route mat.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(vault = new CardImpl.Builder(Cards.Kind.Vault, 5, CardType.Action).addCards(2).description("Discard any number of cards.  +1 coin per card discarded.  Each other player may discard 2 cards.  If he does, he draws a card.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(venture = new CardImpl.Builder(Cards.Kind.Venture, 5, CardType.Treasure).addHeroism(1).description("When you play this, reveal cards from your deck until you reveal a Treasure.  Discard the other cards.  Play that Treasure.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(watchTower = new CardImpl.Builder(Cards.Kind.WatchTower, 3, CardType.Action, CardType.InHandReaction).description("Draw until you have 6 cards in hand.  When you gain a card, you may reveal this from your hand.  If you do, either trash that card, or put it on top of your deck.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(workersVillage = new CardImpl.Builder(Cards.Kind.WorkersVillage, 4, CardType.Action).addCards(1).addActions(2).addBuys(1).expansion(Expansion.Prosperity).build());

        // Cornucopia
        actionCardsCornucopia.add(fairgrounds = new CardImpl.Builder(Cards.Kind.Fairgrounds, 6, CardType.Victory).description("Worth 2 points for every 5 differently named cards in your deck (round down).").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(farmingVillage = new CardImpl.Builder(Cards.Kind.FarmingVillage, 4, CardType.Action).addActions(2).description("Reveal cards from the top of your deck until you reveal an Action or Treasure card.  Put that card into your hand and discard the other cards.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(fortuneTeller = new CardImpl.Builder(Cards.Kind.FortuneTeller, 3, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player reveals cards from the top of his deck until he reveals a Victory or Curse card.  He puts it on top and discards the other revealed cards.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(hamlet = new CardImpl.Builder(Cards.Kind.Hamlet, 2, CardType.Action).addActions(1).addCards(1).description("You may discard a card; if you do +1 Action.  You may discard a card; if you do +1 Buy.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(harvest = new CardImpl.Builder(Cards.Kind.Harvest, 5, CardType.Action).description("Reveal the top 4 cards of your deck, then discard them.  +1 coin per differently named card revealed.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(hornOfPlenty = new CardImpl.Builder(Cards.Kind.HornofPlenty, 5, CardType.Treasure).description("When you play this, gain a card costing up to 1 coin per differently named card you have in play, counting this.  If it's a Victory card, trash this.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(horseTraders = new CardImpl.Builder(Cards.Kind.HorseTraders, 4, CardType.Action, CardType.InHandReaction).addBuys(1).addHeroism(3).description("Discard 2 cards.  When another player plays an Attack card, you may set this aside from your hand.  If you do, then at the start of your next turn, +1 Card and return this to your hand.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(huntingParty = new CardImpl.Builder(Cards.Kind.HuntingParty, 5, CardType.Action).addActions(1).addCards(1).description("Reveal your hand.  Reveal cards from your deck until you reveal a card that isn't a duplicate of one in your hand.  Put it into your hand and discard the rest.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(jester = new CardImpl.Builder(Cards.Kind.Jester, 5, CardType.Action, CardType.Attack).addHeroism(2).description("Each other player discards the top card of his deck.  If it's a Victory card, he gains a Curse.  Otherwise either he gains a copy of the discarded card or you do, your choice.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(menagerie = new CardImpl.Builder(Cards.Kind.Menagerie, 3, CardType.Action).addActions(1).description("Reveal your hand.  If there are no duplicate cards in it, +3 Cards.  Otherwise, +1 Card.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(remake = new CardImpl.Builder(Cards.Kind.Remake, 4, CardType.Action).trashForced().description("Do this twice.  Trash a card from your hand, then gain a card costing exactly 1 coin more than the trashed card.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(tournament = new CardImpl.Builder(Cards.Kind.Tournament, 4, CardType.Action).addActions(1).description("Each player may reveal a Province from his hand.  If you do, discard it and gain a Prize (from the Prize pile) or a Duchy, putting it on top of your deck.  If no one else does, +1 Card, +1 coin.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(youngWitch = new CardImpl.Builder(Cards.Kind.YoungWitch, 4, CardType.Action, CardType.Attack).addCards(2).description("Discard 2 cards.  Each other player may reveal a Bane card from his hand.  If he doesn't, he gains a Curse.  Setup:  Add an extra Kingdom card pile costing 2 or 3 coins to the Supply.  Cards from that pile are Bane cards.").expansion(Expansion.Cornucopia).build());

        // Prizes
        prizeCards.add(bagOfGold   = new CardImpl.Builder(Cards.Kind.BagofGold, 0, CardType.Action, CardType.Prize).addActions(1).description("Gain a Gold, putting it on top of your deck.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(diadem      = new CardImpl.Builder(Cards.Kind.Diadem, 0, CardType.Treasure, CardType.Prize).addHeroism(2).description("When you play this, +1 coin per unused Action you have (Action, not Action card).  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(followers   = new CardImpl.Builder(Cards.Kind.Followers, 0, CardType.Action, CardType.Attack, CardType.Prize).addCards(2).description("Gain an Estate.  Each other player gains a Curse and discards down to 3 cards in hand.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(princess    = new CardImpl.Builder(Cards.Kind.Princess, 0, CardType.Action, CardType.Prize).addBuys(1).description("While this is in play, cards cost 2 coins less, but not less than 0.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(trustySteed = new CardImpl.Builder(Cards.Kind.TrustySteed, 0, CardType.Action, CardType.Prize).description("Choose two:  +2 Cards; +2 Actions; +2 coins; gain 4 silvers and put your deck into your discard pile.  (The choices must be different.)  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());

        // Guilds
        actionCardsGuilds.add(advisor          = new CardImpl.Builder(Cards.Kind.Advisor, 4, CardType.Action).addActions(1).description("Reveal the top 3 cards of your deck. The player to your left chooses one of them. Discard that card. Put the other cards into your hand.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(soothsayer       = new CardImpl.Builder(Cards.Kind.Soothsayer, 5, CardType.Action, CardType.Attack).description("Gain a Gold. Each other player gains a Curse. Each player who did draws a card.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(taxman           = new CardImpl.Builder(Cards.Kind.Taxman, 4, CardType.Action, CardType.Attack).description("You may trash a Treasure from your hand. Each other player with 5 or more cards in hand discards a copy of it (or reveals a hand without it). Gain a Treasure card costing up to $3 more than the trashed card, putting it on top of your deck.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(plaza            = new CardImpl.Builder(Cards.Kind.Plaza, 4, CardType.Action).addCards(1).addActions(2).description("You may discard a Treasure card. If you do, take a Coin token.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(candlestickMaker = new CardImpl.Builder(Cards.Kind.CandlestickMaker, 2, CardType.Action).addActions(1).addBuys(1).description("Take a Coin token.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(baker            = new CardImpl.Builder(Cards.Kind.Baker, 5, CardType.Action).addCards(1).addActions(1).description("Take a Coin token. SETUP: Each Player takes a Coin token.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(merchantGuild    = new CardImpl.Builder(Cards.Kind.MerchantGuild, 5, CardType.Action).addBuys(1).addHeroism(1).description("While this is in play, when you buy a card, take a Coin token.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(butcher          = new CardImpl.Builder(Cards.Kind.Butcher, 5, CardType.Action).description("Take 2 Coin tokens. You may trash a card from your hand and then pay any number of Coin tokens. If you did trash a card, gain a card with a cost of up to the cost of the trashed card plus the number of Coin tokens you paid.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(journeyman       = new CardImpl.Builder(Cards.Kind.Journeyman, 5, CardType.Action).description("Name a card. Reveal cards from the top of your deck until you reveal 3 cards that are not the named card. Put those cards into your hand and discard the rest.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(stonemason       = new CardImpl.Builder(Cards.Kind.StoneMason, 2, CardType.Action).trashForced().isOverpay().description("Trash a card from your hand, Gain 2 cards each costing less than it. When you buy this, you may overpay for it. If you do, gain 2 Action cards each costing the amount you overpaid.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(masterpiece      = new CardImpl.Builder(Cards.Kind.Masterpiece, 3, CardType.Treasure).addHeroism(1).isOverpay().description("When you buy this, you may overpay for it. If you do, gain a Silver per $1 you overpaid.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(doctor           = new CardImpl.Builder(Cards.Kind.Doctor, 3, CardType.Action).isOverpay().description("Name a card. Reveal the top 3 cards of your deck. Trash the matches. Put the rest back on top in any order.  When you buy this, you may overpay for it. For each $1 you overpaid, look at the top card of your deck; trash it, discard it, or put it back.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(herald           = new CardImpl.Builder(Cards.Kind.Herald, 4, CardType.Action).addCards(1).addActions(1).isOverpay().description("Reveal the top card of your deck. If it is an Action, play it. When you buy this, you may overpay for it. For each $1 you overpaid, look through your discard pile and put a card from it on top of your deck.").expansion(Expansion.Guilds).build());

        // Adventures
        actionCardsAdventures.add(amulet          = new CardImpl.Builder(Cards.Kind.Amulet, 3, CardType.Action, CardType.Duration).description("Now and at the start of your next turn, choose one: +1 Coin; or trash a card from your hand; or gain a Silver.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(artificer       = new CardImpl.Builder(Cards.Kind.Artificer, 5, CardType.Action).addCards(1).addActions(1).addHeroism(1).description("Discard any number of cards. You may gain a card costing exactly 1 Coin per card discarded, putting it on top of your deck.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(bridgeTroll     = new CardImpl.Builder(Cards.Kind.BridgeTroll, 5, CardType.Action, CardType.Attack, CardType.Duration).addBuysNextTurn(1).addBuys(1).description("Each other player takes his -1 Coin token. ~ While this is in play, cards cost 1 Coin less on your turns, but not less than 0 Coins.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(caravanGuard    = new CardImpl.Builder(Cards.Kind.CaravanGuard, 3, CardType.Action, CardType.Duration, CardType.InHandReaction).addGoldNextTurn(1).addCards(1).addActions(1).description("When another player plays an Attack card, you may play this from your hand. (+1 Action has no effect if it's not your turn.)").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(coinOfTheRealm  = new CardImpl.Builder(Cards.Kind.CoinOfTheRealm, 2, CardType.Treasure, CardType.Remains).addHeroism(1).callWhenActionResolved().description("When you play this, put it on your Tavern mat. ~ Directly after resolving an Action, you may call this, for +2 Actions.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(distantLands    = new CardImpl.Builder(Cards.Kind.DistantLands, 5, CardType.Action, CardType.Remains, CardType.Victory).description("Put this on your Tavern mat. ~ Worth 4 VP if on your Tavern mat at the end of the game (otherwise worth 0 VP).").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(dungeon         = new CardImpl.Builder(Cards.Kind.Dungeon, 3, CardType.Action, CardType.Duration).addCards(2).addActions(1).description("Discard 2 cards. At the start of your next turn: +2 Cards, then discard 2 cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(duplicate       = new CardImpl.Builder(Cards.Kind.Duplicate, 4, CardType.Action, CardType.Remains).callWhenGainCard(6).description("Put this on your Tavern mat. ~ When you gain a card costing up to 6 Coins, you may call this, to gain a copy of that card.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(gear            = new CardImpl.Builder(Cards.Kind.Gear, 3, CardType.Action, CardType.Duration).addCards(2).description("Set aside up to 2 cards from your hand face down. At the start of your next turn, put them into your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(giant           = new CardImpl.Builder(Cards.Kind.Giant, 5, CardType.Action, CardType.Attack).description("Turn your Journey token over (it starts face up). If it's face down, +1 Coin. If it's face up, +5 Coins, and each other player reveals the top card of his deck, trashes it if it costs from 3 to 6 Coins, and otherwise discards it and gains a Curse.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(guide           = new CardImpl.Builder(Cards.Kind.Guide, 3, CardType.Action, CardType.Remains).callWhenTurnStarts().addCards(1).addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to discard your hand and draw 5 cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(hauntedWoods    = new CardImpl.Builder(Cards.Kind.HauntedWoods, 5, CardType.Action, CardType.Attack, CardType.Duration).addCardsNextTurn(3).description("Until your next turn, when any other player buys a card, he puts his hand on top of his deck in any order.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(hireling        = new CardImpl.Builder(Cards.Kind.Hireling, 6, CardType.Action, CardType.Duration).description("At the start of each of your turns for the rest of the game: +1 Card. (This stays in play.)").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(lostCity        = new CardImpl.Builder(Cards.Kind.LostCity, 5, CardType.Action).addCards(2).addActions(2).description("When you gain this, each other player draws a card.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(magpie          = new CardImpl.Builder(Cards.Kind.Magpie, 4, CardType.Action).addCards(1).addActions(1).description("Reveal the top card of your deck. If it's a Treasure, put it into your hand. If it's an Action or Victory card, gain a Magpie.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(messenger       = new CardImpl.Builder(Cards.Kind.Messenger, 4, CardType.Action).addHeroism(2).addBuys(1).description("You may put your deck into your discard pile. ~ When this is your first buy in a turn, gain a card costing up to 4 Coins, and each other player gains a copy of it.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(miser           = new CardImpl.Builder(Cards.Kind.Miser, 4, CardType.Action).description("Choose one: Put a Copper from your hand onto your Tavern mat; or +1 Coin per Copper on your Tavern mat.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(page            = new CardImpl.Builder(Cards.Kind.Page, 2, CardType.Action, CardType.Crown).addCards(1).addActions(1).description("When you discard this from play, you may exchange it for a Treasure Hunter.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(peasant         = new CardImpl.Builder(Cards.Kind.Peasant, 2, CardType.Action, CardType.Crown).addHeroism(1).addBuys(1).description("When you discard this from play, you may exchange it for a Soldier.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(port            = new CardImpl.Builder(Cards.Kind.Port, 4, CardType.Action).addCards(1).addActions(2).description("When you buy this, gain another Port.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(ranger          = new CardImpl.Builder(Cards.Kind.Ranger, 4, CardType.Action).addBuys(1).description("Turn your Journey token over (it starts face up). If it's face up, +5 Cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(ratcatcher      = new CardImpl.Builder(Cards.Kind.Ratcatcher, 2, CardType.Action, CardType.Remains).callWhenTurnStarts().addCards(1).addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to trash a card from your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(raze            = new CardImpl.Builder(Cards.Kind.Raze, 2, CardType.Action).addActions(1).description("Trash this or a card from your hand. Look at a number of cards from the top of your deck equal to the cost in Coins of the trashed card. Put one into your hand and discard the rest.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(relic           = new CardImpl.Builder(Cards.Kind.Relic, 5, CardType.Treasure, CardType.Attack).addHeroism(2).description("When you play this, each other player puts his -1 Card token on his deck.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(royalCarriage   = new CardImpl.Builder(Cards.Kind.RoyalCarriage, 5, CardType.Action, CardType.Remains).callWhenActionResolved(true).addActions(1).description("Put this on your Tavern mat. ~ Directly after resolving an Action, if it's still in play, you may call this, to replay that Action.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(storyteller     = new CardImpl.Builder(Cards.Kind.Storyteller, 5, CardType.Action).addHeroism(1).addActions(1).description("Play up to 3 Treasures from your hand. Pay all of your Coins; +1 Card per Coin paid.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(swampHag        = new CardImpl.Builder(Cards.Kind.SwampHag, 5, CardType.Action, CardType.Attack, CardType.Duration).addGoldNextTurn(3).description("Until your next turn, when any other player buys a card, he gains a Curse.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(transmogrify    = new CardImpl.Builder(Cards.Kind.Transmogrify, 4, CardType.Action, CardType.Remains).callWhenTurnStarts().addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to trash a card from your hand, gain a card costing up to 1 Coin more than it, and put that card into your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(treasureTrove   = new CardImpl.Builder(Cards.Kind.TreasureTrove, 5, CardType.Treasure).addHeroism(2).description("When you play this, gain a Gold and a Copper.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(wineMerchant    = new CardImpl.Builder(Cards.Kind.WineMerchant, 5, CardType.Action, CardType.Remains).addHeroism(4).addBuys(1).description("Put this on your Tavern mat. ~ At the end of your Buy phase, if you have at least 2 Coins unspent, you may discard this from your Tavern mat.").expansion(Expansion.Adventures).build());
        
        // events
        eventCardsAdventures.add(alms              = new CardImpl.Builder(Cards.Kind.Alms            , 0, CardType.Event).description("Once per turn: If you have no Treasures in play, gain a card costing up to 4 Coins.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(ball              = new CardImpl.Builder(Cards.Kind.Ball            , 5, CardType.Event).description("Take your -1 Coin token. Gain 2 cards each costing up to 4 Coins.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(bonfire           = new CardImpl.Builder(Cards.Kind.Bonfire         , 3, CardType.Event).description("Trash up to 2 cards you have in play.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(borrow            = new CardImpl.Builder(Cards.Kind.Borrow          , 0, CardType.Event).addBuys(1).description("Once per turn: If your -1 Card token isn't on your deck, put it there and +1 Coin.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(expedition        = new CardImpl.Builder(Cards.Kind.Expedition      , 3, CardType.Event).description("Draw 2 extra cards for your next hand.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(ferry             = new CardImpl.Builder(Cards.Kind.Ferry           , 3, CardType.Event).description("Move your -2 cost token to an Action Supply pile (cards from that pile cost 2 Coins less on your turns, but not less than 0 Coins).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(inheritance       = new CardImpl.Builder(Cards.Kind.Inheritance     , 7, CardType.Event).description("Once per game: Set aside a non-Victory Action card from the Supply costing up to 4 Coins. Move your Estate token to it (your Estates gain the abilities and types of that card).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(lostArts          = new CardImpl.Builder(Cards.Kind.LostArts        , 6, CardType.Event).description("Move your +1 Action token to an Action Supply pile (when you play a card from that pile, you first get +1 Action).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(mission           = new CardImpl.Builder(Cards.Kind.Mission         , 4, CardType.Event).description("Once per turn: If the previous turn wasn't yours, take another turn after this one, in which you can't buy cards.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(pathfinding       = new CardImpl.Builder(Cards.Kind.Pathfinding     , 8, CardType.Event).description("Move your +1 Card token to an Action Supply pile (when you play a card from that pile, you first get +1 Card).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(pilgrimage        = new CardImpl.Builder(Cards.Kind.Pilgrimage      , 4, CardType.Event).description("Once per turn: Turn your Journey token over (it starts face up); then if it's face up, choose up to 3 differently named cards you have in play and gain a copy of each.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(plan              = new CardImpl.Builder(Cards.Kind.Plan            , 3, CardType.Event).description("Move your Trashing token to an Action Supply pile (when you buy a card from that pile, you may trash a card from your hand.)").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(quest             = new CardImpl.Builder(Cards.Kind.Quest           , 0, CardType.Event).description("You may discard an Attack, two Curses, or six cards. If you do, gain a Gold.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(raid              = new CardImpl.Builder(Cards.Kind.Raid            , 5, CardType.Event).description("Gain a Silver per Silver you have in play. Each other player puts his -1 Card token on his deck.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(save              = new CardImpl.Builder(Cards.Kind.Save            , 1, CardType.Event).addBuys(1).description("Once per turn: Set aside a card from your hand, and put it into your hand at end of turn (after drawing).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(scoutingParty     = new CardImpl.Builder(Cards.Kind.ScoutingParty   , 2, CardType.Event).addBuys(1).description("Look at the top 5 cards of your deck. Discard 3 and put the rest back in any order.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(seaway            = new CardImpl.Builder(Cards.Kind.Seaway          , 5, CardType.Event).description("Gain an Action card costing up to 4 Coins. Move your +1 Buy token to its pile (when you play a card from that pile, you first get +1 Buy).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(trade             = new CardImpl.Builder(Cards.Kind.Trade           , 5, CardType.Event).description("Trash up to 2 cards from your hand. Gain a Silver per card you trashed.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(training          = new CardImpl.Builder(Cards.Kind.Training        , 6, CardType.Event).description("Move your +1 Coin token to an Action Supply pile (when you play a card from that pile, you first get +1 Coin).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(travellingFair    = new CardImpl.Builder(Cards.Kind.TravellingFair  , 2, CardType.Event).addBuys(2).description("When you gain a card this turn, you may put it on top of your deck.").expansion(Expansion.Adventures).build());
        
        // Travellers
        nonSupplyCards.add(champion        = new CardImpl.Builder(Cards.Kind.Champion, 6, CardType.Action, CardType.Duration).addActions(1).description("For the rest of the game, when another player plays an Attack, it doesn't affect you, and when you play an Action, +1 Action. (This stays in play. This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(disciple        = new CardImpl.Builder(Cards.Kind.Disciple        , 5, CardType.Action, CardType.Crown).description("You may play an Action card from your hand twice. Gain a copy of it. ~ When you discard this from play, you may exchange it for a Teacher. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(fugitive        = new CardImpl.Builder(Cards.Kind.Fugitive        , 4, CardType.Action, CardType.Crown).addCards(2).addActions(1).description("Discard a card. ~ When you discard this from play, you may exchange it for a Disciple. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(hero            = new CardImpl.Builder(Cards.Kind.Hero            , 5, CardType.Action, CardType.Crown).addHeroism(2).description("Gain a Treasure - When you discard this from play, you may exchange it for a Champion. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(soldier         = new CardImpl.Builder(Cards.Kind.Soldier         , 3, CardType.Action, CardType.Attack, CardType.Crown).addHeroism(2).description("+1 Coin per other Attack you have in play. Each other player with 4 or more cards in hand discards a card. ~ When you discard this from play, you may exchange it for a Fugitive. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(teacher         = new CardImpl.Builder(Cards.Kind.Teacher , 6, CardType.Action, CardType.Remains).callWhenTurnStarts().description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to move your +1 Card, +1 Action, +1 Buy, or +1 Coin token to an Action Supply pile you have no tokens on (when you play a card from that pile, you first get that bonus). (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(treasureHunter  = new CardImpl.Builder(Cards.Kind.TreasureHunter  , 3, CardType.Action, CardType.Crown).addHeroism(1).addActions(1).description("Gain a Silver per card the player to your right gained in his last turn. ~ When you discard this from play, you may exchange it for a Warrior. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(warrior         = new CardImpl.Builder(Cards.Kind.Warrior         , 4, CardType.Action, CardType.Attack, CardType.Crown).addCards(2).description("For each Crown you have in play (including this), each other player discards the top card of his deck and trashes it if it costs 3 Coins or 4 Coins. ~ When you discard this from play, you may exchange it for a Hero. (This is not in the Supply.)").expansion(Expansion.Adventures).build());

        // Empires

        // split pile cards
        variablePileCards.add(catapult            = new CardImpl.Builder(Cards.Kind.Catapult, 3, CardType.Action, CardType.Attack).addHeroism(1).trashForced().description("Trash a card from your hand. If it costs (3) Coins or more, each other player gains a Curse. If it's a Treasure, each other player discards down to 3 cards in hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(rocks               = new CardImpl.Builder(Cards.Kind.Rocks, 4, CardType.Treasure).addHeroism(1).description("When you gain or trash this, gain a Silver; if it is your Buy phase, put the Silver on your deck, otherwise put it into your hand.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(encampment          = new CardImpl.Builder(Cards.Kind.Encampment, 2, CardType.Action).addActions(2).addCards(2).description("You may reveal a Gold or Plunder from your hand. If you do not, set this aside, and return it to the Supply at the start of Clean-up.").expansion(Expansion.Empires).build());
        variablePileCards.add(plunder             = new CardImpl.Builder(Cards.Kind.Plunder, 5, CardType.Treasure).addHeroism(2).addVictoryTokens(1).description("").expansion(Expansion.Empires).build());
        
		variablePileCards.add(gladiator           = new CardImpl.Builder(Cards.Kind.Gladiator, 3, CardType.Action).addHeroism(2).description("Reveal a card from your hand. The player to your left may reveal a copy from their hand. If they do not, +(1) Coin and trash a Gladiator from the Supply.").expansion(Expansion.Empires).build());
        variablePileCards.add(fortune             = new CardImpl.Builder(Cards.Kind.Fortune, 8, CardType.Treasure).addBuys(1).costDebt(8).description("When you play this, double your Coins if you haven't yet this turn. - When you gain this, gain a Gold per Gladiator you have in play.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(patrician           = new CardImpl.Builder(Cards.Kind.Patrician, 2, CardType.Action).addActions(1).addCards(1).description("Reveal the top card of your deck. If it costs (5) Coins or more, put it into your hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(emporium            = new CardImpl.Builder(Cards.Kind.Emporium, 5, CardType.Action).addActions(1).addCards(1).addHeroism(1).description("When you gain this, if you have at least 5 Action cards in play, +2 Victory tokens.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(settlers            = new CardImpl.Builder(Cards.Kind.Settlers, 2, CardType.Action).addCards(1).addActions(1).description("Look through your discard pile. You may reveal a Copper from it and put it into your hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(bustlingVillage     = new CardImpl.Builder(Cards.Kind.BustlingVillage, 5, CardType.Action).addCards(1).addActions(3).description("Look through your discard pile. You may reveal a Settlers from it and put it into your hand.").expansion(Expansion.Empires).build());
		
        // castles
        castleCards.add(humbleCastle        = new CardImpl.Builder(Cards.Kind.HumbleCastle, 3, CardType.Treasure, CardType.Victory, CardType.Castle).addHeroism(1).description("Worth 1 VP per Castle you have.").expansion(Expansion.Empires).build());
        castleCards.add(crumblingCastle     = new CardImpl.Builder(Cards.Kind.CrumblingCastle, 4, CardType.Victory, CardType.Castle).vp(1).description("When you gain or trash this, +1 Victory token and gain a Silver.").expansion(Expansion.Empires).build());
        castleCards.add(smallCastle         = new CardImpl.Builder(Cards.Kind.SmallCastle, 5, CardType.Action, CardType.Victory, CardType.Castle).vp(2).trashForced().description("Trash this or a Castle from your hand. If you do, gain a Castle.").expansion(Expansion.Empires).build());
        castleCards.add(hauntedCastle       = new CardImpl.Builder(Cards.Kind.HauntedCastle, 6, CardType.Victory, CardType.Castle).vp(2).description("When you gain this during your turn, gain a Gold, and each other player with 5 or more cards in hand puts 2 cards from their hand onto their deck.").expansion(Expansion.Empires).build());
        castleCards.add(opulentCastle       = new CardImpl.Builder(Cards.Kind.OpulentCastle, 7, CardType.Action, CardType.Victory, CardType.Castle).vp(3).description("Discard any number of Victory cards. +(2) Coins per card discarded.").expansion(Expansion.Empires).build());
        castleCards.add(sprawlingCastle     = new CardImpl.Builder(Cards.Kind.SprawlingCastle, 8, CardType.Victory, CardType.Castle).vp(4).description("When you gain this, gain a Duchy or 3 Estates.").expansion(Expansion.Empires).build());
        castleCards.add(grandCastle         = new CardImpl.Builder(Cards.Kind.GrandCastle, 9, CardType.Victory, CardType.Castle).vp(5).description("When you gain this, reveal your hand. +1 Victory token per Victory card in your hand and/or in play.").expansion(Expansion.Empires).build());
        castleCards.add(kingsCastle         = new CardImpl.Builder(Cards.Kind.KingsCastle, 10, CardType.Victory, CardType.Castle).description("Worth 2 VP per Castle you have.").expansion(Expansion.Empires).build());

        actionCardsEmpires.add(archive                        = new CardImpl.Builder(Cards.Kind.Archive, 5, CardType.Action, CardType.Duration).addActions(1).description("Set aside the top 3 cards of your deck face down (you may look at them). Now and at the start of your next two turns, put one into your hand.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(capital                        = new CardImpl.Builder(Cards.Kind.Capital, 5, CardType.Treasure).addBuys(1).addHeroism(6).description("When you discard this from play, take 6 Debt tokens, and then you may pay off Debt tokens.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualCastle                  = new CardImpl.Builder(Cards.Kind.Castles, 3, CardType.Victory, CardType.Castle).pileCreator(new CastlesPileCreator()).description("Sort the Castle pile by cost, putting the more expensive Castles on the bottom. For a 2-player game, use only one of each Castle. Only the top card of the pile can be gained or bought.").build());
        actionCardsEmpires.add(virtualCatapultRocks           = new CardImpl.Builder(Cards.Kind.CatapultRocks, 3, CardType.Action, CardType.Attack).pileCreator(new SplitPileCreator(catapult, rocks)).description("This pile starts the game with 5 copies of Catapult on top, then 5 copies of Rocks. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(chariotRace                    = new CardImpl.Builder(Cards.Kind.ChariotRace, 3, CardType.Action).addActions(1).description("Reveal the top card of your deck and put it into your hand. The player to your left reveals the top card of their deck. If your card costs more, +(1) Coin and +1 Victory token.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(charm                          = new CardImpl.Builder(Cards.Kind.Charm, 5, CardType.Treasure).description("When you play this, choose one: +1 Buy and +(2) Coins; or the next time you buy a card this turn, you may also gain a differently named card with the same cost.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(cityQuarter                    = new CardImpl.Builder(Cards.Kind.CityQuarter, 0, CardType.Action).addActions(2).costDebt(8).description("Reveal your hand. +1 Card per Action card revealed.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(crown                          = new CardImpl.Builder(Cards.Kind.Crown, 5, CardType.Action, CardType.Treasure).description("If it's your Action phase, you may play an Action from your hand twice. If it's your Buy phase, you may play a Treasure from your hand twice.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualEncampmentPlunder       = new CardImpl.Builder(Cards.Kind.EncampmentPlunder, 2, CardType.Action).pileCreator(new SplitPileCreator(encampment, plunder)).description("This pile starts the game with 5 copies of Encampment on top, then 5 copies of Plunder. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(enchantress                    = new CardImpl.Builder(Cards.Kind.Enchantress, 3, CardType.Action, CardType.Attack, CardType.Duration).addCardsNextTurn(2).description("Until your next turn, the first time each other player plays an Action card on their turn, they get +1 Card and +1 Action instead of following its instructions.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(engineer                       = new CardImpl.Builder(Cards.Kind.Engineer, 0, CardType.Action).costDebt(4).description("Gain a card costing up to (4) Coins. You may trash this. If you do, gain a card costing up to (4) Coins.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(farmersMarket                  = new CardImpl.Builder(Cards.Kind.FarmersMarket, 3, CardType.Action, CardType.Gathering).addBuys(1).description("If there are 4 Victory tokens or more on the Farmers' Market Supply pile, take them and trash this. Otherwise, add 1 Victory token to the pile and then +(1) Coin per 1 Victory token on the pile.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(forum                          = new CardImpl.Builder(Cards.Kind.Forum, 5, CardType.Action).addActions(1).addCards(3).description("Discard 2 cards. ~ When you buy this, +1 Buy.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualGladiatorFortune        = new CardImpl.Builder(Cards.Kind.GladiatorFortune, 3, CardType.Action).pileCreator(new SplitPileCreator(gladiator, fortune)).description("This pile starts the game with 5 copies of Gladiator on top, then 5 copies of Fortune. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(groundskeeper                  = new CardImpl.Builder(Cards.Kind.Groundskeeper, 5, CardType.Action).addCards(1).addActions(1).description("While this is in play, when you gain a Victory card, +1 Victory token.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(legionary                      = new CardImpl.Builder(Cards.Kind.Legionary, 5, CardType.Action, CardType.Attack).addHeroism(3).description("You may reveal a Gold from your hand. If you do, each other player discards down to 2 cards in hand, then draws a card.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(overlord                       = new CardImpl.Builder(Cards.Kind.Overlord, 0, CardType.Action).costDebt(8).description("Play this as if it were an Action card in the Supply costing up to (5) Coins. This is that card until it leaves play.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualPatricianEmporium       = new CardImpl.Builder(Cards.Kind.PatricianEmporium, 2, CardType.Action).pileCreator(new SplitPileCreator(patrician, emporium)).description("This pile starts the game with 5 copies of Patrician on top, then 5 copies of Emporium. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(royalBlacksmith                = new CardImpl.Builder(Cards.Kind.RoyalBlacksmith, 0, CardType.Action).addCards(5).costDebt(8).description("Reveal your hand; discard the Coppers.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(sacrifice                      = new CardImpl.Builder(Cards.Kind.Sacrifice, 4, CardType.Action).trashForced().description("Trash a card from your hand. If it's an... Action card, +2 Cards, +2 Actions; Treasure card, +(2) Coins; Victory card, +2 Victory tokens").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualSettlersBustlingVillage = new CardImpl.Builder(Cards.Kind.SettlersBustlingVillage, 2, CardType.Action).pileCreator(new SplitPileCreator(settlers, bustlingVillage)).description("This pile starts the game with 5 copies of Settlers on top, then 5 copies of Bustling Village. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(temple                         = new CardImpl.Builder(Cards.Kind.Temple, 4, CardType.Action, CardType.Gathering).addVictoryTokens(1).trashForced().description("Trash from 1 to 3 differently named cards from your hand. Add 1 Victory token to the Temple Supply pile. - When you gain this, take the Victory tokens from the Temple Supply pile.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(villa                          = new CardImpl.Builder(Cards.Kind.Villa, 4, CardType.Action).addActions(2).addBuys(1).addHeroism(1).description("When you gain this, put it into your hand, +1 Action, and if it's your Buy phase return to your Action phase.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(wildHunt                       = new CardImpl.Builder(Cards.Kind.WildHunt, 5, CardType.Action, CardType.Gathering).description("Choose one: +3 Cards and add 1 Victory token to the Wild Hunt Supply pile; or gain an Estate, and if you do, take the Victory tokens from the pile.").expansion(Expansion.Empires).build());

        // events
        eventCardsEmpires.add(advance          = new CardImpl.Builder(Cards.Kind.Advance, 0, CardType.Event).description("You may trash an Action card from your hand. If you do, gain an Action card costing up to (6) Coins.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(annex            = new CardImpl.Builder(Cards.Kind.Annex, 0, CardType.Event).costDebt(8).description("Look through your discard pile. Shuffle all but up to 5 cards from it into your deck. Gain a Duchy.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(banquet          = new CardImpl.Builder(Cards.Kind.Banquet, 3, CardType.Event).description("Gain 2 Coppers and a non-Victory card costing up to (5) Coins.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(conquest         = new CardImpl.Builder(Cards.Kind.Conquest, 6, CardType.Event).description("Gain 2 Silvers. +1 Victory token per Silver you've gained this turn.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(delve            = new CardImpl.Builder(Cards.Kind.Delve, 2, CardType.Event).addBuys(1).description("Gain a Silver.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(dominate         = new CardImpl.Builder(Cards.Kind.Dominate, 14, CardType.Event).description("Gain a Province. If you do, +9 Victory tokens.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(donate           = new CardImpl.Builder(Cards.Kind.Donate, 0, CardType.Event).costDebt(8).description("After this turn, put all cards from your deck and discard pile into your hand, trash any number, shuffle your hand into your deck, then draw 5 cards.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(ritual           = new CardImpl.Builder(Cards.Kind.Ritual, 4, CardType.Event).trashForced().description("Gain a Curse. If you do, trash a card from your hand. +1VP per (1) Coin it cost.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(saltTheEarth     = new CardImpl.Builder(Cards.Kind.SaltTheEarth, 4, CardType.Event).addVictoryTokens(1).description("Trash a Victory card from the Supply.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(tax              = new CardImpl.Builder(Cards.Kind.Tax, 2, CardType.Event).description("Add 2 Debt Tokens to a Supply pile. ~ Setup: Add 1 Debt Token to each Supply pile. When a player buys a card, they take the Debt Tokens from its pile.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(triumph          = new CardImpl.Builder(Cards.Kind.Triumph, 0, CardType.Event).costDebt(5).description("Gain an Estate. If you did, +1 Victory token per card you've gained this turn.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(wedding          = new CardImpl.Builder(Cards.Kind.Wedding, 4, CardType.Event).costDebt(3).addVictoryTokens(1).description("Gain a Gold.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(windfall         = new CardImpl.Builder(Cards.Kind.Windfall, 5, CardType.Event).description("If your deck and discard pile are empty, gain 3 Golds.").expansion(Expansion.Empires).build());
        
        // landmarks
        landmarkCardsEmpires.add(aqueduct      = new CardImpl.Builder(Cards.Kind.Aqueduct, CardType.Landmark).description("When you gain a Treasure, move 1 Victory token from its pile to this. When you gain a Victory card, take the Victory tokens from this. ~ Setup: Put 8 Victory tokens on the Silver and Gold piles.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(arena         = new CardImpl.Builder(Cards.Kind.Arena, CardType.Landmark).description("At the start of your Buy phase, you may discard an Action card. If you do, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(banditFort    = new CardImpl.Builder(Cards.Kind.BanditFort, CardType.Landmark).description("When scoring, -2 VP for each Silver and each Gold you have.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(basilica      = new CardImpl.Builder(Cards.Kind.Basilica, CardType.Landmark).description("When you buy a card, if you have (2) Coins or more left, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(baths         = new CardImpl.Builder(Cards.Kind.Baths, CardType.Landmark).description("When you end your turn without having gained a card, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(battlefield   = new CardImpl.Builder(Cards.Kind.Battlefield, CardType.Landmark).description("When you gain a Victory card, take 2 Victory tokens from here. — Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(colonnade     = new CardImpl.Builder(Cards.Kind.Colonnade, CardType.Landmark).description("When you buy an Action card, if you have a copy of it in play, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(defiledShrine = new CardImpl.Builder(Cards.Kind.DefiledShrine, CardType.Landmark).description("When you gain an Action, move 1 Victory token from its pile to this. When you buy a Curse, take the Victory tokens from this. ~ Setup: Put 2 Victory tokens on each non-Gathering Action Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(fountain      = new CardImpl.Builder(Cards.Kind.Fountain, CardType.Landmark).description("When scoring, 15 VP if you have at least 10 Coppers.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(keep          = new CardImpl.Builder(Cards.Kind.Keep, CardType.Landmark).description("When scoring, 5 VP per differently named Treasure you have, that you have more copies of than each other player, or tied for most.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(labyrinth     = new CardImpl.Builder(Cards.Kind.Labyrinth, CardType.Landmark).description("When you gain a 2nd card in one of your turns, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(mountainPass  = new CardImpl.Builder(Cards.Kind.MountainPass, CardType.Landmark).description("When you are the first player to gain a Province, after that turn, each player bids once, up to 40 Debt tokens, ending with you. High bidder gets +8 Victory tokens and takes the Debt tokens they bid.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(museum        = new CardImpl.Builder(Cards.Kind.Museum, CardType.Landmark).description("When scoring, 2 VP per differently named card you have.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(obelisk       = new CardImpl.Builder(Cards.Kind.Obelisk, CardType.Landmark).description("When scoring, 2 VP per card you have from the chosen pile. ~ Setup: Choose a random Action Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(orchard       = new CardImpl.Builder(Cards.Kind.Orchard, CardType.Landmark).description("When scoring, 4 VP per differently named Action card you have 3 or more copies of.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(palace        = new CardImpl.Builder(Cards.Kind.Palace, CardType.Landmark).description("When scoring, 3 VP per set you have of Copper - Silver - Gold.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(tomb          = new CardImpl.Builder(Cards.Kind.Tomb, CardType.Landmark).description("When you trash a card, +1VP.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(tower         = new CardImpl.Builder(Cards.Kind.Tower, CardType.Landmark).description("When scoring, 1 VP per non-Victory card you have from an empty Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(triumphalArch = new CardImpl.Builder(Cards.Kind.TriumphalArch, CardType.Landmark).description("When scoring, 3 VP per copy you have of the 2nd most common Action card among your cards (if it’s a tie, count either).").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(wall          = new CardImpl.Builder(Cards.Kind.Wall, CardType.Landmark).description("When scoring, -1VP per card you have after the first 15.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(wolfDen       = new CardImpl.Builder(Cards.Kind.WolfDen, CardType.Landmark).description("When scoring, -3 VP per card you have exactly one copy of.").expansion(Expansion.Empires).build());
        
        // Promo Cards
        variablePileCards.add(sauna        = new CardImpl.Builder(Cards.Kind.Sauna, 4, CardType.Action).addCards(1).addActions(1).description("You may play an Avanto from your hand. - While this is in play, when you play a Silver, you may trash a card from your hand.").expansion(Expansion.Promo).build());
        variablePileCards.add(avanto       = new CardImpl.Builder(Cards.Kind.Avanto, 5, CardType.Action).addCards(3).description("You may play a Sauna from your hand.").expansion(Expansion.Promo).build());
        
        actionCardsPromo.add(walledVillage = new CardImpl.Builder(Cards.Kind.WalledVillage, 4, CardType.Action).addCards(1).addActions(2).description("At the start of Clean-up, if you have this and no more than one other Action card in play, you may put this on top of your deck.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(governor      = new CardImpl.Builder(Cards.Kind.Governor, 5, CardType.Action).addActions(1).description("Choose one; you get the version in parentheses: Each player gets +1 (+3) Cards; or each player gains a Silver (Gold); or each player may trash a card from his hand and gain a card costing exactly 1 (2) more.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(envoy         = new CardImpl.Builder(Cards.Kind.Envoy, 4, CardType.Action).description("Reveal the top 5 cards of your deck. The player to your left chooses one for you to discard. Draw the rest.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(prince        = new CardImpl.Builder(Cards.Kind.Prince, 8, CardType.Action).description("You may set this aside. If you do, set aside an Action card from your hand costing up to 4 Coins. At the start of each of your turns, play that Action, setting it aside again when you discard it from play. (Stop playing it if you fail to set it aside on a turn you play it.)").expansion(Expansion.Promo).build());
        actionCardsPromo.add(blackMarket   = new CardImpl.Builder(Cards.Kind.BlackMarket, 3, CardType.Action).addHeroism(2).description("Reveal the top 3 cards of the Black Market deck. You may buy one of them immediately. Put the unbought cards on the bottom of the Black Market deck in any order./n(Before the game, make a Black Market deck out of one copy of each Kingdom card not in the supply.)").expansion(Expansion.Promo).build());
        actionCardsPromo.add(stash         = new CardImpl.Builder(Cards.Kind.Stash, 5, CardType.Treasure).addHeroism(2).description("When you shuffle, you may put this anywhere in your deck.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(virtualSaunaAvanto = new CardImpl.Builder(Cards.Kind.SaunaAvanto, 4, CardType.Action).pileCreator(new SplitPileCreator(sauna, avanto)).description("This pile starts the game with 5 copies of Sauna on top, then 5 copies of Avanto. Only the top card of the pile can be gained or bought.").expansion(Expansion.Promo).build());
        
        eventCardsPromo.add(summon         = new CardImpl.Builder(Cards.Kind.Summon, 5, CardType.Event).description("Gain an Action card costing up to 4 Coins. Set it aside. If you do, then at the start of your next turn, play it.").expansion(Expansion.Promo).build());

        // Create map for from variable pile card to randomizer
        for (Card card : castleCards) { variablePileCardToRandomizer.put(card, virtualCastle); }
        variablePileCardToRandomizer.put(catapult, virtualCatapultRocks);
        variablePileCardToRandomizer.put(rocks, virtualCatapultRocks);
        variablePileCardToRandomizer.put(encampment, virtualEncampmentPlunder);
        variablePileCardToRandomizer.put(plunder, virtualEncampmentPlunder);
        variablePileCardToRandomizer.put(gladiator, virtualGladiatorFortune);
        variablePileCardToRandomizer.put(fortune, virtualGladiatorFortune);
        variablePileCardToRandomizer.put(patrician, virtualPatricianEmporium);
        variablePileCardToRandomizer.put(emporium, virtualPatricianEmporium);
        variablePileCardToRandomizer.put(settlers, virtualSettlersBustlingVillage);
        variablePileCardToRandomizer.put(bustlingVillage, virtualSettlersBustlingVillage);
        variablePileCardToRandomizer.put(sauna, virtualSaunaAvanto);
        variablePileCardToRandomizer.put(avanto, virtualSaunaAvanto);
        
        // Collect all Expansions
        for (Card card : heroCardsArpad)  { actionCards.add(card); }
        for (Card card : swordCardsArpad)  { actionCards.add(card); }
        for (Card card : shieldCardsArpad)  { actionCards.add(card); }

        for (Card card : heroCardsSandor)  { actionCards.add(card); }
        for (Card card : staffCardsSandor)  { actionCards.add(card); }
        for (Card card : healingCardsSandor)  { actionCards.add(card); }

        for (Card card : heroCardsWiola)  { actionCards.add(card); }
        for (Card card : shotCardsWiola)  { actionCards.add(card); }
        for (Card card : bowCardsWiola)  { actionCards.add(card); }

        for (Card card : heroCardsZsuzsa)  { actionCards.add(card); }
        for (Card card : daggerCardsZsuzsa)  { actionCards.add(card); }
        for (Card card : bowCardsZsuzsa)  { actionCards.add(card); }

        for (Card card : actionCardsBaseGameAll) { actionCards.add(card); }
        for (Card card : actionCardsIntrigueAll) { actionCards.add(card); }
        for (Card card : actionCardsSeaside)     { actionCards.add(card); }
        for (Card card : actionCardsAlchemy)     { actionCards.add(card); }
        for (Card card : actionCardsProsperity)  { actionCards.add(card); }
        for (Card card : actionCardsCornucopia)  { actionCards.add(card); }
        for (Card card : actionCardsGuilds)      { actionCards.add(card); }
        for (Card card : actionCardsAdventures)  { actionCards.add(card); }
        for (Card card : actionCardsEmpires)     { actionCards.add(card); }
        for (Card card : actionCardsPromo)       { actionCards.add(card); }
        
        for (Card card : eventCardsAdventures)  { eventsCards.add(card); }
        for (Card card : eventCardsEmpires)     { eventsCards.add(card); }
        for (Card card : eventCardsPromo)  		{ eventsCards.add(card); }
        
        for (Card card : landmarkCardsEmpires) { landmarkCards.add(card); }


        for (Card card : nonSupplyCards)        { nonKingdomCards.add(card); }
        for (Card card : prizeCards)            { nonKingdomCards.add(card); }
        for (Card card : eventsCards)           { nonKingdomCards.add(card); }
        for (Card card : landmarkCards)         { nonKingdomCards.add(card); }

        for (Card card : locationCardsFortBriggs) { locationCards.add(card); }
        for (Card card : locationCardsCitadelXajorkith) {locationCards.add(card); }
        for (Card card : locationCardsCitadelXajorkithGate) {locationCards.add(card); }
        for (Card card : locationCardsHoltvaros) {locationCards.add(card);}
        for (Card card : locationCardsHoltvarosMilitia) {locationCards.add(card);}
        for (Card card : locationCardsSylvanHeights) {locationCards.add(card);}
        for (Card card : locationCardsSylvanChorus) {locationCards.add(card);}

        for (Card card : actionCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : prizeCards)        { cardNameToCard.put(card.getName(), card); }
        for (Card card : nonSupplyCards)    { cardNameToCard.put(card.getName(), card); }
        for (Card card : castleCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : variablePileCards) { cardNameToCard.put(card.getName(), card); }
        for (Card card : nonKingdomCards)   { cardNameToCard.put(card.getName(), card); }
        for (Card card : eventsCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : landmarkCards)     { cardNameToCard.put(card.getName(), card); }

        for (Card card : heroismCards)      { cardNameToCard.put(card.getName(), card); }
        for (Card card : cardsGoblin)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : cardsLizard)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : cardsElf)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : cardsWinter)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : woundCards)        { cardNameToCard.put(card.getName(), card); }
        for (Card card : woundPile)         { cardNameToCard.put(card.getName(), card); }
        for (Card card : locationCards)     { cardNameToCard.put(card.getName(), card); }

        blackMarketCards.clear(); // Cards in Black Market deck are not in supply
    }

    public static boolean isKingdomCard(Card c) {
        return !nonKingdomCards.contains(c);
    }

    public static boolean isSupplyCard(Card c) {
        return !(nonSupplyCards.contains(c) || prizeCards.contains(c) || eventsCards.contains(c) || landmarkCards.contains(c) || blackMarketCards.contains(c));
    }
    
    public static boolean isBlackMarketCard(Card c) {
        return blackMarketCards.contains(c);
    }

}

