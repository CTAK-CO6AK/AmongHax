package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class TntNumbersTask extends AutoTask {

	private int minSlot = 999;
	private int minValue = 999;

	public TntNumbersTask(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		for (int i = 2; i < 16; i++) {
			ItemStack item = screen.slots.get(i).getStack();
			int digit = -1;
			if (item != null) {
				String itemName = item.getName().getString().replaceAll("\u00A7c", "red");
				if (itemName.substring(0, 3).equals("red")) {
					try {
						digit = Integer.parseInt(itemName.substring(3));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
			if (digit != -1 && digit < minValue) {
				minValue = digit;
				minSlot = i;
			}
		}
		if (minSlot != 999)
			client.interactionManager.clickSlot(screen.syncId, minSlot, 0, SlotActionType.PICKUP, player);

		minSlot = 999;
		minValue = 999;
		return true;
	}

	@Override
	public void reset() {
		super.reset();
		minSlot = 999;
		minValue = 999;
	}

}
