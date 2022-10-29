import axios, { AxiosResponse } from "axios";
import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

const baseUrl = "http://localhost:8080";

const PostList = () => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    axios.get(baseUrl + "/posts").then((res: AxiosResponse<IPost[]>) => {
      setPosts(res.data);
    });
  }, []);

  return (
    <div>
      {posts.map((post: IPost, index: number) => {
        return <Post post={post} index={index} key={post.id} />; // key값으로 Post의 식별자 사용
      })}
      <br />
      <br />
      <Link to="/articleCreate">
        <Button variant="dark">글 등록</Button>
      </Link>
    </div>
  );
};

const Post: React.FC<IPostWithIndex> = ({ post, index }): JSX.Element => {
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
          deletePost(post.id);
        }}
        variant="danger"
      >
        삭제
      </Button>
    </>
  );
};

interface IPost {
  id: number;
  title: string;
  contents: string;
  createdDate: string;
  lastModifiedDate: string;
}

interface IPostWithIndex {
  post: IPost;
  index: number;
}

/**
 * Post를 삭제하는 메소드
 * @param id post의 식별자
 */
const deletePost = (id: number): void => {
  axios
    .delete(baseUrl + `/posts/${id}`)
    .then(() => {
      console.log("삭제 완료");
    });
};

export default PostList;
