export interface JwtPayload {
  sub: string;
  role: { id: number; name: string }; // this is your role object
  exp: number;
  iat: number;
}
