
export interface StackEntry {

  isLocked: boolean;

  lock(): void

  unlock(): void

}
