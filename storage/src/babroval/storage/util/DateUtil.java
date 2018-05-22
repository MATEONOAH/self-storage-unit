package babroval.storage.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateUtil {

	public static Date stringToDate(String dateStr, String dateFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date sqlDate;

		try {
			sqlDate = new Date(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return sqlDate;

	}

	public static Integer getTodayYear() {

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		return Integer.parseInt(sdf.format(today));
	}
}
