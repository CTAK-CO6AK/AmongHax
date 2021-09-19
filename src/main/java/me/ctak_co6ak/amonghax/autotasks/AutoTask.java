package me.ctak_co6ak.amonghax.autotasks;

import java.util.Random;

import me.ctak_co6ak.amonghax.utils.MSTimer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;

public abstract class AutoTask {

	protected int minDelay;
	protected int maxDelay;
	protected int nextDelay;
	protected PlayerEntity player;
	protected ScreenHandler screen;
	public final static MSTimer delayTimer = new MSTimer();
	public final static Random rand = new Random();

	public AutoTask(int minDel, int maxDel) throws Exception {
		if (maxDel <= minDel)
			throw new Exception("MaxDelay cannot be less or equal to MinDelay");
		minDelay = minDel;
		maxDelay = maxDel;
	}

	public boolean tick(MinecraftClient client) {
		if (!delayTimer.hasTimePassed(nextDelay))
			return false;
		nextDelay = newDelay();
		delayTimer.reset();
		player = client.player;
		screen = player.currentScreenHandler;
		return true;
	}

	public int newDelay() {
		return rand.nextInt(maxDelay - minDelay) + minDelay;
	}

	public void reset() {
		nextDelay = newDelay();
	};

}
