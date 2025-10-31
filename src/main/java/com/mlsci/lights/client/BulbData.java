package com.mlsci.lights.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



// {"title":"Kauf Bulb d80bd9","comment":"","ota":true,"log":true,"lang":"en","esph_v":"2025.8.1","proj_n":"Kauf.RGBWW","proj_v":"1.96(u)","proj_l":"","soft_ssid":"Grange","hard_ssid":"initial_ap","has_ap":true,"free_sp":3657728,"mac_addr":"EC:64:C9:D8:0B:D9"}

@Getter
@Setter
@ToString
public class BulbData {
	private String title;
	private String comment;
	private Boolean ota;
	private Boolean log;
	private String lang;
	private String esph_v;
	private String proj_n;
	private String proj_v;
	private String proj_l;
	private String mac_addr;
	
}
