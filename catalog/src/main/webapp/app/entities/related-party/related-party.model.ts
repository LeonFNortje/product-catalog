export interface IRelatedParty {
  href?: string | null;
  id: string;
  name?: string | null;
  role?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewRelatedParty = Omit<IRelatedParty, 'id'> & { id: null };
