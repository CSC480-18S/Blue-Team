package com.mygdx.oswebble.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.oswebble.OswebbleMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "OSWEBBLE";
                config.useGL30 = true;
                config.width = 1080;
                config.height = 776;
		new LwjglApplication(new OswebbleMain(), config);
	}
}
