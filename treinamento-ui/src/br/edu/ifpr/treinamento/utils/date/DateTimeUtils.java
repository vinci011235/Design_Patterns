package br.edu.ifpr.treinamento.utils.date;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;

public final class DateTimeUtils {
   private static final String DATE_BR_PATTERN = "dd/MM/yyyy";

   // =========================================================================
   // === suporte a data e hora em java.time.* - INÍCIO =======================
   // =========================================================================
   public static String formatLocalDate(LocalDate localDate,
                                        FormatStyle formatStyle) {
      return localDate.format(DateTimeFormatter.ofLocalizedDate(formatStyle));
   }

   public static String formatLocalDate(LocalDate localDate) {
      return formatLocalDate(localDate,FormatStyle.MEDIUM);
   }

   public static LocalDate parseLocalDate(String date, String pattern) {
      return LocalDate.parse(date,DateTimeFormatter.ofPattern(pattern));
   }

   public static LocalDate parseLocalDate(String date) {
      return parseLocalDate(date,DATE_BR_PATTERN);
   }

   public static LocalDate parseLocalDate(int year, int month, int day) {
      return LocalDate.of(year,month,day);
   }

   public static LocalDate getNow() { return LocalDate.now(); }

   public static LocalDate localDateAddYears(LocalDate localDate, int years) {
      return localDate.plusYears(years);
   }

   public static LocalDate localDateAddMonths(LocalDate localDate, int months) {
      return localDate.plusMonths(months);
   }

   public static LocalDate localDateAddDays(LocalDate localDate, int days) {
      return localDate.plusDays(days);
   }

   public static LocalDate localDateNowPlusYears(int years) {
      return LocalDate.now().plusYears(years);
   }

   public static LocalDate localDateNowPlusMonths(int months) {
      return LocalDate.now().plusMonths(months);
   }

   public static LocalDate localDateNowPlusDays(int days) {
      return LocalDate.now().plusDays(days);
   }

   public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
      return new java.sql.Date(localDateToDate(localDate).getTime());
   }

   public static LocalDate sqlDateToLocalDate(java.sql.Date sqlDate) {
      return dateToLocalDate(sqlDateToDate(sqlDate));
   }
   // ==========================================================================
   // === suporte a data e hora em java.time.* - FIM ===========================
   // ==========================================================================

   // ==========================================================================
   // === suporte a data e hora em java.util.* (legado) - INÍCIO ===============
   // ==========================================================================
   public static LocalDate dateToLocalDate(java.util.Date date) {
      Instant instant = Instant.ofEpochMilli(date.getTime());

      return LocalDateTime.ofInstant(
                                  instant,ZoneId.systemDefault()).toLocalDate();
   }

   public static java.util.Date localDateToDate(LocalDate localDate) {
      // mais simples
      return Date.valueOf(localDate);
      // mais complexo
//      Instant instant = localDate.atStartOfDay()
//                                 .atZone(ZoneId.systemDefault()).toInstant();
//
//      return java.util.Date.from(instant);
   }

   public static String formatDate(java.util.Date date, int formatStyle) {
      return DateFormat.getDateInstance(formatStyle).format(date);
   }

   public static String formatDate(java.util.Date date) {
      return formatDate(date,DateFormat.MEDIUM);
   }

   public static String formatDate(java.util.Date date, String pattern) {
      return (new SimpleDateFormat(pattern)).format(date);
   }

   public static java.util.Date parseDate(String date, String pattern) {
      java.util.Date d = null;

      try { d = (new SimpleDateFormat(pattern)).parse(date); }
      catch (ParseException ex) {}

      return d;
   }

   public static java.util.Date parseDate(String date) {
      return parseDate(date,DATE_BR_PATTERN);
   }

   public static java.util.Date parseDate(int year, int month, int day) {
      Calendar c = Calendar.getInstance();

      c.set(year,month,day);

      return c.getTime();
   }

   public static java.util.Date parseDateFromYear(int year) {
      Calendar c = Calendar.getInstance();

      c.setTime(getDate());
      c.set(Calendar.YEAR,year);

      return c.getTime();
   }

   public static java.util.Date parseDateFromMonth(int month) {
      Calendar c = Calendar.getInstance();

      c.setTime(getDate());
      c.set(Calendar.MONTH,month);

      return c.getTime();
   }

   public static java.util.Date parseDateFromDay(int day) {
      Calendar c = Calendar.getInstance();

      c.setTime(getDate());
      c.set(Calendar.DAY_OF_MONTH,day);

      return c.getTime();
   }

   public static java.util.Date getDate() {
      return new java.util.Date();
   }

   public static java.util.Date dateAddYears(java.util.Date date, int years) {
      Calendar c = Calendar.getInstance();

      c.setTime(date);
      c.add(Calendar.YEAR,years);

      return c.getTime();
   }

   public static java.util.Date dateAddMonths(java.util.Date date, int months) {
      Calendar c = Calendar.getInstance();

      c.setTime(date);
      c.add(Calendar.MONTH,months);

      return c.getTime();
   }

   public static java.util.Date dateAddDays(java.util.Date date, int days) {
      Calendar c = Calendar.getInstance();

      c.setTime(date);
      c.add(Calendar.DAY_OF_MONTH,days);

      return c.getTime();
   }

   public static java.util.Date dateNowPlusYears(int years) {
      return dateAddYears(getDate(),years);
   }

   public static java.util.Date dateNowPlusMonths(int months) {
      return dateAddMonths(getDate(),months);
   }

   public static java.util.Date dateNowPlusDays(int days) {
      return dateAddDays(getDate(),days);
   }

   public static java.util.Date dateAddDate(java.util.Date aDate,
                                        java.util.Date bDate) {
      Calendar c = Calendar.getInstance();

      c.setTime(new java.util.Date(aDate.getTime() + bDate.getTime()));

      return c.getTime();
   }

   public static java.util.Date sqlDateToDate(java.sql.Date sqlDate) {
      return new java.util.Date(sqlDate.getTime());
   }

   public static java.sql.Date dateToSqlDate(java.util.Date utilDate) {
      return new java.sql.Date(utilDate.getTime());
   }
   // ==========================================================================
   // === suporte a data e hora em java.util.* - FIM ===========================
   // ==========================================================================
}
