package iShop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class SimulatorView extends JPanel implements Observer{
	private Customer.Status status;
	private static SimulatorView instance;
	private Customer.Indicate indicate;
	BufferedImage backgroundImage;
	BufferedImage indicator;
	BufferedImage blueCustomer;
	BufferedImage greenCustomer;
	BufferedImage redCustomer;
	BufferedImage yellowCustomer;
	
	CopyOnWriteArrayList<Customer> Customers = new CopyOnWriteArrayList<>();
	
	public static SimulatorView simView() {
		if (instance == null) {
			instance = new SimulatorView();
		}
		return instance;
	}
	
	private SimulatorView(){
		
		try {
                    //\src\main\java\com\cccu\iShop\images
			backgroundImage = ImageIO.read(new File("src/iShop/images/middleImage.png"));
			indicator = ImageIO.read(new File("src/iShop/images/indicator.png"));
			blueCustomer = ImageIO.read(new File("src/iShop/images/blue_Customer.png"));
			greenCustomer = ImageIO.read(new File("src/iShop/images/green_Customer.png"));
			redCustomer = ImageIO.read(new File("src/iShop/images/red_Customer.png"));
			yellowCustomer = ImageIO.read(new File("src/iShop/images/yellow_Customer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(1000, 535);
		
	}
	
	@Override
	public void paint(Graphics g) {
		BufferedImage image = null;
		
		this.setSize(1000, 535);
		g.drawImage(backgroundImage, 0, 0, null);
				
		for(Customer Customer : Customers) {
			
			switch (Customer.getColour()) {
			case blue:
				image = blueCustomer;
				break;
			case red:
				image = redCustomer;
				break;
			case green:
				image = greenCustomer;
				break;
			case yellow:
				image = yellowCustomer;
				break;
			}
			
			g.drawImage(rotateImage(addIndicator(image, Customer), Customer.getAngle()), Customer.getX()-25, Customer.getY()-25, null);
		}
	}
	
	private BufferedImage rotateImage(BufferedImage image, double intRotation) {
		BufferedImage bufferedImage = image;
		double rotation = Math.toRadians(intRotation);

		    AffineTransform tx = new AffineTransform();
		    tx.rotate(rotation, 25, 25);

		    AffineTransformOp op = new AffineTransformOp(tx,
		        AffineTransformOp.TYPE_BILINEAR);
		    bufferedImage = op.filter(bufferedImage, null);
		    
		    return bufferedImage;
	}
	

	@Override
	public void update(Observable observable, Object Customer) {
		
		//if observable Customer is not on view's Customer list, then add it
		if (Customer.getClass() == Customer.class) {
			if (!Customers.contains(Customer)) {

				Customers.add((Customer)Customer);
			} 
			
			//if it is, but status is 'none' then remove from list as it's been killed	
			if (((Customer) Customer).getStatus() == status.none) {
				Customers.remove(Customer);
			}
		} 
		
		repaint();
	}
	
	private BufferedImage addIndicator(BufferedImage CustomerImage, Customer Customer) {
		if (!Customer.getIndicating() || Customer.indicate == indicate.none) {
			return CustomerImage;
		}
		
		BufferedImage newCustomer = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = newCustomer.createGraphics();
			g2d.drawImage(CustomerImage, 0, 0, null);
			
			if (Customer.indicate == indicate.left) {
				g2d.drawImage(indicator, 25, 10, null);
			} else {
				g2d.drawImage(indicator, 25, 30, null);
			}
			
			g2d.dispose();
		
		return newCustomer;
	}
	
	//Stops the Customers in the list from notifying the view then removes them from the view's list
	public void clearCustomerList() {
		for (Customer Customer : Customers) {
			Customer.kill();
		}

		Customers.removeAll(Customers);
		System.out.println(Customers.size() + " items in Customers now");
	}
}
