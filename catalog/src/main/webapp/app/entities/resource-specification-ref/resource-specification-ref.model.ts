export interface IResourceSpecificationRef {
  href?: string | null;
  id: string;
  name?: string | null;
  version?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewResourceSpecificationRef = Omit<IResourceSpecificationRef, 'id'> & { id: null };
