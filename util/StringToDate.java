package imports.d20170427coupProjDafnaWeiss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Convert a date of type "String" to a date of type "Date"
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class StringToDate {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	// getting a Date Object from a String field
	/**
	 * A static method that convert String to Date type
	 * @param dateFormat The given String date in Simple Date Format "yyyy-MM-dd"
	 * @return The given date in Date type
	 */
	public static Date dateFormat(String dateFormat){
		
		Date date = null;
		
		try	{
			date = sdf.parse(dateFormat);
		}
		catch (ParseException e){
			e.printStackTrace();
		}
		return date;
	}	
}
