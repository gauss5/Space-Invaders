import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Alien {
    public BufferedImage alien; 
    private int x, y;
    private final int LARGURA = 50;
    private final int ALTURA = 30;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
         try {
            alien = ImageIO.read(getClass().getResource("imgs/alien.png")) ;
        }
        catch (Exception e) {
			System.out.println("Erro ao carregar a imagem!");
		}
    }
    
    public void atualizar(int dxFrota, int dyFrota) {
        this.x += dxFrota;  
        this.y += dyFrota;  
    }

    
    public void desenhar(Graphics g) {
        g.drawImage(alien, x, y, null);
    }
   
    public Rectangle obterLimites() {
        return new Rectangle(x, y, LARGURA, ALTURA);
    }

    public int obterX() {
        return x;
    }

    public int obterY() {
        return y;
    }

    public int obterLargura() {
        return LARGURA;
    }

    public int obterAltura() {
        return ALTURA;
    }
}
