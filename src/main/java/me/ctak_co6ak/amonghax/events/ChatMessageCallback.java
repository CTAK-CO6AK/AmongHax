package me.ctak_co6ak.amonghax.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public interface ChatMessageCallback {

	Event<ChatMessageCallback> EVENT = EventFactory.createArrayBacked(ChatMessageCallback.class,
			(listeners) -> (msgType, text) -> {
				for (ChatMessageCallback listener : listeners) {
					ActionResult result = listener.interact(msgType, text);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	ActionResult interact(MessageType msgType, Text text);
}
