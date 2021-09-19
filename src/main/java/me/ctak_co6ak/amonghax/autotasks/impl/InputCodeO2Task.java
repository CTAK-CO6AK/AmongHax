package me.ctak_co6ak.amonghax.autotasks.impl;

import java.util.ArrayList;
import java.util.List;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class InputCodeO2Task extends AutoTask {

	private List<Integer> slotsToClick = new ArrayList<>();

	public InputCodeO2Task(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		findSlotsSequence(client, slotsToClick);

		if (!super.tick(client))
			return false;

		if (!slotsToClick.isEmpty()) {
			client.interactionManager.clickSlot(screen.syncId, slotsToClick.get(0), 0, SlotActionType.PICKUP, player);
			slotsToClick.remove(0);
			if (slotsToClick.isEmpty())
				nextDelay = 1000;
		}

		return true;
	}

	@Override
	public void reset() {
		super.reset();
		slotsToClick.clear();
	}

	private void findSlotsSequence(MinecraftClient client, List<Integer> list) {
		if (list.isEmpty()) {
			int[] numToSlotID = { 49, 21, 22, 23, 30, 31, 32, 39, 40, 41 };
			String title = client.currentScreen.getTitle().getString();
			String number = title.substring(title.length() - 5);
			System.out.println(number);
			for (char dig : number.toCharArray()) {
				list.add(numToSlotID[dig - 48]);
			}
		}
	}
}
