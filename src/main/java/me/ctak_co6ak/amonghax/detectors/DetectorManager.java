package me.ctak_co6ak.amonghax.detectors;

import java.util.ArrayList;
import java.util.List;

import me.ctak_co6ak.amonghax.AmongHaxMain;
import me.ctak_co6ak.amonghax.detectors.impl.*;
import me.ctak_co6ak.amonghax.events.ChatMessageCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.MessageType;
import net.minecraft.util.ActionResult;

public class DetectorManager {

	private List<Detector> detectors = new ArrayList<>();

	public void initialize() {
		detectors.add(new VentDetector());
		detectors.add(new BodyDetector());

		for (Detector d : detectors)
			d.initialize();

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (!AmongHaxMain.CONFIG.detectorsEnabled)
				return;
			for (Detector d : detectors)
				d.tick(client);
		});

		ChatMessageCallback.EVENT.register((msgType, message) -> {
			if (AmongHaxMain.CONFIG.detectorsEnabled)
				if (msgType == MessageType.CHAT || msgType == MessageType.SYSTEM) {
					if (message.getString().equals("Among Slimes is starting in 1 second."))
						onGameStart();

					if (message.getString().contains("    Thank you for playing Among Slimes") || message.getString()
							.equals("Waiting for your Party Members to finish their game first, then you can Play Again"))
						onGameEnd();
				}

			return ActionResult.PASS;
		});
	}

	public void onGameStart() {
		for (Detector d : detectors)
			d.onGameStart();
	}

	public void onGameEnd() {
		for (Detector d : detectors)
			d.onGameEnd();
	}
	
	public void onConfigChange(boolean b)
	{
		if(b)
			onGameStart();
		else
			onGameEnd();
	}
}
