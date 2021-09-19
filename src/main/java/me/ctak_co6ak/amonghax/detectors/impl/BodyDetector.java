package me.ctak_co6ak.amonghax.detectors.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ctak_co6ak.amonghax.detectors.Detector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class BodyDetector extends Detector {

	private List<String> processedBodies = new ArrayList<>();

	@Override
	public void initialize() {
		reset();
	}

	@Override
	public void tick(MinecraftClient client) {
		if (client.world == null)
			return;
		
		AbstractClientPlayerEntity body = findBodyEntity(client);
		if(body == null)
			return;
		processedBodies.add(body.getName().getString());
		
		List<Killer> killers = findNearbyKillers(client, body);
		
		if (killers.size() > 0)
			printDetectedPlayer(killers, client);
	}

	private void printDetectedPlayer(List<Killer> killers, MinecraftClient client) {
		BlockPos killerPos = new BlockPos(killers.get(0).player.getPos());
		StringBuilder sb = new StringBuilder();
		sb.append(killers.get(0).player.getDisplayName().getString());
		sb.append("\u00A7a убил около вента ");
		sb.append(VentDetector.getNearestVent(killerPos));
		client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(sb.toString()), Util.NIL_UUID);
		killers.remove(0);
		sb = new StringBuilder();
		sb.append("\u00A7a");
		sb.append("Игроков рядом: ");
		sb.append(killers.size());
		client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(sb.toString()), Util.NIL_UUID);
		for (Killer k : killers) {
			if (k.player == null)
				continue;
			sb = new StringBuilder();
			sb.append("\u00A7a");
			sb.append(" - ");
			sb.append(k.player.getDisplayName().getString());
			sb.append("\u00A7a, до тела ");
			sb.append(k.distance);
			sb.append(" блоков");
			client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(sb.toString()), Util.NIL_UUID);
		}
	}

	@Override
	public void onGameStart() {
		reset();

	}

	@Override
	public void onGameEnd() {
		reset();

	}

	private void reset() {
		processedBodies.clear();
	}
	
	private AbstractClientPlayerEntity findBodyEntity(MinecraftClient client)
	{
		List<AbstractClientPlayerEntity> players = client.world.getPlayers();
		for (AbstractClientPlayerEntity body : players) {
			if (body != null && body.getHeight() < 1 && !processedBodies.contains(body.getName().getString())) {
				return body;
			}
		}
		return null;
	}
	
	private List<Killer> findNearbyKillers(MinecraftClient client, Entity body)
	{
		List<Killer> killers = new ArrayList<>();
		for (AbstractClientPlayerEntity killer : client.world.getPlayers()) {
			if (!isKiller(killer))
				continue;

			double dist = ((int) (killer.distanceTo(body) * 100)) / 100d;
			if (dist < 6) {
				killers.add(new Killer(killer, dist));
			}
		}
		
		Collections.sort(killers);
		return killers;
	}
	
	private boolean isKiller(AbstractClientPlayerEntity killer)
	{
		if(killer == null) return false;
		if(processedBodies.contains(killer.getName().getString())) return false;
		if(killer.getHeight() < 1) return false;
		if(killer.hasStatusEffect(StatusEffects.INVISIBILITY)) return false;
		if(killer.getDisplayName().getString().contains("[Ghost]")) return false;
		return true;
	}

	private class Killer implements Comparable<Killer> {
		public AbstractClientPlayerEntity player;
		public double distance;

		public Killer(AbstractClientPlayerEntity p, double d) {
			player = p;
			distance = d;
		}

		@Override
		public int compareTo(Killer b) {
			return Double.compare(distance, b.distance);
		}
	}

}
