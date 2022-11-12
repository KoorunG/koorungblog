import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navigator from "../components/Navigator";
import PostForm from "./PostForm";
import Login from "./Login";
import Main from "./Main";
import PostList from "./PostList";
import PostCreateForm from "./PostCreateForm";
import Join from "./Join";

const AppRouter = () => {
  return (
    <>
      <BrowserRouter>
        <Navigator />
        <Routes>
          <Route path="/main/*" element={<Main />} />
          <Route path="/login/*" element={<Login />} />
          <Route path="/posts/*" element={<PostList />} />
          <Route path="/postForm/*" element={<PostForm />} />
          <Route path="/" element={<Navigate replace to="/main" />} />
          <Route path="/articleCreate/*" element={<PostCreateForm />} />
          <Route path="/join" element={<Join/>}/>
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRouter;
