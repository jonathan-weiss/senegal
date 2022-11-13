import { DateTimeUtil } from './date-time.util';

describe('DateTimeUtil', () => {
  const date = new Date(2002, 11, 31, 15, 37, 48);

  it('should format the date in a swiss-german known fashion', () => {
    expect(DateTimeUtil.formatDateShort(date)).toEqual('31.12.02');
  });

  it('should today date have no hours/minutes/seconds', () => {
    const todayDay = DateTimeUtil.today();
    expect(todayDay.getHours()).toEqual(0);
    expect(todayDay.getMinutes()).toEqual(0);
    expect(todayDay.getSeconds()).toEqual(0);
    expect(todayDay.getMilliseconds()).toEqual(0);
  });

});
