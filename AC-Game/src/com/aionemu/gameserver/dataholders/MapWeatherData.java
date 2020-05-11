package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.world.WeatherTable;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"weatherData"})
@XmlRootElement(name = "weather")
public class MapWeatherData {

    @XmlElement(name = "map", required = true)
    private List<WeatherTable> weatherData;
    @XmlTransient
    private TIntObjectHashMap<WeatherTable> mapWeather;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        mapWeather = new TIntObjectHashMap<WeatherTable>();

        for (WeatherTable table : weatherData) {
            mapWeather.put(table.getMapId(), table);
        }

        weatherData.clear();
        weatherData = null;
    }

    public WeatherTable getWeather(int mapId) {
        return mapWeather.get(mapId);
    }

    public int size() {
        return mapWeather.size();
    }
}
