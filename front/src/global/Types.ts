export interface IUser {
    id : number;
    username : string;
    role : string;  
}

export interface IPost {
    id: number;
    title: string;
    contents: string;
    createdDate: string;
    lastModifiedDate: string;
    author? : string;
  }