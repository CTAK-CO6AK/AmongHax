package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class RefineOreTask extends AutoTask {

	private int clickedID = -1;

	public RefineOreTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		if (clickedID != -1) {
			client.interactionManager.clickSlot(screen.syncId, clickedID + 18, 0, SlotActionType.PICKUP, player);
			clickedID = -1;
		} else {
			for (int i = 11; i < 16; i++) {
				if (screen.slots.get(i).getStack().getItem().equals(Items.IRON_ORE)) {
					client.interactionManager.clickSlot(screen.syncId, i, 0, SlotActionType.PICKUP, player);
					clickedID = i;
					break;
				}
			}
		}
		return true;
	}

	@Override
	public void reset() {
		super.reset();
		clickedID = -1;
	}
}
