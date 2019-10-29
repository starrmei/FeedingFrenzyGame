package FeedingFrenzy;

import javafx.animation.KeyFrame;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class EnemyPane extends Pane {
	Circle enemy = new Circle();
	public int enemyMove, enemyR;
	protected Timeline animation;
	
	public EnemyPane() {
		setMinWidth(850);
		setMinHeight(700);
		setMaxWidth(850);
		setMaxHeight(700);
		
		getEnemy();
		getChildren().add(enemy);
		
		animation = new Timeline(
		new KeyFrame(Duration.millis(500), e -> moveEnemy()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}
	
	public void getEnemy() {
		enemyMove = 1 + (int)(Math.random() * (2)); // 1 = horizontal, 2 = vertical
		if (enemyMove == 1) {
			enemy.setRadius(4 + (int) Math.ceil(Math.random() * 50));
			enemy.setCenterY((int) Math.ceil(Math.random() * 701));
			enemy.setCenterX(0 - enemy.getRadius());
		}
		else if (enemyMove == 2) {
			enemy.setRadius(4 + (int) Math.ceil(Math.random() * 50));
			enemy.setCenterX((int) Math.ceil(Math.random() * 851)); 
			enemy.setCenterY(0 - enemy.getRadius());
		}
		
	}
	
	public double getEnemyRadius() { return enemy.getRadius(); }
	
	public double getEnemyX() {return enemy.getCenterX(); }
	
	public double getEnemyY() { return enemy.getCenterY(); }
	
	public int getEnemyMove() { return enemyMove; }
	
	public void setEnemyY(double newY) {
		enemy.setCenterY(newY);
	}
	
	public void setEnemyX(double newX) {
		enemy.setCenterX(newX);
	}
	
	protected void moveEnemy() {
		double enemyX, enemyY;
		if (getEnemyMove() == 1) {
			animation.setRate(0.5 + Math.ceil(Math.random() * 8));
			enemyX = enemy.getCenterX();
			enemy.setCenterX(enemyX += animation.getRate());
		}
		if (getEnemyMove() == 2) {
			animation.setRate(0.5 + Math.ceil(Math.random() * 8));
			enemyY = enemy.getCenterY();
			enemy.setCenterY(enemyY += animation.getRate());
		}
	}

}
