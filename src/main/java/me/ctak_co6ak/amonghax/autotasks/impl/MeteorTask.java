package me.ctak_co6ak.amonghax.autotasks.impl;

import java.util.ArrayList;
import java.util.List;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class MeteorTask extends AutoTask {

	public MeteorTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		List<Integer> itemSlots = new ArrayList<>();

		for (int i = 9; i < 45; i++) {
			if (screen.slots.get(i).hasStack())
				itemSlots.add(i);
		}

		if (itemSlots.size() > 0)
			client.interactionManager.clickSlot(screen.syncId, itemSlots.get(rand.nextInt(itemSlots.size())), 0,
					SlotActionType.PICKUP, player);

		return true;
	}
}
