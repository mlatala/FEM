
public class Node {

	public double x;
	public double y;
	public int id;
	double temperature;
	// 0 = false __ 1 = dol __ 2 = prawo __ 3 = gora __ 4 = lewo
 	int status;
	
	public Node()
	{
		x=-1;
		y=-1;
		id=-1;
		temperature=0;
		status=0;
	}
	
	public Node(double x,double y, int id,int temperature,int status)
	{
		this.x=x;
		this.y=-y;
		this.id=id;
		this.temperature=temperature;
		this.status=status;
	}
	
	public double getX(){		
		return x;
	}
	public double getY(){		
		return y;
	}
	public int getId(){		
		return id;
	}
	public double getTemperature(){		
		return temperature;
	}
	public int getStatus(){		
		return status;
	}
	
	public void setX(double x){		
		this.x=x;
	}
	public void setY(double y){		
		this.y=y;
	}
	public void setId(int id){		
		this.id=id;
	}
	public void setTemperature(double temperature){		
		this.temperature=temperature;
	}
	public void setStatus(int status){		
		this.status=status;
	}
}
