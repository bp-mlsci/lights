package com.mlsci.lights.repo;

import lombok.Getter;

@Getter
public enum Bulb {
	// window wall cans
	A("d8086c", 2, 0, "EC-64-C9-D8-0B-6C", "192.168.68.200", Room.MAIN, "Window Wall Front 1st" ), // to label
	B("d808da", 3, 0, "EC-64-C9-D8-08-DA", "192.168.68.201", Room.MAIN, "Window Wall Front 2nd"), 
	C("d80953", 4, 0, "EC-64-C9-D8-09-53", "192.168.68.202", Room.MAIN, "Window Wall Front 3rd"),  
	D("d80a6c", 5, 0, "EC-64-C9-D8-0A-6C", "192.168.68.203", Room.MAIN, "Window Wall Back 3rd"), // Check again!
	E("d80877", 6, 0, "EC-64-C9-D8-08-77", "192.168.68.204", Room.MAIN, "Window Wall Back 2nd"), // Check me
	F("d80b9a", 7, 0, "EC-64-C9-D8-0B-9A", "192.168.68.205", Room.MAIN, "Window Wall Back 1st"), // to label  Check

	// bathroom wall
	G("d80bd9", 7, 4, "EC-64-C9-D8-0B-D9", "192.168.68.206", Room.MAIN, "Bathroom Wall Back 1st"), // TO Label
	H("d80995", 6, 4, "EC-64-C9-D8-09-95", "192.168.68.207", Room.MAIN, "Bathroom Wall Back 2nd"), 
	I("d80bec", 5, 4, "EC-64-C9-D8-0B-EC", "192.168.68.208", Room.MAIN, "Bathroom Wall Back 3rd"), 
	J("d80b5a", 4, 4, "EC-64-C9-D8-0B-5A", "192.168.68.209", Room.MAIN, "Bathroom Wall Front 3rd"), // was W
	K("d80994", 3, 4, "EC-64-C9-D8-09-94", "192.168.68.210", Room.MAIN, "Bathroom Wall Front 2nd"),
	L("d80acc", 2, 4, "EC-64-C9-D8-0A-CC", "192.168.68.211", Room.MAIN, "Bathroom Wall Front 1st"), // to label Check

	// back door
	// {"d80882", "8", "1", "M", Room.MAIN),// would not restart after firmware update
	M("d80c08", 8, 1, "EC-64-C9-D8-0C-08", "192.168.68.212", Room.MAIN, "Back Door Window Forward"), // CHECK
	N("d80b29", 9, 1, "EC-64-C9-D8-0B-29", "192.168.68.213", Room.MAIN, "Back Door Window Back"), // Check
	O("d80a26", 9, 2, "EC-64-C9-D8-0A-26", "192.168.68.214", Room.MAIN, "Back Door Center"), // check
	P("d808ae", 9, 3, "EC-64-C9-D8-08-AE", "192.168.68.215", Room.MAIN, "Back Door Bathroom Back"), // CHECK
	Q("d80b28", 8, 3, "EC-64-C9-D8-0B-28", "192.168.68.216", Room.MAIN, "Back Door Bathroom Forward"), // CHECK

	// Front Door
	R("d80b31", 1, 3, "EC-64-C9-D8-0B-31", "192.168.68.217", Room.MAIN, "Front Door Bathroom Forward"), 
	S("d80a43", 0, 3, "EC-64-C9-D8-0A-43", "192.168.68.218", Room.MAIN, "Front Door Bathroom Back"), 
	T("d80ba6", 0, 2, "EC-64-C9-D8-0B-A6", "192.168.68.219", Room.MAIN, "Front Door Center"),
	U("d8092e", 0, 1, "EC-64-C9-D8-09-2E", "192.168.68.220", Room.MAIN, "Front Door Window Back"), // check
	V("d80b7f", 1, 1, "EC-64-C9-D8-0B-7F", "192.168.68.221", Room.MAIN, "Front Door Window Forward "),

	// helix area

	// d80a21 failed on reload firmware	
	W("d80b3d", 1, 5, "EC-64-C9-D8-0B-3D", "192.168.68.222", Room.STALLS, "Helix"), // check
	X("d80936", 0, 6, "EC-64-C9-D8-09-36", "192.168.68.223", Room.STALLS, "Marble Maze"), // check
	Y("d809db", 1, 6, "EC-64-C9-D8-09-DB", "192.168.68.224", Room.STALLS, "PlasticVille"), // check
	Z("30282b", 1, 7, "C8-C9-A3-30-28-2B", "192.168.68.225", Room.STALLS, "Winter Front"), // check

	// bulb Z  save 225
	
	AA("d808b2", 1, 8, "EC-64-C9-D8-08-B2", "192.168.68.226", Room.STALLS, "Transformer Front"),
	AB("d80927", 0, 8, "EC-64-C9-D8-09-27", "192.168.68.227", Room.STALLS, "69th Street"),
	AC("d80ba9", 2, 8, "EC-64-C9-D8-0B-A9", "192.168.68.228", Room.STALLS, "Transformer Back"),
	AD("d80a55", 2, 10, "EC-64-C9-D8-0A-55", "192.168.68.229", Room.STALLS, "Norristown"),
	AE("d80885", 0, 10, "EC-64-C9-D8-08-85", "192.168.68.230", Room.STALLS, "Big L Radio"),
	AF("d808cb", 0, 9, "EC-64-C9-D8-08-CB", "192.168.68.231", Room.STALLS, "Big L Mid Front"),
	AG("d80ac4", 1, 9, "EC-64-C9-D8-0A-C4", "192.168.68.232", Room.STALLS, "Lego"),
	AH("d8096b", 2, 7, "EC-64-C9-D8-09-6B", "192.168.68.233", Room.STALLS, "Winter Back"); //did not  RESPOND reset net
	
	private String name, mac, ip, description;
	private int row, col;
	private Room room;
	
	private Bulb(String name, int row, int col, String mac, String ip, Room room, String description) {
		this.name = name;
		this.row = row;
		this.col = col;
		this.mac = mac;
		this.ip = ip;
		this.room = room;
		this.description = description;
	}	
	
	
	public String getDisplay() {
		return name() + " - " + description;
	}
	
}
