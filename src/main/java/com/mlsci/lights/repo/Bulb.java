package com.mlsci.lights.repo;

import lombok.Getter;

@Getter
public enum Bulb {
	// window wall cans
	A("d8086c", 2, 0, "EC-64-C9-D8-0B-6C", "192.168.68.200", Room.MAIN), // to label
	B("d808da", 3, 0, "EC-64-C9-D8-08-DA", "192.168.68.201", Room.MAIN), 
	C("d80953", 4, 0, "EC-64-C9-D8-09-53", "192.168.68.202", Room.MAIN),  
	D("d80a6c", 5, 0, "EC-64-C9-D8-0A-6C", "192.168.68.203", Room.MAIN), // Check again!
	E("d80877", 6, 0, "EC-64-C9-D8-08-77", "192.168.68.204", Room.MAIN), // Check me
	F("d80b9a", 7, 0, "EC-64-C9-D8-0B-9A", "192.168.68.205", Room.MAIN), // to label  Check

	// bathroom wall
	G("d80bd9", 7, 4, "EC-64-C9-D8-0B-D9", "192.168.68.206", Room.MAIN), // TO Label
	H("d80995", 6, 4, "EC-64-C9-D8-09-95", "192.168.68.207", Room.MAIN), 
	I("d80bec", 5, 4, "EC-64-C9-D8-0B-EC", "192.168.68.208", Room.MAIN), 
	J("d80b5a", 4, 4, "EC-64-C9-D8-0B-5A", "192.168.68.209", Room.MAIN), // was W
	K("d8096b", 3, 4, "EC-64-C9-D8-09-6B", "192.168.68.210", Room.MAIN), 
	L("d80acc", 2, 4, "EC-64-C9-D8-0A-CC", "192.168.68.211", Room.MAIN), // to label Check

	// back door
	// {"d80882", "8", "1", "M", Room.MAIN),// would not restart after firmware update
	M("d80c08", 8, 1, "EC-64-C9-D8-0C-08", "192.168.68.212", Room.MAIN), // CHECK
	N("d80b29", 9, 1, "EC-64-C9-D8-0B-29", "192.168.68.213", Room.MAIN), // Check
	O("d80a26", 9, 2, "EC-64-C9-D8-0A-26", "192.168.68.214", Room.MAIN), // check
	P("d808ae", 9, 3, "EC-64-C9-D8-08-AE", "192.168.68.215", Room.MAIN), // CHECK
	Q("d80b28", 8, 3, "EC-64-C9-D8-0B-28", "192.168.68.216", Room.MAIN), // CHECK

	// Front Door
	R("d80b31", 1, 3, "EC-64-C9-D8-0B-31", "192.168.68.217", Room.MAIN), 
	S("d80a43", 0, 3, "EC-64-C9-D8-0A-43", "192.168.68.218", Room.MAIN), 
	T("d80ba6", 0, 2, "EC-64-C9-D8-0B-A6", "192.168.68.219", Room.MAIN),
	U("d8092e", 0, 1, "EC-64-C9-D8-09-2E", "192.168.68.220", Room.MAIN), // check
	V("d80b7f", 1, 1, "EC-64-C9-D8-0B-7F", "192.168.68.221", Room.MAIN),

	// helix area

	// d80a21 failed on reload firmware	
	W("d80b3d", 3, 5, "EC-64-C9-D8-0B-3D", "192.168.68.222", Room.STALLS), // check
	X("d80936", 2, 5, "EC-64-C9-D8-09-36", "192.168.68.223", Room.STALLS), // check
	Y("d809db", 4, 5, "EC-64-C9-D8-09-DB", "192.168.68.224", Room.STALLS); // check
	// bulb Z  save 225
	
	private String name, mac, ip;
	private int row, col;
	private Room room;
	
	private Bulb(String name, int row, int col, String mac, String ip, Room room) {
		this.name = name;
		this.row = row;
		this.col = col;
		this.mac = mac;
		this.ip = ip;
		this.room = room;
	}	
	
}
