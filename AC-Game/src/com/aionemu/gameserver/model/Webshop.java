package com.aionemu.gameserver.model;

/**
 * This class represents an Webshop
 *
 * @author Blackfire
 */
public class Webshop {

    private int id;
    private String recipient;
	private int itemId;
	private int count;
	private String send;
    
    
    
    public Webshop(int id, String recipient, int itemId, int count, String send) {
        this.id = id;
        this.recipient = recipient;
		this.itemId = itemId;
		this.count = count;
		this.send = send;
    }

    
    public int getId() {
        if (id != 0) {
            return id;
        } else {
            return -1;
        }
    }

    public String getRecipient() {
        return recipient;
    }
	
	public int getItemId() {
        return itemId;
    }

    public int getCount() {
        return count;
    }

   	public String getSend() {
        return send;
    }
}
