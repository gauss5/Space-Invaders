import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Nave {
    public BufferedImage nave; 
    private final int LARGURA_TELA = 800;
    private int x, y;
    private int dx;  // Velocidade horizontal controlada pela classe Game
    private final int VELOCIDADE = 5;
    private final int LARGURA = 50;
    private final int ALTURA = 20;

    public Nave(int x, int y) {
        try {
            nave = ImageIO.read(getClass().getResource("imgs/nave.png"));
        }
        catch (Exception e) {
			System.out.println("Erro ao carregar a imagem!");
		}
        this.x = x;
        this.y = y;
    }

    public void atualizarNave(int dx) {
      
        this.x += dx;
    
        if (this.x < 0) {
            this.x = 0;  
        }
        if (this.x + LARGURA > LARGURA_TELA) {
            this.x = LARGURA_TELA - LARGURA; 
        }
    }
    
    public void desenhar(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(nave, x, y, null);
    }


    public void setDx(int dx) {
        this.dx = dx;
    }

    public int obterX() {
        return x;
    }

    public int obterY() {
        return y;
    }

    public Rectangle obterLimites() {
        return new Rectangle(x, y, LARGURA, ALTURA);
    }
}
