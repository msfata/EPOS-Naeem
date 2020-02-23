package Laptop;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
@SuppressWarnings("all")
public class ScreenShot {
	public static String path;
	static int m = 3700;

	public File getScreen(File file) throws AWTException, IOException, InterruptedException {

		JFrame j = new JFrame();
		j.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				m = 0;
				System.exit(0);
			}
		});
		j.getContentPane().setBackground(Color.RED);
		j.setSize(1, 1);
		j.setUndecorated(true);
		j.setType(Type.UTILITY);
		j.setVisible(true);
		BufferedImage screencapture = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		file = new File("s" + 1 + ".jpg");
		ImageIO.write(screencapture, "jpg", file);
		path = file.getAbsolutePath();
		return file;
	}
}
