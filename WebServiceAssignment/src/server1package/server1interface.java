package server1package;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
@WebService
@SOAPBinding(style=Style.RPC)
public interface server1interface	
{

	public  boolean createroom(String rno,String date,String timeslot)throws  IOException;;
	public boolean deleteroom(String rno, String date, String timeslot);;
	public 	String bookroom(String campusName,String rno,String date,String timeslot,String UID);;
    public String getAvailableTimeSlot ( String date)throws InterruptedException;;
    public String cancelBooking(String bookingID,String userid);;
   // public void listener(int a,int b,String date) throws java.rmi.RemoteException;;
    public String changeReservation(String studentid,String booking_id,String new_date, String new_campus_name, String new_room_no, String new_time_slot) ;;
	
	
}
