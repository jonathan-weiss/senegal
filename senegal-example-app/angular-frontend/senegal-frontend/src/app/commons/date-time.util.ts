export class DateTimeUtil {

  private static SHORT_DATE_STYLE: Intl.DateTimeFormatOptions = {
    dateStyle: 'short',
  };

  private static TIME_STYLE: Intl.DateTimeFormatOptions = {
    timeStyle: 'short',
  };

  private static SHORT_SWISS_DATE_FORMAT: Intl.DateTimeFormat = new Intl.DateTimeFormat('de-CH', DateTimeUtil.SHORT_DATE_STYLE);
  private static TIME_SWISS_DATE_FORMAT: Intl.DateTimeFormat = new Intl.DateTimeFormat('de-CH', DateTimeUtil.TIME_STYLE);


  public static formatDateShort(date: Date): string {
    return DateTimeUtil.SHORT_SWISS_DATE_FORMAT.format(date);
  }

  public static formatTime(date: Date): string {
    return DateTimeUtil.TIME_SWISS_DATE_FORMAT.format(date);
  }

  public static formatDateTime(date: Date): string {
    return DateTimeUtil.formatDateShort(date) + ' ' + DateTimeUtil.formatTime(date);
  }

  public static today(): Date {
    this.SHORT_SWISS_DATE_FORMAT.formatToParts(new Date());

    const today = new Date();
    today.setHours(0);
    today.setMinutes(0);
    today.setSeconds(0);
    today.setMilliseconds(0);
    return today;
  }


}
