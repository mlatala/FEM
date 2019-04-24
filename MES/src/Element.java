
public class Element {

	public int id;
	public int[] surface_id;
	int [] surface_status;
	Node[] node;
	
	public Element()
	{
		surface_id = new int[5];
		for(int i = 0; i< 5; i++)
			surface_id[i]=-1;

		node = new Node[5];
		for(int i = 0; i< 5; i++)
			node[i]=null;
		
		surface_status = new int[5];
		for(int i =0; i<5; i++)
			surface_status[i]=0;

	}
	
	public Element(int id, int[] surface_id) {
		this.id = id;
		this.surface_id = surface_id;
	}

	public int getId(){		
		return id;
	}
	public int getSurface_id(int n){		
		return surface_id[n];
	}
	public int getSurface_status(int n){		
		return surface_status[n];
	}
	public Node getNode(int n){		
		return node[n];
	}
	
	public void setId(int id){		
		this.id=id;
	}
	public void setSurface_id(int n,int surface_id){		
		this.surface_id[n]=surface_id;
	}
	public void setSurface_status(int n,int status){		
		this.surface_status[n]=status;
	}
	public void setNode(int n, Node node){		
		this.node[n]=node; 
	}
}
