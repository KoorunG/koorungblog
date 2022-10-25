import axios, { AxiosResponse } from "axios";
import React, { useEffect, useState } from "react";

const PostList = () => {
  const baseUrl = "http://localhost:8080";

  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    axios.get(baseUrl + "/posts").then((res: AxiosResponse<IPost[]>) => {
      setPosts(res.data);
    });
  }, []);

  return (
    <div>
      {posts.map((post: IPost, index: number) => {
        return <Post post={post} index={index} key={index} />;  // key값은 가급적 index로 쓰는 것을 피해야하므로 추후 수정
      })}
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
      <br /><br/>
    </>
  );
};

interface IPost {
  title: string;
  contents: string;
  createdDate : string;
  lastModifiedDate : string;
}

interface IPostWithIndex {
  post: IPost;
  index: number;
}

export default PostList;
