package part2;


public class AppConstants {

    public static final String PLAYER_TOPIC = "player_topic.t";
    public static final String PLAYER_QUEUE = "player_queue.q";
    public static final String RESOURCE_TOPIC = "resource_topic.t";

    public static final String[] RESOURCES = {"woodResource", "stoneResource", "goldResource"};

    public static final String RESOURCE_TO_PLAYER_QUEUE = "resource_to_player.q";
    public static final String PLAYER_TO_MAIN_QUEUE = "players_to_main.q";
    public static final String RESOURCE_TO_MAIN_QUEUE = "resource_to_main.q";
    public static final String MAIN_TO_RESOURCES_QUEUE = "main_to_resource.q";

    public static final int WOOD_FOR_HOUSE = 150;
    public static final int STONE_FOR_HOUSE = 100;
    public static final int GOLD_FOR_HOUSE = 40;

    public static final String GENERATE_RESOURCE_MESSAGE = "Generate Resource";

    public static final int WAIT_TIME_TO_GENERATE_RESOURCE_MS = 1000;
    public static final int MESSAGE_LATENCY = 100;


}
