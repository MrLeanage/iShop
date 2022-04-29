package iShop;
import java.util.ArrayList;


public class SimulatorController{
	
	private static SimulatorController instance;
	
	private SimulatorView simView;
	private SimulatorModel simModel;
	private OutputPanel outputPanel;
	
	public static SimulatorController simCont() {
		if (instance == null) {
			instance = new SimulatorController();
		}
		return instance;
	}
	
	private SimulatorController() {
		simView = SimulatorView.simView();
		simModel = SimulatorModel.simModel();
	}
	
	public void selectTestCase(int testCase) {
		simModel.setTestCase(testCase);
	}
	
	public void startSim(){
		simModel.startTimer();
	}
	
	public void stopSim(){
		simModel.stopTimer();
	}
	
	public void clearViewCustomerList() {
		simView.clearCustomerList();
	}
	
	public SimulatorView getView() {return simView;}
	public SimulatorModel getModel() {return simModel;}
	public OutputPanel getOutputPanel() {return outputPanel;}
	
	public void setOutputPanel(OutputPanel outputPanel) {this.outputPanel = outputPanel;}
	public void setGenSliderMax(int genSliderMax) {simModel.setGenSliderMax(genSliderMax);}
	public void setMaxCustomers(int maxCustomers) {simModel.setMaxCustomers(maxCustomers);}
	public void setGenSpeed(int genSpeed) {simModel.setGenSpeed(genSpeed);}
	public void setStartingRoads(ArrayList<Customer.RoadLbl> roads) {simModel.setStartingRoads(roads);}
	public void setEndingRoads(ArrayList<Customer.RoadLbl> roads) {simModel.setEndingRoads(roads);}
}
