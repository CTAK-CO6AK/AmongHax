package me.ctak_co6ak.amonghax;

import org.lwjgl.glfw.GLFW;

import me.ctak_co6ak.amonghax.autotasks.*;
import me.ctak_co6ak.amonghax.config.Config;
import me.ctak_co6ak.amonghax.config.ConfigLoader;
import me.ctak_co6ak.amonghax.detectors.DetectorManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

public class AmongHaxMain implements ModInitializer {

	private AutoTasksManager autoTasksManager = new AutoTasksManager();
	private DetectorManager detectorManager = new DetectorManager();
	private static KeyBinding keyBinding;

	public static Config CONFIG = new Config();
	private static ConfigLoader CONFIG_LOADER = new ConfigLoader(new Config(), "amonghax.json",
			config -> AmongHaxMain.CONFIG = config);

	@Override
	public void onInitialize() {
		try {
			CONFIG_LOADER.load();
			autoTasksManager.initialize();
			detectorManager.initialize();

		} catch (Exception e) {
			System.out.println("Error while initializing AmongHax");
			e.printStackTrace();
			MinecraftClient.getInstance().close();
		}

		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("Вкл/выкл детекторы импостеров",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_5, "AmongHax"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				CONFIG.detectorsEnabled = !CONFIG.detectorsEnabled;
				CONFIG_LOADER.save(CONFIG);
				detectorManager.onConfigChange(CONFIG.detectorsEnabled);
				StringBuilder sb = new StringBuilder();
				sb.append("\u00A7aДетекторы компостеров ");
				sb.append(CONFIG.detectorsEnabled ? "включены" : "выключены");
				client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(sb.toString()), Util.NIL_UUID);
			}
		});
	}
}
