package PrezentM;
	
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import PrezentM.Model.Krawedz;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class Widok extends Application {
	int stan=0;
	public static ImageView mapa;
	private static Image Kob;
	private static Image Kar;
	public static ArrayList<Krawedz> ksztaltMapy;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("OknoGlowne.css").toExternalForm());
			primaryStage.setFullScreen(true);
			//Iterator<Screen> it=Screen.getScreens().listIterator();
			zaladujMapy();
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
			wczytajMape(scene,"Kar");
			root.setTop(mapa);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void zaladujMapy()
	{
		Kob=new Image(getClass().getResourceAsStream("pryw/Mapy/Kob.png"));
		Kar=new Image(getClass().getResourceAsStream("pryw/Mapy/Kar.png"));		
	}
	
	private static void wczytajMape(Scene scena, String jakaMapa) throws JAXBException
	{
		ustalKsztaltMapy(scena, "bin/PrezentM/pryw/Mapy/"+jakaMapa+".xml");	
		switch(jakaMapa)
		{
			case "Kob":
				mapa=new ImageView(Kob);
			break;
			case "Kar":
				mapa=new ImageView(Kar);
			break;
		}
	}
	
	private static void ustalKsztaltMapy(Scene scena, String sciezkaMapy) throws JAXBException
	{
		ksztaltMapy=CzytaczXML.przeczytajWierzcholki(sciezkaMapy);
	}
	
	public static void wczytajMape(String sciezka)
	{
		//TODO
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
