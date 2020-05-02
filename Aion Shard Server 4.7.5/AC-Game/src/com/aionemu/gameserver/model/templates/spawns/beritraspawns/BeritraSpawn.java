package com.aionemu.gameserver.model.templates.spawns.beritraspawns;

import com.aionemu.gameserver.model.templates.spawns.Spawn;
import com.aionemu.gameserver.model.beritra.BeritraStateType;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rinzler (Encom)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeritraSpawn")
public class BeritraSpawn
{
	@XmlAttribute(name = "id")
	private int id;
	
	public int getId() {
		return id;
	}
	
	@XmlElement(name = "beritra_type")
	private List<BeritraSpawn.BeritraStateTemplate> BeritraStateTemplate;
	
	public List<BeritraStateTemplate> getSiegeModTemplates() {
		return BeritraStateTemplate;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "BeritraStateTemplate")
	public static class BeritraStateTemplate {
	
		@XmlElement(name = "spawn")
		private List<Spawn> spawns;
		
		@XmlAttribute(name = "bstate")
		private BeritraStateType beritraType;
		
		public List<Spawn> getSpawns() {
			return spawns;
		}
		
		public BeritraStateType getBeritraType() {
			return beritraType;
		}
	}
}