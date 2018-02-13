import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;

public class Hotspot {

	public static void main(String[] args) {
		List<GdanskData> hotSpot = readFromCSV("wifigdansk.csv");
		Collections.sort(hotSpot, new Comparator<GdanskData>() 
		{
			public int compare(GdanskData gd1, GdanskData gd2)
			{
				return Double.valueOf(gd1.getxCoordinate()).compareTo(gd2.getxCoordinate());
			}
		});
		
		List<GdanskData> zone1 = hotSpot.subList(0, 25);
		List<GdanskData> zone2 = hotSpot.subList(25,60);
		List<GdanskData> zone3 = hotSpot.subList(60, 100);
		
		System.out.println("ZONE 1 REGIONS");
		for(GdanskData gd : zone1) {
			System.out.println( gd.getID() + "," +  gd.getLocation());
				}
		
		System.out.println("ZONE 2 REGIONS");
		for(GdanskData gd : zone2) {
			System.out.println( gd.getID() + "," +  gd.getLocation());
				}
		
		System.out.println("ZONE 3 REGIONS");
		for(GdanskData gd : zone3) {
			System.out.println( gd.getID() + "," +  gd.getLocation());
		}
		
		double y[] = {zone1.size(), zone2.size(),zone3.size()};
		double mean = (zone1.size()+ zone2.size()+zone3.size())/y.length;
		drawPlot(mean,y);
			
	}
	
	private static List<GdanskData> readFromCSV(String fileName){
		List<GdanskData> hotSpot = new ArrayList<>();
		String filename = "wifigdansk.csv";
		File file = new File(filename);
		Scanner inputStream;
		
		try  {
			inputStream = new Scanner(file);
			inputStream.next();
			
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] attributes = data.split(",");
				GdanskData gdanskData = createList(attributes);
				
				hotSpot.add(gdanskData);
							
			}
			
			}catch (IOException ioe) {
	            ioe.printStackTrace();
		}
		
		return hotSpot;
	}
	
	private static GdanskData createList(String[] metadata) {
		String ID = metadata[0];
		String location = metadata[1];
		String xc = metadata[2];
		double xCoordinate = Double.parseDouble(xc);
		String yc = metadata[3];
		double yCoordinate = Double.parseDouble(yc);
		
		return new GdanskData(ID, location, xCoordinate,yCoordinate);		
		
	}	
	
	private static void drawPlot(double mean,double y[]) {
	
		double meanvalue = mean;
		double[] yaxis = new double [y.length];
		for(int i = 0; i < y.length;++i ) {
			yaxis[i]= y[i];
		}
		
		
		DefaultCategoryDataset dataset = new  DefaultCategoryDataset();
		dataset.setValue(yaxis[0], "", "Zone 1");
		dataset.setValue(yaxis[1], "", "Zone 2");
		dataset.setValue(yaxis[2], "", "Zone 3");
		
		JFreeChart chart = ChartFactory.createBarChart("HotSpots", "", "Mean Value", dataset,
				PlotOrientation.VERTICAL, false, true, false);
		
		
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.blue);
		CategoryPlot p = chart.getCategoryPlot();
		p.setRangeGridlinePaint(Color.GREEN);
		ValueMarker vm = new ValueMarker(meanvalue, Color.BLUE, new BasicStroke(2.0F));
		p.addRangeMarker(vm);
		ChartFrame frame1 = new ChartFrame("HotSpot counts for different zones", chart);
		frame1.setVisible(true);
		frame1.setSize(500,500);
		
	}

}

class GdanskData {
	private String ID;
	private String location;
	private double xCoordinate;
	private double yCoordinate;
	
	public GdanskData(String ID, String location, double xCoordinate, double yCoordinate) {
		this.ID =ID;
		this.location = location;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	
	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
}
