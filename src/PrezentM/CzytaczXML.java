package PrezentM;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import PrezentM.Model.Krawedz;
import javafx.util.Pair;

public class CzytaczXML {
	
	@XmlRootElement(name="mapa")
	@XmlAccessorType (XmlAccessType.NONE)
	public static class Mapa
	{
		private Granice granice;
		
		public void setGranice(Granice gran)
		{
			this.granice=gran;
		}

		@XmlElement(name="granice")
		public Granice getGranice()
		{
			return this.granice;
		}
	}
	
	@XmlRootElement(name="granice")
	public static class Granice
	{
		private List<Punkt> lista=new ArrayList<Punkt>();
		
		public Granice(){
		}
		
		public Granice(double x, double y)
		{
			lista.add(new Punkt(x,y));
		}
		
		public Granice(List<Punkt> l)
		{
			lista=l;
		}
		
		public void dodaj(Punkt p)
		{
			lista.add(p);
		}
		
		@XmlElements(@XmlElement(name="punkt"))
		public List<Punkt> getPunktyGraniczne(){
			return lista;
		}
		
		public void setPunktyGraniczne(List<Punkt> l)
		{
			lista=l;
		}
	}
	
	@XmlRootElement(name="punkt")
	public static class Punkt
	{
		private double x;
		private double y;
		
		public Punkt()
		{
			x=0;
			y=0;
		}
		
		public Punkt(double X, double Y)
		{
			this.x=X;this.y=Y;
		}
		
		@XmlAttribute(name="x")
		public double getX()
		{
			return x;
		}
		
		public void setX(double X)
		{
			x=X;
		}
		
		@XmlAttribute(name="y")
		public double getY()
		{
			return y;
		}
		
		public void setY(double Y)
		{
			y=Y;
		}
	}
	
	
	public static ArrayList<Krawedz> przeczytajWierzcholki(String sciezka) throws JAXBException
	{
		File file = new File(sciezka);
        JAXBContext jaxbContext = JAXBContext.newInstance(Mapa.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Mapa oMapie = (Mapa) jaxbUnmarshaller.unmarshal(file);		
		int ilePunktow=oMapie.getGranice().getPunktyGraniczne().size();
		ArrayList<Krawedz> listaKrawedzi=new ArrayList<Krawedz>(ilePunktow);
		Iterator<Punkt> it=oMapie.getGranice().getPunktyGraniczne().iterator();
		Punkt poprzedni=it.next();
		while(it.hasNext())
		{
			Punkt obecny=it.next();
			double x2=obecny.getX();
			double y2=obecny.getY();
			double x1=poprzedni.getX();
			double y1=poprzedni.getY();
			int typKrawedzi;
			double dlugosc=x2-x1;
			if(dlugosc==0)
			{
				dlugosc=y2-y1;
				if (dlugosc>0)
				{
					typKrawedzi=Krawedz.LEWO;
				}
				else
				{
					typKrawedzi=Krawedz.PRAWO;					
				}
			}
			else if (dlugosc>0)
			{
				typKrawedzi=Krawedz.DOL;
			}
			else
			{
				typKrawedzi=Krawedz.GORA;
			}
			listaKrawedzi.add(new Krawedz(poprzedni,typKrawedzi,Math.abs(dlugosc)));
			poprzedni=obecny;
		}
		listaKrawedzi.add(new Krawedz(poprzedni,Krawedz.GORA,Math.abs(listaKrawedzi.get(0).polozenie.getX()-
				poprzedni.getX())));
		return listaKrawedzi;		
	}
	
	public static void main(String args[]) throws JAXBException
	{
        File file = new File("bin/PrezentM/pryw/Mapy/Kob.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Mapa.class);
 
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Mapa postcodes = (Mapa) jaxbUnmarshaller.unmarshal(file);
 
        for (Punkt postCode : postcodes.getGranice().getPunktyGraniczne()) {
            System.out.println("Kod pocztowy: " + postCode.getX() + ", Miasto: " + postCode.getY());
        }		
	}
}
