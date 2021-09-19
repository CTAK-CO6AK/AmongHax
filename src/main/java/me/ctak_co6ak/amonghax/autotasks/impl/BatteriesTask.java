package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class BatteriesTask extends AutoTask {

	private int[] indices = { 1, 4, 7 };

	public BatteriesTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		for (int index : indices) {
			int slotId = index + 5 * 9;
			if (shouldClick(index)) {
				client.interactionManager.clickSlot(screen.syncId, slotId, 0, SlotActionType.PICKUP, player);
				
				return true;
			}
		}

		return true;
	}

	private boolean shouldClick(int id) {
		return !screen.slots.get(id).hasStack();
	}

}
