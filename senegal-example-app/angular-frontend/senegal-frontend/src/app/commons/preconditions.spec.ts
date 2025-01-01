import { Preconditions } from './preconditions';

describe('Preconditions', () => {

  it('should return provided value from checkNotUndefined if value is not undefined', () => {
    const x = 1;
    expect(Preconditions.checkDefined(x, '')).toEqual(x);
  });

  it('should throw error from checkNotUndefined if provided value is undefined', () => {
    const x = undefined;
    expect(() => Preconditions.checkDefined(x, '')).toThrowError();
  });

  it('should throw error with provided message from checkNotUndefined', () => {
    const x = undefined;
    expect(() => Preconditions.checkDefined(x, 'x is undefined')).toThrowError('x is undefined');
  });

  it('should not throw an error from check if condition is true', () => {
    const x = 1;
    expect(() => Preconditions.check(x === 1, '')).not.toThrowError();
  });

  it('should throw error from check if condition is false', () => {
    const x = undefined;
    expect(() => Preconditions.check(x === 2, '')).toThrowError();
  });

  it('should throw error with falsy warning from check if condition is falsy but not false', () => {
    const x = undefined;
    // @ts-ignore
    expect(() => Preconditions.check(x, 'bad value')).toThrowError('[Note: condition was not false but falsy] - bad value');
  });

});
