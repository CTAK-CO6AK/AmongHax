package me.ctak_co6ak.amonghax.autotasks.impl;

import java.util.ArrayList;
import java.util.List;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class WiresTask extends AutoTask {

	private List<Integer> wiresSlots = new ArrayList<>();

	public WiresTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;
		
		if (wiresSlots.isEmpty()) {
			for (int i = 1; i < 5; i++)
				for (int j = 1; j < 5; j++) {
					int firstSlotId = i * 9 + 1;
					int secondSlotId = j * 9 + 7;
					ItemStack firstItem = screen.slots.get(firstSlotId).getStack();
					ItemStack secondItem = screen.slots.get(secondSlotId).getStack();
					if (firstItem != null && secondItem != null && firstItem.getItem().equals(secondItem.getItem())) {
						wiresSlots.add(firstSlotId);
						wiresSlots.add(secondSlotId);
					}
				}
		}
		
		if (!wiresSlots.isEmpty()) {
			client.interactionManager.clickSlot(screen.syncId, wiresSlots.get(0), 0, SlotActionType.PICKUP, player);
			wiresSlots.remove(0);
			if (wiresSlots.isEmpty())
				nextDelay = 1000;
		}
		return true;
	}

	@Override
	public void reset() {
		super.reset();
		if (!wiresSlots.isEmpty())
			wiresSlots.clear();
	}
}
