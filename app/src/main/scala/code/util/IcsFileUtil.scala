package code
package util


import java.util.Date

object IcsFileUtil{

  val iCalendarDateFormat = new java.text.SimpleDateFormat("yyyyMMdd'T'HHmm'00'");

  def buildIcsStr (id:String,start:Date, end:Date, email:String, title:String, location:String, description:String ) ={
    "BEGIN:VCALENDAR\n" +
    "METHOD:REQUEST\n" +
    "PRODID:-//Vila Rika Solucoes//Ebelle plataform//EN\n" +
    "VERSION:2.0\n" +
    "BEGIN:VEVENT\n" +
    "DTSTAMP:" + iCalendarDateFormat.format(start) + "\n" +
    "DTSTART:" + iCalendarDateFormat.format(start)+ "\n" +
    "DTEND:"  + iCalendarDateFormat.format(end)+ "\n" +
    "SUMMARY:"+title+"\n" +
    "UID:"+id+"\n" +
    "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:"+email+"\n" +
    "ORGANIZER:MAILTO:"+email+"\n" +
    "LOCATION:"+location+"\n" +
    "DESCRIPTION:"+description+"\n" +
    "SEQUENCE:0\n" +
    "PRIORITY:5\n" +
    "CLASS:PUBLIC\n" +
    "STATUS:CONFIRMED\n" +
    "TRANSP:OPAQUE\n" +
    "BEGIN:VALARM\n" +
    "ACTION:DISPLAY\n" +
    "DESCRIPTION:REMINDER\n" +
    "TRIGGER;RELATED=START:-PT00H15M00S\n" +
    "END:VALARM\n" +
    "END:VEVENT\n" +
    "END:VCALENDAR";
  }
}