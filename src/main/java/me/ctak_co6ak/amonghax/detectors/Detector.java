package me.ctak_co6ak.amonghax.detectors;

import net.minecraft.client.MinecraftClient;

public abstract class Detector {

	public abstract void initialize();

	public abstract void tick(MinecraftClient client);

	public abstract void onGameStart();

	public abstract void onGameEnd();
}
