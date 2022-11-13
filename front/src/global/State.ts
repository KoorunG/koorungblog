import { atom } from "recoil";
import { IUser } from "./Types";

// user 전역상태
export const userState = atom<IUser>({
  key: "userState",
  default: { id: 0, username: "", role: "" },
});
