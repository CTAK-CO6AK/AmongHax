package me.ctak_co6ak.amonghax.detectors.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.ctak_co6ak.amonghax.detectors.Detector;
import me.ctak_co6ak.amonghax.utils.MSTimer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VentDetector extends Detector {

	private final int xRadius = 100;
	private final int zRadius = 100;
	private final int yRadius = 40;
	private MinecraftClient client;

	private List<BlockPos> vents = new ArrayList<>();
	private static HashMap<BlockPos, String> ventLocations = new HashMap<>();
	private MSTimer ventTimer = new MSTimer();
	private String lastImposter = "";

	public static String getNearestVent(BlockPos pos) {
		BlockPos closestBlockPos = pos;
		int minDist = 99;
		for (BlockPos ventPos : ventLocations.keySet()) {
			int dist = ventPos.getManhattanDistance(pos);
			if (dist < minDist) {
				closestBlockPos = ventPos;
				minDist = dist;
			}
		}
		return ventLocations.getOrDefault(closestBlockPos, "null");
	}

	@Override
	public void initialize() {
		//Spaceship vent locations
		ventLocations.put(new BlockPos(942, 25, 21), "TNT to Upper Engine");
		ventLocations.put(new BlockPos(950, 25, 35), "TNT to Lower Engine");
		ventLocations.put(new BlockPos(963, 25, 55), "Lower Engine");
		ventLocations.put(new BlockPos(964, 25, 5), "Upper Engine");
		ventLocations.put(new BlockPos(972, 25, 34), "Security");
		ventLocations.put(new BlockPos(978, 25, 26), "Medbay");
		ventLocations.put(new BlockPos(980, 25, 37), "Electricity");
		ventLocations.put(new BlockPos(1018, 25, 43), "Admin Room");
		ventLocations.put(new BlockPos(1022, 25, 14), "Cafe");
		ventLocations.put(new BlockPos(1037, 25, 2), "Weapons");
		ventLocations.put(new BlockPos(1039, 25, 32), "Navs Corridor");
		ventLocations.put(new BlockPos(1039, 25, 57), "Shields");
		ventLocations.put(new BlockPos(1059, 25, 22), "Navs to Weapons");
		ventLocations.put(new BlockPos(1059, 25, 33), "Navs to Shields");
		//Mars vent locations
		ventLocations.put(new BlockPos(2009,90,60), "Comms");
		ventLocations.put(new BlockPos(2048,82,38), "Garden");
		ventLocations.put(new BlockPos(2044,89,3), "Lab");
		ventLocations.put(new BlockPos(1979,85,54), "TNT");
		ventLocations.put(new BlockPos(1961,85,26), "Near Security");
		ventLocations.put(new BlockPos(1966,88,11), "Navs");
		ventLocations.put(new BlockPos(1980,88,4), "Navs");
		ventLocations.put(new BlockPos(2010,85,11), "Electrical");
		ventLocations.put(new BlockPos(2012,85,0), "Electrical");
		ventLocations.put(new BlockPos(1992,85,20), "Office");
		ventLocations.put(new BlockPos(1994,85,32), "Admin Room");
		
		client = MinecraftClient.getInstance();
	}

	@Override
	public void tick(MinecraftClient client) {
		if (client.world == null)
			return;

		for (BlockPos ventPos : vents) {
			BlockState vent = client.world.getBlockState(ventPos);

			if (!(vent.getBlock() instanceof TrapdoorBlock)) {
				vents.clear();
				return;
			}

			if (vent.get(TrapdoorBlock.OPEN).booleanValue()) {
				List<AbstractClientPlayerEntity> players = client.world.getPlayers();
				AbstractClientPlayerEntity ventedPlayer = null;
				double distance = 5;
				for (AbstractClientPlayerEntity pl : players) {
					Vec3d blockPos = new Vec3d(ventPos.getX() + 0.5, ventPos.getY(), ventPos.getZ() + 0.5);
					if (pl.getPos().distanceTo(blockPos) <= distance) {
						distance = pl.getPos().distanceTo(blockPos);
						ventedPlayer = pl;
					}
				}
				if (ventedPlayer != null) {
					String ventedPlayerName = ventedPlayer.getName().getString();
					if (!lastImposter.equals(ventedPlayerName) || ventTimer.hasTimePassed(2000)) {
						lastImposter = ventedPlayerName;
						ventTimer.reset();
						printDetectedPlayer(ventedPlayer.getDisplayName().getString(),
								ventLocations.getOrDefault(ventPos, "хз если честно"));
					}
					System.out.println(ventedPlayerName + " ВЕНТАНУЛСЯ на кордах " + ventPos.toShortString()
							+ ", расстояние от него до вента - " + distance + " блоков");
				} else
					System.out.println("Кто-то ВЕНТАНУЛСЯ, но ближайший игрок был дальше чем 5 блоков " + ventPos.toShortString());
			}
		}

	}

	private void printDetectedPlayer(String name, String location) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("\u00A7a вентанулся (");
		sb.append(location);
		sb.append(")");
		client.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(sb.toString()), Util.NIL_UUID);
	}

	@Override
	public void onGameStart() {
		vents.clear();
		int playerX = (int) (client.player.getX());
		int playerY = (int) (client.player.getY());
		int playerZ = (int) (client.player.getZ());
		
		for (int x = -xRadius; x < xRadius; x++) {
			for (int y = -yRadius; y < yRadius; y++) {
				for (int z = -zRadius; z < zRadius; z++) {
					int blockx = playerX + x;
					int blocky = playerY + y;
					int blockz = playerZ + z;
					BlockState bs = client.world.getBlockState(new BlockPos(blockx, blocky, blockz));
					if (bs.getBlock().equals(Blocks.IRON_TRAPDOOR) && !bs.get(TrapdoorBlock.OPEN).booleanValue() && bs.get(TrapdoorBlock.HALF).equals(BlockHalf.BOTTOM)) {
						vents.add(new BlockPos(blockx, blocky, blockz));
						System.out.println("Найден вент на " + blockx + " " + blocky + " " + blockz);
					}
				}
			}
		}
		System.out.println("Всего вентов: " + vents.size());
	}

	@Override
	public void onGameEnd() {
		vents.clear();
		lastImposter = "";
	}

}
