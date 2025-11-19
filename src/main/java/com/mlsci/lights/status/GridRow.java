package com.mlsci.lights.status;

import java.util.ArrayList;
import java.util.List;

import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.Light;

public class GridRow {

	private List<GridItem> gridItems;
	
	
	public GridRow(int row, int maxCol) {
		gridItems = new ArrayList<GridItem>();
		for(int col = 0; col <= maxCol; col++) {
			gridItems.add(new GridItem(row, col));
		}
	}

	public void add( Light light) {
		var gridItem = gridItems.get(light.getBulb().getCol());
		gridItem.setLight(light);
	}

	
	public List<GridItem> getGridItems() { return gridItems; }
}
