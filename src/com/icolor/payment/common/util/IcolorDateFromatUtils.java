package com.icolor.payment.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;


public class IcolorDateFromatUtils
{
	private static final Logger LOG = Logger.getLogger(IcolorDateFromatUtils.class);

	private static final String FORMAT_1 = "yyyyMMddHHmmss";

	private static final String FORMAT_2 = "yyyyMMdd";

	private static final String FORMAT_3 = "MMdd";

	private static final String FORMAT_4 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * format date as yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getCurrentTime()
	{
		return new SimpleDateFormat(FORMAT_1).format(new Date());
	}

	/**
	 * format date as YYYYMMDD
	 *
	 * @return
	 */
	public static String getDateString(final Date date)
	{
		return new SimpleDateFormat(FORMAT_2).format(date);
	}

	/**
	 * format date as YYYYMMDD
	 *
	 * @return
	 */
	public static String getDateTimeString(final Date date)
	{
		return new SimpleDateFormat(FORMAT_1).format(date);
	}

	/**
	 * format date as YYYYMMDD
	 *
	 * @return
	 */
	public static String getAfterDateString(final Date date)
	{
		final Date newDate = DateUtils.addDays(date, 7);
		if (newDate.after(new Date()))
		{
			return new SimpleDateFormat(FORMAT_2).format(new Date());
		}
		else
		{
			return new SimpleDateFormat(FORMAT_2).format(newDate);
		}
	}

	public static Date convert2Date(final String dateString)
	{
		try
		{
			return new SimpleDateFormat(FORMAT_1).parse(dateString);
		}
		catch (final ParseException e)
		{
			LOG.error(String.format("dateString %s convert to java.util.Date error", dateString), e);
		}
		return null;
	}

	public static Date convert2Date2(final String dateString)
	{
		try
		{
			return new SimpleDateFormat(FORMAT_4).parse(dateString);
		}
		catch (final ParseException e)
		{
			LOG.error(String.format("dateString %s convert to java.util.Date error", dateString), e);
		}
		return null;
	}

	public static String getMonthAndDayString(final Date date)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_3);
		return sdf.format(date);
	}
}
