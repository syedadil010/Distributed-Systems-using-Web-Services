package server3package;

import java.io.*;
import java.text.SimpleDateFormat;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Timestamp;
import java.util.*;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import server2package.server2interface;
import server2package.threadsender2;
import server1package.server1interface;

@WebService(endpointInterface="server3package.server3interface")
@SOAPBinding(style=Style.RPC)
public class server3impl implements server3interface {
	
	
	static HashMap<String,HashMap<String,String>> b=new HashMap<String,HashMap<String,String>>();
	static HashMap<String,String> c=new HashMap<String,String>();   
	static HashMap<String,HashMap<String,HashMap<String,String>>> a=new HashMap<String,HashMap<String,HashMap<String,String>>>();
	
	static HashMap<String,ArrayList<String>> d=new HashMap<String,ArrayList<String>>();
	static ArrayList<String> e=new ArrayList<String>();
	String bookingid;
	double w1;
	int i=0;
	String date;
	String rno;
	String timeslot; 
	server1interface si1;
	server2interface si2;
	static String fcount;

	public server3impl() 
	{
		super();
	}
	void fwriter(String data,String path)throws IOException
	{
		File f=new File("C:\\Code Repository 2\\WebServiceAssignment\\src\\log files\\"+path+".txt");
		if(!f.exists())
			f.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
		
	    writer.write(data);
       
	    writer.write(System.getProperty( "line.separator" ));
	  
	    writer.close();

		
	}
	public String localcount(String date)
	{
		int lcount=0;
		String s3="Available";
         if(!a.isEmpty())
		{
        	 if(a.containsKey(date))
        	 {
        		  
        		 Set<String> set= a.get(date).keySet();
        		 Iterator it=set.iterator();
        		 while(it.hasNext())
        		 {
        			 String s =(String)it.next();
        			 Set<String> set1 =	a.get(date).get(s).keySet();
        			 Iterator it1 =set1.iterator();
        			 while(it1.hasNext())
        			 {
        				 String s1=(String)it1.next();
        				 if(s3.equals(a.get(date).get(s).get(s1)))
        				 lcount++;
        				 System.out.println(a);
        			 }
	
        		 }
        	 }
        	 else
        	 {	 
        		return"0";
        	 }	 
		}
         String s=Integer.toString(lcount);
         try 
			{	
				fwriter(s+"local available timeslots","DVLServer");
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		return s;
	}
	
	public boolean createroom(String rno,String date,String timeslot)
	{
		
		
		if(a.isEmpty())
		{
			synchronized(this)
			{
				a.put(date, new HashMap<String,HashMap<String,String>>());
				a.get(date).put(rno, new HashMap<String,String>());
				a.get(date).get(rno).put(timeslot,"Available");
				//b.put(rno,c);
			//	a.put(date,b);
			}	
				System.out.println(a);
				try 
				{	
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTA12345");
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTServer");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				return true;
		}
		else
		{
			Set<String> setd= a.keySet();
			Iterator itd=setd.iterator();
			while(itd.hasNext())
			{
				String s1=(String)itd.next();
				if(date.equals(s1))
				{
					Set<String> setr =	a.get(date).keySet();
					Iterator itr =setr.iterator();
					while(itr.hasNext())
					{
						String s2=(String)itr.next();
						if(rno.equals(s2))
						{
							Set<String> sett =	a.get(date).get(rno).keySet();
							Iterator itt =sett.iterator();
							while(itt.hasNext())
							{
								String s3=(String)itt.next();
								if(timeslot.equals(s3))
								{
									System.out.println("Timeslot already exists");
									System.out.println(a);
									try 
									{	
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation unsuccesful)","WSTA12345");
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation unsuccesful)","WSTServer");
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
									return false;
									
								}
							}
							synchronized(this)
							{
							a.get(date).get(rno).put(timeslot,"Available");
							System.out.println(a);
							}
							try 
							{	
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTA12345");
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTServer");
							} 
							catch (IOException e) 
							{
								e.printStackTrace();
							}
							return true;
						}
					}
					synchronized(this)
					{
					a.get(date).put(rno,new HashMap<String,String>());
					a.get(date).get(rno).put(timeslot,"Available");
					System.out.println(a);
					}
					try 
					{	
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTServer");
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					return true;
				}
			}	
			synchronized(this)
			{
			a.put(date, new HashMap<String,HashMap<String,String>>());
			a.get(date).put(rno, new HashMap<String,String>());
			a.get(date).get(rno).put(timeslot,"Available");
			System.out.println(a);
			}
			try 
			{	
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","WSTServer");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return true;
		}				
	}	
	public boolean deleteroom(String rno, String date, String timeslot)
	{
		if(a.containsKey(date))
		{	
			if(a.get(date).containsKey(rno))
			{
				if(a.get(date).get(rno).containsKey(timeslot))
				{
					synchronized(this)
					{
					a.get(date).get(rno).remove(timeslot);
					}
					try 
					{	
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","WSTA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","WSTServer");
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
		
					return true;
				}
			}
		}	
			try 
			{	
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","WSTA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","WSTServer");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return false;
	}
	public String bookroom(String campusName,String rno,String date,String timeslot,String UID)
	
	{
		if(d.isEmpty())
		{
			synchronized(this)
			{
			d.put(UID,new ArrayList<String>());
			}
			}
		if(!d.containsKey(UID))
		{	
			synchronized(this)
			{
			d.put(UID,new ArrayList<String>(3));
			}
			}
		
		if(d.get(UID).size()>=3)
		{	
			System.out.println("You have reached the booking limit already");
		try 
		{	
			fwriter(rno+"	"+date+"	"+timeslot+" 	limit reached",UID);
			fwriter(rno+"	"+date+"	"+timeslot+" 	limit reached","WSTServer");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			return "limit reached";
		}
		else
		{   
			if(campusName.equals(new String("WST")))
			{
				if(a.containsKey(date))
				{	
					if(a.get(date).containsKey(rno))
					{
						if(a.get(date).get(rno).containsKey(timeslot))
						{
							
								System.out.println(a);
								if((a.get(date).get(rno).get(timeslot)).equals("Available"))
								{
									synchronized(this)
									{
									bookingid = UUID.randomUUID().toString();
			
									a.get(date).get(rno).put(timeslot,bookingid);
									System.out.println(a);
									System.out.println("booked");
									d.get(UID).add(bookingid);
									System.out.println(d);
									}
									try 
									{	
										fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful",UID);
										fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful","WSTServer");
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
									return bookingid;
									
								}
						
								else
								{
									System.out.println("its already booked");
									try 
									{	
										fwriter(rno+"	"+date+"	"+timeslot+" 	already booked",UID);
										fwriter(rno+"	"+date+"	"+timeslot+" 	already booked","WSTServer");
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
									return "its already booked";
								}
							
						}
						return "room doesn't exist";
					}
					return "room doesn't exist";
				}
				return "room doesn't exist";
			}	
			else if(campusName.equals(new String("DVL")))
			{
				String s1=new String("DVL");
				String s2=new String(date);
				String s3=new String(rno);
				String s4=new String(timeslot);
				String s5=new String(UID);
				String s7=new String("BR");
				String s6 =new String();
			   s6=s6.concat(s1);
				s6=s6.concat(s2);
				s6=s6.concat(s3);
				s6=s6.concat(s4);
				s6=s6.concat(s5);
				s6=s6.concat(s7);
				threadsender3 ts1=new threadsender3(s6,4000);
			    Thread t1=new Thread(ts1);
			    t1.start();
			    try {
					t1.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Returned value from DVL"+ts1.dstring);
				if(ts1.dstring.matches("limit reached(.*)"))
				{
					bookingid=ts1.dstring.substring(0,13);
					return bookingid;
				}
				else if(ts1.dstring.matches("its already booked(.*)"))
				{
					bookingid=ts1.dstring.substring(0,18);
				}
				else if(ts1.dstring.matches("room doesn't exist(.*)"))
				{	
					bookingid=ts1.dstring.substring(0,18);
				}
				else
				{
					bookingid=ts1.dstring.substring(0,36);
				}	
				try 
				{	
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful in DVL",UID);
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful in DVL","WSTServer");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			else if(campusName.equals(new String("KKL")))
			{

				String s1=new String("KKL");
				String s2=new String(date);
				String s3=new String(rno);
				String s4=new String(timeslot);
				String s5=new String(UID);
				String s7=new String("BR");
				String s6 =new String();
			   s6=s6.concat(s1);
				s6=s6.concat(s2);
				s6=s6.concat(s3);
				s6=s6.concat(s4);
				s6=s6.concat(s5);
				s6=s6.concat(s7);
				threadsender3 ts2=new threadsender3(s6,4001);
				Thread t2=new Thread(ts2);
				t2.start();
				try {
					t2.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Returned value from KKL"+ts2.dstring);
				if(ts2.dstring.matches("limit reached(.*)"))
				{
					bookingid=ts2.dstring.substring(0,13);
					return bookingid;
				}
				else if(ts2.dstring.matches("its already booked(.*)"))
				{
					bookingid=ts2.dstring.substring(0,18);
					return bookingid;
				}
				else if(ts2.dstring.matches("room doesn't exist(.*)"))
				{	
					bookingid=ts2.dstring.substring(0,18);
					return bookingid;
				}
				else
				{
					bookingid=ts2.dstring.substring(0,36);
				}
				try 
				{	
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful in KKL",UID);
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful in KKL","WSTServer");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		synchronized(this)
		{
		d.get(UID).add(bookingid);
		System.out.println(d);
		}
		try 
		{	
			fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful",UID);
			fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+" booking successful","WSTServer");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return bookingid;
	}	
		
	
	public String getAvailableTimeSlot ( String date)
	{	

				
			boolean v1=true;
			boolean v2=true;
			int sc1=0,sc2=0,sc3=0,cou=0;
			String s3="Available";
			String cou1=localcount(date);
			System.out.println("local count of WST  "+cou1);

			threadsender3 ts1=new threadsender3(date,4000);
			threadsender3 ts2=new threadsender3(date,4001);
			Thread t1=new Thread(ts1);
			Thread t2=new Thread(ts2);
			t1.start();
			t2.start();
			String j=new String("");
			String k=new String("");
				try {
					t1.join();
					t2.join();
				} 
				catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			j=ts1.dstring;
			j=j.substring(0,1);
			System.out.println("After j in DVL and the value from KKL is  "+j);
			k=ts2.dstring;
			k=k.substring(0,1);
			System.out.println("After K in DVL and the value from KKL is  "+k);
			System.out.println(a);
			  return "WST("+cou1+")  "+"DVL("+j+")  "+"KKL("+k+")";
		
	}
	  public String  cancelBooking (String bookingID, String userid)
	  {
		  	int c=0;
			String s3=bookingID;
			Set<String> set= a.keySet();
			Iterator it=set.iterator();
			while(it.hasNext())
			{
				String s =(String)it.next();
				Set<String> set1 =	a.get(s).keySet();
				Iterator it1 =set1.iterator();
				while(it1.hasNext())
				{
					String s1=(String)it1.next();
					Set<String> set2=a.get(s).get(s1).keySet();
					Iterator it2=set2.iterator();
					while(it2.hasNext())
					{
						String s2=(String)it2.next();
						if(s3.equals(a.get(s).get(s1).get(s2)))
						{	
							Set<String> setb= d.keySet();
							Iterator idb=setb.iterator();
							while(idb.hasNext())
							{
								
								String s9=(String)idb.next();
								System.out.println(s9);
								System.out.println(d.get(s9));
								
								Iterator setn1=d.get(s9).iterator();
								while(setn1.hasNext())
								{
									String sn1=(String)setn1.next();
									if((s9.equals(userid)) && sn1.equals(bookingID))
			  					{		synchronized(this)
										{
					  						a.get(s).get(s1).put(s2,"Available");
					  						System.out.println(a.get(s).get(s1).get(s2));	  						
					  						System.out.println("Booking cancellation was successful");
					  						/*Set<String> set3= d.keySet();
					  						Iterator it3=set3.iterator();
					  						while(it3.hasNext())
					  						{
					  							String s4=(String)it3.next();
					  							for(i=0;i<d.get(s4).size();i++)
					  							{*/
					  						synchronized(this)
											{
					  							System.out.println(d);
					  									d.get(userid).remove(bookingID);
					  								System.out.println(d);
											}
											}	
					  								try 
													{	
														fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL",userid);
														fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL","WSTServer");
													} 
													catch (IOException e) 
													{
														e.printStackTrace();
													}
					  								
					  								return "cancelled";
					  							}
					  										
					  						
					  						//return "cancelled";
					  					
					  					else
								{
									System.out.println("Invalid Access");
									 try 
										{	
											fwriter(s+"	"+s1+"	"+s2+" 	"+bookingID+"	booking cancellation unsuccessful in WST",userid);
											fwriter(s+"	"+s1+"	"+s2+" 	"+bookingID+"	booking cancellation unsuccessful in WST","WSTServer");
										} 
										catch (IOException e) 
										{
											e.printStackTrace();
										}
								}
							}
						}
					}
					
				}
			}}
			String w1=new String();
			String w2=new String(bookingID);
			String w3=new String(userid);
			w1=w1.concat(w2);
			w1=w1.concat(w3);
			System.out.println(w1);
			threadsender3 ts3=new threadsender3(w1,4000);
			Thread t3=new Thread(ts3);
			t3.start();
			try 
			{
				t3.join();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String w4=ts3.dstring;
			String con=w4.substring(0,9);
			System.out.println(w4);
			if(con.equals(new String("cancelled")))
			{
				synchronized(this)
				{
				d.get(userid).remove(bookingID);
				}
				return "cancelled";
			}
			else
			{     
					System.out.println(w1);
			    	threadsender3 ts4=new threadsender3(w1,4001);
			    	Thread t4=new Thread(ts4);
			    	t4.start();
			    	try 
			    	{
						t4.join();
					} 
			    	catch (InterruptedException e) 
			    	{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	String w5=ts4.dstring;
			    	String con1=w5.substring(0,9);
			    	System.out.println(w5);
			    	if(con1.equals(new String("cancelled")))
					{
			    		synchronized(this)
						{
			    		d.get(userid).remove(bookingID);
					    	return "cancelled";
						}
						}
			    	
			 }
			
	  
			System.out.println("before the return of not cancelled");
			 try 
				{	
					fwriter("booking cancellation unsuccessful in DVL",userid);
					fwriter("booking cancellation unsuccessful in DVL","WSTServer");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			return "notcancelled";
	  }
	  public String  localcancelBooking (String bookingID, String userid)
	  {
	  	int c=0;

	  String s3=bookingID;
	  Set<String> set= a.keySet();
	  Iterator it=set.iterator();
	  while(it.hasNext())
	  {
	  	String s =(String)it.next();
	  	Set<String> set1 =	a.get(s).keySet();
	  	Iterator it1 =set1.iterator();
	  	while(it1.hasNext())
	  	{
	  		String s1=(String)it1.next();
	  		Set<String> set2=a.get(s).get(s1).keySet();
	  		Iterator it2=set2.iterator();
	  		while(it2.hasNext())
	  		{
	  			String s2=(String)it2.next();
	  			if(s3.equals(a.get(s).get(s1).get(s2)))
	  			{	
	  				Set<String> setb= d.keySet();
	  				Iterator idb=setb.iterator();
	  				while(idb.hasNext())
	  				{
	  					
	  					String s9=(String)idb.next();
	  					System.out.println(s9);
	  					//System.out.println(d.get(s9));
	  					//System.out.println(d.get(s9).get(1));
	  					Iterator setn1=d.get(s9).iterator();
						while(setn1.hasNext())
						{
							String sn1=(String)setn1.next();
							if((s9.equals(userid)) && sn1.equals(bookingID))
							{	
								synchronized(this)
								{
								System.out.println(a);
		  						a.get(s).get(s1).put(s2,"Available");
		  						System.out.println(a.get(s).get(s1).get(s2));	  						
		  						System.out.println("Booking cancellation was successful");
		  						/*Set<String> set3= d.keySet();
		  						Iterator it3=set3.iterator();
		  						while(it3.hasNext())
		  						{
		  							String s4=(String)it3.next();
		  							for(i=0;i<d.get(s4).size();i++)
		  							{*/
		  								System.out.println(d);
		  									d.get(userid).remove(bookingID);
		  								System.out.println(d);
								}		try 
										{	
											fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL",userid);
											fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL","WSTServer");
										} 
										catch (IOException e) 
										{
											e.printStackTrace();
										}
		  								
		  								return "cancelled";
		  							}
		  										
		  						
		  						//return "cancelled";
		  					
		  					else
	  					{
	  						System.out.println("Invalid Access");
	  						 try 
	  						{	
	  							fwriter(s+"	"+s1+"	"+s2+" 	"+bookingID+"	booking cancellation unsuccessful in WST",userid);
	  							fwriter(s+"	"+s1+"	"+s2+" 	"+bookingID+"	booking cancellation unsuccessful in WST","WSTServer");
	  						} 
	  						catch (IOException e) 
	  						{
	  							e.printStackTrace();
	  						}
	  					}
	  				}
	  			}
	  		}
	  		}
	  }
	  } 
	  try 
		{	
			fwriter("booking cancellation unsuccessful in WST",userid);
			fwriter("booking cancellation unsuccessful in WST","WSTServer");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	  return "notcancelled";
	  }
	  public String changeReservation(String studentid,String booking_id,String new_date, String new_campus_name, String new_room_no, String new_time_slot) 
	  {
		  String ret=new String();
		Set<String> sb1=d.keySet();
		Iterator ib1=sb1.iterator();
		while(ib1.hasNext())
		{
			String s1=(String)ib1.next();
			Iterator setn1=d.get(s1).iterator();
			while(setn1.hasNext())
			{
				String sn1=(String)setn1.next();
				if((s1.equals(studentid)) && sn1.equals(booking_id))
				{
				if(d.get(studentid).size()==3)
				{   synchronized(this)
					{
					d.get(studentid).remove(booking_id);
					ret=bookroom(new_campus_name,new_room_no, new_date,new_time_slot,studentid);
					d.get(studentid).add(booking_id);
					} 
					if(ret.equals("its already booked"))
			           {
			        	   
			        	   try 
				       		{	
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation ",studentid);
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation ","WSTServer");
				       		} 
				       		catch (IOException e) 
				       		{
				       			e.printStackTrace();
				       		}
			        	   return "its already booked";
			           }
			           
			           else
			           {
			        	   String cancel=cancelBooking(booking_id,studentid);
			        	   System.out.println(a);
			        	   try 
				       		{	
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST",studentid);
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST","WSTServer");
				       		} 
				       		catch (IOException e) 
				       		{
				       			e.printStackTrace();
				       		}
			        	   return ret;
			           }
					}
				else
				{
					ret=bookroom(new_campus_name,new_room_no, new_date,new_time_slot,studentid);
					  System.out.println(a);
			           if(ret.equals("its already booked"))
			           {
			        	   try 
				       		{	
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+"  in WST",studentid);
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+"  in WST","WSTServer");
				       		} 
				       		catch (IOException e) 
				       		{
				       			e.printStackTrace();
				       		}
			        	   return "its already booked";
			           }
			           
			           else
			           {
			        	   String cancel=cancelBooking(booking_id,studentid);
			        	   System.out.println(cancel);
			        	   System.out.println(a);
			        	   try 
				       		{	
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST",studentid);
				       			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST","WSTServer");
				       		} 
				       		catch (IOException e) 
				       		{
				       			e.printStackTrace();
				       		}
			        	   
			        	   return ret;
			           }
					
					
				}
				}
			
		}}
		try 
   		{	
   			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST",studentid);
   			fwriter(new_room_no+"	"+new_date+"	"+new_time_slot+" 	"+ret+" change reservation successful in WST","WSTServer");
   		} 
   		catch (IOException e) 
   		{
   			e.printStackTrace();
   		}
	
		return ret;
		  
	  }
}
