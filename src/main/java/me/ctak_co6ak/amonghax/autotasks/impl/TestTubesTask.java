package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class TestTubesTask extends AutoTask {

	private int[] indices = { 1, 4, 7 };

	public TestTubesTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		for (int index : indices) {
			int slotId = index + 5 * 9;
			if (!screen.slots.get(index).hasStack()
					|| screen.slots.get(index).getStack().getItem().equals(Items.RED_TERRACOTTA)) {
				client.interactionManager.clickSlot(screen.syncId, slotId, 0, SlotActionType.PICKUP, player);
				break;
			}
		}

		return true;
	}
}
