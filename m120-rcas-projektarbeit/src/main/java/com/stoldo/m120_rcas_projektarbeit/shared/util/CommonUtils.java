package com.stoldo.m120_rcas_projektarbeit.shared.util;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.stoldo.m120_rcas_projektarbeit.shared.constants.PathConstants;

public class CommonUtils {
	
	public static double roundTwoDecimals(double d) {
		return Math.round(d * 100.0) / 100.0;
	}
	
	public static File copyFileToImageDir(File srcFile) throws Exception {
		String uuid = UUID.randomUUID().toString();
    	File newFile = new File(PathConstants.IMAGE_PATH + File.separator + uuid + "_" + srcFile.getName());
    	FileUtils.copyFile(srcFile, newFile);
    	return newFile;
	}
	
	public static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static <T> T getRandomElementFromList(List<T> list) {
		Random r = new Random();
		int rdmIndex = r.nextInt(list.size());
		return list.get(rdmIndex);
	}
}
