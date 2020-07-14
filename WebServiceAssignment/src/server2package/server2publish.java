package server2package;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Endpoint;



public class server2publish {
	public static void main(String args[])
	{
		threadlistner tl1=new threadlistner(4001);
		Thread t1=new Thread(tl1);
		t1.start();
		Endpoint endpoint=Endpoint.publish("http://localhost:8081/cal", new server2impl());
		System.out.println(endpoint.isPublished());
		/*Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run()
			  {
				  
				  if(server2impl.a.isEmpty())
				  {
					  
				  }
				  else
				  {
					  System.out.println("before Database reset");
					  System.out.println(server2impl.a);
						System.out.println(server2impl.d);
					Set<String> setd= server2impl.a.keySet();
					Iterator itd=setd.iterator();
					while(itd.hasNext())
					{
						String s1=(String)itd.next();
							Set<String> setr =	server2impl.a.get(s1).keySet();
							Iterator itr =setr.iterator();
							while(itr.hasNext())
							{
								String s2=(String)itr.next();
									Set<String> sett =	server2impl.a.get(s1).get(s2).keySet();
									Iterator itt =sett.iterator();
									while(itt.hasNext())
									{
										String s3=(String)itt.next();
										server2impl.a.get(s1).get(s2).put(s3,"Available");
										
									}
							}
					}
					Set<String> set012= server2impl.d.keySet();
					Iterator it012=set012.iterator();
					while(it012.hasNext())
					{
						String sA1=(String)it012.next();
						server2impl.d.remove(sA1);
					}
					System.out.println("after Database reset");
					System.out.println(server2impl.a);
					System.out.println(server2impl.d);
				  }
			  }
			}, 2*60*1000, 2*60*1000);*/
	}
}
