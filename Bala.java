import java.awt.*;

public class Bala {
    private int x, y;
    private final int VELOCIDADE = 10;
    private final int LARGURA = 5;
    private final int ALTURA = 10;
    private boolean visivel;

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        this.visivel = true;
    }

    public void atualizar() {
        y -= VELOCIDADE;
        if (y < 0) {
            visivel = false;
        }
    }

    public void desenhar(Graphics g) {
        if (visivel) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, LARGURA, ALTURA);
        }
    }

    public boolean estaVisivel() {
        return visivel;
    }

    public Rectangle obterLimites() {
        return new Rectangle(x, y, LARGURA, ALTURA);
    }
}
