package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class CalibrateTask extends AutoTask {

	private int[] indices = { 11, 14, 17 };
	
	public CalibrateTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}
	
	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;
		
		for (int index : indices) {
			if (screen.slots.get(index).getStack().getItem().equals(Items.NETHER_STAR)) {
				client.interactionManager.clickSlot(screen.syncId, index, 0, SlotActionType.PICKUP, player);
				break;
			}
		}
		
		return true;
	}


}
