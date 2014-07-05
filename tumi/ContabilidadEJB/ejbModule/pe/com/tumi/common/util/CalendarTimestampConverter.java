package pe.com.tumi.common.util;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;

import org.apache.log4j.Logger;

public class CalendarTimestampConverter extends DateTimeConverter {
	 
	protected static Logger log = Logger.getLogger(CalendarTimestampConverter.class);

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent component, String dateString) {
              log.debug("getAsObject(): " + dateString);
              Object result;
              try {
                        result = super.getAsObject(arg0, component, dateString);
                        if (result instanceof Date) {
                                  //make it a Timestamp, because that is what jBPM will make of it anyway
                                  result = new java.sql.Timestamp(((Date) result).getTime());
                        }
              } catch (ConverterException ex) {
                        return null;
              }
              return result;
    }


    @Override
    public String getAsString(FacesContext arg0, UIComponent component, Object dateObject) {
              log.debug("getAsString(): " + (dateObject == null ? null : (dateObject.getClass().getSimpleName() + " " + dateObject.toString())));
              String result = null;
              try {
                        result = super.getAsString(arg0, component, dateObject);
              } catch (ConverterException ex) {
                        return null;
              }
              return result;
    }
}