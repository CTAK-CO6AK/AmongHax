package me.ctak_co6ak.amonghax.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.function.Consumer;

public class ConfigLoader {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final Consumer<Config> onLoad;
	private final File file;
	private final Config defaultConfig;

	public ConfigLoader(Config defaultConfig, String filename, Consumer<Config> onLoad) {
		this.defaultConfig = defaultConfig;
		this.onLoad = onLoad;
		this.file = new File(getFolder(), filename);
	}

	public void load() {
		Config config = defaultConfig;

		if (!file.exists()) {
			save(config);
		}

		config = read();

		Defaulter.setDefaults(config, defaultConfig.getClass());

		onLoad.accept(config);
	}

	public Config read() {
		try (FileReader reader = new FileReader(file)) {
			return (Config) GSON.fromJson(reader, defaultConfig.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			return defaultConfig;
		}
	}

	public void save(Config config) {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(GSON.toJson(config));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getFolder() {
		return FabricLoader.getInstance().getConfigDir().toFile();
	}
}
