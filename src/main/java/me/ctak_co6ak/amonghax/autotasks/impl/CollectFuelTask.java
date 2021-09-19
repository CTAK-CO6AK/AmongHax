package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class CollectFuelTask extends AutoTask {

	private int clickedID = -1;

	public CollectFuelTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		if (clickedID != -1) {
			client.interactionManager.clickSlot(screen.syncId, clickedID + 5, 0, SlotActionType.PICKUP, player);
			clickedID = -1;
		} else {
			for (int i = 1; i < 5; i++) {
				int slotID = i * 9 + 1;
				if (screen.slots.get(slotID).getStack().getItem().equals(Items.BUCKET)) {
					client.interactionManager.clickSlot(screen.syncId, slotID, 0, SlotActionType.PICKUP, player);
					clickedID = slotID;
					break;
				}
			}
			if(clickedID == -1) 
				client.interactionManager.clickSlot(screen.syncId, 10, 0, SlotActionType.PICKUP, player);
		}
		return true;
	}

	@Override
	public void reset() {
		super.reset();
		clickedID = -1;
	}

}
