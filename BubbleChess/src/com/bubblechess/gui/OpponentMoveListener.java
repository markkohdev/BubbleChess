package com.bubblechess.gui;

import com.bubblechess.GUIBridge;
import com.bubblechess.client.Move;

public class OpponentMoveListener implements Runnable {
	
	protected GamePlayPanel panel;
	protected GUIBridge bridge;
	
	/**
	 * Constructor for the move listener subject.
	 * @param panel The observer panel which we will alert for refresh
	 * @param bridge The bridge to wait on
	 */
	public OpponentMoveListener(GamePlayPanel panel, GUIBridge bridge) {
		this.panel = panel;
		this.bridge = bridge;
	}

	@Override
	public void run() {
		//Wait for the move
		Move m = bridge.WaitForNextMove();
		
		//We have a move, noitify the panel
		panel.OpponentMoveRecieved(m);
	}

}
