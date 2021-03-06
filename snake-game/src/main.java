

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

class Unit extends Rectangle{
	int x;
	int y;
	
	public Unit() {
		super(25,25);
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}	
}

class Apple extends Circle{
	int x = (int)(Math.random() * 30);
	int y = (int)(Math.random() * 30);
	
	public Apple() {
		super(12.5);
		super.setFill(Color.RED);
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class main extends Application{
	
	final int WINDOW_WIDTH = 30*26, WINDOW_HEIGHT = 30*26;
	static ArrayList<Unit> snake = new ArrayList<>();
	static GridPane pane = new GridPane();
	static Timeline animation;
	static BoxDir dir = BoxDir.RIGHT;
	static Apple apple = new Apple();
	static int score = 0;
	
	enum BoxDir {
		UP, DOWN ,RIGHT, LEFT
	}
	
	public static void paintGrid() {
		for ( int i = 0 ; i < 30; i++) {
			for ( int j = 0 ; j < 30; j++) {
				Rectangle rectangle = new Rectangle(25,25);
				rectangle.setFill(Color.BLACK);
				rectangle.setStroke(Color.BLACK);
				pane.add(rectangle, i, j);
			}
		}
	}
	
	public static void GameOver() {
		
	}

	public static void move() {
		pane.getChildren().clear();
		paintGrid();
		pane.add(new Text(String.valueOf(score)), 0, 0);
		
		pane.add(apple, apple.x, apple.y);
		if(snake.get(0).x == apple.x && snake.get(0).y == apple.y) {
			apple.setLoc((int)(Math.random() * 30),(int)(Math.random() * 30));
			snake.add(new Unit());
			animation.setRate(animation.getRate() + 0.1);
			score++;
		}

		switch(dir) {
		case UP:
				if (snake.get(0).y == 0) {
					snake.get(0).setLoc(snake.get(0).x, 30);
				};
				pane.add(snake.get(0), snake.get(0).x, --snake.get(0).y);
				break;
		case DOWN:
				pane.add(snake.get(0), snake.get(0).x, ++snake.get(0).y);
				if (snake.get(0).y == 30) {
					snake.get(0).setLoc(snake.get(0).x, 0);
				};
				break;
			
		case RIGHT:
				pane.add(snake.get(0), ++snake.get(0).x, snake.get(0).y);
				if (snake.get(0).x == 30) {
					snake.get(0).setLoc(0, snake.get(0).y);
				};
				break;
			
		case LEFT:
				if (snake.get(0).x == 0) {
					snake.get(0).setLoc(30, snake.get(0).y);
				};
				pane.add(snake.get(0), --snake.get(0).x, snake.get(0).y); 
				break;
			
		}
		for(int i = 1; i < snake.size(); i++) {
			if(snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				animation.stop();
			}
		}

		for(int i = snake.size() - 1 ; i >= 1; i--) {
			snake.get(i).setLoc(snake.get(i - 1).x, snake.get(i - 1).y);
			pane.add(snake.get(i), snake.get(i).x, snake.get(i).y);
		}
	}
	
	@Override
	public void start(Stage window){
		paintGrid();
	
		Unit head = new Unit();
		head.setLoc(15, 15);
		head.setFill(Color.YELLOW);
		snake.add(head);
		pane.add(head, head.x, head.y);
		
		snake.add(new Unit());
		snake.add(new Unit());
		snake.add(new Unit());
		snake.add(new Unit());
		snake.add(new Unit());
		
		animation = new Timeline(new KeyFrame(Duration.millis(150), e -> move()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		snake.get(0).setOnKeyPressed(key -> {
			if(key.getCode() == KeyCode.W && dir != BoxDir.DOWN) {
				dir = BoxDir.UP;
			}
			if(key.getCode() == KeyCode.S && dir != BoxDir.UP) {
				dir = BoxDir.DOWN;
			}
			if(key.getCode() == KeyCode.D && dir != BoxDir.LEFT) {
				dir = BoxDir.RIGHT;
			}
			if(key.getCode() == KeyCode.A && dir != BoxDir.RIGHT) {
				dir = BoxDir.LEFT;
			}

		});
		Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setScene(scene);
		window.setTitle("Snake");
		window.show();
		
		snake.get(0).requestFocus();
	}
	

	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	

}
