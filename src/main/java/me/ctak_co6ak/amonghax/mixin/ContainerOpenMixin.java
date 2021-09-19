package me.ctak_co6ak.amonghax.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.ctak_co6ak.amonghax.events.ContainerOpenCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

@Mixin(HandledScreens.class)
public class ContainerOpenMixin {

	@Inject(at = @At("TAIL"), method = "open", cancellable = false)
	private static void onOpen(ScreenHandlerType<?> type, final MinecraftClient client, final int id, final Text title,
			CallbackInfo info) {
		ContainerOpenCallback.EVENT.invoker().interact(client, id, title);
	}
}