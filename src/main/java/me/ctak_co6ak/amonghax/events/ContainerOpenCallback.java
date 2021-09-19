package me.ctak_co6ak.amonghax.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public interface ContainerOpenCallback {
	Event<ContainerOpenCallback> EVENT = EventFactory.createArrayBacked(ContainerOpenCallback.class,
			(listeners) -> (client, id, title) -> {
				for (ContainerOpenCallback listener : listeners) {
					ActionResult result = listener.interact(client, id, title);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	ActionResult interact(MinecraftClient client, int id, Text title);
}
