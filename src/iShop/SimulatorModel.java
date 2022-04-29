package iShop;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


import java.util.Timer;
import java.util.Random;

public class SimulatorModel{

	private static SimulatorModel instance;
	
	int testCase;
	int tickDelay = 50;
	private int genSliderMax;
	Timer frameTimer;
	static Road roadAIn;
	static Road roadAOut;
	static Road roadBIn;
	static Road roadBOut;
	static Road roadCIn;
	static Road roadCOut;
	static Road roadDIn;
	static Road roadDOut;
	
	private int maxCustomers;
	private int genSpeed;
	private ArrayList<Customer.RoadLbl> startingRoads;
	private ArrayList<Customer.RoadLbl> endingRoads;
	
	int[][] rbCtrlPs = new int[4][3];
	
	private List<Customer> Customers = new ArrayList<>();
	
	OutputPanel outputPanel;
		
	public static SimulatorModel simModel() {
		
		if (instance == null) {
			instance = new SimulatorModel();
		}
		
		return instance;
	}
	
	private SimulatorModel() {
		instantiateRoads();
		instantiateControlPoints();
		instantiateStartEndRoads();
				
	}
	
	private void testCase1() {
		addCustomer(1);
		addCustomer(1);
	}
	
	private void testCase2() {
		addCustomer(Customer.RoadLbl.d);
		addCustomer(Customer.RoadLbl.c);
	}
	
	private void testCase3() {
		instantiateStartEndRoads();
		genSpeed = (int)(genSliderMax*0.3);
		maxCustomers = 15;
	}
	
	private void testCase4() {
		instantiateStartEndRoads();
		genSpeed = (int)(genSliderMax*0.8);
		maxCustomers = 15;
	}

	private void newTestCase() {
		Customers.clear();
		System.out.println("Customers cleared");
	}
		
	private void instantiateControlPoints() {
		// in format x, y, angle
		
		//Road A
		rbCtrlPs[0] = new int[] {452, 229, 315};
		
		//Road B
		rbCtrlPs[1] = new int[] {545, 237, 45};
		
		//Road C
		rbCtrlPs[2] = new int[] {539, 310, 135};
		
		//Road D
		rbCtrlPs[3] = new int[] {446, 292, 225};
				
	}
	
	private void instantiateStartEndRoads() {
		startingRoads = new ArrayList<Customer.RoadLbl>();
		
		startingRoads.add(Customer.RoadLbl.a);
		startingRoads.add(Customer.RoadLbl.b);
		startingRoads.add(Customer.RoadLbl.c);
		startingRoads.add(Customer.RoadLbl.d);
		
		endingRoads = new ArrayList<Customer.RoadLbl>();
		
		endingRoads.add(Customer.RoadLbl.a);
		endingRoads.add(Customer.RoadLbl.b);
		endingRoads.add(Customer.RoadLbl.c);
		endingRoads.add(Customer.RoadLbl.d);
	}
	
	private void instantiateRoads(){
		roadAIn = new Road("a", "in", -50, 250, 375, 250, 0);
		roadAOut = new Road("a", "out", -50, 275, 375, 288, 180);
		roadBIn = new Road("b", "in", 508, -50, 508, 170, 90);
		roadBOut = new Road("b", "out", 480, -50, 480, 170, 270);
		roadCIn = new Road("c", "in", 1050, 286, 628, 286, 180);
		roadCOut = new Road("c", "out", 1050, 237, 628, 244, 0);
		roadDIn = new Road("d", "in", 486, 584, 486, 363, 270);
		roadDOut = new Road("d", "out", 511, 584, 513, 363, 90);
	}

	private void addCustomer(int testCase){
		Customers.add(new Customer(testCase));
		
		new Thread(Customers.get(Customers.size()-1)).start();
	}
	
	//for test case 2
	private void addCustomer(Customer.RoadLbl destination){
		Customers.add(new Customer(destination));
		
		new Thread(Customers.get(Customers.size()-1)).start();
	}
	
	public void removeCustomer(Customer Customer) {
		Customers.remove(Customer);
	}
	
	//stop Customer generation timer
	public void stopTimer() {
		frameTimer.cancel();

		Customers.removeAll(Customers);
		
		SimulatorController.simCont().clearViewCustomerList();
	}
	
	//start generation timer for simulation
	public void startTimer() {
		//as some Customers may not have been destroyed
		SimulatorController.simCont().clearViewCustomerList();
		
		Customer.resetCustomerIDs();
		
		newTestCase();
		
		frameTimer = new Timer();
		frameTimer.schedule(new FrameTask(), 0, tickDelay);	
		
		switch(testCase) {
		case 1:
			testCase1();
			break;
		case 2:
			testCase2();
			break;
		case 3:
			testCase3();
			break;
		case 4:
			testCase4();
			break;
		}
		
	}
	
	public List<Customer> getCustomers() {return Customers;}
	public int[][] getRBControlPoints() {return rbCtrlPs;}
	public int getTestCase() {return testCase;}
	public void setGenSliderMax(int genSliderMax) {this.genSliderMax = genSliderMax;}
	public void setTestCase(int testCase) {Customers.clear(); this.testCase = testCase;}
	public void setMaxCustomers(int maxCustomers) {this.maxCustomers = maxCustomers;}
	public void setGenSpeed(int genSpeed) {this.genSpeed = genSpeed;}
	public void setStartingRoads(ArrayList<Customer.RoadLbl> roads) {this.startingRoads = roads;}
	public void setEndingRoads(ArrayList<Customer.RoadLbl> roads) {this.endingRoads = roads;}
	public ArrayList<Customer.RoadLbl> getStartingRoads() {return startingRoads;}
	public ArrayList<Customer.RoadLbl> getEndingRoads() {return endingRoads;}
	

	//Timer to control generation of customers
	SimulatorModel simModel = this;
	class FrameTask extends TimerTask{

	Timer CustomerGenTimer = new Timer();
	Random random = new Random();

	@Override
	public void run() {
		if (Customers.size() < maxCustomers && random.nextInt(genSliderMax-genSpeed) == 0) {
			switch (testCase) {
			case 3:
				simModel.addCustomer(3);
				break;
			case 4:
				simModel.addCustomer(4);
				break;
			case 5:
				simModel.addCustomer(5);
				break;
			}
		}	
		
	}
} 
}