package PrezentM;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.stage.Screen;
import javafx.util.Pair;

public class Model {

	public static boolean demonMyszy=false;
	public static boolean demonStrzalek=false;
	public static boolean kursorPoza=false;
	public static double xMyszy;
	public static double yMyszy;
	public static Object blokada=new Object();
	public static boolean prezentacja=false;
	
	public static class Krawedz
	{
		public CzytaczXML.Punkt polozenie;
		public int typ;
		public double dlugosc;
		public final static int DOL=2;
		public final static int LEWO=4;
		public final static int PRAWO=6;
		public final static int GORA=8;
		
		public Krawedz(CzytaczXML.Punkt para, int t, double dlug)
		{
			this.polozenie=para;
			this.typ=t;
			this.dlugosc=dlug;
		}
	}
	public static void przesunMapeMysza(double X, double Y) {
		xMyszy=X;
		yMyszy=Y;
		synchronized(blokada){
			if(!demonMyszy)
			{
				demonMyszy=true;
				Thread demonMyszy=new Thread(()->{
					int predkosc=3;
					while(true)
					{
						//System.out.println(kursorPoza);TODO jakieś błędy
						if(!(kursorPoza||prezentacja))
						{
							if (xMyszy<10)
								if (yMyszy<10)
									przesunMape(7,predkosc);
								else if(yMyszy>Screen.getPrimary().getVisualBounds().getHeight()-10)//TODO dla wielu monitorów
									przesunMape(1,predkosc);
								else
									przesunMape(4,predkosc);
							else if (xMyszy>Screen.getPrimary().getVisualBounds().getWidth()-10)//TODO dla wielu monitorów
								if (yMyszy<10)
									przesunMape(9,predkosc);
								else if(yMyszy>Screen.getPrimary().getVisualBounds().getHeight()-10)//TODO dla wielu monitorów
									przesunMape(3,predkosc);
								else
									przesunMape(6,predkosc);
							else if (yMyszy<10)
								przesunMape(8,predkosc);
							else if (yMyszy>Screen.getPrimary().getVisualBounds().getHeight()-10)//TODO dla wielu monitorów
								przesunMape(2,predkosc);
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
							}
						}
					}
				});
				demonMyszy.setDaemon(true);
				demonMyszy.start();
			}
		}
	}
	
	public static void przesunMapeStrzalkami(String klawisz)
	{//TODO
		if(!prezentacja)
		{
			int predkosc=15;
			switch (klawisz)
			{
				case "LEFT":
					przesunMape(4,predkosc);
				break;
				case "RIGHT":
					przesunMape(6,predkosc);
				break;
				case "UP":
					przesunMape(8,predkosc);
				break;
				case "DOWN":
					przesunMape(2,predkosc);
				break;
			}
		}
	}
	
	private static void przesunWLewo(int predkosc)
	{
		if(mozna(4))
			Widok.mapa.setTranslateX(Widok.mapa.getTranslateX()+predkosc);
	}
	private static void przesunWPrawo(int predkosc)
	{
		if(mozna(6))
			Widok.mapa.setTranslateX(Widok.mapa.getTranslateX()-predkosc);
	}
	private static void przesunWGore(int predkosc)
	{
		if(mozna(8))
			Widok.mapa.setTranslateY(Widok.mapa.getTranslateY()+predkosc);
	}
	private static void przesunWDol(int predkosc)
	{
		if(mozna(2))
			Widok.mapa.setTranslateY(Widok.mapa.getTranslateY()-predkosc);
	}
	
	private static boolean mozna(int kierunek) {
		// TODO Auto-generated method stub
		double polozenieX=Widok.mapa.getTranslateX()*(-1);
		double polozenieY=Widok.mapa.getTranslateY()*(-1);
		double szerEkranu=Screen.getPrimary().getVisualBounds().getWidth();
		double wysEkranu=Screen.getPrimary().getVisualBounds().getHeight();//TODO wiele monitorów
		boolean zwrot=true;
		
		ArrayList<Krawedz> listaKrawedzi=Widok.ksztaltMapy;
		
		

		Iterator<Krawedz> iter=listaKrawedzi.iterator();
		switch(kierunek)
		{
			case Krawedz.LEWO:
				iter=listaKrawedzi.iterator();
				while(iter.hasNext())
				{
					Krawedz k=iter.next();
					if(k.typ==kierunek)
					{
						double xWektora=polozenieX-k.polozenie.getX();
						double yWektora=polozenieY-k.polozenie.getY();
						if(xWektora<=0)
							if((yWektora+wysEkranu>0&&yWektora+wysEkranu<k.dlugosc)||
									(yWektora>0&&yWektora<k.dlugosc))
								zwrot=false;
					}
				}
			break;
			case Krawedz.DOL:
				iter=listaKrawedzi.iterator();
				while(iter.hasNext())
				{
					Krawedz k=iter.next();
					if(k.typ==kierunek)
					{
						double xWektora=polozenieX-k.polozenie.getX();
						double yWektora=polozenieY+wysEkranu-k.polozenie.getY();
						if(yWektora>=0)
							if((xWektora<k.dlugosc&&xWektora>0)||
									(xWektora+szerEkranu>0&&xWektora+szerEkranu<k.dlugosc))
								zwrot=false;
					}
				}
			break;
			case Krawedz.PRAWO:
				iter=listaKrawedzi.iterator();
				while(iter.hasNext())
				{
					Krawedz k=iter.next();
					if(k.typ==kierunek)
					{
						double xWektora=polozenieX+szerEkranu-k.polozenie.getX();
						double yWektora=polozenieY+wysEkranu-k.polozenie.getY();
						if(xWektora>=0)
							if((yWektora-wysEkranu<0&&yWektora-wysEkranu>k.dlugosc*(-1))||
									(yWektora<0&&yWektora>k.dlugosc*(-1)))
								zwrot=false;
					}
				}
			break;
			case Krawedz.GORA:
				iter=listaKrawedzi.iterator();
				while(iter.hasNext())
				{
					Krawedz k=iter.next();
					if(k.typ==kierunek)
					{
						double xWektora=polozenieX+szerEkranu-k.polozenie.getX();
						double yWektora=polozenieY-k.polozenie.getY();
						if(yWektora<=0)
							if((xWektora>k.dlugosc*(-1)&&xWektora<0)||
									(xWektora-szerEkranu<0&&xWektora-szerEkranu>k.dlugosc*(-1)))
								zwrot=false;
					}
				}
			break;
		}
		return zwrot;
	}

	private static void przesunMape(int kierunek, int predkosc)
	{
		//TODO
		switch (kierunek)
		{
		case 1:
			przesunWLewo(predkosc);
			przesunWDol(predkosc);
		break;
		case 2:
			przesunWDol(predkosc);
		break;
		case 3:
			przesunWPrawo(predkosc);
			przesunWDol(predkosc);
		break;
		case 4:
			przesunWLewo(predkosc);
		break;
		case 6:
			przesunWPrawo(predkosc);
		break;
		case 7:
			przesunWLewo(predkosc);
			przesunWGore(predkosc);
		break;
		case 8:
			przesunWGore(predkosc);
		break;
		case 9:
			przesunWPrawo(predkosc);
			przesunWGore(predkosc);
		break;
		}
	}
}
