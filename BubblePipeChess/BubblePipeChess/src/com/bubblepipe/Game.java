package com.bubblepipe;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	public Game() {
		initUI();
	}
	
	protected void initUI() {
		add(new ChessBoard());
		
		setSize(1024,768);
		
		setTitle("Bubble Pipe Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game ex = new Game();
                ex.setVisible(true);
            }
        });
	}

}
