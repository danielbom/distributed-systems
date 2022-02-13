package chat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Classe utilizada para encapsular operações com emojis.
 * 
 * @author daniel e mara
 */
public class Emoji {
	
	public static ImageIcon SMILE = createSmileFace();
	
	private static HashMap<String, String> emoticons = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
	{
		put("\\:\\)", "😀");
		put("\\:o", "😮");
		put("\\`\\:", "😓");
		put("\\:\\(", "☹️");
	}};
	
	private static ImageIcon createSmileFace() {
        BufferedImage res = new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
        Graphics g = res.getGraphics();
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Preenchimento do rosto
        g.setColor(Color.yellow);
        g.fillOval(0,0,16,16);

        // Contorno do rosto
        g.setColor(Color.black);
        g.drawOval(0,0,16,16);

        // Olho esquerdo
        g.drawLine(4,5, 6,5);
        g.drawLine(4,6, 6,6);

        // Olho direito
        g.drawLine(11,5, 9,5);
        g.drawLine(11,6, 9,6);

        // Boca
        g.drawLine(4,10, 8,12);
        g.drawLine(8,12, 12,10);
        g.dispose();

        return new ImageIcon(res);
    }

	public static String format(String message) {
		String result = message;
		for (Map.Entry<String, String> entry : emoticons.entrySet())
		    result = result.replaceAll(entry.getKey(), entry.getValue());
		return result;
	}
}
