package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class ShieldsTask extends AutoTask {

	public ShieldsTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	private int[] indices = { 2, 4, 6, 11, 13, 15 };

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		for (int index : indices) {
			if (shouldClick(index)) {
				client.interactionManager.clickSlot(screen.syncId, index, 0, SlotActionType.PICKUP, player);
				return true;
			}
		}

		return true;
	}

	private boolean shouldClick(int id) {
		return !screen.slots.get(id).getStack().getName().getString().contains("STABLE");
	}
}
