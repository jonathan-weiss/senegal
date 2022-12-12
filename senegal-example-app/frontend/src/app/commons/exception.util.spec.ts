import { ExceptionUtil } from './exception.util';

describe('ExceptionUtil', () => {

  it('should create an error containing all messages', () => {
    const error = ExceptionUtil.createError('foo', 'bar', 'baz');
    expect(error.message).toContain('foo');
    expect(error.message).toContain('bar');
    expect(error.message).toContain('baz');
  });

});
