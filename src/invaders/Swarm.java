package invaders;

import game.GameLevel;
import gameobjects.Ball;
import gameobjects.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Swarm {

    private GameLevel game;

    /**
     * a function which returns the blockRemover listener.
     * @return - blockremover.
     */
    public BlockRemover getBlockRemoverListener() {
        return blockRemoverListener;
    }

    /**
     * a function which returns the ballremover listener.
     * @return - the ball remover.
     */
    public BallRemover getBallRemoverListener() {
        return ballRemoverListener;
    }

    /**
     * a function which returns the scorelistener.
     * @return - the score listener.
     */
    public ScoreTrackingListener getScoreListener() {
        return scoreListener;
    }

    private BlockRemover blockRemoverListener;
    private BallRemover ballRemoverListener;
    private ScoreTrackingListener scoreListener;
    private List<Invader> invaderList;
    private double shoot = 0;
    private GameEnvironment environment;

    /**
     * a function which returns the invaders Matrix.
     * @return - a list of invaders list.
     */
    public List<List<Invader>> getEnemiesMatrix() {
        return enemiesMatrix;
    }

    private List<List<Invader>> enemiesMatrix;
    private double leftMostX;
    private double rightMostX;
    private double bottomMostY;
    private double topMostY;
    private int minXValue = 30;
    private int maxXValue = 730;
    private int maxYValue = 485;
    private int originalSpeedX;

    /**
     * a function to get the speed of the aliens swarm.
     * @return - the current speed.
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * a function which sets the aliens swarm speed.
     * @param speed - an integer.
     */
    public void setSpeedX(int speed) {
        this.speedX = speed;
    }

    /**
     * the amount of speed to add to the swarm.
     * @param speed - an integer.
     */
    public void setAddedSpeed(int speed) {
        this.addedSpeed = speed;
        this.speedX += addedSpeed;
    }

    private int addedSpeed = 0;
    private int speedX = 70;
    private int yChange = 30;

    /**
     * the constructor method which creates the swarm.
     * @param game - the gamelevel.
     * @param blockRemoverListener - block remove listener.
     * @param ballRemoverListener - ball remove listener.
     * @param scoreListener - score remove listener.
     * @param gameEnv - the game environment.
     * @param speed - aliens swarm speed.
     */
    public Swarm(GameLevel game, BlockRemover blockRemoverListener, BallRemover ballRemoverListener,
                 ScoreTrackingListener scoreListener, GameEnvironment gameEnv, int speed) {
        this.game = game;
        this.blockRemoverListener = blockRemoverListener;
        this.ballRemoverListener = ballRemoverListener;
        this.scoreListener = scoreListener;
        this.environment = gameEnv;
        invaderList = new LinkedList<Invader>();
        createInvaders();
        this.speedX = speed;
    }

    /**
     * a function which returns the game level.
     * @return - the gamelevel.
     */
    public GameLevel getGame() {
        return game;
    }


    /**
     * a function which creates and initializes the invaders matrix.
     */
    private void createInvaders() {
        enemiesMatrix = new ArrayList<List<Invader>>();
        for (int i = 0; i < 10; i++) {
            List<Invader> enemyColumn = new LinkedList<Invader>();
            for (int j = 0; j < 5; j++) {
                Invader darthVader = new Invader(new Point(40 + 50 * i, 30 + j * 40), 40, 30, 1, this);
                darthVader.setInvaderPositionY(j);
                darthVader.setInvaderPostitionX(i);

                darthVader.addToGame(game);
                darthVader.addHitListener(blockRemoverListener);
                darthVader.addHitListener(ballRemoverListener);
                darthVader.addHitListener(scoreListener);
                enemyColumn.add(darthVader);
                darthVader.setInvaderList(enemyColumn);
            }
            enemiesMatrix.add(enemyColumn);
        }


        refreshCollectiveBounds();
    }


    /**
     * adding the block object to the game.
     * @param object - the current game
     */
    public void addToGame(GameLevel object) {
        for (List<Invader> a : enemiesMatrix) {
            for (Invader in : a) {
                object.addCollidable(in);
                object.addSprite(in);
            }
        }
    }

    /**
     * a function which removes the entre swarm from the game.
     * @param gameLevel - the gamelevel.
     */
    public void removeFromGame(GameLevel gameLevel) {
        for (List<Invader> a : enemiesMatrix) {
            for (Invader in : a) {
                gameLevel.removeSprite(in);
                gameLevel.removeCollidable(in);

            }
        }
    }


    /**
     * a function which moves the invaders inside the swarm.
     * @param dx - the x to move.
     * @param dy - the y to move.
     */
    public void moveInvaders(int dx, int dy) {
        for (List<Invader> a : enemiesMatrix) {
            for (Invader in : a) {
                in.moveInvader(dx, dy);
            }
        }
    }

    /**
     * a function which updates the swarm bounds.
     */
    public void refreshCollectiveBounds() {
        int size = 0;

        clearMatrix();

        List<List<Invader>> copyCat = new ArrayList<List<Invader>>(enemiesMatrix);

        List<Invader> largestCol = copyCat.get(0);

        for (int i = 0; i < copyCat.size(); i++) {
            List<Invader> a = copyCat.get(i);
            if (size < a.size()) {
                largestCol = copyCat.get(i);
                size = a.size();
            }
        }
        Rectangle colRect = largestCol.get(size - 1).getCollisionRectangle();
        bottomMostY = colRect.getUpperLeft().getY() + colRect.getHeight();
        topMostY = largestCol.get(0).getCollisionRectangle().getUpperLeft().getY();

        if (copyCat.get(0).size() != 0) {
        leftMostX = copyCat.get(0).get(0).getCollisionRectangle().getUpperLeft().getX();
        }
        if (copyCat.get(copyCat.size() - 1).size() != 0) {
        rightMostX = copyCat.get(copyCat.size() - 1).get(0).getCollisionRectangle().getUpperLeft().getX();
        }
    }

    /**
     * a function which clears the swarm matrix from empty columns.
     */
    private void clearMatrix() {
        for (int i = 0; i < enemiesMatrix.size(); i++) {
            if (enemiesMatrix.get(i).isEmpty()) {
                enemiesMatrix.remove(i);
            }
        }
    }


    /**
     * the timePassed method.
     * @param dt - the time passed since last frame.
     */
    public void timePassed(double dt) {
        this.shoot -= dt;


        int dx = (int) (dt * speedX);
        int dy = 0;

        boolean reachedXBound = false;
        if (dx > 0 && rightMostX + dx > maxXValue) {
            reachedXBound = true;
        } else if (leftMostX + dx < minXValue) {
            reachedXBound = true;
        }


        if (reachedXBound) {
            speedX = (-speedX);
            speedX = ((int) (speedX * 1.1D));
            dx = 0;
            dy = yChange;

            if (bottomMostY + dy > maxYValue) {
                game.setAliensWon();
            }

            bottomMostY += dy;
        } else {
            rightMostX += dx;
            leftMostX += dx;
        }

        for (List<Invader> enemyColumn : enemiesMatrix) {
            for (Invader enemy : enemyColumn) {
                enemy.moveInvader(dx, dy);
            }
        }
        if (this.shoot < 0.0D) {
            if (enemiesMatrix.size() > 0) {
                shootFromColumn(randomColumnToShoot());
            }
        }

        clearMatrix();
    }

    /**
     * a function which resets the position of the invaders swarm.
     */
    public void resetPostition() {

        int dx = (int) (30.0D - leftMostX);
        int dy = (int) (30.0D - topMostY);

        for (List<Invader> enemyColumn : enemiesMatrix) {
            for (Invader enemy : enemyColumn) {
                enemy.moveInvader(dx, dy);
            }
        }
        refreshCollectiveBounds();
        speedX = 70 + addedSpeed;
    }

    /**
     * generates a random column number to shoot.
     * @return - an integer.
     */
    public int randomColumnToShoot() {
        Random rand = new Random();
        int  n = rand.nextInt(enemiesMatrix.size());
        return n;
    }

    /**
     * a function which shoots from a specific column.
     * @param colNum - the column number.
     */
    public void shootFromColumn(int colNum) {
        this.shoot = 0.6D;
        List<Invader> choosenColumn = this.enemiesMatrix.get(colNum);
        double creationPointX = choosenColumn.get(choosenColumn.size() - 1)
                .getCollisionRectangle().getUpperLeft().getX() + 20;
        double creationPointY = choosenColumn.get(choosenColumn.size() - 1)
                .getCollisionRectangle().getUpperLeft().getY() + 40;
        Ball ball = new Ball(creationPointX, creationPointY,
                3, Color.red);
        ball.setVelocity(new Velocity(90, 500));
        ball.addToGame(game);
        ball.setGameEnv(environment);
        didWeWin();
    }


    /**
     * a functiion which checks if the player won against the current swarm.
     * @return - a boolean.
     */
    public boolean didWeWin() {
        if (!enemiesMatrix.isEmpty()) {
            refreshCollectiveBounds();
        }
        if (enemiesMatrix.isEmpty()) {
            return true;
        }

        return false;
    }
}
