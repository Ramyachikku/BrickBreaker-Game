package brickBreaker;

//public class Brickbreaker {
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;

	public class BrickBreaker extends JFrame implements ActionListener {
	    private final int WIDTH = 800;
	    private final int HEIGHT = 600;
	    private final int PADDLE_WIDTH = 100;
	    private final int PADDLE_HEIGHT = 20;
	    private final int BALL_DIAMETER = 20;
	    private final int BRICK_WIDTH = 70;
	    private final int BRICK_HEIGHT = 30;
	    private final int BRICK_ROWS = 5;
	    private final int BRICK_COLS = 10;
	    private final int DELAY = 10;

	    private Timer timer;
	    private boolean gameStarted = false;
	    private boolean ballMoving = false;
	    private int ballX, ballY, ballXSpeed, ballYSpeed;
	    private int paddleX, paddleY;
	    private int score = 0;
	    private boolean bricks[][];

	    public BrickBreaker() {
	        setTitle("Brick Breaker");
	        setSize(WIDTH, HEIGHT);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(false);
	        setLocationRelativeTo(null);

	        timer = new Timer(DELAY, this);

	        initializeGame();
	        addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameStarted) {
	                    gameStarted = true;
	                    ballMoving = true;
	                    timer.start();
	                }
	                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	                    if (paddleX > 0)
	                        paddleX -= 20;
	                }
	                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	                    if (paddleX < WIDTH - PADDLE_WIDTH)
	                        paddleX += 20;
	                }
	            }
	        });

	        setFocusable(true);
	    }

	    public void initializeGame() {
	        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
	        paddleY = HEIGHT - PADDLE_HEIGHT - 50;
	        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
	        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
	        ballXSpeed = 3;
	        ballYSpeed = 3;
	        bricks = new boolean[BRICK_ROWS][BRICK_COLS];
	        for (int i = 0; i < BRICK_ROWS; i++) {
	            for (int j = 0; j < BRICK_COLS; j++) {
	                bricks[i][j] = true;
	            }
	        }
	        score = 0;
	    }

	    public void checkCollisions() {
	        if (ballY >= HEIGHT - BALL_DIAMETER - PADDLE_HEIGHT && ballY <= HEIGHT - BALL_DIAMETER &&
	                ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
	            ballYSpeed = -ballYSpeed;
	        }

	        if (ballY <= 0) {
	            ballYSpeed = -ballYSpeed;
	        }

	        if (ballX >= WIDTH - BALL_DIAMETER || ballX <= 0) {
	            ballXSpeed = -ballXSpeed;
	        }

	        for (int i = 0; i < BRICK_ROWS; i++) {
	            for (int j = 0; j < BRICK_COLS; j++) {
	                if (bricks[i][j]) {
	                    int brickX = j * BRICK_WIDTH;
	                    int brickY = i * BRICK_HEIGHT;
	                    if (ballX + BALL_DIAMETER >= brickX && ballX <= brickX + BRICK_WIDTH &&
	                            ballY + BALL_DIAMETER >= brickY && ballY <= brickY + BRICK_HEIGHT) {
	                        ballYSpeed = -ballYSpeed;
	                        bricks[i][j] = false;
	                        score++;
	                        if (score == BRICK_ROWS * BRICK_COLS) {
	                            JOptionPane.showMessageDialog(this, "You Win!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	                            initializeGame();
	                        }
	                    }
	                }
	            }
	        }

	        if (ballY >= HEIGHT) {
	            JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
	            initializeGame();
	            gameStarted = false;
	            ballMoving = false;
	            timer.stop();
	        }
	    }

	    public void moveBall() {
	        ballX += ballXSpeed;
	        ballY += ballYSpeed;
	    }

	    public void paint(Graphics g) {
	        super.paint(g);
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, WIDTH, HEIGHT);

	        g.setColor(Color.WHITE);
	        g.fillRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

	        g.setColor(Color.YELLOW);
	        g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);

	        for (int i = 0; i < BRICK_ROWS; i++) {
	            for (int j = 0; j < BRICK_COLS; j++) {
	                if (bricks[i][j]) {
	                    g.setColor(Color.pink);
	                    g.fillRect(j * BRICK_WIDTH, i * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
	                }
	            }
	        }

	        g.setColor(Color.WHITE);
	        g.drawString("Score: " + score, 10, 20);

	        if (!gameStarted) {
	            g.setColor(Color.WHITE);
	            g.drawString("Press Space to Start", WIDTH / 2 - 70, HEIGHT / 2);
	        }
	    }

	    public void actionPerformed(ActionEvent e) {
	        if (ballMoving) {
	            moveBall();
	            checkCollisions();
	        }
	        repaint();
	    }

	    public static void main(String[] args) {
	        EventQueue.invokeLater(() -> {
	            BrickBreaker game = new BrickBreaker();
	            game.setVisible(true);
	        });
	    }
	}



