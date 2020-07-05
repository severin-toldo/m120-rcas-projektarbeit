package com.stoldo.m120_rcas_projektarbeit.model.javafx;

import com.stoldo.m120_rcas_projektarbeit.model.VoidCallable;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class DebounceTime {
	
	public static PauseTransition get(int milis) {
		return new PauseTransition(Duration.millis(milis));
	}
	
	public static void withDebounceTime(VoidCallable callable, PauseTransition sliderDebounceTime) {
		sliderDebounceTime.setOnFinished(event -> {
			try {
				callable.call();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		sliderDebounceTime.playFromStart();
	}
}
