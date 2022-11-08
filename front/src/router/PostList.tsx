import axios from "axios";
import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import { Link, NavigateFunction, useNavigate, useNavigation } from "react-router-dom";

const baseUrl = "http://localhost:8080";

interface IPost {
  id: number;
  title: string;
  contents: string;
  createdDate: string;
  lastModifiedDate: string;
}

interface IPostProps {
  post: IPost;
  index: number;
  navigate : NavigateFunction;
}

const promiseGetPosts = (
  setPosts: React.Dispatch<React.SetStateAction<IPost[]>>
) => {
  axios.get(baseUrl + "/posts").then((res) => {
    setPosts(res.data);
  });
};

const promiseDelPost = (id: number) => {
  axios.delete(baseUrl + `/posts/${id}`);
};

const PostList = () => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    promiseGetPosts(setPosts);
  }, []);

  const navigate = useNavigate();

  return (
    <div>
      {posts.map((post: IPost, index: number) => {
        return (
          <Post post={post} index={index} navigate={navigate} key={post.id} />
        ); // key값으로 Post의 식별자 사용
      })}
      <br />
      <br />
      <Link to="/articleCreate">
        <Button variant="dark">글 등록</Button>
      </Link>
      <br />
      <br />
      <Button onClick={() => {}}>글 가져오기</Button>
    </div>
  );
};

const Post: React.FC<IPostProps> = ({ post, index, navigate }): JSX.Element => {
  return (
    <>
      <h3>{index + 1}번째 글</h3>
      <b>제목 : {post.title}</b>
      <br />
      <b>내용 : {post.contents}</b>
      <br />
      <b>등록일 : {post.createdDate}</b>
      <br />
      <b>수정일 : {post.lastModifiedDate}</b>
      <br />
      <br />
      <Button
        onClick={() => {
          const isDel = window.confirm("글을 삭제하시겠습니까?");
          if (isDel) {
            promiseDelPost(post.id);
            window.alert("글이 삭제되었습니다.");
            navigate(0);  // 새로고침
          }
        }}
        variant="danger">
        삭제
      </Button>
    </>
  );
};

export default PostList;
