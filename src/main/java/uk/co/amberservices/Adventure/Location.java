package uk.co.amberservices.Adventure;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a location that the character can be in.
 * Location has a unique identifier (for obvious reasons, I'd hope), a textural description
 * and a HashMap of Location which represents neighbours - keyed by direction (north, south, east, west)
 * 
 * @author AdrianBell
 *
 */
public class Location
{

    private Integer id;
    private String description;
    private Map<String, Integer> neighbours = new HashMap<String, Integer>();

    public Integer getId()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Map<String, Integer> getNeighbours()
    {
        return neighbours;
    }

    public Location(Integer id, String description)
    {
        super();
        this.id = id;
        this.description = description;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

}