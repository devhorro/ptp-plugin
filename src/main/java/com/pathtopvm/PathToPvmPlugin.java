package com.pathtopvm;

import com.google.inject.Provides;
import com.pathtopvm.utils.BossKillParser;
import com.pathtopvm.utils.Utils;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@PluginDescriptor(
	name = "Path to PvM"
)
public class PathToPvmPlugin extends Plugin
{

	private static final Logger logger = LoggerFactory.getLogger(PathToPvmPlugin.class);
	@Inject
	private Client client;

	@Inject
	private PathToPvmConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	private final Utils utils = new Utils();

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		checkChatMessage(event);
	}

	public void checkChatMessage(ChatMessage event) {
		if (event.getType() != ChatMessageType.GAMEMESSAGE
				&& event.getType() != ChatMessageType.SPAM
				&& event.getType() != ChatMessageType.TRADE
				&& event.getType() != ChatMessageType.FRIENDSCHATNOTIFICATION)
		{
			return;
		}

		String playerName = "";

		if (client.getLocalPlayer() != null && client.getLocalPlayer().getName() != null) {
			playerName = client.getLocalPlayer().getName();
		}

		String message = utils.unescapeJavaString(Text.removeTags(event.getMessage()));

		if(event.getType() == ChatMessageType.GAMEMESSAGE) {
			BossKillParser.BossKillInfo bossKillInfo = BossKillParser.parseBossKillMessage(message);

			if (bossKillInfo != null) {
				String bossName = bossKillInfo.getBossName();
				int killCount = bossKillInfo.getKillCount();
				log.info("Player: {} boss kill: {} - Kill count: {}", playerName, bossName, killCount);
				// Send POST request to the server with bossName and killCount
			}
		}
	}
	@Provides
	PathToPvmConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PathToPvmConfig.class);
	}
}
