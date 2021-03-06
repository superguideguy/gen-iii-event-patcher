package com.github.superguideguy.g3ep;

import static com.github.superguideguy.g3ep.SavPatcher.SAVE_SECTION_LENGTH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

	public static final int TOTAL_BLOCKS = 32;

	public static byte[][] getSaveFile(String savString) {
		Path		savPath	= Paths.get(savString);
		byte[][]	data	= new byte[TOTAL_BLOCKS][SAVE_SECTION_LENGTH];
		try {
			byte[] savFile = Files.readAllBytes(savPath);
			if (savFile.length < (TOTAL_BLOCKS * SAVE_SECTION_LENGTH)) throw new IOException();
			for (int i = 0; i < TOTAL_BLOCKS; i++)
				for (int j = 0; j < SAVE_SECTION_LENGTH; j++) data[i][j] = savFile[(SAVE_SECTION_LENGTH * i) + j];
		} catch (IOException e) {
			return null;
		}
		return data;
	}

	public static byte[] getRomFile(String romString) {
		Path	romPath	= Paths.get(romString);
		byte[]	romFile	= null;
		try {
			romFile = Files.readAllBytes(romPath);
		} catch (IOException e) {
			return null;
		}
		return romFile;
	}

	public static boolean writeSaveFile(String savString, byte[][] data) {
		byte[] savData = new byte[data.length * data[0].length];
		for (int i = 0; i < savData.length; i++) savData[i] = data[i / data[0].length][i % data[0].length];
		return writeRomFile(savString, savData);
	}

	public static boolean writeRomFile(String romString, byte[] data) {
		Path romPath = Paths.get(romString);
		try {
			Files.write(romPath, data);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
