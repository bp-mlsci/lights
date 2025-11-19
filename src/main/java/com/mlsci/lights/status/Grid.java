package com.mlsci.lights.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.Light;


public class Grid {
	private List<GridRow> gridRows;
	private boolean reversed = false;
	
	public Grid(int maxRow, int maxCol) {
		gridRows = new ArrayList<GridRow>();
		for(int row = 0; row <= maxRow; row++) {
			gridRows.add(new GridRow(row, maxCol));
		}
	}
	
	public void add( Light light) {
		gridRows.get(light.getBulb().getRow()).add(light);	
	}

	
	public List<GridRow> getGridRows() {
		if(! reversed) {
			Collections.reverse(gridRows);
			reversed = true;
		}
		return gridRows;
	}
	
}
