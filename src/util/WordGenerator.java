package util;
import java.util.List;
import java.util.Random;

public class WordGenerator {
    private static final List<String> WORDS = List.of(
        "flibbertigibbet", "collywobbles", "gobbledygook", "bamboozle", "lollygag", "skedaddle",
        "hullabaloo", "cattywampus", "kerfuffle", "snickersnee", "brouhaha", "canoodle", 
        "nudiustertian", "ballyhoo", "malarkey", "flummox", "hodgepodge", "foofaraw", "claptrap", 
        "rumpus", "sassafras", "whippersnapper", "gallivant", "thingamabob", "whatchamacallit", 
        "guffaw", "hobnob", "bazinga", "yokel", "hornswoggle", "tryst", "limerence", "sonorous", 
        "serendipity", "defenestration", "absquatulate", "nincompoop", "pauciloquent", "quibble", 
        "ragamuffin", "bumbershoot", "lackadaisical", "wishy-washy", "agog", "akimbo", "alfresco", 
        "balderdash", "billingsgate", "cachinnate", "callipygian", "discombobulate", "fandango", 
        "fartlek", "gazump", "gobemouche", "gongoozle", "harum-scarum", "jentacular", "kibitzer", 
        "mugwump", "namby-pamby", "oxter", "pandiculation", "quockerwodger", "ratoon", "smellfungus", 
        "snollygoster", "tarradiddle", "twattle", "wabbit", "xertz", "yaffle", "zabernism", 
        "zymurgy", "zugzwang", "vuvuzela", "widdershins", "vomitory", "turophile", "spatchcock", 
        "slumgullion", "rip-roaring", "quixotic", "plethora", "oxymoron", "muggle", "juggernaut", 
        "hippopotomonstrosesquipedaliophobia", "gazebo", "flapdoodle", "esperanto", "dingy", "cockamamie", 
        "blatherskite", "bandwagon", "anemone", "ambrosial", "agog", "snazzy", "flabbergasted", "quirky"
    );
    private static final Random RANDOM = new Random();

    public static String getNewWord() {
        return WORDS.get(RANDOM.nextInt(WORDS.size()));
    }
}
