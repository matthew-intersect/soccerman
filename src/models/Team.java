package models;

public class Team
{
	private int id;
	private String name;
	private String code;
	private String manager;
	
	public Team()
	{
	}
	
	public Team(String name, int id, String manager)
	{
		this.name = name;
		this.id = id;
		this.manager = manager;
	}
	
	public Team(String name, int id, String code, String manager)
	{
		this.id = id;
		this.name = name;
		this.code = code;
		this.manager = manager;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getManager()
	{
		return manager;
	}

	public void setManager(String manager)
	{
		this.manager = manager;
	}
}
