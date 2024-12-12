import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
    private final int LARGURA_TELA = 800;
    private final int ALTURA_TELA = 600;
    private Nave nave;
    private ArrayList<Bala> balas;
    private ArrayList<Alien> aliens;
    private boolean jogoRodando;
    private boolean venceu;
    private int pontuacao;
    private int dx; 
    private long tempoUltimaMovimentacao = System.currentTimeMillis();
    private final long intervaloMovimentoFrota = 50; 

    
    private int dxFrota = 1; 
    private int dyFrota = 1;

    
    private boolean k_esquerda = false;
    private boolean k_direita = false;
    private boolean k_espaco = false;

    public Game() {
        setPreferredSize(new java.awt.Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.black);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                teclaPressionada(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                teclaSolta(e);
            }
        });
        setFocusable(true);

        inicializarJogo();
        
       
        new Thread(this::gameLoop).start();
    }

    
    private void gameLoop() {
        while (true) {

            handlerEvents();
            update();
            render();
            try {
                Thread.sleep(17); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handlerEvents() {
        dx = 0; 

        if (k_esquerda) {
            dx = -5; 
        }
        if (k_direita) {
            dx = 5; 
        }
        if (k_espaco) {
            atirar(); 
        }
    }

    public void update() {
   
    nave.atualizarNave(dx);

    
    for (int i = 0; i < balas.size(); i++) {
        Bala bala = balas.get(i);
        bala.atualizar();

        if (!bala.estaVisivel()) {
            balas.remove(i);
            i--;
        }
    }

    long tempoAtual = System.currentTimeMillis();
    if (tempoAtual - tempoUltimaMovimentacao >= intervaloMovimentoFrota) {
       
        for (Alien alien : aliens) {
            alien.atualizar(dxFrota, dyFrota);  
        }
        
        tempoUltimaMovimentacao = tempoAtual;
    }

    testarColisoes();

    if (aliens.isEmpty()) {
        venceu = true; 
        jogoRodando = false;
    }

    verificarLimitesDaTela();
}
   
    public void render() {
        repaint();
    }

    private void inicializarJogo() {
        nave = new Nave(LARGURA_TELA / 2, ALTURA_TELA - 50);
        balas = new ArrayList<>();
        aliens = new ArrayList<>();
        jogoRodando = true;
        pontuacao = 0;

        criarFrota();
    }

    private void criarFrota() {
        int linhas = 4;
        int colunas = 10;
        int larguraAlien = 50;
        int alturaAlien = 30;
        int espaco = 20;

        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                int x = coluna * (larguraAlien + espaco) + 50;
                int y = linha * (alturaAlien + espaco) + 50;
                aliens.add(new Alien(x, y));
            }
        }
    }

    private void testarColisoes() {
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);

            if (alien.obterLimites().intersects(nave.obterLimites())) {
                jogoRodando = false;
                venceu = false;
                return;
            }

            for (int j = 0; j < balas.size(); j++) {
                Bala bala = balas.get(j);
                if (alien.obterLimites().intersects(bala.obterLimites())) {
                    aliens.remove(i);
                    balas.remove(j);
                    pontuacao += 10;
                    i--;
                    break;
                }
            }
        }
    }

  
    private void verificarLimitesDaTela() {
        boolean atingiuLimiteDireita = false;
        boolean atingiuLimiteEsquerda = false;

        for (Alien alien : aliens) {
            if (alien.obterX() + alien.obterLargura() >= LARGURA_TELA) {
                atingiuLimiteDireita = true;
            }
            if (alien.obterX() <= 0) {
                atingiuLimiteEsquerda = true;
            }
        }

        if (atingiuLimiteDireita || atingiuLimiteEsquerda) {
            dxFrota = -dxFrota;
            dyFrota = 1;
        }
    }

    public void teclaPressionada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                k_esquerda = true;
                break;
            case KeyEvent.VK_RIGHT:
                k_direita = true;
                break;
            case KeyEvent.VK_SPACE:
                k_espaco = true;
                break;
        }
    }

    public void teclaSolta(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                k_esquerda = false;
                break;
            case KeyEvent.VK_RIGHT:
                k_direita = false;
                break;
            case KeyEvent.VK_SPACE:
                k_espaco = false;
                break;
        }
    }

    public void atirar() {
        if (balas.size() < 5) {
            balas.add(new Bala(nave.obterX() + 20, nave.obterY()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jogoRodando && !venceu) {
            renderizar(g);
        } 
        else {
            if (!jogoRodando && !venceu) {
                g.setColor(Color.RED);
                g.drawString("Game Over", LARGURA_TELA / 2 - 50, ALTURA_TELA / 2);
            } 
            else {
                if (!jogoRodando && venceu) {
                    g.setColor(Color.GREEN);
                    g.drawString("VITÓRIA", LARGURA_TELA / 2 - 50, ALTURA_TELA / 2);
                } 
            }
        }
       
    }

    private void renderizar(Graphics g) {
        nave.desenhar(g);

        for (Bala bala : balas) {
            bala.desenhar(g);
        }

        for (Alien alien : aliens) {
            alien.desenhar(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Pontuação: " + pontuacao, 10, 20);
    }

}
