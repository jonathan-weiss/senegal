export class ExceptionUtil {

  public static throwError<T = void>(message: string, ...args: any[]): T {
    throw this.createError(message, args);
  }

  public static createError(message: string, ...args: any[]): Error {
    let concatenatedMessage = message;
    for(const arg of args) {
      concatenatedMessage += ' ' + arg;
    }
    return new Error(concatenatedMessage);
  }

}
