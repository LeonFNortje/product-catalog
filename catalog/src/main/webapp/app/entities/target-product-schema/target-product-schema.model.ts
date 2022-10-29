export interface ITargetProductSchema {
  id: number;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewTargetProductSchema = Omit<ITargetProductSchema, 'id'> & { id: null };
