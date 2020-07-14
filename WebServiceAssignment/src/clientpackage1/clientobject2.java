package clientpackage1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server1package.server1interface;

public class clientobject2 implements Runnable
{   
	public  clientobject2(int i)
	{
		UID="DVLS"+i;
	}
	String campusName="DVL";
	String rno="201";
	String date="30-10-1995";
	String timeslot="10-1";
	String UID;
	 public void run()
	 {
		 server1interface si1=null;
		try {
			si1 = (server1interface)Naming.lookup("rmi://localhost:25011/tag1");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 si1.bookroom(campusName, rno, date, timeslot, UID);
		 
	 }
	

}