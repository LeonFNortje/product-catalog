export interface IRelatedParty {
  id: string;
  href?: string | null;
  name?: string | null;
  role?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewRelatedParty = Omit<IRelatedParty, 'id'> & { id: null };
