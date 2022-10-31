export interface IRelatedPlace {
  id: string;
  href?: string | null;
  name?: string | null;
  role?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewRelatedPlace = Omit<IRelatedPlace, 'id'> & { id: null };
