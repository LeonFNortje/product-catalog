export interface IServiceSpecificationRef {
  href?: string | null;
  id: string;
  name?: string | null;
  version?: string | null;
  schemaLocation?: string | null;
  type?: string | null;
}

export type NewServiceSpecificationRef = Omit<IServiceSpecificationRef, 'id'> & { id: null };
