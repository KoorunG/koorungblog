import { useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navigator from "../components/Navigator";
import { IUser } from "../global/Types";
import Join from "./Join";
import Login from "./Login";
import Main from "./Main";
import PostCreateForm from "./PostCreateForm";
import PostDetail from "./PostDetail";
import PostList from "./PostList";

const AppRouter = () => {
  const [user, setUser] = useState<IUser>({ id: 0, username: "", role: "" });

  return (
    <>
      <BrowserRouter>
        <Navigator />
        <Routes>
          <Route path="/main/*" element={<Main user={user} />} />
          <Route path="/login/*" element={<Login setUser={setUser} />} />
          <Route path="/posts/*">
            <Route path="" element={<PostList user={user} />}/>     {/* /posts : 전체 글 목록*/}
            <Route path=":id" element={<PostDetail/>}/>             {/* /posts/:id : 상세 글 내용 */}
          </Route>
          <Route path="/" element={<Navigate replace to="/main" />} />
          <Route path="/form/posts/*" element={<PostCreateForm user={user} />} />
          <Route path="/join" element={<Join />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRouter;
