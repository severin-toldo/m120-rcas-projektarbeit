package com.stoldo.m120_rcas_projektarbeit.util;

import lombok.Getter;

public enum IconKey {
	
	UPLOAD_ICON("upload_icon.png");
	
	@Getter
	private String key;
	
	private IconKey(String key) {
		this.key = key;
	}
}
