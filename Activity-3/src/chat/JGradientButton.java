package chat;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;

/**
 * Classe utilizada para ersonalizar os botões da aplicação.
 * 
 * @author daniel e mara
 * link: https://stackoverflow.com/questions/7115672/change-jbutton-gradient-color-but-only-for-one-button-not-all
 */
public final class JGradientButton extends JButton{
	private static final long serialVersionUID = 1L;

	public JGradientButton(String text){
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0), 
                getBackground(), 
                new Point(0, getHeight()/3), 
                Color.WHITE));
        g2.fillRect(0, 0, getWidth(), getHeight()/3);
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight()/3), 
                Color.WHITE, 
                new Point(0, getHeight()), 
                getBackground()));
        g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}