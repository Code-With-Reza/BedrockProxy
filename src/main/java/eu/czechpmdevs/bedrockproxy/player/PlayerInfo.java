package eu.czechpmdevs.bedrockproxy.player;

import java.util.UUID;

public class PlayerInfo {
	
	private String address = "0.0.0.0";
	private int port;
	private UUID uuid = null;
	
	
	public PlayerInfo(String address, int port, UUID uuid) {
		this.address = address;
		this.port = port;
		this.uuid = uuid;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
}