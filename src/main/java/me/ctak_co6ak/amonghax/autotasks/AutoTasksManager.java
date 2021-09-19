package me.ctak_co6ak.amonghax.autotasks;

import java.util.HashMap;
import java.util.Map.Entry;

import me.ctak_co6ak.amonghax.autotasks.impl.*;
import me.ctak_co6ak.amonghax.events.ContainerOpenCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.ActionResult;

public class AutoTasksManager {

	private HashMap<String, AutoTask> tasks = new HashMap<>();

	public AutoTask getTaskByTitle(String title) {
		if (title.startsWith("Input Code: "))
			return tasks.get("Input Code");

		if (title.startsWith("Harp")) return tasks.get("Harp");
		return tasks.get(title);
	}

	public void initialize() throws Exception {
		tasks.clear();
		tasks.put("Wires", new WiresTask(200, 250));
		tasks.put("Press the Numbers in Order", new TntNumbersTask(350, 400));
		tasks.put("Navigate to Planet", new PlanetsTask(350, 400));
		tasks.put("Empty Chute", new EmptyChuteTask(150, 200));
		tasks.put("Chart Course", new ChartCourseTask(150, 200));
		tasks.put("Test Tubes", new TestTubesTask(450, 500));
		tasks.put("Charge The Batteries", new BatteriesTask(150, 200));
		tasks.put("Input Code", new InputCodeO2Task(300, 400));
		tasks.put("Connect the Rails", new CommsTask(200, 250));
		// new tasks go here
		tasks.put("Calibrate Oscillators", new CalibrateTask(50, 70));
		tasks.put("Destroy Meteoroids", new MeteorTask(200, 250));
		tasks.put("Collect Engine Fuel", new CollectFuelTask(200, 250));
		tasks.put("Fuel the Engines", new RefuelEngineTask(250, 300));
		tasks.put("Refine Ore Samples", new RefineOreTask(200, 250));
		tasks.put("Restore the Shields", new ShieldsTask(600, 750));

		tasks.put("Harp", new TestHypxiel(30, 50));

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.currentScreen != null) {
				String title = client.currentScreen.getTitle().getString();
				AutoTask task = getTaskByTitle(title);
				if (task != null)
					task.tick(client);
			} else {
				AutoTask.delayTimer.reset();
			}
		});

		ContainerOpenCallback.EVENT.register((client, id, title) -> {
			System.out.println("Screen opened " + title.getString());
			AutoTask task = getTaskByTitle(title.getString());
			if (task != null)
				task.reset();
			return ActionResult.PASS;
		});
	}

	public void resetTasks() {
		AutoTask.delayTimer.reset();
		for (Entry<String, AutoTask> task : tasks.entrySet()) {
			task.getValue().reset();
		}
	}
}
