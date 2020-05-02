package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.beritra.BeritraTemplate;
import com.aionemu.gameserver.model.beritra.BeritraLocation;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javolution.util.FastMap;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beritra_invasion")
public class BeritraData
{
	@XmlElement(name = "beritra_location")
	private List<BeritraTemplate> beritraTemplates;
	
	@XmlTransient
	private FastMap<Integer, BeritraLocation> beritra = new FastMap<Integer, BeritraLocation>();
	
	/**
	 * @param u  
	 * @param parent 
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) {
		for (BeritraTemplate template : beritraTemplates) {
			beritra.put(template.getId(), new BeritraLocation(template));
		}
	}
	
	public int size() {
		return beritra.size();
	}
	
	public FastMap<Integer, BeritraLocation> getBeritraLocations() {
		return beritra;
	}
}