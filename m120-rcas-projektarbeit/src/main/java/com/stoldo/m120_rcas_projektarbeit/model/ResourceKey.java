package com.stoldo.m120_rcas_projektarbeit.model;


public enum ResourceKey {
	
	APPLICATION_TITLE("applicationTitle"),
	MMM_CHART_TITLE("mmmChartTitle"),
	X_AXIS_LABLE("xAxisLabel"),
	Y_AXIS_LABLE("yAxisLabel");
	
	private String key;
	
	private ResourceKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
