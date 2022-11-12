import { useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navigator from "../components/Navigator";
import { IUser } from "../global/Types";
import Join from "./Join";
import Login from "./Login";
import Main from "./Main";
import PostCreateForm from "./PostCreateForm";
import PostList from "./PostList";

const AppRouter = () => {

  const [user, setUser] = useState<IUser>({id : 0, username : '', role : ''});

  return (
    <>
      <BrowserRouter>
        <Navigator />
        <Routes>
          <Route path="/main/*" element={<Main user={user}/>} />
          <Route path="/login/*" element={<Login setUser={setUser}/>} />
          <Route path="/posts/*" element={<PostList user={user}/>} />
          <Route path="/" element={<Navigate replace to="/main" />} />
          <Route path="/form/posts/*" element={<PostCreateForm user={user}/>} />
          <Route path="/join" element={<Join/>}/>
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRouter;
