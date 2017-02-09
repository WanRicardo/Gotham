package br.com.inatel.ec206.controller;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Fight {

	public void run()
    {
        FileInputStream fis = null;
        try {
            String songFile = "C:/Users/usuario/Documents/Inatel/2015 - 2/EC206 - Engenharia de Software II/Lab/paradas do projeto/fight.mp3";
            fis = new FileInputStream(songFile);
            Player playWav = new Player(fis);
        	playWav.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
	
	public Fight() {
		run();
	}
}