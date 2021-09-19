package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class ChartCourseTask extends AutoTask {

	private int[] idOffsets = { 1, -9, 9 };

	public ChartCourseTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		int cowSlotID = -1;
		for (int i = 9; i < 54; i++) {
			if (screen.slots.get(i).getStack().getItem().equals(Items.PLAYER_HEAD)) {
				System.out.println("Cow in slot " + i);
				cowSlotID = i;
				break;
			}
		}

		int nextPathID = findNextCell(cowSlotID);
		if (isSlotValid(nextPathID)) {
			System.out.println("Next path slot id " + nextPathID);
			client.interactionManager.clickSlot(screen.syncId, nextPathID, 0, SlotActionType.PICKUP, player);
		}

		return true;
	}

	private int findNextCell(int cowSlotID) {
		for (int offset : idOffsets) {
			int newID = cowSlotID + offset;
			if (isPath(newID))
				return newID;
		}
		return -1;
	}

	private boolean isPath(int id) {
		return isSlotValid(id) && screen.slots.get(id).getStack().getItem().equals(Items.BROWN_STAINED_GLASS_PANE);
	}

	private boolean isSlotValid(int id) {
		return id >= 0 && id < screen.slots.size();
	}
}
