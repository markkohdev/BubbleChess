package com.bubblechess.gui;

import com.bubblechess.GUIBridge;

public class OpponentMoveListener implements Runnable {
	
	protected GamePlayPanel panel;
	protected GUIBridge bridge;
	
	public OpponentMoveListener(GamePlayPanel panel, GUIBridge bridge) {
		this.panel = panel;
		this.bridge = bridge;
	}

	@Override
	public void run() {
		System.out.println("Listener waiting for move");
		bridge.WaitForNextMove();
		panel.OpponentMoveRecieved();
	}

}
