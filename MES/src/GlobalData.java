
public class GlobalData {

	double H, B, tBegin, time, timeStep,t_otoczenia,t_otoczenia_2,c,k,r,alfa;
	int nH, nB, nn, ne;
	
	public GlobalData()
	{
	tBegin=10;
	time=600;
	timeStep=60;
	t_otoczenia=25;
	t_otoczenia_2=1300;
	c=450;
	k=10.1;
	r=7900;
	alfa=15.9;
	H=0.005;
	B=0.1;
	nH=4;
	nB=5;		
	nn= nB * nH;
	ne= (nB-1) * (nH-1);		
	}
	
	public void printData()
	{
		System.out.println("H= " + H + "\nB= " + B +"\nnH= " + nH + "\nnB= " + nB + "\nnh= " + nn + "\nne= " + ne);
	}
	public double getH(){		
		return H;
	}
	public double getB(){		
		return B;
	}
	public int getnH(){		
		return nH;
	}
	public int getnB(){		
		return nB;
	}
	public int getnn(){		
		return nn;
	}
	public int getne(){		
		return ne;
	}
	
	public void setH(double H){		
		this.H=H;
	}
	public void setB(double B){		
		this.B=B;
	}
	public void setnH(int nH){		
		this.nH=nH;
		nn= nB * nH;
		ne= (nB-1) * (nH-1);
	}
	public void setnB(int nB){		
		this.nB=nB;
		nn= nB * nH;
		ne= (nB-1) * (nH-1);
	}

}
