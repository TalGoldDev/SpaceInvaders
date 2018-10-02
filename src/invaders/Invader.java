package invaders;

import biuoop.DrawSurface;
import game.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;
import geometry.Point;
import geometry.Rectangle;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Invader extends Block {

    /**
     * a function which returns the aliens x in the matrix.
     * @return - an integer.
     */
    public int getInvaderPostitionX() {
        return invaderPostitionX;
    }

    /**
     * a function which returns the aliens y in the matrix.
     * @return - an integer.
     */
    public int getInvaderPositionY() {
        return invaderPositionY;
    }

    /**
     * a function which sets the aliens x position in the matrix.
     * @param postitionX - an integer.
     */
    public void setInvaderPostitionX(int postitionX) {
        this.invaderPostitionX = postitionX;
    }

    /**
     * a function which sets the aliens y in the matrix.
     * @param positionY - an integer.
     */
    public void setInvaderPositionY(int positionY) {
        this.invaderPositionY = positionY;
    }

    private int invaderPostitionX, invaderPositionY;
    private Swarm spaceShip;
    private List<Invader> invaderList;

    /**
     * a function which sets the invaders column.
     * @param invaderColumn - the invader list.
     */
    public void setInvaderList(List<Invader> invaderColumn) {
        this.invaderList = invaderColumn;
    }

    /**
     * a constructor of the invader.
     * @param r - a rectangle.
     * @param c - string colour.
     * @param n - integer.
     * @param s - colour.
     * @param h - hash map.
     */
    public Invader(Rectangle r, String c, int n, Color s, HashMap<Integer, String> h) {
        super(r, c, n, s, h);
    }

    /**
     * the main constructor method of the alien.
     * @param upperLeft - upper left point.
     * @param width - aliens width.
     * @param height - aliens height.
     * @param hp - aliens hp.
     * @param ship - the swarm the alien is created in.
     */
    public Invader(geometry.Point upperLeft, double width, double height, int hp, Swarm ship) {
        super(upperLeft, width, height, hp);
        spaceShip = ship;
    }


    /**
     * a function which moves the invader.
     * @param dx - x value to move.
     * @param dy - y value to move.
     */
    public void moveInvader(int dx, int dy) {
        double x = this.getCollisionRectangle().getUpperLeft().getX() + dx;
        double y = this.getCollisionRectangle().getUpperLeft().getY() + dy;
        double width = this.getCollisionRectangle().getWidth();
        double height = this.getCollisionRectangle().getHeight();

        Rectangle rec = new Rectangle(new Point(x, y), width, height);
        this.updateCollisionRectangle(rec);
    }


    /**
     * a function which returns the score of destroying te alien.
     * @return - an integer.
     */
    public int getScoreWorth() {
        return 100;
    }

    /**
     * the drawing function which draws the alien to the surface.
     * @param surface - the inputed surface we want to draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        Image image = null;
        try {
            File pathToFile = new File("resources/block_images/enemy.png");
            image = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        surface.drawImage((int) getCollisionRectangle().getUpperLeft().getX(),
                (int) getCollisionRectangle().getUpperLeft().getY(), image);

    }

    /**
     * a function which notifies the game when the alien gets hit.
     * @param hitter - the ball the collision happened with.
     */
    public void notifyHit(Ball hitter) {
        super.notifyHit(hitter);
        this.removeHitListener(this.spaceShip.getBallRemoverListener());
        this.removeHitListener(this.spaceShip.getBlockRemoverListener());
        this.removeHitListener(this.spaceShip.getScoreListener());

        this.removeFromMatrix();
        spaceShip.refreshCollectiveBounds();
    }

    /**
     * a function which removes the alien from the matrix and from the game.
     */
    private void removeFromMatrix() {
        //List<List<Invader>> enemiesMatrix = this.spaceShip.getEnemiesMatrix();
        //List<Invader> invaderList = enemiesMatrix.get(this.getInvaderPostitionX());

        invaderList.remove(this);
        removeFromGame(spaceShip.getGame());
    }

    /**
     * a function which removes the alien from the game.
     * @param gameLevel - the gameLevel we want to remove this object from.
     */
    public void removeFromGame(GameLevel gameLevel) {
                gameLevel.removeSprite(this);
                gameLevel.removeCollidable(this);

    }

}
