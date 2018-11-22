package PrezentM;
	
import java.util.LinkedList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class Widok extends Application {
	int stan=0;
	public static ImageView mapa;
	private Image Kob;
	private Image Kar;
	public static LinkedList<Pair<Double,Double>> ksztaltMapy;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("OknoGlowne.css").toExternalForm());
			primaryStage.setFullScreen(true);
			//Iterator<Screen> it=Screen.getScreens().listIterator();
			Kob=new Image(getClass().getResourceAsStream("pryw/Mapy/Kob.png"));
			mapa=new ImageView(Kob);
			Kar=new Image(getClass().getResourceAsStream("pryw/Mapy/Kar 3828x4395.png"));
			root.setTop(mapa);
			primaryStage.setScene(scene);
			scene.setOnMouseMoved((wyd)->{
				Model.przesunMapeMysza(wyd.getX(),wyd.getY());
			});
			scene.setOnKeyPressed((wyd)->{
				Model.przesunMapeStrzalkami(wyd.getCode().toString());
			});
			/*scene.setOnKeyReleased((wyd)->{
				System.out.println(wyd.getCode());
			});*/
			scene.setOnMouseEntered((wyd)->{Model.kursorPoza=false;});
			scene.setOnMouseExited((wyd)->{Model.kursorPoza=true;});
			primaryStage.show();
			ustalKsztaltMapy(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void ustalKsztaltMapy(Scene scena)
	{
		//TODO XML
		double wys=scena.getWindow().getHeight();
		double szer=scena.getWindow().getWidth();
		ksztaltMapy=new LinkedList<Pair<Double,Double>>();
		Pair<Double,Double> punkt=new Pair<Double,Double>(0.0,0.0);
		ksztaltMapy.add(punkt);
		punkt=new Pair<Double,Double>(0.0,wys-1);
		ksztaltMapy.add(punkt);
		punkt=new Pair<Double,Double>(szer-1,wys-1);
		ksztaltMapy.add(punkt);
		punkt=new Pair<Double,Double>(szer-1,0.0);
		ksztaltMapy.add(punkt);
	}
	
	public static void wczytajMape(String sciezka)
	{
		//TODO
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
