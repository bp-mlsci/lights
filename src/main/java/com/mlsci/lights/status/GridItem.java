package com.mlsci.lights.status;

import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.Light;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class GridItem {
		private Bulb bulb;
		private Light light;
		private int row, col;

	public GridItem(int row, int col) {
		this.row = row;
		this.col = col;
	}

}
