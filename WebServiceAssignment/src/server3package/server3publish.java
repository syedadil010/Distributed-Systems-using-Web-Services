package server3package;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Endpoint;

public class server3publish {
	public static void main(String args[])
	{
		threadlistner tl1=new threadlistner(4002);
		Thread t1=new Thread(tl1);
		t1.start();
		Endpoint endpoint=Endpoint.publish("http://localhost:8082/cal", new server3impl());
		System.out.println(endpoint.isPublished());
		/*Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run()
			  {
				  
				  if(server3impl.a.isEmpty())
				  {
					  
				  }
				  else
				  {
					
					  System.out.println("before Database reset");
					  System.out.println(server3impl.a);
						System.out.println(server3impl.d);
					Set<String> setd= server3impl.a.keySet();
					Iterator itd=setd.iterator();
					while(itd.hasNext())
					{
						String s1=(String)itd.next();
							Set<String> setr =	server3impl.a.get(s1).keySet();
							Iterator itr =setr.iterator();
							while(itr.hasNext())
							{
								String s2=(String)itr.next();
									Set<String> sett =	server3impl.a.get(s1).get(s2).keySet();
									Iterator itt =sett.iterator();
									while(itt.hasNext())
									{
										String s3=(String)itt.next();
										server3impl.a.get(s1).get(s2).put(s3,"Available");
									}
							}
					}
					Set<String> set234= server3impl.d.keySet();
					Iterator it234=set234.iterator();
					while(it234.hasNext())
					{
						String sA1=(String)it234.next();
						server3impl.d.remove(sA1);
					}
					System.out.println("after Database reset");
					System.out.println(server3impl.a);
					System.out.println(server3impl.d);
				  }
			  }
			}, 2*60*1000, 2*60*1000);*/
	}
}
