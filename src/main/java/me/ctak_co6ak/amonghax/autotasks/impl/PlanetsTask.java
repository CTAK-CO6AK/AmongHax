package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class PlanetsTask extends AutoTask {

	private final int planetSlot = 58;

	public PlanetsTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		String currPlanetName = screen.slots.get(planetSlot).getStack().getName().getString();

		for (int i = 0; i < 45; i++) {
			if (screen.slots.get(i).getStack().getName().getString().equals(currPlanetName)) {
				client.interactionManager.clickSlot(screen.syncId, i, 0, SlotActionType.PICKUP, player);
				break;
			}
		}
		return true;
	}

	@Override
	public void reset() {
		super.reset();
	}
}
