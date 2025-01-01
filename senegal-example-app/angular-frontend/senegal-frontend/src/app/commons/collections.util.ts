export class CollectionsUtil {
  private static readonly EMPTY_LIST: ReadonlyArray<any> = [];
  private static readonly EMPTY_MAP: ReadonlyMap<any, any> = new Map();

  public static emptyList<T>(): ReadonlyArray<T> {
    return this.EMPTY_LIST;
  }

  public static emptyMap<K, V>(): ReadonlyMap<K, V> {
    return this.EMPTY_MAP;
  }

  public static clearAll<T>(array: Array<T>): void {
    array.length = 0;
  }

  public static addAll<T>(array: Array<T>, arrayToAdd: ReadonlyArray<T>): void {
    array.push(...arrayToAdd);
  }

  public static toMutableArray<T>(array: ReadonlyArray<T>): Array<T> {
    return array.concat();
  }

  public static distinct<T, R>(array: ReadonlyArray<T>, callbackFn: (e: T) => R): Array<T> {
    return array.reduce((prev: T[], item: T) => {
      const unseen = prev
        .filter((e: T) => callbackFn(e) === callbackFn(item))
        .length === 0;
      if (unseen) {
        prev.push(item);
      }
      return prev;
    }, []);
  }
}
