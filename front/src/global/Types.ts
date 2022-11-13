export interface IUser {
  id: number;
  username: string;
  role: string;
}

export interface IPost {
  id: number;
  title: string;
  contents: string;
  createdDate: string;
  lastModifiedDate: string;
  author?: string;
}

export type AllowedColorScheme =
  | "blue"
  | "cyan"
  | "gray"
  | "green"
  | "orange"
  | "pink"
  | "purple"
  | "red"
  | "teal"
  | "yellow"
  | "whiteAlpha"
  | "blackAlpha"
  | "linkedin"
  | "facebook"
  | "messenger"
  | "whatsapp"
  | "twitter"
  | "telegram"
  | undefined;
