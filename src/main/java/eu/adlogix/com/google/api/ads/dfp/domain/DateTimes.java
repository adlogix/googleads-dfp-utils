package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;

import org.joda.time.DateTime;

import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;

public class DateTimes {

	/**
	 * @param version
	 * @param lastModifiedDateTime
	 * @return dfpDateTime
	 */
	public static <T> T toDateTime(DfpVersion version, DateTime lastModifiedDateTime) {
		Class<?> dateTimesClass = null;

		try {
			dateTimesClass = new DfpServiceClassManager().getDateTimes(version);
			return on(dateTimesClass).call("toDateTime", lastModifiedDateTime).get();

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:toDateTime in class:" + dateTimesClass.getName(), e);
		}

	}
}
