import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navigator from "../components/Navigator";
import Join from "./Join";
import Login from "./Login";
import Main from "./Main";
import PostCreateForm from "./PostCreateForm";
import PostDetail from "./PostDetail";
import PostList from "./PostList";

const AppRouter = () => {
  
  return (
    <>
      <BrowserRouter>
        <Navigator />
        <Routes>
          <Route path="/main/*" element={<Main/>} />
          <Route path="/login/*" element={<Login/>} />
          <Route path="/posts/*">
            <Route path="" element={<PostList/>}/>                  {/* /posts : 전체 글 목록*/}
            <Route path=":id" element={<PostDetail/>}/>             {/* /posts/:id : 상세 글 내용 */}
          </Route>
          <Route path="/" element={<Navigate replace to="/main" />} />
          <Route path="/form/posts/*" element={<PostCreateForm/>} />
          <Route path="/join" element={<Join />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRouter;
