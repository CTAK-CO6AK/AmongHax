package me.ctak_co6ak.amonghax.autotasks.impl;

import me.ctak_co6ak.amonghax.autotasks.AutoTask;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class TestHypxiel extends AutoTask {

	public TestHypxiel(int minDel, int maxDel) throws Exception {
		super(minDel, maxDel);
	}

	private int clickedSlot = 0;
	private boolean doubl = false;

	@Override
	public boolean tick(MinecraftClient client) {
		if (!super.tick(client))
			return false;

		for (int i = 0; i < 9; i++) {
			int slotId = i + (4 * 9);
			Item item = screen.slots.get(slotId).getStack().getItem();
			if (item.equals(Items.QUARTZ_BLOCK)) {
				if (clickedSlot != slotId) {
					if (!isGlassPane(screen.slots.get(slotId - 9).getStack().getItem())) {
						doubl = true;
					}
					clickedSlot = slotId;
					client.interactionManager.clickSlot(screen.syncId, slotId, 0, SlotActionType.CLONE, player);
					break;
				}
				else if (doubl && isGlassPane(screen.slots.get(slotId - 9).getStack().getItem()))
				{
					doubl = false;
					client.interactionManager.clickSlot(screen.syncId, slotId, 0, SlotActionType.CLONE, player);
				}
			}
		}

		if (!screen.slots.get(clickedSlot).getStack().getItem().equals(Items.QUARTZ_BLOCK))
			clickedSlot = 0;

		return true;
	}

	private boolean isGlassPane(Item item) {
		return item.equals(Items.PINK_STAINED_GLASS_PANE) || item.equals(Items.YELLOW_STAINED_GLASS_PANE)
				|| item.equals(Items.LIME_STAINED_GLASS_PANE) || item.equals(Items.GREEN_STAINED_GLASS_PANE)
				|| item.equals(Items.PURPLE_STAINED_GLASS_PANE) || item.equals(Items.BLUE_STAINED_GLASS_PANE)
				|| item.equals(Items.LIGHT_BLUE_STAINED_GLASS_PANE);
	}

	@Override
	public void reset() {
		super.reset();
		clickedSlot = 0;
		doubl = false;
	}
}
