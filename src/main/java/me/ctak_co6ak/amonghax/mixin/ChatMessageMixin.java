package me.ctak_co6ak.amonghax.mixin;

import me.ctak_co6ak.amonghax.events.ChatMessageCallback;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.ActionResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMessageMixin {

	@Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
	private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo info) {
		ActionResult result = ChatMessageCallback.EVENT.invoker().interact(packet.getLocation(), packet.getMessage());

		if (result == ActionResult.FAIL) {
			info.cancel();
		}

	}
}
