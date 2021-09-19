package me.ctak_co6ak.amonghax.autotasks.impl;

import java.util.ArrayList;
import java.util.List;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class CommsTask extends AutoTask {

	public CommsTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	private List<Integer> slotsSequence = new ArrayList<>();

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;
		
		if (slotsSequence.isEmpty()) {
			for (int i = 0; i < 9; i++) {
				Item item = screen.slots.get(i).getStack().getItem();
				if (isRails(item)) {
					int counter = 1;
					for (int j = 9; j < 45; j++) {
						if (item.equals(screen.slots.get(j).getStack().getItem())) {
							slotsSequence.add(j);
							slotsSequence.add(counter * 9 + i);
							counter++;
						}
					}
				}
			}
		}
		
		if (!slotsSequence.isEmpty()) {
			client.interactionManager.clickSlot(screen.syncId, slotsSequence.get(0), 0, SlotActionType.PICKUP, player);
			slotsSequence.remove(0);
			if (slotsSequence.isEmpty())
				nextDelay = 1000;
		}

		return true;
	}

	private boolean isRails(Item item) {
		return item.equals(Items.RAIL) || item.equals(Items.DETECTOR_RAIL) || item.equals(Items.POWERED_RAIL);
	}

	@Override
	public void reset() {
		super.reset();
		slotsSequence.clear();
	}
}
