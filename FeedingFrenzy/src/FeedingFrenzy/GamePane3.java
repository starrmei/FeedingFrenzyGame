package FeedingFrenzy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class GamePane3 extends Pane {
	public double playerRadius = 10, ogPX = 300, ogPY = 200;
	private double playerX = 300, playerY = 200;
	private Circle player = new Circle(ogPX, ogPY, playerRadius);
	
	private double enemyRadius = 30, ogEX = 50, ogEY = 50;
	private double enemyX = enemyRadius, enemyY = enemyRadius;
	private double dx = 1, dy = 1;
	private Circle enemy = new Circle(ogEX, ogEY, enemyRadius);
	
	private Circle pellet = new Circle();
	
	private Timeline animation;
	private int startSpeed = 1;
	
	protected Label stats;
	private int score;
	private int width = 600;
	private int height = 400;
	
	public GamePane3() {
		score = 0;
		
	    setMinWidth(width);
	    setMinHeight(height);
	    setMaxWidth(width);
	    setMaxHeight(height);

	    player.setFill(Color.GREEN);
	    getChildren().add(player);
	    enemy.setFill(Color.RED);
	    getChildren().add(enemy);

	    animation = new Timeline(
	      new KeyFrame(Duration.millis(50), e -> moveEnemy()));
	    animation.setCycleCount(Timeline.INDEFINITE);
	    animation.setRate(startSpeed);
	    animation.play(); 
	    
	    stats = new Label();
	    stats.setFont(new Font("Arial", 20));
	    stats.setText("Score: " + score + "\t\tSpeed: " + animation.getRate());	
	}
	
	//Accessors
	public void play() { animation.play(); }
	public void pause() { animation.pause(); }
	
	public Label getStats() { return stats; }
	public double getAnimationSpeed() { return animation.getRate(); }
	public int getScore() { return score; }
	public int getPaneWidth() { return width; }
	public int getPaneHeight() { return height; }
	
	//Mutators
	public void setPlayerRadius(int newR) {
		player.setRadius(newR);
		playerRadius = newR;
	}
	
	public void setEnemyRadius(int newR) {
		enemy.setRadius(newR);
		enemyRadius = newR;
	}
	
	public void setSpeed(int newSpd) {
		animation.setRate(newSpd);
		startSpeed = 1;
	}
	
	public void setHeight(int newH) {
		setMinHeight(newH);
		setMaxHeight(newH);
		height = newH;
	}
	
	public void setWidth(int newW) {
		setMinWidth(newW);
		setMaxWidth(newW);
		width = newW;
	}
	
	//Player movement
 	public void moveUp() {
		if ((playerY + playerRadius) >= (15 + playerRadius)) {
			playerY = playerY - 10;
			player.setCenterY(playerY);
		}
		else if ((playerY + playerRadius) < (15 + playerRadius)) {
			playerY = 0 + playerRadius;
			player.setCenterY(playerY);
		}
		if ((playerY - playerRadius) < 0) {
			player.setCenterY(0 + playerRadius);
		}
	}
			  
	public void moveDown() {
		if ((playerY + playerRadius) <= height) {
			playerY = playerY + 10;
			player.setCenterY(playerY);
		}
		else if ((playerY + playerRadius) > height) {
			playerY = height - playerRadius;
			player.setCenterY(playerY);
		}
		if ((playerY + playerRadius) > height) {
			player.setCenterY(height - playerRadius);
		}
	}
			  
	public void moveLeft() {
		if ((playerX + playerRadius) >= (15 + playerRadius)) {
			playerX = playerX - 10;
			player.setCenterX(playerX);
		}
		else if ((playerX + playerRadius) < (15 + playerRadius)) {
			playerX = 0 + playerRadius;
			player.setCenterX(playerX);
		}
		if ((playerX - playerRadius) < 0) {
			player.setCenterX(0 + playerRadius);
		}
	}
			  
	public void moveRight() {
		if ((playerX + playerRadius) <= width) {
			playerX = playerX + 10;
			player.setCenterX(playerX);
		}
		else if ((playerX + playerRadius) > width) {
			playerX = width - playerRadius;
			player.setCenterX(playerX);
		}
		if ((playerX + playerRadius) > width) {
			player.setCenterX(width - playerRadius);
		}
	}
	
	//Enemy
	protected void moveEnemy() {
		if (getEnemyDist() > playerRadius + enemyRadius) {
			if (enemyX < enemyRadius) {
				dx *= -1;
			}
			else if (enemyX > width - enemyRadius) {
				dx *= -1;
			}
			else if (enemyY < enemyRadius) {
			  	dy *= -1;
			}
			else if (enemyY > height - enemyRadius) {
				dy *= -1;
		    }
			// Adjust position
			enemyX += dx;
			enemyY += dy;
			enemy.setCenterX(enemyX);
			enemy.setCenterY(enemyY);
			    
			stats.setFont(new Font("Arial", 20));
			stats.setText("Score: " + score + "\t\tSpeed: " + animation.getRate());
		}
		else if (getEnemyDist() <= playerRadius + enemyRadius) {
			gameOver();
			end();
		}
		
	    if (getPelletDist() <= playerRadius + pellet.getRadius()) {
	    	removePellet();
	    	addPellet();
	    	score += 25;
	    	animation.setRate(animation.getRate() + 1);
	    }
	}
	
	//Food pellets
	public void addPellet() {
		pellet.setRadius(5);
		pellet.setCenterY((int) Math.ceil(Math.random() * (height - 10)) + 5);
		pellet.setCenterX((int) Math.ceil(Math.random() * (width - 10)) + 5);
		pellet.setFill(Color.DEEPSKYBLUE);
		getChildren().add(pellet);
	}
	
	public void removePellet() {
		getChildren().remove(pellet);
	}

	//Collision
	public double getEnemyDist() {
		double distance = Math.sqrt(Math.pow((player.getCenterX() - enemy.getCenterX()), 2) + Math.pow((player.getCenterY() - enemy.getCenterY()), 2));
		return distance;
	}
	
	public double getPelletDist() {
		double distance = Math.sqrt(Math.pow((player.getCenterX() - pellet.getCenterX()), 2) + Math.pow((player.getCenterY() - pellet.getCenterY()), 2));
		return distance;
	}
	
	public void gameOver() {
		animation.stop();
		getChildren().remove(player);
	}
	
	public void end() {
		Alert alEnd = new Alert(AlertType.INFORMATION);
		alEnd.setTitle("Feeding Frenzy");
	  	alEnd.setHeaderText("Game Over");
	    alEnd.setContentText("You've Been Eaten! \nScore: " + getScore() + "\tSpeed: " + getAnimationSpeed() + "\n\nPress OK then SPACE to restart");
	    alEnd.show();
	}
	
	public void restart() {
		animation.stop();
		getChildren().remove(enemy);
		getChildren().remove(player);
		enemy.setCenterX(ogEX);
		enemyX = ogEX;
		enemy.setCenterY(ogEY);
		enemyY = ogEY;
		enemy.setRadius(enemyRadius);
		
		player.setCenterX(ogPX);
		playerX = ogPX;
		player.setCenterY(ogPY);
		playerY = ogPY;
		player.setRadius(playerRadius);
		
		getChildren().add(enemy);
		getChildren().add(player);
		
		removePellet();
		addPellet();
		
		score = 0;
		animation.setRate(startSpeed);
		animation.play();
	}

	
}