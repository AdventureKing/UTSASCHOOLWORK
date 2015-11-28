package Proj1_JUnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class test_inventory {

	//Tests adding a new part with no conflicts
	@Test
	public void test1() {
		InventoryModel model = new InventoryModel();
		PartModel partModel = new PartModel("79810", "Rock", "Scissor Co.", 1);
		int flag = model.addInventoryPart(partModel);
		
		assertTrue(flag > 0);
	}
	
	//Tests adding a new part with name conflict
	@Test
	public void test2() {
		InventoryModel model = new InventoryModel();
		PartModel partModel = new PartModel("79810", "Pipe", "Scissor Co.", 1);
		int flag = model.addInventoryPart(partModel);
		
		assertFalse(flag > 0);
	}
	
	//Tests saving a part no quantity conflicts
	@Test
	public void test3() {
		InventoryModel model = new InventoryModel();
		ArrayList<PartModel> partList = model.getInventory();
		
		PartModel part = partList.get(0);
		
		part.setPartQuan("1000");
		
		int flag = model.updateInventoryPart(part);
		
		assertTrue(flag > 0);
	}
	
	//Tests saving a part with quantity conflict
	@Test
	public void test4() {
		InventoryModel model = new InventoryModel();
		ArrayList<PartModel> partList = model.getInventory();
		
		PartModel part = partList.get(0);
		
		part.setPartQuan("-1000");
		
		int flag = model.updateInventoryPart(part);
		
		assertFalse(flag > 0);
	}

}
