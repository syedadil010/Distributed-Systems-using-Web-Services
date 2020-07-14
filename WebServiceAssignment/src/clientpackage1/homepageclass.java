package clientpackage1;
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;


import server1package.*;
import server2package.*;
import server3package.*;



import java.lang.*;
import java.net.URL;

public class homepageclass {
	 public static void main(String args[]) throws Exception 
	{
		String ID;
		char a;
		int i;
		clientstudent s;
		clientadmin ad;	
		
		URL url1=new URL("http://localhost:8080/cal?wsdl");
		QName qname1=new QName("http://server1package/","server1implService");
		Service service1=Service.create(url1,qname1);
		server1interface commonimpl1=service1.getPort(server1interface.class);
		
		URL url2=new URL("http://localhost:8081/cal?wsdl");
		QName qname2=new QName("http://server2package/","server2implService");
		Service service2=Service.create(url2,qname2);
		server2interface commonimpl2=service2.getPort(server2interface.class);
		
		URL url3=new URL("http://localhost:8082/cal?wsdl");
		QName qname3=new QName("http://server3package/","server3implService");
		Service service3=Service.create(url3,qname3);
		server3interface commonimpl3=service3.getPort(server3interface.class);
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        
        while(true)
        {	
        	System.out.println("Enter 1 to use the Application Enter 2 to exit");
        	
			i=Integer.parseInt(br.readLine());
           if((i==1||i==2))
            {
        	  
        	   
        	 
           
			switch(i)
			{    
				case 1:
				  
				{
					System.out.println("Enter the User ID");
		
					ID=br.readLine();
					if((ID.length()==9) &&((ID.substring(0,3).equals(new String("DVL")) || ID.substring(0,3).equals(new String("KKL")) || ID.substring(0,3).equals(new String("WST")))) && (ID.substring(4,9).matches("[0-9][0-9][0-9][0-9][0-9]")))
					{
							if(ID.charAt(3)=='S')
							{
						
								s=new clientstudent();
								s.student(ID,commonimpl1,commonimpl2,commonimpl3);
							}

							else if(ID.charAt(3)=='A')
							{
									ad=new clientadmin();
									ad.admin(ID,commonimpl1,commonimpl2,commonimpl3);
							}
							else 
							{
								System.out.println("Enter a valid User ID");
							}
					}
							 else
				   {
						System.out.println("please provide a valid user id ");
				        
				   }
					break;
				}
				case 2:
				
				{	System.exit(0);
					break;
				}
			}
        }
           else
           {
        	   System.out.println("enter the correct input");
           }
	}
	
}
}