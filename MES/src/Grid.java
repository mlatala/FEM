
public class Grid {

	Node[] ND;
	Element[] EL;
	
	public Grid(GlobalData GD)
	{
		ND = new Node[GD.getnn()];
		EL = new Element[GD.getne()];
		setNodes(GD);
		setElements(GD);
	}

	void printElements() {
		for (int i = 0; i < EL.length; i++) {
			System.out.println("EL = " + EL[i].getId());
			System.out.println("\tND 1 ID = " +  EL[i].getSurface_id(1));
			System.out.println("\t\tx="+EL[i].getNode(1).getX() + " y= "+EL[i].getNode(1).getY());
			
			System.out.println("\tND 2 ID = " +  EL[i].getSurface_id(2));
			System.out.println("\t\tx="+EL[i].getNode(2).getX() + " y= "+EL[i].getNode(2).getY());
			
			System.out.println("\tND 3 ID = " +  EL[i].getSurface_id(3));
			System.out.println("\t\tx="+EL[i].getNode(3).getX() + " y= "+EL[i].getNode(3).getY());
			
			System.out.println("\tND 4 ID = " +  EL[i].getSurface_id(4));
			System.out.println("\t\tx="+EL[i].getNode(4).getX() + " y= "+EL[i].getNode(4).getY());
			
			System.out.println("\n\tSurface1 Status = " + EL[i].getSurface_status(1) + "\n\tSurface2 Status = " + EL[i].getSurface_status(2)+ "\n\tSurface3 Status = " + EL[i].getSurface_status(3)+ "\n\tSurface4 Status = " + EL[i].getSurface_status(4) + "\n");

		}
	}
	
	void printNodes()
	{
		for(int i =0; i<ND.length;i++)
		{
			System.out.println("ND_ID = " + ND[i].getId() + "\n  x = " + ND[i].getX() + "  y = " + ND[i].getY()  +"\n  Status = " + ND[i].getStatus());
		}
	}


	public void setElements(GlobalData gd)
	{
		int n = 0;
		int iteration = 0;
		double tmpX = gd.getB()/(gd.getnB()-1);
        double tmpY = gd.getH()/((gd.getnH()-1));

		for(int i = 0; i< EL.length; i++)
		{
			EL[i] = new Element();
			EL[i].setId(i + 1);

			EL[i].setSurface_id(1,i + 1 + n);
			EL[i].node[1] = new Node();
			EL[i].getNode(1).setId(EL[i].getSurface_id(1));
			EL[i].getNode(1).setX(getNodeX(EL[i].getNode(1).getId())*tmpX);
			EL[i].getNode(1).setY(getNodeY(EL[i].getNode(1).getId())*tmpY);
			EL[i].getNode(1).setTemperature(gd.tBegin);

			EL[i].setSurface_id(2,i + 1 + gd.nH + n);
			EL[i].node[2] = new Node();
			EL[i].getNode(2).setId(EL[i].getSurface_id(2));
			EL[i].getNode(2).setX(getNodeX(EL[i].getNode(2).getId())*tmpX);
			EL[i].getNode(2).setY(getNodeY(EL[i].getNode(2).getId())*tmpY);
			EL[i].getNode(2).setTemperature(gd.tBegin);
			
			EL[i].setSurface_id(3,i + 2 + gd.nH + n);
			EL[i].node[3] = new Node();
			EL[i].getNode(3).setId(EL[i].getSurface_id(3));
			EL[i].getNode(3).setX(getNodeX(EL[i].getNode(3).getId())*tmpX);
			EL[i].getNode(3).setY(getNodeY(EL[i].getNode(3).getId())*tmpY);
			EL[i].getNode(3).setTemperature(gd.tBegin);
			
			EL[i].setSurface_id(4,i + 2 + n);
			EL[i].node[4] = new Node();
			EL[i].getNode(4).setId(EL[i].getSurface_id(4));
			EL[i].getNode(4).setX(getNodeX(EL[i].getNode(4).getId())*tmpX);
			EL[i].getNode(4).setY(getNodeY(EL[i].getNode(4).getId())*tmpY);
			EL[i].getNode(4).setTemperature(gd.tBegin);
			
			iteration++;
			if(iteration ==  gd.nH-1)
			{
				iteration = 0;
				n++;
			}
		}

		for(int k = 0; k<EL.length; k++)
		{
			int id1 = EL[k].getSurface_id(1);
			int id2 = EL[k].getSurface_id(2);
			int id3 = EL[k].getSurface_id(3);
			int id4 = EL[k].getSurface_id(4);

			Node n1=new Node();
			Node n2=new Node();
			Node n3=new Node();
			Node n4=new Node();
			
			for (Node node : ND) {
				if(node.getId() == id1 )
					n1=node;
				else if(node.getId() == id2)
					n2=node;
				else if(node.getId() == id3)
					n3=node;
				else if(node.getId() == id4)
					n4=node;
			}

			if(n1.getStatus() > 0 && n2.getStatus() > 0)
				EL[k].setSurface_status(2,1);
			if(n2.getStatus() > 0 && n3.getStatus() > 0)
				EL[k].setSurface_status(3,2);
			if(n3.getStatus() > 0 && n4.getStatus() > 0)
				EL[k].setSurface_status(4,3);
			if(n4.getStatus() > 0 && n1.getStatus() > 0)
				EL[k].setSurface_status(1,4);
		}

	}

	public void setNodes(GlobalData gd)
	{
		int n = 0;
		int id = 0;		
		
		for(int i = 0; i< ND.length; i++)
			ND[i] = new Node();

		for(int i = 0; i< gd.getnB(); i++ )
		{
			for(int j = 0 ; j< gd.getnH() ; j++)
			{		
				id = j + (i*gd.getnH()) + 1;
				ND[n].setX(i);
				ND[n].setY(j);
				ND[n].setId(id);

				if( i == 0 )
					ND[n].status = 1;
				else if(i == (gd.getnB()-1))
					ND[n].status = 3;
				else if( j == 0 || j== (gd.getnH()-1))
					ND[n].status = 4;
				else if( j == 0 || j== (gd.getnH()-1))
					ND[n].status = 2;
				else
					ND[n].status = 0;

				n++;

			}
		}
	}
	
	public double getNodeX(int id)
	{
		for(int i =0; i< ND.length; i++)
			if(ND[i].getId() == id)
				return ND[i].getX();
		
		return -1;
	}

	public double getNodeY(int id)
	{
		for(int i =0; i< ND.length; i++)
			if(ND[i].getId() == id)
				return ND[i].getY();

		return -1;
	}
	public double getNodeT(int id)
	{
		for(int i =0; i< ND.length; i++)
			if(ND[i].getId() == id)
				return ND[i].getTemperature();

		return -1;
	}
}
