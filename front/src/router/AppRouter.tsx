import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navigator from "../components/Navigator";
import PostForm from "./PostForm";
import Login from "./Login";
import Main from "./Main";
import PostList from "./PostList";
import { PostCreateForm } from "./PostCreateForm";

const AppRouter = () => {
  return (
    <>
      <BrowserRouter>
        <Navigator />
        <Routes>
          <Route path="/main/*" element={<Main />} />
          <Route path="/login/*" element={<Login />} />
          <Route path="/article/*" element={<PostList />} />
          <Route path="/articleForm/*" element={<PostForm />} />
          <Route path="/" element={<Navigate replace to="/main" />} />
          <Route path="/articleCreate/*" element={<PostCreateForm />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRouter;
