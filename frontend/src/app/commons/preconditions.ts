import { ExceptionUtil } from './exception.util';

export class Preconditions {
  static checkDefined<T>(value: T | undefined | null, message: string, ...additionalMessages: any[]): T {
    if (value == null) {
      throw Preconditions.createError(message, ...additionalMessages);
    }
    return value;
  }

  static check(condition: boolean, message: string, ...additionalMessages: any[]): void {
    if (condition === false) {
      throw Preconditions.createError(message, ...additionalMessages);
    } else if (!condition) {
      throw Preconditions.createError('[Note: condition was not false but falsy] - ' + message, ...additionalMessages);
    }
  }

  private static createError(message: string, ...additionalMessages: any[]): Error {
    return ExceptionUtil.createError(message, ...additionalMessages);
  }

}
